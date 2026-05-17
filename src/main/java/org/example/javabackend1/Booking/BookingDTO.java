package org.example.javabackend1.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Room.RoomEntity;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDTO {
    @NotNull
    private Long id;

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
