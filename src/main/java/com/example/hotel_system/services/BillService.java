// // package com.example.hotel_system.services;

// // import com.example.hotel_system.models.Bill;
// // import com.example.hotel_system.models.Booking;
// // import com.example.hotel_system.repository.BillRepository;
// // import com.example.hotel_system.repository.BookingRepository;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;

// // import java.time.LocalDateTime;

// // @Service
// // public class BillService {

// //     @Autowired
// //     private BillRepository billRepository;

// //     @Autowired
// //     private BookingRepository bookingRepository;

// //     public Bill generateBill(String bookingId) {
// //         // Check if a bill already exists for this booking
// //         Bill existingBill = billRepository.findByBookingId(bookingId);
// //         if (existingBill != null) {
// //             throw new RuntimeException("A bill already exists for this booking.");
// //         }

// //         // Fetch the booking details
// //         Booking booking = bookingRepository.findById(bookingId)
// //                 .orElseThrow(() -> new RuntimeException("Booking not found"));

// //         // Create a new bill
// //         Bill bill = new Bill();
// //         bill.setBookingId(bookingId);
// //         bill.setUserId(booking.getUserId());
// //         bill.setRoomNumber(booking.getRoomNumber());
// //         bill.setTotalAmount(booking.getTotalPrice());
// //         bill.setDueDate(LocalDateTime.now().plusDays(7)); // Set due date to 7 days from now

// //         // Save the bill
// //         return billRepository.save(bill);
// //     }

// //     public Bill getBillByBookingId(String bookingId) {
// //         return billRepository.findByBookingId(bookingId);
// //     }

// //     public Bill getBillById(String id) {
// //         return billRepository.findById(id)
// //                 .orElseThrow(() -> new RuntimeException("Bill not found"));
// //     }
// // }
// package com.example.hotel_system.services;

// import com.example.hotel_system.models.Bill;
// import com.example.hotel_system.models.Booking;
// import com.example.hotel_system.repository.BillRepository;
// import com.example.hotel_system.repository.BookingRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.time.LocalDateTime;
// import java.util.List;

// @Service
// public class BillService {

//     @Autowired
//     private BillRepository billRepository;

//     @Autowired
//     private BookingRepository bookingRepository;

//     public Bill generateBill(String bookingId) {
//         // Check if a bill already exists for this booking
//         Bill existingBill = billRepository.findByBookingId(bookingId);
//         if (existingBill != null) {
//             throw new RuntimeException("A bill already exists for this booking.");
//         }

//         // Fetch the booking details
//         Booking booking = bookingRepository.findById(bookingId)
//                 .orElseThrow(() -> new RuntimeException("Booking not found"));

//         // Create a new bill
//         Bill bill = new Bill();
//         bill.setBookingId(bookingId);
//         bill.setUserId(booking.getUserId());
//         bill.setRoomNumber(booking.getRoomNumber());
//         bill.setTotalAmount(booking.getTotalPrice());
//         bill.setDueDate(LocalDateTime.now().plusDays(7)); // Set due date to 7 days from now

//         // Save the bill
//         return billRepository.save(bill);
//     }

//     public Bill getBillByBookingId(String bookingId) {
//         return billRepository.findByBookingId(bookingId);
//     }

//     public Bill getBillById(String id) {
//         return billRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Bill not found"));
//     }

//     // New methods for guest functionality
//     public List<Bill> getBillsByUserId(String userId) {
//         return billRepository.findByUserId(userId);
//     }

//     public List<Bill> getPendingBillsByUserId(String userId) {
//         return billRepository.findByUserIdAndPaymentStatus(userId, Bill.PaymentStatus.PENDING);
//     }

//     public Bill updateBill(Bill bill) {
//         return billRepository.save(bill);
//     }

//     public Bill processBillPayment(String billId, String userId, String paymentMethod) {
//         Bill bill = getBillById(billId);
        
//         if (bill == null || !bill.getUserId().equals(userId)) {
//             throw new RuntimeException("Bill not found or access denied");
//         }
        
//         if (bill.getPaymentStatus() != Bill.PaymentStatus.PENDING) {
//             throw new RuntimeException("Bill is not in pending status");
//         }
        
//         bill.setPaymentStatus(Bill.PaymentStatus.PAID);
//         bill.setPaymentDate(LocalDateTime.now());
//         bill.setPaymentMethod(paymentMethod);
//         bill.setTransactionId("TXN" + System.currentTimeMillis());
        
//         return billRepository.save(bill);
//     }
// }

package com.example.hotel_system.services;

import com.example.hotel_system.models.Bill;
import com.example.hotel_system.models.Booking;
import com.example.hotel_system.repository.BillRepository;
import com.example.hotel_system.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BillService {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public List<Bill> getAllBills() {
        return billRepository.findAll();
    }

    public Bill generateBill(String bookingId) {
        // Check if a bill already exists for this booking
        Bill existingBill = billRepository.findByBookingId(bookingId);
        if (existingBill != null) {
            throw new RuntimeException("A bill already exists for this booking.");
        }

        // Fetch the booking details
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Create a new bill
        Bill bill = new Bill();
        bill.setBookingId(bookingId);
        bill.setUserId(booking.getUserId());
        bill.setRoomNumber(booking.getRoomNumber());
        bill.setTotalAmount(booking.getTotalPrice());
        bill.setDueDate(LocalDateTime.now().plusDays(7)); // Set due date to 7 days from now

        // Save the bill
        return billRepository.save(bill);
    }

    public Bill getBillByBookingId(String bookingId) {
        return billRepository.findByBookingId(bookingId);
    }

    public Bill getBillById(String id) {
        return billRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bill not found"));
    }

    public List<Bill> getBillsByUserId(String userId) {
        return billRepository.findByUserId(userId);
    }

    public List<Bill> getPendingBillsByUserId(String userId) {
        return billRepository.findByUserIdAndPaymentStatus(userId, Bill.PaymentStatus.PENDING);
    }

    public Bill updateBill(Bill bill) {
        return billRepository.save(bill);
    }

    public Bill processBillPayment(String billId, String userId, String paymentMethod) {
        // Get the bill and perform validations
        Bill bill = getBillById(billId);
        
        if (bill == null || !bill.getUserId().equals(userId)) {
            throw new RuntimeException("Bill not found or access denied");
        }
        
        if (bill.getPaymentStatus() != Bill.PaymentStatus.PENDING) {
            throw new RuntimeException("Bill is not in pending status");
        }
        
        // Update payment information
        bill.setPaymentStatus(Bill.PaymentStatus.PAID);
        bill.setPaymentDate(LocalDateTime.now());
        bill.setPaymentMethod(paymentMethod);
        
        // Generate a unique transaction ID using timestamp and random number
        String transactionId = String.format("TXN%d%04d", 
            System.currentTimeMillis(), 
            (int)(Math.random() * 10000));
        bill.setTransactionId(transactionId);
        
        // Save and return the updated bill
        return billRepository.save(bill);
    }
}