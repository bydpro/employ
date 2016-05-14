
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

public class Login4Yuan extends JFrame {
    JPanel contentPane;
    BorderLayout borderLayout1 = new BorderLayout();
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuManager = new JMenu("员工个人信息");
    JMenuItem jMenuUp1 = new JMenuItem("修改密码");
    JMenuItem jMenuInfo = new JMenuItem("查看员工个人信息");
    JMenuItem jMenuExit = new JMenuItem("退出");
    JMenu jMenuHelp = new JMenu("帮助");
    JMenuItem jMenuHelpAbout = new JMenuItem("关于本系统");
    JToolBar jToolBar = new JToolBar();
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
    String idStr="";
    public Login4Yuan(String id) {
        try {
        	idStr = id;
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

        jMenuUp1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ChangePwd().setVisible(true);
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
        jMenuInfo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new chakanemployeeinfo());
            }
        });
        jMenuBar1.add(jMenuManager);
        jMenuManager.add(jMenuUp1);;
        jMenuManager.add(jMenuInfo);;
        jMenuManager.addSeparator();
        jMenuManager.add(jMenuExit);
        jMenuBar1.add(jMenuHelp);
        jMenuHelp.add(jMenuHelpAbout);
        jMenuHelp.addSeparator();
        setJMenuBar(jMenuBar1);
        jMenuInfo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dp.add(new chakanemployeeinfo());
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
