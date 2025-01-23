package com.example.hotel_system.services;

import com.example.hotel_system.models.Room;
import com.example.hotel_system.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public Optional<Room> getRoomById(String id) {
        return roomRepository.findById(id);
    }

    public Room addRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room updateRoom(String id, Room roomDetails) {
        Room room = roomRepository.findById(id).orElseThrow(() -> new RuntimeException("Room not found"));
        room.setRoomNumber(roomDetails.getRoomNumber());
        room.setRoomType(roomDetails.getRoomType());
        room.setBasePrice(roomDetails.getBasePrice());
        room.setStatus(roomDetails.getStatus());
        room.setMaintenanceSchedule(roomDetails.getMaintenanceSchedule());
        return roomRepository.save(room);
    }

    public void deleteRoom(String id) {
        roomRepository.deleteById(id);
    }

    public List<Room> searchRooms(String roomType, String status) {
        if (roomType != null && status != null) {
            return roomRepository.findByRoomTypeAndStatus(roomType, status); // Use the new method
        } else if (roomType != null) {
            return roomRepository.findByRoomType(roomType);
        } else if (status != null) {
            return roomRepository.findByStatus(status);
        } else {
            return roomRepository.findAll();
        }
    }
}