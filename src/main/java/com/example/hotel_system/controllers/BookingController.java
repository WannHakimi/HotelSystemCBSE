// BookingController.java
package com.example.hotel_system.controllers;

import com.example.hotel_system.models.Booking;
import com.example.hotel_system.models.Room;
import com.example.hotel_system.services.BookingService;
import com.example.hotel_system.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    // Show booking form
    @GetMapping("/guest/create/{roomId}")
    public String showBookingForm(@PathVariable String roomId, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Room room = roomService.getRoomById(roomId)
            .orElseThrow(() -> new RuntimeException("Room not found"));

        // Create new booking with pre-filled roomId
        Booking booking = new Booking();
        booking.setRoomId(roomId);
        
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        model.addAttribute("room", room);
        model.addAttribute("booking", booking);
        
        return "guest/booking-form";
    }

    // Process booking creation
    @PostMapping("/guest/create")
    public String createBooking(@ModelAttribute Booking booking, RedirectAttributes redirectAttributes) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            booking.setUserId(auth.getName());
            
            // Create the booking
            Booking createdBooking = bookingService.createBooking(booking);
            
            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", 
                "Booking confirmed for Room " + createdBooking.getRoomNumber());
            
            return "redirect:/guest/dashboard";
        } catch (RuntimeException e) {
            // Add error message
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/bookings/guest/create/" + booking.getRoomId();
        }
    }

    // View booking list
    @GetMapping("/guest/list")
    public String listBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        model.addAttribute("username", userId);
        model.addAttribute("role", auth.getAuthorities().toString());
        model.addAttribute("bookings", bookingService.getBookingsByUser(userId));
        
        return "guest/booking-list";
    }

    // Cancel booking
    @PostMapping("/guest/cancel/{id}")
    public String cancelBooking(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            bookingService.cancelBooking(id);
            redirectAttributes.addFlashAttribute("successMessage", "Booking cancelled successfully");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/bookings/guest/list";
    }

    @GetMapping("/receptionist/list")
    public String listAllBookings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        model.addAttribute("bookings", bookingService.getAllBookings());
        
        return "receptionist/booking-list";
    }
}
