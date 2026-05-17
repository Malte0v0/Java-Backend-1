package org.example.javabackend1.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    List<CustomerEntity> findByFirstNameAndLastName(String firstName, String lastName);
    List<CustomerEntity> findByEmail(String email);
    List<CustomerEntity> findByPhone(String phone);
}