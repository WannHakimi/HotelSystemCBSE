package com.example.hotel_system.repository;

import com.example.hotel_system.models.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BillRepository extends MongoRepository<Bill, String> {
    Bill findByBookingId(String bookingId);
    List<Bill> findByUserId(String userId);
    List<Bill> findByUserIdAndPaymentStatus(String userId, Bill.PaymentStatus status);
}