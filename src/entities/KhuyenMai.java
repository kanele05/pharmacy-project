package entities;

import java.time.LocalDate;

public class KhuyenMai {
    private String maKM;
    private String tenCT;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private double phanTramGiamGia;
    private int dieuKienApDung;
    private boolean trangThai;
    public KhuyenMai(String maKM, String tenCT, LocalDate tuNgay, LocalDate denNgay, double phanTramGiamGia, int dieuKienApDung,boolean trangThai) {
        this.maKM = maKM;
        setMaKM(maKM);
        this.tenCT = tenCT;
        setTenCT(tenCT);
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.phanTramGiamGia = phanTramGiamGia;
        setPhanTramGiamGia(phanTramGiamGia);
        this.dieuKienApDung = dieuKienApDung;
        this.trangThai=trangThai;
    }
    public KhuyenMai(String maKM, String tenCT, LocalDate tuNgay, LocalDate denNgay, double phanTramGiamGia, int dieuKienApDung) {
        this.maKM = maKM;
        setMaKM(maKM);
        this.tenCT = tenCT;
        setTenCT(tenCT);
        this.tuNgay = tuNgay;
        this.denNgay = denNgay;
        this.phanTramGiamGia = phanTramGiamGia;
        setPhanTramGiamGia(phanTramGiamGia);
        this.dieuKienApDung = dieuKienApDung;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public KhuyenMai() {
    }
    public KhuyenMai(KhuyenMai km) {
        this.maKM = km.maKM;
        this.tenCT = km.tenCT;
        this.tuNgay = km.tuNgay;
        this.denNgay = km.denNgay;
        this.phanTramGiamGia = km.phanTramGiamGia;
        this.dieuKienApDung = km.dieuKienApDung;
    }
    public String getMaKM() {
        return maKM;
    }

    public String getTenCT() {
        return tenCT;
    }

    public LocalDate getTuNgay() {
        return tuNgay;
    }

    public LocalDate getDenNgay() {
        return denNgay;
    }

    public double getPhanTramGiamGia() {
        return phanTramGiamGia;
    }

    public int getDieuKienApDung() {
        return dieuKienApDung;
    }

    public void setMaKM(String maKM) {
        if (maKM.isEmpty() || maKM.isBlank()){
            throw new IllegalArgumentException("Mã Khuyến Mãi không được trống");
        }
        this.maKM = maKM;
    }

    public void setTenCT(String tenCT) {
        if (tenCT.isEmpty() || tenCT.isBlank()){
            throw new IllegalArgumentException("Tên Chương  không được trống");
        }
        this.tenCT = tenCT;
    }

    public void setTuNgay(LocalDate tuNgay) {

        this.tuNgay = tuNgay;
    }

    public void setDenNgay(LocalDate denNgay) {
        this.denNgay = denNgay;
    }

    public void setPhanTramGiamGia(double phanTramGiamGia) {
        if (phanTramGiamGia < 0 || phanTramGiamGia >100){
            throw new IllegalArgumentException("Phần trăm giảm từ 0-100%");
        }
        this.phanTramGiamGia = phanTramGiamGia;
    }

    public void setDieuKienApDung(int dieuKienApDung) {
        this.dieuKienApDung = dieuKienApDung;
    }

    @Override
    public String toString() {
        return "KhuyenMai{" +
                "maKM='" + maKM + '\'' +
                ", tenCT='" + tenCT + '\'' +
                ", tuNgay=" + tuNgay +
                ", denNgay=" + denNgay +
                ", phanTramGiamGia=" + phanTramGiamGia +
                ", dieuKienApDung=" + dieuKienApDung +
                '}';
    }
}
