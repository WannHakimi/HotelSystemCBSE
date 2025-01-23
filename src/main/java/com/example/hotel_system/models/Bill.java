// package com.example.hotel_system.models;

// import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.mapping.Document;
// import java.time.LocalDateTime;

// @Document(collection = "bills")
// public class Bill {
//     @Id
//     private String id;
//     private String bookingId;
//     private String userId;
//     private String roomNumber;
//     private double totalAmount;
//     private PaymentStatus paymentStatus;
//     private LocalDateTime creationDate;
//     private LocalDateTime dueDate;
//     private String notes;

//     public enum PaymentStatus {
//         PENDING,
//         PAID,
//         OVERDUE,
//         CANCELLED
//     }

//     // Constructor
//     public Bill() {
//         this.creationDate = LocalDateTime.now();
//         this.paymentStatus = PaymentStatus.PENDING;
//     }

//     // Getters and Setters
//     public String getId() {
//         return id;
//     }

//     public void setId(String id) {
//         this.id = id;
//     }

//     public String getBookingId() {
//         return bookingId;
//     }

//     public void setBookingId(String bookingId) {
//         this.bookingId = bookingId;
//     }

//     public String getUserId() {
//         return userId;
//     }

//     public void setUserId(String userId) {
//         this.userId = userId;
//     }

//     public String getRoomNumber() {
//         return roomNumber;
//     }

//     public void setRoomNumber(String roomNumber) {
//         this.roomNumber = roomNumber;
//     }

//     public double getTotalAmount() {
//         return totalAmount;
//     }

//     public void setTotalAmount(double totalAmount) {
//         this.totalAmount = totalAmount;
//     }

//     public PaymentStatus getPaymentStatus() {
//         return paymentStatus;
//     }

//     public void setPaymentStatus(PaymentStatus paymentStatus) {
//         this.paymentStatus = paymentStatus;
//     }

//     public LocalDateTime getCreationDate() {
//         return creationDate;
//     }

//     public void setCreationDate(LocalDateTime creationDate) {
//         this.creationDate = creationDate;
//     }

//     public LocalDateTime getDueDate() {
//         return dueDate;
//     }

//     public void setDueDate(LocalDateTime dueDate) {
//         this.dueDate = dueDate;
//     }

//     public String getNotes() {
//         return notes;
//     }

//     public void setNotes(String notes) {
//         this.notes = notes;
//     }
// } 
package com.example.hotel_system.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "bills")
public class Bill {
    @Id
    private String id;
    private String bookingId;
    private String userId;
    private String roomNumber;
    private double totalAmount;
    private PaymentStatus paymentStatus;
    private LocalDateTime creationDate;
    private LocalDateTime dueDate;
    private String notes;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String transactionId;

    public enum PaymentStatus {
        PENDING,
        PAID,
        OVERDUE,
        CANCELLED
    }

    // Constructor
    public Bill() {
        this.creationDate = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.PENDING;
    }

    // Original getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    // New getters and setters for payment fields
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}