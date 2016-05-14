import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class EmpLogin extends JFrame {

	private static final long serialVersionUID = 1L;

	JLabel title = new JLabel("��ӭʹ��");
	JTextField userName = new JTextField();
	JLabel userNameLable = new JLabel("�û�����");
	JPasswordField password = new JPasswordField();
	JLabel passwordLable = new JLabel("���룺");
	JRadioButton adminJRadio = new JRadioButton("ϵͳ����Ա");
	JRadioButton empJRadio = new JRadioButton("ְ��");
	JButton login = new JButton("��½");
	JButton exit = new JButton("�˳�");
	ButtonGroup radioButtonGroup = new ButtonGroup();
	public EmpLogin() {
		setTitle("���ʹ���ϵͳ");
		setSize(350, 350);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container cp = getContentPane();
		getContentPane().setLayout(null);

		title.setFont(new Font("����", Font.BOLD, 24));
		title.setBounds(120, 20, 150, 50);
		userNameLable.setBounds(20, 75, 60, 30);
		userName.setBounds(80, 75, 200, 30);
		passwordLable.setBounds(20, 125, 60, 30);
		password.setBounds(80, 125, 200, 30);
		adminJRadio.setBounds(80, 175, 100, 30);
		empJRadio.setBounds(185, 175, 150, 30);
		login.setBounds(80, 230, 60, 30);
		exit.setBounds(180, 230, 60, 30);
		radioButtonGroup.add(adminJRadio);
		radioButtonGroup.add(empJRadio);
		cp.add(title);
		cp.add(userNameLable);
		cp.add(userName);
		cp.add(passwordLable);
		cp.add(password);
		cp.add(adminJRadio);
		cp.add(empJRadio);
		cp.add(exit);
		cp.add(login);
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedLogin(e);
			}
		});
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedLogin(e);
			}
		});
		setVisible(true);

	}

	public void actionPerformedLogin(ActionEvent e) {
		if (e.getSource().equals(login)) { // �ж��ǵ�½����ȡ��
			if (userName.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "�������û�����");
				userName.requestFocus();
			} else {
				if (String.valueOf(password.getPassword()).equals("")) {
					JOptionPane.showMessageDialog(null, "���������룡");
					password.requestFocus();
				}

				else {
					String userType = "";
					if (adminJRadio.isSelected() || empJRadio.isSelected()) {
						if (adminJRadio.isSelected()) {
							userType = "1";
						} else {
							userType = "2";
						}
						String sql = "select password,flag from Admin where adminname='" + userName.getText() + "'";
						try {
							Connection con = new DBCon().getCon();
							Statement st = con.createStatement();
							ResultSet rs = st.executeQuery(sql);
							if (rs.next()) {
								String str = rs.getString(1);
								String flag = rs.getString(2);
								if (!userType.equals(flag)) {
									JOptionPane.showMessageDialog(null, "�Ñ���ѡ�û����ͺ�ʵ���û����Ͳ�����"); // ��������ʾ��ʾ�Ի���
								} else {
									if (str.equals(String.valueOf(password.getPassword()))) { // �ж��û����������Ƿ���ȷ
										this.dispose(); // ��ȷ�����ٶԻ������ϵͳ
										if("1".equals(flag)){
											EmployeeMSFrame frame = new EmployeeMSFrame();
											// Validate frames that have preset
											// sizes
											// Pack frames that have useful
											// preferred
											// size info, e.g. from their layou

											// Center the window
											Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
											Dimension frameSize = frame.getSize();
											if (frameSize.height > screenSize.height) {
												frameSize.height = screenSize.height;
											}
											if (frameSize.width > screenSize.width) {
												frameSize.width = screenSize.width;
											}
											frame.setLocation((screenSize.width - frameSize.width) / 2,
													(screenSize.height - frameSize.height) / 2);
											frame.setVisible(true);
										}else{
											Login4Yuan frame2 = new Login4Yuan(userName.getText().toString());
											// Validate frames that have preset
											// sizes
											// Pack frames that have useful
											// preferred
											// size info, e.g. from their layou

											// Center the window
											Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
											Dimension frameSize = frame2.getSize();
											if (frameSize.height > screenSize.height) {
												frameSize.height = screenSize.height;
											}
											if (frameSize.width > screenSize.width) {
												frameSize.width = screenSize.width;
											}
											frame2.setLocation((screenSize.width - frameSize.width) / 2,
													(screenSize.height - frameSize.height) / 2);
											frame2.setVisible(true);
										}
										

									} else {
										JOptionPane.showMessageDialog(null, "�������"); // ��������ʾ��ʾ�Ի���
										password.setText("");
										password.requestFocus();
									}
								}

							} else {
								JOptionPane.showMessageDialog(null, "��ǰ�û������ڣ�"); // ��������ʾ��ʾ�Ի���
							}
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "��ѡ���û����ͣ�"); // ��������ʾ��ʾ�Ի���
						adminJRadio.requestFocus();
					}

				}
			}
		} else {
			System.exit(0);// �������ĵ����˳���ť����������
		}
	}
}
