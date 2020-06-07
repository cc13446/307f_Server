package MyGui;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CreateInvoiceUI extends JFrame{
    public CreateInvoiceUI(){
        setTitle("查看账单");
        setBounds(610, 140, 370, 320);//设置窗口大小
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
//          //入住日期
//        JLabel reportDateIn = new JLabel("入住日期:",JLabel.CENTER);
//        reportDateIn.setBounds(0,60,80,40);
//        add(reportDateIn);
//
//        //date选择器
//        final JXDatePicker dateInPick = new JXDatePicker();
//        dateInPick.setDate(new Date());// 设置 date日期
//        dateInPick.setBounds(150,60,200,40);
//        add(dateInPick);
//
//        //退房日期
//        JLabel reportDateOut = new JLabel("退房日期:",JLabel.CENTER);
//        reportDateOut.setBounds(0,120,80,40);
//        add(reportDateOut);
//
//        //date选择器
//        final JXDatePicker dateOutPick = new JXDatePicker();
//        dateOutPick.setDate(new Date());// 设置 date日期
//        dateOutPick.setBounds(150,120,200,40);
//        add(dateOutPick);

        JLabel invoiceText = new JLabel("共需交费￥:",JLabel.CENTER);
        invoiceText.setBounds(8,60,80,40);
        add(invoiceText);
        //显示总费用
        JTextArea fee = new JTextArea();
        fee.setText("totalFee");
        fee.setEditable(false);
        fee.setBounds(150,60,200,40);
        add(fee);

        //添加按钮
        JButton queryButton = new JButton();
        queryButton.setText("查看总费用");
        queryButton.setBounds(60,120,100,30);
        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查总费用
            }
        });
        add(queryButton);



        JButton printButton = new JButton();
        printButton.setText("打印账单");
        printButton.setBounds(180,120,100,30);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //查总费用
            }
        });
        add(printButton);




    }
}
