package MyGui;

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


public class CreateDetailBillUI extends JFrame{
    public CreateDetailBillUI() {
        setTitle("请求报表");
        setBounds(610, 140, 420, 500);//设置窗口大小
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

        //添加按钮
//        JButton addButton = new JButton();
//        addButton.setText("添加房间");
//        addButton.setBounds(300,5,80,30);
//        add(addButton);



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

        //报表类型标签
//        JLabel reportType = new JLabel("报表类型:",JLabel.CENTER);
//        reportType.setBounds(0,120,80,40);
//        add(reportType);
//
//        //报表类型
//        JComboBox<String> typeReport = new JComboBox<String>(new String[]{"日报", "周报", "月报", "年报"});
//        // 设置默认选中的条目
//        typeReport.setSelectedIndex(0);
//        typeReport.setBounds(195,120,200,40);
//        add(typeReport);

        //添加按钮
        JButton queryButton = new JButton();
        queryButton.setText("查看详单");
        queryButton.setBounds(160,170,100,30);
        add(queryButton);

        //输出框
        JTextArea result = new JTextArea();
        result.setEditable(false);

        //设置滚动条
        JScrollPane scroll=new JScrollPane(result);
        //分别设置水平和垂直滚动条自动出现
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(5,205,400,255);
        add(scroll);

    }
}
