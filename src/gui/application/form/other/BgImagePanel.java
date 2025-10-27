package gui.application.form.other;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class BgImagePanel extends JPanel {
    private BufferedImage img;

    // Dùng nếu ảnh nằm ngoài classpath (đường dẫn file)
    public BgImagePanel(String filePath) {
        loadFromFile(filePath);
    }

    // Dùng nếu ảnh để trong resources (classpath), ví dụ /images/bg6.jpg
    public static BgImagePanel fromResource(String resourcePath) {
        BgImagePanel p = new BgImagePanel();
        p.loadFromResource(resourcePath);
        return p;
    }
    private BgImagePanel() {}

    private void loadFromFile(String filePath) {
        try {
            img = ImageIO.read(new File(filePath));
        } catch (Exception e) {
            img = null;
        }
    }
    private void loadFromResource(String resourcePath) {
        try {
            URL url = getClass().getResource(resourcePath);
            if (url != null) img = ImageIO.read(url);
        } catch (Exception e) {
            img = null;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (img == null) return;

        // “cover”: lấp đầy panel, cắt bớt nếu cần, giữ tỉ lệ
        int pw = getWidth(), ph = getHeight();
        int iw = img.getWidth(), ih = img.getHeight();
        double scale = Math.max((double) pw / iw, (double) ph / ih);
        int w = (int) Math.round(iw * scale);
        int h = (int) Math.round(ih * scale);
        int x = (pw - w) / 2;
        int y = (ph - h) / 2;

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, x, y, w, h, null);
        g2.dispose();
    }
}
