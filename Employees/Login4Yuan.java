
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
    JMenu jMenuManager = new JMenu("Ա��������Ϣ");
    JMenuItem jMenuUp1 = new JMenuItem("�޸�����");
    JMenuItem jMenuInfo = new JMenuItem("�鿴Ա��������Ϣ");
    JMenuItem jMenuExit = new JMenuItem("�˳�");
    JMenu jMenuHelp = new JMenu("����");
    JMenuItem jMenuHelpAbout = new JMenuItem("���ڱ�ϵͳ");
    JToolBar jToolBar = new JToolBar();
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

        jMenuUp1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                new ChangePwd().setVisible(true);
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
                int receive = JOptionPane.showConfirmDialog(null,"ȷ��Ҫ�˳�ϵͳ��","ȷ��",JOptionPane.YES_NO_OPTION);
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
