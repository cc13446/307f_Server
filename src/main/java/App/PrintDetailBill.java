package App;

import Domain.DetailBill;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PrintDetailBill {

    private int customId;
    private DetailBill detailBill;
    private double totalFee;

    public PrintDetailBill(int customId,DetailBill detailBill) throws IOException {
        this.customId=customId;
        this.detailBill=detailBill;
    }

    public boolean printDetailBill() throws IOException {
        String filename="DetailBillFile"+customId+".txt";
        File detailBillFile=new File(filename);
        //OutputStream outputStream=new FileOutputStream(detailBillFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write("顾客ID为 "+customId+'\n');
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        writer.write("空调开始使用时间：" + df.format(detailBill.getRequestOnDate()) + "\n");
        writer.write("空调结束使用时间：" + df.format(detailBill.getRequestOffDate())+ "\n");

        totalFee=0;
        for (int i=0;i<detailBill.getDetailBillList().size();i++){

            writer.write("服务开始时间："+detailBill.getDetailBillList().get(i).getStartTime().toString()+'\n');
            writer.write("服务结束："+detailBill.getDetailBillList().get(i).getEndTime()+'\n');
            long ms=detailBill.getDetailBillList().get(i).getDuration();
            writer.write("服务持续时间："+ms/1000/60/60+"小时"+ms/1000/60%60+"分"+ms/1000%60+"秒"+'\n');
            writer.write("目标温度："+detailBill.getDetailBillList().get(i).getTargetTemp()+'\n');
            writer.write("风速："+detailBill.getDetailBillList().get(i).getFee()+'\n');
            writer.write("模式："+detailBill.getDetailBillList().get(i).getMode()+'\n');
            writer.write("费率"+detailBill.getDetailBillList().get(i).getFeeRate()+'\n');
            writer.write("服务的费用"+detailBill.getDetailBillList().get(i).getFee()+'\n');
            writer.write("---------------------------------------------" +'\n');

            totalFee=totalFee+detailBill.getDetailBillList().get(i).getFee();

        }
        writer.write("费用总计："+totalFee);
        writer.close();

        return true;
    }

}
