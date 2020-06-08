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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class RoomStateUI extends JFrame {
    // 创建表头
    private final Vector<String> columnNames = new Vector<String>();
    private JPanel jp;
    private JTable roomStateTable;
    private JButton flush;

    public RoomStateUI(JFrame relativeWindow) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(relativeWindow);
        setTitle("Room State");
        setSize(600, 400);

        // 设置表头
        columnNames.add("房间号");
        columnNames.add("用户ID");
        columnNames.add("房间状态");
        columnNames.add("当前温度");
        columnNames.add("目标温度");
        columnNames.add("风速");
        columnNames.add("当前费率");
        columnNames.add("总费用");
        columnNames.add("总服务时间");

        jp = new JPanel(new BorderLayout());
        jp.setSize(300,400);
        // 创建表格
        roomStateTable = new JTable();
        requestRoomState();

        flush = new JButton("刷新房间状态");
        flush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 刷新数据
                System.out.println("点击刷新数据按钮");
                requestRoomState();
            }
        });

        // 表格的显示
        jp.add(roomStateTable.getTableHeader(), BorderLayout.NORTH);
        jp.add(roomStateTable, BorderLayout.CENTER);
        jp.add(flush, BorderLayout.SOUTH);

        setContentPane(jp);
        setLocationRelativeTo(null);
    }

    private void requestRoomState() {
        /********************发送网络请求****************************/
        HttpRequestModel httpRequestModel = new HttpRequestModel();
        JSONObject json = new JSONObject();

        //写json包
        json.put("msgType", 1);

        //发送请求
        JSONArray temp = null;
        try {
            temp = httpRequestModel.send1(json);
            System.out.println(temp);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        if (temp != null) {
            getRoomStateFromJson(temp);
        } else {
            JOptionPane.showMessageDialog(null, "发送请求失败");
        }
    }

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
            room.add(Integer.toString(json.getInt("fanSpeed")));
            room.add(Double.toString(json.getDouble("feeRate")));
            room.add(Double.toString(json.getDouble("fee")));
            room.add(Long.toString(json.getLong("duration")));
            roomStateData.add(room.clone());
        }
        DefaultTableModel model = new DefaultTableModel(roomStateData, columnNames);
        roomStateTable.setModel(model);
    }
}
