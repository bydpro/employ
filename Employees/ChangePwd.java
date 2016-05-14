
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

public class ChangePwd extends JDialog {
    private JLabel labelUser = new JLabel("用 户 名：");
    private JComboBox box = new JComboBox();
    private JLabel labelPwd = new JLabel("原 密 码：");
    private JPasswordField pwd = new JPasswordField();
    private JLabel labelp1 = new JLabel("新 密 码：");
    private JPasswordField pwd1 = new JPasswordField();
    private JLabel labelp2 = new JLabel("确认密码：");
    private JPasswordField pwd2 = new JPasswordField();
    private JButton bYes = new JButton("提交");
    private JButton bNo = new JButton("重置");
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
      setTitle("更改密码");
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
              JOptionPane.showMessageDialog(null, "两次输入的密码不同，请重新输入！");
              pwd1.setText("");
              pwd1.requestFocus(true);
              pwd2.setText("");
            }
          }
          else {
            JOptionPane.showMessageDialog(null, "请确认密码！");
            pwd2.requestFocus(true);
          }
        }
        else {
          JOptionPane.showMessageDialog(null, "请输入新密码！");
          pwd1.requestFocus(true);
        }
      }
      else {
        JOptionPane.showMessageDialog(null, "请输入密码！");
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
          JOptionPane.showMessageDialog(null, "更改密码成功！");
          dispose();
        }
        else {
          JOptionPane.showMessageDialog(null, "密码错误，请重新输入！");
          pwd.setText("");
          pwd.requestFocus(true);
          pwd1.setText("");
          pwd2.setText("");
        }
      }
      catch (Exception e) {
        JOptionPane.showMessageDialog(null, "更改密码操作失败，请联系管理员！");
        e.printStackTrace();
      }
    }
  }
