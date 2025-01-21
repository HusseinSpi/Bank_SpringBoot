package com.example.bank.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AdminRole getRole() {
        return role;
    }

    public void setRole(AdminRole role) {
        this.role = role;
    }

    public enum AdminRole {
        SUPER_ADMIN,
        ACCOUNT_ADMIN,
        LOAN_ADMIN,
        FOREX_ADMIN
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private AdminRole role; // e.g. SUPER_ADMIN, ACCOUNT_ADMIN, LOAN_ADMIN, FOREX_ADMIN

    public Admin() {
    }

}
