
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
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class AddUser extends JDialog{
  JLabel labelname = new JLabel("用  户  名：");
  JTextField tfname = new JTextField(15);
  JLabel labelpwd = new JLabel("密       码：");
  JPasswordField pwd = new JPasswordField();
  JLabel labelquanxian = new JLabel("设置权限：");
  JComboBox<String> box = new JComboBox<String>();
  JButton bYes = new JButton("确定");
  JButton bNo = new JButton("重置");
  Container con;
  Connection cont;
  JPanel pC = new JPanel(new GridLayout(3,3,10,10));
  JPanel pN = new JPanel();
  public AddUser(){
    labelname.setHorizontalAlignment(JLabel.RIGHT);
    labelpwd.setHorizontalAlignment(JLabel.RIGHT);
    labelquanxian.setHorizontalAlignment(JLabel.RIGHT);

    box.addItem("系统管理员");
    box.addItem("职工");
    box.setFont(new Font("",Font.BOLD,16));
    box.setForeground(Color.BLUE);
    tfname.setFont(new Font("",Font.BOLD,16));
    tfname.setForeground(Color.BLUE);
    pwd.setFont(new Font("",Font.BOLD,16));
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
    con.add(pC,BorderLayout.CENTER);
    con.add(pN,BorderLayout.SOUTH);
    this.setTitle("增加用户");
    this.setSize(350,200);
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
    bYes.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        add();
      }
    });
    bNo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        clear();
      }
    });
  }
  public void clear(){
    tfname.setText("");
    tfname.requestFocus(true);
    pwd.setText("");
    box.setSelectedItem("1");
  }
  public void add(){
    if(!tfname.getText().trim().equals("")){
      if(!String.valueOf(pwd.getPassword()).trim().equals("")){
    	  String flag="";
    	  if(box.getSelectedItem().toString().equals("系统管理员")){
    		  flag="1";
    	  }else{
    		  flag="2";
    	  }
        String sql="insert into admin(adminname,password,flag)  values('"+tfname.getText()+"','"+String.valueOf(pwd.getPassword())+"','"+flag+"')";
        try{
          cont = new DBCon().getCon();
          Statement st = cont.createStatement();
          int i = st.executeUpdate(sql);
          JOptionPane.showMessageDialog(null,"成功增加了一名用户！");
          st.close();
          cont.close();
          clear();
          dispose();
        }
        catch(Exception e){
          JOptionPane.showMessageDialog(null,"增加用户操作失败，请联系系统管理员！");
        }
      }
      else{
        JOptionPane.showMessageDialog(null,"请输入密码！");
        clear();
      }
    }
    else{
      JOptionPane.showMessageDialog(null,"请输入用户名！");
      clear();
    }
  }
private void Query() {
	// TODO Auto-generated method stub
	
}
}
