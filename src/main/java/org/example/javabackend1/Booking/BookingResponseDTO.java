package org.example.javabackend1.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Room.RoomEntity;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingResponseDTO {
    @NotNull
    private Long id;

    @NotNull
    private CustomerEntity customer;

    @NotNull
    private RoomEntity room;

    @NotNull
    private LocalDate checkInDate;

    @NotNull
    private LocalDate checkOutDate;

    @NotNull
    private int numberOfGuests;

    @NotNull
    private BookingStatus status;
}
