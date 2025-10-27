package gui.application.form;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Danh sách hoá đơn (Swing) - Bộ lọc ở phía trên
 * Cột mẫu: Mã | Ngày | Khách hàng | Nhân viên | Tổng tiền | Trạng thái
 */
public class DsHoaDonForm extends JPanel {

    private final JTextField txtSearch = new JTextField();            // tìm mã/khách/nhân viên
    private final JComboBox<String> cbStatus = new JComboBox<>(new String[]{
            "Tất cả", "Đã thanh toán", "Chưa thanh toán", "Huỷ"
    });
    private final JSpinner spFrom = new JSpinner(new SpinnerDateModel());
    private final JSpinner spTo   = new JSpinner(new SpinnerDateModel());
    private final JButton btnFilter = new JButton("Lọc");
    private final JButton btnClear  = new JButton("Xoá lọc");
    private final JButton btnReload = new JButton("Tải lại");

    private final JTable table;
    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;

    private final NumberFormat money = NumberFormat.getNumberInstance(new Locale("vi", "VN"));
    private final SimpleDateFormat dmy = new SimpleDateFormat("dd/MM/yyyy");

    // dữ liệu cache để có thể re-load (nếu nạp từ DAO thì bạn có thể bỏ cache này)
    private final java.util.List<Object[]> allRows = new ArrayList<>();

    public DsHoaDonForm() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(8, 8, 8, 8));

        // ===== TOP FILTER BAR =====
        JPanel top = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 6, 6, 6);
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy = 0;

        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm mã / khách hàng / nhân viên");

        // spinner format dd/MM/yyyy
        JSpinner.DateEditor edFrom = new JSpinner.DateEditor(spFrom, "dd/MM/yyyy");
        JSpinner.DateEditor edTo   = new JSpinner.DateEditor(spTo,   "dd/MM/yyyy");
        spFrom.setEditor(edFrom);
        spTo.setEditor(edTo);
        // mặc định: từ đầu tháng đến hôm nay
        Calendar cal = Calendar.getInstance();
        Date today = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        spFrom.setValue(cal.getTime());
        spTo.setValue(today);

        int col = 0;
        gc.gridx = col++; gc.weightx = 1.4; top.add(txtSearch, gc);
        gc.gridx = col++; gc.weightx = 0;   top.add(new JLabel("Từ ngày"), gc);
        gc.gridx = col++; gc.weightx = 0.3; top.add(spFrom, gc);
        gc.gridx = col++; gc.weightx = 0;   top.add(new JLabel("Đến ngày"), gc);
        gc.gridx = col++; gc.weightx = 0.3; top.add(spTo, gc);
        gc.gridx = col++; gc.weightx = 0;   top.add(new JLabel("Trạng thái"), gc);
        gc.gridx = col++; gc.weightx = 0.5; top.add(cbStatus, gc);
        gc.gridx = col++; gc.weightx = 0;   top.add(btnFilter, gc);
        gc.gridx = col++; gc.weightx = 0;   top.add(btnClear, gc);
        gc.gridx = col++; gc.weightx = 0;   top.add(btnReload, gc);

        add(top, BorderLayout.NORTH);

        // ===== TABLE =====
        String[] cols = {"Mã HĐ", "Ngày lập", "Khách hàng", "Nhân viên", "Tổng tiền", "Trạng thái"};
        model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
            @Override public Class<?> getColumnClass(int columnIndex) {
                return switch (columnIndex) {
                    case 1 -> Date.class;    // Ngày lập
                    case 4 -> Double.class;  // Tổng tiền
                    default -> String.class;
                };
            }
        };
        table = new JTable(model);
        table.setRowHeight(26);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        // format renderer cho tiền & ngày
        table.getColumnModel().getColumn(1).setCellRenderer((tbl, val, sel, foc, row, col1) -> {
            JLabel l = new JLabel();
            l.setOpaque(true);
            if (sel) { l.setBackground(tbl.getSelectionBackground()); l.setForeground(tbl.getSelectionForeground()); }
            l.setText(val instanceof Date ? dmy.format((Date) val) : "");
            l.setHorizontalAlignment(SwingConstants.LEFT);
            return l;
        });
        table.getColumnModel().getColumn(4).setCellRenderer((tbl, val, sel, foc, row, col1) -> {
            JLabel l = new JLabel();
            l.setOpaque(true);
            if (sel) { l.setBackground(tbl.getSelectionBackground()); l.setForeground(tbl.getSelectionForeground()); }
            if (val instanceof Number n) l.setText(money.format(n.doubleValue()));
            l.setHorizontalAlignment(SwingConstants.RIGHT);
            l.setFont(l.getFont().deriveFont(Font.BOLD));
            return l;
        });

        add(new JScrollPane(table), BorderLayout.CENTER);

        // ===== EVENTS =====
        btnFilter.addActionListener(e -> applyFilters());
        btnClear.addActionListener(e -> {
            txtSearch.setText("");
            cbStatus.setSelectedIndex(0);
            // reset ngày về mặc định
            Calendar c = Calendar.getInstance();
            Date t = c.getTime();
            c.set(Calendar.DAY_OF_MONTH, 1);
            spFrom.setValue(c.getTime());
            spTo.setValue(t);
            applyFilters();
        });
        btnReload.addActionListener(e -> {
            loadDataFromDAO();   // nạp từ DB
            applyFilters();      // áp lại filter hiện tại
        });
        // gõ Enter để lọc
        txtSearch.addActionListener(e -> applyFilters());

        // ===== LOAD DATA =====
        loadDataFromDAO();  // hoặc dữ liệu mẫu
        applyFilters();
    }

    /** Nạp dữ liệu (sửa hàm này để lấy từ DAO của bạn) */
    private void loadDataFromDAO() {
        // Xoá cũ
        allRows.clear();
        model.setRowCount(0);

        try {
            // TODO: thay bằng HoaDonDAO.getAll() (ví dụ)
            // Ví dụ dữ liệu mẫu:
            allRows.add(row("HD001", "02/03/2025", "Nguyễn Văn A", "Trần B", 1500000, "Đã thanh toán"));
            allRows.add(row("HD002", "05/03/2025", "Lê Thị C", "Trần B",   250000, "Chưa thanh toán"));
            allRows.add(row("HD003", "10/03/2025", "Phạm D",     "Ngô E",  800000, "Huỷ"));
            allRows.add(row("HD004", "11/03/2025", "Nguyễn Văn A","Ngô E",  560000, "Đã thanh toán"));

            // Nếu dùng DAO, bạn convert entity -> Object[] theo thứ tự cột ở đây
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        for (Object[] r : allRows) model.addRow(r);
    }

    private Object[] row(String ma, String ngay_ddMMyyyy, String kh, String nv, double tongTien, String tt) throws ParseException {
        Date d = dmy.parse(ngay_ddMMyyyy);
        return new Object[]{ma, d, kh, nv, tongTien, tt};
    }

    /** Áp RowFilter theo ô tìm kiếm + ngày + trạng thái */
    private void applyFilters() {
        String q = txtSearch.getText().trim().toLowerCase();
        Date from = (Date) spFrom.getValue();
        Date to   = (Date) spTo.getValue();
        // đảm bảo to cuối ngày
        Calendar c = Calendar.getInstance();
        c.setTime(to);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        Date toEnd = c.getTime();

        String st = Objects.toString(cbStatus.getSelectedItem(), "Tất cả");

        List<RowFilter<DefaultTableModel, Integer>> fs = new ArrayList<>();

        // tìm kiếm ở cột 0,2,3
        if (!q.isEmpty()) {
            RowFilter<DefaultTableModel, Integer> fMa = RowFilter.regexFilter("(?i)" + Pattern.quote(q), 0);
            RowFilter<DefaultTableModel, Integer> fKH = RowFilter.regexFilter("(?i)" + Pattern.quote(q), 2);
            RowFilter<DefaultTableModel, Integer> fNV = RowFilter.regexFilter("(?i)" + Pattern.quote(q), 3);
            fs.add(RowFilter.orFilter(List.of(fMa, fKH, fNV)));
        }

        // lọc theo ngày (cột 1)
        fs.add(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Integer> entry) {
                Object val = entry.getValue(1);
                if (!(val instanceof Date d)) return true;
                return !d.before(from) && !d.after(toEnd);
            }
        });

        // lọc trạng thái (cột 5)
        if (!"Tất cả".equals(st)) {
            fs.add(RowFilter.regexFilter("^" + Pattern.quote(st) + "$", 5));
        }

        sorter.setRowFilter(fs.isEmpty() ? null : RowFilter.andFilter(fs));
    }

    // ====== tiện ích: chuyển LocalDate -> Date nếu bạn cần set range từ code ======
    private static Date toDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    // ====== Demo chạy lẻ ======
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Danh sách hoá đơn");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new DsHoaDonForm());
            f.setSize(1100, 640);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }
}
