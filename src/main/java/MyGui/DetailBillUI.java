package MyGui;

import Domain.DetailBill;
import Domain.DetailBillItem;
import Domain.ReportForm;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Vector;

public class DetailBillUI extends JFrame{
    private Vector rowData, columnName;
    private JTable jt = null;
    private JScrollPane jsp = null;

    public DetailBillUI(){
        //int roomId=detailBill.getRoomId();
        int roomId=1;
        setTitle("房间"+String.valueOf(roomId)+"的详单");
        setSize(800, 300);
        setLocation(610, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(false);

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
        add(jsp);
    }
}
