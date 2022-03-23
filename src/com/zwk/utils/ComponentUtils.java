package com.zwk.utils;

import com.sun.awt.AWTUtilities;
import com.sun.java.swing.SwingUtilities3;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicScrollBarUI;


public class ComponentUtils {
	public static Font defaultFont = new Font("微软雅黑", Font.PLAIN, 14);
	public static Font defaultFont_bold = new Font("微软雅黑", Font.BOLD, 14);

	/**
	 * 获取一个带有图标的label用以侧边栏 菜单栏label
	 *
	 * @param iconPath
	 * @param text
	 * @return
	 */
	public static JLabel getIconLabel(URL iconPath, String text) {
		JLabel content_label = new JLabel(text);
		content_label.setIcon(new ImageIcon(iconPath));
		content_label.setBorder(BorderFactory.createMatteBorder(0, 20, 0, 0, new Color(32, 47, 61)));
		content_label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		content_label.setForeground(Color.white);
		return content_label;
	}

	/**
	 * 获取左侧边栏button
	 *
	 * @param text
	 * @return
	 */
	public static JButton getLeftSidebarCommonButton(String text) {
		JButton jButton = new MyButton();
		jButton.setBorder(null);
		jButton.setText(text);
		jButton.setBackground(SystemColor.control);
		jButton.setFocusPainted(false);
		jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		jButton.setBackground(new Color(22, 33, 43));
		jButton.setForeground(new Color(242, 242, 242));
		jButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		// 设置内容水平靠左
		jButton.setHorizontalAlignment(SwingConstants.LEFT);
		// 使用border来控制内边距
		jButton.setBorder(BorderFactory.createMatteBorder(0, 40, 0, 0, new Color(22, 33, 43)));
		jButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				jButton.setBackground(new Color(6, 101, 161));
				jButton.setBorder(BorderFactory.createMatteBorder(0, 40, 0, 0, new Color(6, 101, 161)));

			}

			@Override
			public void mouseExited(MouseEvent e) {
				jButton.setBackground(new Color(22, 33, 43));
				jButton.setBorder(BorderFactory.createMatteBorder(0, 40, 0, 0, new Color(22, 33, 43)));
			}

		});
		return jButton;
	}

	/**
	 * 获取一个按比例缩放的图片
	 *
	 * @param imagePath
	 * @return
	 */
	public static ImageIcon getScaledImage(URL imagePath, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(imagePath);
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return imageIcon;
	}

	public static ImageIcon getScaledImage(ImageIcon imageIcon, int width, int height) {
		imageIcon.setImage(imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT));
		return imageIcon;
	}

	/**
	 * 返回一个带有图标和功能名称
	 *
	 * @param imagePath
	 * @param functionName
	 * @return
	 */
	public static JPanel getFunctionJPanel(URL imagePath, String functionName) {
		JPanel content_panel = new JPanel();
		content_panel.setLayout(null);
		content_panel.setSize(200, 200);
		content_panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		content_panel.setBackground(Color.white);
		content_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		content_panel.addMouseListener(new MouseAdapter() {
			boolean isClicked = false;

			@Override
			public void mouseClicked(MouseEvent e) {
				isClicked = !isClicked;
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				content_panel.setBorder(BorderFactory.createLineBorder(new Color(54, 148, 117), 2));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				content_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
			}
		});
		ImageIcon imageIcon = new ImageIcon(imagePath);
		JLabel image_label = new JLabel();
		image_label.setIcon(imageIcon);
		image_label.setBounds((content_panel.getWidth() - 60) / 2, (content_panel.getHeight() - 90) / 2, 60, 60);
		content_panel.add(image_label);
		JLabel functionName_label = new JLabel(functionName);
		functionName_label.setFont(defaultFont);
		functionName_label.setBounds((content_panel.getWidth() - functionName.length() * 15) / 2,
				image_label.getY() + image_label.getHeight() + 2, functionName.length() * 15, 30);
		content_panel.add(functionName_label);
		return content_panel;
	}

	public static JLabel getDetailedInfoLabel(String text) {
		JLabel template_label = new JLabel(text);
		template_label.setFont(defaultFont);
		template_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
		return template_label;
	}

	public static JButton getCommonButton(String title) {
		JButton jButton = new MyButton(title);
		jButton.setBackground(new Color(54, 148, 117));
		jButton.setForeground(Color.white);
		jButton.setBorder(null);
		jButton.setFocusPainted(false);
		jButton.setFont(new Font("微软雅黑", Font.BOLD, 14));
		jButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return jButton;
	}

	public static BasicScrollBarUI getDemoScrollBarUI() {
		DemoScrollBarUI demoScrollBarUI = new DemoScrollBarUI();
		return demoScrollBarUI;
	}
}


/**
 * 更改默认鼠标点击时背景色的button
 */
class MyButton extends JButton {

	private final Color hoverBackgroundColor = new Color(6, 101, 161);
	private final Color pressedBackgroundColor = new Color(6, 101, 161);

	public MyButton() {
		this(null);
	}

	public MyButton(String text) {
		super(text);
		super.setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(pressedBackgroundColor);
		} else if (getModel().isRollover()) {
			g.setColor(hoverBackgroundColor);
		} else {
			g.setColor(getBackground());
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}


}


//自定义滚动条UI
class DemoScrollBarUI extends BasicScrollBarUI {

	@Override
	protected void configureScrollBarColors() {

		// 把手

		// thumbColor = Color.GRAY;

		// thumbHighlightColor = Color.BLUE;

		// thumbDarkShadowColor = Color.BLACK;

		// thumbLightShadowColor = Color.YELLOW;

		// 滑道

		trackColor = Color.black;

		setThumbBounds(0, 0, 3, 10);

		// trackHighlightColor = Color.GREEN;

	}

	/**
	 * 设置滚动条的宽度
	 */

	@Override
	public Dimension getPreferredSize(JComponent c) {

		// TODO Auto-generated method stub

		c.setPreferredSize(new Dimension(20, 0));

		return super.getPreferredSize(c);

	}


	// 重绘滑块的滑动区域背景
	@Override
	public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {

		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gp = null;

		//判断滚动条是垂直的 还是水平的

		if (this.scrollbar.getOrientation() == JScrollBar.VERTICAL) {

			//设置画笔

			gp = new GradientPaint(0, 0, new Color(255, 255, 255),

					trackBounds.width, 0, new Color(255, 255, 255));

		}

//		if (this.scrollbar.getOrientation() == JScrollBar.HORIZONTAL) {
//
//			gp = new GradientPaint(0, 0, new Color(80, 80, 80),
//
//					trackBounds.height, 0, new Color(80, 80, 80));
//
//		}


		g2.setPaint(gp);

		//填充Track

		g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width,

				trackBounds.height);

		//绘制Track的边框
		g2.setColor(new Color(128, 128, 128));
		g2.drawRect(trackBounds.x, trackBounds.y, trackBounds.width - 1,
				trackBounds.height - 1);


		if (trackHighlight == BasicScrollBarUI.DECREASE_HIGHLIGHT)

			this.paintDecreaseHighlight(g);

		if (trackHighlight == BasicScrollBarUI.INCREASE_HIGHLIGHT)

			this.paintIncreaseHighlight(g);

	}


	@Override
	protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {

		// 把绘制区的x，y点坐标定义为坐标系的原点

		// 这句一定一定要加上啊，不然拖动就失效了

		g.translate(thumbBounds.x, thumbBounds.y);

		// 设置把手颜色

		g.setColor(new Color(21, 116, 147));

		// 画一个圆角矩形

		// 这里面前四个参数就不多讲了，坐标和宽高

		// 后两个参数需要注意一下，是用来控制角落的圆角弧度

		// g.drawRoundRect(0, 0, 5, thumbBounds.height - 1, 5, 5);

		// 消除锯齿

		Graphics2D g2 = (Graphics2D) g;

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,

				RenderingHints.VALUE_ANTIALIAS_ON);

		g2.addRenderingHints(rh);

		// 半透明

//		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
//
//				0.5f));

		// 设置填充颜色，这里设置了渐变，由下往上

		// g2.setPaint(new GradientPaint(c.getWidth() / 2, 1, Color.GRAY,

		// c.getWidth() / 2, c.getHeight(), Color.GRAY));

		// 填充圆角矩形

		g2.fillRoundRect(1, 1, 18, thumbBounds.height - 2, 5, 5);
	}


	/**
	 * 创建滚动条上方的按钮
	 */

	@Override

	protected JButton createIncreaseButton(int orientation) {
		JButton button = ComponentUtils.getCommonButton("▼");
		button.setSize(getThumbBounds().width, 10);
		return button;

	}

	/**
	 * 创建滚动条下方的按钮
	 */

	@Override

	protected JButton createDecreaseButton(int orientation) {
		JButton button = ComponentUtils.getCommonButton("▲");
		button.setSize(getThumbBounds().width, 10);
		return button;

	}

}