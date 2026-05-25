package org.example.javabackend1.Customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return customerService.findById(id);
    }

    @PostMapping
    public CustomerResponseDTO createCustomer(@RequestBody CustomerCreateDTO dto) {
        return customerService.create(dto);
    }

    @PutMapping("/{id}")
    public CustomerResponseDTO updateCustomer(@PathVariable Long id,
                                              @RequestBody CustomerCreateDTO dto) {
        return customerService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
    }
}

