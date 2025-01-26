    package com.example.bank.service;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.Map;

    @Service
    public class DatabaseService {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        // Method to get all table names
        public List<String> getAllTableNames() {
            String query = "SHOW TABLES";  // Change this query if using another DB (e.g., PostgreSQL)
            return jdbcTemplate.queryForList(query, String.class);
        }

        // Method to get all data from a specific table
        public List<Map<String, Object>> getTableData(String tableName) {
            String query = "SELECT * FROM " + tableName;
            return jdbcTemplate.queryForList(query);
        }

        // Method to print all tables and their data
        public void printAllTablesData() {
            List<String> tableNames = getAllTableNames();

            // Loop through each table name and print its data
            for (String tableName : tableNames) {
                System.out.println("Table: " + tableName);
                List<Map<String, Object>> tableData = getTableData(tableName);
                for (Map<String, Object> row : tableData) {
                    System.out.println(row);
                }
                System.out.println();
            }
        }
    }
