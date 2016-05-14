
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.Timer;

public class EmployeeMSFrame extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuManager = new JMenu("用户管理");
    JMenuItem jMenuAdd1 = new JMenuItem("增加用户");
    JMenuItem jMenuUp1 = new JMenuItem("修改密码");
    JMenuItem jMenuDel1 = new JMenuItem("删除用户");
    JMenuItem jMenuExit = new JMenuItem("退出");
    JMenu jMenuEmp = new JMenu("员工信息管理");
    JMenuItem jMenuAdd2 = new JMenuItem("员工基本信息");
    //JMenuItem jMenuUp2 = new JMenuItem("修改职工信息");
    JMenu jMenuPay = new JMenu("工资管理");
    //JMenuItem jMenuAdd3 = new JMenuItem("增加工资奖金");
    JMenuItem jMenuUp3 = new JMenuItem("工资奖金信息");
    JMenu jMenuStu = new JMenu("出勤管理");
    //JMenuItem jMenuAdd4 = new JMenuItem("出勤信息");
    JMenuItem jMenuUp4 = new JMenuItem("出勤信息");
    JMenu jMenuQuery = new JMenu("查看信息");
    JMenuItem jMenuQue = new JMenuItem("查看员工信息");
    JMenu jMenuSystem = new JMenu("重要记录");
    JMenuItem jpeixun = new JMenuItem("培训记录");
    JMenuItem jjiangcheng = new JMenuItem("奖惩记录");
    JMenu jMenuHelp = new JMenu("帮助");
    JMenuItem jMenuHelpAbout = new JMenuItem("关于本系统");
    JMenuItem jMenuCalc = new JMenuItem("计算器");
    JMenuItem jMenuNotepad = new JMenuItem("记事本");
    JToolBar jToolBar = new JToolBar();
    JButton bUser = new JButton("增加用户",new ImageIcon(("img\\1.png")));
    JButton bEmp = new JButton("增加员工",new ImageIcon(("img\\3.gif")));
    JButton bPay = new JButton("奖金管理",new ImageIcon(("img\\2.png")));
    JButton bLeave = new JButton("出勤管理",new ImageIcon(("img\\4.gif")));
    JButton bEinfo = new JButton("查看员工信息",new ImageIcon(("img\\yy.png")));
    JButton bHelp = new JButton("关于系统",new ImageIcon(("img\\8.png")));
    JButton bClose = new JButton("退出系统",new ImageIcon(("img\\9.gif")));
    JDesktopPane dp = new JDesktopPane();
    JPanel p_South = new JPanel(new GridLayout(1,4));
    JLabel user = new JLabel();
    JLabel author = new JLabel(" ");//此处修改作者名称 
    JLabel tel = new JLabel("");//此处修改学校名称
    JLabel labeltime = new JLabel();
    TimerActionListener ta = new TimerActionListener();
    Timer t = new Timer(500,ta);
    public EmployeeMSFrame() {
        try {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception {
        t.start();	
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(borderLayout1);   
        setSize(new Dimension(890, 600));
        setTitle("中小型企业员工工资管理系统");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int receive = JOptionPane.showConfirmDialog(null,"确定要退出系统吗？","确认",JOptionPane.YES_NO_OPTION);
                if(receive==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        /*******************************
         * 菜单栏
         *
         ******************************/
        jMenuAdd1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AddUser().setVisible(true);
            }
        });
        jMenuUp1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ChangePwd().setVisible(true);
            }
        });
        jMenuDel1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new Del().setVisible(true);
            }
        });
        jMenuAdd2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               dp.add(new AddEmployee());
            }
        });
        jMenuUp3.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            dp.add(new Pay());
          }
        });
        jMenuUp4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new Chuqin());
            }
        });
        jMenuQue.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new chakanemployeeinfo());
            }
        });
        jpeixun.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              dp.add(new peixun());
            }
        });
        jjiangcheng.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
              dp.add(new jiangcheng());
            }
        });
        jMenuExit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int receive = JOptionPane.showConfirmDialog(null,"确定要退出系统吗？","确认",JOptionPane.YES_NO_OPTION);
                if(receive==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        jMenuHelpAbout.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AboutMessage().setVisible(true);
            }
        });
        jMenuCalc.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    Runtime.getRuntime().exec("calc.exe");
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"无法打开计算器程序！","错误",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        jMenuNotepad.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try{
                    Runtime.getRuntime().exec("notepad.exe");
                }
                catch(Exception ex){
                    JOptionPane.showMessageDialog(null,"无法打开计事本程序！","错误",JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });
        jMenuBar1.add(jMenuManager);
        jMenuManager.add(jMenuAdd1);
        jMenuManager.add(jMenuUp1);
        jMenuManager.add(jMenuDel1);
        jMenuManager.addSeparator();
        jMenuManager.add(jMenuExit);
        jMenuBar1.add(jMenuEmp);
        jMenuEmp.add(jMenuAdd2);
        //jMenuEmp.add(jMenuUp2);
        jMenuBar1.add(jMenuPay);
        //jMenuPay.add(jMenuAdd3);
        jMenuPay.add(jMenuUp3);
        jMenuBar1.add(jMenuStu);
        //jMenuStu.add(jMenuAdd4);
        jMenuStu.add(jMenuUp4);
        jMenuBar1.add(jMenuQuery);
        jMenuQuery.add(jMenuQue);
        jMenuBar1.add(jMenuSystem);
        jMenuSystem.add(jpeixun);
        jMenuSystem.add(jjiangcheng);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuHelp.addSeparator();
        jMenuHelp.add(jMenuCalc);
        jMenuHelp.add(jMenuNotepad);
        setJMenuBar(jMenuBar1);
        /*******************************
         * 工具栏
         *
         ******************************/
        bUser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	dp.add(new  UserMng());
            }
        });
        bEmp.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new AddEmployee());
            }
        });
        bPay.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new Pay());
            }
        });
        bLeave.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new Chuqin());
            }
        });
        bEinfo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new chakanemployeeinfo());
            }
        });
        bHelp.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new AboutMessage().setVisible(true);
            }
        });
        bClose.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int receive = JOptionPane.showConfirmDialog(null,"确定要退出系统吗？","确认",JOptionPane.YES_NO_OPTION);
                if(receive==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        jToolBar.add(bUser);
        jToolBar.add(bEmp);
        jToolBar.add(bPay);
        jToolBar.add(bLeave);
        jToolBar.add(bEinfo);
       // jToolBar.add(bBeifen);
        jToolBar.add(bHelp);
        jToolBar.add(bClose);
        contentPane.add(jToolBar, BorderLayout.NORTH);
        contentPane.add(dp,BorderLayout.CENTER);
        /*******************************
         * 状态栏
         * 显示当前用户、系统时间
         *
         ******************************/
        author.setForeground(Color.BLUE);
        tel.setForeground(Color.BLUE);
        user.setText("中小型企业员工工资管理系统");
        user.setForeground(Color.BLUE);
        user.setHorizontalAlignment(JLabel.CENTER);
        author.setHorizontalAlignment(JLabel.CENTER);
        tel.setHorizontalAlignment(JLabel.CENTER);
        labeltime.setHorizontalAlignment(JLabel.CENTER);
        user.setBorder(BorderFactory.createLoweredBevelBorder());
        author.setBorder(BorderFactory.createLoweredBevelBorder());
        tel.setBorder(BorderFactory.createLoweredBevelBorder());
        labeltime.setForeground(Color.BLUE);
        labeltime.setBorder(BorderFactory.createLoweredBevelBorder());
        p_South.add(user);
        p_South.add(author);
        p_South.add(tel);
        p_South.add(labeltime);
        contentPane.add(p_South,BorderLayout.SOUTH);
    }
    /*****************************
     * 时间格式
     *
     *****************************/

    class TimerActionListener implements ActionListener{//内部类，用Calengar类和SimpleDateFormat的
        public void actionPerformed(ActionEvent e){        //format方法将字符串形式的时间（毫秒单位）
            Calendar now = Calendar.getInstance();
            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy:MM:dd");
            SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss  E");
            String s1 = sdfdate.format(now.getTime());
            String s2 = sdftime.format(now.getTime());
            labeltime.setText(s1+" "+s2);
        }
    }
}
