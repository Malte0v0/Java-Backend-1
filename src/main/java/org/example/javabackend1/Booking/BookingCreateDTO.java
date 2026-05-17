package org.example.javabackend1.Booking;

import jakarta.validation.constraints.NotNull;
import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Room.RoomEntity;

import java.time.LocalDateTime;

public class BookingCreateDTO {
    @NotNull
    private CustomerEntity customer;

    @NotNull
    private RoomEntity room;

    @NotNull
    private LocalDateTime checkInDate;

    @NotNull
    private LocalDateTime checkOutDate;

    @NotNull
    private int numberOfGuests;

    @NotNull
    private BookingStatus status;
}
