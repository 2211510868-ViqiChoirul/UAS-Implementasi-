package com.budiluhur.model;

public class Student {
    private int studentId;
    private String name;
    private String jurusan;
    private String alamat;
    private String email;

    public Student() {}

    public Student(int studentId, String name, String jurusan, String alamat, String email) {
        this.studentId = studentId;
        this.name = name;
        this.jurusan = jurusan;
        this.alamat = alamat;
        this.email = email;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getJurusan() { return jurusan; }
    public void setJurusan(String jurusan) { this.jurusan = jurusan; }

    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return studentId + " - " + name + " (" + jurusan + ")";
    }
}