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

    public CreateInvoiceUI(){
        setTitle("查看账单");
        setBounds(610, 140, 370, 290);//设置窗口大小
        setResizable(false);//设置窗口不能改变大小
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
        setLayout(null);


        //房间号标签
        JLabel roomID = new JLabel("输入房间号:",JLabel.CENTER);
        roomID.setBounds(8,0,80,40);
        add(roomID);

        //房间号输入框
        JTextField roomTextField = new JTextField();
        roomTextField.setBounds(150,5,80,30);
        add(roomTextField);


        JLabel invoiceText = new JLabel("共需交费￥:",JLabel.CENTER);
        invoiceText.setBounds(8,60,80,40);
        add(invoiceText);
        //显示总费用
        JTextArea fee = new JTextArea();
        fee.setText("totalFee");
        fee.setEditable(false);
        fee.setBounds(150,60,200,40);
        add(fee);


        /***
         * ### to
         *
         * ```json
         * {
         *     "msgType":  2,
         *     "roomId": 1
         * }
         * ```
         *
         * ### from
         *
         * ```json
         * {
         *     "customId":10,
         *      "totalFee" : 20.0,
         *     "requestOnDate": 2020-10-20 00:00:00,
         *      "requestOffDate": 2020-10-20 00:00:00
         * }
         */



        //添加按钮
        JButton queryButton = new JButton();
        queryButton.setText("查看总费用");
        queryButton.setBounds(60,120,100,30);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查总费用
                invoice=new Invoice();
                roomId=Integer.parseInt(roomTextField.getText());

                if (isNumeric(roomTextField.getText())==false){

                    JOptionPane.showMessageDialog(null,"输入房间号不合法");

                }

                System.out.println("房间号"+roomId);
                HttpRequestModel httpRequestModel = new HttpRequestModel();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roomId",roomId);
                jsonObject.put("msgType",2);


                JSONObject msg=new JSONObject();
                invoice.setCustomId(msg.getInt("customId"));
                try {
                    msg=httpRequestModel.send(jsonObject);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    JOptionPane.showMessageDialog(null, "发送请求失败");
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }

                try {
                    invoice.setRequestOnDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOnDate")));
                    invoice.setRequestOffDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(msg.getString("requestOffDate")));
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
                invoice.setTotalFee(msg.getDouble("totalFee"));
                invoiceText.setText(String.valueOf(invoice.getTotalFee()));


            }
        });
        add(queryButton);



        JButton printButton = new JButton();
        printButton.setText("打印账单");
        printButton.setBounds(180,120,100,30);
        printButton.setEnabled(false);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //print
                PrintInvoice print=new PrintInvoice(customId,invoice);
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
