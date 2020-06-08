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

    private int roomId;
    private Invoice invoice;
    private boolean result;
    private int customId;
    private JLabel roomID;
    private JTextField roomTextField;
    private JLabel requestOnLabel;
    private JTextArea requestOnTextField;
    private JLabel requestOffLabel;
    private JTextArea requestOffTextField;
    private JLabel invoiceText;
    private JTextArea fee;
    private JButton printButton;
    private JButton queryButton;

    public CreateInvoiceUI(){
        setTitle("查看账单");
        setBounds(610, 140, 370, 290);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);

        //房间号标签
        roomID = new JLabel("输入房间号:",JLabel.CENTER);
        roomID.setBounds(8,5,150,25);
        add(roomID);

        //房间号输入框
        roomTextField = new JTextField();
        roomTextField.setBounds(180,5,150,25);
        add(roomTextField);

        requestOnLabel = new JLabel("空调开始使用时间:",JLabel.CENTER);
        requestOnLabel.setBounds(8,40,150,25);
        add(requestOnLabel);

        requestOnTextField = new JTextArea();
        requestOnTextField.setBounds(180,40,150,25);
        add(requestOnTextField);

        requestOffLabel = new JLabel("空调结束使用时间:",JLabel.CENTER);
        requestOffLabel.setBounds(8,75,150,25);
        add(requestOffLabel);

        requestOffTextField = new JTextArea();
        requestOffTextField.setBounds(180,75,150,25);
        add(requestOffTextField);


        invoiceText = new JLabel("共需交费￥:",JLabel.CENTER);
        invoiceText.setBounds(8,110,150,25);
        add(invoiceText);
        //显示总费用
        fee = new JTextArea();
        fee.setText("totalFee");
        fee.setEditable(false);
        fee.setBounds(180,110,150,25);
        add(fee);

        printButton = new JButton();
        printButton.setText("打印账单");
        printButton.setBounds(180,145,100,30);
        printButton.setEnabled(false);

        //添加按钮
        queryButton = new JButton();
        queryButton.setText("查看总费用");
        queryButton.setBounds(60,145,100,30);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查总费用
                invoice=new Invoice();
                roomId=Integer.parseInt(roomTextField.getText());

                if (isNumeric(roomTextField.getText())==false){
                    JOptionPane.showMessageDialog(null,"输入房间号不合法");
                }
                if (roomTextField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"输入房间号不能为空！");
                }

                System.out.println("房间号"+roomId);
                HttpRequestModel httpRequestModel = new HttpRequestModel();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roomId",roomId);
                jsonObject.put("msgType",2);


                try {
                    JSONObject msg=httpRequestModel.send(jsonObject);
                    System.out.println(msg);
                    invoice.setCustomId(msg.getInt("customId"));
                    invoice.setRequestOnDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOnDate")));
                    invoice.setRequestOffDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOffDate")));
                    invoice.setTotalFee(msg.getDouble("totalFee"));
                    System.out.println(invoice);
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

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //print
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
