package com.example.hotel_system.controllers;

import com.example.hotel_system.models.Room;
import com.example.hotel_system.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/receptionist/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public String listRooms(@RequestParam(required = false) String roomType,
                            @RequestParam(required = false) String status,
                            Model model) {
        // Get the logged-in user's details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());

        // Fetch and display rooms
        List<Room> rooms = roomService.searchRooms(roomType, status);
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomType", roomType);
        model.addAttribute("status", status);
        return "receptionist/room-list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        // Get the logged-in user's details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());

        // Add an empty room object for the form
        model.addAttribute("room", new Room());
        return "receptionist/room-form";
    }

    @PostMapping("/add")
    public String addRoom(@ModelAttribute Room room) {
        roomService.addRoom(room);
        return "redirect:/receptionist/rooms";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        // Get the logged-in user's details
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        model.addAttribute("role", auth.getAuthorities().toString());

        // Fetch the room to edit
        Room room = roomService.getRoomById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        model.addAttribute("room", room);
        return "receptionist/room-form";
    }

    @PostMapping("/edit/{id}")
    public String updateRoom(@PathVariable String id, @ModelAttribute Room room) {
        roomService.updateRoom(id, room);
        return "redirect:/receptionist/rooms";
    }

    @GetMapping("/delete/{id}")
    public String deleteRoom(@PathVariable String id) {
        roomService.deleteRoom(id);
        return "redirect:/receptionist/rooms";
    }
}