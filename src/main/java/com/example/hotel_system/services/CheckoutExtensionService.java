package com.example.hotel_system.services;

import com.example.hotel_system.models.Booking;
import com.example.hotel_system.models.Room;
import com.example.hotel_system.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CheckoutExtensionService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @Transactional
    public Booking requestCheckoutExtension(String bookingId, int extensionDays, String reason) {
        // Validate extension request
        if (extensionDays <= 0 || extensionDays > 3) {
            throw new RuntimeException("Extension days must be between 1 and 3");
        }

        // Get the booking
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Validate booking status
        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED && 
            booking.getStatus() != Booking.BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Cannot extend checkout for this booking status");
        }

        // Validate extension request timing
        LocalDate today = LocalDate.now();
        if (booking.getCheckOutDate().isBefore(today)) {
            throw new RuntimeException("Cannot extend past bookings");
        }

        // Check if the room is available for the extended period
        LocalDate newCheckoutDate = booking.getCheckOutDate().plusDays(extensionDays);
        if (!bookingService.isRoomAvailable(booking.getRoomId(), booking.getCheckOutDate(), newCheckoutDate)) {
            throw new RuntimeException("Room is not available for the requested extension period");
        }

        // Calculate additional cost
        Room room = roomService.getRoomById(booking.getRoomId())
            .orElseThrow(() -> new RuntimeException("Room not found"));
        double additionalCost = room.getBasePrice() * extensionDays;

        // Update booking
        booking.setCheckOutDate(newCheckoutDate);
        booking.setTotalPrice(booking.getTotalPrice() + additionalCost);
        booking.setExtensionDays(extensionDays);
        booking.setExtensionReason(reason);
        booking.setExtensionRequestTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    public boolean canExtendCheckout(Booking booking) {
        if (booking == null) return false;

        // Check if booking is active
        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED && 
            booking.getStatus() != Booking.BookingStatus.CHECKED_IN) {
            return false;
        }

        // Check if checkout date is not in the past
        if (booking.getCheckOutDate().isBefore(LocalDate.now())) {
            return false;
        }

        // Check if already extended maximum times (e.g., limit to one extension)
        if (booking.getExtensionDays() > 0) {
            return false;
        }

        return true;
    }
}