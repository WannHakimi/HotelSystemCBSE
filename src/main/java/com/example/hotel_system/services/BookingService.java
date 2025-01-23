// package com.example.hotel_system.services;

// import com.example.hotel_system.models.Booking;
// import com.example.hotel_system.models.Room;
// import com.example.hotel_system.repository.BookingRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.time.LocalDate;
// import java.time.temporal.ChronoUnit;
// import java.util.List;
// import java.util.Optional;
// import java.util.stream.Collectors;

// @Service
// public class BookingService {

//     @Autowired
//     private BookingRepository bookingRepository;

//     @Autowired
//     private RoomService roomService;

//     public List<Booking> getAllBookings() {
//         return bookingRepository.findAll();
//     }

//     public Optional<Booking> getBookingById(String id) {
//         return bookingRepository.findById(id);
//     }

//     public List<Booking> getBookingsByUser(String userId) {
//         return bookingRepository.findByUserId(userId).stream()
//                 .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
//                 .collect(Collectors.toList());
//     }

//     public Booking createBooking(Booking booking) {
//         // Get room details
//         Room room = roomService.getRoomById(booking.getRoomId())
//             .orElseThrow(() -> new RuntimeException("Room not found"));

//         // Check room availability
//         if (!isRoomAvailable(booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate())) {
//             throw new RuntimeException("Room is not available for the selected dates");
//         }

//         // Validate dates
//         if (booking.getCheckInDate().isBefore(LocalDate.now())) {
//             throw new RuntimeException("Check-in date cannot be in the past");
//         }
//         if (booking.getCheckOutDate().isBefore(booking.getCheckInDate())) {
//             throw new RuntimeException("Check-out date must be after check-in date");
//         }

//         // Calculate total price
//         long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
//         booking.setTotalPrice(room.getBasePrice() * nights);
        
//         // Set booking details
//         booking.setStatus(Booking.BookingStatus.CONFIRMED);
//         booking.setBookingDate(LocalDate.now());
//         booking.setRoomNumber(room.getRoomNumber());

//         return bookingRepository.save(booking);
//     }

//     public Booking cancelBooking(String id) {
//         Booking booking = bookingRepository.findById(id)
//             .orElseThrow(() -> new RuntimeException("Booking not found"));

//         if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
//             throw new RuntimeException("Booking cannot be cancelled");
//         }

//         if (booking.getCheckInDate().isBefore(LocalDate.now())) {
//             throw new RuntimeException("Past bookings cannot be cancelled");
//         }

//         booking.setStatus(Booking.BookingStatus.CANCELLED);
//         return bookingRepository.save(booking);
//     }

//     public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {
//         List<Booking> conflictingBookings = bookingRepository
//             .findConflictingBookings(roomId, checkIn, checkOut);
        
//         return conflictingBookings.isEmpty();
//     }

//     public Optional<Booking> getCurrentBookingForUser(String userId) {
//         return bookingRepository.findCurrentActiveBooking(userId, LocalDate.now());
//     }

//     public List<Booking> getRecentBookingsForUser(String userId, int limit) {
//         return bookingRepository.findByUserId(userId)
//             .stream()
//             .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
//             .limit(limit)
//             .collect(Collectors.toList());
//     }

//     public int getTotalActiveBookings() {
//         return bookingRepository.countByStatus(Booking.BookingStatus.CONFIRMED);
//     }

//     public List<Booking> getTodayCheckIns() {
//         return bookingRepository.findByCheckInDate(LocalDate.now());
//     }

//     public List<Booking> getTodayCheckOuts() {
//         return bookingRepository.findByCheckOutDate(LocalDate.now());
//     }
// } 

// // package com.example.hotel_system.services;

// // import com.example.hotel_system.models.Booking;
// // import com.example.hotel_system.models.Room;
// // import com.example.hotel_system.repository.BookingRepository;
// // import com.example.hotel_system.repository.BillRepository;
// // import org.springframework.beans.factory.annotation.Autowired;
// // import org.springframework.stereotype.Service;
// // import org.springframework.transaction.annotation.Transactional;

// // import java.time.LocalDate;
// // import java.time.LocalDateTime;
// // import java.time.temporal.ChronoUnit;
// // import java.util.Arrays;
// // import java.util.List;
// // import java.util.Optional;
// // import java.util.stream.Collectors;

// // @Service
// // public class BookingService {

// //     @Autowired
// //     private BookingRepository bookingRepository;

// //     @Autowired
// //     private RoomService roomService;

// //     @Autowired
// //     private BillRepository billRepository;

// //     // Basic CRUD Operations
// //     public List<Booking> getAllBookings() {
// //         return bookingRepository.findAll();
// //     }

// //     public Optional<Booking> getBookingById(String id) {
// //         return bookingRepository.findById(id);
// //     }

// //     public List<Booking> getBookingsByUser(String userId) {
// //         return bookingRepository.findByUserId(userId).stream()
// //                 .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
// //                 .collect(Collectors.toList());
// //     }

// //     // Booking Creation and Modification
// //     @Transactional
// //     public Booking createBooking(Booking booking) {
// //         // Get room details
// //         Room room = roomService.getRoomById(booking.getRoomId())
// //             .orElseThrow(() -> new RuntimeException("Room not found"));

// //         // Check room availability
// //         if (!isRoomAvailable(booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate())) {
// //             throw new RuntimeException("Room is not available for the selected dates");
// //         }

// //         // Validate dates
// //         validateBookingDates(booking.getCheckInDate(), booking.getCheckOutDate());

// //         // Calculate total price
// //         long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
// //         booking.setTotalPrice(room.getBasePrice() * nights);
        
// //         // Set booking details
// //         booking.setStatus(Booking.BookingStatus.CONFIRMED);
// //         booking.setBookingDate(LocalDate.now());
// //         booking.setRoomNumber(room.getRoomNumber());
// //         booking.setHasBeenExtended(false);
// //         booking.setNumberOfExtensions(0);

// //         return bookingRepository.save(booking);
// //     }

// //     @Transactional
// //     public Booking cancelBooking(String id) {
// //         Booking booking = bookingRepository.findById(id)
// //             .orElseThrow(() -> new RuntimeException("Booking not found"));

// //         if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
// //             throw new RuntimeException("Only confirmed bookings can be cancelled");
// //         }

// //         if (booking.getCheckInDate().isBefore(LocalDate.now())) {
// //             throw new RuntimeException("Past bookings cannot be cancelled");
// //         }

// //         // Check if there are any unpaid bills
// //         if (billRepository.existsByBookingIdAndPaymentStatusNot(id, Bill.PaymentStatus.PAID)) {
// //             throw new RuntimeException("Cannot cancel booking with unpaid bills");
// //         }

// //         booking.setStatus(Booking.BookingStatus.CANCELLED);
// //         return bookingRepository.save(booking);
// //     }

// //     @Transactional
// //     public Booking extendBooking(String id, int days) {
// //         if (days <= 0 || days > 7) {
// //             throw new RuntimeException("Extension days must be between 1 and 7");
// //         }

// //         Booking booking = bookingRepository.findById(id)
// //             .orElseThrow(() -> new RuntimeException("Booking not found"));

// //         if (booking.getNumberOfExtensions() >= 2) {
// //             throw new RuntimeException("Maximum number of extensions reached");
// //         }

// //         if (booking.getStatus() != Booking.BookingStatus.CHECKED_IN) {
// //             throw new RuntimeException("Only checked-in bookings can be extended");
// //         }

// //         LocalDate newCheckOutDate = booking.getCheckOutDate().plusDays(days);
        
// //         // Check if the room is available for the extended period
// //         if (!isRoomAvailable(booking.getRoomId(), booking.getCheckOutDate(), newCheckOutDate)) {
// //             throw new RuntimeException("Room is not available for the requested extension period");
// //         }

// //         // Calculate additional cost
// //         Room room = roomService.getRoomById(booking.getRoomId())
// //             .orElseThrow(() -> new RuntimeException("Room not found"));

// //         double extensionCost = room.getBasePrice() * days;
        
// //         booking.setOriginalPrice(booking.getTotalPrice());
// //         booking.setTotalPrice(booking.getTotalPrice() + extensionCost);
// //         booking.setCheckOutDate(newCheckOutDate);
// //         booking.setHasBeenExtended(true);
// //         booking.setNumberOfExtensions(booking.getNumberOfExtensions() + 1);
// //         booking.setExtensionDays(booking.getExtensionDays() + days);
// //         booking.setStatus(Booking.BookingStatus.EXTENDED);

// //         return bookingRepository.save(booking);
// //     }

// //     // Validation Methods
// //     public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {
// //         List<Booking> conflictingBookings = bookingRepository
// //             .findConflictingBookings(roomId, checkIn, checkOut);
        
// //         return conflictingBookings.isEmpty();
// //     }

// //     private void validateBookingDates(LocalDate checkIn, LocalDate checkOut) {
// //         if (checkIn.isBefore(LocalDate.now())) {
// //             throw new RuntimeException("Check-in date cannot be in the past");
// //         }
// //         if (checkOut.isBefore(checkIn)) {
// //             throw new RuntimeException("Check-out date must be after check-in date");
// //         }
// //         if (ChronoUnit.DAYS.between(checkIn, checkOut) > 30) {
// //             throw new RuntimeException("Maximum booking duration is 30 days");
// //         }
// //     }

// //     // Query Methods
// //     public Optional<Booking> getCurrentBookingForUser(String userId) {
// //         return bookingRepository.findCurrentActiveBooking(userId, LocalDate.now());
// //     }

// //     public List<Booking> getRecentBookingsForUser(String userId, int limit) {
// //         return bookingRepository.findByUserId(userId)
// //             .stream()
// //             .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
// //             .limit(limit)
// //             .collect(Collectors.toList());
// //     }

// //     public List<Booking> getActiveBookings() {
// //         return bookingRepository.findByStatusIn(Arrays.asList(
// //             Booking.BookingStatus.CONFIRMED,
// //             Booking.BookingStatus.CHECKED_IN,
// //             Booking.BookingStatus.EXTENDED
// //         ));
// //     }

// //     // Billing-related Methods
// //     public List<Booking> getEligibleBookingsForBilling() {
// //         return bookingRepository.findByStatusIn(Arrays.asList(
// //             Booking.BookingStatus.CHECKED_IN,
// //             Booking.BookingStatus.COMPLETED
// //         )).stream()
// //         .filter(booking -> !billRepository.existsByBookingId(booking.getId()))
// //         .collect(Collectors.toList());
// //     }

// //     public boolean isBillable(String bookingId) {
// //         Optional<Booking> bookingOpt = getBookingById(bookingId);
// //         if (!bookingOpt.isPresent()) {
// //             return false;
// //         }
        
// //         Booking booking = bookingOpt.get();
// //         boolean hasValidStatus = booking.getStatus() == Booking.BookingStatus.CHECKED_IN || 
// //                                booking.getStatus() == Booking.BookingStatus.COMPLETED;
// //         boolean hasNoBill = !billRepository.existsByBookingId(bookingId);
        
// //         return hasValidStatus && hasNoBill;
// //     }

// //     // Statistics and Reports
// //     public int getTotalActiveBookings() {
// //         return bookingRepository.countByStatus(Booking.BookingStatus.CONFIRMED);
// //     }

// //     public List<Booking> getTodayCheckIns() {
// //         return bookingRepository.findByCheckInDate(LocalDate.now());
// //     }

// //     public List<Booking> getTodayCheckOuts() {
// //         return bookingRepository.findByCheckOutDate(LocalDate.now());
// //     }

// //     public List<Booking> getBookingsWithExtensions() {
// //         return bookingRepository.findByHasBeenExtendedTrue();
// //     }

// //     public double calculateTotalRevenue(LocalDate startDate, LocalDate endDate) {
// //         return bookingRepository.findByCheckInDateBetween(startDate, endDate)
// //             .stream()
// //             .filter(booking -> booking.getStatus() != Booking.BookingStatus.CANCELLED)
// //             .mapToDouble(Booking::getTotalPrice)
// //             .sum();
// //     }

// //     public double calculateAverageBookingDuration(LocalDate startDate, LocalDate endDate) {
// //         List<Booking> bookings = bookingRepository.findByCheckInDateBetween(startDate, endDate);
// //         if (bookings.isEmpty()) {
// //             return 0;
// //         }
        
// //         double totalDays = bookings.stream()
// //             .filter(booking -> booking.getStatus() != Booking.BookingStatus.CANCELLED)
// //             .mapToLong(booking -> ChronoUnit.DAYS.between(
// //                 booking.getCheckInDate(), 
// //                 booking.getCheckOutDate()
// //             ))
// //             .sum();
        
// //         return totalDays / bookings.size();
// //     }
// // }

package com.example.hotel_system.services;

import com.example.hotel_system.models.Booking;
import com.example.hotel_system.models.Room;
import com.example.hotel_system.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomService roomService;

    // Basic CRUD Operations
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingById(String id) {
        return bookingRepository.findById(id);
    }

    public List<Booking> getBookingsByUser(String userId) {
        return bookingRepository.findByUserId(userId).stream()
                .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
                .collect(Collectors.toList());
    }

    // Booking Creation and Validation
    @Transactional
    public Booking createBooking(Booking booking) {
        // Get room details
        Room room = roomService.getRoomById(booking.getRoomId())
            .orElseThrow(() -> new RuntimeException("Room not found"));

        // Validate dates
        validateBookingDates(booking.getCheckInDate(), booking.getCheckOutDate());

        // Check for existing active bookings for this user
        List<Booking> overlappingBookings = bookingRepository.findOverlappingBookingsForUser(
            booking.getUserId(),
            booking.getCheckInDate(),
            booking.getCheckOutDate()
        );

        if (!overlappingBookings.isEmpty()) {
            throw new RuntimeException("You already have an active booking for this period");
        }

        // Check room availability
        if (!isRoomAvailable(booking.getRoomId(), booking.getCheckInDate(), booking.getCheckOutDate())) {
            throw new RuntimeException("Room is not available for the selected dates");
        }

        // Calculate total price
        long nights = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        booking.setTotalPrice(room.getBasePrice() * nights);
        
        // Set booking details
        booking.setStatus(Booking.BookingStatus.CONFIRMED);
        booking.setBookingDate(LocalDate.now());
        booking.setRoomNumber(room.getRoomNumber());
        booking.setHasBeenExtended(false);
        booking.setNumberOfExtensions(0);

        return bookingRepository.save(booking);
    }

    @Transactional
    public Booking cancelBooking(String id) {
        Booking booking = bookingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED) {
            throw new RuntimeException("Only confirmed bookings can be cancelled");
        }

        if (booking.getCheckInDate().isBefore(LocalDate.now())) {
            throw new RuntimeException("Past bookings cannot be cancelled");
        }

        booking.setStatus(Booking.BookingStatus.CANCELLED);
        return bookingRepository.save(booking);
    }

    // Validation Methods
    private void validateBookingDates(LocalDate checkIn, LocalDate checkOut) {
        if (checkIn == null || checkOut == null) {
            throw new RuntimeException("Check-in and check-out dates are required");
        }
        
        if (checkIn.isBefore(LocalDate.now())) {
            throw new RuntimeException("Check-in date cannot be in the past");
        }
        
        if (checkOut.isBefore(checkIn)) {
            throw new RuntimeException("Check-out date must be after check-in date");
        }
        
        long nights = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (nights > 30) {
            throw new RuntimeException("Maximum booking duration is 30 days");
        }
        
        if (nights < 1) {
            throw new RuntimeException("Minimum booking duration is 1 night");
        }
    }

    public boolean isRoomAvailable(String roomId, LocalDate checkIn, LocalDate checkOut) {
        List<Booking> conflictingBookings = bookingRepository.findConflictingBookings(roomId, checkIn, checkOut);
        return conflictingBookings.isEmpty();
    }

    // Query Methods
    public Optional<Booking> getCurrentBookingForUser(String userId) {
        List<Booking> activeBookings = bookingRepository.findCurrentActiveBookings(userId, LocalDate.now());
        return activeBookings.stream()
            .max(Comparator.comparing(Booking::getBookingDate));
    }

    public List<Booking> getRecentBookingsForUser(String userId, int limit) {
        return bookingRepository.findByUserId(userId).stream()
            .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
            .limit(limit)
            .collect(Collectors.toList());
    }

    public List<Booking> getActiveBookings() {
        return bookingRepository.findByStatusIn(Arrays.asList(
            Booking.BookingStatus.CONFIRMED,
            Booking.BookingStatus.CHECKED_IN,
            Booking.BookingStatus.EXTENDED
        ));
    }

    // Statistics and Reports
    public int getTotalActiveBookings() {
        return bookingRepository.countByStatus(Booking.BookingStatus.CONFIRMED);
    }

    public List<Booking> getTodayCheckIns() {
        return bookingRepository.findByCheckInDate(LocalDate.now());
    }

    public List<Booking> getTodayCheckOuts() {
        return bookingRepository.findByCheckOutDate(LocalDate.now());
    }

    public List<Booking> getBookingsWithExtensions() {
        return bookingRepository.findByHasBeenExtendedTrue();
    }

    public double calculateAverageBookingDuration(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = bookingRepository.findAll().stream()
            .filter(b -> !b.getCheckInDate().isBefore(startDate) && !b.getCheckOutDate().isAfter(endDate))
            .filter(b -> b.getStatus() != Booking.BookingStatus.CANCELLED)
            .collect(Collectors.toList());

        if (bookings.isEmpty()) {
            return 0;
        }

        double totalDays = bookings.stream()
            .mapToLong(booking -> ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate()))
            .sum();

        return totalDays / bookings.size();
    }
}