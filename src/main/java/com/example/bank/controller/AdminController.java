package com.example.bank.controller;

import com.example.bank.entity.Admin;
import com.example.bank.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * يتحكم في عمليات CRUD على كائنات Admin (المسؤول).
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * إنشاء Admin جديد.
     */
    @PostMapping
    public Admin createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    /**
     * الحصول على Admin عبر معرّفه (ID).
     */
    @GetMapping("/{id}")
    public Admin getAdmin(@PathVariable Long id) {
        return adminService.getAdminById(id);
    }

    /**
     * الحصول على جميع الـ Admins.
     */
    @GetMapping
    public List<Admin> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    /**
     * تعديل Admin موجود عبر ID.
     */
    @PutMapping("/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        // لا نستطيع استخدام setAdminId(id). بدلاً من ذلك نمرر المعرف للـ Service.
        return adminService.updateAdmin(id, updatedAdmin);
    }

    /**
     * حذف Admin عبر ID.
     */
    @DeleteMapping("/{id}")
    public void deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
    }
}
