package MyGui;

import Domain.Report;
import Domain.ReportForm;
import jxl.write.Label;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;

public class ViewReportUI extends JFrame {
    //rowData存放数据
    //columnName存放列名
    private Vector rowData, columnName;
    private JTable jt = null;
    private JScrollPane jsp = null;

    public ViewReportUI(JFrame queryReportUI, ArrayList<ReportForm> reportFormList){
        setTitle("报表");
        setSize(800, 300);
        setLocation(610, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(false);

        //本窗口关闭后，设置原窗口可选中
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                //设置启用
                queryReportUI.setEnabled(true);
            }
        });

        columnName = new Vector();
        //设置列名
        columnName.add("房间号");
        columnName.add("房间开关次数");
        columnName.add("空调使用时长");
        columnName.add("总费用");
        columnName.add("被调度的次数");
        columnName.add("详单数");
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
            line.add(reportForm.getDetailBillNumber());
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
