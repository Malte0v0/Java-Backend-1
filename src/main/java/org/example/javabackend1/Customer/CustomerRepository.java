package org.example.javabackend1.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
@RestController
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
}