package picture;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Coordinate;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.util.ThumbnailatorUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by wana on 2015/11/30.
 */
public class PictureUtils {
    public static BufferedImage clipCircle(BufferedImage sourceBi) {
        BufferedImage resultBi = new BufferedImage(sourceBi.getWidth(), sourceBi.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, sourceBi.getWidth(), sourceBi
                .getHeight());
        Graphics2D g = resultBi.createGraphics();
        g.setColor(new Color(26, 144, 117, 255));

        g.fill(new Rectangle(resultBi.getWidth(), resultBi.getHeight()));
        g.setClip(shape);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawImage(sourceBi, 0, 0, null);
        g.dispose();
        return resultBi;
    }

    public static void main(String[] args) {
        File target = new File("D:\\test\\target.jpg");

        File base = new File("D:\\test\\base.jpg");
        String iconUrl = "http://wx.qlogo.cn/mmopen/Q3auHgzwzM6jpTAmSzMiaKmiaFwq2icGIQIcFfQNice4ba80P0VYgHTOF0WKTPX9JQU9WekT8zBt5hTMBAdEkq4LCg/0";
        Position iconPos = new Coordinate(50, 140);
        try {
//            BufferedImage iconBi =Thumbnails.of(new URL(iconUrl)).size(210,210).asBufferedImage();
//            .watermark(iconPos, iconBi, 1).scale(1.0).toFile(target);
            BufferedImage bi = Thumbnails.of(base).scale(1.0).asBufferedImage();
            Graphics2D g = bi.createGraphics();
            g.setColor(Color.WHITE);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.drawString("你好", 400, 250);
            g.dispose();
            Thumbnails.of(bi).scale(1.0).toFile(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
