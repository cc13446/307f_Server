package MyGui;

import App.PrintDetailBill;
import App.PrintInvoice;
import Domain.DetailBill;
import Domain.DetailBillItem;
import MyHttp.HttpRequestModel;
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

    private DetailBill detailBill;
    private int customId;
    private boolean result;
    private JTable jTable;
    private JLabel roomID;
    private JLabel requestOnLabel;
    private JTextArea requestOnTextField;
    private JLabel requestOffLabel;
    private JTextArea requestOffTextField;
    private DefaultTableModel tableModel;

    public CreateDetailBillUI() {
        setTitle("创建详单");
        setBounds(610, 140, 800, 500);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);

        //房间号标签
        roomID = new JLabel("输入房间号:",JLabel.CENTER);
        roomID.setBounds(280,5,100,25);
        add(roomID);

        //房间号输入框
        roomTextField = new JTextField();
        roomTextField.setBounds(420,5,100,25);
        add(roomTextField);

        requestOnLabel = new JLabel("空调开始使用时间:",JLabel.CENTER);
        requestOnLabel.setBounds(280,40,100,25);
        add(requestOnLabel);

        requestOnTextField = new JTextArea();
        requestOnTextField.setBounds(420,40,150,25);
        add(requestOnTextField);

        requestOffLabel = new JLabel("空调结束使用时间:",JLabel.CENTER);
        requestOffLabel.setBounds(280,75,100,25);
        add(requestOffLabel);

        requestOffTextField = new JTextArea();
        requestOffTextField.setBounds(420,75,150,25);
        add(requestOffTextField);

        columnName = new Vector();
        columnName.add("开始时间");
        columnName.add("结束时间");
        columnName.add("模式");
        columnName.add("风速");
        columnName.add("温度");
        columnName.add("持续时长");
        columnName.add("费率");
        columnName.add("费用");

        tableModel=new DefaultTableModel();
        tableModel.setColumnIdentifiers(columnName);

        detailBillButton = new JButton();
        detailBillButton.setText("查看详单");
        detailBillButton.setBounds(280,120,100,30);
        add(detailBillButton);

        printButton=new JButton();
        printButton.setText("打印详单");
        printButton.setBounds(420,120,100,30);
        add(printButton);
        printButton.setEnabled(false);

        detailBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取详单信息
                detailBill=new DetailBill();
                if (roomTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"输入房间号不能为空！");
                }
                if (isNumeric(roomTextField.getText())==false){
                    JOptionPane.showMessageDialog(null,"输入房间号不合法");
                }
                roomId=Integer.parseInt(roomTextField.getText());

                //网络请求
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
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                requestOnTextField.setText(df.format(detailBill.getRequestOnDate()));
                requestOffTextField.setText(df.format(detailBill.getRequestOffDate()));
                JSONArray data=msg.getJSONArray("data");
                for (Object object:data){
                    JSONObject dataJson=(JSONObject)object;
                    DetailBillItem item=new DetailBillItem();
                    try {
                        item.setStartTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataJson.getString("startTime")));
                        item.setEndTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataJson.getString("endTime")));

                    } catch (ParseException parseException) {
                        parseException.printStackTrace();
                    }


                    item.setMode(Mode.values()[dataJson.getInt("mode")]);
                    item.setFanSpeed(FanSpeed.values()[dataJson.getInt("fanSpeed")]);
                    item.setTargetTemp(dataJson.getDouble("targetTemp"));
                    item.setDuration(dataJson.getLong("duration"));
                    item.setFeeRate(dataJson.getDouble("feeRate"));
                    item.setFee(dataJson.getDouble("fee"));
                    detailBillItems.add(item);

                }
                detailBill.setDetailBillList(detailBillItems);
                df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (DetailBillItem item:detailBill.getDetailBillList()){
                    Vector row = new Vector();
                    row.add(df.format(item.getStartTime()));
                    row.add(df.format(item.getEndTime()));
                    row.add(item.getMode());
                    row.add(item.getFanSpeed());
                    row.add(item.getTargetTemp());
                    long ms = item.getDuration();
                    row.add(ms/1000/60/60+"小时"+ms/1000/60%60+"分"+ms/1000%60+"秒");
                    row.add(item.getFeeRate());
                    row.add(item.getFee());
                    tableModel.addRow(row);
                }
                printButton.setEnabled(true);

            }
        });


        jTable=new JTable(tableModel);
        jsp = new JScrollPane(jTable);
        jsp.setBounds(5,160,780,255);
        add(jsp);


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
