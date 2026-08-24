package org.example.javabackend1.Customer;

import org.example.javabackend1.Exceptions.CustomerException;
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
        return "customers/customer";
    }

    // --- GET /customers/list ---
    @GetMapping("/list")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", this.customerService.findAll());
        return "customers/list";
    }



    //              ------------------------------ NEW ------------------------------
    // ↓↓↓↓↓ SKAPA EN NY CUSTOMER ↓↓↓↓↓
    // --- GET /customers/new ---
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new CustomerCreateDTO());
        return "customers/new";
    }

    @PostMapping("/new")
    // --- POST /customers/new ---
    public String createCustomer(@ModelAttribute("customer") CustomerCreateDTO dto) {
        this.customerService.create(dto);
        return "redirect:/customers/list";
    }
    // ↑↑↑↑↑ SKAPA EN NY CUSTOMER ↑↑↑↑↑



    //              ------------------------------ EDIT ------------------------------
    // --- GET /customers/edit ---
    @GetMapping("/edit")
    public String showEditSearch() {
        return "customers/edit";
    }

    // ↓↓↓↓↓ HITTA EN CUSTOMER VIA ID ↓↓↓↓↓
    // --- GET /customers/edit/find ---
    @GetMapping("/edit/find")
    public String findCustomerToEdit(@RequestParam Long id, Model model) {
        model.addAttribute("customer", this.customerService.findById(id));
        return "customers/edit";
    }
    // ↑↑↑↑↑ HITTA EN CUSTOMER VIA ID ↑↑↑↑↑

    // ↓↓↓↓↓ REDIGERA EN CUSTOMER ↓↓↓↓↓
    // --- GET /customers/{id}/edit ---
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "customers/edit";
    }

    // --- POST /customers/{id}/edit ---
    @PostMapping("/{id}/edit")
    public String updateCustomerById(@PathVariable Long id, @ModelAttribute("customer") CustomerCreateDTO dto) {
        customerService.update(id, dto);
        return "redirect:/customers/list";
    }
    // ↑↑↑↑↑ REDIGERA EN CUSTOMER ↑↑↑↑↑



    //              ------------------------------ DELETE ------------------------------
    // --- GET /customers/delete ---
    @GetMapping("/delete")
    public String showDeleteSearch() {
        return "redirect:/customers/list";
    }

    // ↓↓↓↓↓ RADERA EN CUSTOMER ↓↓↓↓↓
    // --- GET /customers/{id}/delete ---
    @GetMapping("/{id}/delete")
    public String showDeletePage(
            @PathVariable Long id, Model model) {
        model.addAttribute("customer",
                this.customerService.findById(id));
        return "customers/delete";
    }

    // --- POST /customers/{id}/delete ---
    @PostMapping("/{id}/delete")
    public String deleteCustomerById(@PathVariable Long id, Model model) {
        try {
            this.customerService.delete(id);
            return "redirect:/customers/list";
        } catch (CustomerException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("customer", customerService.findById(id));
            return "customers/delete";
        }
    }
    // ↑↑↑↑↑ RADERA EN CUSTOMER ↑↑↑↑↑

    // ↓↓↓↓↓ HITTA EN CUSTOMER VIA ID ↓↓↓↓↓
    // --- GET /customers/delete/find ---
    @GetMapping("/delete/find")
    public String findCustomerToDelete(@RequestParam Long id, Model model) {
        model.addAttribute("customer", this.customerService.findById(id));
        return "customers/delete";
    }
    // ↑↑↑↑↑ HITTA EN CUSTOMER VIA ID ↑↑↑↑↑
}

