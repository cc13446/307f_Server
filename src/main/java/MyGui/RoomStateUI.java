package MyGui;

import Domain.Report;
import Domain.ReportForm;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import Enum.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

/**
 * 查看房间状态的UI界面：用于显示所有房间状态，和提供刷新房间状态的按钮
 * 最后修改时间：2020/6/12 19：22
 */

public class RoomStateUI extends JFrame implements Runnable {
    // 创建表头
    private final Vector<String> columnNames = new Vector<String>();
    // 中间容器
    private JPanel jp;
    // 表格部件
    private JTable roomStateTable;
    // 刷新表格状态的按钮
    private JButton flush;
    // 持续向服务器请求房间状态，直到窗口关闭
    private boolean keepRequest = true;

    public RoomStateUI(JFrame relativeWindow) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(relativeWindow);
        setTitle("Room State");
        setSize(700, 400);

        // 设置表头
        columnNames.add("房间号");
        columnNames.add("用户ID");
        columnNames.add("空调状态");
        columnNames.add("当前温度/°C");
        columnNames.add("目标温度/°C");
        columnNames.add("风速");
        columnNames.add("当前费率/元");
        columnNames.add("总费用/元");
        columnNames.add("总服务时间/秒");

        jp = new JPanel(new BorderLayout());
        jp.setSize(700,400);

        // 创建表格
        roomStateTable = new JTable();
        // 请求房间数据，会显示在表格中
        requestRoomState();

        // 刷新房间状态按钮
        flush = new JButton("刷新房间状态");
        // 点击按钮刷新数据
        flush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 刷新数据
                System.out.println("点击刷新数据按钮");
                requestRoomState();
            }
        });

        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                keepRequest = false;
                super.windowClosing(e);
            }
        });

        // 表格的显示
        jp.add(roomStateTable.getTableHeader(), BorderLayout.NORTH);
        jp.add(roomStateTable, BorderLayout.CENTER);
        jp.add(flush, BorderLayout.SOUTH);

        setContentPane(jp);
        setLocationRelativeTo(null);
    }

    @Override
    public void run() {
        while(keepRequest) {
            System.out.println("查询房间状态");
            requestRoomState();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // 发送请求，获得服务器上的房间数据
    private void requestRoomState() {
        /********************发送网络请求****************************/
        HttpRequestModel httpRequestModel = new HttpRequestModel();
        JSONObject json = new JSONObject();

        //写json包
        json.put("msgType", 1);

        //发送请求
        JSONArray temp = null;
        try {
            // temp是服务器返回的json包
            temp = httpRequestModel.send1(json);
            System.out.println(temp);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        if (temp != null) {
            // 将json包中的内容解析到表格中
            getRoomStateFromJson(temp);
        } else {
            JOptionPane.showMessageDialog(null, "发送请求失败");
        }
    }

    // 将json包中的内容解析到表格中
    private void getRoomStateFromJson(JSONArray list) {
        Vector roomStateData = new Vector();
        for (Object o : list) {
            JSONObject json = (JSONObject) o;
            System.out.println(o);

            Vector<String> room = new Vector<String>();
            room.add(Integer.toString(json.getInt("roomID")));
            room.add(Integer.toString(json.getInt("customerID")));
            State state = State.values()[json.getInt("state")];
            room.add(state.toString());
            room.add(Double.toString(json.getDouble("currentTemp")));
            room.add(Double.toString(json.getDouble("targetTemp")));
            FanSpeed speed = FanSpeed.values()[json.getInt("fanSpeed")];
            room.add(speed.toString());
            room.add(Double.toString(json.getDouble("feeRate")));
            room.add(Double.toString(json.getDouble("fee")));
            room.add(Long.toString((json.getLong("duration")) / 1000));
            roomStateData.add(room.clone());
        }
        DefaultTableModel model = new DefaultTableModel(roomStateData, columnNames);
        roomStateTable.setModel(model);
    }
}
