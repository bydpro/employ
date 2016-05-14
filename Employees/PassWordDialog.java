
import java.awt.*;

import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

class PassWordDialog extends JDialog implements ActionListener{//继承对话框类
  JPanel panelCenter = new JPanel(new BorderLayout());  //设置组件
  JLabel name = new JLabel("用 户 名：");
  JTextField nam = new JTextField();
  JLabel labelpwd = new JLabel("密    码：");
  JPasswordField pwd = new JPasswordField();//密码域
  JLabel chushi = new JLabel("初始密码：");
  Label Chumima = new Label("888888");
  JPanel panelCenter_C_C = new JPanel(new GridLayout(3,2,5,5));
  JLabel labelKey = new JLabel(new ImageIcon(("img\\56.png")));
  JPanel panelKey = new JPanel();
  JPanel p = new JPanel();
  JPanel panelSouth = new JPanel();//按钮图片
  ImageIcon icon1 = new ImageIcon(("img\\3.gif"));
  ImageIcon icon2 = new ImageIcon(("img\\9.gif"));
  JButton login = new JButton("登陆[L]",icon1);
  JButton exit = new JButton("退出[C]",icon2);
  String str;
  public PassWordDialog() {
    name.setHorizontalAlignment(JLabel.RIGHT);
    labelpwd.setHorizontalAlignment(JLabel.RIGHT);
    chushi.setHorizontalAlignment(JLabel.RIGHT);

    nam.setFont(new Font("楷书",Font.BOLD,15));
    nam.setForeground(Color.BLUE);
    pwd.setFont(new Font("楷书",Font.BOLD,15));
    panelCenter_C_C.add(name);
    panelCenter_C_C.add(nam);
    panelCenter_C_C.add(labelpwd);
    panelCenter_C_C.add(pwd);
    panelCenter_C_C.add(chushi);
    panelCenter_C_C.add(Chumima);
   nam.setText("超级用户");
    panelKey.add(labelKey);

    panelSouth.setLayout(new FlowLayout());
    panelSouth.add(login);
    login.setMnemonic('L');
    panelSouth.add(exit);
    exit.setMnemonic('C');

    login.addActionListener(this);
    exit.addActionListener(this);

    p.setPreferredSize(new Dimension(10,160));
    panelCenter.add(panelCenter_C_C,BorderLayout.CENTER);
    panelCenter.add(panelKey,BorderLayout.EAST);
    panelCenter.add(p,BorderLayout.WEST);
    panelCenter.setBorder(BorderFactory.createTitledBorder("管理员登陆"));//在边界线中显示的标题

    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(panelCenter,BorderLayout.CENTER);
    cont.add(panelSouth,BorderLayout.SOUTH);
    setTitle("中小型企业工资管理");
    this.getRootPane().setDefaultButton(login);
    this.setSize(330,195);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension th = this.getSize();
    if(th.width>d.width){
        th.width=d.width;
    }
    if(th.height>d.height){
        th.height=d.height;
    }
    this.setLocation((d.width-th.width)/2,(d.height-th.height)/2);
    this.setModal(true);
    this.setResizable(false);
    this.addWindowListener(new WindowAdapter(){//关闭对话框时结素程序退出系统
      public void windowClosing(WindowEvent e){
        System.exit(0);
      }
    });
  }
  public String getName(){
      str = String.valueOf(nam.getText());
      return str;
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource().equals(login)){  //判断是登陆还是取消
      if (nam.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "请输入用户名！");
        nam.requestFocus();
      }
      else {
        if (String.valueOf(pwd.getPassword()).equals("")) {
          JOptionPane.showMessageDialog(null, "请输入密码！");
          pwd.requestFocus();
        }
        else {
          String sql = "select password from Admin where adminname='"+nam.getText()+"'";
          try {
            Connection con = new DBCon().getCon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
              String str=rs.getString(1);
                if (str.equals(String.valueOf(pwd.getPassword()))) { //判断用户名和密码是否正确
                  this.dispose(); //正确则销毁对话框进入系统
                }
                else {
                  JOptionPane.showMessageDialog(null, "密码错误！"); //错误则显示提示对话框
                  pwd.setText("");
                  pwd.requestFocus();
                }
            }
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
    else {
      System.exit(0);//如果点击的的是退出按钮，结束程序
    }
  }
}
