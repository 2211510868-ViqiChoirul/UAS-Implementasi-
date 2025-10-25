-- SQLite schema for students table
CREATE TABLE IF NOT EXISTS students (
  student_id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT,
  jurusan TEXT,
  alamat TEXT,
  email TEXT
);

-- Sample data
INSERT INTO students(name, jurusan, alamat, email) VALUES
('Andi Rahman', 'Teknik Informatika', 'Jl. Mawar 1', 'andi@mail.com'),
('Budi Santoso', 'Sistem Informasi', 'Jl. Melati 2', 'budi@mail.com'),
('Citra Dewi', 'Teknik Informatika', 'Jl. Kenanga 3', 'citra@mail.com'),
('Dewi Lestari', 'Sistem Informasi', 'Jl. Anggrek 4', 'dewi@mail.com'),
('Eko Prasetyo', 'Teknik Komputer', 'Jl. Sakura 5', 'eko@mail.com');