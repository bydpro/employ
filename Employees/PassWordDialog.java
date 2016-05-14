
import java.awt.*;

import javax.swing.*;
import java.sql.*;
import java.awt.event.*;

class PassWordDialog extends JDialog implements ActionListener{//�̳жԻ�����
  JPanel panelCenter = new JPanel(new BorderLayout());  //�������
  JLabel name = new JLabel("�� �� ����");
  JTextField nam = new JTextField();
  JLabel labelpwd = new JLabel("��    �룺");
  JPasswordField pwd = new JPasswordField();//������
  JLabel chushi = new JLabel("��ʼ���룺");
  Label Chumima = new Label("888888");
  JPanel panelCenter_C_C = new JPanel(new GridLayout(3,2,5,5));
  JLabel labelKey = new JLabel(new ImageIcon(("img\\56.png")));
  JPanel panelKey = new JPanel();
  JPanel p = new JPanel();
  JPanel panelSouth = new JPanel();//��ťͼƬ
  ImageIcon icon1 = new ImageIcon(("img\\3.gif"));
  ImageIcon icon2 = new ImageIcon(("img\\9.gif"));
  JButton login = new JButton("��½[L]",icon1);
  JButton exit = new JButton("�˳�[C]",icon2);
  String str;
  public PassWordDialog() {
    name.setHorizontalAlignment(JLabel.RIGHT);
    labelpwd.setHorizontalAlignment(JLabel.RIGHT);
    chushi.setHorizontalAlignment(JLabel.RIGHT);

    nam.setFont(new Font("����",Font.BOLD,15));
    nam.setForeground(Color.BLUE);
    pwd.setFont(new Font("����",Font.BOLD,15));
    panelCenter_C_C.add(name);
    panelCenter_C_C.add(nam);
    panelCenter_C_C.add(labelpwd);
    panelCenter_C_C.add(pwd);
    panelCenter_C_C.add(chushi);
    panelCenter_C_C.add(Chumima);
   nam.setText("�����û�");
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
    panelCenter.setBorder(BorderFactory.createTitledBorder("����Ա��½"));//�ڱ߽�������ʾ�ı���

    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(panelCenter,BorderLayout.CENTER);
    cont.add(panelSouth,BorderLayout.SOUTH);
    setTitle("��С����ҵ���ʹ���");
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
    this.addWindowListener(new WindowAdapter(){//�رնԻ���ʱ���س����˳�ϵͳ
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
    if(e.getSource().equals(login)){  //�ж��ǵ�½����ȡ��
      if (nam.getText().equals("")) {
        JOptionPane.showMessageDialog(null, "�������û�����");
        nam.requestFocus();
      }
      else {
        if (String.valueOf(pwd.getPassword()).equals("")) {
          JOptionPane.showMessageDialog(null, "���������룡");
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
                if (str.equals(String.valueOf(pwd.getPassword()))) { //�ж��û����������Ƿ���ȷ
                  this.dispose(); //��ȷ�����ٶԻ������ϵͳ
                }
                else {
                  JOptionPane.showMessageDialog(null, "�������"); //��������ʾ��ʾ�Ի���
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
      System.exit(0);//�������ĵ����˳���ť����������
    }
  }
}
