package com.budiluhur.ui;

import com.budiluhur.dao.IStudentDAO;
import com.budiluhur.model.Student;
import com.budiluhur.report.ReportGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {
    private final IStudentDAO studentDAO;
    private final DefaultTableModel tableModel;
    private final JTable table;

    private final JTextField idField;
    private final JTextField nameField;
    private final JTextField jurusanField;
    private final JTextField alamatField;
    private final JTextField emailField;

    public MainFrame(IStudentDAO dao) {
        super("Student Master - UAS");
        this.studentDAO = dao;

        setLayout(new BorderLayout());

        // ==== INPUT PANEL ====
        JPanel input = new JPanel(new GridLayout(2, 5, 5, 5));
        idField = new JTextField();
        nameField = new JTextField();
        jurusanField = new JTextField();
        alamatField = new JTextField();
        emailField = new JTextField();

        input.add(new JLabel("ID (leave blank for add)"));
        input.add(new JLabel("Name"));
        input.add(new JLabel("Jurusan"));
        input.add(new JLabel("Alamat"));
        input.add(new JLabel("Email"));

        input.add(idField);
        input.add(nameField);
        input.add(jurusanField);
        input.add(alamatField);
        input.add(emailField);
        add(input, BorderLayout.NORTH);

        // ==== TABLE ====
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Jurusan", "Alamat", "Email"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // ==== BUTTON PANEL ====
        JPanel buttons = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton deleteBtn = new JButton("Delete");
        JButton swapBtn = new JButton("Swap Emails");
        JButton reportBtn = new JButton("Generate Report (PDF)");

        buttons.add(addBtn);
        buttons.add(updateBtn);
        buttons.add(deleteBtn);
        buttons.add(swapBtn);
        buttons.add(reportBtn);
        add(buttons, BorderLayout.SOUTH);

        // ==== BUTTON ACTIONS ====
        addBtn.addActionListener(e -> {
            Student s = new Student(0, nameField.getText(), jurusanField.getText(), alamatField.getText(), emailField.getText());
            if (studentDAO.addStudent(s)) {
                JOptionPane.showMessageDialog(this, "Student added successfully.");
                clearFields();
                loadTable();
            }
        });

        updateBtn.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                Student s = new Student(id, nameField.getText(), jurusanField.getText(), alamatField.getText(), emailField.getText());
                if (studentDAO.updateStudent(s)) {
                    JOptionPane.showMessageDialog(this, "Student updated successfully.");
                    clearFields();
                    loadTable();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid ID to update.");
            }
        });

        deleteBtn.addActionListener(e -> {
            int sel = table.getSelectedRow();
            if (sel == -1) {
                JOptionPane.showMessageDialog(this, "Select a row first.");
                return;
            }
            int id = (int) tableModel.getValueAt(sel, 0);
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure to delete?");
            if (choice == JOptionPane.YES_OPTION) {
                if (studentDAO.deleteStudent(id)) {
                    JOptionPane.showMessageDialog(this, "Deleted successfully.");
                    loadTable();
                }
            }
        });

        swapBtn.addActionListener(e -> {
            String a = JOptionPane.showInputDialog(this, "Enter ID A:");
            String b = JOptionPane.showInputDialog(this, "Enter ID B:");
            try {
                int idA = Integer.parseInt(a);
                int idB = Integer.parseInt(b);
                if (studentDAO.swapStudentEmails(idA, idB)) {
                    JOptionPane.showMessageDialog(this, "Emails swapped successfully.");
                    loadTable();
                } else {
                    JOptionPane.showMessageDialog(this, "Swap failed.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid IDs.");
            }
        });

        // ==== REPORT BUTTON (UPDATE INI) ====
        reportBtn.addActionListener(e -> {
            try {
                ReportGenerator rg = new ReportGenerator();
                rg.generateStudentListPdf(1, 9999, "reports/StudentList.pdf");

                JOptionPane.showMessageDialog(this, "Report generated successfully: reports/StudentList.pdf");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error generating report: " + ex.getMessage());
            }
        });

        // ==== TABLE SELECTION ====
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int sel = table.getSelectedRow();
                if (sel != -1) {
                    idField.setText(tableModel.getValueAt(sel, 0).toString());
                    nameField.setText(tableModel.getValueAt(sel, 1).toString());
                    jurusanField.setText(tableModel.getValueAt(sel, 2).toString());
                    alamatField.setText(tableModel.getValueAt(sel, 3).toString());
                    emailField.setText(tableModel.getValueAt(sel, 4).toString());
                }
            }
        });

        // ==== FINAL SETTINGS ====
        loadTable();
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        jurusanField.setText("");
        alamatField.setText("");
        emailField.setText("");
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<Student> students = studentDAO.getAllStudents();
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getStudentId(),
                    s.getName(),
                    s.getJurusan(),
                    s.getAlamat(),
                    s.getEmail()
            });
        }
    }
}
