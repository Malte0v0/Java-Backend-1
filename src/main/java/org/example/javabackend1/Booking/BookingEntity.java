package org.example.javabackend1.Booking;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Room.RoomEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table (name = "bookning")
public class BookingEntity {

    @Id
    @GeneratedValue
    private Long id;
    private int numberOfGuests;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private BookingStatus status;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;




}
