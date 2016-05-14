

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;

public class peixun extends JInternalFrame{
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
  public peixun() {
    super("员工培训记录", true, true, true, true);
    showInfo();
  }

  public void showInfo() {
    p_North = new JPanel();
    bAdd = new JButton("增加",
                       new ImageIcon(("img\\2.png")));
    bUpdate = new JButton("更新",
                          new ImageIcon(("img\\4.gif")));
    bDel = new JButton("删除",
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
      tm.addColumn("ID");
      tm.addColumn("姓名");
      tm.addColumn("培训内容");
      tm.addColumn("培训地点");
      tm.addColumn("时间");
      tm.addColumn("备注");
      rs = st.executeQuery("select * from peixun");
      while (rs.next()) {
        String str[] = {rs.getString(1),
            rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6)};
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
            st.executeUpdate("delete from peixun where id='" + z + "'");
            JOptionPane.showMessageDialog(null,"删除成功！");
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
        JOptionPane.showMessageDialog(null, "请在列表中选择要删除的列！");
    }
  }
  public void addData(){
    new AddData().setVisible(true);
    showData();
  }
  public void upData(){
    Updata ud = new Updata();
    try {
        Object x = ta.getValueAt(ta.getSelectedRow(), 0);
        final String z = x.toString();
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            ResultSet rs=st.executeQuery("select * from peixun where id='" + z + "'");
            while(rs.next()){
              ud.textfield.setText(rs.getString(1)); 
              ud.box1.setSelectedItem(rs.getString(2));
              ud.jtf1.setText(rs.getString(3));
              ud.jtf2.setText(rs.getString(4));
              ud.tf1.setText(rs.getString(5));
              ud.tf2.setText(rs.getString(6));
              ud.setVisible(true);
              showData();
            }
        } catch (Exception ex) {
        JOptionPane.showMessageDialog(null,"操作失败！");
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
        JOptionPane.showMessageDialog(null, "请在列表中选择要更改的列！");
    }
  }
}
class AddData extends JDialog implements ActionListener{
  JLabel name=new JLabel("姓    名：");
  JComboBox box1 = new JComboBox();
  JLabel depart = new JLabel("培训内容：");
  JTextField jtf1 = new JTextField();
  JLabel posit = new JLabel("培训地点：");
  JTextField jtf2 = new JTextField();
  JLabel pay = new JLabel("时    间：");
  JTextField tf1 = new JTextField();
  JLabel bonus = new JLabel("备    注：");
  JTextField tf2 = new JTextField();
  JButton bYes = new JButton("确    定");
  JButton bNo = new JButton("重    置");
  JPanel pC = new JPanel(new GridLayout(5,3,5,5));
  JPanel pS = new JPanel();
  public AddData(){
    Query();
    name.setHorizontalAlignment(JLabel.RIGHT);
    depart.setHorizontalAlignment(JLabel.RIGHT);
    posit.setHorizontalAlignment(JLabel.RIGHT);
    pay.setHorizontalAlignment(JLabel.RIGHT);
    bonus.setHorizontalAlignment(JLabel.RIGHT);
    pC.setBorder(BorderFactory.createTitledBorder(""));
    pC.add(name);pC.add(box1);pC.add(new JLabel(""));
    pC.add(depart);pC.add(jtf1);pC.add(new JLabel(""));
    pC.add(posit);pC.add(jtf2);pC.add(new JLabel(""));
    pC.add(pay);pC.add(tf1);pC.add(new JLabel(""));
    pC.add(bonus);pC.add(tf2);pC.add(new JLabel(""));
    pS.add(bYes);pS.add(bNo);
    Container con = this.getContentPane();
    con.setLayout(new BorderLayout());
    con.add(pC,BorderLayout.CENTER);
    con.add(pS,BorderLayout.SOUTH);
    this.setTitle("员工培训信息");
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
      st.close();
      cont.close();
    }
    catch (Exception exce) {
      exce.printStackTrace();
    }
  }
  public void actionPerformed(ActionEvent e){
    if(e.getSource().equals(bYes)){
      if(!jtf1.getText().equals("")){
        if(!jtf2.getText().equals("")){
          if (!tf1.getText().trim().equals("")) {
            if (!tf2.getText().trim().equals("")) {
              String sql = "insert into peixun(e_name,e_info,e_address,e_time,remark) values('" +
                  box1.getSelectedItem().toString() + "','" + jtf1.getText() +
                  "','" + jtf2.getText() + "','" + tf1.getText() + "','" +
                  tf2.getText() + "')";
              try {
                Connection cont = new DBCon().getCon();
                Statement st = cont.createStatement();
                st.executeUpdate(sql);
                st.close();
                cont.close();
                JOptionPane.showMessageDialog(null, "操作成功！");
                dispose();
              }
              catch (Exception exc1) {
                JOptionPane.showMessageDialog(null, "操作失败，请联系系统管理员！");
                exc1.printStackTrace();
              }
            }
            else {
              JOptionPane.showMessageDialog(null, "请输入备注！");
              tf2.requestFocus(true);
            }
          }
          else {
            JOptionPane.showMessageDialog(null, "请输入时间！");
            tf1.requestFocus(true);
          }
        }
        else{
          JOptionPane.showMessageDialog(null, "请输入培训地点！");
          jtf2.requestFocus(true);
        }
      }
      else{
        JOptionPane.showMessageDialog(null, "请输入培训内容！");
        jtf1.requestFocus(true);
      }
    }
    else{
      jtf1.setText("");
      jtf2.setText("");
      tf1.setText("");
      tf2.setText("");
    }
  }
}
class Updata extends JDialog implements ActionListener{
  JLabel name=new JLabel("姓    名：");
  JComboBox box1 = new JComboBox();
  JLabel depart = new JLabel("培训内容：");
  JTextField jtf1 = new JTextField();
  JLabel posit = new JLabel("培训地点：");
  JTextField jtf2 = new JTextField();
  JLabel pay = new JLabel("时    间：");
  JTextField tf1 = new JTextField();
  JLabel bonus = new JLabel("备    注：");
  JTextField tf2 = new JTextField();
  JButton bYes = new JButton("确    定");
  JButton bNo = new JButton("重    置");
  JPanel pC = new JPanel(new GridLayout(5,3,5,5));
  JPanel pS = new JPanel();
  JTextField textfield=new JTextField();
  public Updata(){
    Query();
    name.setHorizontalAlignment(JLabel.RIGHT);
    depart.setHorizontalAlignment(JLabel.RIGHT);
    posit.setHorizontalAlignment(JLabel.RIGHT);
    pay.setHorizontalAlignment(JLabel.RIGHT);
    bonus.setHorizontalAlignment(JLabel.RIGHT);
    pC.setBorder(BorderFactory.createTitledBorder(""));
    pC.add(name);pC.add(box1);pC.add(new JLabel(""));
    pC.add(depart);pC.add(jtf1);pC.add(new JLabel(""));
    pC.add(posit);pC.add(jtf2);pC.add(new JLabel(""));
    pC.add(pay);pC.add(tf1);pC.add(new JLabel(""));
    pC.add(bonus);pC.add(tf2);pC.add(new JLabel(""));
    pS.add(bYes);pS.add(bNo);
    Container con = this.getContentPane();
    con.setLayout(new BorderLayout());
    con.add(pC,BorderLayout.CENTER);
    con.add(pS,BorderLayout.SOUTH);
    this.setTitle("员工培训信息");
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
      }rs1.close();
      st.close();
      cont.close();
    }
    catch (Exception exce) {
      exce.printStackTrace();
    }
  }

  public void actionPerformed(ActionEvent e) {
    if (e.getSource().equals(bYes)) {
      if (!jtf1.getText().equals("")) {
        if (!jtf2.getText().equals("")) {
          if (!tf1.getText().trim().equals("")) {
            if (!tf2.getText().trim().equals("")) {
              String sql = "update  peixun set e_name='" +
                  box1.getSelectedItem().toString() + "',e_info='" +
                  jtf1.getText() + "',e_address='" + jtf2.getText() + "',e_time='" +
                  tf1.getText() + "',remark='" + tf2.getText() +
                  "'where id='" + textfield.getText() + "'";
              try {
                Connection cont = new DBCon().getCon();
                Statement st = cont.createStatement();
                st.executeUpdate(sql);
                st.close();
                cont.close();
                JOptionPane.showMessageDialog(null, "操作成功！");
                dispose();
              }
              catch (Exception exception) {
                JOptionPane.showMessageDialog(null, "操作失败，请联系系统管理员！");
                exception.printStackTrace();
              }
            }
            else {
              JOptionPane.showMessageDialog(null, "请输入备注！");
              tf2.requestFocus(true);
            }
          }
          else {
            JOptionPane.showMessageDialog(null, "请输入时间！");
            tf1.requestFocus(true);
          }
        }
        else{
          JOptionPane.showMessageDialog(null, "请输入培训地点！");
          jtf2.requestFocus(true);
        }
      }
      else{
        JOptionPane.showMessageDialog(null, "请输入培训内容！");
        jtf1.requestFocus(true);
      }
    }
    else {
      jtf1.setText("");
      jtf2.setText("");
      tf1.setText("");
      tf2.setText("");
    }
  }
}
