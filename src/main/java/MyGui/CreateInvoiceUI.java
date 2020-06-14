package MyGui;

import App.PrintInvoice;
import Domain.Invoice;
import MyHttp.HttpRequestModel;
import net.sf.json.JSONObject;
import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateInvoiceUI extends JFrame{
    //房间号
    private int roomId;
    //账单
    private Invoice invoice;
    //打印是否成功结果
    private boolean result;
    //房间号
    private JLabel roomID;
    //房间号输入框
    private JTextField roomTextField;
    //开始使用时间标签
    private JLabel requestOnLabel;
    //开始使用时间文本框
    private JTextArea requestOnTextField;
    //结束使用时间标签
    private JLabel requestOffLabel;
    //结束使用时间文本框
    private JTextArea requestOffTextField;
    //总费用标签
    private JLabel invoiceText;
    //总费用文本框
    private JTextArea fee;
    //打印账单按钮
    private JButton printButton;
    //查看账单按钮
    private JButton queryButton;


    public CreateInvoiceUI(){
        setTitle("查看账单");
        setBounds(610, 140, 370, 290);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);

        //创建所有控件
        roomID = new JLabel("输入房间号:",JLabel.CENTER);
        roomTextField = new JTextField();
        requestOnLabel = new JLabel("空调开始使用时间:",JLabel.CENTER);
        requestOnTextField = new JTextArea();
        requestOffLabel = new JLabel("空调结束使用时间:",JLabel.CENTER);
        requestOffTextField = new JTextArea();
        invoiceText = new JLabel("共需交费￥:",JLabel.CENTER);
        fee = new JTextArea();
        printButton = new JButton();
        queryButton = new JButton();


        //房间号标签
        roomID.setBounds(8,5,150,25);
        add(roomID);

        //房间号输入框
        roomTextField.setBounds(180,5,150,25);
        add(roomTextField);

        //开始使用时间标签
        requestOnLabel.setBounds(8,40,150,25);
        add(requestOnLabel);
        //开始使用时间显示框
        requestOnTextField.setBounds(180,40,150,25);
        add(requestOnTextField);

        //结束使用时间标签
        requestOffLabel.setBounds(8,75,150,25);
        add(requestOffLabel);
        //结束使用时间显示框
        requestOffTextField.setBounds(180,75,150,25);
        add(requestOffTextField);

        //总费用标签
        invoiceText.setBounds(8,110,150,25);
        add(invoiceText);
        //总费用显示框
        fee.setText("0.00");
        fee.setEditable(false);
        fee.setBounds(180,110,150,25);
        add(fee);

        //打印账单按钮
        printButton.setText("打印账单");
        printButton.setBounds(180,145,100,30);
        printButton.setEnabled(false);

        //查看总费用 按钮
        queryButton.setText("查看总费用");
        queryButton.setBounds(60,145,100,30);

        //查看总费用按钮的监听
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查总费用
                invoice=new Invoice();
                roomId=Integer.parseInt(roomTextField.getText());

                //处理不合法输入
                if (!isNumeric(roomTextField.getText())){
                    JOptionPane.showMessageDialog(null,"输入房间号不合法");
                }
                if (roomTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"输入房间号不能为空！");
                }

                System.out.println("房间号"+roomId);
                //网络请求
                HttpRequestModel httpRequestModel = new HttpRequestModel();
                JSONObject jsonObject = new JSONObject();
                //写json包
                jsonObject.put("roomId",roomId);
                jsonObject.put("msgType",2);

                try {
                    JSONObject msg=httpRequestModel.send(jsonObject);
                    System.out.println(msg);
                    //账单对象中的数据
                    invoice.setCustomId(msg.getInt("customId"));
                    invoice.setRequestOnDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOnDate")));
                    invoice.setRequestOffDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOffDate")));
                    invoice.setTotalFee(msg.getDouble("totalFee"));
                    System.out.println(invoice);
                    //文本框内容显示，总费用、开始/结束使用的时间
                    fee.setText(String.valueOf(invoice.getTotalFee()));
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    requestOnTextField.setText(df.format(invoice.getRequestOnDate()));
                    requestOffTextField.setText(df.format(invoice.getRequestOffDate()));
                    printButton.setEnabled(true);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    JOptionPane.showMessageDialog(null, "发送请求失败");
                }

            }
        });
        add(queryButton);

        //打印账单按钮的监听
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //打印账单
                PrintInvoice print=new PrintInvoice(invoice.getCustomId(),invoice);
                try {
                    result=print.printInvoice();
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
