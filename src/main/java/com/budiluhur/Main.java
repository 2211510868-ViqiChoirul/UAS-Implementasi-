package com.budiluhur;

import com.budiluhur.dao.IStudentDAO;
import com.budiluhur.dao.StudentDAO;
import com.budiluhur.ui.MainFrame;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IStudentDAO dao = new StudentDAO();
            MainFrame frame = new MainFrame(dao);
            frame.setVisible(true);
        });
    }
}
