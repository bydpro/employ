
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;

public class chakanemployeeinfo  extends JInternalFrame{
  private DefaultTableModel tm;
  private JTable ta;
  private JScrollPane sr;
  private JButton bQuery;
  private JButton bAll;
  private JPanel p_North;
  private Connection con;
  private Statement st;
  private ResultSet rs;
  public chakanemployeeinfo() {
    super("员工信息", true, true, true, true);
    showInfo();
  }

  public void showInfo() {
    p_North = new JPanel();
    bQuery = new JButton("查询",
                         new ImageIcon(("img\\2.png")));
    bAll = new JButton("全部",
                       new ImageIcon(("img\\yy.png")));
    p_North.add(new JLabel("按编号查询"));
    final JTextField tf = new JTextField(15);
    p_North.add(tf);
    p_North.add(bQuery);
    p_North.add(bAll);
    tm = new DefaultTableModel();
    showData();
    ta = new JTable(tm);
    sr = new JScrollPane(ta);
    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(p_North, BorderLayout.NORTH);
    cont.add(sr, BorderLayout.CENTER);
    this.setSize(800,600 );
    this.setVisible(true);
    bQuery.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if(!tf.getText().trim().equals("")){
          try{
            Integer.parseInt(tf.getText().trim());
            queryData(tf.getText().trim());
          }
          catch(Exception ex){
            JOptionPane.showMessageDialog(null,"员工编号只能为整数！");
            tf.requestFocus(true);
          }
        }
        else{
          JOptionPane.showMessageDialog(null,"请输入要查询员工的编号！");
          tf.requestFocus(true);
        }
      }
    });
    bAll.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        showData();
        tf.setText("");
        tf.requestFocus(true);
      }
    });
  }
  public void queryData(String s) {
    tm.setColumnCount(0);
    tm.setRowCount(0);
    try {
      con = new DBCon().getCon();
      st = con.createStatement();
      tm.addColumn("编 号");
      tm.addColumn("姓 名");
      tm.addColumn("性 别");
      tm.addColumn("年 龄");
      tm.addColumn("民 族");
      tm.addColumn("电 话");
      tm.addColumn("地 址");
      tm.addColumn("部门名称");
      tm.addColumn("职位名称");
      tm.addColumn("基本工资");
      tm.addColumn("奖 金");
      tm.addColumn("备 注");
      rs = st.executeQuery("select a.e_id,a.e_name,a.sex,a.age,a.nation,a.phone,a.address,a.department_id,a.position_id,p.pay,p.bonus,a.remark from employee_info a,pay_info p where a.e_name=p.e_name and a.e_id='"+s+"'");
      while (rs.next()) {
        String str[] = {rs.getString(1) ,
            rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
            rs.getString(10), rs.getString(11), rs.getString(12)};
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
  public void showData() {
    tm.setColumnCount(0);
    tm.setRowCount(0);
    try {
      con = new DBCon().getCon();
      st = con.createStatement();
      tm.addColumn("编  号");
      tm.addColumn("姓  名");
      tm.addColumn("性  别");
      tm.addColumn("年  龄");
      tm.addColumn("民  族");
      tm.addColumn("电  话");
      tm.addColumn("地  址");
      tm.addColumn("部门名称");
      tm.addColumn("职位名称");
      tm.addColumn("基本工资");
      tm.addColumn("奖  金");
      tm.addColumn("备  注");
      rs = st.executeQuery("select a.e_id,a.e_name,a.sex,a.age,a.nation,a.phone,a.address,a.department_id,a.position_id,p.pay,p.bonus,a.remark from employee_info a,pay_info p where a.e_name=p.e_name");
      while (rs.next()) {
        String str[] = {
            rs.getString(1),
            rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
            rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
            rs.getString(10), rs.getString(11), rs.getString(12)};
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
}
