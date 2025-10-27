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

public class AddThuocForm extends JPanel {

    // Text fields
    private final JTextField txtMaThuoc = new JTextField();
    private final JTextField txtTenThuoc = new JTextField();
    private final JTextField txtHamLuong = new JTextField();
    private final JTextField txtGiaTien = new JTextField();
    private final JTextField txtNhaSanXuat = new JTextField();

    // Combos tĩnh
    private final JComboBox<String> cbxDangThuoc = new JComboBox<>(new String[]{
            "", "Viên nén", "Viên nang", "Siro", "Dung dịch", "Thuốc bôi"
    });
    private final JComboBox<String> cbxDonViTinh = new JComboBox<>(new String[]{
            "", "Viên", "Chai", "Ống", "Tuýp", "Hộp"
    });

    // Combos từ DAO
    private final JComboBox<KeThuoc>   cbxKe = new JComboBox<>();
    private final JComboBox<NhomThuoc> cbxNhom = new JComboBox<>();
    private final JComboBox<NhanVien>  cbxNhanVien = new JComboBox<>();

    // Ảnh
    private final JLabel imgPreview = new JLabel();


    private String imgPath = null;

    // DAO
    private final KeThuocDAO keDao = new KeThuocDAO();
    private final NhomThuocDAO nhomDao = new NhomThuocDAO();
    private final NhanVienDAO nvDao = new NhanVienDAO();
    private final ThuocDAO thuocDao = new ThuocDAO();

    public AddThuocForm() {
        setPreferredSize(new Dimension(806, 460));
        setLayout(new BorderLayout());
        add(buildContent(), BorderLayout.CENTER);
        loadComboFromDAO();
    }

    private JComponent buildContent() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Tiêu đề
        JLabel title = new JLabel("Thêm Thuốc", JLabel.LEFT);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        root.add(title, BorderLayout.NORTH);

        // Trái: Ảnh + nút chọn ảnh
        JPanel left = new JPanel(new BorderLayout(0, 8));
        left.setBorder(new EmptyBorder(10, 0, 0, 12));
        JPanel imgBox = new JPanel(new BorderLayout());
        imgBox.setPreferredSize(new Dimension(220, 240));
        imgBox.setBackground(new Color(0xEEEEEE));
        imgPreview.setOpaque(false);
        imgPreview.setHorizontalAlignment(SwingConstants.CENTER);
        imgBox.add(imgPreview, BorderLayout.CENTER);
        JButton btnChonAnh = new JButton("Chọn ảnh");
        btnChonAnh.addActionListener(this::onChonAnh);
        left.add(imgBox, BorderLayout.CENTER);
        left.add(btnChonAnh, BorderLayout.SOUTH);

        // Phải: Form fields
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        int r = 0;
        addRow(form, gc, r++, "Mã Thuốc", txtMaThuoc);
        addRow(form, gc, r++, "Tên Thuốc", txtTenThuoc);

        JPanel rowHamDangDonVi = new JPanel(new GridLayout(1, 3, 8, 0));
        rowHamDangDonVi.add(labeled(txtHamLuong, "Hàm Lượng"));
        rowHamDangDonVi.add(labeled(cbxDangThuoc, "Dạng Thuốc"));
        rowHamDangDonVi.add(labeled(cbxDonViTinh, "Đơn Vị Tính"));
        addRow(form, gc, r++, rowHamDangDonVi);

        addRow(form, gc, r++, "Nhà Sản Xuất", txtNhaSanXuat);
        addRow(form, gc, r++, "Giá Tiền", txtGiaTien);

        JPanel row3Combo = new JPanel(new GridLayout(1, 3, 8, 0));
        row3Combo.add(labeled(cbxKe, "Kệ"));
        row3Combo.add(labeled(cbxNhom, "Nhóm"));
        row3Combo.add(labeled(cbxNhanVien, "Nhân Viên"));
        addRow(form, gc, r++, row3Combo);

        // Buttons
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnLuu = new JButton("Lưu");
        JButton btnClear = new JButton("Làm mới");
        btnLuu.addActionListener(this::onLuu);
        btnClear.addActionListener(e -> clearForm());
        actions.add(btnClear);
        actions.add(btnLuu);

        JPanel right = new JPanel(new BorderLayout());
        right.add(form, BorderLayout.CENTER);
        right.add(actions, BorderLayout.SOUTH);

        // Layout chính
        JPanel center = new JPanel(new BorderLayout());
        center.add(left, BorderLayout.WEST);
        center.add(right, BorderLayout.CENTER);

        root.add(center, BorderLayout.CENTER);
        return root;
    }

    private void addRow(JPanel panel, GridBagConstraints gc, int row, String label, JComponent comp) {
        gc.gridx = 0; gc.gridy = row; gc.gridwidth = 1; gc.weightx = 0;
        panel.add(new JLabel(label), gc);
        gc.gridx = 1; gc.gridwidth = 2; gc.weightx = 1;
        panel.add(comp, gc);
    }
    private void addRow(JPanel panel, GridBagConstraints gc, int row, JComponent compSpanning) {
        gc.gridx = 0; gc.gridy = row; gc.gridwidth = 3; gc.weightx = 1;
        panel.add(compSpanning, gc);
    }
    private JComponent labeled(JComponent comp, String label) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.add(new JLabel(label), BorderLayout.NORTH);
        p.add(comp, BorderLayout.CENTER);
        return p;
    }

    // ==== DAO -> Combo ====
    private void loadComboFromDAO() {
        // Kệ
        cbxKe.removeAllItems();
        ArrayList<KeThuoc> dsKe = keDao.getAllTblKeThuoc();
        for (KeThuoc k : dsKe) cbxKe.addItem(k);
        cbxKe.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof KeThuoc) {
                    KeThuoc k = (KeThuoc) value;
                    // Hiển thị: Mã kệ - Vị trí (tuỳ biến theo entity của bạn)
                    setText(safe(() -> k.getMaKe()) + " - " + safe(() -> k.getViTri()));
                }
                return this;
            }
        });

        // Nhóm
        cbxNhom.removeAllItems();
        ArrayList<NhomThuoc> dsNhom = nhomDao.getAllTblNhomThuoc();
        for (NhomThuoc n : dsNhom) cbxNhom.addItem(n);
        cbxNhom.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NhomThuoc) {
                    NhomThuoc n = (NhomThuoc) value;
                    setText(safe(() -> n.getMaNhom()) + " - " + safe(() -> n.getTenNhom()));
                }
                return this;
            }
        });

        // Nhân viên
        cbxNhanVien.removeAllItems();
        ArrayList<NhanVien> dsNV = nvDao.getAllTblNhanVien();
        for (NhanVien nv : dsNV) cbxNhanVien.addItem(nv);
        cbxNhanVien.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NhanVien) {
                    NhanVien nv = (NhanVien) value;
                    setText(safe(() -> nv.getMaNhanVien()) + " - " + safe(() -> nv.getTenNhanVien()));
                }
                return this;
            }
        });
    }

    // ==== Chọn ảnh ====
    private void onChonAnh(ActionEvent e) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn ảnh đại diện thuốc");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Ảnh (JPG, PNG, JPEG)", "jpg", "jpeg", "png"
        ));
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            imgPath = f.getAbsolutePath();
            showPreview(imgPath);
        }
    }
    private void showPreview(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img == null) {
                JOptionPane.showMessageDialog(this, "Không đọc được ảnh.");
                return;
            }
            // scale preview
            int boxW = 220, boxH = 240;
            int w = img.getWidth(), h = img.getHeight();
            double scale = Math.min((double)boxW / w, (double)boxH / h);
            Image scaled = img.getScaledInstance((int)(w*scale), (int)(h*scale), Image.SCALE_SMOOTH);
            imgPreview.setIcon(new ImageIcon(scaled));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi hiển thị ảnh: " + ex.getMessage());
        }
    }

    // ==== Lưu ====
    private void onLuu(ActionEvent e) {
        try {
            // Validate cơ bản
            String ma = must(txtMaThuoc.getText(), "Mã thuốc");
            String ten = must(txtTenThuoc.getText(), "Tên thuốc");
            String ham = must(txtHamLuong.getText(), "Hàm lượng");
            String dang = must((String) cbxDangThuoc.getSelectedItem(), "Dạng thuốc");
            String dvt  = must((String) cbxDonViTinh.getSelectedItem(), "Đơn vị tính");
            String nsx  = must(txtNhaSanXuat.getText(), "Nhà sản xuất");
            String giaStr = must(txtGiaTien.getText(), "Giá tiền");

            double gia;
            try {
                // chấp nhận định dạng "12.345" theo VN
                Number n = NumberFormat.getNumberInstance(new Locale("vi", "VN")).parse(giaStr);
                gia = n.doubleValue();
            } catch (Exception ex) {
                throw new IllegalArgumentException("Giá tiền không hợp lệ");
            }

            KeThuoc ke = (KeThuoc) cbxKe.getSelectedItem();
            NhomThuoc nhom = (NhomThuoc) cbxNhom.getSelectedItem();
            NhanVien nv = (NhanVien) cbxNhanVien.getSelectedItem();
            if (ke == null)   throw new IllegalArgumentException("Vui lòng chọn Kệ");
            if (nhom == null) throw new IllegalArgumentException("Vui lòng chọn Nhóm");
            if (nv == null)   throw new IllegalArgumentException("Vui lòng chọn Nhân Viên");

            // Tạo đối tượng Thuoc
            Thuoc t = new Thuoc();
            // Dùng setter để an toàn (đổi theo entity của bạn nếu cần)
            // t.setTrangThai("Đang Kinh Doanh"); // nếu cần default
            t.setMaThuoc(ma);
            t.setTenThuoc(ten);
            t.setHamLuong(ham);
            t.setDangThuoc(dang);
            t.setGiaThuoc(gia);
            t.setDonViTinh(dvt);
            t.setNhaSanXuat(nsx);
            t.setAnhDaiDien(imgPath);
            t.setKeThuoc(ke);
            t.setNhomThuoc(nhom);
            t.setNhanVien(nv);

            boolean ok = thuocDao.themThuoc(t);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Đã lưu thuốc thành công!");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Lưu thất bại. Vui lòng kiểm tra lại dữ liệu!");
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lưu: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String must(String v, String fieldName) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException(fieldName + " không được rỗng");
        return v.trim();
    }

    private void clearForm() {
        txtMaThuoc.setText("");
        txtTenThuoc.setText("");
        txtHamLuong.setText("");
        cbxDangThuoc.setSelectedIndex(0);
        cbxDonViTinh.setSelectedIndex(0);
        txtGiaTien.setText("");
        txtNhaSanXuat.setText("");
        cbxKe.setSelectedIndex(-1);
        cbxNhom.setSelectedIndex(-1);
        cbxNhanVien.setSelectedIndex(-1);
        imgPath = null;
        imgPreview.setIcon(null);
        txtMaThuoc.requestFocus();
    }

    // helper tránh NullPointer khi renderer gọi getter
    private interface Getter { String get() throws Exception; }
    private String safe(Getter g) {
        try { String s = g.get(); return s == null ? "" : s; }
        catch (Exception e) { return ""; }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Thêm Thuốc (Demo)");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new AddThuocForm());
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
