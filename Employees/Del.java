
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;


public class Del extends JDialog {
    private JLabel labelName = new JLabel("用户名：");
    private JLabel labelPwd = new JLabel("密  码：");
    private JTextField tfName = new JTextField("超级用户");
    private JPasswordField tfPwd = new JPasswordField(20);
    private JComboBox box = new JComboBox();
    private JButton bDel = new JButton("删除");
    private JPanel pD = new JPanel();
    private JButton bYes = new JButton("确定");
    private JButton bReset = new JButton("重置");
    private JPanel pC = new JPanel(new GridLayout(2,3,10,10));
    private JPanel pS = new JPanel(new GridLayout(2,1,5,5));
    private JPanel pS1 = new JPanel();
    private Connection con;
    private Statement st;
    private ResultSet rs;
    Container cont;
    public Del() {
      JOptionPane.showMessageDialog(null,"只有超级用户才能执行此操作，请先以超级用户身份登陆！");
      tfName.setFont(new Font("",Font.BOLD,16));
      tfName.setForeground(Color.BLUE);
        labelName.setHorizontalAlignment(JLabel.RIGHT);
        labelPwd.setHorizontalAlignment(JLabel.RIGHT);
        tfName.setEditable(false);
        pC.add(labelName);
        pC.add(tfName);
        pC.add(new JLabel());
        pC.add(labelPwd);
        pC.add(tfPwd);
        pC.add(new JLabel());
        pS1.add(bYes);
        pS1.add(bReset);
        pD.add(box);
        pD.add(bDel);
        showData();
        bDel.setEnabled(false);
        pS.add(pS1);
        pS.add(pD);
        pC.setBorder(BorderFactory.createTitledBorder(""));
        cont = this.getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(pC,BorderLayout.CENTER);
        cont.add(pS,BorderLayout.SOUTH);
        setTitle("删除用户");
        setSize(300,200);
        setModal(true);
        setResizable(false);
        getRootPane().setDefaultButton(bYes);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension th = this.getSize();
        setLocation((d.width -th.width)/2,(d.height-th.height)/2);

        bYes.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                delManager();
            }
        });
        bReset.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                tfPwd.setText("");
                tfPwd.requestFocus(true);
            }
        });
    }
    public void showData(){
        box.removeAllItems();
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            rs = st.executeQuery("select * from Admin");
            while(rs.next()){
                box.addItem(rs.getString(2));
            }
            box.setEditable(false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
    public void delManager(){
        if (!String.valueOf(tfPwd.getPassword()).equals("")) {
            try {
                con = new DBCon().getCon();
                st = con.createStatement();
                rs = st.executeQuery("select * from Admin where adminname='超级用户' and password='"+String.valueOf(tfPwd.getPassword())+"'");
                if(rs.next()){
                    box.setEditable(true);
                    bDel.setEnabled(true);
                    tfPwd.setText("");
                    tfPwd.requestFocus(true);
                    bYes.setEnabled(false);
                    bReset.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"请在下面的下拉列表中选择要删除的用户！");
                    bDel.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            if(box.getSelectedItem().equals("超级用户")){
                                JOptionPane.showMessageDialog(null,"不能删除超级用户！");
                            }
                            else{
                                int index = JOptionPane.showConfirmDialog(null,"真的要删除吗？","确认",JOptionPane.YES_NO_OPTION);
                                if(index==JOptionPane.YES_OPTION){
                                    String s = String.valueOf(box.
                                            getSelectedItem()).trim();
                                    ok(s);
                                }
                            }
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(rs!=null){
                    try{
                        rs.close();
                    }catch(Exception e){
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
        } else {
            JOptionPane.showMessageDialog(null, "必须填写密码！");
            tfPwd.requestFocus(true);
        }
    }
    public void ok(String s){
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            st.executeUpdate("delete from Admin where adminname='" + s + "'");
            JOptionPane.showMessageDialog(null, "删除操作成功！");
            showData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"删除操作失败！");
            ex.printStackTrace();
        }
        finally{
            if(st!=null){
              try{
                  st.close();
              }catch(Exception e){
                  e.printStackTrace();
              }
            }
            if(con!=null){
                try{
                    con.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
