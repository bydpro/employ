
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
    JMenu jMenuManager = new JMenu("�û�����");
    JMenuItem jMenuAdd1 = new JMenuItem("�����û�");
    JMenuItem jMenuUp1 = new JMenuItem("�޸�����");
    JMenuItem jMenuDel1 = new JMenuItem("ɾ���û�");
    JMenuItem jMenuExit = new JMenuItem("�˳�");
    JMenu jMenuEmp = new JMenu("Ա����Ϣ����");
    JMenuItem jMenuAdd2 = new JMenuItem("Ա��������Ϣ");
    //JMenuItem jMenuUp2 = new JMenuItem("�޸�ְ����Ϣ");
    JMenu jMenuPay = new JMenu("���ʹ���");
    //JMenuItem jMenuAdd3 = new JMenuItem("���ӹ��ʽ���");
    JMenuItem jMenuUp3 = new JMenuItem("���ʽ�����Ϣ");
    JMenu jMenuStu = new JMenu("���ڹ���");
    //JMenuItem jMenuAdd4 = new JMenuItem("������Ϣ");
    JMenuItem jMenuUp4 = new JMenuItem("������Ϣ");
    JMenu jMenuQuery = new JMenu("�鿴��Ϣ");
    JMenuItem jMenuQue = new JMenuItem("�鿴Ա����Ϣ");
    JMenu jMenuSystem = new JMenu("��Ҫ��¼");
    JMenuItem jpeixun = new JMenuItem("��ѵ��¼");
    JMenuItem jjiangcheng = new JMenuItem("���ͼ�¼");
    JMenu jMenuHelp = new JMenu("����");
    JMenuItem jMenuHelpAbout = new JMenuItem("���ڱ�ϵͳ");
    JMenuItem jMenuCalc = new JMenuItem("������");
    JMenuItem jMenuNotepad = new JMenuItem("���±�");
    JToolBar jToolBar = new JToolBar();
    JButton bUser = new JButton("�����û�",new ImageIcon(("img\\1.png")));
    JButton bEmp = new JButton("����Ա��",new ImageIcon(("img\\3.gif")));
    JButton bPay = new JButton("�������",new ImageIcon(("img\\2.png")));
    JButton bLeave = new JButton("���ڹ���",new ImageIcon(("img\\4.gif")));
    JButton bEinfo = new JButton("�鿴Ա����Ϣ",new ImageIcon(("img\\yy.png")));
    JButton bHelp = new JButton("����ϵͳ",new ImageIcon(("img\\8.png")));
    JButton bClose = new JButton("�˳�ϵͳ",new ImageIcon(("img\\9.gif")));
    JDesktopPane dp = new JDesktopPane();
    JPanel p_South = new JPanel(new GridLayout(1,4));
    JLabel user = new JLabel();
    JLabel author = new JLabel(" ");//�˴��޸��������� 
    JLabel tel = new JLabel("");//�˴��޸�ѧУ����
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
        setTitle("��С����ҵԱ�����ʹ���ϵͳ");
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int receive = JOptionPane.showConfirmDialog(null,"ȷ��Ҫ�˳�ϵͳ��","ȷ��",JOptionPane.YES_NO_OPTION);
                if(receive==JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        /*******************************
         * �˵���
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
                int receive = JOptionPane.showConfirmDialog(null,"ȷ��Ҫ�˳�ϵͳ��","ȷ��",JOptionPane.YES_NO_OPTION);
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
                    JOptionPane.showMessageDialog(null,"�޷��򿪼���������","����",JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(null,"�޷��򿪼��±�����","����",JOptionPane.ERROR_MESSAGE);
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
         * ������
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
                int receive = JOptionPane.showConfirmDialog(null,"ȷ��Ҫ�˳�ϵͳ��","ȷ��",JOptionPane.YES_NO_OPTION);
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
         * ״̬��
         * ��ʾ��ǰ�û���ϵͳʱ��
         *
         ******************************/
        author.setForeground(Color.BLUE);
        tel.setForeground(Color.BLUE);
        user.setText("��С����ҵԱ�����ʹ���ϵͳ");
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
     * ʱ���ʽ
     *
     *****************************/

    class TimerActionListener implements ActionListener{//�ڲ��࣬��Calengar���SimpleDateFormat��
        public void actionPerformed(ActionEvent e){        //format�������ַ�����ʽ��ʱ�䣨���뵥λ��
            Calendar now = Calendar.getInstance();
            SimpleDateFormat sdfdate = new SimpleDateFormat("yyyy:MM:dd");
            SimpleDateFormat sdftime = new SimpleDateFormat("HH:mm:ss  E");
            String s1 = sdfdate.format(now.getTime());
            String s2 = sdftime.format(now.getTime());
            labeltime.setText(s1+" "+s2);
        }
    }
}
