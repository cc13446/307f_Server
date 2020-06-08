package MyGui;

import Domain.Report;
import Domain.ReportForm;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RoomStateUI extends JFrame {
    // 创建表头
    private final Object[] columnNames = {"房间号", "用户ID", "房间状态", "当前温度", "目标温度", "风速", "当前费率", "总费用", "总服务时间"};

    // 获取表格数据
    private Object[][] roomState = {

    };
    JTable roomStateTable = null;

    public RoomStateUI(JFrame relativeWindow) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(relativeWindow);
        setTitle("Room State");
        setSize(600, 400);

        JPanel jp = new JPanel(new BorderLayout());
        DefaultTableModel model = new DefaultTableModel(roomState, columnNames);
        // 创建表格
        roomStateTable = new JTable();
        roomStateTable.setModel(model);

        JButton flush = new JButton("刷新房间状态");
        flush.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 刷新数据
                System.out.println("点击刷新数据按钮");

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
                    getRoomStateFromJson(temp, roomStateTable);
                } else {
                    JOptionPane.showMessageDialog(null, "发送请求失败");
                }
            }
        });

        // 表格的显示
        jp.add(roomStateTable.getTableHeader(), BorderLayout.NORTH);
        jp.add(roomStateTable, BorderLayout.CENTER);
        jp.add(flush, BorderLayout.SOUTH);

        setContentPane(jp);
        pack();
        setLocationRelativeTo(null);
    }

    private void getRoomStateFromJson(JSONArray list, JTable roomStateTable) {
        int count = 0;
        for (Object o : list) {
            JSONObject json = (JSONObject) o;
            System.out.println(o);
            DefaultTableModel model = (DefaultTableModel) roomStateTable.getModel();
            model.addRow(columnNames);
            roomStateTable.getModel().setValueAt(json.getInt("roomID"), count, 0);
            roomStateTable.getModel().setValueAt(json.getInt("customerID"), count, 1);
            roomStateTable.getModel().setValueAt(json.getInt("state"), count, 2);
            roomStateTable.getModel().setValueAt(json.getDouble("currentTemp"), count, 3);
            roomStateTable.getModel().setValueAt(json.getDouble("targetTemp"), count, 4);
            roomStateTable.getModel().setValueAt(json.getInt("fanSpeed"), count, 5);
            roomStateTable.getModel().setValueAt(json.getDouble("feeRate"), count, 6);
            roomStateTable.getModel().setValueAt(json.getDouble("fee"), count, 7);
            roomStateTable.getModel().setValueAt(json.getLong("duration"), count, 8);
            count++;
        }
    }
}
