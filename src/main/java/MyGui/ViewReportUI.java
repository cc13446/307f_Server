package MyGui;

import Domain.ReportForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

/*
 *  查询报表显示的UI界面，采用JTable表格显示数据
 */

public class ViewReportUI extends JFrame {
    //每行数据和各列名字
    private Vector rowData, columnName;
    //显示信息的表格
    private JTable jt = null;
    //表格的拖动条
    private JScrollPane jsp = null;


    //构造方法
    public ViewReportUI(ArrayList<ReportForm> reportFormList){
        setTitle("报表");
        setSize(800, 300);
        setLocation(610, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(false);

        columnName = new Vector();
        //设置列名
        columnName.add("房间号");
        columnName.add("空调开关次数");
        columnName.add("空调服务时长");
        columnName.add("总费用/元");
        columnName.add("被调度的次数");
        columnName.add("旅客人数");
        columnName.add("调温次数");
        columnName.add("调风次数");

        rowData = new Vector();
        //rowData可存放多行，每行显示一个房间的ReportForm里面存储的各项数据
        for (ReportForm reportForm:reportFormList){
            Vector line = new Vector();
            line.add(reportForm.getRoomId());
            line.add(reportForm.getTurnTimes());
            line.add(reportForm.getStringUseTime());
            line.add(reportForm.getTotalFee());
            line.add(reportForm.getSchedulerTimes());
            line.add(reportForm.getCustomerNumber());
            line.add(reportForm.getChangeTempTimes());
            line.add(reportForm.getChangeFanSpeedTimes());
            rowData.add(line);
        }

        //初始化JTable
        jt = new JTable(rowData, columnName);
        jt.setEnabled(false);
        //为JTable加上拖动条
        jsp = new JScrollPane(jt);
        add(jsp);

    }
}
