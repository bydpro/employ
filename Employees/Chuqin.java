
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;

public class Chuqin  extends JInternalFrame{
  private DefaultTableModel tm;
  private JTable ta;
  private JScrollPane sr;
  private JButton bAdd;
  private JButton bDel;
  private JPanel p_North;
  private Connection con;
  private Statement st;
  private ResultSet rs;
  public Chuqin() {
    super("Ա��������Ϣ", true, true, true, true);
    showInfo();
  }

  public void showInfo() {
    p_North = new JPanel();
    bAdd = new JButton("����",
                       new ImageIcon(("img\\2.png")));
    bDel = new JButton("ɾ��",
                       new ImageIcon(("img\\yy.png")));
    p_North.add(bAdd);
    p_North.add(bDel);
    tm = new DefaultTableModel();
    showData();
    ta = new JTable(tm);
    sr = new JScrollPane(ta);
    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(p_North, BorderLayout.NORTH);
    cont.add(sr, BorderLayout.CENTER);
    this.setSize(500, 350);
    this.setVisible(true);
    bAdd.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        addData();
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
      tm.addColumn("��  ��");
      tm.addColumn("�������");
      tm.addColumn("����");
    // tm.addColumn("");
      tm.addColumn("ʱ  ��");
      rs = st.executeQuery("select * from chuqin_info");
      while (rs.next()) {
        String str[] = {rs.getString(1),
            rs.getString(2), rs.getString(3), rs.getString(6),rs.getString(7)
            };
        tm.addRow(str);
      }
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    finally {
      if (rs != null) {
        try {
          rs.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (st != null) {
        try {
          st.close();
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
      if (con != null) {
        try {
          con.close();
        }
        catch (Exception e) {
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
        st.executeUpdate("delete from chuqin_info where id='" + z + "'");
        JOptionPane.showMessageDialog(null, "ɾ���ɹ���");
      }
      catch (Exception ex) {
        ex.printStackTrace();
      }
      finally {
        if (st != null) {
          try {
            st.close();
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
        if (con != null) {
          try {
            con.close();
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
        }
      }
    }
    catch (Exception e) {
      JOptionPane.showMessageDialog(null, "�����б���ѡ��Ҫɾ�����У�");
    }
  }
  public void addData(){
    new AddChuQinData().setVisible(true);
    showData();
  }
}
class AddChuQinData extends JDialog implements ActionListener{
  JLabel name=new JLabel("��    ����");
  JComboBox box1 = new JComboBox();
  JLabel chuqiuinfo = new JLabel("���������");
  JComboBox box2 = new JComboBox();
  JLabel cishu = new JLabel("��    ����");
  JTextField box3 = new JTextField();
  JLabel time = new JLabel("ʱ    �䣺");
  JTextField tf1 = new JTextField();
  JButton bYes = new JButton("ȷ    ��");
  JButton bNo = new JButton("��    ��");
  JPanel pC = new JPanel(new GridLayout(5,3,5,5));
  JPanel pS = new JPanel();
  public AddChuQinData(){
    Query();
    box2.addItem("�ٵ�");box2.addItem("����");box2.addItem("���");
    name.setHorizontalAlignment(JLabel.RIGHT);
    chuqiuinfo.setHorizontalAlignment(JLabel.RIGHT);
    cishu.setHorizontalAlignment(JLabel.RIGHT);
    time.setHorizontalAlignment(JLabel.RIGHT);
    pC.setBorder(BorderFactory.createTitledBorder(""));
    pC.add(name);pC.add(box1);pC.add(new JLabel(""));
    pC.add(chuqiuinfo);pC.add(box2);pC.add(new JLabel(""));
    pC.add(cishu);pC.add(box3);pC.add(new JLabel(""));
    pC.add(time);pC.add(tf1);pC.add(new JLabel(""));
    pC.add(new JLabel("ʱ���ʽ��",JLabel.RIGHT));pC.add(new JLabel("20160908"));pC.add(new JLabel(""));
    pS.add(bYes);pS.add(bNo);
    Container con = this.getContentPane();
    con.setLayout(new BorderLayout());
    con.add(pC,BorderLayout.CENTER);
    con.add(pS,BorderLayout.SOUTH);
    this.setTitle("Ա�����ڼ�¼");
    this.setSize(350,240);
    this.setModal(true);
    this.setResizable(false);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension th = this.getSize();
    setLocation( (d.width - th.width) / 2, (d.height - th.height) / 2);
    bYes.addActionListener(this);
    bNo.addActionListener(this);
  }
  public void Query(){
    String sql1="select e_name from employee_info";
    try {
      Connection cont = new DBCon().getCon();
      Statement st = cont.createStatement();
      ResultSet rs1 = st.executeQuery(sql1);
      while(rs1.next()){
        box1.addItem(rs1.getString(1));
      }
      rs1.close();
      st.close();
      cont.close();
    }
    catch (Exception exce) {
      exce.printStackTrace();
    }
  }
  public void actionPerformed(ActionEvent e){
    String sql=null;
    if(e.getSource().equals(bYes)){
      if(!box3.getText().trim().equals("")){
        if(!tf1.getText().trim().equals("")){
          try{
            float f1=Float.parseFloat(tf1.getText());
            float f2=Float.parseFloat(box3.getText());
            if(box2.getSelectedItem().toString().equals("�ٵ�")){
             sql="insert into chuqin_info(e_name,late,early,leave2,time1,time) values('"+box1.getSelectedItem().toString()+"','"+"�ٵ�"+"','0','0','"+box3.getText()+"','"+tf1.getText()+"')";
            }
            else if(box2.getSelectedItem().toString().equals("����")){
              sql="insert into chuqin_info(e_name,late,early,leave2,time1,time) values('"+box1.getSelectedItem().toString()+"','"+"����"+"','0','0','"+box3.getText()+"','"+tf1.getText()+"')";
            }
            else{
              sql="insert into chuqin_info(e_name,late,early,leave2,time1,time) values('"+box1.getSelectedItem().toString()+"','"+"���"+"','0','0','"+box3.getText()+"','"+tf1.getText()+"')";
            }
            try{
              Connection cont = new DBCon().getCon();
              Statement st = cont.createStatement();
              st.executeUpdate(sql);
              st.close();
              cont.close();
              JOptionPane.showMessageDialog(null,"�����ɹ���");
              dispose();
            }
            catch(Exception exc){
                JOptionPane.showMessageDialog(null,"����ʧ�ܣ�����ϵϵͳ����Ա��");
            }
          }
          catch(Exception ex){
            JOptionPane.showMessageDialog(null,"������ʱ��ֻ�������֣�");
          }
        }
        else{
          JOptionPane.showMessageDialog(null,"������ʱ�䣡");
        }
      }
      else{
        JOptionPane.showMessageDialog(null,"�����������");
      }
    }
    else{
      tf1.setText("");
      box3.setText("");
    }
  }
}
