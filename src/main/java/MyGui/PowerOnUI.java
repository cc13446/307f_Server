package MyGui;

import javax.swing.*;
import Enum.*;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.chainsaw.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

public class PowerOnUI extends JFrame {
    private double feeRateHigh;
    private double feeRateMid;
    private double feeRateLow;
    private Mode mode;
    private double tempHighLimit;
    private double tempLowLimit;
    private double defaultTargetTemp;

    public PowerOnUI(JFrame relativeWindow) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(relativeWindow);
        setTitle("开机");
        setSize(250, 330);
        setResizable(false);

        String[] modeList = new String[]{"制热", "制冷"};

        // 创建本窗口所有基本组件
        JButton confirmMode = new JButton("确认空调模式");
        JButton confirmPara = new JButton("确认空调初始化参数");
        JComboBox<String> modeComboBox = new JComboBox<String>(modeList);
        JTextField textFeeRateHigh = new JTextField(8);
        JTextField textFeeRateMid = new JTextField(8);
        JTextField textFeeRateLow = new JTextField(8);
        JTextField textTempHighLimit = new JTextField(8);
        JTextField textTempLowLimit = new JTextField(8);
        JTextField textDefaultTargetTemp = new JTextField(8);
        JLabel labelMode = new JLabel("空调模式");
        JLabel labelFeeRateHigh = new JLabel("高风速费率");
        JLabel labelFeeRateMid = new JLabel("中风速费率");
        JLabel labelFeeRateLow = new JLabel("低风速费率");
        JLabel labelTempHighLimit = new JLabel("最高温度");
        JLabel labelTempLowLimit = new JLabel("最低温度");
        JLabel labelDefaultTargetTemp = new JLabel("默认温度");


        // 使用分组布局
        JPanel jp = new JPanel();
        GroupLayout layout = new GroupLayout(jp);
        jp.setLayout(layout);

        // 自动创建组件之间的间隙
        layout.setAutoCreateGaps(true);
        // 自动创建容器与触到容器边框的组件之间的间隙
        layout.setAutoCreateContainerGaps(true);

        // 水平组
        GroupLayout.SequentialGroup hSeqGroup1 = layout.createSequentialGroup().addComponent(labelMode).addComponent(modeComboBox);
        GroupLayout.SequentialGroup hSeqGroup2 = layout.createSequentialGroup().addComponent(labelTempHighLimit).addComponent(textTempHighLimit);
        GroupLayout.SequentialGroup hSeqGroup3 = layout.createSequentialGroup().addComponent(labelTempLowLimit).addComponent(textTempLowLimit);
        GroupLayout.SequentialGroup hSeqGroup4 = layout.createSequentialGroup().addComponent(labelDefaultTargetTemp).addComponent(textDefaultTargetTemp);
        GroupLayout.SequentialGroup hSeqGroup5 = layout.createSequentialGroup().addComponent(labelFeeRateHigh).addComponent(textFeeRateHigh);
        GroupLayout.SequentialGroup hSeqGroup6 = layout.createSequentialGroup().addComponent(labelFeeRateMid).addComponent(textFeeRateMid);
        GroupLayout.SequentialGroup hSeqGroup7 = layout.createSequentialGroup().addComponent(labelFeeRateLow).addComponent(textFeeRateLow);
        GroupLayout.ParallelGroup hParalGroup = layout.createParallelGroup().addGroup(hSeqGroup1).addComponent(confirmMode, GroupLayout.Alignment.CENTER).addGroup(hSeqGroup2).
                addGroup(hSeqGroup3).addGroup(hSeqGroup4).addGroup(hSeqGroup5).addGroup(hSeqGroup6).addGroup(hSeqGroup7).addComponent(confirmPara, GroupLayout.Alignment.CENTER);
        layout.setHorizontalGroup(hParalGroup);

        // 垂直组
        GroupLayout.ParallelGroup vParalGroup1 = layout.createParallelGroup().addComponent(labelMode, GroupLayout.Alignment.CENTER).addComponent(modeComboBox, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup vParalGroup2 = layout.createParallelGroup().addComponent(labelTempHighLimit, GroupLayout.Alignment.CENTER).addComponent(textTempHighLimit, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup vParalGroup3 = layout.createParallelGroup().addComponent(labelTempLowLimit, GroupLayout.Alignment.CENTER).addComponent(textTempLowLimit, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup vParalGroup4 = layout.createParallelGroup().addComponent(labelDefaultTargetTemp, GroupLayout.Alignment.CENTER).addComponent(textDefaultTargetTemp, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup vParalGroup5 = layout.createParallelGroup().addComponent(labelFeeRateHigh, GroupLayout.Alignment.CENTER).addComponent(textFeeRateHigh, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup vParalGroup6 = layout.createParallelGroup().addComponent(labelFeeRateMid, GroupLayout.Alignment.CENTER).addComponent(textFeeRateMid, GroupLayout.Alignment.CENTER);
        GroupLayout.ParallelGroup vParalGroup7 = layout.createParallelGroup().addComponent(labelFeeRateLow, GroupLayout.Alignment.CENTER).addComponent(textFeeRateLow, GroupLayout.Alignment.CENTER);
        GroupLayout.SequentialGroup vSeqGroup = layout.createSequentialGroup().addGroup(vParalGroup1).addComponent(confirmMode).addGroup(vParalGroup2).
                addGroup(vParalGroup3).addGroup(vParalGroup4).addGroup(vParalGroup5).addGroup(vParalGroup6).addGroup(vParalGroup7).addComponent(confirmPara);
        layout.setVerticalGroup(vSeqGroup);

        // 设置默认选中的条目
        modeComboBox.setSelectedIndex(0);
        // 设置默认参数
        feeRateHigh = 1;
        feeRateMid = 0.5;
        feeRateLow = 0.33;
        mode = Mode.HOT;
        tempHighLimit = 25;
        tempLowLimit = 18;
        defaultTargetTemp = 22;
        textTempHighLimit.setText(Double.toString(tempHighLimit));
        textTempLowLimit.setText(Double.toString(tempLowLimit));
        textDefaultTargetTemp.setText(Double.toString(defaultTargetTemp));
        textFeeRateHigh.setText(Double.toString(feeRateHigh));
        textFeeRateMid.setText(Double.toString(feeRateMid));
        textFeeRateLow.setText(Double.toString(feeRateLow));

        // 没有点击确认空调模式按钮，不可修改参数和确认参数
        textTempHighLimit.setEditable(false);
        textTempLowLimit.setEditable(false);
        textDefaultTargetTemp.setEditable(false);
        textFeeRateHigh.setEditable(false);
        textFeeRateMid.setEditable(false);
        textFeeRateLow.setEditable(false);
        confirmPara.setEnabled(false);

        // 添加确认空调模式按钮的点击事件监听器
        confirmMode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 允许修改空调参数和确认参数
                textTempHighLimit.setEditable(true);
                textTempLowLimit.setEditable(true);
                textDefaultTargetTemp.setEditable(true);
                textFeeRateHigh.setEditable(true);
                textFeeRateMid.setEditable(true);
                textFeeRateLow.setEditable(true);
                confirmPara.setEnabled(true);
                System.out.println("确认空调模式按钮被点击");
            }
        });

        // 添加确认空调初始化参数按钮的点击事件监听器
        confirmPara.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 从文本框中获取最终参数
                feeRateHigh = Double.parseDouble(textFeeRateHigh.getText());
                feeRateMid = Double.parseDouble(textFeeRateMid.getText());
                feeRateLow = Double.parseDouble(textFeeRateLow.getText());
                tempHighLimit = Double.parseDouble(textTempHighLimit.getText());
                tempLowLimit = Double.parseDouble(textTempLowLimit.getText());
                defaultTargetTemp = Double.parseDouble(textDefaultTargetTemp.getText());
                mode = modeComboBox.getSelectedIndex() == 0? Mode.COLD: Mode.HOT;

                System.out.println("确认空调初始化参数按钮被点击");
                String printMsg = "mode:" + mode.toString() + " feeRateHigh:" + feeRateHigh + " feeRateMid:" + feeRateMid + " feeRateLow:" + feeRateLow
                        + " tempHighLimit:" + tempHighLimit + " tempLowLimit:" + tempLowLimit + " defaultTargetTemp:" + defaultTargetTemp;
                System.out.println("空调初始化参数为：" + printMsg);

                // 发送信息
                sendPara(relativeWindow);
            }
        });

        modeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                // 处理选中的状态
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    System.out.println("选中: " + modeComboBox.getSelectedIndex() + " = " + modeComboBox.getSelectedItem());
                    // 参数不可修改
                    textTempHighLimit.setEditable(false);
                    textTempLowLimit.setEditable(false);
                    textDefaultTargetTemp.setEditable(false);
                    textFeeRateHigh.setEditable(false);
                    textFeeRateMid.setEditable(false);
                    textFeeRateLow.setEditable(false);
                    confirmPara.setEnabled(false);

                    // 重新显示默认参数
                    feeRateHigh = 1;
                    feeRateMid = 0.5;
                    feeRateLow = 0.33;
                    tempLowLimit = 18;

                    if (modeComboBox.getSelectedIndex() == 1) {
                        mode = Mode.COLD;
                        tempHighLimit = 28;
                        defaultTargetTemp = 25;
                    } else {
                        mode = Mode.HOT;
                        tempHighLimit = 25;
                        defaultTargetTemp = 22;
                    }
                    textTempHighLimit.setText(Double.toString(tempHighLimit));
                    textTempLowLimit.setText(Double.toString(tempLowLimit));
                    textDefaultTargetTemp.setText(Double.toString(defaultTargetTemp));
                    textFeeRateHigh.setText(Double.toString(feeRateHigh));
                    textFeeRateMid.setText(Double.toString(feeRateMid));
                    textFeeRateLow.setText(Double.toString(feeRateLow));
                }
            }
        });

        setContentPane(jp);
        setLocationRelativeTo(null);
    }

    // 发送从UI获得的空调初始化参数
    public void sendPara(JFrame relativeWindow) {
        System.out.println("准备发送空调默认参数");

        HttpRequestModel httpRequestModel = new HttpRequestModel();
        JSONObject json = new JSONObject();

        //写json包
        json.put("msgType", 0);
        json.put("mode", mode.ordinal());
        json.put("tempHighLimit", tempHighLimit);
        json.put("tempLowLimit", tempLowLimit);
        json.put("defaultTargetTemp", defaultTargetTemp);
        json.put("feeRateHigh", feeRateHigh);
        json.put("feeRateMid", feeRateMid);
        json.put("feeRateLow", feeRateLow);

        //发送请求
        JSONObject temp = null;
        try {
            temp = httpRequestModel.send(json);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        if (temp != null) {
            int state = temp.getInt("state");
            if (state == 0) {
                System.out.println("发送空调默认参数成功");
                ((MainUI) relativeWindow).setButtonCheckRoomStateEnabled(true);
                ((MainUI) relativeWindow).setButtonCreateInvoiceEnabled(true);
                ((MainUI) relativeWindow).setButtonCreateRDREnabled(true);
                ((MainUI) relativeWindow).setButtonQueryReportEnabled(true);
            } else {
                System.out.println("发送空调默认参数失败");
            }
        } else {
            JOptionPane.showMessageDialog(null, "发送请求失败");
        }
        dispose();
    }
}
