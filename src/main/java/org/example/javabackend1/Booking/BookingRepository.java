package org.example.javabackend1.Booking;

import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Room.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<BookingEntity, Long> {
    List<BookingEntity> findByCustomer(CustomerEntity customer);
    List<BookingEntity> findByRoom(RoomEntity room);
    List<BookingEntity> findByStatus(BookingStatus status);
}
