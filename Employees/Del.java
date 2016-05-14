
import java.awt.*;
import javax.swing.*;
import java.sql.*;
import java.awt.event.*;


public class Del extends JDialog {
    private JLabel labelName = new JLabel("�û�����");
    private JLabel labelPwd = new JLabel("��  �룺");
    private JTextField tfName = new JTextField("�����û�");
    private JPasswordField tfPwd = new JPasswordField(20);
    private JComboBox box = new JComboBox();
    private JButton bDel = new JButton("ɾ��");
    private JPanel pD = new JPanel();
    private JButton bYes = new JButton("ȷ��");
    private JButton bReset = new JButton("����");
    private JPanel pC = new JPanel(new GridLayout(2,3,10,10));
    private JPanel pS = new JPanel(new GridLayout(2,1,5,5));
    private JPanel pS1 = new JPanel();
    private Connection con;
    private Statement st;
    private ResultSet rs;
    Container cont;
    public Del() {
      JOptionPane.showMessageDialog(null,"ֻ�г����û�����ִ�д˲����������Գ����û���ݵ�½��");
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
        setTitle("ɾ���û�");
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
                rs = st.executeQuery("select * from Admin where adminname='�����û�' and password='"+String.valueOf(tfPwd.getPassword())+"'");
                if(rs.next()){
                    box.setEditable(true);
                    bDel.setEnabled(true);
                    tfPwd.setText("");
                    tfPwd.requestFocus(true);
                    bYes.setEnabled(false);
                    bReset.setEnabled(false);
                    JOptionPane.showMessageDialog(null,"��������������б���ѡ��Ҫɾ�����û���");
                    bDel.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            if(box.getSelectedItem().equals("�����û�")){
                                JOptionPane.showMessageDialog(null,"����ɾ�������û���");
                            }
                            else{
                                int index = JOptionPane.showConfirmDialog(null,"���Ҫɾ����","ȷ��",JOptionPane.YES_NO_OPTION);
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
            JOptionPane.showMessageDialog(null, "������д���룡");
            tfPwd.requestFocus(true);
        }
    }
    public void ok(String s){
        try {
            con = new DBCon().getCon();
            st = con.createStatement();
            st.executeUpdate("delete from Admin where adminname='" + s + "'");
            JOptionPane.showMessageDialog(null, "ɾ�������ɹ���");
            showData();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,"ɾ������ʧ�ܣ�");
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
