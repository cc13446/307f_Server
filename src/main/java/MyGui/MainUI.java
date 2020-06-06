package MyGui;

import Domain.PrintReport;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI extends JFrame {
    //构造方法
    public MainUI() {
        final JFrame mainUI = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("系统管理主界面");
        setBounds(610, 140, 600, 400);//设置窗口大小
        setLayout(new BorderLayout());
//        setLayout(new GridBagLayout());

        //中间容器
        JPanel centerPane = new JPanel();
        centerPane.setLayout(new GridBagLayout());
        getContentPane().add(centerPane);


        //管理员部分
        //添加标题
        JLabel administratorLabel = new JLabel("管理员", JLabel.CENTER);
        administratorLabel.setFont(new Font("楷体", Font.PLAIN, 30));

        //添加开启按钮
        JButton buttonPowerON = new JButton("PowerON");
        buttonPowerON.setFont(new Font("楷体", Font.PLAIN, 24));
        //添加动作监听器
        buttonPowerON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PowerOnUI powerOnWin = new PowerOnUI(mainUI);
                powerOnWin.setVisible(true);
            }
        });

        //添加查看房间状态按钮
        JButton buttonCheckRoomState = new JButton("查看房间状态");
        buttonCheckRoomState.setFont(new Font("楷体", Font.PLAIN, 24));
        //添加动作监听器
        buttonCheckRoomState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomStateUI roomStateWin = new RoomStateUI(mainUI);
                roomStateWin.setVisible(true);
            }
        });

        //前台部分
        //添加标题
        JLabel receptionLabel = new JLabel("前台", JLabel.CENTER);
        receptionLabel.setFont(new Font("楷体", Font.PLAIN, 30));

        //添加创建账单按钮
        JButton buttonCreateInvoice = new JButton("创建账单");
        buttonCreateInvoice.setFont(new Font("楷体", Font.PLAIN, 24));
        //buttonCreateRDR.addActionListener();//添加动作监听器
        buttonCreateInvoice.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                CreateInvoiceUI createInvoiceGUI=new CreateInvoiceUI();
                createInvoiceGUI.setVisible(true);
            }
        });

        //添加打印账单按钮
        JButton buttonPrintInvoice = new JButton("打印账单");
        buttonPrintInvoice.setFont(new Font("楷体", Font.PLAIN, 24));
        buttonPrintInvoice.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PrintInvoiceUI printInvoiceUI=new PrintInvoiceUI();
                printInvoiceUI.setVisible(true);
            }
        });

        //添加创建详单按钮
        JButton buttonCreateRDR = new JButton("创建详单");
        buttonCreateRDR.setFont(new Font("楷体", Font.PLAIN, 24));
        buttonCreateRDR.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                CreateDetailBillUI createDetailBillGUI=new CreateDetailBillUI();
                createDetailBillGUI.setVisible(true);
                //System.out.println("t");
            }
        });
//        buttonCreateRDR.addActionListener();//添加动作监听器

        //添加打印详单按钮
        JButton buttonPrintRDR = new JButton("打印详单");
        buttonPrintRDR.setFont(new Font("楷体", Font.PLAIN, 24));
        buttonPrintRDR.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                PrintInvoiceUI printInvoiceUI=new PrintInvoiceUI();
                printInvoiceUI.setVisible(true);
            }
        });


        //经理部分
        //添加标题
        JLabel managerLabel = new JLabel("经理", JLabel.CENTER);
        managerLabel.setFont(new Font("楷体", Font.PLAIN, 30));

        //添加查看报表按钮
        JButton buttonQueryReport = new JButton("查看报表");
        buttonQueryReport.setFont(new Font("楷体", Font.PLAIN, 24));
        buttonQueryReport.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                mainUI.setEnabled(false);//设置本窗口不可选中
                QueryReportUI queryReport = new QueryReportUI(mainUI);
                queryReport.setVisible(true);
            }
        });//添加动作监听器

        //添加打印报表按钮
        JButton buttonPrintReport = new JButton("打印报表");
        buttonPrintReport.setFont(new Font("楷体", Font.PLAIN, 24));
        buttonPrintReport.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
//                mainUI.setEnabled(false);
                PrintReport printReport = new PrintReport();
                if (printReport.printReport()) {
                    JOptionPane.showMessageDialog(null, "报表打印成功");
                } else
                    JOptionPane.showMessageDialog(null, "报表打印失败");
            }
        });//添加动作监听器

        //设置布局

        GridBagConstraints s = new GridBagConstraints();//定义一个GridBagConstraints，用来控制添加进的组件的显示位置
        s.fill = GridBagConstraints.BOTH;//组件填满所在区域
        s.weightx = 1;
        s.weighty = 1;//组件会随窗口拉伸而拉伸

        s.gridx = 0;
        s.gridy = 0;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(administratorLabel, s);

        s.gridx = 1;
        s.gridy = 0;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(receptionLabel, s);

        s.gridx = 2;
        s.gridy = 0;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(managerLabel, s);

        s.fill = GridBagConstraints.NONE;//组件不填满所在区域

        s.gridx = 0;
        s.gridy = 1;
        s.gridwidth = 1;
        s.gridheight = 2;
        centerPane.add(buttonPowerON, s);

        s.gridx = 0;
        s.gridy = 3;
        s.gridwidth = 1;
        s.gridheight = 2;
        centerPane.add(buttonCheckRoomState, s);

        s.gridx = 1;
        s.gridy = 1;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(buttonCreateInvoice, s);

        s.gridx = 1;
        s.gridy = 2;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(buttonPrintInvoice, s);

        s.gridx = 1;
        s.gridy = 3;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(buttonCreateRDR, s);

        s.gridx = 1;
        s.gridy = 4;
        s.gridwidth = 1;
        s.gridheight = 1;
        centerPane.add(buttonPrintRDR, s);

        s.gridx = 2;
        s.gridy = 1;
        s.gridwidth = 1;
        s.gridheight = 2;
        centerPane.add(buttonQueryReport, s);

        s.gridx = 2;
        s.gridy = 3;
        s.gridwidth = 1;
        s.gridheight = 2;
        centerPane.add(buttonPrintReport, s);

    }

    public static void main(String[] args) {
        MainUI a = new MainUI();
        a.setVisible(true);
    }
}
