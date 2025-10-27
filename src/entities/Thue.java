package entities;

import java.time.LocalDate;

public class Thue {
    private String maThue;
    private String tenThue;
    private LocalDate hieuLucTu;
    private LocalDate hetHieuLuc;

    public Thue(String maThue, String tenThue, LocalDate hieuLucTu, LocalDate hetHieuLuc) {
        this.maThue = maThue;
        this.tenThue = tenThue;
        this.hieuLucTu = hieuLucTu;
        this.hetHieuLuc = hetHieuLuc;
    }

    public Thue() {
    }
    public Thue(Thue other) {
        this.maThue = other.maThue;
        this.tenThue = other.tenThue;
        this.hieuLucTu = other.hieuLucTu;
        this.hetHieuLuc = other.hetHieuLuc;
    }
    public Thue (String maThue) {
        this.maThue = maThue;
    }
    public String getMaThue() {
        return maThue;
    }

    public String getTenThue() {
        return tenThue;
    }

    public LocalDate getHieuLucTu() {
        return hieuLucTu;
    }

    public LocalDate getHetHieuLuc() {
        return hetHieuLuc;
    }

    public void setMaThue(String maThue) {
        if (maThue.isBlank() ||maThue.isEmpty()){
            throw new IllegalArgumentException("Mã thuế không được để trống");

        }
        this.maThue = maThue;
    }

    public void setTenThue(String tenThue) {
        if (tenThue.isBlank() ||tenThue.isEmpty()){
            throw new IllegalArgumentException("Tên thuế không được để trống");
        }
        this.tenThue = tenThue;
    }

    public void setHieuLucTu(LocalDate hieuLucTu) {
        this.hieuLucTu = hieuLucTu;
    }

    public void setHetHieuLuc(LocalDate hetHieuLuc) {
        this.hetHieuLuc = hetHieuLuc;
    }

    @Override
    public String toString() {
        return "Thue{" +
                "maThue='" + maThue + '\'' +
                ", tenThue='" + tenThue + '\'' +
                ", hieuLucTu=" + hieuLucTu +
                ", hetHieuLuc=" + hetHieuLuc +
                '}';
    }
}
