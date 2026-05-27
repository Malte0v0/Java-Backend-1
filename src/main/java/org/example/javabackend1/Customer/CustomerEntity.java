package org.example.javabackend1.Customer;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.javabackend1.Booking.BookingEntity;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String phone;

    @OneToMany(mappedBy = "customer")
    private List<BookingEntity> bookings = new ArrayList<>();
}
