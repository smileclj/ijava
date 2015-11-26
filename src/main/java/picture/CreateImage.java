package picture;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

public class CreateImage
{   
    public static void main(String[] args) throws Exception   
    {   
        int width = 100;   
        int height = 100;   
        String s = "你好";   
           
        File file = new File("D:/test/image.jpg");
        BufferedImage bi = ImageIO.read(new File("D:\\test\\g1.jpg"));
        Font font = new Font("黑体",Font.BOLD,30);
//        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D)bi.getGraphics();   
//        g2.setBackground(Color.WHITE);
//        g2.clearRect(0, 0, width, height);
        g2.setPaint(Color.RED);
           
//        FontRenderContext context = g2.getFontRenderContext();
//        Rectangle2D bounds = font.getStringBounds(s, context);
//        double x = (width - bounds.getWidth()) / 2;
//        double y = (height - bounds.getHeight()) / 2;
//        double ascent = -bounds.getY();
//        double baseY = y + ascent;

        g2.drawString(s, 50, 50);
//        g2.drawString(s, (int)x, (int)baseY);

        ImageIO.write(bi, "jpg", file);
    }   
}   