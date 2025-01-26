package com.example.bank.service;

import com.example.bank.entity.Admin;
import java.util.List;

public interface AdminService {
    Admin createAdmin(Admin admin);
    Admin getAdminById(Long id);
    List<Admin> getAllAdmins();

    Admin updateAdmin(Long id, Admin updatedAdmin);

    void deleteAdmin(Long id);
}
