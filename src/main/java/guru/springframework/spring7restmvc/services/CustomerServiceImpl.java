package guru.springframework.spring7restmvc.services;

import guru.springframework.spring7restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.accept.ApiVersionStrategy;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private final ApiVersionStrategy mvcApiVersionStrategy;
    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl(@Nullable ApiVersionStrategy mvcApiVersionStrategy) {
        this.customerMap = new HashMap<>();

        Customer customer1 = Customer.builder()
                .customerName("Jandier Rojas")
                .id(UUID.randomUUID())
                .version(1)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        Customer customer2 = Customer.builder()
                .customerName("Anna Scluu")
                .id(UUID.randomUUID())
                .version(1)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        Customer customer3 = Customer.builder()
                .customerName("Ling yi")
                .id(UUID.randomUUID())
                .version(1)
                .lastModifiedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
        this.mvcApiVersionStrategy = mvcApiVersionStrategy;
    }

    @Override
    public List<Customer> customerList() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById( UUID id) {
        log.debug("Get Customer Id = " + id);
        return customerMap.get(id);
    }

    @Override
    public Customer saveNewCustomer(@RequestBody Customer customer) {
        Customer savedCustomer = Customer.builder()
                .customerName(customer.getCustomerName())
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .version(1)
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }
}
