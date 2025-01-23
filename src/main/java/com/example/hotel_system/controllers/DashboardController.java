// DashboardController.java
package com.example.hotel_system.controllers;

import com.example.hotel_system.services.BookingService;
import com.example.hotel_system.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private RoomService roomService;

    @GetMapping("/receptionist/dashboard")
    public String receptionistDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());
        return "receptionist/dashboard";
    }

    @GetMapping("/guest/dashboard")
    public String guestDashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        
        model.addAttribute("username", userId);
        model.addAttribute("role", auth.getAuthorities().toString());
        
        // Get current booking (if exists) and unwrap it
        bookingService.getCurrentBookingForUser(userId).ifPresent(booking -> 
            model.addAttribute("currentBooking", booking)
        );
        
        // Get available rooms
        model.addAttribute("availableRooms", roomService.searchRooms(null, "AVAILABLE"));
        
        // Get recent bookings
        model.addAttribute("recentBookings", bookingService.getRecentBookingsForUser(userId, 5));
        
        return "guest/dashboard";
    }
}