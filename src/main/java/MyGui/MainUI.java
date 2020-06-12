package MyGui;

import Domain.PrintReport;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class MainUI extends JFrame {
    private JPanel centerPane;
    private JLabel administratorLabel;
    private JButton buttonPowerON;
    private JButton buttonCheckRoomState;
    private JLabel receptionLabel;
    private JButton buttonCreateInvoice;
    private JButton buttonCreateRDR;
    private JLabel managerLabel;
    private JButton buttonQueryReport;

    //构造方法
    public MainUI() {
        final MainUI mainUI = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("系统管理主界面");
        setBounds(610, 140, 600, 400);//设置窗口大小
        setLayout(new BorderLayout());

        //中间容器
        centerPane = new JPanel();
        centerPane.setLayout(new GridBagLayout());
        getContentPane().add(centerPane);


        //管理员部分
        //添加标题
        administratorLabel = new JLabel("管理员", JLabel.CENTER);
        administratorLabel.setFont(new Font("楷体", Font.PLAIN, 30));

        //添加开启按钮
        buttonPowerON = new JButton("空调开机");
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
        buttonCheckRoomState = new JButton("查看房间状态");
        buttonCheckRoomState.setFont(new Font("楷体", Font.PLAIN, 24));
        setButtonCheckRoomStateEnabled(false);
        //添加动作监听器
        buttonCheckRoomState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RoomStateUI roomStateWin = new RoomStateUI(mainUI);
                roomStateWin.setVisible(true);
                new Thread(roomStateWin).start();
            }
        });

        //前台部分
        //添加标题
        receptionLabel = new JLabel("前台", JLabel.CENTER);
        receptionLabel.setFont(new Font("楷体", Font.PLAIN, 30));

        //添加创建账单按钮
        buttonCreateInvoice = new JButton("查看账单");
        buttonCreateInvoice.setFont(new Font("楷体", Font.PLAIN, 24));
        setButtonCreateInvoiceEnabled(false);
        buttonCreateInvoice.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                CreateInvoiceUI createInvoiceGUI=new CreateInvoiceUI();
                createInvoiceGUI.setVisible(true);
            }
        });

        //添加创建详单按钮
        buttonCreateRDR = new JButton("查看详单");
        buttonCreateRDR.setFont(new Font("楷体", Font.PLAIN, 24));
        setButtonCreateRDREnabled(false);
        buttonCreateRDR.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                CreateDetailBillUI createDetailBillGUI=new CreateDetailBillUI();
                createDetailBillGUI.setVisible(true);
            }
        });

        //经理部分
        //添加标题
        managerLabel = new JLabel("经理", JLabel.CENTER);
        managerLabel.setFont(new Font("楷体", Font.PLAIN, 30));

        //添加查看报表按钮
        buttonQueryReport = new JButton("查看报表");
        buttonQueryReport.setFont(new Font("楷体", Font.PLAIN, 24));
        setButtonQueryReportEnabled(false);
        buttonQueryReport.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                QueryReportUI queryReport = new QueryReportUI();
                queryReport.setVisible(true);
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
        s.gridheight = 2;
        centerPane.add(buttonCreateInvoice, s);

        s.gridx = 1;
        s.gridy = 3;
        s.gridwidth = 1;
        s.gridheight = 2;
        centerPane.add(buttonCreateRDR, s);

        s.gridx = 2;
        s.gridy = 1;
        s.gridwidth = 1;
        s.gridheight = 4;
        centerPane.add(buttonQueryReport, s);

    }

    public void setButtonCheckRoomStateEnabled(boolean enable) {
        this.buttonCheckRoomState.setEnabled(enable);
    }

    public void setButtonCreateInvoiceEnabled(boolean enable) {
        this.buttonCreateInvoice.setEnabled(enable);
    }

    public void setButtonCreateRDREnabled(boolean enable) {
        this.buttonCreateRDR.setEnabled(enable);
    }

    public void setButtonQueryReportEnabled(boolean enable) {
        this.buttonQueryReport.setEnabled(enable);
    }

    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("msgType", 5);
                try {
                    jsonObject = new HttpRequestModel().send(jsonObject);
                    if(jsonObject.getInt("state") != 0){
                        JOptionPane.showMessageDialog(mainUI, "关闭远程服务器失败", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        JOptionPane.showMessageDialog(mainUI, "关闭远程服务器成功", "恭喜", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(mainUI, "关闭远程服务器失败", "警告", JOptionPane.ERROR_MESSAGE);
                }
                System.exit(0);
            }
        });
        mainUI.setVisible(true);
    }
}
