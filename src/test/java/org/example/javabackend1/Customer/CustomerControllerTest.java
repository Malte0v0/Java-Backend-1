package org.example.javabackend1.Customer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerController customerController;

    @Autowired
    private CustomerRepository customerRepository;

    private Long savedId;

    @BeforeEach
    void setUp() {
        CustomerEntity c1 = new CustomerEntity(null, "Bert", "Svensson", "bert.svensson@gmail.com", "0707006512");
        CustomerEntity c2 = new CustomerEntity(null, "Arne", "Andersson", "arne.andersson@gmail.com", "0709607512");
        CustomerEntity c3 = new CustomerEntity(null, "Kurt", "Svensson", "kurt.svensson@gmail.com", "0734566587");

        customerRepository.save(c1);
        customerRepository.save(c2);
        CustomerEntity saved = customerRepository.save(c3);
        savedId = saved.getId();
    }

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    public void contextLoads() {
        assertThat(customerController).isNotNull();
    }

    @Test
    void mainMenu_returnsCustomerView() throws Exception {
        mockMvc.perform(get("/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/customer"));
    }

    @Test
    void getAllCustomers_returnsListViewWithCustomers() throws Exception {
        mockMvc.perform(get("/customers/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/list"))
                .andExpect(model().attributeExists("customers"))
                .andExpect(model().attribute("customers", hasSize(3)));
    }

    @Test
    void showCreateForm_returnsNewViewWithEmptyDTO() throws Exception {
        mockMvc.perform(get("/customers/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/new"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void createCustomer_savesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/customers/new")
                        .param("firstName", "Bengt")
                        .param("lastName", "Svensson")
                        .param("email", "bengt.svensson@gmail.com")
                        .param("phone", "0731312313"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers/list"));

        assertThat(customerRepository.findAll()).hasSize(4);
    }

    @Test
    void showEditPage_returnsEditViewWithCustomer() throws Exception {
        mockMvc.perform(get("/customers/{id}/edit", savedId))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/edit"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void updateCustomer_updatesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/customers/{id}/edit", savedId)
                        .param("firstName", "KurtUpdated")
                        .param("lastName", "Svensson")
                        .param("email", "kurt.updated@gmail.com")
                        .param("phone", "0736544453"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers/list"));

        CustomerEntity updated = customerRepository.findById(savedId).orElseThrow();
        assertThat(updated.getFirstName()).isEqualTo("KurtUpdated");
    }

    @Test
    void showDeletePage_returnsDeleteViewWithCustomer() throws Exception {
        mockMvc.perform(get("/customers/{id}/delete", savedId))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/delete"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void deleteCustomer_deletesAndRedirectsToList() throws Exception {
        mockMvc.perform(post("/customers/{id}/delete", savedId))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/customers/list"));

        assertThat(customerRepository.findById(savedId)).isEmpty();
        assertThat(customerRepository.findAll()).hasSize(2);
    }

    @Test
    void findCustomerToEdit_returnsEditViewWithCorrectCustomer() throws Exception {
        mockMvc.perform(get("/customers/edit/find").param("id", savedId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/edit"))
                .andExpect(model().attributeExists("customer"));
    }

    @Test
    void findCustomerToDelete_returnsDeleteViewWithCorrectCustomer() throws Exception {
        mockMvc.perform(get("/customers/delete/find").param("id", savedId.toString()))
                .andExpect(status().isOk())
                .andExpect(view().name("customers/delete"))
                .andExpect(model().attributeExists("customer"));
    }
}
