package com.zwk.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class TodoImages extends JFrame {
	public TodoImages() {
		super();
		this.setLayout(null);
		this.setVisible(true);
		this.setSize(400, 300);
		JLabel jLabel = new JLabel();
		jLabel.setBounds(0, 0, 100, 100);
		this.add(jLabel);
		JButton jButton = new JButton("hello");
		jButton.setBounds(100, 100, 100, 20);
		jButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				File file = new File(TodoImages.class.getResource("/images/login_backGround_img.png").getPath());

				try {
					Connection connection = JDBCUtils.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement("update  gaga_library_user_test Set  img= ? where id=2");
					try {
						InputStream fileInputStream = new FileInputStream(file);
						preparedStatement.setBinaryStream(1, fileInputStream, fileInputStream.available());
					} catch (IOException fileNotFoundException) {
						fileNotFoundException.printStackTrace();
					}
					int a = preparedStatement.executeUpdate();
					System.out.println(a);
					ResultSet resultSet = preparedStatement.executeQuery("select img FROM gaga_library_user_test Where id=2");
					resultSet.next();
					InputStream binaryStream = resultSet.getBinaryStream(1);
					BufferedImage bufferedImage = ImageIO.read(binaryStream);
					jLabel.setIcon(new ImageIcon(bufferedImage));
				} catch (SQLException | IOException throwables) {
					throwables.printStackTrace();
				}
			}
		});
		this.add(jButton);
	}

	public static void main(String[] args) {

		TodoImages todoImages = new TodoImages();
	}
}
