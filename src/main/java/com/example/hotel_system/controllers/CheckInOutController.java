package com.example.hotel_system.controllers;

import com.example.hotel_system.models.Booking;
import com.example.hotel_system.services.BookingService;
import com.example.hotel_system.services.CheckInOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/receptionist/checkinout")
public class CheckInOutController {

    @Autowired
    private CheckInOutService checkInOutService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("")
    public String showCheckInOutPage(Model model) {
        // Get authentication details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());

        // Get today's check-ins and check-outs
        List<Booking> checkIns = bookingService.getTodayCheckIns();
        List<Booking> checkOuts = bookingService.getTodayCheckOuts();

        model.addAttribute("checkIns", checkIns);
        model.addAttribute("checkOuts", checkOuts);

        // Add helper methods for UI
        model.addAttribute("checkInOutService", checkInOutService);

        return "receptionist/checkinout";
    }

    @PostMapping("/checkin")
    public String markAsCheckedIn(@RequestParam String bookingId, RedirectAttributes redirectAttributes) {
        try {
            Booking booking = checkInOutService.markAsCheckedIn(bookingId);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Check-in successful for Room " + booking.getRoomNumber());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Check-in failed: " + e.getMessage());
        }
        return "redirect:/receptionist/checkinout";
    }

    @PostMapping("/checkout")
    public String markAsCheckedOut(@RequestParam String bookingId, RedirectAttributes redirectAttributes) {
        try {
            Booking booking = checkInOutService.markAsCheckedOut(bookingId);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Check-out successful for Room " + booking.getRoomNumber());
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Check-out failed: " + e.getMessage());
        }
        return "redirect:/receptionist/checkinout";
    }
}