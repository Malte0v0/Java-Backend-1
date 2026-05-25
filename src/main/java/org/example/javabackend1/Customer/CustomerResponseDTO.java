package org.example.javabackend1.Customer;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;

    @Email
    private String email;
    private String phone;
}
