package com.budiluhur.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:sqlite:src/main/resources/db/student.db";

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        ensureTableAndData(conn);
        return conn;
    }

    private static void ensureTableAndData(Connection conn) {
        try (Statement stmt = conn.createStatement()) {
            // Buat tabel jika belum ada
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS students (
                    student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT,
                    jurusan TEXT,
                    alamat TEXT,
                    email TEXT
                );
            """);

            // Cek apakah tabel sudah punya data
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM students");
            if (rs.next() && rs.getInt("count") == 0) {
                // Isi data contoh
                stmt.executeUpdate("""
                    INSERT INTO students (name, jurusan, alamat, email) VALUES
                    ('Viqi Choirul Fuad', 'Fakultas Teknologi Informasi', 'Jl. Petukangan', 'viqi@example.com'),
                    ('Rina Marlina', 'Fakultas Ekonomi', 'Jl. Budi Luhur 2', 'rina@example.com'),
                    ('Dewi Sartika', 'Fakultas Komunikasi', 'Jl. Kebayoran Lama', 'dewi@example.com'),
                    ('Andi Pratama', 'Fakultas Teknik', 'Jl. Ciledug Raya', 'andi@example.com'),
                    ('Budi Santoso', 'Fakultas Hukum', 'Jl. Pesanggrahan', 'budi@example.com');
                """);
                System.out.println("✅ Database initialized with sample data.");
            }
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
}
