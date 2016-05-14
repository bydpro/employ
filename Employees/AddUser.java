
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
  JLabel labelname = new JLabel("��  ��  ����");
  JTextField tfname = new JTextField(15);
  JLabel labelpwd = new JLabel("��       �룺");
  JPasswordField pwd = new JPasswordField();
  JLabel labelquanxian = new JLabel("����Ȩ�ޣ�");
  JComboBox<String> box = new JComboBox<String>();
  JButton bYes = new JButton("ȷ��");
  JButton bNo = new JButton("����");
  Container con;
  Connection cont;
  JPanel pC = new JPanel(new GridLayout(3,3,10,10));
  JPanel pN = new JPanel();
  public AddUser(){
    labelname.setHorizontalAlignment(JLabel.RIGHT);
    labelpwd.setHorizontalAlignment(JLabel.RIGHT);
    labelquanxian.setHorizontalAlignment(JLabel.RIGHT);

    box.addItem("ϵͳ����Ա");
    box.addItem("ְ��");
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
    this.setTitle("�����û�");
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
    	  if(box.getSelectedItem().toString().equals("ϵͳ����Ա")){
    		  flag="1";
    	  }else{
    		  flag="2";
    	  }
        String sql="insert into admin(adminname,password,flag)  values('"+tfname.getText()+"','"+String.valueOf(pwd.getPassword())+"','"+flag+"')";
        try{
          cont = new DBCon().getCon();
          Statement st = cont.createStatement();
          int i = st.executeUpdate(sql);
          JOptionPane.showMessageDialog(null,"�ɹ�������һ���û���");
          st.close();
          cont.close();
          clear();
          dispose();
        }
        catch(Exception e){
          JOptionPane.showMessageDialog(null,"�����û�����ʧ�ܣ�����ϵϵͳ����Ա��");
        }
      }
      else{
        JOptionPane.showMessageDialog(null,"���������룡");
        clear();
      }
    }
    else{
      JOptionPane.showMessageDialog(null,"�������û�����");
      clear();
    }
  }
private void Query() {
	// TODO Auto-generated method stub
	
}
}
