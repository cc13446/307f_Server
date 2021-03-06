package MyGui;

import javax.swing.*;

import Domain.PrintReport;
import Domain.Report;
import Domain.ReportForm;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import Enum.*;
import org.apache.commons.lang.StringUtils;

/*
 *  打印报表的UI界面
 */

public class QueryReportUI extends JFrame {
    //房间列表
    private ArrayList<Integer> roomList = new ArrayList<>();
    //请求得到的报表
    private Report report = null;
    //房间号标签
    private JLabel roomID;
    //房间号输入框
    private JTextField roomTextField;
    //添加房间按钮
    private JButton addButton;
    //报表日期标签
    private JLabel reportDate;
    //date选择器
    private final JXDatePicker datePick;
    //报表类型标签
    private JLabel reportTypeLabel;
    //报表类型
    private JComboBox<String> typeReportJBox;
    //房间列表标签
    private JLabel roomListLabel;
    //显示选择的房间
    private JTextArea roomListTextArea;
    //滚动条
    private JScrollPane scrollRoom;
    //添加清空房间列表按钮
    private JButton clearRoomListButton;
    //添加查询报表按钮
    private JButton queryButton;
    //添加打印报表按钮
    private JButton buttonPrintReport;


    //构造方法
    public QueryReportUI() {
        setTitle("请求报表");
        setBounds(610, 140, 350, 340);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);

        //房间号标签
        roomID = new JLabel("输入房间号:", JLabel.CENTER);
        roomID.setBounds(8, 5, 80, 25);
        add(roomID);

        //房间号输入框
        roomTextField = new JTextField();
        roomTextField.setBounds(110, 5, 80, 25);
        add(roomTextField);

        //添加房间按钮
        addButton = new JButton();
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
                        if ((int) o1 > (int) o2) {
                            return 1;
                        } else {
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
        reportDate = new JLabel("报表日期:", JLabel.CENTER);
        reportDate.setBounds(0, 60, 80, 25);
        add(reportDate);

        //date选择器
        datePick = new JXDatePicker();
        datePick.setDate(new Date());// 设置 date日期
        System.out.println(datePick.getDate());
        datePick.setBounds(110, 60, 200, 25);
        add(datePick);

        //报表类型标签
        reportTypeLabel = new JLabel("报表类型:", JLabel.CENTER);
        reportTypeLabel.setBounds(0, 120, 80, 25);
        add(reportTypeLabel);

        //报表类型
        typeReportJBox = new JComboBox<String>(new String[]{"日报", "周报", "月报", "年报"});
        // 设置默认选中的条目
        typeReportJBox.setSelectedIndex(0);
        typeReportJBox.setBounds(110, 120, 200, 25);
        add(typeReportJBox);

        //房间列表标签
        roomListLabel = new JLabel("房间列表:", JLabel.CENTER);
        roomListLabel.setBounds(0, 180, 80, 25);
        add(roomListLabel);

        //显示选择的房间
        roomListTextArea = new JTextArea();
        roomListTextArea.setEditable(false);
        //设置滚动条
        scrollRoom = new JScrollPane(roomListTextArea);
        //分别设置水平和垂直滚动条自动出现
        scrollRoom.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollRoom.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollRoom.setBounds(5, 210, 320, 35);
        add(scrollRoom);

        //添加清空房间列表按钮
        clearRoomListButton = new JButton();
        clearRoomListButton.setText("清空列表");
        clearRoomListButton.setBounds(8, 260, 100, 25);
        clearRoomListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                roomList.clear();
                roomListTextArea.setText("");
            }
        });//添加监听器
        add(clearRoomListButton);

        //添加查询报表按钮
        queryButton = new JButton();
        queryButton.setText("查询报表");
        queryButton.setBounds(118, 260, 100, 25);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();//返回的是某日0点的时间
                date.setTime(datePick.getDate().getTime() + 24 * 3600 * 1000 - 1);//把它变成某日的23:59:59

                if (roomList.size() == 0)
                    JOptionPane.showMessageDialog(null, "无可查询的房间号");
                else {
                    /********************发送网络请求****************************/
                    HttpRequestModel httpRequestModel = new HttpRequestModel();
                    JSONObject json = new JSONObject();

                    //写json包
                    json.put("msgType", 4);
                    json.put("reportDate", new SimpleDateFormat(" yyyy-MM-dd HH:mm:ss ").format(date));
                    json.put("reportType", typeReportJBox.getSelectedIndex());
                    JSONArray room = new JSONArray();
                    for (int roomID : roomList) {
                        JSONObject roomJson = new JSONObject();
                        roomJson.put("roomId", roomID);
                        room.add(roomJson);
                    }
                    json.put("roomList", room);

                    //发送请求
                    JSONArray temp = null;
                    try {
                        temp = httpRequestModel.send1(json);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    if (temp != null) {
                        report = getReportFromJson(temp, date, typeReportJBox.getSelectedIndex());
                        ArrayList<ReportForm> reportFormList = new ArrayList<>();
                        reportFormList = report.getReportFormList();
                        //跳转界面
                        ViewReportUI viewReportUI = new ViewReportUI(reportFormList);
                        viewReportUI.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "发送请求失败");
                    }
                }
            }
        });//添加监听器
        add(queryButton);

        //添加打印报表按钮
        buttonPrintReport = new JButton("打印报表");
        buttonPrintReport.setBounds(228, 260, 100, 25);
        buttonPrintReport.addActionListener(new ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (report == null)
                    JOptionPane.showMessageDialog(null, "无可打印的报表");
                else {
                    PrintReport printReport = new PrintReport(report);
                    if (printReport.printReport()) {
                        JOptionPane.showMessageDialog(null, "报表打印成功");
                    } else
                        JOptionPane.showMessageDialog(null, "报表打印失败");
                }
            }
        });//添加动作监听器
        add(buttonPrintReport);
    }

    //从返回的Json包中解析出Report实例并返回
    public Report getReportFromJson(JSONArray list, Date date, int reportType) {
        Report reportResult = new Report();

        //报表类型
        TypeReport typeReport = TypeReport.DAILY;
        if (reportType == 0)
            typeReport = TypeReport.DAILY;
        if (reportType == 1)
            typeReport = TypeReport.WEEKLY;
        if (reportType == 2)
            typeReport = TypeReport.MONTHLY;
        if (reportType == 3)
            typeReport = TypeReport.ANNUAL;
        reportResult.setTypeReport(typeReport);

        //设置日期
        reportResult.setDate(date);

        //设置ReportForm列表，解析ReportForm里面的各个属性，加入列表
        for (Object o : list) {
            JSONObject json = (JSONObject) o;
            ReportForm reportForm = new ReportForm(json.getInt("turnTimes"),
                    json.getLong("useTime"),
                    json.getDouble("totalFee"),
                    json.getInt("schedulerTimes"),
                    json.getInt("customNumber"),
                    json.getInt("changeTempTimes"),
                    json.getInt("changeFanSpeedTimes"),
                    json.getInt("roomId"));
            reportResult.addReportForm(reportForm);
        }
        return reportResult;
    }
}
