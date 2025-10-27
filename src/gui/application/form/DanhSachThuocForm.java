package gui.application.form;

import dao.ThuocDAO;
import entities.Thuoc;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableCellRenderer;


public class DanhSachThuocForm extends JPanel {

    
    private JTable tblThuoc;
    private DefaultTableModel model;
    private TableRowSorter<DefaultTableModel> sorter;

    private JTextField txtSearch;
    private JComboBox<String> cbDangThuoc, cbDonViTinh, cbNhaSanXuat;
    private final ThuocDAO dao = new ThuocDAO();   // dùng trực tiếp, hoặc inject từ ngoài

    // Lưu cache rows để filter dễ
    private List<Thuoc> allRows = new ArrayList<>();
    public DanhSachThuocForm() {
        setPreferredSize(new Dimension(806, 460));
        setLayout(new BorderLayout());

        add(buildTop(), BorderLayout.NORTH);
        add(buildCenterTable(), BorderLayout.CENTER);

        // Tự load dữ liệu khi khởi tạo
        loadDataAsync();
    }
    
    private JComponent buildTop() {
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(new EmptyBorder(8,10,8,10));

        JPanel left = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6,6,6,6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;

        JTextField title = new JTextField("Danh Sách Thuốc");
        title.setEditable(false);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBackground(new Color(0xF5,0xF5,0xF5));
        title.setFont(title.getFont().deriveFont(Font.PLAIN, 16f));

        gc.gridx=0; gc.gridy=0; gc.gridwidth=4;
        left.add(title, gc);

        txtSearch = new JTextField();
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm theo mã hoặc tên");
        gc.gridy=1; gc.gridx=0; gc.gridwidth=3;
        left.add(txtSearch, gc);

//        JButton btnLoc = new JButton("Lọc");
//        btnLoc.setForeground(Color.WHITE);
//        btnLoc.setBackground(new Color(0x00,0x70,0xF4));
//        btnLoc.setFocusPainted(false);
//        btnLoc.addActionListener(e -> applyFilters());
//        gc.gridy=1; gc.gridx=3; gc.gridwidth=1; gc.weightx=0;
//        left.add(btnLoc, gc);

        cbDangThuoc  = new JComboBox<>(new String[]{""});
        cbDonViTinh  = new JComboBox<>(new String[]{""});
        cbNhaSanXuat = new JComboBox<>(new String[]{""});

        gc.gridy=2; gc.gridx=0; gc.gridwidth=1; gc.weightx=1.0;
        left.add(labeled(cbDangThuoc, "Dạng thuốc"), gc);
        gc.gridx=1;
        left.add(labeled(cbDonViTinh, "Đơn vị tính"), gc);
        gc.gridx=2;
        left.add(labeled(cbNhaSanXuat, "Nhà sản xuất"), gc);

        top.add(left, BorderLayout.WEST);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 12));
        JButton btnXoa = new JButton("Xóa");
        btnXoa.setForeground(Color.WHITE);
        btnXoa.setBackground(new Color(0xF4,0x43,0x36));
        btnXoa.addActionListener(e -> handle_xoa());
        JButton btnCapNhat = new JButton("Cập nhật");
        btnCapNhat.setForeground(Color.WHITE);
        btnCapNhat.setBackground(new Color(0x00,0xB6,0x3E));
        btnCapNhat.addActionListener(e -> handle_capNhat());
        actions.add(btnXoa);
        actions.add(btnCapNhat);
        top.add(actions, BorderLayout.CENTER);

        // Enter để lọc nhanh
        txtSearch.addActionListener(e -> applyFilters());
        cbDangThuoc.addActionListener(e -> applyFilters());
        cbDonViTinh.addActionListener(e -> applyFilters());
        cbNhaSanXuat.addActionListener(e -> applyFilters());

        return top;
        
    }

    private JComponent labeled(JComponent c, String t) {
        JPanel p = new JPanel(new BorderLayout(0,4));
        p.add(new JLabel(t), BorderLayout.NORTH);
        p.add(c, BorderLayout.CENTER);
        return p;
    }

    private JComponent buildCenterTable() {
        String[] cols = {
                "Mã Thuốc","Tên Thuốc","Hàm Lượng","Dạng Thuốc",
                "Giá Thuốc","Đơn Vị Tính","Nhà Sản Xuất","Hình Ảnh"
        };
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        tblThuoc = new JTable(model);
        tblThuoc.setRowHeight(24);
        sorter = new TableRowSorter<>(model);
        tblThuoc.setRowSorter(sorter);

        // (Tuỳ chọn) canh phải cột giá
        tblThuoc.getColumnModel().getColumn(4).setCellRenderer((table, value, isSelected, hasFocus, row, column) -> {
            JLabel l = new JLabel();
            l.setOpaque(true);
            if (isSelected) {
                l.setBackground(table.getSelectionBackground());
                l.setForeground(table.getSelectionForeground());
            } else {
                l.setBackground(table.getBackground());
                l.setForeground(table.getForeground());
            }
            l.setHorizontalAlignment(SwingConstants.LEFT);
            l.setText(value == null ? "" : value.toString());
            return l;
        });

        return new JScrollPane(tblThuoc);
    }

    /** Load dữ liệu từ DB trên background thread để không giật UI */
    private void loadDataAsync() {
        new SwingWorker<List<Thuoc>, Void>() {
            @Override protected List<Thuoc> doInBackground() {
                // gọi DAO
                return dao.getAllTblThuoc();
            }
            @Override protected void done() {
                try {
                    allRows = get();
                    refreshTable(allRows);
                    fillFiltersFromData(allRows);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(DanhSachThuocForm.this,
                            "Lỗi tải dữ liệu thuốc: " + ex.getMessage(),
                            "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }.execute();
    }

    /** Đổ dữ liệu vào model */
    private void refreshTable(List<Thuoc> rows) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        model.setRowCount(0);
        for (Thuoc t : rows) {
            // Giả định getters tương ứng với constructor bạn đã dùng trong DAO
            // Thuoc(maThuoc, tenThuoc, hamLuong, dangThuoc, giaThuoc, donViTinh, nhaSanXuat, trangThai, anhDaiDien, keThuoc, nv, nhomThuoc)
            String ma = t.getMaThuoc();
            String ten = t.getTenThuoc();
            String ham = t.getHamLuong();
            String dang = t.getDangThuoc();
            String gia = nf.format(t.getGiaThuoc());
            String dvt = t.getDonViTinh();
            String nsx = t.getNhaSanXuat();
            String img = t.getAnhDaiDien();  // có thể là path hoặc URL

            model.addRow(new Object[]{ma, ten, ham, dang, gia, dvt, nsx, img});
        }
    }

    /** Lấy giá trị duy nhất cho 3 combobox từ dữ liệu đã load */
    private void fillFiltersFromData(List<Thuoc> rows) {
        Set<String> dangs = new TreeSet<>();
        Set<String> dvts  = new TreeSet<>();
        Set<String> nsxs  = new TreeSet<>();
        for (Thuoc t : rows) {
            if (t.getDangThuoc() != null && !t.getDangThuoc().isBlank()) dangs.add(t.getDangThuoc());
            if (t.getDonViTinh() != null && !t.getDonViTinh().isBlank()) dvts.add(t.getDonViTinh());
            if (t.getNhaSanXuat() != null && !t.getNhaSanXuat().isBlank()) nsxs.add(t.getNhaSanXuat());
        }
        setComboItems(cbDangThuoc, dangs);
        setComboItems(cbDonViTinh, dvts);
        setComboItems(cbNhaSanXuat, nsxs);
    }

    private void setComboItems(JComboBox<String> cb, Set<String> items) {
        String selected = Objects.toString(cb.getSelectedItem(), "");
        cb.removeAllItems();
        cb.addItem(""); // trống = không lọc
        for (String it : items) cb.addItem(it);
        cb.setSelectedItem(selected.isEmpty() ? "" : selected);
    }

    // ================== FILTER ==================
    private void applyFilters() {
        List<RowFilter<DefaultTableModel,Integer>> fs = new ArrayList<>();
        String key = txtSearch.getText().trim();
        if (!key.isEmpty()) {
            String pat = "(?i)" + Pattern.quote(key);
            fs.add(RowFilter.orFilter(List.of(
                    RowFilter.regexFilter(pat, 0), // mã
                    RowFilter.regexFilter(pat, 1)  // tên
            )));
        }
        String dang = sel(cbDangThuoc);
        if (!dang.isEmpty()) fs.add(RowFilter.regexFilter("^"+Pattern.quote(dang)+"$", 3));
        String dvt = sel(cbDonViTinh);
        if (!dvt.isEmpty()) fs.add(RowFilter.regexFilter("^"+Pattern.quote(dvt)+"$", 5));
        String nsx = sel(cbNhaSanXuat);
        if (!nsx.isEmpty()) fs.add(RowFilter.regexFilter("^"+Pattern.quote(nsx)+"$", 6));

        sorter.setRowFilter(fs.isEmpty() ? null : RowFilter.andFilter(fs));
    }

    private String sel(JComboBox<String> cb) {
        Object v = cb.getSelectedItem();
        return v == null ? "" : v.toString().trim();
    }

    // ================== ACTIONS ==================
    private void handle_xoa() {
        int view = tblThuoc.getSelectedRow();
        if (view < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
            return;
        }
        int modelRow = tblThuoc.convertRowIndexToModel(view);
        String ma = Objects.toString(model.getValueAt(modelRow, 0), "");
        String ten = Objects.toString(model.getValueAt(modelRow, 1), "");

        if (JOptionPane.showConfirmDialog(this, "Xóa (ngừng kinh doanh): " + ten + " ?",
                "Xác nhận", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            boolean ok = dao.xoaThuoc(ma);
            if (ok) {
                // load lại dữ liệu
                loadDataAsync();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa không thành công.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi xóa thuốc: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handle_capNhat() {
        int viewRow = tblThuoc.getSelectedRow();
        if (viewRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để cập nhật.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int modelRow = tblThuoc.convertRowIndexToModel(viewRow);
        String ma = Objects.toString(model.getValueAt(modelRow, 0), "");

        Window owner = SwingUtilities.getWindowAncestor(this);
        CapNhatThuocDialog dlg = new CapNhatThuocDialog(owner, ma);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        if (dlg.isUpdated()) {
            // Sau khi cập nhật thành công -> load lại dữ liệu danh sách
            loadDataAsync();   // chính là hàm bạn đang dùng để refresh bảng
        }
    }
}
