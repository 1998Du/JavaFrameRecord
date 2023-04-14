package checkcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * Description:
 *          生成验证码
 * @author: mushi
 * @Date: 2021/2/6 9:31
 */
public class CheckCode {

    /**验证码*/
    private String check = "";
    List<Integer> reuse = new ArrayList<>();

    char[] charCode = {'1','2','3','4','5','6','7','8','9','0','a','b',
                        'c','d','e','f','g','h','i','j','k','l','m','n',
                        'o','p','q','r','s','t','u','v','w','x','y','z',
                        'A','B','C','D','E','F','G','H','I','J','K','L',
                        'M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

    /**num:验证码位数*/
    public String getCheckCode(int num){

        char[] result = new char[num];

        for (int k = 0;k<num;k++){
            int j = new Random().nextInt(62);
            char en = charCode[j];
            int randow = getRandow(num);
            result[randow] = en;
        }
        for (int i = 0;i<result.length;i++){
            check += String.valueOf(result[i]);
        }

        return check;
    }

    /**排位*/
    public int getRandow(int round){
        int cheakNum = new Random().nextInt(round);
        if (!reuse.contains(cheakNum)){
            reuse.add(cheakNum);
        }else{
            return this.getRandow(round);
        }
        return cheakNum;
    }


    /**图片验证码*/
    private static Random random = new Random();
    private static int width = 160;// 宽
    private static int height = 40;// 高
    private static int lineSize = 30;// 干扰线数量
    private static int stringNum = 4;//随机产生字符的个数
    private static String randomString = "0123456789abcdefghijklmnopqrstuvwxyz";//随机字符
    private static String pictureFormat = "PNG";// 图片格式

    public void imageCode(OutputStream outputStream){
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();
        g.fillRect(0, 0, width, height);
        g.setColor(getRandomColor(105, 189));
        g.setFont(getFont());

        // 绘制干扰线
        for (int i = 0; i < lineSize; i++) {
            drawLine(g);
        }

        // 绘制随机字符
        String random_string = "";
        for (int i = 0; i < stringNum; i++) {
            random_string = drawString(g, random_string, i);
        }

        //System.out.println(random_string);

        g.dispose();

//        session.removeAttribute(sessionKey);
//        session.setAttribute(sessionKey, random_string);

        String base64String = "";
        try {
            //  直接返回图片
            ImageIO.write(image, pictureFormat, outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**随机颜色设置*/
    private static Color getRandomColor(int fc, int bc) {

        fc = Math.min(fc, 255);
        bc = Math.min(bc, 255);

        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 12);

        return new Color(r, g, b);
    }

    /**字体设置*/
    private static Font getFont() {
        return new Font("Times New Roman", Font.ROMAN_BASELINE, 40);
    }

    private static String drawString(Graphics g, String randomStr, int i) {
        g.setFont(getFont());
        g.setColor(getRandomColor(108, 190));
        //System.out.println(random.nextInt(randomString.length()));
        String rand = getRandomString(random.nextInt(randomString.length()));
        randomStr += rand;
        g.translate(random.nextInt(3), random.nextInt(6));
        g.drawString(rand, 40 * i + 10, 25);
        return randomStr;
    }

    /**干扰线绘制*/
    private static void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(20);
        int yl = random.nextInt(10);
        g.drawLine(x, y, x + xl, y + yl);
    }

    private static String getRandomString(int num) {
        num = num > 0 ? num : randomString.length();
        return String.valueOf(randomString.charAt(random.nextInt(num)));
    }

}
