package com.zwk.view;

import com.zwk.dao.UserPropertyEnum;
import com.zwk.model.Book;
import com.zwk.model.BorrowBook;
import com.zwk.model.User;
import com.zwk.service.LibraryService;
import com.zwk.service.LibraryServiceImpl;
import com.zwk.service.ServiceMessage;
import com.zwk.service.UserService;
import com.zwk.service.UserServiceImpl;
import com.zwk.utils.ComponentUtils;
import com.zwk.utils.DataViewJTable;
import com.zwk.utils.MyDateFormat;
import com.zwk.utils.MyDocument;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @img from zwk
 */
public class UserClient extends CommonFrame {
	private HashMap<String, JButton> contentMap_button = null;
	private HashMap<String, JPanel> contentMap_panel = null;
	private HashMap<String, JButton> sidebarMap_button = null;
	// ????????????
	private JLayeredPane contentParent_pane;
	// ??????????????????
	private int commonContentX = 1, commonContentY = 45, commonContentWidth, commonContentHeight;
	// ???????????????????????? topBar???button?????????
	private int xOffset = 0;
	private String currentContent = "??????";
	private String topBarCurrentContent = "??????";
	// ????????????
	private JPanel contentTopBar_panel = null;
	// ???????????????
	private LibraryService libraryService;
	// ????????????
	private UserService userService;
	private User currentUser;

	public UserClient(String title, User user) {
		// ???????????????
		super(title);
		this.currentUser = user;
		contentMap_button = new HashMap<>();
		contentMap_panel = new HashMap<>();
		this.init();
		JFrame that = this;
		new Thread() {
			@Override
			public void run() {
				try {
					if (!that.isMinimumSizeSet())
						that.repaint();
					Thread.sleep(300);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}.start();
		libraryService = new LibraryServiceImpl();
		userService = new UserServiceImpl();
		userService.setCurrentUser(currentUser);
	}

	// ???????????????
	public void init() {
		this.setBackground(new Color(242, 242, 242));
		JPanel personalCenter_panel = new JPanel();
		personalCenter_panel.setLayout(null);
		personalCenter_panel.setBounds(1080, 0, 140, 140);
		// ????????????????????????
		personalCenter_panel.setOpaque(false);

		// ??????????????????
		JPanel personalMenu_panel = new JPanel();
		personalMenu_panel.setLayout(null);
		personalMenu_panel.setBounds(940, -10, 84, 91);
		personalMenu_panel.setBorder(BorderFactory.createLineBorder(new Color(54, 148, 117), 2));
		personalMenu_panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		personalMenu_panel.setVisible(false);
		personalMenu_panel.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				personalMenu_panel.setVisible(false);
			}
		});
		personalMenu_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				personalMenu_panel.setVisible(false);
				personalMenu_panel.repaint();
			}
		});

		// ??????????????????--????????????
		JButton item1 = new JButton("????????????");
		item1.setBounds(2, 10, 80, 40);
		this.setMenuItemStyle_button(item1);
		item1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				item1.setBackground(new Color(69, 163, 104));
				personalMenu_panel.setVisible(false);
				addPageToContent("????????????", addPersonalContent());

				if (null != sidebarMap_button.get("??????")) {
					sidebarMap_button.get(topBarCurrentContent).setBackground(new Color(22, 33, 43));
					sidebarMap_button.get(topBarCurrentContent).setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(22, 33, 43)));
					sidebarMap_button.get("????????????").setBackground(new Color(6, 101, 161));
					sidebarMap_button.get("????????????").setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(6, 101, 161)));
					topBarCurrentContent = "????????????";
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				item1.setBackground(new Color(6, 101, 161));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				item1.setBackground(new Color(54, 148, 117));
			}

		});

		// ??????????????????--????????????
		JButton item2 = new JButton("????????????");
		item2.setBounds(2, 50, 80, 40);
		this.setMenuItemStyle_button(item2);
		item2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				item2.setBackground(new Color(69, 163, 104));
				personalMenu_panel.setVisible(false);
				addPageToContent("????????????", addPersonalContent());
				if (null != sidebarMap_button.get("??????")) {
					sidebarMap_button.get(topBarCurrentContent).setBackground(new Color(22, 33, 43));
					sidebarMap_button.get(topBarCurrentContent).setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(22, 33, 43)));
					sidebarMap_button.get("????????????").setBackground(new Color(6, 101, 161));
					sidebarMap_button.get("????????????").setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(6, 101, 161)));
					topBarCurrentContent = "????????????";
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				item2.setBackground(new Color(6, 101, 161));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				item2.setBackground(new Color(54, 148, 117));
			}

		});
		personalMenu_panel.add(item1);
		personalMenu_panel.add(item2);

		// ????????????
		contentParent_pane = new JLayeredPane();
		contentParent_pane.setBounds(170, 40, 1130, 760);
		commonContentWidth = contentParent_pane.getWidth() - 2;
		commonContentHeight = contentParent_pane.getHeight() - 46;
		contentParent_pane.repaint();
		contentParent_pane.setBackground(new Color(242, 242, 242));
		contentParent_pane.setBorder(BorderFactory.createLineBorder(new Color(182, 182, 182), 1));

		contentParent_pane.setOpaque(true);
		contentParent_pane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				personalMenu_panel.setVisible(false);
			}
		});

		// ??????????????? topBar
		contentParent_pane.add(personalMenu_panel, 2, 1);
		contentTopBar_panel = new JPanel();
		contentTopBar_panel.setLayout(null);
		contentTopBar_panel.setBounds(1, 0, contentParent_pane.getWidth() - 2, 40);
		contentTopBar_panel.setBackground(Color.white);
		contentTopBar_panel.setForeground(new Color(99, 99, 99, 255));
		contentTopBar_panel.setOpaque(true);
		contentParent_pane.add(contentTopBar_panel, 1, 0);
		contentParent_pane.repaint();

		// ??????logo
		JLabel personalLogo_label = new JLabel();
		personalLogo_label.setIcon(new ImageIcon(UserClient.class.getResource("/images/topBar_personalCenterLogo.png")));
		personalLogo_label.setBounds(0, 0, 35, 40);

		// ??????label??????
		JLabel userName_label = new JLabel(currentUser.getUserName() + " ???");
		userName_label.setOpaque(false);
		userName_label.setBounds(36, 0, Math.min(userName_label.getText().length() * 10, 100), 40);
		userName_label.setForeground(Color.white);
		userName_label.setFont(new Font("????????????", Font.PLAIN, 14));
		userName_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		userName_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				userName_label.setForeground(new Color(25, 119, 145));
				personalMenu_panel.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				userName_label.setForeground(Color.white);
				if (!(e.getY() > userName_label.getX())) {
					personalMenu_panel.setVisible(false);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				personalMenu_panel.setVisible(!personalMenu_panel.isVisible());
			}
		});
		personalCenter_panel.add(userName_label);
		personalCenter_panel.add(personalLogo_label);
		super.addToTopBar(personalCenter_panel);
		addIndexContent();
		// ?????????????????????
		super.addLeftSidebar();
		// ??????????????????
		initLeftBar();
		this.getContentPane().add(contentParent_pane);
		super.setVisible(true);
		this.setVisible(true);

	}

	// ????????????????????????????????????????????????????????????????????????
	private void setMenuItemStyle_button(JButton menuItem_button) {
		menuItem_button.setForeground(Color.white);
		menuItem_button.setBackground(new Color(54, 148, 117));
		menuItem_button.setFont(new Font("????????????", Font.PLAIN, 12));
		menuItem_button.setBorder(null);
		menuItem_button.setFocusPainted(false);
	}

	// ???????????????????????????
	private int searchOffset = 0;
	// ????????????
	private int limit = 3;
	// ?????????(0-x)
	private int currentPageIndex = 0;
	// ?????????
	private int allPages = 0;
	// ???????????????????????????
	private String currentSearchBookName = "";
	// book?????????
	private int bookCount = 0;
	// ???????????????button?????????map??????key(0-4)
	private int currentButtonKey = 0;
	// ????????????????????????
	private int trustFlag = 2;
	// ???????????????????????????????????? ?????????????????????????????????????????????????????????
	JLabel personal_userTrust;

	// ??????
	private JPanel addIndexContent() {
		// ????????????
		JPanel index_content = new JPanel();
		index_content.setLayout(null);
		index_content.setBackground(Color.white);
		index_content.setBounds(commonContentX, commonContentY, commonContentWidth,
				commonContentHeight);
		// ??????????????????
		JPanel search_panel = new JPanel();
		search_panel.setLayout(null);
		search_panel.setBounds((index_content.getWidth() - 500) / 2, (index_content.getHeight() - 100) / 2,
				500, 50);
		// ?????????
		JTextField search_field = new JTextField();
		search_field.setBounds(0, 0, 350, search_panel.getHeight());
		search_field.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2,
				new Color(32, 47, 61)));
		search_field.setBackground(new Color(242, 242, 242));
		search_field.setFont(new Font("????????????", Font.PLAIN, 14));
		search_field.setBorder(null);
		search_field.setForeground(Color.GRAY);
		search_field.setText("???????????????????????????all");
		search_field.addFocusListener(new FocusAdapter() {
			final String remindText = "???????????????????????????all";

			@Override
			public void focusGained(FocusEvent e) {
				if (search_field.getText().equals(remindText)) {
					search_field.setForeground(Color.black);
					search_field.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (search_field.getText().equals("")) {
					search_field.setForeground(Color.GRAY);
					search_field.setText(remindText);
				}
			}
		});
		search_field.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				// ???????????????????????????????????????????????????
				search_field.setBorder(BorderFactory.createLineBorder(new Color(54, 148, 117), 1));
			}

			@Override
			public void focusLost(FocusEvent e) {
				// ??????????????????????????????
				search_field.setBorder(null);
			}
		});
		// ?????????????????????
		JLabel prompt_label = new JLabel("");
		prompt_label.setFont(new Font("????????????", Font.PLAIN, 14));
		index_content.add(prompt_label);
		// ???????????????enter?????????
		search_field.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (!currentSearchBookName.equals(search_field.getText())) {
						bookCount = libraryService.getSearchCount(search_field.getText());
						if (bookCount > 0) {
							allPages = bookCount / limit + (bookCount % limit == 0 ? 0 : 1);
							prompt_label.setText("??????" + bookCount + "??? " + allPages + "??????\"" + search_field.getText() + "\" ???????????????");
							prompt_label.setForeground(new Color(54, 148, 117));
							searchOffset = 0;
							currentPageIndex = 0;
							searchBook(search_field, prompt_label, search_panel, index_content);
							currentSearchBookName = search_field.getText();
							addPaging(search_field, prompt_label, search_panel, index_content);
						} else {
							prompt_label.setText("?????????\"" + search_field.getText() + "\" ???????????????");
							prompt_label.setForeground(new Color(242, 80, 34));
							// ????????????????????????
							prompt_label.setBounds(search_panel.getX(), search_panel.getY() + search_field.getHeight() + 2,
									prompt_label.getText().length() * 15, 30);
						}
					}
				}
			}
		});
		search_panel.add(search_field);

		// ????????????
		JButton search_button = new JButton("??????");
		search_button.setBounds(350, 0, 150, search_panel.getHeight());
		search_button.setFocusPainted(false);
		search_button.setForeground(Color.white);
		search_button.setBackground(new Color(54, 148, 117));
		search_button.setBorder(null);
		search_button.setFont(new Font("????????????", Font.BOLD, 14));
		search_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		search_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!currentSearchBookName.equals(search_field.getText())) {
					// ???????????????
					bookCount = libraryService.getSearchCount(search_field.getText());
					if (bookCount > 0) {
						allPages = bookCount / limit + (bookCount % limit == 0 ? 0 : 1);
						prompt_label.setText("??????" + bookCount + "??????" + allPages + "??????\"" + search_field.getText() + "\" ???????????????");
						prompt_label.setForeground(new Color(54, 148, 117));
						// ????????????????????????????????????
						searchOffset = 0;
						currentPageIndex = 0;
						searchBook(search_field, prompt_label, search_panel, index_content);
						currentSearchBookName = search_field.getText();
						addPaging(search_field, prompt_label, search_panel, index_content);
					} else {
						prompt_label.setText("?????????\"" + search_field.getText() + "\" ???????????????");
						prompt_label.setForeground(new Color(242, 80, 34));
						// ????????????????????????
						prompt_label.setBounds(search_panel.getX(), search_panel.getY() + search_field.getHeight() + 2,
								prompt_label.getText().length() * 15, 30);
					}
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				search_button.setBackground(new Color(6, 101, 161));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				search_button.setBackground(new Color(54, 148, 117));
			}
		});

		search_panel.add(search_button);
		index_content.add(search_panel);

		return index_content;
	}

	// ????????????
	JPanel pagingPanel = new JPanel();

	private void addPaging(JTextField search_field, JLabel prompt_label, JPanel search_panel, JPanel index_content) {
		// ???????????????????????????????????????buttonKey??????
		currentButtonKey = 0;
		// ??????????????????????????????
		pagingPanel.removeAll();
		// ????????????
		pagingPanel.setLayout(null);
		pagingPanel.setBounds(542, 594, 473, 40);
		HashMap<Integer, JButton> buttonHashMap = new HashMap<>();
		// ???????????????
		JButton downPage_button = ComponentUtils.getCommonButton(">");
		// ???????????????
		JButton upPage_button = ComponentUtils.getCommonButton("<");
		upPage_button.setFont(new Font("????????????", Font.BOLD, 18));
		upPage_button.setBounds(0, 0, 40, 40);
		upPage_button.setEnabled(false);
		upPage_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ????????????????????????
				if (currentPageIndex != 0) {
					// ????????????button???????????????
					buttonHashMap.get(currentButtonKey).setBackground(Color.white);
					buttonHashMap.get(currentButtonKey).setForeground(Color.black);
					// ????????????????????????????????????????????????key????????????
					if (currentButtonKey > 0)
						currentButtonKey -= 1;
					// ?????????????????????????????? ??????????????????text-1??????????????????????????????
					if (currentPageIndex == Integer.parseInt(buttonHashMap.get(0).getText()) - 1) {
						// ?????????????????????-1
						for (int i = 0; i < buttonHashMap.size(); i++) {
							JButton temp_button = buttonHashMap.get(i);
							temp_button.setText(String.valueOf(Integer.parseInt(temp_button.getText()) - 1));
						}
					}
					// ????????????-1
					currentPageIndex -= 1;
					// ?????????????????????????????????limit
					searchOffset -= limit;
					// ????????????
					searchBook(search_field, prompt_label, search_panel, index_content);
					// ?????????????????????
					if (currentPageIndex < allPages - 1) {
						downPage_button.setEnabled(true);
					} else {
						downPage_button.setEnabled(false);
					}
					if (currentPageIndex == 0)
						upPage_button.setEnabled(false);
					else {
						upPage_button.setEnabled(true);
					}
					//
					buttonHashMap.get(currentButtonKey).setBackground(new Color(54, 148, 117));
					buttonHashMap.get(currentButtonKey).setForeground(Color.white);
				}
			}
		});
		// ?????? ??????
		JPanel choicePage_panel = new JPanel();
		choicePage_panel.setLayout(null);
		choicePage_panel.setBounds(upPage_button.getX() + upPage_button.getWidth() + 5, 0,
				40 * Math.min(allPages, 5), 40);
		for (int i = 0; i < (Math.min(allPages, 5)); i++) {
			JButton temp_button = new JButton(String.valueOf(i + 1));
			buttonHashMap.put(i, temp_button);
			temp_button.setBorder(null);
			temp_button.setFocusPainted(false);
			temp_button.setFont(ComponentUtils.defaultFont);
			temp_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			temp_button.setBounds(i * 40, 0, 40, choicePage_panel.getHeight());
			temp_button.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, new Color(242, 242, 242)));
			// ??????????????????????????????button?????????????????????
			if (i == 0) {
				temp_button.setBackground(new Color(54, 148, 117));
				temp_button.setForeground(Color.white);
			} else {
				temp_button.setForeground(Color.black);
				temp_button.setBackground(Color.white);
			}
			// ??????i
			int finalI = i;
			temp_button.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// ???????????????????????????button???????????????
					if (currentPageIndex != Integer.parseInt(temp_button.getText()) - 1) {
						buttonHashMap.get(currentButtonKey).setBackground(Color.white);
						buttonHashMap.get(currentButtonKey).setForeground(Color.black);
						temp_button.setBackground(new Color(54, 148, 117));
						temp_button.setForeground(Color.white);
						currentButtonKey = finalI;
						// ???????????????
						currentPageIndex = Integer.parseInt(temp_button.getText()) - 1;
						System.out.println(currentPageIndex);
						// ?????????????????????????????????????????????????????????
						if (currentPageIndex < allPages - 1) {
							downPage_button.setEnabled(true);
						} else {
							downPage_button.setEnabled(false);
						}
						if (currentPageIndex == 0)
							upPage_button.setEnabled(false);
						else upPage_button.setEnabled(true);
						searchOffset = currentPageIndex * limit;
						searchBook(search_field, prompt_label, search_panel, index_content);
					}

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					temp_button.setBackground(new Color(54, 148, 117));
					temp_button.setForeground(Color.white);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (currentPageIndex != Integer.parseInt(temp_button.getText()) - 1) {
						temp_button.setForeground(Color.black);
						temp_button.setBackground(Color.white);
					}
				}
			});

			choicePage_panel.add(temp_button);
		}
		// ???????????????
		downPage_button.setFont(new Font("????????????", Font.BOLD, 18));
		downPage_button.setBounds(choicePage_panel.getX() + choicePage_panel.getWidth() + 5, 0, 40, 40);
		// allPages????????????????????????1??????  currentPageIndex??????????????????0??????
		if (currentPageIndex < allPages - 1) {
			downPage_button.setEnabled(true);
		} else {
			downPage_button.setEnabled(false);
		}
		downPage_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (currentPageIndex < allPages - 1) {
					buttonHashMap.get(currentButtonKey).setBackground(Color.white);
					buttonHashMap.get(currentButtonKey).setForeground(Color.black);
					if (currentButtonKey < 4)
						currentButtonKey += 1;
					if (currentPageIndex == Integer.parseInt(buttonHashMap.get(buttonHashMap.size() - 1).getText()) - 1) {
						for (int i = 0; i < buttonHashMap.size(); i++) {
							JButton jButton = buttonHashMap.get(i);
							jButton.setText(String.valueOf(Integer.parseInt(jButton.getText()) + 1));
						}
					}
					currentPageIndex += 1;
					searchOffset += limit;
					searchBook(search_field, prompt_label, search_panel, index_content);
					if (currentPageIndex < allPages - 1) {
						downPage_button.setEnabled(true);
					} else {
						downPage_button.setEnabled(false);
					}
					if (currentPageIndex == 0)
						upPage_button.setEnabled(false);
					else upPage_button.setEnabled(true);
					buttonHashMap.get(currentButtonKey).setBackground(new Color(54, 148, 117));
					buttonHashMap.get(currentButtonKey).setForeground(Color.white);
				}
			}
		});
		JPanel jumpPage_panel = new JPanel();
		jumpPage_panel.setLayout(null);
		jumpPage_panel.setOpaque(false);
		jumpPage_panel.setBounds(downPage_button.getWidth() + downPage_button.getX() + 10, 0, 200, pagingPanel.getHeight());
		JLabel jumpTitle_label = new JLabel("??????");
		jumpTitle_label.setFont(ComponentUtils.defaultFont);
		jumpTitle_label.setBounds(0, 0, 30, jumpPage_panel.getHeight());
		jumpPage_panel.add(jumpTitle_label);
		JTextField jumpPage_input = new JTextField();
		jumpPage_input.setBorder(BorderFactory.createLineBorder(new Color(242, 242, 242), 1));
		jumpPage_input.setBounds(jumpTitle_label.getX() + jumpTitle_label.getWidth() + 10, 0, 50, 40);
		jumpPage_input.setFont(ComponentUtils.defaultFont);
		jumpPage_input.setHorizontalAlignment(JTextField.CENTER);
		jumpPage_input.setDocument(new MyDocument("[1-9][0-9]{0," + (String.valueOf(allPages).length() - 1) + "}", allPages));
		jumpPage_input.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				jumpPage_input.setBorder(BorderFactory.createLineBorder(new Color(54, 148, 117), 1));
			}

			@Override
			public void focusLost(FocusEvent e) {
				jumpPage_input.setBorder(BorderFactory.createLineBorder(new Color(242, 242, 242), 1));
			}
		});
		jumpPage_panel.add(jumpPage_input);
		JLabel jumpTitle_label_1 = new JLabel("???");
		jumpTitle_label_1.setFont(ComponentUtils.defaultFont);
		jumpTitle_label_1.setBounds(jumpPage_input.getX() + jumpPage_input.getWidth() + 10, 0, 15, 40);
		jumpPage_panel.add(jumpTitle_label_1);
		JButton jumpPage_button = ComponentUtils.getCommonButton("??????");
		jumpPage_button.setBounds(jumpTitle_label_1.getX() + jumpTitle_label_1.getWidth() + 10, 0, 50, 40);
		jumpPage_panel.add(jumpPage_button);
		jumpPage_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (jumpPage_input.getText().equals("")) return;
				int jumpPage = Integer.parseInt(jumpPage_input.getText()) - 1;
				// ????????????????????????,??????????????????????????????
				if (jumpPage != currentPageIndex && allPages > jumpPage) {
					// ???????????????????????????>=??????????????????????????????,?????????????????????<?????????
					if (Integer.parseInt(buttonHashMap.get(buttonHashMap.size() - 1).getText()) - 1 >= jumpPage &&
							Integer.parseInt(buttonHashMap.get(0).getText()) - 1 <= jumpPage) {
						for (int i = 0; i < buttonHashMap.size(); i++) {
							JButton tempButton = buttonHashMap.get(i);
							if (Integer.parseInt(tempButton.getText()) - 1 == jumpPage) {
								System.out.println(currentButtonKey);
								buttonHashMap.get(currentButtonKey).setBackground(Color.white);
								buttonHashMap.get(currentButtonKey).setForeground(Color.black);
								currentButtonKey = i;
								currentPageIndex = Integer.parseInt(tempButton.getText()) - 1;
								upPage_button.setEnabled(Integer.parseInt(tempButton.getText()) - 1 != 0);
								downPage_button.setEnabled(Integer.parseInt(tempButton.getText()) - 1 != allPages - 1);
								searchOffset = currentPageIndex * limit;
								searchBook(search_field, prompt_label, search_panel, index_content);
								tempButton.setBackground(new Color(54, 148, 117));
								tempButton.setForeground(Color.white);
							}
						}
					} else if (jumpPage <= 4) {// ???????????????5???
						buttonHashMap.get(currentButtonKey).setBackground(Color.white);
						buttonHashMap.get(currentButtonKey).setForeground(Color.black);
						for (int i = 0; i < buttonHashMap.size(); i++) {
							JButton tempButton = buttonHashMap.get(i);
							tempButton.setText(String.valueOf(i + 1));
							if (Integer.parseInt(tempButton.getText()) - 1 == jumpPage) {
								currentPageIndex = Integer.parseInt(tempButton.getText()) - 1;
								upPage_button.setEnabled(Integer.parseInt(tempButton.getText()) - 1 != 0);
								downPage_button.setEnabled(Integer.parseInt(tempButton.getText()) - 1 != allPages - 1);
								currentButtonKey = jumpPage;
								searchOffset = currentPageIndex * limit;
								searchBook(search_field, prompt_label, search_panel, index_content);
								tempButton.setBackground(new Color(54, 148, 117));
								tempButton.setForeground(Color.white);
							}
						}
					} else if (jumpPage >= (allPages - 1 - 5)) {// ??????????????????5???
						buttonHashMap.get(currentButtonKey).setBackground(Color.white);
						buttonHashMap.get(currentButtonKey).setForeground(Color.black);
						for (int i = 0; i < buttonHashMap.size(); i++) {
							JButton tempButton = buttonHashMap.get(i);
							tempButton.setText(String.valueOf(allPages - (5 - i) + 1));
							if (Integer.parseInt(tempButton.getText()) - 1 == jumpPage) {
								currentPageIndex = Integer.parseInt(tempButton.getText()) - 1;
								upPage_button.setEnabled(Integer.parseInt(tempButton.getText()) - 1 != 0);
								downPage_button.setEnabled(Integer.parseInt(tempButton.getText()) - 1 != allPages - 1);
								currentButtonKey = i;
								searchOffset = currentPageIndex * limit;
								searchBook(search_field, prompt_label, search_panel, index_content);
								tempButton.setBackground(new Color(54, 148, 117));
								tempButton.setForeground(Color.white);
							}
						}
					} else {
						buttonHashMap.get(currentButtonKey).setBackground(Color.white);
						buttonHashMap.get(currentButtonKey).setForeground(Color.black);
						for (int i = 0; i < buttonHashMap.size(); i++) {
							JButton tempButton = buttonHashMap.get(i);
							if (i == 2) {
								currentButtonKey = i;
								currentPageIndex = jumpPage;
								upPage_button.setEnabled(true);
								downPage_button.setEnabled(true);
								searchOffset = currentPageIndex * limit;
								searchBook(search_field, prompt_label, search_panel, index_content);
								tempButton.setText(String.valueOf(jumpPage + 1));
								tempButton.setBackground(new Color(54, 148, 117));
								tempButton.setForeground(Color.white);
							} else {
								if (i < 2)
									tempButton.setText(String.valueOf((jumpPage + 1) - (2 + -i)));
								else
									tempButton.setText(String.valueOf((jumpPage + 1) - (2 - i)));
							}
						}
					}
				} else jumpPage_input.setText(" ");
			}
		});
		pagingPanel.add(upPage_button);
		pagingPanel.add(choicePage_panel);
		pagingPanel.add(downPage_button);
		pagingPanel.add(jumpPage_panel);
		index_content.add(pagingPanel);
	}

	// ??????????????????????????????
	private JTable tempTable = new DataViewJTable();
	// ????????????
	private JScrollPane tempScrollPane = new JScrollPane(tempTable);

	// ?????????????????????????????????????????????Enter???????????????????????????????????????????????????????????????
	private void searchBook(JTextField search_field, JLabel prompt_label, JPanel search_panel, JPanel index_content) {
		// ??????????????????????????????
		if (search_field.getText().equals("???????????????????????????all")) {
			prompt_label.setText("?????????????????????");
			prompt_label.setForeground(new Color(242, 80, 34));
		} else {
			// ??????libraryService?????????????????????????????????   ?????????  ???????????????????????? ???????????????????????????????????? ??????
			List<Book> bookList = libraryService.getSearchBook(search_field.getText(), searchOffset, limit,
					new ServiceMessage() {
						@Override
						public void showMessage(String message, boolean state) {
						}
					});
			// ????????????
			if (null == bookList) {

			} else {

				// ?????????
				search_panel.setLocation(search_panel.getX(), 1);
				// ????????????
				String[] columnNames = {"??????", "??????", "??????", "?????????", "????????????", "??????/???", "??????/???"};
				// ??????????????????
				String[][] rowData = new String[bookList.size()][7];
				// ??????
				AtomicInteger i = new AtomicInteger(0);
				// ?????????????????????????????????????????????
				bookList.forEach(book -> {
					rowData[i.getAndAdd(1)] = new String[]{book.getIsbn(), book.getBookName(), book.getBookAuthor(),
							book.getBookPress(), MyDateFormat.getDate(book.getBookPublicationTime(), "yyyy-MM-dd"),
							book.getBookPrice(), book.getBookStock().toString()};
				});
				// ????????????
				TableModel tableModel = new DefaultTableModel(rowData, columnNames);
				// ??????????????????????????????
				tempTable.setModel(tableModel);
				tempTable.setLocation(0, 0);
				tempTable.getColumnModel().getColumn(0).setPreferredWidth(100);
				tempTable.setRowHeight(30);
				tempTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tempTable.setToolTipText("????????????");
				// ??????????????????
				if (tempTable.getMouseListeners().length > 2) {
					tempTable.removeMouseListener(tempTable.getMouseListeners()[2]);
				}
				// ????????????????????????  ??????????????????
				tempTable.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							String[] tempData = rowData[tempTable.getSelectedRow()];
							// ?????????????????????
							JPanel popup_panel = getPersonalCommonJPanel("????????????");
							popup_panel.setBackground(Color.white);
							popup_panel.setBounds((contentParent_pane.getWidth() - 380) / 2,
									(contentParent_pane.getHeight() - 500) / 2, 380, 500);
							// ??????????????????????????????
							JPanel borrowBook_panel = new JPanel();
							borrowBook_panel.setLayout(null);
							borrowBook_panel.setOpaque(false);
							borrowBook_panel.setBounds(0, 40, popup_panel.getWidth(), popup_panel.getHeight() - 40);
							// ???????????????
							JLabel promptInformation_label = new JLabel();
							promptInformation_label.setOpaque(true);
							promptInformation_label.setBounds(1, 0, borrowBook_panel.getWidth() - 2, 40);
							promptInformation_label.setFont(ComponentUtils.defaultFont_bold);
							promptInformation_label.setBackground(new Color(54, 148, 117));
							promptInformation_label.setForeground(Color.white);
							promptInformation_label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
									new Color(242, 242, 242)));
							promptInformation_label.setHorizontalAlignment(JLabel.CENTER);
							promptInformation_label.setVisible(false);
							popup_panel.add(promptInformation_label);
							// ????????????
							JLabel bookInfo_label = new JLabel("????????????");
							bookInfo_label.setBounds(1, 0, borrowBook_panel.getWidth() - 2, 40);
							bookInfo_label.setFont(ComponentUtils.defaultFont);
							bookInfo_label.setHorizontalAlignment(JLabel.CENTER);
							bookInfo_label.setOpaque(true);
							bookInfo_label.setBackground(new Color(242, 242, 242));
							bookInfo_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(bookInfo_label);
							// ????????????
							JLabel bookNumber_label = new JLabel("   ?????????    ");
							bookNumber_label.setBounds(1, 40, borrowBook_panel.getWidth() - 2, 40);
							bookNumber_label.setFont(ComponentUtils.defaultFont);
							bookNumber_label.setText(bookNumber_label.getText() + tempData[0]);
							bookNumber_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(bookNumber_label);
							// ????????????
							JLabel bookName_label = new JLabel("   ?????????   ");
							bookName_label.setBounds(1, 80, borrowBook_panel.getWidth() - 2, 40);
							bookName_label.setFont(ComponentUtils.defaultFont);
							bookName_label.setText(bookName_label.getText() + "???" + tempData[1] + "???");
							bookName_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(bookName_label);
							// ????????????
							JLabel bookAuthor_label = new JLabel("   ?????????    ");
							bookAuthor_label.setBounds(1, 120, borrowBook_panel.getWidth() - 2, 40);
							bookAuthor_label.setFont(ComponentUtils.defaultFont);
							bookAuthor_label.setText(bookAuthor_label.getText() + tempData[2]);
							bookAuthor_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(bookAuthor_label);
							// ????????????
							JLabel bookStock_label = new JLabel("   ?????????    ");
							bookStock_label.setBounds(1, 160, borrowBook_panel.getWidth() - 2, 40);
							bookStock_label.setFont(ComponentUtils.defaultFont);
							bookStock_label.setText(bookStock_label.getText() + tempData[6]);
							bookStock_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(bookStock_label);
							// ????????????
							JLabel borrowInfo_label = new JLabel("????????????");
							borrowInfo_label.setBounds(1, 200, borrowBook_panel.getWidth() - 2, 40);
							borrowInfo_label.setHorizontalAlignment(JLabel.CENTER);
							borrowInfo_label.setFont(ComponentUtils.defaultFont);
							borrowInfo_label.setOpaque(true);
							borrowInfo_label.setBackground(new Color(242, 242, 242));
							borrowInfo_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(borrowInfo_label);
							// ????????????
							JLabel borrowDate_label = new JLabel("   ???????????????");
							borrowDate_label.setBounds(1, 240, borrowDate_label.getText().length() * 12, 40);
							borrowDate_label.setFont(ComponentUtils.defaultFont);
							borrowDate_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							borrowBook_panel.add(borrowDate_label);
							// ????????????
							JTextField borrowDateInput_field = new JTextField();
							borrowDateInput_field.setBounds(borrowDate_label.getWidth() + 1, 245,
									borrowBook_panel.getWidth() - borrowDate_label.getWidth() - 40, 30);
							borrowDateInput_field.setFont(ComponentUtils.defaultFont);
							borrowDateInput_field.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128), 1));
							borrowDateInput_field.setDocument(new MyDocument("[1-9][0-9]{0,1}", 2));
							borrowDateInput_field.addFocusListener(new FocusAdapter() {
								@Override
								public void focusGained(FocusEvent e) {
									borrowDateInput_field.setBorder(BorderFactory.createLineBorder(
											new Color(54, 148, 117), 1));
								}

								@Override
								public void focusLost(FocusEvent e) {
									borrowDateInput_field.setBorder(BorderFactory.createLineBorder(
											new Color(128, 128, 128), 1));
								}
							});
							borrowBook_panel.add(borrowDateInput_field);
							// ??????????????????
							JLabel borrowDateTip_label = new JLabel("???");
							borrowDateTip_label.setBounds(borrowDateInput_field.getX() + borrowDateInput_field.getWidth() + 5,
									borrowDateInput_field.getY(), 15, 30);
							borrowDateTip_label.setFont(ComponentUtils.defaultFont);
							borrowBook_panel.add(borrowDateTip_label);
							// ?????????
							JLabel showUserTrust_label = new JLabel("   ????????????  " + currentUser.getTrust());
							showUserTrust_label.setLayout(null);
							showUserTrust_label.setBounds(1, 280, borrowBook_panel.getWidth(), 40);
							showUserTrust_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0,
									new Color(242, 242, 242)));
							showUserTrust_label.setToolTipText("???????????????????????????,?????????x??? * 2,??????????????????50???????????????,???????????????????????????");
							showUserTrust_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							showUserTrust_label.setFont(ComponentUtils.defaultFont);
							borrowBook_panel.add(showUserTrust_label);
							// ??????????????????????????????????????????
							JLabel deductionTrust_label = new JLabel("");
							deductionTrust_label.setBounds(showUserTrust_label.getText().length() * 9, 280, 40, 40);
							deductionTrust_label.setFont(ComponentUtils.defaultFont);
							deductionTrust_label.setForeground(new Color(54, 148, 117));
							borrowBook_panel.add(deductionTrust_label);

							// ????????????????????????????????????????????????
							borrowDateInput_field.addKeyListener(new KeyAdapter() {
								@Override
								public void keyReleased(KeyEvent e) {
									if (!borrowDateInput_field.getText().isEmpty()) {
										int trust = Integer.parseInt(borrowDateInput_field.getText()) * trustFlag;
										if (trust > 51)
											deductionTrust_label.setForeground(new Color(219, 88, 96));
										else
											deductionTrust_label.setForeground(new Color(54, 148, 117));
										deductionTrust_label.setText("- " + trust);

									} else {
										deductionTrust_label.setText("");
									}
									deductionTrust_label.repaint();
								}
							});
							// ???????????????
							JLabel borrowUserName_label = new JLabel("   ????????????   " + currentUser.getUserName());
							borrowUserName_label.setBounds(1, 320, borrowBook_panel.getWidth(), 40);
							borrowUserName_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
							borrowUserName_label.setFont(ComponentUtils.defaultFont);
							borrowBook_panel.add(borrowUserName_label);
							// ???????????????
							JLabel borrowId_label = new JLabel("   ?????????      " + currentUser.getUserId());
							borrowId_label.setBounds(1, 360, borrowBook_panel.getWidth(), 40);
							borrowId_label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
							borrowId_label.setFont(ComponentUtils.defaultFont);
							borrowBook_panel.add(borrowId_label);
							// ????????????/????????????????????????
							JPanel borrowUserConfirm_panel = new JPanel();
							borrowUserConfirm_panel.setLayout(null);
							borrowUserConfirm_panel.setBounds((borrowBook_panel.getWidth() - 260) / 2, 410, 260, 40);
							borrowUserConfirm_panel.setOpaque(false);
							borrowBook_panel.add(borrowUserConfirm_panel);
							// ??????????????????
							JButton confirmBorrow_button = ComponentUtils.getCommonButton("??????");
							confirmBorrow_button.setBounds(0, 0, 100, 40);
							confirmBorrow_button.setForeground(Color.white);
							confirmBorrow_button.setBackground(new Color(54, 148, 117));
							confirmBorrow_button.setFocusPainted(false);
							confirmBorrow_button.setBorder(null);
							borrowUserConfirm_panel.add(confirmBorrow_button);
							confirmBorrow_button.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									// ?????????????????????
									if (borrowDateInput_field.getText().isEmpty()) {
										promptInformation_label.setForeground(new Color(219, 88, 96));
										showPrompt(promptInformation_label, "??????????????? ????????????????????? ???");
									} else {
										int trust = Integer.parseInt(borrowDateInput_field.getText()) * trustFlag;
										if (libraryService.bookIsBorrow(tempData[0], currentUser.getUserId())) {
											promptInformation_label.setForeground(new Color(219, 88, 96));
											showPrompt(promptInformation_label, "??????????????? ?????????????????? ???");
										} else if (trust > 50) {// ??????????????????>50
											promptInformation_label.setForeground(new Color(219, 88, 96));
											showPrompt(promptInformation_label, "??????????????? ????????????25??? ???");
										} else if (currentUser.getTrust() < trust) {// ???????????????????????????????????????
											promptInformation_label.setForeground(new Color(219, 88, 96));
											showPrompt(promptInformation_label, "??????????????? ??????????????? ???");
										} else {// ???????????????????????????
											// ??????????????????
											Date nowDate = new Date();
											// ????????????????????????
											SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											// ??????????????????
											Calendar calendar = new GregorianCalendar();
											// ??????????????????
											calendar.setTime(nowDate);
											//?????????????????????????????????,??????  ?????????,??????????????????
											calendar.add(Calendar.DATE, Integer.parseInt(borrowDateInput_field.getText()));
											//????????????????????????????????????????????????
											// ?????????????????????
											System.out.println("-----------" + (bookList.get(tempTable.getSelectedRow()).getBookName()));
											libraryService.borrowBook(bookList.get(tempTable.getSelectedRow()), currentUser,
													simpleDateFormat.format(nowDate), simpleDateFormat.format(calendar.getTime()));
											new Thread(() -> {
												// ??????????????????
												showPrompt(promptInformation_label, "??????????????????" + tempData[1] + "??? " + borrowDateInput_field.getText() + "???");
												// ????????????
												searchBook(search_field, prompt_label, search_panel, index_content);
												// ?????????????????????
												currentUser.setTrust((byte) (currentUser.getTrust() - trust));
												// ?????????????????????????????????
												if (null != personal_userTrust)
													personal_userTrust.setText("?????????:   " + currentUser.getTrust());

												System.out.println(userService.updateUserProperty(currentUser.getUserId(),
														UserPropertyEnum.TRUST, currentUser.getTrust(), new ServiceMessage() {
															@Override
															public void showMessage(String message, boolean state) {
															}
														}));
												// ???????????????
												currentUser.setBorrowCount((byte) (currentUser.getBorrowCount() + 1));
												userService.updateUserProperty(currentUser.getUserId(),
														UserPropertyEnum.USER_BORROW_COUNT, currentUser.getBorrowCount(), new ServiceMessage() {
															@Override
															public void showMessage(String message, boolean state) {
															}
														});
												try {
													// ????????????????????????????????????
													Thread.sleep(2000);
												} catch (InterruptedException interruptedException) {
													interruptedException.printStackTrace();
												}
												// ?????????????????????
												contentParent_pane.remove(popup_panel);
												contentParent_pane.remove(mask_panel);
												contentParent_pane.repaint();
											}).start();
										}
									}
								}
							});

							// ??????????????????
							JButton cancelBorrow_button = ComponentUtils.getCommonButton("??????");
							cancelBorrow_button.setBounds(borrowUserConfirm_panel.getWidth() - 100, 0, 100, 40);
							cancelBorrow_button.setForeground(Color.white);
							cancelBorrow_button.setBackground(new Color(54, 148, 117));
							cancelBorrow_button.setFocusPainted(false);
							cancelBorrow_button.setBorder(null);
							borrowUserConfirm_panel.add(cancelBorrow_button);
							cancelBorrow_button.addMouseListener(new MouseAdapter() {
								@Override
								public void mouseClicked(MouseEvent e) {
									contentParent_pane.remove(popup_panel);
									contentParent_pane.remove(mask_panel);
									contentParent_pane.repaint();
								}
							});
							popup_panel.add(borrowBook_panel);
							contentParent_pane.add(popup_panel, 100, 1);
						}
					}
				});

				// ??? ?????? ?????? ???????????? ???????????????????????????????????????????????????
				// 100 200
				tempScrollPane.setBounds((index_content.getWidth() - 900) / 2, 100, 900, index_content.getHeight() - 230);
				tempScrollPane.setBackground(new Color(242, 242, 242));
				// ?????? ???????????? ??? ????????????
				index_content.add(tempScrollPane);
				// ????????????????????????
				index_content.repaint();
			}
		}
		// ????????????????????????
		prompt_label.setBounds(search_panel.getX(), search_panel.getY() + search_field.getHeight() + 2,
				prompt_label.getText().length() * 15, 30);
	}

	// ????????????
	private JPanel addPersonalContent() {
		// y????????????
		int yOffset = 0;
		// ????????????????????????
		JPanel personal_content = new JPanel();
		personal_content.setLayout(null);
		personal_content.setBounds(commonContentX, commonContentY, commonContentWidth,
				commonContentHeight);
		personal_content.setBackground(Color.white);
		// ???????????????
		JLabel personalTitle_label = new JLabel("????????????");
		// ??????????????????
		personalTitle_label.setHorizontalAlignment(SwingConstants.CENTER);
		personalTitle_label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,
				new Color(242, 242, 242)));
		personalTitle_label.setBounds(0, 0, commonContentWidth, 45);
		personalTitle_label.setFont(new Font("????????????", Font.PLAIN, 14));
		// y??????????????????
		yOffset += personalTitle_label.getHeight();
		personal_content.add(personalTitle_label);
		// ??????????????????????????????
		JPanel personalMessage_panel = new JPanel();
		personalMessage_panel.setLayout(null);
		personalMessage_panel.setBounds(0, yOffset, commonContentWidth, 290);
		personalMessage_panel.setBackground(new Color(242, 242, 242));
		personal_content.add(personalMessage_panel);
		// ??????????????????
		JPanel personalImgContent_panel = new JPanel();
		personalImgContent_panel.setLayout(null);
		personalImgContent_panel.setBounds(40, (personalMessage_panel.getHeight() - 250) / 2, 250, 250);
		personalImgContent_panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1,
				1, new Color(242, 242, 242)));
		personalMessage_panel.add(personalImgContent_panel);
		// ????????????
		JLabel personalImg_label = new JLabel();
		personalImg_label.setBounds(0, 0, 250, 250);
		// ??????????????????
		// ???????????????null??????????????????
		if (userService.getCurrentUser().getImg() == null) {
			personalImg_label.setIcon(ComponentUtils.getScaledImage(UserClient.class.getResource("/images/wallHaven-45e9z7.png"), 250, 250));

		} else
			personalImg_label.setIcon(ComponentUtils.getScaledImage(userService.getCurrentUser().getImg(), 250, 250));
		yOffset += 290;
		// ??????????????????
		JLabel alterPersonalImg_label = new JLabel();
		alterPersonalImg_label.setIcon(ComponentUtils.getScaledImage(UserClient.class.getResource("/images/alterPersonalImg_logo1.png"),
				40, 40));
		alterPersonalImg_label.setBounds(personalImg_label.getWidth() - 40, personalImg_label.getHeight() - 40, 40, 40);
		alterPersonalImg_label.setHorizontalAlignment(JLabel.RIGHT);
		alterPersonalImg_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		alterPersonalImg_label.setOpaque(true);
		alterPersonalImg_label.setBackground(new Color(127, 127, 127));
		alterPersonalImg_label.setToolTipText("??????????????????");
		alterPersonalImg_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ??????UI??????
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						UnsupportedLookAndFeelException classNotFoundException) {
					classNotFoundException.printStackTrace();
				}
				// ??????????????????
				JFileChooser jFileChooser = new JFileChooser("C:/Users/ZWK/Pictures");
				jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				jFileChooser.setBackground(new Color(242, 242, 242));
				jFileChooser.setFont(ComponentUtils.defaultFont);
				jFileChooser.addChoosableFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						String fileName = f.getName().toLowerCase();
						return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith("png");
					}

					@Override
					public String getDescription() {
						return "????????????(*.jpg???jpeg???png)";
					}
				});
				int result = jFileChooser.showDialog(personal_content, "????????????");
				// ????????????
				if (result == JFileChooser.APPROVE_OPTION) {
					File file = jFileChooser.getSelectedFile();
					// ????????????????????????????????????????????????
					try {
						personalImg_label.setIcon(ComponentUtils.getScaledImage(file.toURI().toURL(),
								personalImg_label.getWidth(), personalImg_label.getHeight()));
					} catch (MalformedURLException malformedURLException) {
						malformedURLException.printStackTrace();
					}
					// ??????????????????????????????
					userService.changeUserImg(file, new ServiceMessage() {
						@Override
						public void showMessage(String message, boolean state) {
							System.out.println(message);
						}
					});
				}
				// ???????????????????????? ???ui????????????????????????
				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
						UnsupportedLookAndFeelException classNotFoundException) {
					classNotFoundException.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				alterPersonalImg_label.setOpaque(true);
				alterPersonalImg_label.setBackground(new Color(68, 68, 68));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				alterPersonalImg_label.setBackground(new Color(127, 127, 127));
			}
		});
		personalImgContent_panel.add(alterPersonalImg_label);
		// ????????????
		personalImgContent_panel.add(personalImg_label);
		JLabel personal_userName = new JLabel("??????:   ");
		personal_userName.setBounds(personalImg_label.getWidth() + 50, 50,
				(currentUser.getUserName().length() + 2) * 15, 30);
		personal_userName.setText(personal_userName.getText() + currentUser.getUserName());
		personal_userName.setFont(ComponentUtils.defaultFont);
		personalMessage_panel.add(personal_userName);
		// ????????????
		JLabel personal_userId = new JLabel("??????:   ");
		personal_userId.setBounds(personalImg_label.getWidth() + 50, 80,
				(currentUser.getUserId().length() + 2) * 15, 30);
		personal_userId.setText(personal_userId.getText() + currentUser.getUserId());
		personal_userId.setFont(ComponentUtils.defaultFont);
		personalMessage_panel.add(personal_userId);
		// ???????????????
		personal_userTrust = new JLabel("?????????:   ");
		personal_userTrust.setText(personal_userTrust.getText() + currentUser.getTrust());
		personal_userTrust.setBounds(personalImg_label.getWidth() + 50, 110,
				(personal_userTrust.getText().length() + 2) * 15, 30);
		personal_userTrust.setFont(ComponentUtils.defaultFont);
		personalMessage_panel.add(personal_userTrust);
		// ???????????????
		JLabel personal_userBorrowCount = new JLabel("????????????  ");
		personal_userBorrowCount.setText(personal_userBorrowCount.getText() + currentUser.getBorrowCount());
		personal_userBorrowCount.setBounds(personal_userTrust.getX(), personal_userTrust.getY() + personal_userTrust.getHeight(),
				personal_userBorrowCount.getText().length() * 14, 30);
		personal_userBorrowCount.setFont(ComponentUtils.defaultFont);
		personalMessage_panel.add(personal_userBorrowCount);
		// ??????????????????????????????
		JPanel userInfoFunction_panel = ComponentUtils.getFunctionJPanel(UserClient.class.getResource(
				"/images/userInfoFunction_logo.png"),
				"????????????");
		userInfoFunction_panel.setBounds(personal_userName.getX() + Math.max(personal_userId.getWidth(),
				personal_userName.getWidth()) + 150,
				(personalMessage_panel.getHeight() - 200) / 2, 200, 200);
		userInfoFunction_panel.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				userInfoFunction_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				JPanel userDetailedInfoContent_panel = getPersonalCommonJPanel("????????????");
				userDetailedInfoContent_panel.setVisible(true);
				userDetailedInfoContent_panel.setBounds((contentParent_pane.getWidth() - 380) / 2, (contentParent_pane.getHeight() - 500) / 2,
						380, 500);
				// ????????????????????????panel
				JPanel userDetailedInfo_panel = new JPanel();
				userDetailedInfo_panel.setBackground(Color.white);
				userDetailedInfo_panel.setLayout(new GridLayout(7, 1));
				userDetailedInfo_panel.setBounds(1, 40, userDetailedInfoContent_panel.getWidth() - 2,
						userDetailedInfoContent_panel.getHeight() - 40 - 1);
				// ???????????????
				JLabel userName_label = ComponentUtils.getDetailedInfoLabel("   ?????????      " + userService.getCurrentUser().getUserName());
				userDetailedInfo_panel.add(userName_label);
				// ??????id
				JLabel userId_label = ComponentUtils.getDetailedInfoLabel("   id???         " + userService.getCurrentUser().getUserId());
				userDetailedInfo_panel.add(userId_label);
				// ????????????
				JLabel userGender_label = ComponentUtils.getDetailedInfoLabel("   ?????????       " + userService.getCurrentUser().getGender());
				userDetailedInfo_panel.add(userGender_label);
				// ????????????
				JLabel userMajor_label = ComponentUtils.getDetailedInfoLabel("   ?????????       " + userService.getCurrentUser().getMajor());
				userDetailedInfo_panel.add(userMajor_label);
				// ???????????????
				JLabel userBorrowCount_label = ComponentUtils.getDetailedInfoLabel("   ????????????    " + userService.getCurrentUser().getBorrowCount());
				userDetailedInfo_panel.add(userBorrowCount_label);
				// ???????????????
				JLabel userTelephoneNumber_label = ComponentUtils.getDetailedInfoLabel("   ????????????    " + userService.getCurrentUser().getTelephone_number());
				userDetailedInfo_panel.add(userTelephoneNumber_label);
				// ???????????????????????????????????????????????????????????????jlabel???button???????????????????????????
				JLabel jLabel = new JLabel();
				userDetailedInfo_panel.add(jLabel);
				// ??????????????????????????????
				JButton determine_button = new JButton("??????");
				determine_button.setFocusPainted(false);
				determine_button.setBackground(new Color(54, 148, 117));
				determine_button.setForeground(Color.white);
				determine_button.setBorder(null);
				determine_button.setFont(ComponentUtils.defaultFont);
				determine_button.setBounds((userDetailedInfo_panel.getWidth() - 100) / 2, (66 - 40) / 2, 100, 40);
				determine_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				jLabel.add(determine_button);
				determine_button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// ??????????????????????????????
						userDetailedInfoContent_panel.setVisible(false);
						contentParent_pane.remove(userDetailedInfoContent_panel);
						mask_panel.setVisible(false);
						contentParent_pane.remove(mask_panel);
						contentParent_pane.repaint();
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						determine_button.setBackground(new Color(6, 101, 161));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						determine_button.setBackground(new Color(54, 148, 117));
					}
				});
				// ????????????????????????????????????
				userDetailedInfoContent_panel.add(userDetailedInfo_panel);
				contentParent_pane.add(userDetailedInfoContent_panel, 100, 1);
			}
		});
		personalMessage_panel.add(userInfoFunction_panel);
		// ????????????????????????
		JPanel alterUserInfoFunction_panel = ComponentUtils.getFunctionJPanel(UserClient.class.getResource(
				"/images/alterUserInfo_logo.png"),
				"????????????");
		alterUserInfoFunction_panel.setBounds(userInfoFunction_panel.getX() + userInfoFunction_panel.getWidth() + 100,
				(personalMessage_panel.getHeight() - 200) / 2, 200, 200);
		alterUserInfoFunction_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ????????????????????????
				JPanel alterUserInfoContent_panel = getPersonalCommonJPanel("????????????");
				alterUserInfoContent_panel.setVisible(true);
				alterUserInfoContent_panel.setBounds((contentParent_pane.getWidth() - 380) / 2, (contentParent_pane.getHeight() - 500) / 2,
						380, 500);
				// ???????????????
				JLabel promptInformation_label = new JLabel();
				promptInformation_label.setOpaque(true);
				promptInformation_label.setBounds(1, 0, alterUserInfoContent_panel.getWidth() - 2, 40);
				promptInformation_label.setFont(ComponentUtils.defaultFont_bold);
				promptInformation_label.setBackground(new Color(54, 148, 117));
				promptInformation_label.setForeground(Color.white);
				promptInformation_label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
						new Color(242, 242, 242)));
				promptInformation_label.setHorizontalAlignment(JLabel.CENTER);
				promptInformation_label.setVisible(false);
				alterUserInfoContent_panel.add(promptInformation_label);
				// ??????????????????????????????
				JPanel alterDetailedInfo_panel = new JPanel();
				alterDetailedInfo_panel.setBackground(Color.white);
				alterDetailedInfo_panel.setLayout(null);
				alterDetailedInfo_panel.setBounds(1, 40, alterUserInfoContent_panel.getWidth() - 2,
						alterUserInfoContent_panel.getHeight() - 40 - 1);
				alterUserInfoFunction_panel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
				alterUserInfoContent_panel.add(alterDetailedInfo_panel);
				// ??????????????????alterUserName_panel
				JPanel alterUserName_panel = new JPanel();
				alterUserName_panel.setLayout(null);
				alterUserName_panel.setOpaque(false);
				alterUserName_panel.setBounds(0, 0, alterDetailedInfo_panel.getWidth(), 66);
				alterUserName_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
				// ??????????????????label
				JLabel alterUserName_label = new JLabel("   ?????????    ");
				alterUserName_label.setFont(ComponentUtils.defaultFont);
				alterUserName_label.setBounds(0, 0, alterUserName_label.getText().length() * 15, 66);
				alterDetailedInfo_panel.add(alterUserName_panel);
				alterUserName_panel.add(alterUserName_label);
				// ??????????????????input
				JTextField changeUserNameInput = new JTextField();
				// "[\\u4e00-\\u9fa5a-zA-z0-9']{1,15}" ????????????????????????????????????'?????????
				changeUserNameInput.setDocument(new MyDocument("[\\u4e00-\\u9fa5a-zA-z0-9']{1,15}"));
				changeUserNameInput.setText(userService.getCurrentUser().getUserName());
				changeUserNameInput.setBorder(BorderFactory.createLineBorder(new Color(163, 184, 204), 1));
				changeUserNameInput.setBounds(alterUserName_label.getX() + alterUserName_label.getWidth()
						, (alterUserName_panel.getHeight() - 40) / 2, 200, 40);
				alterUserName_panel.add(changeUserNameInput);
				// ???????????????????????????alterGender_panel
				JPanel alterGender_panel = new JPanel();
				alterGender_panel.setLayout(null);
				alterGender_panel.setOpaque(false);
				alterGender_panel.setBounds(0, 66, alterDetailedInfo_panel.getWidth(), 66);
				alterGender_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
				// ????????????label
				JLabel alterGender_label = new JLabel("   ?????????    ");
				alterGender_label.setFont(ComponentUtils.defaultFont);
				alterGender_label.setBounds(0, 0, alterGender_label.getText().length() * 15, 66);
				alterGender_panel.add(alterGender_label);
				// ???????????????
				JComboBox<String> gender_cmb = new JComboBox<>();
				gender_cmb.addItem("??????");
				gender_cmb.addItem("???");
				gender_cmb.addItem("???");
				gender_cmb.setFont(new Font("????????????", Font.BOLD, 14));
				gender_cmb.setBounds(alterGender_label.getX() + alterGender_label.getWidth(), (alterGender_panel.getHeight() - 40) / 2, 200, 40);
				gender_cmb.setBackground(Color.white);
				gender_cmb.setBorder(null);
				gender_cmb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				gender_cmb.setSelectedItem(currentUser.getGender());
				alterGender_panel.add(gender_cmb);
				alterDetailedInfo_panel.add(alterGender_panel);
				// ?????????????????????
				JPanel alterMajor_panel = new JPanel();
				alterMajor_panel.setLayout(null);
				alterMajor_panel.setOpaque(false);
				alterMajor_panel.setBounds(0, 132, alterDetailedInfo_panel.getWidth(), 66);
				alterMajor_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
				// ??????label
				JLabel alterMajor_label = new JLabel("   ?????????    ");
				alterMajor_label.setFont(ComponentUtils.defaultFont);
				alterMajor_label.setBounds(0, 0, alterMajor_label.getText().length() * 15, 66);
				alterMajor_panel.add(alterMajor_label);
				// ?????????????????????
				JComboBox<String> major_cmb = new JComboBox<>();
				major_cmb.addItem("????????????????????????");
				major_cmb.addItem("?????????");
				major_cmb.addItem("????????????");
				major_cmb.addItem("????????????");
				major_cmb.setFont(new Font("????????????", Font.BOLD, 14));
				major_cmb.setBounds(alterMajor_label.getX() + alterMajor_label.getWidth(), (alterMajor_panel.getHeight() - 40) / 2, 200, 40);
				major_cmb.setBackground(Color.white);
				major_cmb.setBorder(null);
				major_cmb.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				major_cmb.setSelectedItem(currentUser.getMajor());
				alterMajor_panel.add(major_cmb);
				alterDetailedInfo_panel.add(alterMajor_panel);
				// ???????????????????????????
				JPanel alterTelephone_panel = new JPanel();
				alterTelephone_panel.setLayout(null);
				alterTelephone_panel.setOpaque(false);
				alterTelephone_panel.setBounds(0, alterMajor_panel.getY() + alterMajor_panel.getHeight()
						, alterDetailedInfo_panel.getWidth(), 66);
				alterTelephone_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
				// ??????????????????label
				JLabel alterTelephone_label = new JLabel("   ????????????   ");
				alterTelephone_label.setFont(ComponentUtils.defaultFont);
				alterTelephone_label.setBounds(0, 0, alterTelephone_label.getText().length() * 15, 66);
				alterTelephone_panel.add(alterTelephone_label);
				// ??????????????????????????????
				JTextField changeTelephoneInput = new JTextField();
				// "[\\u4e00-\\u9fa5a-zA-z0-9']{1,15}" ????????????????????????????????????'?????????
				changeTelephoneInput.setDocument(new MyDocument("\\d{1,11}", 11));
				changeTelephoneInput.setText(userService.getCurrentUser().getTelephone_number());
				changeTelephoneInput.setBorder(BorderFactory.createLineBorder(new Color(163, 184, 204), 1));
				changeTelephoneInput.setBounds(alterTelephone_label.getX() + alterTelephone_label.getWidth()
						, (alterTelephone_panel.getHeight() - 40) / 2, 200, 40);

				alterTelephone_panel.add(changeTelephoneInput);
				JLabel telephoneCheck_label = getCheckLabel();
				telephoneCheck_label.setBounds(changeTelephoneInput.getX() + changeTelephoneInput.getWidth(), changeTelephoneInput.getY(), 20, 40);
				alterTelephone_panel.add(telephoneCheck_label);
				alterDetailedInfo_panel.add(alterTelephone_panel);
				changeTelephoneInput.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						telephoneCheck_label.setVisible(false);
					}
				});
				// ??????????????????????????????
				JPanel alterPwd_panel = new JPanel();
				alterPwd_panel.setLayout(null);
				alterPwd_panel.setOpaque(false);
				alterPwd_panel.setBounds(0, alterTelephone_panel.getY() + alterTelephone_panel.getHeight(), alterDetailedInfo_panel.getWidth(), 132);
				alterPwd_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
				// ???????????????????????????label
				JLabel oldPwdTips_label = new JLabel("   ?????????:   ");
				oldPwdTips_label.setFont(ComponentUtils.defaultFont);
				oldPwdTips_label.setBounds(0, 3, oldPwdTips_label.getText().length() * 15, 40);
				alterPwd_panel.add(oldPwdTips_label);
				// ?????????????????????
				JPasswordField oldPwdInput = new JPasswordField();
				oldPwdInput.setLayout(null);
				oldPwdInput.setDocument(new MyDocument("[_.0-9a-z]+", 12));
				oldPwdInput.setBounds(oldPwdTips_label.getWidth(), 3, 200, 40);
				oldPwdInput.setBorder(BorderFactory.createLineBorder(new Color(163, 184, 204), 1));
				alterPwd_panel.add(oldPwdInput);
				// ??????????????????X???label ????????????????????????????????????
				JLabel oldPwdCheck_label = getCheckLabel();
				oldPwdCheck_label.setBounds(oldPwdInput.getX() + oldPwdInput.getWidth(), oldPwdInput.getY(), 20, 40);
				alterPwd_panel.add(oldPwdCheck_label);
				oldPwdInput.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						oldPwdCheck_label.setVisible(false);
					}
				});
				// ???????????????????????????label ??????????????????????????????????????????
				JLabel showOldPwd_label = new JLabel("????");
				showOldPwd_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showOldPwd_label.setBounds(oldPwdInput.getWidth() - 20, 0, 20, 40);
				showOldPwd_label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						oldPwdInput.setEchoChar((char) 0);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						oldPwdInput.setEchoChar('*');
					}
				});
				oldPwdInput.add(showOldPwd_label);
				// ???????????????????????????
				JLabel newPwdTips_label = new JLabel("   ?????????:   ");
				newPwdTips_label.setFont(ComponentUtils.defaultFont);
				newPwdTips_label.setBounds(0, oldPwdInput.getHeight() + 2, newPwdTips_label.getText().length() * 15, 40);
				alterPwd_panel.add(newPwdTips_label);
				// ??????????????????
				JPasswordField newPwdInput = new JPasswordField();
				newPwdInput.setLayout(null);
				newPwdInput.setDocument(new MyDocument("[_.0-9a-z]+", 12));
				newPwdInput.setBounds(newPwdTips_label.getWidth(), oldPwdInput.getHeight() + 2, 200, 40);
				newPwdInput.setBorder(BorderFactory.createLineBorder(new Color(163, 184, 204), 1));
				alterPwd_panel.add(newPwdInput);
				// ???????????????
				JLabel newPwdCheck_label = getCheckLabel();
				newPwdCheck_label.setBounds(newPwdInput.getX() + newPwdInput.getWidth(), newPwdInput.getY(), 20, 40);
				alterPwd_panel.add(newPwdCheck_label);
				oldPwdInput.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						newPwdCheck_label.setVisible(false);
					}
				});
				// ??????????????????????????????
				JLabel showNewPwd_label = new JLabel("????");
				showNewPwd_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showNewPwd_label.setBounds(newPwdInput.getWidth() - 20, 0, 20, 40);
				showNewPwd_label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						// ????????????
						newPwdInput.setEchoChar((char) 0);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						// ????????????
						newPwdInput.setEchoChar('*');
					}
				});
				newPwdInput.add(showNewPwd_label);
				// ?????????????????????
				JLabel enterNwePwdTips_label = new JLabel("   ???????????????: ");
				enterNwePwdTips_label.setFont(ComponentUtils.defaultFont);
				enterNwePwdTips_label.setBounds(0, newPwdInput.getY() + newPwdInput.getHeight() + 2,
						enterNwePwdTips_label.getText().length() * 15, 40);
				alterPwd_panel.add(enterNwePwdTips_label);
				// ?????????????????????
				JPasswordField enterNewPwdInput = new JPasswordField();
				enterNewPwdInput.setLayout(null);
				enterNewPwdInput.setDocument(new MyDocument("[_.0-9a-z]+", 12));
				enterNewPwdInput.setBounds(enterNwePwdTips_label.getWidth(), newPwdInput.getY() + newPwdInput.getHeight() + 2,
						200, 40);
				enterNewPwdInput.setBorder(BorderFactory.createLineBorder(new Color(163, 184, 204), 1));
				alterPwd_panel.add(enterNewPwdInput);
				JLabel enterNewPwdCheck_label = getCheckLabel();
				enterNewPwdCheck_label.setBounds(enterNewPwdInput.getX() + enterNewPwdInput.getWidth(), enterNewPwdInput.getY(),
						20, 40);
				alterPwd_panel.add(enterNewPwdCheck_label);
				oldPwdInput.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						enterNewPwdCheck_label.setVisible(false);
					}
				});
				// ?????????????????????????????????????????????
				JLabel showEnterNewPwd_label = new JLabel("????");
				showEnterNewPwd_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				showEnterNewPwd_label.setBounds(enterNewPwdInput.getWidth() - 20, 0, 20, 40);
				showEnterNewPwd_label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						enterNewPwdInput.setEchoChar((char) 0);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						enterNewPwdInput.setEchoChar('*');
					}
				});
				enterNewPwdInput.add(showEnterNewPwd_label);
				// ????????????
				JButton saveChange_button = new JButton("??????");
				saveChange_button.setFocusPainted(false);
				saveChange_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				saveChange_button.setBorder(null);
				int temp = alterPwd_panel.getY() + alterPwd_panel.getHeight();
				saveChange_button.setBounds((alterDetailedInfo_panel.getWidth() - 250) / 2,
						(alterDetailedInfo_panel.getHeight() - temp - 40) / 2 + temp,
						100, 40);
				saveChange_button.setBackground(new Color(54, 148, 117));
				saveChange_button.setFont(ComponentUtils.defaultFont);
				saveChange_button.setForeground(Color.white);
				alterDetailedInfo_panel.add(saveChange_button);
				// ????????????????????????
				saveChange_button.addMouseListener(new MouseAdapter() {
					// ??????????????????????????????
					boolean isAdopt = false;

					@Override
					public void mouseClicked(MouseEvent e) {
						currentUser = userService.getCurrentUser();
						// ???????????????
						if (!currentUser.getUserName().equals(changeUserNameInput.getText())) {
							currentUser.setUserName(changeUserNameInput.getText());
							userService.updateUserProperty(currentUser.getUserId(),
									UserPropertyEnum.USER_NAME, changeUserNameInput.getText(), new ServiceMessage() {
										@Override
										public void showMessage(String message, boolean state) {
											isAdopt = state;
											if (isAdopt) personal_userName.setText(currentUser.getUserName());
										}
									});
						}
						// ??????????????????
						if (!currentUser.getGender().equals(Objects.requireNonNull(gender_cmb.getSelectedItem()).toString())) {
							currentUser.setGender(gender_cmb.getSelectedItem().toString());
							userService.updateUserProperty(currentUser.getUserId(),
									UserPropertyEnum.USER_GENDER, gender_cmb.getSelectedItem().toString(), new ServiceMessage() {
										@Override
										public void showMessage(String message, boolean state) {
											isAdopt = state;
										}
									});
						}
						// ??????????????????
						if (!currentUser.getMajor().equals(Objects.requireNonNull(major_cmb.getSelectedItem()).toString())) {
							currentUser.setMajor(major_cmb.getSelectedItem().toString());
							userService.updateUserProperty(currentUser.getUserId(),
									UserPropertyEnum.USER_MAJOR, major_cmb.getSelectedItem().toString(), new ServiceMessage() {
										@Override
										public void showMessage(String message, boolean state) {
											isAdopt = state;
										}
									});
						}
						// ?????????????????????
						if (Pattern.matches("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$", changeTelephoneInput.getText())) {
							currentUser.setTelephone_number(changeTelephoneInput.getText());
							if (!changeTelephoneInput.getText().equals(currentUser.getTelephone_number())) {
								telephoneCheck_label.setVisible(true);
								telephoneCheck_label.setForeground(new Color(54, 148, 117));
								telephoneCheck_label.setText("???");
								telephoneCheck_label.setToolTipText("?????????????????????");
								userService.updateUserProperty(currentUser.getUserId(),
										UserPropertyEnum.USER_TELEPHONE, changeTelephoneInput.getText(), new ServiceMessage() {
											@Override
											public void showMessage(String message, boolean state) {
												isAdopt = state;
											}
										});
							}
						} else {
							telephoneCheck_label.setVisible(true);
							telephoneCheck_label.setForeground(new Color(219, 88, 96));
							telephoneCheck_label.setText("X");
							showPrompt(promptInformation_label, "???????????????????????????");
							telephoneCheck_label.setToolTipText("??????????????????????????????????????????");
							isAdopt = false;
						}
//						 ????????????
						oldPwdInput.getPassword();
						if (String.valueOf(oldPwdInput.getPassword()).equals(userService.getUserPwd(currentUser.getUserId(), new ServiceMessage() {
							@Override
							public void showMessage(String message, boolean state) {
							}
						}))) {
							if (String.valueOf(newPwdInput.getPassword()).equals(String.valueOf(enterNewPwdInput.getPassword()))) {
								isAdopt = true;
								userService.updateUserProperty(currentUser.getUserId(), UserPropertyEnum.USER_PWD, String.valueOf(newPwdInput.getPassword()), new ServiceMessage() {
									@Override
									public void showMessage(String message, boolean state) {
										isAdopt = state;
									}
								});
							} else {
								enterNewPwdCheck_label.setForeground(new Color(219, 88, 96));
								enterNewPwdCheck_label.setText("X");
								enterNewPwdCheck_label.setVisible(true);
								enterNewPwdCheck_label.setToolTipText("?????????????????????");
								showPrompt(promptInformation_label, "????????????????????????????????????");
								isAdopt = false;
							}
						} else {
							if (!(oldPwdInput.getPassword().length < 1)) {
								oldPwdCheck_label.setForeground(new Color(219, 88, 96));
								oldPwdCheck_label.setText("X");
								oldPwdCheck_label.setVisible(true);
								enterNewPwdCheck_label.setToolTipText("???????????????");
								showPrompt(promptInformation_label, "?????????????????????????????????");
								isAdopt = false;
							}
						}
						// ???????????????????????????????????????
						if (isAdopt) {
							new Thread(() -> {
								try {
									showPrompt(promptInformation_label, "??????????????????");
									Thread.sleep(1400);
									contentParent_pane.remove(alterUserInfoContent_panel);
									mask_panel.setVisible(false);
									contentParent_pane.remove(mask_panel);

								} catch (InterruptedException interruptedException) {
									interruptedException.printStackTrace();
								}
							}).start();

						}else showPrompt(promptInformation_label, "??????????????????????????? ");
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						saveChange_button.setBackground(new Color(6, 101, 161));
					}

					@Override
					public void mouseExited(MouseEvent e) {
						saveChange_button.setBackground(new Color(54, 148, 117));
					}
				});
				// ?????????????????????????????????
				JButton saveCancel_button = new JButton("??????");
				saveCancel_button.setFocusPainted(false);
				saveCancel_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				saveCancel_button.setBorder(null);
				saveCancel_button.setBounds(saveChange_button.getX() + saveChange_button.getWidth() + 50,
						saveChange_button.getY(), 100, 40);
				saveCancel_button.setBackground(new Color(54, 148, 117));
				saveCancel_button.setFont(ComponentUtils.defaultFont);
				saveCancel_button.setForeground(Color.white);
				alterDetailedInfo_panel.add(saveCancel_button);
				saveCancel_button.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						alterUserInfoContent_panel.setVisible(false);
						contentParent_pane.remove(alterUserInfoContent_panel);
						mask_panel.setVisible(false);
						contentParent_pane.remove(mask_panel);
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						saveCancel_button.setBackground(new Color(6, 101, 161));

					}

					@Override
					public void mouseExited(MouseEvent e) {
						saveCancel_button.setBackground(new Color(54, 148, 117));
					}
				});
				alterDetailedInfo_panel.add(alterPwd_panel);

				contentParent_pane.add(alterUserInfoContent_panel, 100, 1);
				// ?????????????????????user????????????
				//"^1[3456789]d{9}$"changeTelephoneInput
			}
		});
		personalMessage_panel.add(alterUserInfoFunction_panel);

		// ???????????????
		JLabel libraryTitle_label = new JLabel("????????????");
		// ??????????????????
		libraryTitle_label.setHorizontalAlignment(SwingConstants.CENTER);
		libraryTitle_label.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0,
				new Color(242, 242, 242)));
		libraryTitle_label.setBounds(0, yOffset, commonContentWidth, 45);
		libraryTitle_label.setFont(new Font("????????????", Font.PLAIN, 14));
		personal_content.add(libraryTitle_label);
		yOffset += 45;
		// ?????????????????????
		JPanel libraryRelevant_panel = new JPanel();
		libraryRelevant_panel.setLayout(null);
		libraryRelevant_panel.setBounds(0, yOffset, commonContentWidth, 290);
		libraryRelevant_panel.setBackground(new Color(242, 242, 242));
		personal_content.add(libraryRelevant_panel);
		// ??????????????????
		JPanel myBookBagFunction_panel = ComponentUtils.getFunctionJPanel(UserClient.class.getResource(
				"/images/myBookPag_logo.png"),
				"????????????");
		myBookBagFunction_panel.setBounds(personalImgContent_panel.getX(),
				(personalMessage_panel.getHeight() - 200) / 2, 200, 200);
		libraryRelevant_panel.add(myBookBagFunction_panel);
		// ??????????????????
		JPanel userBookBagContent_panel = new JPanel();
		userBookBagContent_panel.setLayout(null);
		userBookBagContent_panel.setBounds(290, (libraryRelevant_panel.getHeight() - 260) / 2,
				alterUserInfoFunction_panel.getWidth() + alterUserInfoFunction_panel.getX() - 290, 260);
		userBookBagContent_panel.setBackground(Color.white);
		userBookBagContent_panel.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128), 1));
		userBookBagContent_panel.setVisible(false);
		libraryRelevant_panel.add(userBookBagContent_panel);
		// ????????????
		JTable userBookPag_table = new DataViewJTable();
		// ??????????????????
		userBookPag_table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		// ???????????????????????????
		userBookPag_table.getTableHeader().setReorderingAllowed(false);
		JScrollPane jScrollPane = new JScrollPane(userBookPag_table);
		// ?????????????????????
		jScrollPane.getVerticalScrollBar().setUI(ComponentUtils.getDemoScrollBarUI());
		// ????????????????????????
		jScrollPane.setBounds(1, 1, (int) (userBookBagContent_panel.getWidth() * 0.8),
				userBookBagContent_panel.getHeight() - 2);
		jScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(122, 138, 153)));
		jScrollPane.setBackground(new Color(242, 242, 242));
		userBookBagContent_panel.add(jScrollPane);
		// ?????????????????????????????????????????????
		JPanel selectedBook_panel = new JPanel();
		selectedBook_panel.setLayout(null);
		selectedBook_panel.setOpaque(false);
		selectedBook_panel.setBounds(jScrollPane.getX() + jScrollPane.getWidth(),
				jScrollPane.getY(), userBookBagContent_panel.getWidth() - jScrollPane.getWidth() - 2,
				jScrollPane.getHeight());
		// ??????
		JLabel showTipBook_Label = new JLabel("????????????:");
		showTipBook_Label.setFont(ComponentUtils.defaultFont);
		showTipBook_Label.setBounds(0, 0, selectedBook_panel.getWidth(), 30);
		showTipBook_Label.setHorizontalAlignment(JLabel.CENTER);
		selectedBook_panel.add(showTipBook_Label);
		// ?????????????????????????????????
		JLabel showCurrentBookName = new JLabel();
		showCurrentBookName.setFont(ComponentUtils.defaultFont);
		showCurrentBookName.setHorizontalAlignment(JLabel.CENTER);
		showCurrentBookName.setBounds(0, showTipBook_Label.getY() + showTipBook_Label.getHeight(), selectedBook_panel.getWidth(), 40);
		showCurrentBookName.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(242, 242, 242)));
		selectedBook_panel.add(showCurrentBookName);
		// ??????????????????
		JLabel showBorrowDateTip_label = new JLabel("????????????");
		showBorrowDateTip_label.setFont(ComponentUtils.defaultFont);
		showBorrowDateTip_label.setHorizontalAlignment(JLabel.CENTER);
		showBorrowDateTip_label.setBounds(0, showCurrentBookName.getY() + showCurrentBookName.getHeight(),
				selectedBook_panel.getWidth(), 33);
		selectedBook_panel.add(showBorrowDateTip_label);
		// ??????????????????
		JLabel showBorrowDate_label = new JLabel();
		showBorrowDate_label.setFont(ComponentUtils.defaultFont);
		showBorrowDate_label.setHorizontalAlignment(JLabel.CENTER);
		showBorrowDate_label.setBounds(0, showBorrowDateTip_label.getY() + showBorrowDateTip_label.getHeight(),
				selectedBook_panel.getWidth(), 40);
		showBorrowDate_label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(242, 242, 242)));
		selectedBook_panel.add(showBorrowDate_label);
		// ??????????????????
		JLabel showReturnDateTip_label = new JLabel("????????????");
		showReturnDateTip_label.setFont(ComponentUtils.defaultFont);
		showReturnDateTip_label.setHorizontalAlignment(JLabel.CENTER);
		showReturnDateTip_label.setBounds(0, showBorrowDate_label.getY() + showBorrowDate_label.getHeight(),
				selectedBook_panel.getWidth(), 33);
		selectedBook_panel.add(showReturnDateTip_label);
		// ??????????????????
		JLabel showReturnDate_label = new JLabel();
		showReturnDate_label.setFont(ComponentUtils.defaultFont);
		showReturnDate_label.setHorizontalAlignment(JLabel.CENTER);
		showReturnDate_label.setBounds(0, showReturnDateTip_label.getY() + showReturnDateTip_label.getHeight(),
				selectedBook_panel.getWidth(), 40);
		showReturnDate_label.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(242, 242, 242)));

		selectedBook_panel.add(showReturnDate_label);
		// ??????
		JButton returnBook_button = ComponentUtils.getCommonButton("??????");
		returnBook_button.setBounds(1, showReturnDate_label.getY() + showReturnDate_label.getHeight(),
				selectedBook_panel.getWidth() / 2 - 1, 40);
		returnBook_button.setEnabled(false);
		returnBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		selectedBook_panel.add(returnBook_button);
		// ??????
		JButton renewBook_button = ComponentUtils.getCommonButton("??????");
		renewBook_button.setBounds(selectedBook_panel.getWidth() / 2, returnBook_button.getY(),
				selectedBook_panel.getWidth() / 2 - 1, 40);
		renewBook_button.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(242, 242, 242)));
		renewBook_button.setEnabled(false);
		renewBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		selectedBook_panel.add(renewBook_button);
		myBookBagFunction_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ????????????????????????????????????
				if (currentUser.getBorrowCount() != 0) {
					returnBook_button.setEnabled(false);
					returnBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					renewBook_button.setEnabled(false);
					renewBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					List<BorrowBook> borrowBooks = libraryService.getUserBorrowBooks(currentUser.getUserId(), new ServiceMessage() {
						@Override
						public void showMessage(String message, boolean state) {
						}
					});
					String[] columnNames = {"??????", "??????", "??????", "??????/???", "????????????", "????????????"};
					String[][] rowData = new String[borrowBooks.size()][8];
					AtomicInteger count = new AtomicInteger(0);
					borrowBooks.forEach((borrowBook) -> {
						rowData[count.getAndAdd(1)] = new String[]{borrowBook.getIsbn(), borrowBook.getBookName(),
								borrowBook.getBookAuthor(),
								borrowBook.getBookPrice(),
								MyDateFormat.getDate(borrowBook.getBorrowTime(), "yy-MM-dd hh:mm"),
								MyDateFormat.getDate(borrowBook.getDueTime(), "yy-MM-dd hh:mm")
						};
					});
					// ???????????????????????????
					userBookPag_table.setModel(new DefaultTableModel(rowData, columnNames));
					// ????????????
					userBookPag_table.setRowHeight(25);
					// ????????????????????? ??????
					userBookPag_table.getColumnModel().getColumn(0).setPreferredWidth(100);
					userBookPag_table.getColumnModel().getColumn(4).setPreferredWidth(100);
					userBookPag_table.getColumnModel().getColumn(5).setPreferredWidth(100);
					// ????????????
					// ????????????????????????????????????click??????
					if (userBookPag_table.getMouseListeners().length > 2) {
						userBookPag_table.removeMouseListener(userBookPag_table.getMouseListeners()[2]);
					}
					// ??????????????????????????????????????????
					if (returnBook_button.getMouseListeners().length > 1) {
						returnBook_button.removeMouseListener(returnBook_button.getMouseListeners()[1]);
					}
					// ????????????
					returnBook_button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							// ??????????????????
							if (userBookPag_table.getSelectedRow() != -1) {
								// ???????????????
								String[] tempRow = rowData[userBookPag_table.getSelectedRow()];
								// ???????????????
								JPanel returnBookCommon_Panel = getPersonalCommonJPanel("????????????");
								returnBookCommon_Panel.setLocation((contentParent_pane.getWidth() - returnBookCommon_Panel.getWidth()) / 2,
										(contentParent_pane.getHeight() - returnBookCommon_Panel.getHeight()) / 2);
								JLabel promptInformation_label = new JLabel();
								promptInformation_label.setOpaque(true);
								promptInformation_label.setBounds(1, 0, returnBookCommon_Panel.getWidth() - 2, 40);
								promptInformation_label.setFont(ComponentUtils.defaultFont_bold);
								promptInformation_label.setBackground(new Color(54, 148, 117));
								promptInformation_label.setForeground(Color.white);
								promptInformation_label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
										new Color(242, 242, 242)));
								promptInformation_label.setHorizontalAlignment(JLabel.CENTER);
								promptInformation_label.setVisible(false);
								returnBookCommon_Panel.add(promptInformation_label);
								// ??????????????????
								JPanel returnBookContent_panel = new JPanel();
								returnBookContent_panel.setLayout(null);
								returnBookContent_panel.setBounds(1, 40,
										returnBookCommon_Panel.getWidth() - 2, returnBookCommon_Panel.getHeight() - 40 - 1);
								returnBookContent_panel.setBackground(Color.white);
								returnBookCommon_Panel.add(returnBookContent_panel);
								// ?????????
								JPanel returnBookIsbn_panel = new JPanel();
								returnBookIsbn_panel.setLayout(null);
								returnBookIsbn_panel.setBounds(0, 0, returnBookContent_panel.getWidth(), 55);
								returnBookIsbn_panel.setBackground(Color.white);
								returnBookIsbn_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookIsbn_panel);
								// ??????
								JLabel returnBookIsbnTip_label = new JLabel("   ?????????    ");
								returnBookIsbnTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookIsbnTip_label.setBounds(0, 0,
										returnBookIsbnTip_label.getText().length() * 10, returnBookIsbn_panel.getHeight());
								returnBookIsbn_panel.add(returnBookIsbnTip_label);
								// ????????????
								JLabel returnBookIsbn_label = new JLabel(tempRow[0]);
								returnBookIsbn_label.setFont(ComponentUtils.defaultFont);
								returnBookIsbn_label.setBounds(returnBookIsbnTip_label.getWidth() + returnBookIsbnTip_label.getX(),
										returnBookIsbnTip_label.getY(), returnBookIsbn_label.getText().length() * 12, returnBookIsbn_panel.getHeight());
								returnBookIsbn_panel.add(returnBookIsbn_label);
								// ?????????
								JPanel returnBookName_panel = new JPanel();
								returnBookName_panel.setLayout(null);
								returnBookName_panel.setBounds(0, returnBookIsbn_panel.getY() + returnBookIsbn_panel.getHeight(),
										returnBookContent_panel.getWidth(), 55);
								returnBookName_panel.setBackground(Color.white);
								returnBookName_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookName_panel);
								// ??????
								JLabel returnBookNameTip_label = new JLabel("   ?????????    ");
								returnBookNameTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookNameTip_label.setBounds(0, 0,
										returnBookNameTip_label.getText().length() * 10, returnBookName_panel.getHeight());
								returnBookName_panel.add(returnBookNameTip_label);
								// ????????????
								JLabel returnBookName_label = new JLabel(tempRow[1]);
								returnBookName_label.setFont(ComponentUtils.defaultFont);
								returnBookName_label.setBounds(returnBookNameTip_label.getWidth() + returnBookNameTip_label.getX(),
										returnBookNameTip_label.getY(), returnBookName_label.getText().length() * 14, returnBookName_panel.getHeight());
								returnBookName_panel.add(returnBookName_label);

								// ?????????
								JPanel returnBookAuthor_panel = new JPanel();
								returnBookAuthor_panel.setLayout(null);
								returnBookAuthor_panel.setBounds(0, returnBookName_panel.getY() + returnBookName_panel.getHeight(),
										returnBookContent_panel.getWidth(), 55);
								returnBookAuthor_panel.setBackground(Color.white);
								returnBookAuthor_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookAuthor_panel);
								// ??????
								JLabel returnBookAuthorTip_label = new JLabel("   ?????????    ");
								returnBookAuthorTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookAuthorTip_label.setBounds(0, 0,
										returnBookAuthorTip_label.getText().length() * 10, returnBookAuthor_panel.getHeight());
								returnBookAuthor_panel.add(returnBookAuthorTip_label);
								// ????????????
								JLabel returnBookAuthor_label = new JLabel(tempRow[2]);
								returnBookAuthor_label.setFont(ComponentUtils.defaultFont);
								returnBookAuthor_label.setBounds(returnBookNameTip_label.getWidth() + returnBookNameTip_label.getX(),
										returnBookNameTip_label.getY(),
										returnBookAuthor_label.getText().length() * 14, returnBookAuthor_panel.getHeight());
								returnBookAuthor_panel.add(returnBookAuthor_label);

								// ?????????
								JPanel returnBookPrice_panel = new JPanel();
								returnBookPrice_panel.setLayout(null);
								returnBookPrice_panel.setBounds(0, returnBookAuthor_panel.getY() + returnBookAuthor_panel.getHeight(),
										returnBookContent_panel.getWidth(), 55);
								returnBookPrice_panel.setBackground(Color.white);
								returnBookPrice_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookPrice_panel);
								// ??????
								JLabel returnBookPriceTip_label = new JLabel("   ?????????    ");
								returnBookPriceTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookPriceTip_label.setBounds(0, 0,
										returnBookPriceTip_label.getText().length() * 10, returnBookPrice_panel.getHeight());
								returnBookPrice_panel.add(returnBookPriceTip_label);
								// ????????????
								JLabel returnBookPrice_label = new JLabel(tempRow[3]);
								returnBookPrice_label.setFont(ComponentUtils.defaultFont);
								returnBookPrice_label.setBounds(returnBookPriceTip_label.getWidth() + returnBookPriceTip_label.getX(),
										returnBookPriceTip_label.getY(),
										returnBookPrice_label.getText().length() * 14, returnBookPrice_panel.getHeight());
								returnBookPrice_panel.add(returnBookPrice_label);

								// ???????????????
								JPanel returnBookBorrowDate_panel = new JPanel();
								returnBookBorrowDate_panel.setLayout(null);
								returnBookBorrowDate_panel.setBounds(0, returnBookPrice_panel.getY() + returnBookPrice_panel.getHeight(),
										returnBookContent_panel.getWidth(), 55);
								returnBookBorrowDate_panel.setBackground(Color.white);
								returnBookBorrowDate_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookBorrowDate_panel);
								// ????????????
								JLabel returnBookBorrowDateTip_label = new JLabel("   ???????????????    ");
								returnBookBorrowDateTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookBorrowDateTip_label.setBounds(0, 0,
										returnBookBorrowDateTip_label.getText().length() * 10, returnBookBorrowDate_panel.getHeight());
								returnBookBorrowDate_panel.add(returnBookBorrowDateTip_label);
								// ??????????????????
								JLabel returnBookBorrowDate_label = new JLabel(tempRow[4]);
								returnBookBorrowDate_label.setFont(ComponentUtils.defaultFont);
								returnBookBorrowDate_label.setBounds(returnBookPriceTip_label.getWidth() + returnBookPriceTip_label.getX(),
										returnBookPriceTip_label.getY(),
										returnBookBorrowDate_label.getText().length() * 14, returnBookBorrowDate_panel.getHeight());
								returnBookBorrowDate_panel.add(returnBookBorrowDate_label);

								// ???????????????
								JPanel returnBookDueDate_panel = new JPanel();
								returnBookDueDate_panel.setLayout(null);
								returnBookDueDate_panel.setBounds(0, returnBookBorrowDate_panel.getY() + returnBookBorrowDate_panel.getHeight(),
										returnBookContent_panel.getWidth(), 55);
								returnBookDueDate_panel.setBackground(Color.white);
								returnBookDueDate_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookDueDate_panel);
								// ????????????
								JLabel returnBookDueDateTip_label = new JLabel("   ???????????????    ");
								returnBookDueDateTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookDueDateTip_label.setBounds(0, 0,
										returnBookDueDateTip_label.getText().length() * 10, returnBookDueDate_panel.getHeight());
								returnBookDueDate_panel.add(returnBookDueDateTip_label);
								// ??????????????????
								JLabel returnBookDueDate_label = new JLabel(tempRow[5]);
								returnBookDueDate_label.setFont(ComponentUtils.defaultFont);
								returnBookDueDate_label.setBounds(returnBookPriceTip_label.getWidth() + returnBookPriceTip_label.getX(),
										returnBookPriceTip_label.getY(),
										returnBookDueDate_label.getText().length() * 14, returnBookBorrowDate_panel.getHeight());
								returnBookDueDate_panel.add(returnBookDueDate_label);
								// ????????????
								JPanel returnBookTrust_panel = new JPanel();
								returnBookTrust_panel.setLayout(null);
								returnBookTrust_panel.setBounds(0, returnBookDueDate_panel.getY() + returnBookDueDate_panel.getHeight(),
										returnBookContent_panel.getWidth(), 55);
								returnBookTrust_panel.setBackground(Color.white);
								returnBookTrust_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								returnBookContent_panel.add(returnBookTrust_panel);
								// ?????????
								JLabel returnBookTrustTip_label = new JLabel("   ????????????    ");
								returnBookTrustTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookTrustTip_label.setBounds(0, 0,
										returnBookTrustTip_label.getText().length() * 10, returnBookDueDate_panel.getHeight());
								returnBookTrust_panel.add(returnBookTrustTip_label);
								// ???????????????
								JLabel returnBookTrust_label = new JLabel();
								returnBookTrust_label.setFont(ComponentUtils.defaultFont);
								returnBookTrust_label.setBounds(returnBookPriceTip_label.getWidth() + returnBookPriceTip_label.getX(),
										returnBookPriceTip_label.getY(),
										0, returnBookBorrowDate_panel.getHeight());
								SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm");
								// ?????????????????????
								int returnTrust = 0;
								try {
									Date borrowDate = dateFormat.parse(tempRow[4]);
									Date dueDate = dateFormat.parse(tempRow[5]);
									int day = (int) (dueDate.getTime() - borrowDate.getTime()) / (24 * 60 * 60 * 1000);
									// ?????????????????????????????????????????????
									if (new Date().getTime() - dueDate.getTime() > 0) {
										returnBookTrust_label.setForeground(new Color(219, 88, 96));
										returnBookTrust_label.setFont(ComponentUtils.defaultFont_bold);
										returnBookTrust_label.setText("?????????????????????" + day * trustFlag + "/" + trustFlag + "=" + day + "?????????");
										returnTrust = day;
									} else {// ?????????
										returnBookTrust_label.setForeground(new Color(54, 148, 117));
										returnBookTrust_label.setFont(ComponentUtils.defaultFont_bold);
										returnBookTrust_label.setText("???????????????????????????" + day * trustFlag + "?????????");
										returnTrust = day * trustFlag;
									}
									// ??????????????????label?????????
									returnBookTrust_label.setSize(returnBookTrust_label.getText().length() * 14, returnBookTrust_label.getHeight());
								} catch (ParseException parseException) {
									parseException.printStackTrace();
								}
								returnBookTrust_panel.add(returnBookTrust_label);
								// ??????/??????????????????
								JPanel operationReturnBookPanel = new JPanel();
								operationReturnBookPanel.setLayout(null);
								operationReturnBookPanel.setBounds(0, returnBookTrust_panel.getY() + returnBookTrust_panel.getHeight(),
										returnBookContent_panel.getWidth(), 75);
								operationReturnBookPanel.setBackground(Color.white);
								returnBookContent_panel.add(operationReturnBookPanel);
								// ??????????????????
								JButton confirmReturnBook_button = ComponentUtils.getCommonButton("??????");
								confirmReturnBook_button.setBounds((operationReturnBookPanel.getWidth() - 250) / 2,
										(operationReturnBookPanel.getHeight() - 40) / 2, 100, 40);
								operationReturnBookPanel.add(confirmReturnBook_button);
								int finalReturnTrust = returnTrust;
								confirmReturnBook_button.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										if (!confirmReturnBook_button.isEnabled()) return;
										// ?????????????????????
										currentUser.setTrust((byte) ((int) currentUser.getTrust() + finalReturnTrust));
										// ???????????????
										userService.updateUserProperty(currentUser.getUserId(),
												UserPropertyEnum.USER_BORROW_COUNT, currentUser.getTrust(), new ServiceMessage() {
													@Override
													public void showMessage(String message, boolean state) {

													}
												});
										// ?????????????????????
										currentUser.setBorrowCount((byte) ((int) currentUser.getBorrowCount() - 1));
										// ???????????????
										userService.updateUserProperty(currentUser.getUserId(),
												UserPropertyEnum.USER_BORROW_COUNT, currentUser.getBorrowCount(), new ServiceMessage() {
													@Override
													public void showMessage(String message, boolean state) {

													}
												});
										// ????????????????????????+1
										libraryService.dueBook(borrowBooks.get(userBookPag_table.getSelectedRow()).getIsbn(), currentUser.getUserId());
										// ????????????
										List<BorrowBook> borrowBooks = libraryService.getUserBorrowBooks(currentUser.getUserId(), new ServiceMessage() {
											@Override
											public void showMessage(String message, boolean state) {
											}
										});
										String[] columnNames = {"??????", "??????", "??????", "??????/???", "????????????", "????????????"};
										String[][] rowData = new String[borrowBooks.size()][8];
										AtomicInteger count = new AtomicInteger(0);
										borrowBooks.forEach((borrowBook) -> {
											rowData[count.getAndAdd(1)] = new String[]{borrowBook.getIsbn(), borrowBook.getBookName(),
													borrowBook.getBookAuthor(),
													borrowBook.getBookPrice(),
													MyDateFormat.getDate(borrowBook.getBorrowTime(), "yy-MM-dd hh:mm"),
													MyDateFormat.getDate(borrowBook.getDueTime(), "yy-MM-dd hh:mm")
											};
										});
										// ????????????????????????
										showCurrentBookName.setText("");
										showBorrowDate_label.setText("");
										showReturnDate_label.setText("");
										renewBook_button.setEnabled(false);
										returnBook_button.setEnabled(false);
										confirmReturnBook_button.setEnabled(false);
										// ???????????????????????????
										userBookPag_table.setModel(new DefaultTableModel(rowData, columnNames));
										// ????????????
										userBookPag_table.setRowHeight(25);
										// ????????????????????? ??????
										userBookPag_table.getColumnModel().getColumn(0).setPreferredWidth(100);
										userBookPag_table.getColumnModel().getColumn(4).setPreferredWidth(100);
										userBookPag_table.getColumnModel().getColumn(5).setPreferredWidth(100);
										showPrompt(promptInformation_label, "????????????????????????" + finalReturnTrust + "??????????????????");
										// ?????????????????????????????????
										personal_userTrust.setText("????????????  " + currentUser.getTrust());
										// ?????????????????????????????????
										personal_userBorrowCount.setText("????????????  " + currentUser.getBorrowCount());
										// ????????????
										new Thread(() -> {
											try {
												Thread.sleep(2000);
												// ???????????????
												contentParent_pane.remove(mask_panel);
												contentParent_pane.remove(returnBookCommon_Panel);
												contentParent_pane.repaint();
											} catch (InterruptedException interruptedException) {
												interruptedException.printStackTrace();
											}
										}).start();
									}
								});
								// ????????????
								JButton cancelReturnBook_button = ComponentUtils.getCommonButton("??????");
								cancelReturnBook_button.setBounds(confirmReturnBook_button.getX() + confirmReturnBook_button.getWidth() + 50,
										confirmReturnBook_button.getY(), 100, 40);
								operationReturnBookPanel.add(cancelReturnBook_button);
								cancelReturnBook_button.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										contentParent_pane.remove(mask_panel);
										contentParent_pane.remove(returnBookCommon_Panel);
										contentParent_pane.repaint();
									}
								});
								// ???????????????????????????????????????
								contentParent_pane.add(returnBookCommon_Panel, 100, 1);
							}

						}
					});
					// ??????????????????????????????????????????
					if (renewBook_button.getMouseListeners().length > 1) {
						renewBook_button.removeMouseListener(renewBook_button.getMouseListeners()[1]);
					}
					// ????????????
					renewBook_button.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							if (userBookPag_table.getSelectedRow() != -1) {
								// ???????????????
								JPanel renewBookCommon_Panel = getPersonalCommonJPanel("????????????");
								renewBookCommon_Panel.setBackground(Color.white);
								renewBookCommon_Panel.setLocation((contentParent_pane.getWidth() - renewBookCommon_Panel.getWidth()) / 2,
										(contentParent_pane.getHeight() - renewBookCommon_Panel.getHeight()) / 2);
								contentParent_pane.add(renewBookCommon_Panel, 100, 1);
								// ???????????????
								String[] tempRow = rowData[userBookPag_table.getSelectedRow()];
								JLabel promptInformation_label = new JLabel();
								promptInformation_label.setOpaque(true);
								promptInformation_label.setBounds(1, 0, renewBookCommon_Panel.getWidth() - 2, 40);
								promptInformation_label.setFont(ComponentUtils.defaultFont_bold);
								promptInformation_label.setBackground(new Color(54, 148, 117));
								promptInformation_label.setForeground(Color.white);
								promptInformation_label.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0,
										new Color(242, 242, 242)));
								promptInformation_label.setHorizontalAlignment(JLabel.CENTER);
								promptInformation_label.setVisible(false);
								renewBookCommon_Panel.add(promptInformation_label);
								// ??????????????????
								JPanel renewBookContent_panel = new JPanel();
								renewBookContent_panel.setLayout(null);
								renewBookContent_panel.setBounds(1, 40,
										renewBookCommon_Panel.getWidth() - 2, renewBookCommon_Panel.getHeight() - 40 - 1);
								renewBookContent_panel.setBackground(Color.white);
								renewBookCommon_Panel.add(renewBookContent_panel);
								// ?????????
								JPanel renewBookIsbn_panel = new JPanel();
								renewBookIsbn_panel.setLayout(null);
								renewBookIsbn_panel.setBounds(0, 0, renewBookContent_panel.getWidth(), 55);
								renewBookIsbn_panel.setBackground(Color.white);
								renewBookIsbn_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookIsbn_panel);
								// ??????
								JLabel returnBookIsbnTip_label = new JLabel("   ?????????    ");
								returnBookIsbnTip_label.setFont(ComponentUtils.defaultFont_bold);
								returnBookIsbnTip_label.setBounds(0, 0,
										returnBookIsbnTip_label.getText().length() * 10, renewBookIsbn_panel.getHeight());
								renewBookIsbn_panel.add(returnBookIsbnTip_label);
								// ????????????
								JLabel returnBookIsbn_label = new JLabel(tempRow[0]);
								returnBookIsbn_label.setFont(ComponentUtils.defaultFont);
								returnBookIsbn_label.setBounds(returnBookIsbnTip_label.getWidth() + returnBookIsbnTip_label.getX(),
										returnBookIsbnTip_label.getY(), returnBookIsbn_label.getText().length() * 12, renewBookIsbn_panel.getHeight());
								renewBookIsbn_panel.add(returnBookIsbn_label);
								// ?????????
								JPanel renewBookName_panel = new JPanel();
								renewBookName_panel.setLayout(null);
								renewBookName_panel.setBounds(0, renewBookIsbn_panel.getY() + renewBookIsbn_panel.getHeight(),
										renewBookContent_panel.getWidth(), 55);
								renewBookName_panel.setBackground(Color.white);
								renewBookName_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookName_panel);
								// ??????
								JLabel renewBookNameTip_label = new JLabel("   ?????????    ");
								renewBookNameTip_label.setFont(ComponentUtils.defaultFont_bold);
								renewBookNameTip_label.setBounds(0, 0,
										renewBookNameTip_label.getText().length() * 10, renewBookName_panel.getHeight());
								renewBookName_panel.add(renewBookNameTip_label);
								// ????????????
								JLabel renewBookName_label = new JLabel(tempRow[1]);
								renewBookName_label.setFont(ComponentUtils.defaultFont);
								renewBookName_label.setBounds(renewBookNameTip_label.getWidth() + renewBookNameTip_label.getX(),
										renewBookNameTip_label.getY(), renewBookName_label.getText().length() * 14, renewBookName_panel.getHeight());
								renewBookName_panel.add(renewBookName_label);

								// ?????????
								JPanel renewBookAuthor_panel = new JPanel();
								renewBookAuthor_panel.setLayout(null);
								renewBookAuthor_panel.setBounds(0, renewBookName_panel.getY() + renewBookName_panel.getHeight(),
										renewBookContent_panel.getWidth(), 55);
								renewBookAuthor_panel.setBackground(Color.white);
								renewBookAuthor_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookAuthor_panel);
								// ??????
								JLabel renewBookAuthorTip_label = new JLabel("   ?????????    ");
								renewBookAuthorTip_label.setFont(ComponentUtils.defaultFont_bold);
								renewBookAuthorTip_label.setBounds(0, 0,
										renewBookAuthorTip_label.getText().length() * 10, renewBookAuthor_panel.getHeight());
								renewBookAuthor_panel.add(renewBookAuthorTip_label);
								// ????????????
								JLabel renewBookAuthor_label = new JLabel(tempRow[2]);
								renewBookAuthor_label.setFont(ComponentUtils.defaultFont);
								renewBookAuthor_label.setBounds(renewBookAuthorTip_label.getWidth() + renewBookAuthorTip_label.getX(),
										renewBookAuthorTip_label.getY(),
										renewBookAuthor_label.getText().length() * 14, renewBookAuthorTip_label.getHeight());
								renewBookAuthor_panel.add(renewBookAuthor_label);
								// ???????????????
								JPanel renewBookBorrowDate_panel = new JPanel();
								renewBookBorrowDate_panel.setLayout(null);
								renewBookBorrowDate_panel.setBackground(Color.white);
								renewBookBorrowDate_panel.setBounds(0, renewBookAuthor_panel.getY() + renewBookAuthor_panel.getHeight(),
										renewBookContent_panel.getWidth(), 55);
								renewBookBorrowDate_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookBorrowDate_panel);
								// ????????????
								JLabel renewBookBorrowDateTip_label = new JLabel("   ???????????????    ");
								renewBookBorrowDateTip_label.setFont(ComponentUtils.defaultFont_bold);
								renewBookBorrowDateTip_label.setBounds(0, 0,
										renewBookBorrowDateTip_label.getText().length() * 10, renewBookBorrowDate_panel.getHeight());
								renewBookBorrowDate_panel.add(renewBookBorrowDateTip_label);
								// ??????????????????
								JLabel renewBookBorrowDate_label = new JLabel(tempRow[4]);
								renewBookBorrowDate_label.setFont(ComponentUtils.defaultFont);
								renewBookBorrowDate_label.setBounds(renewBookAuthor_label.getX(),
										renewBookBorrowDateTip_label.getY(),
										renewBookBorrowDate_label.getText().length() * 14, renewBookBorrowDate_panel.getHeight());
								renewBookBorrowDate_panel.add(renewBookBorrowDate_label);

								// ???????????????
								JPanel renewBookDueDate_panel = new JPanel();
								renewBookDueDate_panel.setLayout(null);
								renewBookDueDate_panel.setBounds(0, renewBookBorrowDate_panel.getY() + renewBookBorrowDate_panel.getHeight(),
										renewBookContent_panel.getWidth(), 55);
								renewBookDueDate_panel.setBackground(Color.white);
								renewBookDueDate_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookDueDate_panel);
								// ????????????
								JLabel renewBookDueDateTip_label = new JLabel("   ???????????????    ");
								renewBookDueDateTip_label.setFont(ComponentUtils.defaultFont_bold);
								renewBookDueDateTip_label.setBounds(0, 0,
										renewBookDueDateTip_label.getText().length() * 10, renewBookDueDate_panel.getHeight());
								renewBookDueDate_panel.add(renewBookDueDateTip_label);
								// ??????????????????
								JLabel renewBookDueDate_label = new JLabel(tempRow[5]);
								renewBookDueDate_label.setFont(ComponentUtils.defaultFont);
								renewBookDueDate_label.setBounds(renewBookAuthor_label.getX(),
										renewBookDueDateTip_label.getY(),
										renewBookDueDate_label.getText().length() * 14, renewBookBorrowDate_panel.getHeight());
								renewBookDueDate_panel.add(renewBookDueDate_label);
								// ???????????????
								JPanel renewBookInput_panel = new JPanel();
								renewBookInput_panel.setLayout(null);
								renewBookInput_panel.setBounds(0, renewBookDueDate_panel.getY() + renewBookDueDate_panel.getHeight(),
										renewBookContent_panel.getWidth(), 55);
								renewBookInput_panel.setBackground(Color.white);
								renewBookInput_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookInput_panel);
								// ????????????
								JLabel renewBookInputTip_label = new JLabel("   ????????????/??????    ");
								renewBookInputTip_label.setFont(ComponentUtils.defaultFont_bold);
								renewBookInputTip_label.setBounds(0, 0,
										renewBookInputTip_label.getText().length() * 10, renewBookDueDate_label.getHeight());
								renewBookInput_panel.add(renewBookInputTip_label);
								// ??????????????????
								JTextField renewBook_input = new JTextField();
								renewBook_input.setBounds(renewBookDueDate_label.getX(), (renewBookInput_panel.getHeight() - 30) / 2,
										220, 30);
								renewBook_input.setFont(ComponentUtils.defaultFont);
								renewBook_input.setBorder(BorderFactory.createLineBorder(new Color(128, 128, 128), 1));
								renewBook_input.setDocument(new MyDocument("[1-9][0-9]{0,1}", 2));
								renewBookInput_panel.add(renewBook_input);
								renewBook_input.addFocusListener(new FocusAdapter() {
									@Override
									public void focusGained(FocusEvent e) {
										renewBook_input.setBorder(BorderFactory.createLineBorder(
												new Color(54, 148, 117), 1));
									}

									@Override
									public void focusLost(FocusEvent e) {
										renewBook_input.setBorder(BorderFactory.createLineBorder(
												new Color(128, 128, 128), 1));
									}
								});
								// ????????????
								JPanel renewBookTrust_panel = new JPanel();
								renewBookTrust_panel.setLayout(null);
								renewBookTrust_panel.setBounds(0, renewBookInput_panel.getY() + renewBookInput_panel.getHeight(),
										renewBookContent_panel.getWidth(), 55);
								renewBookTrust_panel.setBackground(Color.white);
								renewBookTrust_panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								renewBookTrust_panel.setToolTipText("???????????????????????????,?????????x??? * 2,??????????????????50???????????????,???????????????????????????");
								renewBookTrust_panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(242, 242, 242)));
								renewBookContent_panel.add(renewBookTrust_panel);
								// ?????????
								JLabel renewBookTrustTip_label = new JLabel("   ???????????? " + currentUser.getTrust());
								renewBookTrustTip_label.setFont(ComponentUtils.defaultFont_bold);
								renewBookTrustTip_label.setBounds(0, 0,
										renewBookTrustTip_label.getText().length() * 10, renewBookDueDate_label.getHeight());
								renewBookTrust_panel.add(renewBookTrustTip_label);
								// ???????????????
								JLabel renewBookTrust_label = new JLabel();
								renewBookTrust_label.setFont(ComponentUtils.defaultFont);
								renewBookTrust_label.setBounds(renewBookAuthor_label.getX(),
										renewBookTrustTip_label.getY(),
										0, renewBookBorrowDate_panel.getHeight());
								SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd hh:mm");
								renewBook_input.addKeyListener(new KeyAdapter() {
									@Override
									public void keyReleased(KeyEvent e) {
										if (!renewBook_input.getText().isEmpty()) {
											int trust = Integer.parseInt(renewBook_input.getText()) * trustFlag;
											if (trust > 51)
												renewBookTrust_label.setForeground(new Color(219, 88, 96));
											else
												renewBookTrust_label.setForeground(new Color(54, 148, 117));
											renewBookTrust_label.setText("-" + trust);
										} else {
											renewBookTrust_label.setText("");
										}
										renewBookTrust_label.setSize(renewBookTrust_label.getText().length() * 14, renewBookTrust_label.getHeight());
										renewBookTrust_label.repaint();
									}
								});

								renewBookTrust_label.setSize(renewBookTrust_label.getText().length() * 14, renewBookTrust_label.getHeight());
								renewBookTrust_panel.add(renewBookTrust_label);
								// ??????/??????????????????
								JPanel operationRenewBookPanel = new JPanel();
								operationRenewBookPanel.setLayout(null);
								operationRenewBookPanel.setBounds(0, renewBookTrust_panel.getY() + renewBookTrust_panel.getHeight(),
										renewBookContent_panel.getWidth(), 75);
								operationRenewBookPanel.setBackground(Color.white);
								renewBookContent_panel.add(operationRenewBookPanel);
								// ??????????????????
								JButton confirmRenewBook_button = ComponentUtils.getCommonButton("??????");
								confirmRenewBook_button.setBounds((operationRenewBookPanel.getWidth() - 250) / 2,
										(operationRenewBookPanel.getHeight() - 40) / 2, 100, 40);
								operationRenewBookPanel.add(confirmRenewBook_button);
								confirmRenewBook_button.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										if (!confirmRenewBook_button.isEnabled()) return;
										// ?????????????????????
										if (renewBook_input.getText().isEmpty()) {
											promptInformation_label.setForeground(new Color(219, 88, 96));
											showPrompt(promptInformation_label, "??????????????? ????????????????????? ???");
										} else {
											SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
											BorrowBook borrowBook = borrowBooks.get(userBookPag_table.getSelectedRow());
											int trust = Integer.parseInt(renewBook_input.getText()) * trustFlag;
											// ???????????????????????????
											try {
												if (simpleDateFormat.parse(borrowBook.getDueTime()).getTime() < new Date().getTime()) {
													promptInformation_label.setForeground(new Color(219, 88, 96));
													showPrompt(promptInformation_label, "??????????????? ????????????????????????");
												} else if (trust > 50) {// ??????????????????>50
													promptInformation_label.setForeground(new Color(219, 88, 96));
													showPrompt(promptInformation_label, "??????????????? ????????????25??? ???");
												} else if (currentUser.getTrust() < trust) {// ???????????????????????????????????????
													promptInformation_label.setForeground(new Color(219, 88, 96));
													showPrompt(promptInformation_label, "??????????????? ??????????????? ???");
												} else {// ???????????????????????????
													// ??????????????????
													Calendar calendar = new GregorianCalendar();
													// ??????????????????
													calendar.setTime(simpleDateFormat.parse(borrowBook.getDueTime()));
													//?????????????????????????????????,??????  ?????????,??????????????????
													calendar.add(Calendar.DATE, Integer.parseInt(renewBook_input.getText()));
													libraryService.renewBook(borrowBook.getIsbn(), currentUser,
															borrowBook.getBorrowTime(), simpleDateFormat.format(calendar.getTime()));
													confirmRenewBook_button.setEnabled(false);
													// ???????????????????????????
													currentUser.setTrust((byte) ((int) (currentUser.getTrust()) - trust));
													// ?????????????????????
													userService.updateUserProperty(currentUser.getUserId(), UserPropertyEnum.TRUST,
															currentUser.getTrust(), new ServiceMessage() {
																@Override
																public void showMessage(String message, boolean state) {

																}
															});
													// ???????????????????????????????????????
													personal_userTrust.setText(currentUser.getTrust().toString());
													new Thread(() -> {
														// ??????????????????
														showPrompt(promptInformation_label, "??????????????????" + borrowBook.getBookName() + "??? " + renewBook_input.getText() + "???");
														// ????????????
														List<BorrowBook> borrowBooks = libraryService.getUserBorrowBooks(currentUser.getUserId(), new ServiceMessage() {
															@Override
															public void showMessage(String message, boolean state) {
															}
														});
														String[] columnNames = {"??????", "??????", "??????", "??????/???", "????????????", "????????????"};
														String[][] rowData = new String[borrowBooks.size()][8];
														AtomicInteger count = new AtomicInteger(0);
														borrowBooks.forEach((borrowBook1) -> {
															rowData[count.getAndAdd(1)] = new String[]{borrowBook1.getIsbn(), borrowBook1.getBookName(),
																	borrowBook1.getBookAuthor(),
																	borrowBook1.getBookPrice(),
																	MyDateFormat.getDate(borrowBook1.getBorrowTime(), "yy-MM-dd hh:mm"),
																	MyDateFormat.getDate(borrowBook1.getDueTime(), "yy-MM-dd hh:mm")
															};
														});
														// ????????????????????????
														showCurrentBookName.setText("");
														showBorrowDate_label.setText("");
														showReturnDate_label.setText("");
														renewBook_button.setEnabled(false);
														returnBook_button.setEnabled(false);
														confirmRenewBook_button.setEnabled(false);
														// ???????????????????????????
														userBookPag_table.setModel(new DefaultTableModel(rowData, columnNames));
														// ????????????
														userBookPag_table.setRowHeight(25);
														// ????????????????????? ??????
														userBookPag_table.getColumnModel().getColumn(0).setPreferredWidth(100);
														userBookPag_table.getColumnModel().getColumn(4).setPreferredWidth(100);
														userBookPag_table.getColumnModel().getColumn(5).setPreferredWidth(100);
														// ????????????
														// ?????????????????????
														currentUser.setTrust((byte) (currentUser.getTrust() - trust));
														userService.updateUserProperty(currentUser.getUserId(),
																UserPropertyEnum.TRUST, currentUser.getTrust(), new ServiceMessage() {
																	@Override
																	public void showMessage(String message, boolean state) {
																	}
																});
														try {
															// ????????????????????????????????????
															Thread.sleep(2000);
														} catch (InterruptedException interruptedException) {
															interruptedException.printStackTrace();
														}
														// ?????????????????????
														contentParent_pane.remove(renewBookCommon_Panel);
														contentParent_pane.remove(mask_panel);
														contentParent_pane.repaint();
													}).start();
												}
											} catch (ParseException parseException) {
												parseException.printStackTrace();
											}
										}
									}
								});
								// ????????????
								JButton cancelRenewBook_button = ComponentUtils.getCommonButton("??????");
								cancelRenewBook_button.setBounds(confirmRenewBook_button.getX() + confirmRenewBook_button.getWidth() + 50,
										confirmRenewBook_button.getY(), 100, 40);
								operationRenewBookPanel.add(cancelRenewBook_button);
								cancelRenewBook_button.addMouseListener(new MouseAdapter() {
									@Override
									public void mouseClicked(MouseEvent e) {
										contentParent_pane.remove(mask_panel);
										contentParent_pane.remove(renewBookCommon_Panel);
										contentParent_pane.repaint();
									}
								});
							}
						}
					});
					// ??????????????????
					userBookPag_table.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
							String[] tempData = rowData[userBookPag_table.getSelectedRow()];
							showCurrentBookName.setText("???" + tempData[1] + "???");
							showBorrowDate_label.setText(tempData[4]);
							showReturnDate_label.setText(tempData[5]);
							// ???????????????????????????????????????????????????
							if (!renewBook_button.isEnabled()) {
								returnBook_button.setEnabled(true);
								returnBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								renewBook_button.setEnabled(true);
								renewBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							}
						}

						@Override
						public void mouseReleased(MouseEvent e) {
							String[] tempData = rowData[userBookPag_table.getSelectedRow()];
							showCurrentBookName.setText("???" + tempData[1] + "???");
							showBorrowDate_label.setText(tempData[4]);
							showReturnDate_label.setText(tempData[5]);
							// ???????????????????????????????????????????????????
							if (!renewBook_button.isEnabled()) {
								returnBook_button.setEnabled(true);
								returnBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
								renewBook_button.setEnabled(true);
								renewBook_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
							}
						}
					});
					// ??????????????????????????????????????????????????????
					userBookBagContent_panel.add(selectedBook_panel);
					// ??????/????????????
					userBookBagContent_panel.setVisible(!userBookBagContent_panel.isVisible());
				}

			}
		});
		return personal_content;
	}

	// ????????????????????????????????????
	private void addPageToContent(String content_title, JPanel content) {
		// ???????????????????????????
		if (null != contentMap_button.get(content_title)) {
			// ???????????????????????????
			if (!currentContent.equals(content_title)) {
				// ????????????topBar??????button?????????
				contentMap_button.get(currentContent).setBackground(Color.white);
				contentMap_button.get(content_title).setBackground(new Color(242, 242, 242));
				// ?????????????????????
				contentMap_panel.get(currentContent).setVisible(false);
				contentMap_panel.get(content_title).setVisible(true);
				// ????????????????????????
				currentContent = content_title;
				this.repaint();
			}
			return;
		}
		// ?????????tooBar???????????????
		JButton temp_button = new JButton(content_title);
		temp_button.setLayout(null);
		temp_button.setBackground(Color.white);
		temp_button.setForeground(Color.black);
		temp_button.setBounds(xOffset, 0, content_title.length() * 20 + 15, 40);
		// ??????topBar??????X???????????????,?????????????????????button????????????
		xOffset += temp_button.getWidth();
		// ?????????????????????????????????????????????
		temp_button.setFocusPainted(false);
		// ???????????????1????????? ????????????????????????button
		temp_button.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1,
				new Color(242, 242, 242)));
		// ?????????????????????????????????
		temp_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		if (temp_button.getText().equals("??????"))
			temp_button.setBackground(new Color(242, 242, 242));
		temp_button.setFont(new Font("????????????", Font.PLAIN, 12));
		// ?????????button??????????????????
		temp_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ???????????????????????????button?????????
				if (!currentContent.equals(temp_button.getText())) {
					if (null != sidebarMap_button.get(temp_button.getText())) {
						sidebarMap_button.get(topBarCurrentContent).setBackground(new Color(22, 33, 43));
						sidebarMap_button.get(topBarCurrentContent).setBorder(BorderFactory.createMatteBorder(0,
								40, 0, 0, new Color(22, 33, 43)));
						sidebarMap_button.get(temp_button.getText()).setBackground(new Color(6, 101, 161));
						sidebarMap_button.get(temp_button.getText()).setBorder(BorderFactory.createMatteBorder(0,
								40, 0, 0, new Color(6, 101, 161)));
						topBarCurrentContent = temp_button.getText();
					}
					// ????????????????????????????????????
					temp_button.setBackground(new Color(242, 242, 242));
					// ?????????????????????button???????????????????????????
					contentMap_button.get(currentContent).setBackground(Color.white);
					// ?????????button?????????????????????
					contentMap_panel.get(temp_button.getText()).setVisible(true);
					// ????????????????????????????????????
					contentMap_panel.get(currentContent).setVisible(false);
					// ?????????button???name ????????????????????????
					currentContent = temp_button.getText();
				}
			}
		});
		// ?????????topBar???button?????????????????????label
		JLabel closeContent_label = new JLabel("X");
		// ???????????????
		closeContent_label.setOpaque(true);
		// ?????????button????????????
		closeContent_label.setBounds(temp_button.getWidth() - 15, (temp_button.getHeight() - 10) / 2, 10, 10);
		// ????????????????????????
		if (!content_title.equals("??????"))
			temp_button.add(closeContent_label);
		// ???????????????????????????????????????
		closeContent_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (null != sidebarMap_button.get(temp_button.getText())) {
					sidebarMap_button.get(temp_button.getText()).setBackground(new Color(22, 33, 43));
					sidebarMap_button.get(temp_button.getText()).setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(22, 33, 43)));
					sidebarMap_button.get("??????").setBackground(new Color(6, 101, 161));
					sidebarMap_button.get("??????").setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(6, 101, 161)));
					topBarCurrentContent = "??????";
				}
				// ???x??????????????????????????????????????????button
				xOffset -= temp_button.getWidth();
				// ?????????????????????button??????
				contentMap_panel.get(content_title).setVisible(false);
				contentMap_button.get(content_title).setVisible(false);
				// ????????????topBar???????????????
				contentMap_button.get("??????").setVisible(true);
				contentMap_panel.get("??????").setVisible(true);
				// ????????????content???button
				contentParent_pane.remove(contentMap_panel.get(content_title));
				contentTopBar_panel.remove(contentMap_button.get(content_title));
				// ??????map??????????????????content???button
				contentMap_panel.remove(content_title);
				contentMap_button.remove(content_title);
				// ?????????????????????????????????
				currentContent = "??????";
			}
		});
		// ???topBar??????button
		contentTopBar_panel.add(temp_button);
		// ????????????????????????
		content.setVisible(true);
		// ???content???????????????
		contentParent_pane.add(content);
		// ???button???content_panel?????????map???????????????
		contentMap_button.put(content_title, temp_button);
		contentMap_panel.put(content_title, content);
		// ????????????????????????????????????
		if (!content_title.equals(currentContent))
			// ??????????????????????????????????????????????????????????????????????????????????????????????????????
			contentMap_panel.get(currentContent).setVisible(false);
		// ????????????button??????????????????
		contentMap_button.get(currentContent).setBackground(Color.white);
		// ????????????button???????????????????????????
		contentMap_button.get(content_title).setBackground(new Color(242, 242, 242));
		// ??????????????????
		currentContent = content_title;
		// ??????topBar
		contentTopBar_panel.repaint();
		// ???????????????
		contentParent_pane.repaint();
	}

	// ??????????????????
	private void initLeftBar() {
		// ???????????????
		JPanel leftSidebar_panel = super.getLeftSidebarPanel();
		// ???????????????????????????
		sidebarMap_button = new HashMap<>();
		// ???????????????
		JPanel menu_panel = new JPanel();
		// ??????????????????????????????
		menu_panel.setLayout(null);
		// ?????????????????????
		menu_panel.setBounds(0, 0, leftSidebar_panel.getWidth(), 120);
		// ???????????????????????????label ???????????????/??????
		JLabel personal_label = ComponentUtils.getIconLabel(UserClient.class.getResource("/images/personalLogo.png"),
				"????????????            ???");
		// ?????????label????????????
		personal_label.setBackground(new Color(32, 47, 61));
		personal_label.setOpaque(true);
		// ??????????????????????????????
		personal_label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// ???????????????label?????????
		personal_label.setBounds(0, 0, menu_panel.getWidth(), 40);
		// ????????????label??????????????????
		personal_label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// ????????????/???????????????
				if (menu_panel.getHeight() == 40) {
					menu_panel.setSize(personal_label.getWidth(), 120);
					personal_label.setText("????????????            ???");
				} else {
					menu_panel.setSize(personal_label.getWidth(), 40);
					personal_label.setText("????????????            ???");
				}
			}
		});
		// ????????????label?????????????????????
		menu_panel.add(personal_label);
		// ????????????????????????
		JButton index_button = ComponentUtils.getLeftSidebarCommonButton("??????");
		index_button.setBackground(new Color(6, 101, 161));
		index_button.setBorder(BorderFactory.createMatteBorder(0, 40, 1, 0, new Color(6, 101, 161)));
		index_button.setBounds(0, 40, menu_panel.getWidth(), 40);
		addPageToContent("??????", addIndexContent());
		index_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addPageToContent("??????", addIndexContent());
				if (!topBarCurrentContent.equals(index_button.getText())) {
					if (null != sidebarMap_button.get(index_button.getText())) {
						sidebarMap_button.get(topBarCurrentContent).setBackground(new Color(22, 33, 43));
						sidebarMap_button.get(topBarCurrentContent).setBorder(BorderFactory.createMatteBorder(0, 40, 0, 0, new Color(22, 33, 43)));
						sidebarMap_button.get(topBarCurrentContent).setBackground(new Color(22, 33, 43));
						sidebarMap_button.get(index_button.getText()).setBackground(new Color(6, 101, 161));
						topBarCurrentContent = "??????";
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (currentContent.equals("??????")) {
					index_button.setBackground(new Color(6, 101, 161));
					index_button.setBorder(BorderFactory.createMatteBorder(0,
							40, 0, 0, new Color(6, 101, 161)));
				}
			}
		});
		sidebarMap_button.put("??????", index_button);
		menu_panel.add(index_button);


		JButton personal_button = ComponentUtils.getLeftSidebarCommonButton("????????????");
		personal_button.setBounds(0, 80, menu_panel.getWidth(), 40);
		personal_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				addPageToContent("????????????", addPersonalContent());
				if (!topBarCurrentContent.equals(personal_button.getText())) {
					if (null != sidebarMap_button.get(personal_button.getText())) {
						sidebarMap_button.get(topBarCurrentContent).setBackground(new Color(22, 33, 43));
						sidebarMap_button.get(topBarCurrentContent).setBorder(BorderFactory.createMatteBorder(0,
								40, 0, 0, new Color(22, 33, 43)));
						sidebarMap_button.get(personal_button.getText()).setBackground(new Color(6, 101, 161));
						sidebarMap_button.get(personal_button.getText()).setBorder(BorderFactory.createMatteBorder(0,
								40, 0, 0, new Color(6, 101, 161)));
						topBarCurrentContent = "????????????";
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (topBarCurrentContent.equals("????????????")) {
					personal_button.setBackground(new Color(6, 101, 161));
					personal_button.setBorder(BorderFactory.createMatteBorder(0, 40, 0, 0, new Color(6, 101, 161)));
				}
			}
		});
		sidebarMap_button.put("????????????", personal_button);
		menu_panel.add(personal_button);
		// ???menu????????????????????????
		super.addToLeftSidebar(menu_panel);
	}

	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	// ???????????? ??????
	private void showPrompt(Component component, String message) {
		new Thread(() -> {
			while (component.getY() < 40) {
				try {
					Thread.sleep(10);
					((JLabel) component).setText(message);
					component.setVisible(true);
					component.setLocation(1, component.getY() + 1);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
			}
			try {
				Thread.sleep(2000);
				while (component.getY() > 0) {
					try {
						Thread.sleep(10);
						((JLabel) component).setText(message);
						component.setVisible(true);
						component.setLocation(1, component.getY() - 1);
					} catch (InterruptedException interruptedException) {
						interruptedException.printStackTrace();
					}
				}
				component.setVisible(false);
				component.setLocation(1, 0);
			} catch (InterruptedException interruptedException) {
				interruptedException.printStackTrace();
			}
		}).start();
	}

	// ??????
	private JPanel mask_panel = null;

	// ??????????????????????????????????????????
	public JPanel getPersonalCommonJPanel(String title) {
		// j???????????????????????????????????????
		mask_panel = new JPanel();
		mask_panel.setBackground(null);
		mask_panel.setOpaque(false);
		mask_panel.setBounds(commonContentX, commonContentY, commonContentWidth, commonContentHeight);
		mask_panel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		// ?????????????????????????????????
		contentParent_pane.add(mask_panel, 99, 0);
		JPanel personalCommon_panel = new JPanel();
		personalCommon_panel.setLayout(null);
		personalCommon_panel.setBounds(0, 0, 380, 500);
		personalCommon_panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(128, 128, 128)));
		JPanel topBar_panel = new JPanel();
		topBar_panel.setLayout(null);
		topBar_panel.setBounds(0, 0, 380, 40);
		topBar_panel.setBackground(new Color(54, 148, 117));
		JLabel topBar_title = new JLabel(" " + title);
		topBar_title.setBounds(0, 0, title.length() * 15, topBar_panel.getHeight());
		topBar_title.setFont(new Font("????????????", Font.PLAIN, 14));
		topBar_title.setForeground(Color.white);
		topBar_title.setHorizontalAlignment(JLabel.LEFT);
		topBar_panel.add(topBar_title);
		JButton close_button = new JButton("X");
		close_button.setBounds(personalCommon_panel.getWidth() - 40, 0, 40, 40);
		close_button.setBackground(new Color(54, 148, 117));
		close_button.setForeground(Color.white);
		close_button.setFocusPainted(false);
		close_button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		close_button.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(30, 124, 140)));
		close_button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentParent_pane.remove(personalCommon_panel);
				contentParent_pane.remove(mask_panel);
				contentParent_pane.repaint();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				close_button.setBackground(new Color(219, 88, 96));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				close_button.setBackground(new Color(54, 148, 117));
			}
		});
		topBar_panel.add(close_button);
		personalCommon_panel.add(topBar_panel);
		return personalCommon_panel;
	}

	public JLabel getCheckLabel() {
		JLabel checkLabel = new JLabel();
		checkLabel.setHorizontalAlignment(JLabel.CENTER);
		checkLabel.setVisible(false);
		checkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		return checkLabel;
	}
	// test
		public static void main(String[] args) {
			User user = new User();
			user.setUserName("Voryla");
			user.setUserId("123456");
			user.setTrust((byte) 100);
			user.setBorrowCount((byte) 3);
			UserClient userClient = new UserClient("Welcome Gaga Library", user);
		}
}