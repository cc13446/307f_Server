package MyGui;

import Domain.DetailBill;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.util.Date;
import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.Vector;


public class CreateDetailBillUI extends JFrame{
    private Date dateIn;
    private Date dateOut;
    private int roomId;
    private Vector columnName,rowData;
    private JTable jt = null;
    private JScrollPane jsp = null;
    public CreateDetailBillUI() {
        setTitle("创建详单");
        setBounds(610, 140, 450, 500);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);


        //房间号标签
        JLabel roomID = new JLabel("输入房间号:",JLabel.CENTER);
        roomID.setBounds(8,0,80,40);
        add(roomID);

        //房间号输入框
        JTextField roomTextField = new JTextField();
        roomTextField.setBounds(195,5,80,30);
        add(roomTextField);


        //报表日期标签
        JLabel reportDateIn = new JLabel("入住日期:",JLabel.CENTER);
        reportDateIn.setBounds(0,60,80,40);
        add(reportDateIn);

        //date选择器
        final JXDatePicker dateInPick = new JXDatePicker();
        dateInPick.setDate(new Date());// 设置 date日期
        dateInPick.setBounds(195,60,200,40);
        add(dateInPick);

        JLabel reportDateOut = new JLabel("退房日期:",JLabel.CENTER);
        reportDateOut.setBounds(0,120,80,40);
        add(reportDateOut);

        //date选择器
        final JXDatePicker dateOutPick = new JXDatePicker();
        dateOutPick.setDate(new Date());// 设置 date日期
        dateOutPick.setBounds(195,120,200,40);
        add(dateOutPick);


        //添加按钮
        JButton detailBillButton = new JButton();
        detailBillButton.setText("查看详单");
        detailBillButton.setBounds(160,170,100,30);
        add(detailBillButton);


        detailBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取详单信息
                DetailBill detailBill=new DetailBill();
//        detailBill.setDateIn(dateInPick.getDate());
//        detailBill.setDateOut(dateOutPick.getDate());
//        detailBill.setRoomId(Integer.parseInt(roomTextField.getText()));
            }
        });


        columnName = new Vector();
        //设置列名
        //columnName.add("房间号");
        columnName.add("模式");
        columnName.add("风速");
        columnName.add("温度");
        columnName.add("开始时间");
        columnName.add("结束时间");
        columnName.add("持续时长");
        columnName.add("费率");
        columnName.add("费用");

        rowData = new Vector();


        //初始化JTable
        jt = new JTable(rowData, columnName);
        jsp = new JScrollPane(jt);
        jsp.setBounds(5,205,440,255);
        add(jsp);

    }
}
