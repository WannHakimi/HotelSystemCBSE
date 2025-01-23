package com.example.hotel_system.repository;

import com.example.hotel_system.models.Room;
import com.example.hotel_system.models.RoomType;
import com.example.hotel_system.models.Status;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findByRoomType(String roomType);
    List<Room> findByStatus(String status);
    List<Room> findByRoomNumber(String roomNumber);
    List<Room> findByRoomTypeAndStatus(String roomType, String status); // Add this method
}