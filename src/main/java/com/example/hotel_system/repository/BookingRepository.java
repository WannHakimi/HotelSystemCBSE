// // // package com.example.hotel_system.repository;

// // // import com.example.hotel_system.models.Booking;
// // // import org.springframework.data.mongodb.repository.MongoRepository;
// // // import org.springframework.data.mongodb.repository.Query;
// // // import org.springframework.stereotype.Repository;
// // // import java.time.LocalDate;
// // // import java.time.LocalDateTime;
// // // import java.util.List;
// // // import java.util.Optional;

// // // @Repository
// // // public interface BookingRepository extends MongoRepository<Booking, String> {
// // //     // Basic CRUD operations are inherited from MongoRepository

// // //     // Find bookings by user
// // //     List<Booking> findByUserId(String userId);
    
// // //     // Find bookings by status
// // //     List<Booking> findByStatus(Booking.BookingStatus status);
    
// // //     // Find bookings by user and status
// // //     List<Booking> findByUserIdAndStatus(String userId, Booking.BookingStatus status);
    
// // //     // Find bookings by check-in/out dates
// // //     List<Booking> findByCheckInDate(LocalDate date);
// // //     List<Booking> findByCheckOutDate(LocalDate date);
    
// // //     // Find current active booking for a user
// // //     @Query("{'userId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
// // //            "'checkInDate': {$lte: ?1}, 'checkOutDate': {$gte: ?1}}")
// // //     Optional<Booking> findCurrentActiveBooking(String userId, LocalDate currentDate);
    
// // //     // Find all extended bookings for a user
// // //     List<Booking> findByUserIdAndHasBeenExtendedTrue(String userId);
    
// // //     // Find pending extension requests
// // //     List<Booking> findByStatusAndExtensionRequestTimeNotNull(Booking.BookingStatus status);
    
// // //     // Find conflicting bookings for room availability check
// // //     @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, $or: [" +
// // //            "{'checkInDate': {$lt: ?2, $gt: ?1}}, " +
// // //            "{'checkOutDate': {$lt: ?2, $gt: ?1}}, " +
// // //            "{$and: [" +
// // //            "{'checkInDate': {$lte: ?1}}, " +
// // //            "{'checkOutDate': {$gte: ?2}}" +
// // //            "]}" +
// // //            "]}")
// // //     List<Booking> findConflictingBookings(String roomId, LocalDate startDate, LocalDate endDate);
    
// // //     // Count bookings by status
// // //     int countByStatus(Booking.BookingStatus status);
    
// // //     // Find bookings with extensions
// // //     List<Booking> findByHasBeenExtendedTrue();
    
// // //     // Find bookings with multiple extensions
// // //     @Query("{'numberOfExtensions': {$gt: 1}}")
// // //     List<Booking> findBookingsWithMultipleExtensions();
    
// // //     // Find extended bookings for a specific date range
// // //     @Query("{'hasBeenExtended': true, 'checkInDate': {$gte: ?0}, 'checkOutDate': {$lte: ?1}}")
// // //     List<Booking> findExtendedBookingsInDateRange(LocalDate startDate, LocalDate endDate);
    
// // //     // Find bookings that were extended in the last N days
// // //     @Query("{'hasBeenExtended': true, 'extensionRequestTime': {$gte: ?0}}")
// // //     List<Booking> findRecentlyExtendedBookings(LocalDateTime since);
    
// // //     // Find bookings by room number and status
// // //     List<Booking> findByRoomNumberAndStatus(String roomNumber, Booking.BookingStatus status);
    
// // //     // Find all future bookings for a room
// // //     @Query("{'roomId': ?0, 'checkInDate': {$gte: ?1}}")
// // //     List<Booking> findFutureBookingsForRoom(String roomId, LocalDate currentDate);
    
// // //     // Custom query to find overlapping bookings for a room
// // //     @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
// // //            "$or: [" +
// // //            "{checkInDate: {$lte: ?2}, checkOutDate: {$gte: ?1}}" +
// // //            "]}")
// // //     List<Booking> findOverlappingBookings(String roomId, LocalDate startDate, LocalDate endDate);
// // // }
// // package com.example.hotel_system.repository;

// // import com.example.hotel_system.models.Booking;
// // import org.springframework.data.mongodb.repository.MongoRepository;
// // import org.springframework.data.mongodb.repository.Query;
// // import org.springframework.stereotype.Repository;
// // import java.time.LocalDate;
// // import java.time.LocalDateTime;
// // import java.util.List;
// // import java.util.Optional;

// // @Repository
// // public interface BookingRepository extends MongoRepository<Booking, String> {
// //     // Basic CRUD operations are inherited from MongoRepository

// //     // Find bookings by user
// //     List<Booking> findByUserId(String userId);
    
// //     // Find bookings by status
// //     List<Booking> findByStatus(Booking.BookingStatus status);
    
// //     // Find bookings by multiple statuses
// //     List<Booking> findByStatusIn(List<Booking.BookingStatus> statuses);
    
// //     // Find bookings by user and status
// //     List<Booking> findByUserIdAndStatus(String userId, Booking.BookingStatus status);
    
// //     // Find bookings by check-in/out dates
// //     List<Booking> findByCheckInDate(LocalDate date);
// //     List<Booking> findByCheckOutDate(LocalDate date);
    
// //     // Find current active booking for a user
// //     @Query("{'userId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
// //            "'checkInDate': {$lte: ?1}, 'checkOutDate': {$gte: ?1}}")
// //     Optional<Booking> findCurrentActiveBooking(String userId, LocalDate currentDate);
    
// //     // Find all extended bookings for a user
// //     List<Booking> findByUserIdAndHasBeenExtendedTrue(String userId);
    
// //     // Find pending extension requests
// //     List<Booking> findByStatusAndExtensionRequestTimeNotNull(Booking.BookingStatus status);
    
// //     // Find conflicting bookings for room availability check
// //     @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, $or: [" +
// //            "{'checkInDate': {$lt: ?2, $gt: ?1}}, " +
// //            "{'checkOutDate': {$lt: ?2, $gt: ?1}}, " +
// //            "{$and: [" +
// //            "{'checkInDate': {$lte: ?1}}, " +
// //            "{'checkOutDate': {$gte: ?2}}" +
// //            "]}" +
// //            "]}")
// //     List<Booking> findConflictingBookings(String roomId, LocalDate startDate, LocalDate endDate);
    
// //     // Count bookings by status
// //     int countByStatus(Booking.BookingStatus status);
    
// //     // Find bookings with extensions
// //     List<Booking> findByHasBeenExtendedTrue();
    
// //     // Find bookings with multiple extensions
// //     @Query("{'numberOfExtensions': {$gt: 1}}")
// //     List<Booking> findBookingsWithMultipleExtensions();
    
// //     // Find extended bookings for a specific date range
// //     @Query("{'hasBeenExtended': true, 'checkInDate': {$gte: ?0}, 'checkOutDate': {$lte: ?1}}")
// //     List<Booking> findExtendedBookingsInDateRange(LocalDate startDate, LocalDate endDate);
    
// //     // Find bookings that were extended in the last N days
// //     @Query("{'hasBeenExtended': true, 'extensionRequestTime': {$gte: ?0}}")
// //     List<Booking> findRecentlyExtendedBookings(LocalDateTime since);
    
// //     // Find bookings by room number and status
// //     List<Booking> findByRoomNumberAndStatus(String roomNumber, Booking.BookingStatus status);
    
// //     // Find all future bookings for a room
// //     @Query("{'roomId': ?0, 'checkInDate': {$gte: ?1}}")
// //     List<Booking> findFutureBookingsForRoom(String roomId, LocalDate currentDate);
    
// //     // Custom query to find overlapping bookings for a room
// //     @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
// //            "$or: [" +
// //            "{checkInDate: {$lte: ?2}, checkOutDate: {$gte: ?1}}" +
// //            "]}")
// //     List<Booking> findOverlappingBookings(String roomId, LocalDate startDate, LocalDate endDate);
// // }
// package com.example.hotel_system.repository;

// import com.example.hotel_system.models.Booking;
// import org.springframework.data.mongodb.repository.MongoRepository;
// import org.springframework.data.mongodb.repository.Query;
// import org.springframework.stereotype.Repository;
// import java.time.LocalDate;
// import java.time.LocalDateTime;
// import java.util.List;

// @Repository
// public interface BookingRepository extends MongoRepository<Booking, String> {
//     // Find bookings by user
//     List<Booking> findByUserId(String userId);
    
//     // Find bookings by status
//     List<Booking> findByStatus(Booking.BookingStatus status);
    
//     // Find bookings by multiple statuses
//     List<Booking> findByStatusIn(List<Booking.BookingStatus> statuses);
    
//     // Find bookings by user and status
//     List<Booking> findByUserIdAndStatus(String userId, Booking.BookingStatus status);
    
//     // Find bookings by check-in/out dates
//     List<Booking> findByCheckInDate(LocalDate date);
//     List<Booking> findByCheckOutDate(LocalDate date);
    
//     // Find current active bookings for a user
//     @Query("{'userId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
//            "'checkInDate': {$lte: ?1}, 'checkOutDate': {$gte: ?1}}")
//     List<Booking> findCurrentActiveBookings(String userId, LocalDate currentDate);
    
//     // Find all extended bookings for a user
//     List<Booking> findByUserIdAndHasBeenExtendedTrue(String userId);
    
//     // Find pending extension requests
//     List<Booking> findByStatusAndExtensionRequestTimeNotNull(Booking.BookingStatus status);
    
//     // Find conflicting bookings for room availability check
//     @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, $or: [" +
//            "{'checkInDate': {$lt: ?2, $gt: ?1}}, " +
//            "{'checkOutDate': {$lt: ?2, $gt: ?1}}, " +
//            "{$and: [" +
//            "{'checkInDate': {$lte: ?1}}, " +
//            "{'checkOutDate': {$gte: ?2}}" +
//            "]}" +
//            "]}")
//     List<Booking> findConflictingBookings(String roomId, LocalDate startDate, LocalDate endDate);
    
//     // Count bookings by status
//     int countByStatus(Booking.BookingStatus status);
    
//     // Find bookings with extensions
//     List<Booking> findByHasBeenExtendedTrue();
    
//     // Find bookings with multiple extensions
//     @Query("{'numberOfExtensions': {$gt: 1}}")
//     List<Booking> findBookingsWithMultipleExtensions();
    
//     // Find extended bookings for a specific date range
//     @Query("{'hasBeenExtended': true, 'checkInDate': {$gte: ?0}, 'checkOutDate': {$lte: ?1}}")
//     List<Booking> findExtendedBookingsInDateRange(LocalDate startDate, LocalDate endDate);
    
//     // Find bookings that were extended in the last N days
//     @Query("{'hasBeenExtended': true, 'extensionRequestTime': {$gte: ?0}}")
//     List<Booking> findRecentlyExtendedBookings(LocalDateTime since);
    
//     // Find bookings by room number and status
//     List<Booking> findByRoomNumberAndStatus(String roomNumber, Booking.BookingStatus status);
    
//     // Find all future bookings for a room
//     @Query("{'roomId': ?0, 'checkInDate': {$gte: ?1}}")
//     List<Booking> findFutureBookingsForRoom(String roomId, LocalDate currentDate);
    
//     // Custom query to find overlapping bookings for a room
//     @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
//            "$or: [" +
//            "{checkInDate: {$lte: ?2}, checkOutDate: {$gte: ?1}}" +
//            "]}")
//     List<Booking> findOverlappingBookings(String roomId, LocalDate startDate, LocalDate endDate);
    
//     // Find overlapping bookings for a user
//     @Query("{'userId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
//            "$or: [" +
//            "{checkInDate: {$lte: ?2}, checkOutDate: {$gte: ?1}}" +
//            "]}")
//     List<Booking> findOverlappingBookingsForUser(String userId, LocalDate startDate, LocalDate endDate);
// }
package com.example.hotel_system.repository;

import com.example.hotel_system.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends MongoRepository<Booking, String> {
    // Find bookings by user
    List<Booking> findByUserId(String userId);
    
    // Find bookings by status
    List<Booking> findByStatus(Booking.BookingStatus status);
    
    // Find bookings by multiple statuses
    List<Booking> findByStatusIn(List<Booking.BookingStatus> statuses);
    
    // Find bookings by user and status
    List<Booking> findByUserIdAndStatus(String userId, Booking.BookingStatus status);
    
    // Find bookings by check-in/out dates
    List<Booking> findByCheckInDate(LocalDate date);
    List<Booking> findByCheckOutDate(LocalDate date);
    
    // Find current active bookings for a user
    @Query("{'userId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
           "'checkInDate': {$lte: ?1}, 'checkOutDate': {$gte: ?1}}")
    List<Booking> findCurrentActiveBookings(String userId, LocalDate currentDate);
    
    // Find all extended bookings for a user
    List<Booking> findByUserIdAndHasBeenExtendedTrue(String userId);
    
    // Find pending extension requests
    List<Booking> findByStatusAndExtensionRequestTimeNotNull(Booking.BookingStatus status);
    
    // Find conflicting bookings for room availability check
    @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, $or: [" +
           "{'checkInDate': {$lt: ?2, $gt: ?1}}, " +
           "{'checkOutDate': {$lt: ?2, $gt: ?1}}, " +
           "{$and: [" +
           "{'checkInDate': {$lte: ?1}}, " +
           "{'checkOutDate': {$gte: ?2}}" +
           "]}" +
           "]}")
    List<Booking> findConflictingBookings(String roomId, LocalDate startDate, LocalDate endDate);
    
    // Count bookings by status
    int countByStatus(Booking.BookingStatus status);
    
    // Find bookings with extensions
    List<Booking> findByHasBeenExtendedTrue();
    
    // Find bookings with multiple extensions
    @Query("{'numberOfExtensions': {$gt: 1}}")
    List<Booking> findBookingsWithMultipleExtensions();
    
    // Find extended bookings for a specific date range
    @Query("{'hasBeenExtended': true, 'checkInDate': {$gte: ?0}, 'checkOutDate': {$lte: ?1}}")
    List<Booking> findExtendedBookingsInDateRange(LocalDate startDate, LocalDate endDate);
    
    // Find bookings that were extended in the last N days
    @Query("{'hasBeenExtended': true, 'extensionRequestTime': {$gte: ?0}}")
    List<Booking> findRecentlyExtendedBookings(LocalDateTime since);
    
    // Find bookings by room number and status
    List<Booking> findByRoomNumberAndStatus(String roomNumber, Booking.BookingStatus status);
    
    // Find all future bookings for a room
    @Query("{'roomId': ?0, 'checkInDate': {$gte: ?1}}")
    List<Booking> findFutureBookingsForRoom(String roomId, LocalDate currentDate);
    
    // Custom query to find overlapping bookings for a room
    @Query("{'roomId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
           "$or: [" +
           "{checkInDate: {$lte: ?2}, checkOutDate: {$gte: ?1}}" +
           "]}")
    List<Booking> findOverlappingBookings(String roomId, LocalDate startDate, LocalDate endDate);
    
    // Find overlapping bookings for a user
    @Query("{'userId': ?0, 'status': {$in: ['CONFIRMED', 'CHECKED_IN', 'EXTENDED']}, " +
           "$or: [" +
           "{checkInDate: {$lte: ?2}, checkOutDate: {$gte: ?1}}" +
           "]}")
    List<Booking> findOverlappingBookingsForUser(String userId, LocalDate startDate, LocalDate endDate);
}