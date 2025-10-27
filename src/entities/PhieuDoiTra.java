package entities;

import java.time.LocalDate;

    public class PhieuDoiTra {
        private String maDoiTra;
        private LocalDate thoiDiem;
        private String lyDo;
        private String loaiDoiTra;
        private NhanVien nhanVien;

        public PhieuDoiTra(String maDoiTra, LocalDate thoiDiem, String lyDo, String loaiDoiTra, NhanVien nhanVien) {
            this.maDoiTra = maDoiTra;
            this.thoiDiem = thoiDiem;
            this.lyDo = lyDo;
            this.loaiDoiTra = loaiDoiTra;
            this.nhanVien = nhanVien;
        }

        public PhieuDoiTra() {
        }
        public PhieuDoiTra(entities.PhieuDoiTra dt) {
            this.maDoiTra = dt.maDoiTra;
            this.thoiDiem = dt.thoiDiem;
            this.lyDo = dt.lyDo;
            this.loaiDoiTra = dt.loaiDoiTra;
            this.nhanVien = dt.nhanVien;
        }

        public String getMaDoiTra() {
            return maDoiTra;
        }

        public void setMaDoiTra(String maDoiTra) {
            this.maDoiTra = maDoiTra;
        }

        public LocalDate getThoiDiem() {
            return thoiDiem;
        }

        public void setThoiDiem(LocalDate thoiDiem) {
            this.thoiDiem = thoiDiem;
        }

        public String getLyDo() {
            return lyDo;
        }

        public void setLyDo(String lyDo) {
            this.lyDo = lyDo;
        }

        public String getLoaiDoiTra() {
            return loaiDoiTra;
        }

        public void setLoaiDoiTra(String loaiDoiTra) {
            this.loaiDoiTra = loaiDoiTra;
        }

        public NhanVien getNhanVien() {
            return nhanVien;
        }

        public void setNhanVien(NhanVien nhanVien) {
            this.nhanVien = nhanVien;
        }

        @Override
        public String toString() {
            return "PhieuDoiTra{" +
                    "maDoiTra='" + maDoiTra + '\'' +
                    ", thoiDiem=" + thoiDiem +
                    ", lyDo='" + lyDo + '\'' +
                    ", loaiDoiTra='" + loaiDoiTra + '\'' +
                    ", nhanVien=" + nhanVien +
                    '}';
        }
    }
