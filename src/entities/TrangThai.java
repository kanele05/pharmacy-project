package entities;

public enum TrangThai {
    Dang_Ban("Đang Bán"),
    Ngung_Ban("Ngừng Bán");

    private final String mota;

    TrangThai(String mota){
        this.mota = mota;
    }
    public String getMota(){
        return mota;
    }

}
