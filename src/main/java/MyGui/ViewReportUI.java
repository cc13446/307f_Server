package MyGui;

import Domain.ReportForm;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Vector;

public class ViewReportUI extends JFrame {
    //rowData存放数据
    //columnName存放列名
    private Vector rowData, columnName;
    private JTable jt = null;
    private JScrollPane jsp = null;

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
        //rowData可存放多行
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
        jsp = new JScrollPane(jt);
        add(jsp);

    }
}
