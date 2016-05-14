
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * 显示软件有关信息的对话框
 */
class AboutMessage extends JDialog{

  JPanel panelNorth = new JPanel(new GridLayout(1,1));
  JPanel panelCenter = new JPanel(new FlowLayout());
  JLabel labelNorth = new JLabel("  ");
  Icon icon = new ImageIcon(("img\\7878.jpg"));
  JLabel label_C_N = new JLabel(icon);
  JPanel panel_C_N = new JPanel();
  JLabel label_C_W1 = new JLabel("欢     迎");
  JLabel label_C_W2 = new JLabel("使     用");
  //JLabel label_C_W3 = new JLabel("品     服");
  //JLabel label_C_W4 = new JLabel("质     务");
  JPanel panel_C_W = new JPanel(new GridLayout(4,1));
  JLabel label_C_C1 = new JLabel("");
  JLabel label_C_C2 = new JLabel("");
  JLabel label_C_C3 = new JLabel("");
  JLabel label_C_C4 = new JLabel("");
  JLabel label_C_C5 = new JLabel("");
  JLabel label_C_C6 = new JLabel("");
  JLabel label_C_C7 = new JLabel("version:      1.0");
  JLabel label_C_C8 = new JLabel("Copyright  (c)  毕业设计");
  JPanel pane = new JPanel();
  JButton bYes = new JButton("关闭");
  JPanel panel_C_C = new JPanel(new GridLayout(9,1));
  JPanel p = new JPanel();
  Font f = new Font("楷书",Font.BOLD,45);
  Font f1 = new Font("楷书",Font.BOLD,15);
  public AboutMessage() {

    labelNorth.setFont(new Font("楷书",Font.BOLD|Font.HANGING_BASELINE,40));//字体楷书，粗体，斜体，40像素
    labelNorth.setForeground(Color.RED);
    labelNorth.setHorizontalAlignment(JLabel.LEFT);
    panelNorth.setBackground(Color.WHITE);
    label_C_N.setPreferredSize(new Dimension(600,100));
    panel_C_N.add(label_C_N);
    panel_C_N.setBackground(Color.WHITE);
    //panelCenter.add(panel_C_N,BorderLayout.NORTH);
    label_C_W1.setFont(f);
    label_C_W1.setForeground(Color.RED);
    label_C_W2.setFont(f);
    label_C_W2.setForeground(Color.RED);
   //label_C_W3.setFont(f);
   // label_C_W3.setForeground(Color.RED);
    //label_C_W4.setFont(f);
    //label_C_W4.setForeground(Color.RED);
    panel_C_W.setBackground(Color.WHITE);
    panel_C_W.add(label_C_W1);
    panel_C_W.add(label_C_W2);
   // panel_C_W.add(label_C_W3);
   // panel_C_W.add(label_C_W4);
    panelCenter.add(panel_C_W);
    p.setPreferredSize(new Dimension(30,30));
    p.setBackground(Color.WHITE);
    panelCenter.add(p);
    pane.setBackground(Color.WHITE);
    label_C_C1.setFont(f1);
    label_C_C1.setForeground(Color.BLUE);
    label_C_C2.setFont(f1);
    label_C_C2.setForeground(Color.BLUE);
    label_C_C3.setFont(f1);
    label_C_C3.setForeground(Color.BLUE);
    label_C_C4.setFont(f1);
    label_C_C4.setForeground(Color.BLUE);
    label_C_C5.setFont(f1);
    label_C_C5.setForeground(Color.BLUE);
    label_C_C6.setFont(f1);
    label_C_C6.setForeground(Color.BLUE);
    label_C_C7.setFont(f1);
    label_C_C7.setForeground(Color.BLUE);
    label_C_C8.setFont(f1);
    label_C_C8.setForeground(Color.BLUE);
    panel_C_C.setBackground(Color.WHITE);
    panel_C_C.add(label_C_C1);
    panel_C_C.add(label_C_C2);
    panel_C_C.add(label_C_C3);
    panel_C_C.add(label_C_C4);
    panel_C_C.add(label_C_C5);
    panel_C_C.add(label_C_C6);
    panel_C_C.add(label_C_C7);
    panel_C_C.add(label_C_C8);
    panel_C_C.add(pane);
    pane.add(bYes);
    panelCenter.add(panel_C_C);
    panelCenter.setBackground(Color.WHITE);
    panelNorth.add(labelNorth);
    Container cont = this.getContentPane();
    cont.setLayout(new BorderLayout());
    cont.add(panelNorth,BorderLayout.NORTH);
    cont.add(panel_C_N,BorderLayout.CENTER);
    cont.add(panelCenter,BorderLayout.SOUTH);
    this.setSize(600,450);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension th = this.getSize();
    if(th.width>d.width){
        th.width=d.width;
    }
    if(th.height>d.height){
        th.height=d.height;
    }
    this.setLocation((d.width-th.width)/2,(d.height-th.height)/2);
    this.setResizable(false);
    this.setTitle("关于本系统");
    this.setModal(true);
    bYes.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
            dispose();
        }
    });
  }
}
