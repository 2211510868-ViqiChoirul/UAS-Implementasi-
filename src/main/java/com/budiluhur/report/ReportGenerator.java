package com.budiluhur.report;

import net.sf.jasperreports.engine.*;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportGenerator {

    public void generateStudentListPdf(int startId, int endId, String outputPath) {
        Connection conn = null;
        try {
            // Pastikan folder output ada
            File outDir = new File("reports");
            if (!outDir.exists()) {
                outDir.mkdirs();
            }

            // Path database (pastikan sama dengan file SQLite kamu)
            String dbPath = "src/main/resources/db/student.db";
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);

            // Query ambil data dari tabel students
            String sql = "SELECT student_id, name, jurusan, alamat, email " +
                         "FROM students WHERE student_id BETWEEN ? AND ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, startId);
            ps.setInt(2, endId);
            ResultSet rs = ps.executeQuery();

            // DataSource untuk JasperReport
            JRResultSetDataSource dataSource = new JRResultSetDataSource(rs);

            // Compile file .jrxml ke .jasper (otomatis)
            String reportPath = "src/main/resources/report/student_report.jrxml";
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath);

            // Isi report dengan data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Ekspor ke PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

            System.out.println("✅ Laporan berhasil dibuat: " + outputPath);

        } catch (Exception e) {
            System.err.println("❌ Gagal membuat laporan:");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (Exception ignore) {}
        }
    }
}
