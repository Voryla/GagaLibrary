package com.zwk.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputAdapter;

/**
 * 客户端模板   管理员/用户端
 */
public class CommonFrame extends JFrame {
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private JPanel topBar_panel = new JPanel();
	private JLabel topBar_background_label;
	private JPanel left_sidebar_panel;
	// 拖动窗体时使用
	Point point = new Point(0, 0);
	// 拖动窗体时使用
	private int originX, originY;

	public CommonFrame() {
	}

	public CommonFrame(String string) {
		super();
		initCommonFrame();
		addTitle(string);
	}

	public void initCommonFrame() {
		// 设置图标
		this.setIconImage(new ImageIcon(CommonFrame.class.getResource("/images/topBar_logo.png")).getImage());
		// 去除顶部栏
		this.setUndecorated(true);
		this.setBounds((int) (toolkit.getScreenSize().getWidth() - 1300) / 2,
				(int) (toolkit.getScreenSize().getHeight() - 800) / 2, 1300, 800);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		topBar_panel = new JPanel();
		topBar_panel.setLayout(null);
		topBar_panel.setBounds(0, 0, 1300, 40);
		JButton exit_button = new JButton("X");
		exit_button.setForeground(new Color(244, 244, 244));
		exit_button.setBounds(1265, 0, 35, 40);
		exit_button.setBorder(BorderFactory.createLineBorder(new Color(44, 139, 125), 1));
		exit_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// 除去焦点的文字框
		exit_button.setFocusPainted(false);
		// 除去默认的背景填充
		exit_button.setContentAreaFilled(false);
		exit_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				exit_button.setBackground(new Color(219, 88, 96));
				exit_button.setContentAreaFilled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exit_button.setContentAreaFilled(false);
			}
		});
		// 最小化按钮
		JButton mini_button = new JButton("一");
		mini_button.setForeground(new Color(244, 244, 244));
		mini_button.setBounds(1225, 0, 35, 40);
		mini_button.setBorder(BorderFactory.createLineBorder(new Color(44, 139, 125), 1));
		exit_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// 除去焦点的文字框
		mini_button.setFocusPainted(false);
		// 除去默认的背景填充
		mini_button.setContentAreaFilled(false);
		JFrame that = this;
		mini_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 将窗口最小化
				that.setState(JFrame.ICONIFIED);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				mini_button.setBackground(new Color(51, 145, 120));
				mini_button.setContentAreaFilled(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				mini_button.setContentAreaFilled(false);
			}
		});
		topBar_panel.add(mini_button);
		// 鼠标拖动
		topBar_panel.addMouseListener(new MouseInputAdapter() {
			public void mousePressed(MouseEvent e) {
				originX = e.getX();
				originY = e.getY();
			}
		});
		// 鼠标拖动窗体
		topBar_panel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point point = that.getLocation();
				// 偏移距离
				int offsetX = e.getX() - originX;
				int offsetY = e.getY() - originY;
				that.setLocation(point.x + offsetX, point.y + offsetY);

			}

			@Override
			public void mouseMoved(MouseEvent e) {

			}
		});
		// 标题栏左侧图标
		JLabel topBar_logo_label = new JLabel();
		topBar_logo_label.setOpaque(true);
		topBar_logo_label.setBounds(0, 0, 40, 40);
		topBar_logo_label.setIcon(new ImageIcon(CommonFrame.class.getResource("/images/topBar_logo.png")));
		topBar_panel.add(topBar_logo_label);
		// 标题栏背景
		topBar_background_label = new JLabel();
		topBar_background_label.setOpaque(true);
		topBar_background_label.setBounds(0, 0, 1300, 40);
		topBar_background_label.setIcon(new ImageIcon(CommonFrame.class.getResource("/images/topBar_backGround.png")));
		topBar_panel.add(exit_button);
		topBar_panel.add(topBar_background_label);
		this.add(topBar_panel);
		this.setVisible(true);
	}

	public void addToTopBar(Component component) {
		this.topBar_panel.add(component);
		topBar_panel.add(topBar_background_label);
	}

	public void addTitle(String title) {
		JLabel topBarTitle_label = new JLabel(title);
		topBarTitle_label.setBounds(50, 0, 400, 40);
		topBarTitle_label.setFont(new Font("微软雅黑", Font.BOLD, 24));
		topBarTitle_label.setForeground(Color.white);
		this.addToTopBar(topBarTitle_label);
	}

	/**
	 * 添加侧边栏
	 */
	public void addLeftSidebar() {
		left_sidebar_panel = new JPanel();
		left_sidebar_panel.setLayout(null);
		left_sidebar_panel.setBounds(0, 40, 170, 760);
		left_sidebar_panel.setBackground(new Color(32, 47, 61));
		this.add(left_sidebar_panel);
	}

	public void addToLeftSidebar(Component component) {
		if (null != left_sidebar_panel) {
			left_sidebar_panel.add(component);
			this.setVisible(true);
		}
	}

	public JPanel getLeftSidebarPanel() {
		return this.left_sidebar_panel;
	}
}