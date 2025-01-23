package com.example.hotel_system.controllers;

import com.example.hotel_system.models.Booking;
import com.example.hotel_system.services.BookingService;
import com.example.hotel_system.services.CheckoutExtensionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/guest")
public class CheckoutExtensionController {

    @Autowired
    private CheckoutExtensionService checkoutExtensionService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/extend-checkout")
    public String showExtendCheckoutForm(Model model) {
        // Get authentication details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();

        // Add user info to model
        model.addAttribute("username", userId);
        model.addAttribute("role", auth.getAuthorities().toString());

        // Get current booking if exists
        bookingService.getCurrentBookingForUser(userId).ifPresent(booking -> {
            model.addAttribute("currentBooking", booking);
            model.addAttribute("canExtend", checkoutExtensionService.canExtendCheckout(booking));
        });

        return "guest/extend-checkout";
    }

    @PostMapping("/extend-checkout")
    public String processExtendCheckout(
            @RequestParam String bookingId,
            @RequestParam int extensionDays,
            @RequestParam String reason,
            RedirectAttributes redirectAttributes) {
        try {
            // Verify user authorization
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = auth.getName();

            // Verify booking belongs to user
            Booking booking = bookingService.getBookingById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
            
            if (!booking.getUserId().equals(userId)) {
                throw new RuntimeException("Unauthorized access to booking");
            }

            // Process extension request
            Booking updatedBooking = checkoutExtensionService.requestCheckoutExtension(
                bookingId, extensionDays, reason);
            
            // Add success message
            redirectAttributes.addFlashAttribute("successMessage", 
                String.format("Checkout extended successfully to %s. Additional cost: $%.2f", 
                    updatedBooking.getCheckOutDate(),
                    updatedBooking.getTotalPrice() - booking.getTotalPrice()));
                
        } catch (RuntimeException e) {
            // Add error message
            redirectAttributes.addFlashAttribute("errorMessage", 
                "Failed to extend checkout: " + e.getMessage());
        }
        
        return "redirect:/guest/extend-checkout";
    }
}