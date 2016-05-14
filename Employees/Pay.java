
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;

public class Pay extends JInternalFrame{
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
  public Pay() {
    super("���ʽ�����Ϣ", true, true, true, true);
    showInfo();
  }

  public void showInfo() {
    p_North = new JPanel();
    bAdd = new JButton("����",
                       new ImageIcon(("img\\2.png")));
    bUpdate = new JButton("����",
                          new ImageIcon(("img\\4.gif")));
    bDel = new JButton("ɾ��",
                       new ImageIcon(("img\\yy.png")));
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
      tm.addColumn("����");
      tm.addColumn("��������");
      tm.addColumn("ְλ����");
      tm.addColumn("��������");
      tm.addColumn("����");
      tm.addColumn("�ܹ���");
      rs = st.executeQuery("select * from pay_info");
      while (rs.next()) {
    	 float sum= Float.parseFloat(rs.getString(5))+Float.parseFloat(rs.getString(6));
        String str[] = {
            rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6),String.valueOf(sum)};
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
            st.executeUpdate("delete from pay_info where e_name='" + z + "'");
            JOptionPane.showMessageDialog(null,"ɾ���ɹ���");
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
  public void addData(){
    new AddEmployeeData().setVisible(true);
    showData();
  }
  public void upData(){
	  UpPlaydata ud = new UpPlaydata();
    try {
        Object x = ta.getValueAt(ta.getSelectedRow(), 0);
        final String z = x.toString();
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            ResultSet rs=st.executeQuery("select * from pay_info where e_name='" + z + "'");
            while(rs.next()){
              ud.box1.setSelectedItem(rs.getString(2));
              ud.box2.setSelectedItem(rs.getString(3));
              ud.box3.setSelectedItem(rs.getString(4));
              ud.tf1.setText(rs.getString(5));
              ud.tf2.setText(rs.getString(6));
              ud.setVisible(true);
              showData();
            }
        } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,"����ʧ�ܣ�");
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
 class AddEmployeeData extends JDialog implements ActionListener{
  JLabel name=new JLabel("��    ����");
  JComboBox combox1 = new JComboBox();
  JLabel depart = new JLabel("��������");
  JComboBox combox2 = new JComboBox();
  JLabel posit= new JLabel("ְλ����");
  JComboBox combox3 = new JComboBox();
  JLabel pay = new JLabel("��������");
  JTextField tf1 = new JTextField();
  JLabel bonus = new JLabel("��    ��");
  JTextField tf2 = new JTextField();
  JButton bYes = new JButton("ȷ    ��");
  JButton bNo = new JButton("��    ��");
  JPanel pC = new JPanel(new GridLayout(5,3,5,5));
  JPanel pS = new JPanel();
  public AddEmployeeData(){
    Query();
    name.setHorizontalAlignment(JLabel.RIGHT);
    depart.setHorizontalAlignment(JLabel.RIGHT);
    posit.setHorizontalAlignment(JLabel.RIGHT);
    pay.setHorizontalAlignment(JLabel.RIGHT);
    bonus.setHorizontalAlignment(JLabel.RIGHT);
    pC.setBorder(BorderFactory.createTitledBorder(""));
    pC.add(name);pC.add(combox1);pC.add(new JLabel(""));
    pC.add(depart);pC.add(combox2);pC.add(new JLabel(""));
    pC.add(posit);pC.add(combox3);pC.add(new JLabel(""));
    pC.add(pay);pC.add(tf1);pC.add(new JLabel(""));
    pC.add(bonus);pC.add(tf2);pC.add(new JLabel(""));
    pS.add(bYes);pS.add(bNo);
    Container con = this.getContentPane();
    con.setLayout(new BorderLayout());
    con.add(pC,BorderLayout.CENTER);
    con.add(pS,BorderLayout.SOUTH);
    this.setTitle("���ӹ��ʽ���");
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
    String sql2="select department_name from department_info";
    String sql3="select position_name from position_info";
    try {
      Connection cont = new DBCon().getCon();
      Statement st = cont.createStatement();
      ResultSet rs1 = st.executeQuery(sql1);
      while(rs1.next()){
    	  combox1.addItem(rs1.getString(1));
      }
      ResultSet rs2 = st.executeQuery(sql2);
      while(rs2.next()){
    	  combox2.addItem(rs2.getString(1));
      }
      ResultSet rs3 = st.executeQuery(sql3);
      while(rs3.next()){
    	  combox3.addItem(rs3.getString(1));
      }
      st.close();
      cont.close();
    }
    catch (Exception exce) {
      exce.printStackTrace();
    }
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource().equals(bYes)){
      if(!tf1.getText().trim().equals("")){
        if(!tf2.getText().trim().equals("")){
          try{
            float f1=Float.parseFloat(tf1.getText());
            float f2=Float.parseFloat(tf2.getText());
            String sql="insert into pay_info(e_name,department_name,position_name,pay,bonus,deduction,insurancs,reaplay)  values('"+combox1.getSelectedItem().toString()+"','"+combox2.getSelectedItem().toString()+"','"+combox3.getSelectedItem().toString()+"','"+f1+"','"+f1+"','','','')";
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
            JOptionPane.showMessageDialog(null,"�������ͽ�����ֻ�������֣�");
          }
        }
        else{
          JOptionPane.showMessageDialog(null,"�����뽱������");
        }
      }
      else{
        JOptionPane.showMessageDialog(null,"�����������������");
      }
    }
    else{
      tf1.setText("");
      tf2.setText("");
    }
  }
}
class UpPlaydata extends JDialog implements ActionListener{
  JLabel name=new JLabel("��    ����");
  JComboBox box1 = new JComboBox();
  JLabel depart = new JLabel("��������");
  JComboBox box2 = new JComboBox();
  JLabel posit = new JLabel("ְλ����");
  JComboBox box3 = new JComboBox();
  JLabel pay = new JLabel("��������");
  JTextField tf1 = new JTextField();
  JLabel bonus = new JLabel("��    ��");
  JTextField tf2 = new JTextField();
  JButton bYes = new JButton("ȷ    ��");
  JButton bNo = new JButton("��    ��");
  JPanel pC = new JPanel(new GridLayout(5,3,5,5));
  JPanel pS = new JPanel();
  public UpPlaydata(){
    Query();
    name.setHorizontalAlignment(JLabel.RIGHT);
    depart.setHorizontalAlignment(JLabel.RIGHT);
    posit.setHorizontalAlignment(JLabel.RIGHT);
    pay.setHorizontalAlignment(JLabel.RIGHT);
    bonus.setHorizontalAlignment(JLabel.RIGHT);
    pC.setBorder(BorderFactory.createTitledBorder(""));
    pC.add(name);pC.add(box1);pC.add(new JLabel(""));
    pC.add(depart);pC.add(box2);pC.add(new JLabel(""));
    pC.add(posit);pC.add(box3);pC.add(new JLabel(""));
    pC.add(pay);pC.add(tf1);pC.add(new JLabel(""));
    pC.add(bonus);pC.add(tf2);pC.add(new JLabel(""));
    pS.add(bYes);pS.add(bNo);
    Container con = this.getContentPane();
    con.setLayout(new BorderLayout());
    con.add(pC,BorderLayout.CENTER);
    con.add(pS,BorderLayout.SOUTH);
    this.setTitle("���Ĺ��ʽ���");
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
    String sql2="select department_name from department_info";
    String sql3="select position_name from position_info";
    try {
      Connection cont = new DBCon().getCon();
      Statement st = cont.createStatement();
      ResultSet rs1 = st.executeQuery(sql1);
      while(rs1.next()){
        box1.addItem(rs1.getString(1));
      }
      ResultSet rs2 = st.executeQuery(sql2);
      while(rs2.next()){
        box2.addItem(rs2.getString(1));
      }
      ResultSet rs3 = st.executeQuery(sql3);
      while(rs3.next()){
        box3.addItem(rs3.getString(1));
      }
      st.close();
      cont.close();
    }
    catch (Exception exce) {
      exce.printStackTrace();
    }
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource().equals(bYes)){
      if(!tf1.getText().trim().equals("")){
        if(!tf2.getText().trim().equals("")){
          try{
            float f1=Float.parseFloat(tf1.getText());
            float f2=Float.parseFloat(tf2.getText());
            String sql="update  pay_info set e_name='"+box1.getSelectedItem().toString()+"',department_name='"+box2.getSelectedItem().toString()+"',position_name='"+box3.getSelectedItem().toString()+"',pay='"+tf1.getText()+"',bonus='"+tf2.getText()+"'where e_name='"+box1.getSelectedItem().toString()+"'";
            try{
              Connection cont = new DBCon().getCon();
              Statement st = cont.createStatement();
              st.executeUpdate(sql);
              st.close();
              cont.close();
              JOptionPane.showMessageDialog(null,"�����ɹ���");
              dispose();
            }
            catch(Exception exception){
                JOptionPane.showMessageDialog(null,"����ʧ�ܣ�����ϵϵͳ����Ա��");
                exception.printStackTrace();
            }
          }
          catch(Exception ex){
            JOptionPane.showMessageDialog(null,"�������ͽ�����ֻ�������֣�");
          }
        }
        else{
          JOptionPane.showMessageDialog(null,"�����뽱������");
        }
      }
      else{
        JOptionPane.showMessageDialog(null,"�����������������");
      }
    }
    else{
      tf1.setText("");
      tf2.setText("");
    }
  }
}
