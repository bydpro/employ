
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class UserMng extends JInternalFrame {
	private DefaultTableModel tm;
	private JTable ta;
	private JScrollPane sr;
	private JButton bAdd;
	private JButton bUpdate;
	private JButton bDel;
	private JPanel p_North;
	private Connection con;
	private Statement st;
	private ResultSet rs;

	public UserMng() {
		super("�û�����", true, true, true, true);
		showInfo();
	}

	public void showInfo() {
		p_North = new JPanel();
		bAdd = new JButton("����", new ImageIcon(("img\\2.png")));
		bUpdate = new JButton("����", new ImageIcon(("img\\4.gif")));
		bDel = new JButton("ɾ��", new ImageIcon(("img\\yy.png")));
		p_North.add(bAdd);
		p_North.add(bUpdate);
		p_North.add(bDel);

		tm = new DefaultTableModel();
		showData();
		ta = new JTable(tm);
		sr = new JScrollPane(ta);
		Container cont = this.getContentPane();
		cont.setLayout(new BorderLayout());
		cont.add(p_North, BorderLayout.NORTH);
		cont.add(sr, BorderLayout.CENTER);
		this.setSize(750, 590);
		this.setVisible(true);
		bAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addData();
			}
		});
		bUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				upData();
			}
		});
		bDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delData();
				showData();
			}
		});
	}

	public void showData() {
		tm.setColumnCount(0);
		tm.setRowCount(0);
		try {
			con = new DBCon().getCon();
			st = con.createStatement();
			tm.addColumn("ID");
			tm.addColumn("����");
			tm.addColumn("�û�����");
			rs = st.executeQuery("select * from admin");
			while (rs.next()) {
				String flag = "";
				if ("1".equals(rs.getString(4))) {
					flag = "ϵͳ����Ա";
				} else {
					flag = "ְ��";
				}
				String str[] = { rs.getString(1), rs.getString(2), flag};
				tm.addRow(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (st != null) {
				try {
					st.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void delData() {
		try {
			Object x = ta.getValueAt(ta.getSelectedRow(), 0);
			final String z = x.toString();
			try {
				con = new DBCon().getCon();
				st = con.createStatement();
				st.executeUpdate("delete from admin where id='" + z + "'");
				JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�����б���ѡ��Ҫɾ�����У�");
		}
	}

	public void addData() {
		new AddUser().setVisible(true);
		showData();
	}

	public void upData() {
		UpUserdata ud = new UpUserdata();
		try {
			Object x = ta.getValueAt(ta.getSelectedRow(), 0);
			final String z = x.toString();
			try {
				con = new DBCon().getCon();
				st = con.createStatement();
				ResultSet rs = st.executeQuery("select * from admin where id='" + z + "'");
				String flag = "";
				while (rs.next()) {
					if ("1".equals(rs.getString(4))) {
						flag = "ϵͳ����Ա";
					} else {
						flag = "ְ��";
					}
					ud.textfield.setText(rs.getString(1));
					ud.tfname.setText(rs.getString(2));
					ud.pwd.setText(rs.getString(3));

					ud.box.setSelectedItem(rs.getString(4));
					ud.setVisible(true);
					showData();
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "����ʧ�ܣ�");
				ex.printStackTrace();
			} finally {
				if (st != null) {
					try {
						st.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				if (con != null) {
					try {
						con.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "�����б���ѡ��Ҫ���ĵ��У�");
		}
	}

}

class UpUserdata extends JDialog implements ActionListener {
	JLabel labelname = new JLabel("��  ��  ����");
	JTextField tfname = new JTextField(15);
	JLabel labelpwd = new JLabel("��       �룺");
	JPasswordField pwd = new JPasswordField();
	JLabel labelquanxian = new JLabel("����Ȩ�ޣ�");
	JComboBox box = new JComboBox();
	JButton bYes = new JButton("ȷ��");
	JButton bNo = new JButton("����");
	Container con;
	Connection cont;
	JPanel pC = new JPanel(new GridLayout(3, 3, 10, 10));
	JPanel pN = new JPanel();

	JTextField textfield = new JTextField();

	public UpUserdata() {
		labelname.setHorizontalAlignment(JLabel.RIGHT);
		labelpwd.setHorizontalAlignment(JLabel.RIGHT);
		labelquanxian.setHorizontalAlignment(JLabel.RIGHT);

		box.addItem("ϵͳ����Ա");
		box.addItem("ְ��");
		box.setFont(new Font("", Font.BOLD, 16));
		box.setForeground(Color.BLUE);
		tfname.setFont(new Font("", Font.BOLD, 16));
		tfname.setForeground(Color.BLUE);
		pwd.setFont(new Font("", Font.BOLD, 16));
		pwd.setForeground(Color.BLUE);
		pC.add(labelname);
		pC.add(tfname);
		pC.add(new JLabel(""));
		pC.add(labelpwd);
		pC.add(pwd);
		pC.add(new JLabel(""));
		pC.add(labelquanxian);
		pC.add(box);
		pC.add(new JLabel(""));
		pC.setBorder(BorderFactory.createTitledBorder(""));
		pN.add(bYes);
		pN.add(bNo);
		con = this.getContentPane();
		con.setLayout(new BorderLayout());
		con.add(pC, BorderLayout.CENTER);
		con.add(pN, BorderLayout.SOUTH);
		this.setTitle("�޸��û�");
		this.setSize(350, 200);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension th = this.getSize();
		if (th.width > d.width) {
			th.width = d.width;
		}
		if (th.height > d.height) {
			th.height = d.height;
		}
		this.setLocation((d.width - th.width) / 2, (d.height - th.height) / 2);
		this.setModal(true);
		this.setResizable(false);
		bYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateUser();
			}
		});
		bNo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearUser();
			}
		});

	}

	public void clearUser() {
		tfname.setText("");
		tfname.requestFocus(true);
		pwd.setText("");
		box.setSelectedItem("ϵͳ����Ա");
	}

	public void updateUser() {
		if (!tfname.getText().trim().equals("")) {
			if (!String.valueOf(pwd.getPassword()).trim().equals("")) {
				String flag = "";
				if (box.getSelectedItem().toString().equals("ϵͳ����Ա")) {
					flag = "1";
				} else {
					flag = "2";
				}
				String sql = "update admin set adminname = '" + tfname.getText().toString() + "', password =	'"
						+ String.valueOf(pwd.getPassword()) + "', flag='" + flag + "'" + " where id='"
						+ textfield.getText().toString() + "'";
				try {
					cont = new DBCon().getCon();
					Statement st = cont.createStatement();
					int i = st.executeUpdate(sql);
					JOptionPane.showMessageDialog(null, "�ɹ��޸��û���Ϣ��");
					st.close();
					cont.close();

					dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "�޸��û�����ʧ�ܣ�����ϵϵͳ����Ա��");
				}
			} else {
				JOptionPane.showMessageDialog(null, "���������룡");

			}
		} else {
			JOptionPane.showMessageDialog(null, "�������û�����");

		}
	}

	public void actionPerformed(ActionEvent e) {
	}

}
