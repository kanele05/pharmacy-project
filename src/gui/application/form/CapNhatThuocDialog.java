package gui.application.form;

import dao.KeThuocDAO;
import dao.NhanVienDAO;
import dao.NhomThuocDAO;
import dao.ThuocDAO;
import entities.KeThuoc;
import entities.NhanVien;
import entities.NhomThuoc;
import entities.Thuoc;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class CapNhatThuocDialog extends JDialog {

    // Fields
    private final JTextField txtMaThuoc = new JTextField();
    private final JTextField txtTenThuoc = new JTextField();
    private final JTextField txtHamLuong = new JTextField();
    private final JComboBox<String> cbxDangThuoc = new JComboBox<>(new String[]{"", "Viên nén","Viên nang","Siro","Dung dịch","Thuốc bôi"});
    private final JComboBox<String> cbxDonViTinh = new JComboBox<>(new String[]{"", "viên","chai","ống","tuýp","hộp"});
    private final JTextField txtGiaTien = new JTextField();
    private final JTextField txtNhaSanXuat = new JTextField();

    // Combos từ DAO
    private final JComboBox<KeThuoc> cbxKe = new JComboBox<>();
    private final JComboBox<NhomThuoc> cbxNhom = new JComboBox<>();
    private final JComboBox<NhanVien> cbxNhanVien = new JComboBox<>();

    // Ảnh
    private final JLabel imgPreview = new JLabel();
    private String imgPath;   // đường dẫn mới (nếu người dùng đổi)
    private String originalImgPath; // để so sánh hoặc giữ nguyên

    private final ThuocDAO thuocDAO = new ThuocDAO();
    private final KeThuocDAO keDAO = new KeThuocDAO();
    private final NhomThuocDAO nhomDAO = new NhomThuocDAO();
    private final NhanVienDAO nvDAO = new NhanVienDAO();

    private Thuoc current;     // Thuốc đang chỉnh sửa
    private boolean updated;   // để biết sau khi đóng dialog có cập nhật thành công không

    public CapNhatThuocDialog(Window owner, String maThuoc) {
        super(owner, "Cập nhật thuốc", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(720, 520);
        setLocationRelativeTo(owner);

        // UI
        setContentPane(buildUI());
        // load data
        loadCombos();
        loadThuoc(maThuoc);
    }

    public boolean isUpdated() {
        return updated;
    }

    private JPanel buildUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(10,10,10,10));

        JLabel title = new JLabel("Cập nhật thuốc", JLabel.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        root.add(title, BorderLayout.NORTH);

        // Left: ảnh + chọn ảnh
        JPanel left = new JPanel(new BorderLayout(0,8));
        left.setBorder(new EmptyBorder(10,0,0,12));
        JPanel imgBox = new JPanel(new BorderLayout());
        imgBox.setPreferredSize(new Dimension(220, 240));
        imgBox.setBackground(new Color(0xEEEEEE));
        imgPreview.setHorizontalAlignment(SwingConstants.CENTER);
        imgBox.add(imgPreview, BorderLayout.CENTER);

        JButton btnChonAnh = new JButton("Chọn ảnh...");
        btnChonAnh.addActionListener(this::onChonAnh);

        left.add(imgBox, BorderLayout.CENTER);
        left.add(btnChonAnh, BorderLayout.SOUTH);

        // Right: form
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        int r = 0;
        txtMaThuoc.setEditable(false);
        addRow(form, gc, r++, "Mã thuốc", txtMaThuoc);
        addRow(form, gc, r++, "Tên thuốc", txtTenThuoc);

        JPanel rowHamDangDvt = new JPanel(new GridLayout(1,3,8,0));
        rowHamDangDvt.add(labeled(txtHamLuong, "Hàm lượng"));
        rowHamDangDvt.add(labeled(cbxDangThuoc, "Dạng thuốc"));
        rowHamDangDvt.add(labeled(cbxDonViTinh, "Đơn vị tính"));
        addRow(form, gc, r++, rowHamDangDvt);

        addRow(form, gc, r++, "Nhà sản xuất", txtNhaSanXuat);
        addRow(form, gc, r++, "Giá tiền", txtGiaTien);

        JPanel row3Combo = new JPanel(new GridLayout(1,3,8,0));
        row3Combo.add(labeled(cbxKe, "Kệ"));
        row3Combo.add(labeled(cbxNhom, "Nhóm"));
        row3Combo.add(labeled(cbxNhanVien, "Nhân viên"));
        addRow(form, gc, r++, row3Combo);

        JPanel right = new JPanel(new BorderLayout());
        right.add(form, BorderLayout.CENTER);

        // Buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnCancel = new JButton("Hủy");
        JButton btnSave = new JButton("Lưu thay đổi");
        btnCancel.addActionListener(e -> dispose());
        btnSave.addActionListener(this::onSave);
        actions.add(btnCancel);
        actions.add(btnSave);
        right.add(actions, BorderLayout.SOUTH);

        JPanel center = new JPanel(new BorderLayout());
        center.add(left, BorderLayout.WEST);
        center.add(right, BorderLayout.CENTER);

        root.add(center, BorderLayout.CENTER);
        return root;
    }

    private void addRow(JPanel panel, GridBagConstraints gc, int row, String label, JComponent comp) {
        gc.gridx=0; gc.gridy=row; gc.gridwidth=1; gc.weightx=0;
        panel.add(new JLabel(label), gc);
        gc.gridx=1; gc.gridwidth=2; gc.weightx=1;
        panel.add(comp, gc);
    }
    private void addRow(JPanel panel, GridBagConstraints gc, int row, JComponent spanning) {
        gc.gridx=0; gc.gridy=row; gc.gridwidth=3; gc.weightx=1;
        panel.add(spanning, gc);
    }
    private JComponent labeled(JComponent c, String t) {
        JPanel p = new JPanel(new BorderLayout(0,4));
        p.add(new JLabel(t), BorderLayout.NORTH);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    // ==== Load combo từ DAO ====
    private void loadCombos() {
        // Kệ
        cbxKe.removeAllItems();
        ArrayList<KeThuoc> dsKe = keDAO.getAllTblKeThuoc();
        for (KeThuoc k : dsKe) cbxKe.addItem(k);
        cbxKe.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof KeThuoc k) setText(safe(k.getMaKe()) + " - " + safe(k.getViTri()));
                return this;
            }
        });

        // Nhóm
        cbxNhom.removeAllItems();
        ArrayList<NhomThuoc> dsNhom = nhomDAO.getAllTblNhomThuoc();
        for (NhomThuoc n : dsNhom) cbxNhom.addItem(n);
        cbxNhom.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NhomThuoc n) setText(safe(n.getMaNhom()) + " - " + safe(n.getTenNhom()));
                return this;
            }
        });

        // Nhân viên
        cbxNhanVien.removeAllItems();
        ArrayList<NhanVien> dsNV = nvDAO.getAllTblNhanVien();
        for (NhanVien nv : dsNV) cbxNhanVien.addItem(nv);
        cbxNhanVien.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NhanVien nv) setText(safe(nv.getMaNhanVien()) + " - " + safe(nv.getTenNhanVien()));
                return this;
            }
        });
    }

    // ==== Load 1 thuốc theo mã, đổ lên form ====
    private void loadThuoc(String maThuoc) {
        current = thuocDAO.findById(maThuoc);      // Bạn thêm hàm này trong DAO (mẫu bên dưới)
        if (current == null) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy thuốc mã: " + maThuoc, "Lỗi", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        txtMaThuoc.setText(safe(current.getMaThuoc()));
        txtTenThuoc.setText(safe(current.getTenThuoc()));
        txtHamLuong.setText(safe(current.getHamLuong()));
        cbxDangThuoc.setSelectedItem(safe(current.getDangThuoc()));
        cbxDonViTinh.setSelectedItem(safe(current.getDonViTinh()));
        txtNhaSanXuat.setText(safe(current.getNhaSanXuat()));

        // giá
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("vi","VN"));
        txtGiaTien.setText(nf.format(current.getGiaThuoc()));

        // combos tham chiếu
        selectComboById(cbxKe, current.getKeThuoc() != null ? current.getKeThuoc().getMaKe() : null);
        selectComboById(cbxNhom, current.getNhomThuoc() != null ? current.getNhomThuoc().getMaNhom() : null);
        selectComboById(cbxNhanVien, current.getNhanVien() != null ? current.getNhanVien().getMaNhanVien() : null);

        // ảnh
        originalImgPath = current.getAnhDaiDien();
        imgPath = originalImgPath;
        showPreview(imgPath);
    }

    private <T> void selectComboById(JComboBox<T> combo, String id) {
        if (id == null) {
            combo.setSelectedIndex(-1);
            return;
        }
        for (int i = 0; i < combo.getItemCount(); i++) {
            T it = combo.getItemAt(i);
            String itemId = null;
            if (it instanceof KeThuoc k) itemId = k.getMaKe();
            else if (it instanceof NhomThuoc n) itemId = n.getMaNhom();
            else if (it instanceof NhanVien nv) itemId = nv.getMaNhanVien();

            if (Objects.equals(id, itemId)) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        combo.setSelectedIndex(-1);
    }

    // ==== Chọn ảnh ====
    private void onChonAnh(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Ảnh (JPG, JPEG, PNG)", "jpg", "jpeg", "png"));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            imgPath = f.getAbsolutePath();
            showPreview(imgPath);
        }
    }
    private void showPreview(String path) {
        try {
            if (path == null || path.isBlank()) {
                imgPreview.setIcon(null);
                return;
            }
            BufferedImage img = ImageIO.read(new File(path));
            if (img == null) {
                imgPreview.setIcon(null);
                return;
            }
            int boxW = 220, boxH = 240;
            int w = img.getWidth(), h = img.getHeight();
            double scale = Math.min((double) boxW / w, (double) boxH / h);
            Image scaled = img.getScaledInstance((int)(w*scale), (int)(h*scale), Image.SCALE_SMOOTH);
            imgPreview.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            imgPreview.setIcon(null);
        }
    }

    // ==== Lưu ====
    private void onSave(ActionEvent e) {
        try {
            if (current == null) throw new IllegalStateException("Thuốc hiện tại không tồn tại");
            String ten = must(txtTenThuoc.getText(), "Tên thuốc");
            String ham = must(txtHamLuong.getText(), "Hàm lượng");
            String dang = must((String) cbxDangThuoc.getSelectedItem(), "Dạng thuốc");
            String dvt  = must((String) cbxDonViTinh.getSelectedItem(), "Đơn vị tính");
            String nsx  = must(txtNhaSanXuat.getText(), "Nhà sản xuất");
            String giaStr = must(txtGiaTien.getText(), "Giá tiền");

            double gia;
            try {
                Number n = NumberFormat.getNumberInstance(new Locale("vi","VN")).parse(giaStr);
                gia = n.doubleValue();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Giá tiền không hợp lệ");
            }

            KeThuoc ke = (KeThuoc) cbxKe.getSelectedItem();
            NhomThuoc nhom = (NhomThuoc) cbxNhom.getSelectedItem();
            NhanVien nv = (NhanVien) cbxNhanVien.getSelectedItem();
            if (ke == null)   throw new IllegalArgumentException("Vui lòng chọn Kệ");
            if (nhom == null) throw new IllegalArgumentException("Vui lòng chọn Nhóm");
            if (nv == null)   throw new IllegalArgumentException("Vui lòng chọn Nhân viên");

            // Không cho ảnh null nếu DB not-null
            if (imgPath == null || imgPath.isBlank()) {
                imgPath = (originalImgPath != null) ? originalImgPath : "";  // hoặc gán ảnh mặc định
            }

            // set lại vào current
            current.setTenThuoc(ten);
            current.setHamLuong(ham);
            current.setDangThuoc(dang);
            current.setGiaThuoc(gia);
            current.setDonViTinh(dvt);
            current.setNhaSanXuat(nsx);
            current.setAnhDaiDien(imgPath);
            current.setKeThuoc(ke);
            current.setNhomThuoc(nhom);
            current.setNhanVien(nv);

            boolean ok = thuocDAO.capNhatThuoc(current);
            if (ok) {
                updated = true;
                JOptionPane.showMessageDialog(this, "Đã cập nhật thuốc: " + current.getMaThuoc());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String must(String v, String name) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException(name + " không được rỗng");
        return v.trim();
    }

    private String safe(String s) { return s == null ? "" : s; }
}
