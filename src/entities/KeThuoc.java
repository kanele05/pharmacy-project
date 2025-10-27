package entities;

import java.util.Objects;

public class KeThuoc {
    private String maKe;
    private String viTri;
    private String loaiKe;
    private int sucChua;

    public KeThuoc(String maKe) {
        this.maKe = maKe;
    }

    public KeThuoc(String maKe, String viTri, String loaiKe, int sucChua) {
        this.maKe = maKe;
        setMaKe(maKe);
        this.viTri = viTri;
        setViTri(viTri);
        this.loaiKe = loaiKe;
        this.sucChua = sucChua;
    }

    public KeThuoc(KeThuoc kt) {
        this.maKe = kt.maKe;
        this.viTri = kt.viTri;
        this.loaiKe = kt.loaiKe;
        this.sucChua = kt.sucChua;
    }

    public String getMaKe() {
        return maKe;
    }

    public String getViTri() {
        return viTri;
    }

    public String getLoaiKe() {
        return loaiKe;
    }

    public int getSucChua() {
        return sucChua;
    }

    public void setMaKe(String maKe) {
        if (!maKe.matches("^KE\\d{3}$"))
        {
            throw new IllegalArgumentException("Mã kệ bắt đầu bằng KE theo sau 3 sô vd: KE001");
        }
            this.maKe = maKe;
    }

    public void setViTri(String viTri) {
        if (viTri.isEmpty() || viTri.isBlank() || viTri.length() > 100){
            throw new IllegalArgumentException("Vị trí không hợp lệ");
        }
        this.viTri = viTri;
    }

    public void setLoaiKe(String loaiKe) {
        this.loaiKe = loaiKe;
    }

    public void setSucChua(int sucChua) {
        this.sucChua = sucChua;
    }

    @Override
    public String toString() {
        return "KeThuoc{" +
                "maKe='" + maKe + '\'' +
                ", viTri='" + viTri + '\'' +
                ", loaiKe='" + loaiKe + '\'' +
                ", sucChua='" + sucChua + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeThuoc keThuoc = (KeThuoc) o;
        return Objects.equals(maKe, keThuoc.maKe);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(maKe);
    }
}
