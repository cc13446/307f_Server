package App;

import Domain.Invoice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class PrintInvoice {

    private int customId;
    private Invoice invoice;
    public PrintInvoice(int customId,Invoice invoice){
        this.customId=customId;
        this.invoice=invoice;
    }
    public boolean printInvoice() throws IOException {
        String filename="Invoice"+customId+".txt";
        File Invoice=new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(Invoice));
        writer.write("顾客"+customId+'\n');
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        writer.write("空调开始使用时间："+df.format(invoice.getRequestOnDate()) + "\n");
        writer.write("空调结束使用时间："+df.format(invoice.getRequestOffDate()) + "\n");
        writer.write("总费用:"+invoice.getTotalFee()+'\n');
        writer.close();
        return true;
    }
}
