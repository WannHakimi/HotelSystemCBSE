package com.example.hotel_system.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String userId;
    private String roomId;
    private String roomNumber;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private BookingStatus status;
    private double totalPrice;
    private LocalDate bookingDate;
    
    // Extension related fields
    private int extensionDays;
    private String extensionReason;
    private LocalDateTime extensionRequestTime;
    private double originalPrice; // To track price before extension
    private boolean hasBeenExtended;
    private int numberOfExtensions;
    
    public enum BookingStatus {
        CONFIRMED,
        CHECKED_IN,
        COMPLETED,
        CANCELLED,
        EXTENSION_PENDING,  // New status for when extension is requested but not approved
        EXTENDED;          // New status for when extension is approved
        
        public String getLowerCase() {
            return this.name().toLowerCase();
        }
    }

    // Default constructor
    public Booking() {
        this.extensionDays = 0;
        this.hasBeenExtended = false;
        this.numberOfExtensions = 0;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDateTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalDateTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalDateTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalDateTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    // Extension related getters and setters
    public int getExtensionDays() {
        return extensionDays;
    }

    public void setExtensionDays(int extensionDays) {
        this.extensionDays = extensionDays;
    }

    public String getExtensionReason() {
        return extensionReason;
    }

    public void setExtensionReason(String extensionReason) {
        this.extensionReason = extensionReason;
    }

    public LocalDateTime getExtensionRequestTime() {
        return extensionRequestTime;
    }

    public void setExtensionRequestTime(LocalDateTime extensionRequestTime) {
        this.extensionRequestTime = extensionRequestTime;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public boolean isHasBeenExtended() {
        return hasBeenExtended;
    }

    public void setHasBeenExtended(boolean hasBeenExtended) {
        this.hasBeenExtended = hasBeenExtended;
    }

    public int getNumberOfExtensions() {
        return numberOfExtensions;
    }

    public void setNumberOfExtensions(int numberOfExtensions) {
        this.numberOfExtensions = numberOfExtensions;
    }

    // Helper methods
    public double getExtensionCost() {
        return totalPrice - originalPrice;
    }

    public boolean canBeExtended() {
        return (status == BookingStatus.CONFIRMED || status == BookingStatus.CHECKED_IN) 
            && numberOfExtensions < 2  // Limit to maximum 2 extensions
            && !checkOutDate.isBefore(LocalDate.now());
    }

    public void applyExtension(int days, double additionalCost) {
        if (originalPrice == 0) {
            originalPrice = totalPrice;  // Store original price before first extension
        }
        extensionDays += days;
        totalPrice += additionalCost;
        checkOutDate = checkOutDate.plusDays(days);
        hasBeenExtended = true;
        numberOfExtensions++;
        status = BookingStatus.EXTENDED;
    }
}