package com.zwk.view;

import com.zwk.service.UserService;
import com.zwk.service.UserServiceImpl;
import com.zwk.service.ServiceMessage;
import com.zwk.utils.Log4jInit;
import com.zwk.utils.MyDocument;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import org.apache.log4j.Logger;

/**
 * 用户登录
 */
public class LoginClient extends JFrame {
	public static void main(String[] args) {
		LoginClient loginClient = new LoginClient();
	}

	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	private Properties properties = new Properties();
	private Logger logger = Log4jInit.getLogger(this);
	private UserService userService;
	private boolean isAdminLogin = false;

	public LoginClient() {
		try {
			properties.load(LoginClient.class.getClassLoader().getResourceAsStream("window_config.properties"));
		} catch (IOException e) {
			properties.setProperty("login.state", "user");
			e.printStackTrace();
		}
		logger.info("开始加载");
		// 加载登录页面
		init();
		// 加载服务
		userService = new UserServiceImpl();
	}

	private void init() {
		this.setLayout(null);
		// 设置图标
		this.setIconImage(new ImageIcon(CommonFrame.class.getResource("/images/topBar_logo.png")).getImage());
		// 将窗体设置在屏幕中央
		this.setBounds(((int) toolkit.getScreenSize().getWidth() - 900) / 2,
				((int) toolkit.getScreenSize().getHeight() - 600) / 2, 900, 600);
		this.setFocusable(true);
		// 标题栏设置
		this.setUndecorated(true);
		// 背景面板
		JPanel back_panel = new JPanel();
		// 将背景面板添加至窗体中
		this.add(back_panel);
		// 清空panel的默认布局
		back_panel.setLayout(null);
		// 使用Label装载图片
		JLabel backImg_lab = new JLabel();
		// 设置图片
		backImg_lab.setIcon(new ImageIcon(LoginClient.class.getResource("/images/login_backGround_img.png")));
		// 设置背景图位置及大小
		backImg_lab.setBounds(0, 0, 900, 600);
		// 将label容器加入面板中
		// 设置面板的位置及大小
		back_panel.setBounds(0, 0, 900, 600);
		// 显示系统名称
		JLabel title_lab = new JLabel("Gaga Library");
		title_lab.setForeground(Color.white);
		title_lab.setFont(new Font("微软雅黑", Font.BOLD, 28));
		title_lab.setBounds(365, 90, 200, 50);
		back_panel.add(title_lab);
		// ui 用户登录
		JLabel loginTitle_lab = new JLabel("用户登录");
		loginTitle_lab.setForeground(Color.black);
		loginTitle_lab.setFont(new Font("微软雅黑", Font.BOLD, 26));
		loginTitle_lab.setBounds(595, 235, 200, 100);
		back_panel.add(loginTitle_lab);
		// 用户输入面板
		JPanel input_panel = new JPanel();
		input_panel.setLayout(null);
		input_panel.setBackground(Color.white);
		input_panel.setBorder(BorderFactory.createLineBorder(new Color(44, 139, 126), 1, true));
		input_panel.setBounds(520, 320, 260, 160);
		// 创建提示框
		JLabel message_lab = new JLabel();
		message_lab.setOpaque(true);
		message_lab.setBackground(Color.white);
		message_lab.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		message_lab.setForeground(new Color(219, 88, 96));
		message_lab.setBounds(85, 90, 150, 20);
		input_panel.add(message_lab);
		//  用户名框
		JTextField userName_field = new JTextField(6);
		userName_field.setBackground(new Color(244, 244, 244));
		userName_field.setBounds(55, 10, 190, 30);
		userName_field.setBorder(null);
		userName_field.setDocument(new MyDocument("[0-9]+", 6));
		inputStateJtext(input_panel, userName_field);

		// 密码框
		JPasswordField password_field = new JPasswordField(12);
		password_field.setBounds(55, 60, 190, 30);
		password_field.setBackground(new Color(244, 244, 244));
		password_field.setBorder(null);
		password_field.setDocument(new MyDocument("[_.0-9a-z]+", 12));
		inputStateJtext(input_panel, password_field);
		// 提示框
		JLabel prompt_user_lab = new JLabel("账号");
		prompt_user_lab.setBorder(null);
		prompt_user_lab.setBounds(15, 10, 50, 30);
		prompt_user_lab.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		input_panel.add(prompt_user_lab);
		// 提示框
		JLabel prompt_pwd_lab = new JLabel("密码");
		prompt_pwd_lab.setBorder(null);
		prompt_pwd_lab.setBounds(15, 60, 50, 30);
		prompt_pwd_lab.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		input_panel.add(prompt_pwd_lab);
		// 登录按钮
		JButton login_button = new JButton("登录");
		login_button.setBackground(new Color(57, 207, 136));
		login_button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		login_button.setForeground(Color.white);
		login_button.setBorder(null);
		login_button.setBounds(75, 120, 60, 30);
		login_button.setFocusPainted(false);
		// 设置鼠标移动至button变成手指
		login_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		login_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				login_button.setBackground(new Color(39, 133, 131));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				login_button.setBackground(new Color(57, 207, 136));
			}

			@Override
			public void mouseClicked(MouseEvent e) {

				message_lab.setText("登录中...");
				if (isAdminLogin) {
					userService.loginAsAdmin(userName_field.getText(), String.valueOf(password_field.getPassword()),
							new ServiceMessage() {
								@Override
								public void showMessage(String message, boolean state) {
									message_lab.setText(message);
									if (state) {
									}
								}
							});
				} else {
					if (userName_field.getText().length() < 6) {
						message_lab.setText("请输入6位数字账号");
					} else if (password_field.getPassword().length < 6) {
						message_lab.setText("请输入6-12位密码");
					} else {
						userService.userLogin(userName_field.getText(), String.valueOf(password_field.getPassword()),
								new ServiceMessage() {
									@Override
									public void showMessage(String message, boolean state) {
										message_lab.setText(message);
										if (state) {
											UserClient userClient = new UserClient("Welcome Gaga Library", userService.getCurrentUser());
											setVisible(false);
										}
									}
								});
					}
				}

			}
		});
		input_panel.add(login_button);
		// 注册按钮
		JButton register_button = new JButton("注册");
		register_button.setBackground(new Color(57, 207, 136));
		register_button.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		register_button.setForeground(Color.white);
		register_button.setBorder(null);
		register_button.setBounds(155, 120, 60, 30);
		register_button.setFocusPainted(false);
		register_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		register_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (register_button.isEnabled())
					register_button.setBackground(new Color(39, 133, 131));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (register_button.isEnabled())
					register_button.setBackground(new Color(57, 207, 136));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (userName_field.getText().length() < 6) {
					message_lab.setText("请输入6位数字账号");
				} else if (password_field.getPassword().length < 6) {
					message_lab.setText("请输入6-12位密码");
				} else {
					message_lab.setText("注册中...");
					userService.userRegister(userName_field.getText(), String.valueOf(password_field.getPassword()),
							new ServiceMessage() {
								@Override
								public void showMessage(String message, boolean state) {
									message_lab.setText(message);
								}
							});
				}
			}
		});
		input_panel.add(register_button);
		// 是否使用管理员登录
		JRadioButton is_admin_radio = new JRadioButton("管理员", false);
		is_admin_radio.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		is_admin_radio.setFocusPainted(false);
		is_admin_radio.setOpaque(true);
		is_admin_radio.setBackground(Color.white);
		is_admin_radio.setBounds(13, 90, 70, 20);
		is_admin_radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		is_admin_radio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				register_button.setEnabled(e.getStateChange() != ItemEvent.SELECTED);
				isAdminLogin = e.getStateChange() == ItemEvent.SELECTED;
				if (!isAdminLogin) {
					String name = userName_field.getText();
					String pwd = userName_field.getText();
					userName_field.setDocument(new MyDocument("[0-9]+", 6));
					userName_field.setText(name);
					password_field.setDocument(new MyDocument("[_.0-9a-z]+", 12));
					password_field.setText(pwd);
				} else {
					String name = userName_field.getText();
					userName_field.setDocument(new MyDocument("[_0-9a-z]+", 6));
					userName_field.setText(name);
				}
			}
		});
		input_panel.add(is_admin_radio);
		JButton exit_button = new JButton("X");
		exit_button.setForeground(new Color(244, 244, 244));
		exit_button.setBounds(870, 0, 30, 30);
		exit_button.setBorder(BorderFactory.createLineBorder(new Color(44, 139, 125), 1));
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
		back_panel.add(exit_button);

		back_panel.add(input_panel);
		back_panel.add(backImg_lab);

		// 设置关闭
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void inputStateJtext(JPanel input_panel, JTextField userName_field) {
		userName_field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				userName_field.setBorder(BorderFactory.createLineBorder(new Color(57, 207, 136), 1));
			}

			@Override
			public void focusLost(FocusEvent e) {
				userName_field.setBorder(null);
			}
		});
		input_panel.add(userName_field);
	}

}