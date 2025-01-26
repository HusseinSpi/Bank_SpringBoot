package com.example.bank.controller;

import com.example.bank.entity.Customer;
import com.example.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * يتحكم في عمليات CRUD على كائنات Customer (العميل).
 */
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    /**
     * الحصول على عميل عبر معرّفه (ID).
     */
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    /**
     * الحصول على جميع العملاء.
     */
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    /**
     * تعديل عميل موجود عبر ID.
     */
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        // لا نستطيع استخدام setCustomerId(id). بدلاً من ذلك نمرر المعرف للـ Service.
        return customerService.updateCustomer(id, updatedCustomer);
    }

    /**
     * حذف عميل عبر ID.
     */
    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
