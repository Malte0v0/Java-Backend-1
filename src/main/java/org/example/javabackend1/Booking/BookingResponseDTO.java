package org.example.javabackend1.Booking;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingResponseDTO {
    @NotNull
    private Long id;
    @Email
    private String customerEmail;
    @NotNull
    private Long customerId;
    @NotNull
    private String customerFirstName;
    @NotNull
    private String customerLastName;
    @NotNull
    private Long roomId;
    @NotNull
    private String roomType;
    @NotNull
    private int extraBeds;
    @NotNull
    private LocalDate checkInDate;
    @NotNull
    private LocalDate checkOutDate;
    @NotNull
    private int numberOfGuests;
}
