# StudentMaster - Maven Version

**Author:** Viqi Choirul Fuad - 2211510868 - FTI Universitas Budi Luhur

## Cara Menjalankan
1. Pastikan sudah install Maven dan JDK 17.
2. Buka folder ini di NetBeans (pilih "Open Project").
3. Maven akan otomatis mengunduh dependency:
   - `jasperreports`
   - `sqlite-jdbc`
4. Jalankan kelas utama: `com.budiluhur.Main`.
5. Database ada di `src/main/resources/db/student.db`.
6. Report template Jasper ada di `src/main/resources/report/student_report.jrxml`.
7. Laporan PDF akan disimpan ke folder `reports/`.

## Catatan
- Folder `captures/` digunakan untuk screenshot setiap aksi CRUD.
- Folder `reports/` untuk hasil PDF.
- Struktur project ini mengikuti prinsip SOLID, transaksi database, dan integrasi JasperReports.