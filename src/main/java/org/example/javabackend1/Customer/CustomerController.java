package org.example.javabackend1.Customer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private final CustomerRepository repo;

    public CustomerController(CustomerRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/add")
    public String addCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setFirstName("asd");
        customer.setLastName("awdwadaw");
        customer.setEmail("dwadaw@dwada.com");
        customer.setPhone(231312312);
        this.repo.save(customer);
        return "Saved";
    }
}
