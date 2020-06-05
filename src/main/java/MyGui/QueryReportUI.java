package MyGui;

import javax.swing.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Date;

import Enum.*;
import org.apache.commons.lang.StringUtils;

public class QueryReportUI extends JFrame {
    ArrayList<Integer> roomList = new ArrayList<>();//房间列表

    //构造方法
    public QueryReportUI() {
        setTitle("请求报表");
        setBounds(610, 140, 420, 580);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);

        //房间列表标签
        JLabel roomListLabel = new JLabel("房间列表:", JLabel.CENTER);
        roomListLabel.setBounds(0, 200, 80, 40);
        add(roomListLabel);

        //显示选择的房间
        JTextArea roomListTextArea = new JTextArea();
        roomListTextArea.setEditable(false);
        //设置滚动条
        JScrollPane scrollRoom = new JScrollPane(roomListTextArea);
        //分别设置水平和垂直滚动条自动出现
        scrollRoom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollRoom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollRoom.setBounds(5, 240, 400, 35);
        add(scrollRoom);

        //输出框
        JTextArea resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        //设置滚动条
        JScrollPane scroll = new JScrollPane(resultTextArea);
        //分别设置水平和垂直滚动条自动出现
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(5, 285, 400, 255);
        add(scroll);

        //房间号标签
        JLabel roomID = new JLabel("输入房间号:", JLabel.CENTER);
        roomID.setBounds(8, 0, 80, 40);
        add(roomID);

        //房间号输入框
        JTextField roomTextField = new JTextField();
        roomTextField.setBounds(195, 5, 80, 30);
        add(roomTextField);

        //添加房间按钮
        JButton addButton = new JButton();
        addButton.setText("添加房间");
        addButton.setBounds(300, 5, 100, 30);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果输入的是数字，且数字长度未超过int限制，且未包含在房间列表中，加入房间列表
                if (roomTextField.getText().length() < 10 && StringUtils.isNumeric(roomTextField.getText()) && !roomList.contains(Integer.parseInt(roomTextField.getText())))
                    roomList.add(Integer.parseInt(roomTextField.getText()));

                String id = new String();
                for (int roomID : roomList)
                    id += String.valueOf(roomID) + "  ";
                roomListTextArea.setText(id);
            }
        });//添加监听器
        add(addButton);


        //报表日期标签
        JLabel reportDate = new JLabel("报表日期:", JLabel.CENTER);
        reportDate.setBounds(0, 60, 80, 40);
        add(reportDate);

        //date选择器
        final JXDatePicker datePick = new JXDatePicker();
        datePick.setDate(new Date());// 设置 date日期
        System.out.println(datePick.getDate());
        datePick.setBounds(195, 60, 200, 40);
        add(datePick);

        //报表类型标签
        JLabel reportTypeLabel = new JLabel("报表类型:", JLabel.CENTER);
        reportTypeLabel.setBounds(0, 120, 80, 40);
        add(reportTypeLabel);

        //报表类型
        JComboBox<String> typeReportJBox = new JComboBox<String>(new String[]{"日报", "周报", "月报", "年报"});
        // 设置默认选中的条目
        typeReportJBox.setSelectedIndex(0);
        typeReportJBox.setBounds(195, 120, 200, 40);
        add(typeReportJBox);

        //添加按钮
        JButton clearRoomListButton = new JButton();
        clearRoomListButton.setText("清空房间列表");
        clearRoomListButton.setBounds(40, 170, 120, 30);
        clearRoomListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roomList.clear();
                roomListTextArea.setText("");
            }
        });//添加监听器
        add(clearRoomListButton);

        //添加按钮
        JButton queryButton = new JButton();
        queryButton.setText("查询报表");
        queryButton.setBounds(260, 170, 120, 30);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();//返回的是某日0点的时间
                date.setTime(datePick.getDate().getTime() + 24 * 3600 * 1000 - 1);//把它变成某日的23:59:59

                System.out.println(datePick.getDate());
                System.out.println(date);

                TypeReport typeReport = TypeReport.DAILY;//报表类型
                if (typeReportJBox.getSelectedIndex() == 0)
                    typeReport = TypeReport.DAILY;
                if (typeReportJBox.getSelectedIndex() == 1)
                    typeReport = TypeReport.WEEKLY;
                if (typeReportJBox.getSelectedIndex() == 2)
                    typeReport = TypeReport.MONTHLY;
                if (typeReportJBox.getSelectedIndex() == 3)
                    typeReport = TypeReport.ANNUAL;

                //发送网络请求**********************************************************************************
                String reportStr = "";
                reportStr = roomList.toString() + typeReport.toString() + date.toString();
//                reportStr = getIntent(roomList, typeReport, date);
                resultTextArea.setText(reportStr);

            }
        });//添加监听器
        add(queryButton);
    }
}
