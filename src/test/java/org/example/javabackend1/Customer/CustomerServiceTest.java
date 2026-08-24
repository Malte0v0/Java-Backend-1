package org.example.javabackend1.Customer;

import org.example.javabackend1.Exceptions.CustomerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAllInBatch();
    }

    @Test
    void create_shouldSaveAndReturnCustomer() {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setFirstName("Bengt");
        dto.setLastName("Svensson");
        dto.setEmail("bengt@test.com");
        dto.setPhone("070485648");

        CustomerResponseDTO result = customerService.create(dto);

        assertNotNull(result.getId());
        assertEquals("Bengt", result.getFirstName());
        assertEquals("bengt@test.com", result.getEmail());
    }

    @Test
    void create_shouldThrow_whenEmailAlreadyExists() {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setFirstName("Bengt");
        dto.setLastName("Svensson");
        dto.setEmail("duplicate@test.com");
        dto.setPhone("070485648");

        customerService.create(dto);

        assertThrows(CustomerException.class, () -> customerService.create(dto));
    }

    @Test
    void findById_shouldReturnCustomer() {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setFirstName("Bengt");
        dto.setLastName("Svensson");
        dto.setEmail("bengt@test.com");
        dto.setPhone("070485648");

        CustomerResponseDTO created = customerService.create(dto);
        CustomerResponseDTO found = customerService.findById(created.getId());

        assertEquals(created.getId(), found.getId());
        assertEquals("Bengt", found.getFirstName());
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        assertThrows(CustomerException.class, () -> customerService.findById(999L));
    }

    @Test
    void findAll_shouldReturnAllCustomers() {
        CustomerCreateDTO dto1 = new CustomerCreateDTO();
        dto1.setFirstName("A"); dto1.setLastName("A"); dto1.setEmail("a@test.com"); dto1.setPhone("111");

        CustomerCreateDTO dto2 = new CustomerCreateDTO();
        dto2.setFirstName("B"); dto2.setLastName("B"); dto2.setEmail("b@test.com"); dto2.setPhone("222");

        customerService.create(dto1);
        customerService.create(dto2);

        List<CustomerResponseDTO> all = customerService.findAll();
        assertEquals(2, all.size());
    }

    @Test
    void update_shouldChangeCustomerFields() {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setFirstName("Old"); dto.setLastName("Name"); dto.setEmail("old@test.com"); dto.setPhone("000");
        CustomerResponseDTO created = customerService.create(dto);

        CustomerCreateDTO updateDTO = new CustomerCreateDTO();
        updateDTO.setFirstName("New"); updateDTO.setLastName("Name"); updateDTO.setEmail("new@test.com"); updateDTO.setPhone("999");

        CustomerResponseDTO updated = customerService.update(created.getId(), updateDTO);

        assertEquals("New", updated.getFirstName());
        assertEquals("new@test.com", updated.getEmail());
    }

    @Test
    void delete_shouldRemoveCustomer() {
        CustomerCreateDTO dto = new CustomerCreateDTO();
        dto.setFirstName("ToDelete"); dto.setLastName("X"); dto.setEmail("delete@test.com"); dto.setPhone("000");
        CustomerResponseDTO created = customerService.create(dto);

        customerService.delete(created.getId());

        assertThrows(CustomerException.class, () -> customerService.findById(created.getId()));
    }
}