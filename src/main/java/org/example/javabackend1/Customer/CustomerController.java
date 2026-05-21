package org.example.javabackend1.Customer;

import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/api/customers")
//public class CustomerController {
//
//    private final CustomerService customerService;
//
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @GetMapping
//    public List<CustomerDTO> getAllCustomers() {
//        return customerService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public CustomerDTO getCustomerById(@PathVariable Long id) {
//        return customerService.findById(id);
//    }
//
//    @PostMapping
//    public CustomerDTO createCustomer(@RequestBody CustomerCreateDTO dto) {
//        return customerService.create(dto);
//    }
//
//    @PutMapping("/{id}")
//    public CustomerDTO updateCustomer(@PathVariable Long id,
//                                      @RequestBody CustomerCreateDTO dto) {
//        return customerService.update(id, dto);
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteCustomer(@PathVariable Long id) {
//        customerService.delete(id);
//    }
//}
//
