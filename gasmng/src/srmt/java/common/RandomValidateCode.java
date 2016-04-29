package srmt.java.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

/**
 * @author Instant
 *生成验证码
 */
@Service
public class RandomValidateCode {


	private final Random random = new Random();

	private final String randString = "123456789ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";// 随机产生的字符串
	private final int width = 110;// 图片宽
	private final int height = 30;// 图片高
	private final int lineSize = 50;//干扰线数量
	private final int stringNum = 4;// 随机产生字符数量

	private final int fontSize = 33;// 字体大小

	/**
	 *生成随机图片
	 */
	public void getRandcode(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();
		g.fillRect(0, 0, this.width, this.height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, this.fontSize));
		g.setColor(this.getRandColor(110, 133));
		 // 绘制干扰线
		for (int i = 0; i <= this.lineSize; i++) {
			this.drawLine(g);
		}
		// 绘制随机字符
		String randomString = "";
		for (int i = 1; i <= this.stringNum; i++) {
			randomString = this.drawString(g, randomString, i);
		}
		session.removeAttribute(Constants.VALIDATE_CODE_KEY);
		session.setAttribute(Constants.VALIDATE_CODE_KEY, randomString);
		session.setAttribute(Constants.VALIDATE_CODE_TIME, new Date());
		g.dispose();
		try {
			 // 禁止图像缓存。
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			// 将内存中的图片通过流动形式输出到客户端
			ImageIO.write(image, "JPEG", response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 *获取随机的字符
	 */
	public String getRandomString(int num) {
		return String.valueOf(this.randString.charAt(num));
	}

	/**
	 *绘制干扰线
	 */
	private void drawLine(Graphics g) {
		int x = this.random.nextInt(this.width);
		int y = this.random.nextInt(this.height);
		int xl = this.random.nextInt(13);
		int yl = this.random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	/**
	 *绘制字符串
	 */
	private String drawString(Graphics g, String randomString, int i) {
		g.setFont(this.getFont());
		g.setColor(new Color(this.random.nextInt(155), this.random.nextInt(123), this.random.nextInt(176)));
		String rand = String.valueOf(this.getRandomString(this.random.nextInt(this.randString.length())));
		randomString += rand;
		g.translate(this.random.nextInt(3), this.random.nextInt(3));
		g.drawString(rand, (this.width / this.stringNum - 10) * i, this.height - 7);
		return randomString;
	}

	  /**
     * 获得字体
     */
	private Font getFont() {
		return new Font("Times New Roman", Font.CENTER_BASELINE, this.fontSize);
	}
	   /**
     * 获得颜色
     */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255) {
			fc = 255;
		}
		if (bc > 255) {
			bc = 255;
		}
		int r = fc + this.random.nextInt(bc - fc - 16);
		int g = fc + this.random.nextInt(bc - fc - 14);
		int b = fc + this.random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}
}