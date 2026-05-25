package org.example.javabackend1.Booking;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.javabackend1.Customer.CustomerEntity;
import org.example.javabackend1.Room.RoomEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingCreateDTO {
    @NotNull
    private CustomerEntity customer;

    @NotNull
    private RoomEntity room;

    @NotNull
    private String checkInDate;

    @NotNull
    private String checkOutDate;

    @NotNull
    private int numberOfGuests;

    @NotNull
    private BookingStatus status;
}
