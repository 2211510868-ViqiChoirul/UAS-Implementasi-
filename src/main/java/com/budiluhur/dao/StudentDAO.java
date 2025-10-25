package com.budiluhur.dao;

import com.budiluhur.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {

    @Override
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students ORDER BY student_id";
        try (Connection conn = DatabaseConnector.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("jurusan"),
                    rs.getString("alamat"),
                    rs.getString("email")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean addStudent(Student s) {
        String sql = "INSERT INTO students(name, jurusan, alamat, email) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getJurusan());
            pst.setString(3, s.getAlamat());
            pst.setString(4, s.getEmail());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateStudent(Student s) {
        String sql = "UPDATE students SET name = ?, jurusan = ?, alamat = ?, email = ? WHERE student_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, s.getName());
            pst.setString(2, s.getJurusan());
            pst.setString(3, s.getAlamat());
            pst.setString(4, s.getEmail());
            pst.setInt(5, s.getStudentId());
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, studentId);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean swapStudentEmails(int idA, int idB) {
        String getEmail = "SELECT email FROM students WHERE student_id = ?";
        String updateEmail = "UPDATE students SET email = ? WHERE student_id = ?";
        Connection conn = null;
        try {
            conn = DatabaseConnector.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement pstGet = conn.prepareStatement(getEmail)) {
                pstGet.setInt(1, idA);
                ResultSet rsA = pstGet.executeQuery();
                String emailA = rsA.next() ? rsA.getString("email") : null;

                pstGet.setInt(1, idB);
                ResultSet rsB = pstGet.executeQuery();
                String emailB = rsB.next() ? rsB.getString("email") : null;

                if (emailA == null || emailB == null) {
                    throw new SQLException("One or both students not found.");
                }

                try (PreparedStatement pstUp = conn.prepareStatement(updateEmail)) {
                    pstUp.setString(1, emailB);
                    pstUp.setInt(2, idA);
                    pstUp.executeUpdate();

                    pstUp.setString(1, emailA);
                    pstUp.setInt(2, idB);
                    pstUp.executeUpdate();
                }
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Transaction failed, rolling back.");
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
        }
    }
}