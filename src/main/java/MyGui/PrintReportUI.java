//package MyGui;
//
//import org.jdesktop.swingx.JXDatePicker;
//
//import javax.swing.*;
//import java.util.Date;
//
//public class PrintReportUI extends JFrame {
//    //构造方法
//    public PrintReportUI() {
//        setTitle("打印报表");
//        setBounds(610, 140, 420, 500);//设置窗口大小
//        setResizable(false);//设置窗口不能改变大小
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);//关闭窗口 dispose这个窗口
//        setLayout(null);
//
//        //输出框
//        JTextArea result = new JTextArea();
//        result.setEditable(false);
//
//        //设置滚动条
//        JScrollPane scroll=new JScrollPane(result);
//        //分别设置水平和垂直滚动条自动出现
//        scroll.setHorizontalScrollBarPolicy(
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scroll.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        scroll.setBounds(5,5,400,455);
//        add(scroll);
//
//    }
//}
