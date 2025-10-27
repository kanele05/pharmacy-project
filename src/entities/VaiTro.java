package entities;

public enum VaiTro {
    ADMIN("Admin"),
    STAFF("Staff");

    private final String vaiTro;

    VaiTro(String vaiTro){
        this.vaiTro = vaiTro;
    }
    public String getVaiTro(){
        return vaiTro;
    }
}
