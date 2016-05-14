

import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.table.*;

public class AddEmployee extends JInternalFrame {
private DefaultTableModel tm;
private JTable ta;
private JScrollPane sr;
private JButton bAdd;
private JButton bUpdate;
private JButton bDel;
private JPanel p_North;
private Connection con;
private Statement st;
private ResultSetMetaData rmd;
private PreparedStatement ps;
private ResultSet rs;
int index=0;
public AddEmployee() {
    super("员工基本信息", true, true, true, true);
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
            upDate();
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
        ps = con.prepareStatement("select * from employee_info");
        st = con.createStatement();
        rmd = ps.getMetaData();
        tm.addColumn("员工编号");tm.addColumn("姓名");tm.addColumn("性别");tm.addColumn("出生日期");tm.addColumn("年龄");tm.addColumn("民族");
        tm.addColumn("电话");tm.addColumn("地址");tm.addColumn("部门");tm.addColumn("职位");tm.addColumn("备注");
        rs = st.executeQuery("select * from employee_info");
        while (rs.next()) {
            String str[] = {rs.getString(1), rs.getString(2),
                           rs.getString(3), rs.getString(4), rs.getString(5),
                           rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)};
            tm.addRow(str);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
public void addData() {
    new AddData("增加","增加员工信息").setVisible(true);
}

public void upDate() {
    index=1;
    try {
        Object x = ta.getValueAt(ta.getSelectedRow(), 1);
        final String z = x.toString();
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            rs = st.executeQuery("select * from employee_info where e_name='" + z + "'");
            rs.next();
            AddData ad = new AddData("更改","更改员工信息");
            ad.tf1.setText(rs.getString(2));
            ad.tf2.setSelectedItem(rs.getString(3));
            ad.tf3.setText(rs.getString(4));
            ad.tf4.setText(rs.getString(5));
            ad.tf5.setText(rs.getString(6));
            ad.tf6.setText(rs.getString(7));
            ad.tf7.setText(rs.getString(8));
            ad.tf8.setText(rs.getString(9));
            ad.tf9.setText(rs.getString(10));
            ad.tf11.setText(rs.getString(11));
            ad.tf2.setEditable(false);
            ad.bContinue.setEnabled(false);
            ad.bYes.setText("确定");
            ad.bReset.setEnabled(false);
            ad.setVisible(true);
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
        JOptionPane.showMessageDialog(null, "请在列表中选择要更改的列！");
    }
}

public void delData() {
    try {
        Object x = ta.getValueAt(ta.getSelectedRow(), 1);
        final String z = x.toString();
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            st.executeUpdate("delete from employee_info where e_name='" + z + "'");
            st.executeUpdate("delete from chuqin_info where e_name='" + z + "'");
            st.executeUpdate("delete from pay_info where e_name='" + z + "'");
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
/**
 * 内部类
 * 执行增加记录操作
 */
class AddData extends JDialog implements ActionListener {
    private JLabel labelName = new JLabel("姓      名：");
    private JLabel labelSex = new JLabel("性      别：");
    private JLabel labelbirthday = new JLabel("出生日期：");
    private JLabel labelage = new JLabel("年      龄：");
    private JLabel labelTitle = new JLabel("民      族：");
    private JLabel labelTel = new JLabel("电      话：");
    private JLabel labelAddress = new JLabel("住      址：");
    private JLabel labeldepartid = new JLabel("部      门：");
    private JLabel labelposid = new JLabel("职      务：");
    private JLabel labelremark = new JLabel("备       注：");
    private JTextField tf1 = new JTextField(30);
    private JComboBox tf2 = new JComboBox();
    private JTextField tf3 = new JTextField(30);
    private JTextField tf4 = new JTextField(30);
    private JTextField tf5 = new JTextField(30);
    private JTextField tf6 = new JTextField(30);
    private JTextField tf7 = new JTextField(30);
    private JTextField tf8 = new JTextField(30);
    private JTextField tf9 = new JTextField(30);
    private JTextArea tf11 = new JTextArea(5,30);
    private JButton bContinue = new JButton("增加继续",
                                            new ImageIcon(("img\\2.png")));
    private JButton bYes = new JButton("增加结束",
                                       new ImageIcon(("img\\4.gif")));
    private JButton bReset = new JButton("重置",
                                         new ImageIcon(("img\\1.png")));
    private JButton bExit = new JButton("取消",
                                        new ImageIcon(("img\\yy.png")));
    private JPanel p_C_Center = new JPanel(new GridLayout(6, 4, 0, 5));
    private JPanel p_C_C = new JPanel(new BorderLayout());
    private JPanel p_C_C_South = new JPanel(new BorderLayout());
    private JPanel p_C_South = new JPanel(new BorderLayout());
    private JPanel p_Center = new JPanel(new BorderLayout());
    private JPanel p_South = new JPanel();
    private Connection con;
    private Statement st;
    String s1,s2;
    public AddData(String str1,String str2) {
        this.s1=str1;
        this.s2=str2;
        tf2.addItem("男");
        tf2.addItem("女");
        labelName.setHorizontalAlignment(JLabel.RIGHT);
        labelSex.setHorizontalAlignment(JLabel.RIGHT);
        labelbirthday.setHorizontalAlignment(JLabel.RIGHT);
        labelage.setHorizontalAlignment(JLabel.RIGHT);
        labelTitle.setHorizontalAlignment(JLabel.RIGHT);
        labelTel.setHorizontalAlignment(JLabel.RIGHT);
        labelAddress.setHorizontalAlignment(JLabel.RIGHT);
        labeldepartid.setHorizontalAlignment(JLabel.RIGHT);
        labelposid.setHorizontalAlignment(JLabel.RIGHT);
        labelremark.setHorizontalAlignment(JLabel.RIGHT);
        Container con = this.getContentPane();
        con.add(p_Center, BorderLayout.CENTER);
        con.add(p_South, BorderLayout.SOUTH);
        setSize(500, 350);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension th = this.getSize();
        setLocation((d.width - th.width) / 2, (d.height - th.height) / 2);
        setTitle(s1);
        setModal(true);
        setResizable(false);
        p_Center.setBorder(BorderFactory.createTitledBorder(s2));
        p_Center.add(p_C_C, BorderLayout.CENTER);
        p_Center.add(p_C_South, BorderLayout.SOUTH);
        p_C_C.add(p_C_Center, BorderLayout.CENTER);
        p_C_C.add(p_C_C_South, BorderLayout.SOUTH);
        p_C_Center.add(labelName);
        p_C_Center.add(tf1);
        p_C_Center.add(labelSex);
        p_C_Center.add(tf2);
        p_C_Center.add(labelbirthday);
        p_C_Center.add(tf3);
        p_C_Center.add(labelage);
        p_C_Center.add(tf4);
        p_C_Center.add(labelTitle);
        p_C_Center.add(tf5);
        p_C_Center.add(labelTel);
        p_C_Center.add(tf6);
        p_C_Center.add(labelAddress);
        p_C_Center.add(tf7);
        p_C_Center.add(labeldepartid);
        p_C_Center.add(tf8);
        p_C_Center.add(labelposid);
        p_C_Center.add(tf9);
        p_C_Center.add(new JLabel(""));
        p_C_Center.add(new JLabel(""));
        p_C_Center.add(new JLabel("时间格式：",JLabel.RIGHT));
        p_C_Center.add(new JLabel("20060522"));
        p_C_Center.add(new JLabel(""));
        p_C_Center.add(new JLabel(""));
        p_C_C_South.add(labelremark, BorderLayout.WEST);
        p_C_C_South.add(tf11, BorderLayout.CENTER);
        p_South.add(bContinue);
        p_South.add(bYes);
        p_South.add(bReset);
        p_South.add(bExit);

        bContinue.addActionListener(this);
        bYes.addActionListener(this);
        bReset.addActionListener(this);
        bExit.addActionListener(this);
    }
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource().equals(bContinue)) {
            if (tf1.getText().equals("") || tf3.getText().equals("") ||
                tf4.getText().equals("") ||
                tf5.getText().equals("") || tf6.getText().equals("") ||
                tf7.getText().equals("") || tf8.getText().equals("") ||
                tf9.getText().equals("") || tf11.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "必须填写所有项目！", "提示",
                                              JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    int i = Integer.parseInt(tf3.getText());
                    int j = Integer.parseInt(tf4.getText());
                    try {
                        con = new DBCon().getCon();
                        st = con.createStatement();
                           st.executeUpdate(
                             "insert into employee_info(e_name,sex,birthday,age,nation,phone,address,department_id,position_id,remark)  values('" +
                                tf1.getText() + "','" +
                                tf2.getSelectedItem().toString() +
                                "','" + tf3.getText() + "','" +
                                tf4.getText() + "','" +
                                tf5.getText() +
                                "','" + tf6.getText() + "','" +
                                tf7.getText() + "','"+tf8.getText()+"','"+tf9.getText()+"','"+tf11.getText()+"')");
                        JOptionPane.showMessageDialog(null, "增加记录成功！");
                        clear();
                        showData();
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请正确输入出生日期或年龄！");
                    tf3.requestFocus(true);
                }
            }
        } else if (ev.getSource().equals(bYes)) {
            if (tf1.getText().equals("") || tf3.getText().equals("") || tf4.getText().equals("") ||
                tf5.getText().equals("") || tf6.getText().equals("") ||
                tf7.getText().equals("") || tf8.getText().equals("") ||
                tf9.getText().equals("") ||tf11.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "必须填写所有项目！", "提示",
                                              JOptionPane.WARNING_MESSAGE);
            } else {
                try {
                    int i = Integer.parseInt(tf3.getText());
                    int j = Integer.parseInt(tf4.getText());
                    try {
                        con = new DBCon().getCon();
                        st = con.createStatement();
                        if(index==0){
                            st.executeUpdate(
                                    "insert into employee_info(e_name,sex,birthday,age,nation,phone,address,department_id,position_id,remark) values('" +
                                    tf1.getText() + "','" +
                                    tf2.getSelectedItem().toString() +
                                    "','" + tf3.getText() + "','" +
                                    tf4.getText() + "','" +
                                    tf5.getText() +
                                    "','" + tf6.getText() + "','" + tf7.getText() +
                                    "','"+tf8.getText()+"','"+tf9.getText()+"','"+tf11.getText()+"')");
                            JOptionPane.showMessageDialog(null, "增加记录成功！");
                        }
                        else if(index==1){
                           st.executeUpdate("update employee_info set e_name='"+tf1.getText()+"',sex='"+tf2.getSelectedItem().toString()+"',birthday='"+tf3.getText()+"',age='"+tf4.getText()+"',nation='"+tf5.getText()+"',phone='"+tf6.getText()+"',address='"+tf7.getText()+"',department_id='"+tf8.getText()+"',position_id='"+tf9.getText()+"',remark='"+tf11.getText()+"' where e_name='"+tf1.getText()+"'");
                           JOptionPane.showMessageDialog(null,"更改成功！");
                           index=0;
                       }
                      clear();
                      showData();
                      dispose();
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
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "请正确输入出生日期或年龄！");
                    tf3.setText("");
                    tf3.requestFocus(true);
                }
            }
        } else if (ev.getSource().equals(bReset)) {
            clear();
        } else {
            dispose();
        }
    }
    public void clear() {
        tf1.setText("");
        tf1.requestFocus(true);
        tf3.setText("");
        tf4.setText("");
        tf5.setText("");
        tf6.setText("");
        tf7.setText("");
        tf8.setText("");
        tf9.setText("");
        tf11.setText("");
    }
}
}
