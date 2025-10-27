package gui.application.form;

import dao.ThuocDAO;
import entities.Thuoc;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Lập hoá đơn - Layout giống ảnh mẫu:
 * - Phải: Grid danh sách thuốc (card: ảnh + tên + giá)
 * - Trái: Giỏ hàng (VBox) + Tổng tiền + Thanh toán
 */
public class LapHoaDonForm extends JPanel {

    // ===== Model đơn giản =====
    static class Product {
        String ma;
        String ten;
        double gia;
        String anhPath; // có thể là đường dẫn file/URL

        Product(String ma, String ten, double gia, String anhPath) {
            this.ma = ma; this.ten = ten; this.gia = gia; this.anhPath = anhPath;
        }
    }
    static class CartItem {
        Product p;
        int qty;
        CartItem(Product p, int qty) { this.p = p; this.qty = qty; }
        double subtotal() { return p.gia * qty; }
    }

    // ===== UI =====
    private final JPanel cartList = new JPanel();          // VBox (Y_AXIS)
    private final JLabel lblTotalQty = new JLabel("0");
    private final JLabel lblTotalMoney = new JLabel("0");
    private final JTextField txtSearch = new JTextField();
    private final JPanel productGrid = new JPanel();       // Grid cards
    private final JComboBox<Integer> cbCols = new JComboBox<>(new Integer[]{3, 4, 5, 6});

    // ===== Data =====
    private final Map<String, CartItem> cart = new LinkedHashMap<>();
    private final List<Product> allProducts = new ArrayList<>();

    private final NumberFormat money = NumberFormat.getNumberInstance(new Locale("vi", "VN"));

    public LapHoaDonForm() {
        setLayout(new BorderLayout());
        add(buildHeader(), BorderLayout.NORTH);
        add(buildBody(), BorderLayout.CENTER);

        // load data
        loadProductsFromDAO();
        renderProductGrid(allProducts);
        updateTotals();
    }

    private JComponent buildHeader() {
        // dải màu xanh đậm như ảnh (tuỳ chọn)
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(0x0A67FF));
        bar.setBorder(new EmptyBorder(8, 12, 8, 12));

        JLabel title = new JLabel("Lập hoá đơn");
        title.setForeground(Color.WHITE);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 16f));

        bar.add(title, BorderLayout.WEST);
        return bar;
    }

    private JComponent buildBody() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setDividerSize(6);
        split.setResizeWeight(0.45);
        split.setLeftComponent(buildCartPanel());
        split.setRightComponent(buildProductPanel());

        return split;
    }

    // ----------------- LEFT (Cart) -----------------
    private JComponent buildCartPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(8, 8, 8, 6));
        root.setMinimumSize(new Dimension(420, 0));  // không cho nhỏ hơn

        // Search hàng hoá bên trái (tuỳ chọn, giống ảnh bạn để F3)
        JPanel searchBar = new JPanel(new BorderLayout(6, 0));
        JTextField tf = new JTextField();
        tf.putClientProperty("JTextField.placeholderText", "Tìm hàng hoá (F3)");
        JButton scanBtn = new JButton("\uD83D\uDD0D"); // icon giả
        scanBtn.setFocusable(false);
        searchBar.add(tf, BorderLayout.CENTER);
        searchBar.add(scanBtn, BorderLayout.EAST);
        root.add(searchBar, BorderLayout.NORTH);

        // Cart list as VBox
        cartList.setLayout(new BoxLayout(cartList, BoxLayout.Y_AXIS));
        JPanel vboxWrapper = new JPanel(new BorderLayout());
        vboxWrapper.add(cartList, BorderLayout.NORTH); // để nó co theo nội dung
        JScrollPane scroll = new JScrollPane(vboxWrapper);
        scroll.setBorder(null);
        root.add(scroll, BorderLayout.CENTER);

        // Footer: note + totals + button
        root.add(buildCartFooter(), BorderLayout.SOUTH);

        return wrapCard(root);
    }

    private JComponent buildCartFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(8, 0, 0, 0));

//        JTextField note = new JTextField();
//        note.putClientProperty("JTextField.placeholderText", "Ghi chú đơn hàng");
//        footer.add(note, BorderLayout.NORTH);

        JPanel totalBar = new JPanel(new GridLayout(1, 3, 12, 0));
        totalBar.setBorder(new EmptyBorder(8, 0, 8, 0));

        totalBar.add(labeledCenter("Tổng tiền hàng", lblTotalQty));
        totalBar.add(new JLabel("")); // chèn khoảng giống ảnh

        JLabel lblMoneyTitle = new JLabel("Tổng tiền (VNĐ)", SwingConstants.RIGHT);
        lblMoneyTitle.setFont(lblMoneyTitle.getFont().deriveFont(Font.PLAIN, 12f));
        lblTotalMoney.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTotalMoney.setFont(lblTotalMoney.getFont().deriveFont(Font.BOLD, 16f));

        JPanel moneyBox = new JPanel(new BorderLayout());
        moneyBox.add(lblMoneyTitle, BorderLayout.NORTH);
        moneyBox.add(lblTotalMoney, BorderLayout.SOUTH);
        totalBar.add(moneyBox);

        footer.add(totalBar, BorderLayout.CENTER);

        JButton pay = new JButton("THANH TOÁN");
        pay.setBackground(new Color(0x0A67FF));
        pay.setForeground(Color.WHITE);
        pay.setFocusPainted(false);
        pay.setFont(pay.getFont().deriveFont(Font.BOLD, 14f));
        footer.add(pay, BorderLayout.SOUTH);

        return footer;
    }

    private JComponent labeledCenter(String title, JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        JLabel lb = new JLabel(title);
        lb.setFont(lb.getFont().deriveFont(Font.PLAIN, 12f));
        c.setFont(c.getFont().deriveFont(Font.BOLD, 16f));
        lb.setHorizontalAlignment(SwingConstants.LEFT);
        if (c instanceof JLabel l) l.setHorizontalAlignment(SwingConstants.LEFT);
        p.add(lb, BorderLayout.NORTH);
        p.add(c, BorderLayout.SOUTH);
        return p;
    }

    private void addToCart(Product p) {
        CartItem item = cart.get(p.ma);
        if (item == null) {
            cart.put(p.ma, new CartItem(p, 1));
        } else {
            item.qty++;
        }
        renderCart();
        updateTotals();
    }

    private void removeFromCart(String ma) {
        cart.remove(ma);
        renderCart();
        updateTotals();
    }

    private void changeQty(String ma, int delta) {
        CartItem it = cart.get(ma);
        if (it == null) return;
        it.qty += delta;
        if (it.qty <= 0) cart.remove(ma);
        renderCart();
        updateTotals();
    }

    private void renderCart() {
        cartList.removeAll();
        for (CartItem it : cart.values()) {
            cartList.add(buildCartRow(it));
            cartList.add(Box.createVerticalStrut(8));
        }
        cartList.revalidate();
        cartList.repaint();
    }

    private JComponent buildCartRow(CartItem it) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(new LineBorder(new Color(230,230,230), 1, true));
        row.setBackground(Color.WHITE);

        // Left: qty bubble + mã + tên
        JPanel left = new JPanel(new BorderLayout(8, 0));
        left.setOpaque(false);
        JLabel qtyBubble = badge(String.valueOf(it.qty));
        left.add(qtyBubble, BorderLayout.WEST);

        JPanel nameBox = new JPanel(new GridLayout(2,1));
        nameBox.setOpaque(false);
        nameBox.add(new JLabel(it.p.ma));
        JLabel ten = new JLabel(it.p.ten);
        ten.setFont(ten.getFont().deriveFont(Font.BOLD));
        nameBox.add(ten);
        left.add(nameBox, BorderLayout.CENTER);

        // Right: [-]  qty  [+]  ... subtotal + remove
        JPanel right = new JPanel(new GridBagLayout());
        right.setOpaque(false);
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(2,6,2,6);
        JButton minus = tinyButton("-");
        JButton plus  = tinyButton("+");
        JButton trash = tinyIconButton("\uD83D\uDDD1"); // 🗑

        JLabel qty = new JLabel(String.valueOf(it.qty));
        qty.setHorizontalAlignment(SwingConstants.CENTER);
        qty.setPreferredSize(new Dimension(28, 24));

        JLabel moneyLb = new JLabel(money.format(it.subtotal()));
        moneyLb.setFont(moneyLb.getFont().deriveFont(Font.BOLD));
        moneyLb.setHorizontalAlignment(SwingConstants.RIGHT);

        minus.addActionListener(e -> changeQty(it.p.ma, -1));
        plus.addActionListener(e -> changeQty(it.p.ma, +1));
        trash.addActionListener(e -> removeFromCart(it.p.ma));

        gc.gridx=0; right.add(minus, gc);
        gc.gridx=1; right.add(qty, gc);
        gc.gridx=2; right.add(plus, gc);
        gc.gridx=3; gc.weightx=1; gc.anchor = GridBagConstraints.EAST; right.add(moneyLb, gc);
        gc.gridx=4; gc.weightx=0; right.add(trash, gc);

        row.add(left, BorderLayout.WEST);
        row.add(right, BorderLayout.EAST);
        return row;
    }

    private JButton tinyButton(String text) {
        JButton b = new JButton(text);
        b.setMargin(new Insets(2,8,2,8));
        return b;
    }
    private JButton tinyIconButton(String text) {
        JButton b = new JButton(text);
        b.setMargin(new Insets(2,8,2,8));
        return b;
    }
    private JLabel badge(String text) {
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(new Color(0xF0F0F0));
        l.setBorder(new LineBorder(new Color(210,210,210), 1, true));
        l.setPreferredSize(new Dimension(26, 26));
        return l;
    }

    private void updateTotals() {
        int totalQty = cart.values().stream().mapToInt(ci -> ci.qty).sum();
        double totalMoney = cart.values().stream().mapToDouble(CartItem::subtotal).sum();
        lblTotalQty.setText(String.valueOf(totalQty));
        lblTotalMoney.setText(money.format(totalMoney));
    }

    private JComponent wrapCard(JComponent c) {
        c.setBorder(new LineBorder(new Color(230,230,230), 1, true));
        return c;
    }

    // ----------------- RIGHT (Product Grid) -----------------
    private JComponent buildProductPanel() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(8, 6, 8, 8));

        // Top: search + controls
        JPanel top = new JPanel(new BorderLayout(6,0));
        txtSearch.putClientProperty("JTextField.placeholderText", "Tìm khách hàng (F4)");
        JButton plusCustomer = new JButton("+"); plusCustomer.setToolTipText("Thêm khách");
        JButton layoutBtn = new JButton("\u2630"); layoutBtn.setToolTipText("Tuỳ chọn lưới");
        layoutBtn.addActionListener(e -> {
            Integer cols = (Integer) cbCols.getSelectedItem();
            setGridColumns(cols == null ? 4 : cols);
        });
        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        rightControls.add(new JLabel("Cột:"));
        rightControls.add(cbCols);
        rightControls.add(layoutBtn);
        rightControls.add(plusCustomer);
        top.add(txtSearch, BorderLayout.CENTER);
        top.add(rightControls, BorderLayout.EAST);
        root.add(top, BorderLayout.NORTH);

        // Grid
        productGrid.setLayout(new GridLayout(0, 3, 12, 12));
        JScrollPane scroll = new JScrollPane(productGrid);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);

        // search logic
        txtSearch.getDocument().addDocumentListener((SimpleDocumentListener) e -> {
            String q = txtSearch.getText().trim().toLowerCase();
            List<Product> filtered = allProducts.stream()
                    .filter(p -> p.ma.toLowerCase().contains(q) || p.ten.toLowerCase().contains(q))
                    .collect(Collectors.toList());
            renderProductGrid(filtered);
        });

        return wrapCard(root);
    }

    private void setGridColumns(int cols) {
        productGrid.setLayout(new GridLayout(0, Math.max(1, cols), 12, 12));
        productGrid.revalidate();
    }

    private void renderProductGrid(List<Product> list) {
        productGrid.removeAll();
        for (Product p : list) {
            productGrid.add(buildProductCard(p));
        }
        productGrid.revalidate();
        productGrid.repaint();
    }

    private JComponent buildProductCard(Product p) {
        JPanel card = new JPanel(new BorderLayout(6,6));
        card.setBorder(new LineBorder(new Color(225,225,225), 1, true));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(180, 90));

        JLabel img = new JLabel();
        img.setHorizontalAlignment(SwingConstants.CENTER);
        img.setPreferredSize(new Dimension(48,48));
        img.setIcon(loadScaledIcon(p.anhPath, 48, 48));

        JLabel name = new JLabel(htmlEllipsis(p.ten, 28));
        JLabel price = new JLabel(money.format(p.gia));
        name.setFont(name.getFont().deriveFont(Font.PLAIN));
        price.setFont(price.getFont().deriveFont(Font.BOLD));
        price.setForeground(new Color(0x0A67FF));

        JPanel center = new JPanel(new GridLayout(2,1));
        center.setOpaque(false);
        center.add(name);
        center.add(price);

        JPanel left = new JPanel(new BorderLayout());
        left.setOpaque(false);
        left.add(img, BorderLayout.CENTER);

        // click để thêm vào giỏ
        MouseAdapter addToCart = new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) { addToCart(p); }
        };
        card.addMouseListener(addToCart);
        img.addMouseListener(addToCart);
        name.addMouseListener(addToCart);
        price.addMouseListener(addToCart);

        card.add(left, BorderLayout.WEST);
        card.add(center, BorderLayout.CENTER);
        return card;
    }

    private Icon loadScaledIcon(String path, int w, int h) {
        try {
            if (path != null && !path.isBlank()) {
                BufferedImage image = ImageIO.read(new File(path));
                if (image != null) {
                    Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    return new ImageIcon(scaled);
                }
            }
        } catch (Exception ignored) {}
        // placeholder
        BufferedImage ph = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = ph.createGraphics();
        g.setColor(new Color(240,240,240));
        g.fillRoundRect(0,0,w,h,8,8);
        g.setColor(new Color(200,200,200));
        g.drawRoundRect(0,0,w-1,h-1,8,8);
        g.dispose();
        return new ImageIcon(ph);
    }

    private String htmlEllipsis(String s, int max) {
        if (s == null) return "";
        return "<html>" + (s.length() > max ? s.substring(0, max-1) + "…" : s) + "</html>";
    }

    // ----------------- DATA -----------------
    private void loadProductsFromDAO() {
        try {
            // Dùng dữ liệu thật
            ThuocDAO dao = new ThuocDAO();
            List<Thuoc> ds = dao.getAllTblThuoc(); // lấy thuốc "Đang Kinh Doanh"
            for (Thuoc t : ds) {
                allProducts.add(new Product(
                        t.getMaThuoc(),
                        t.getTenThuoc(),
                        t.getGiaThuoc(),
                        t.getAnhDaiDien()
                ));
            }
        } catch (Exception ex) {
            // Fallback demo nếu DAO lỗi
            allProducts.clear();
            allProducts.add(new Product("NT00001", "Aikido Gel Cool Patch H/3 Gói", 0, ""));
            allProducts.add(new Product("NT00002", "Floslek Kem Dưỡng Ẩm Kiềm Dầu 50ml", 0, ""));
            allProducts.add(new Product("NT00003", "Cebraton Hộp 5 Vỉ x10 Viên", 0, ""));
            allProducts.add(new Product("NT00004", "Băng keo cá nhân 1.9 x 7.2cm", 0, ""));
        }
    }

    // ====== Demo chạy lẻ ======
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame f = new JFrame("Lập hoá đơn (Demo)");
            f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            f.setContentPane(new LapHoaDonForm());
            f.setSize(1280, 720);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        });
    }

    // ====== Document listener gọn ======
    private interface SimpleDocumentListener extends javax.swing.event.DocumentListener {
        void update(javax.swing.event.DocumentEvent e);
        @Override default void insertUpdate(javax.swing.event.DocumentEvent e) { update(e); }
        @Override default void removeUpdate(javax.swing.event.DocumentEvent e) { update(e); }
        @Override default void changedUpdate(javax.swing.event.DocumentEvent e) { update(e); }
    }
}
