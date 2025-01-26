package com.example.bank.controller;

import com.example.bank.service.DatabaseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * يتحكم في وظيفة طباعة بيانات جداول قاعدة البيانات.
 */
@RestController
public class DatabaseController {

    private final DatabaseService databaseService;

    // Constructor injection for DatabaseService
    public DatabaseController(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * لاستدعاء طباعة بيانات جميع الجداول في الـ Console.
     */
    @GetMapping("/print-tables")
    public String printAllTablesData() {
        databaseService.printAllTablesData();
        return "Check the console for the table data!";
    }
}
