package org.example.javabackend1.Customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public String mainMenu() {
        return "/customers/customer";
    }

    @GetMapping("/list")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", this.customerService.findAll());
        return "customers/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customerForm", new CustomerCreateDTO());
        return "customers/new";
    }

    @PostMapping("/new")
    public String createCustomer(@ModelAttribute CustomerCreateDTO dto) {
        this.customerService.create(dto);
        return "redirect:/customers/list";
    }

    @GetMapping("/edit")
    public String showEditSearch() {
        return "customers/edit";
    }

    @GetMapping("/edit/find")
    public String findCustomerToEdit(@RequestParam Long id, Model model) {
        model.addAttribute("customer", this.customerService.findById(id));
        return "customers/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateCustomerById(@PathVariable Long id,
                                    @ModelAttribute CustomerCreateDTO dto) {
        customerService.update(id, dto);
        return "redirect:/customers/list";
    }

    @GetMapping("/delete")
    public String showDeleteSearch() {
        return "customers/delete";
    }

    @GetMapping("/delete/find")
    public String findCustomerToDelete(@RequestParam Long id, Model model) {
        model.addAttribute("customer", this.customerService.findById(id));
        return "customers/delete";
    }

    @PostMapping("/{id}/delete")
    public String deleteCustomerById(@PathVariable Long id) {
        this.customerService.delete(id);
        return "redirect:/customers/list";
    }
}

