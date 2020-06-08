package MyGui;

import App.PrintDetailBill;
import App.PrintInvoice;
import Domain.DetailBill;
import Domain.DetailBillItem;
import MyHttp.HttpRequestModel;
import MyListener.CreateDetailBillListener;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.jdesktop.swingx.JXDatePicker;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;
import Enum.Mode;
import Enum.FanSpeed;


public class CreateDetailBillUI extends JFrame{
    private Date dateIn;
    private Date dateOut;
    private int roomId;
    private Vector rowData;
    private final Vector columnName;
    private JTable jt = null;
    private JScrollPane jsp = null;
    private JButton detailBillButton;
    private JButton printButton;
    private JTextField roomTextField;
    private CreateDetailBillListener createDetailBillListener;
    private DetailBill detailBill;
    private int customId;
    private boolean result;
    private JTable jTable;

    public CreateDetailBillUI() {
        setTitle("创建详单");
        setBounds(610, 140, 470, 450);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);


        //房间号标签
        JLabel roomID = new JLabel("输入房间号:",JLabel.CENTER);
        roomID.setBounds(120,0,80,40);
        add(roomID);

        //房间号输入框
        roomTextField = new JTextField();
        roomTextField.setBounds(270,5,100,30);
        add(roomTextField);


        //jt=new JTable();

        columnName = new Vector();
        //设置列名
        //columnName.add("房间号");
        columnName.add("开始时间");
        columnName.add("结束时间");
        columnName.add("模式");
        columnName.add("风速");
        columnName.add("温度");
        columnName.add("持续时长/秒");
        columnName.add("费率/元");
        columnName.add("费用/元");

        DefaultTableModel tableModel=new DefaultTableModel();

        detailBillButton = new JButton();
        detailBillButton.setText("查看详单");
        detailBillButton.setBounds(120,60,100,30);
        add(detailBillButton);


        detailBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取详单信息
                detailBill=new DetailBill();
                if (isNumeric(roomTextField.getText())==false){

                    JOptionPane.showMessageDialog(null,"输入房间号不合法");

                }
                roomId=Integer.parseInt(roomTextField.getText());

                HttpRequestModel httpRequestModel = new HttpRequestModel();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roomId",roomId);
                jsonObject.put("msgType",3);

                JSONObject msg=new JSONObject();

                try {
                    msg=httpRequestModel.send(jsonObject);

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "发送请求失败");
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                ArrayList<DetailBillItem> detailBillItems=new ArrayList<>();
                customId=msg.getInt("customId");
                detailBill.setCustomId(customId);

                try {
                    detailBill.setRequestOnDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOnDate")));
                    detailBill.setRequestOffDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOffDate")));

                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }

                JSONArray data=msg.getJSONArray("data");
                //DetailBillItem item=new DetailBillItem();
                for (Object object:data){
                    JSONObject dataJson=(JSONObject)object;
                    DetailBillItem item=new DetailBillItem();
                    try {
                        item.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataJson.getString("startTime")));
                        item.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataJson.getString("endTime")));

                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }
                    //mode
                    if (dataJson.getInt("mode")==0){
                        item.setMode(Mode.HOT);
                    }
                    else if (dataJson.getInt("mode")==1){
                        item.setMode(Mode.COLD);
                    }
                    else if (dataJson.getInt("mode")==2){
                        item.setMode(Mode.FAN);
                    }
                    //fanspeed

                    item.setFanSpeed(FanSpeed.values()[dataJson.getInt("fanSpeed")]);


                    //targetTemp
                    item.setTargetTemp(dataJson.getDouble("targetTemp"));
                    item.setDuration(dataJson.getLong("duration"));
                    item.setFeeRate(dataJson.getDouble("feeRate"));
                    item.setFee(dataJson.getDouble("fee"));
                    detailBillItems.add(item);



                }
                detailBill.setDetailBillList(detailBillItems);

                rowData = new Vector();

                for (DetailBillItem item:detailBill.getDetailBillList()){
                    Vector row = new Vector();
                    row.add(item.getStartTime());
                    row.add(item.getEndTime());
                    row.add(item.getMode());
                    row.add(item.getFanSpeed());
                    row.add(item.getTargetTemp());
                    row.add(item.getDuration()/1000);
                    row.add(item.getFeeRate());
                    row.add(item.getFee());
                    System.out.println(item.getFee());
                    //rowData.add(row);
                    tableModel.addRow(row);
                }

//                jt=new JTable(rowData,columnName);
//                DefaultTableModel model = new DefaultTableModel(jt, columnName);
//                jt.setModel(model);


            }
        });

        //jt = new JTable(rowData, columnName);
//        jt=new JTable(rowData,columnName);
//        DefaultTableModel model = new DefaultTableModel(jt, columnName);
//        jt.setModel(model);
        tableModel.setColumnIdentifiers(columnName);
        jTable=new JTable(tableModel);
        jsp = new JScrollPane(jTable);
        jsp.setBounds(5,105,440,255);
        add(jsp);

        printButton=new JButton();
        printButton.setText("打印详单");
        printButton.setBounds(270,60,100,30);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //prinrt
                try {
                    PrintDetailBill print=new PrintDetailBill(customId,detailBill);
                    result=print.printDetailBill();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                if (result){
                    JOptionPane.showMessageDialog(null, "详单打印成功");
                }
                else {
                    JOptionPane.showMessageDialog(null, "详单打印失败");
                }
            }
        });
        add(printButton);


    }

    public static boolean isNumeric(String str) {

        for (int i = 0; i < str.length(); i++) {

            System.out.println(str.charAt(i));

            if (!Character.isDigit(str.charAt(i))) {

                return false;

            }

        }

        return true;

    }

}
