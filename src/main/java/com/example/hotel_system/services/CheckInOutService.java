package com.example.hotel_system.services;

import com.example.hotel_system.models.Booking;
import com.example.hotel_system.models.Room;
import com.example.hotel_system.models.Status;
import com.example.hotel_system.repository.BookingRepository;
import com.example.hotel_system.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckInOutService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Transactional
    public Booking markAsCheckedIn(String bookingId) {
        // Fetch booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Validate booking status
        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
            throw new RuntimeException("Only confirmed bookings can be checked in");
        }

        // Validate check-in date
        if (booking.getCheckInDate().isAfter(LocalDate.now())) {
            throw new RuntimeException("Cannot check in before the scheduled check-in date");
        }

        if (booking.getCheckInDate().isBefore(LocalDate.now().minusDays(1))) {
            throw new RuntimeException("Booking has expired. Please create a new booking");
        }

        // Get room and validate its status
        Room room = roomRepository.findById(booking.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.getStatus() == Status.UNDER_MAINTENANCE) {
            throw new RuntimeException("Room is under maintenance");
        }

        if (room.getStatus() == Status.OCCUPIED) {
            throw new RuntimeException("Room is currently occupied");
        }

        // Update room status
        room.setStatus(Status.OCCUPIED);
        roomRepository.save(room);

        // Update booking
        booking.setStatus(Booking.BookingStatus.CHECKED_IN);
        booking.setCheckInTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking markAsCheckedOut(String bookingId) {
        // Fetch booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Validate booking status
        if (booking.getStatus() != Booking.BookingStatus.CHECKED_IN) {
            throw new RuntimeException("Only checked-in bookings can be checked out");
        }

        // Get room
        Room room = roomRepository.findById(booking.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Validate if check-out is not too early
        if (LocalDate.now().isBefore(booking.getCheckOutDate()) && 
            !isLateCheckOut(booking.getCheckOutTime())) {
            throw new RuntimeException("Early check-out requires approval");
        }

        // Update room status
        room.setStatus(Status.AVAILABLE);
        roomRepository.save(room);

        // Update booking
        booking.setStatus(Booking.BookingStatus.COMPLETED);
        booking.setCheckOutTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    private boolean isLateCheckOut(LocalDateTime checkOutTime) {
        if (checkOutTime == null) return false;
        return checkOutTime.isAfter(LocalDateTime.now().withHour(11).withMinute(0));
    }
    
    public List<Booking> getTodaysCheckIns() {
        return bookingRepository.findByCheckInDate(LocalDate.now())
                .stream()
                .filter(booking -> booking.getStatus() == Booking.BookingStatus.CONFIRMED)
                .collect(Collectors.toList());
    }

    public List<Booking> getTodaysCheckOuts() {
        return bookingRepository.findByCheckOutDate(LocalDate.now())
                .stream()
                .filter(booking -> booking.getStatus() == Booking.BookingStatus.CHECKED_IN)
                .collect(Collectors.toList());
    }
    
    public boolean canCheckIn(Booking booking) {
        if (booking == null) return false;
        
        return booking.getStatus() == Booking.BookingStatus.CONFIRMED && 
               !booking.getCheckInDate().isAfter(LocalDate.now()) &&
               !booking.getCheckInDate().isBefore(LocalDate.now().minusDays(1));
    }
    
    public boolean canCheckOut(Booking booking) {
        if (booking == null) return false;
        
        return booking.getStatus() == Booking.BookingStatus.CHECKED_IN;
    }
}