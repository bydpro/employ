
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class ChangePwd extends JDialog {
    private JLabel labelUser = new JLabel("�� �� ����");
    private JComboBox box = new JComboBox();
    private JLabel labelPwd = new JLabel("ԭ �� �룺");
    private JPasswordField pwd = new JPasswordField();
    private JLabel labelp1 = new JLabel("�� �� �룺");
    private JPasswordField pwd1 = new JPasswordField();
    private JLabel labelp2 = new JLabel("ȷ�����룺");
    private JPasswordField pwd2 = new JPasswordField();
    private JButton bYes = new JButton("�ύ");
    private JButton bNo = new JButton("����");
    private JPanel pC = new JPanel(new GridLayout(4,3,10,10));
    private JPanel pS = new JPanel();
    private Connection con;
    private Statement st;
    private ResultSet rs;
    Container cont;
    public ChangePwd() {
      cont = this.getContentPane();
      cont.setLayout(new BorderLayout());
      cont.add(pC, BorderLayout.CENTER);
      cont.add(pS, BorderLayout.SOUTH);
      setTitle("��������");
      setSize(350, 200);
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension th = this.getSize();
      setLocation( (d.width - th.width) / 2, (d.height - th.height) / 2);
      setModal(true);
      setResizable(false);
      getRootPane().setDefaultButton(bYes);
      labelUser.setHorizontalAlignment(JLabel.RIGHT);
      labelPwd.setHorizontalAlignment(JLabel.RIGHT);
      labelp1.setHorizontalAlignment(JLabel.RIGHT);
      labelp2.setHorizontalAlignment(JLabel.RIGHT);
      pC.add(labelUser);
      pC.add(box);
      pC.add(labelPwd);
      pC.add(new JLabel());
      pC.add(labelPwd);
      pC.add(pwd);
      pC.add(new JLabel());
      pC.add(labelp1);
      pC.add(pwd1);
      pC.add(new JLabel());
      pC.add(labelp2);
      pC.add(pwd2);
      pC.add(new JLabel());
      pS.add(bYes);
      pS.add(bNo);
      showData();

      bYes.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          change();
        }
      });
      bNo.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          pwd.setText("");
          pwd.requestFocus(true);
          pwd1.setText("");
          pwd2.setText("");
        }
      });
    }
    public void showData() {
      box.removeAllItems();
      try {
        con = new DBCon().getCon();
        st = con.createStatement();
        rs = st.executeQuery("select * from Admin");
        while (rs.next()) {
          box.addItem(rs.getString(2));
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }
    }
    public void change() {
      if (!String.valueOf(pwd.getPassword()).equals("")) {
        if (!String.valueOf(pwd1.getPassword()).equals("")) {
          if (!String.valueOf(pwd2.getPassword()).equals("")) {
            if (String.valueOf(pwd1.getPassword()).equals(String.valueOf(pwd2.
                getPassword()))) {
              String s1 = String.valueOf(pwd.getPassword());
              String s2 = String.valueOf(pwd1.getPassword());
              String s3 = String.valueOf(box.getSelectedItem());
              ch(s1, s2, s3);
              showData();
            }
            else {
              JOptionPane.showMessageDialog(null, "������������벻ͬ�����������룡");
              pwd1.setText("");
              pwd1.requestFocus(true);
              pwd2.setText("");
            }
          }
          else {
            JOptionPane.showMessageDialog(null, "��ȷ�����룡");
            pwd2.requestFocus(true);
          }
        }
        else {
          JOptionPane.showMessageDialog(null, "�����������룡");
          pwd1.requestFocus(true);
        }
      }
      else {
        JOptionPane.showMessageDialog(null, "���������룡");
        pwd.requestFocus(true);
      }
    }
    public void ch(String s1, String s2, String s3) {
      try {
        con = new DBCon().getCon();
        st = con.createStatement();
        rs = st.executeQuery("select * from Admin where adminname ='" + s3 +
                             "'and password='" + s1 + "'");
        if (rs.next()) {
          st.executeUpdate("update Admin set password='" + s2 +
                           "' where adminname='" + s3 + "'");
          JOptionPane.showMessageDialog(null, "��������ɹ���");
          dispose();
        }
        else {
          JOptionPane.showMessageDialog(null, "����������������룡");
          pwd.setText("");
          pwd.requestFocus(true);
          pwd1.setText("");
          pwd2.setText("");
        }
      }
      catch (Exception e) {
        JOptionPane.showMessageDialog(null, "�����������ʧ�ܣ�����ϵ����Ա��");
        e.printStackTrace();
      }
    }
  }
