package MyGui;

import javax.swing.*;

import Domain.Report;
import Domain.ReportForm;
import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import Enum.*;
import org.apache.commons.lang.StringUtils;

public class QueryReportUI extends JFrame {
    ArrayList<Integer> roomList = new ArrayList<>();//房间列表

    //构造方法
    public QueryReportUI(JFrame mainUI) {
        JFrame queryReportUI = this;
        setTitle("请求报表");
        setBounds(610, 140, 350, 340);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);

        //本窗口关闭后，设置原窗口可选中
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //设置启用
                mainUI.setEnabled(true);
            }
        });

        //房间列表标签
        JLabel roomListLabel = new JLabel("房间列表:", JLabel.CENTER);
        roomListLabel.setBounds(0, 180, 80, 25);
        add(roomListLabel);

        //显示选择的房间
        JTextArea roomListTextArea = new JTextArea();
        roomListTextArea.setEditable(false);
        //设置滚动条
        JScrollPane scrollRoom = new JScrollPane(roomListTextArea);
        //分别设置水平和垂直滚动条自动出现
        scrollRoom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollRoom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollRoom.setBounds(5, 210, 320, 35);
        add(scrollRoom);

        //房间号标签
        JLabel roomID = new JLabel("输入房间号:", JLabel.CENTER);
        roomID.setBounds(8, 5, 80, 25);
        add(roomID);

        //房间号输入框
        JTextField roomTextField = new JTextField();
        roomTextField.setBounds(110, 5, 80, 25);
        add(roomTextField);

        //添加房间按钮
        JButton addButton = new JButton();
        addButton.setText("添加房间");
        addButton.setBounds(220, 5, 100, 25);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //如果输入的是数字，且数字长度未超过int限制，且未包含在房间列表中，加入房间列表
                if (roomTextField.getText().length() < 10 && StringUtils.isNumeric(roomTextField.getText()) && !roomList.contains(Integer.parseInt(roomTextField.getText())))
                    roomList.add(Integer.parseInt(roomTextField.getText()));
                else if (roomTextField.getText().length() >= 10 || !StringUtils.isNumeric(roomTextField.getText()))
                    JOptionPane.showMessageDialog(null, "输入的数字不是合法的房间号");
                else
                    JOptionPane.showMessageDialog(null, "输入的房间号重复");

                //对房间序号进行排序
                Collections.sort(roomList, new Comparator<Integer>() {
                    @Override
                    public int compare(Integer o1, Integer o2) {
                        if((int)o1 > (int)o2) {
                            return 1;
                        }
                        else {
                            return -1;
                        }
                    }
                });

                //编辑框显示房间号序列
                String id = new String();
                for (int roomID : roomList)
                    id += String.valueOf(roomID) + "  ";
                roomListTextArea.setText(id);
            }
        });//添加监听器
        add(addButton);


        //报表日期标签
        JLabel reportDate = new JLabel("报表日期:", JLabel.CENTER);
        reportDate.setBounds(0, 60, 80, 25);
        add(reportDate);

        //date选择器
        final JXDatePicker datePick = new JXDatePicker();
        datePick.setDate(new Date());// 设置 date日期
        System.out.println(datePick.getDate());
        datePick.setBounds(110, 60, 200, 25);
        add(datePick);

        //报表类型标签
        JLabel reportTypeLabel = new JLabel("报表类型:", JLabel.CENTER);
        reportTypeLabel.setBounds(0, 120, 80, 25);
        add(reportTypeLabel);

        //报表类型
        JComboBox<String> typeReportJBox = new JComboBox<String>(new String[]{"日报", "周报", "月报", "年报"});
        // 设置默认选中的条目
        typeReportJBox.setSelectedIndex(0);
        typeReportJBox.setBounds(110, 120, 200, 25);
        add(typeReportJBox);

        //添加清空房间列表按钮
        JButton clearRoomListButton = new JButton();
        clearRoomListButton.setText("清空房间列表");
        clearRoomListButton.setBounds(40, 260, 120, 25);
        clearRoomListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roomList.clear();
                roomListTextArea.setText("");
            }
        });//添加监听器
        add(clearRoomListButton);

        //添加查询报表按钮
        JButton queryButton = new JButton();
        queryButton.setText("查询报表");
        queryButton.setBounds(190, 260, 120, 25);
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
                ArrayList<ReportForm> reportFormList = new ArrayList<>();
                reportFormList = test(roomList, typeReport, date);

                //跳转界面
                queryReportUI.setEnabled(false);//设置本窗口不可选中
                ViewReportUI viewReportUI = new ViewReportUI(queryReportUI,reportFormList);
                viewReportUI.setVisible(true);

            }
        });//添加监听器
        add(queryButton);
    }

    //测试方法
    public ArrayList<ReportForm> test(ArrayList<Integer> roomList, TypeReport typeReport, Date date) {
        ArrayList<ReportForm> list = new ArrayList<>();
        for (Integer i:roomList){
            ReportForm reportForm = new ReportForm();
            reportForm.setRoomId(i);
            list.add(reportForm);
        }
        return list;
    }

}
