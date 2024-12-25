package com.market.wingy.service;

import com.market.wingy.model.Customer;
import com.market.wingy.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Optional<Customer> getCustomerById(ObjectId id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Optional<Customer> getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if (customerRepository.existsByPhone(customer.getPhone())) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        customer.setJoined(LocalDateTime.now());
        customer.setLastActivity(LocalDateTime.now());
        return customerRepository.save(customer);
    }

    @Transactional
    public Optional<Customer> updateCustomer(ObjectId id, Customer customerDetails) {
        return customerRepository.findById(id)
                .map(existingCustomer -> {
                    existingCustomer.setFirstName(customerDetails.getFirstName());
                    existingCustomer.setLastName(customerDetails.getLastName());
                    existingCustomer.setBirthdate(customerDetails.getBirthdate());
                    existingCustomer.setGender(customerDetails.getGender());
                    existingCustomer.setDefaultDeliveryAddress(customerDetails.getDefaultDeliveryAddress());
                    existingCustomer.setCurrentLocation(customerDetails.getCurrentLocation());
                    existingCustomer.setLastActivity(LocalDateTime.now());
                    return customerRepository.save(existingCustomer);
                });
    }

    @Transactional
    public void deleteCustomer(ObjectId id) {
        customerRepository.deleteById(id);
    }

    @Transactional
    public void updateLastActivity(ObjectId id) {
        customerRepository.findById(id)
                .ifPresent(customer -> {
                    customer.setLastActivity(LocalDateTime.now());
                    customerRepository.save(customer);
                });
    }
}