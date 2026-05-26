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
        return "customers/customer";
    }



    @GetMapping("/list")
    public String getAllCustomers(Model model) {
        model.addAttribute("customers", this.customerService.findAll());
        return "customers/list";
    }


    // /customers/new
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new CustomerCreateDTO());
        return "customers/new";
        // Visar
        // templates/customers/new.html
    }



    @PostMapping("/new")
    public String createCustomer(@ModelAttribute("customer") CustomerCreateDTO dto) {
        this.customerService.create(dto);

        return "redirect:/customers/list";
    }
    // Efter save:
    // gå tillbaka till customerlistan.

    // /customers/edit
    @GetMapping("/edit")
    public String showEditSearch() {
        return "customers/edit";
    }


    // /customers/3/edit
    @GetMapping("/{id}/edit")
    public String showEditPage(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        // templates/customers/edit.html
        return "customers/edit";
    }


    @GetMapping("/edit/find")
    public String findCustomerToEdit(@RequestParam Long id, Model model) {
        model.addAttribute("customer", this.customerService.findById(id));
        return "customers/edit";
    }



    @PostMapping("/{id}/edit")
    public String updateCustomerById(@PathVariable Long id,
                                    @ModelAttribute("customer") CustomerCreateDTO dto) {
        customerService.update(id, dto);
        return "redirect:/customers/list";
    }


    // /customers/delete
    @GetMapping("/delete")
    public String showDeleteSearch() {
        return "redirect:/customers/list";
    }

    // /customers/3/delete
    @GetMapping("/{id}/delete")
    public String showDeletePage(
            @PathVariable Long id, Model model) {
        model.addAttribute("customer",
                this.customerService.findById(id));
// Hämtar customer som ska tas bort.

        return "customers/delete";
    }

    // Körs när man söker customer att deletea via id.
    @GetMapping("/delete/find")
    public String findCustomerToDelete(@RequestParam Long id, Model model) {
        model.addAttribute("customer", this.customerService.findById(id));
        return "customers/delete";
    }


    // Körs när delet knappen trycks.
    @PostMapping("/{id}/delete")
    public String deleteCustomerById(@PathVariable Long id) {
        this.customerService.delete(id);
        return "redirect:/customers/list";
    }
}

