package App;

import Domain.DetailBill;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrintDetailBill {

    private int customId;
    private DetailBill detailBill;

    public PrintDetailBill(int customId,DetailBill detailBill) throws IOException {
        this.customId=customId;
        this.detailBill=detailBill;
    }

    public boolean printDetailBill() throws IOException {
        File detailBillFile=new File("DetailBillFile.txt");
        //OutputStream outputStream=new FileOutputStream(detailBillFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter("DetailBillFile.txt"));
        writer.write("顾客"+customId+'\n');
        writer.write(detailBill.getRequestOnDate()+"-----"+detailBill.getRequestOffDate()+'\n');

        for (int i=0;i<detailBill.getDetailBillList().size();i++){
            //writer.write(detailBill.getDetailBillList().get(i).toString()+'\n');
            writer.write("服务开始时间："+detailBill.getDetailBillList().get(i).getStartTime().toString()+'\n');
            writer.write("服务结束："+detailBill.getDetailBillList().get(i).getEndTime()+'\n');
            writer.write("服务持续时间："+detailBill.getDetailBillList().get(i).getDuration()+'\n');
            writer.write("目标温度："+detailBill.getDetailBillList().get(i).getTargetTemp()+'\n');
            writer.write("风速："+detailBill.getDetailBillList().get(i).getFee()+'\n');
            writer.write("模式："+detailBill.getDetailBillList().get(i).getMode()+'\n');
            writer.write("费率"+detailBill.getDetailBillList().get(i).getFeeRate()+'\n');
            writer.write("服务的费用"+detailBill.getDetailBillList().get(i).getFee()+'\n');
            writer.write("---------------------------------------------" +'\n');


        }
        writer.close();

        return true;
    }

}
