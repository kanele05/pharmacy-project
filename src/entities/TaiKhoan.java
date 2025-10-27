package entities;

public class TaiKhoan {
    private String maTaiKhoan;
    private String tenDangNhap;
    private String matKhau;
    private String vaiTro;
    private String trangThai;

    public TaiKhoan(String maTaiKhoan, String tenDangNhap, String matKhau, String vaiTro, String trangThai) {
        this.maTaiKhoan = maTaiKhoan;
        this.tenDangNhap = tenDangNhap;
        this.matKhau = matKhau;
        this.vaiTro = vaiTro;
        this.trangThai = trangThai;
    }

    public TaiKhoan(TaiKhoan tk) {
        this.maTaiKhoan = tk.maTaiKhoan;
        this.tenDangNhap = tk.tenDangNhap;
        this.matKhau = tk.matKhau;
        this.vaiTro = tk.vaiTro;
        this.trangThai = tk.trangThai;
    }

    public TaiKhoan() {
    }

    public TaiKhoan(String maTaiKhoan) {
        this.maTaiKhoan = maTaiKhoan;
    }
    public String getMaTaiKhoan() {
        return maTaiKhoan;
    }

    public String getTenDangNhap() {
        return tenDangNhap;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setMaTaiKhoan(String maTaiKhoan) {
        if (!maTaiKhoan.matches("^TK\\d{3}")){
            throw new IllegalArgumentException("Mã Tài Khoản bắt đầu bằng Tk và theo sau 3 số vd: Tk001");
        }
        this.maTaiKhoan = maTaiKhoan;
    }

    public void setTenDangNhap(String tenDangNhap) {
        if (tenDangNhap.isEmpty() || tenDangNhap.length() < 4 || tenDangNhap.length() >30){
            throw new IllegalArgumentException("Tên Đăng Nhập không được rỗng và từ 4-30 ký tự");
        }
        this.tenDangNhap = tenDangNhap;
    }

    public void setMatKhau(String matKhau) {
        if (!matKhau.matches("^(?=.[A-Za-z])(?=.*\\d).{8,}$")){
            throw new IllegalArgumentException("Mật Khẩu bao gồm số và chữ, độ dài >=8");
        }
        this.matKhau = matKhau;
    }

    public void setVaiTro(String vaiTro) {
        this.vaiTro = vaiTro;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "TaiKhoan{" +
                "maTaiKhoan='" + maTaiKhoan + '\'' +
                ", tenDangNhap='" + tenDangNhap + '\'' +
                ", matKhau='" + matKhau + '\'' +
                ", vaiTro='" + vaiTro + '\'' +
                ", trangThai='" + trangThai + '\'' +
                '}';
    }
}
