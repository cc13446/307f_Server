package App;

import Domain.Invoice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PrintInvoice {

    private int customId;
    private Invoice invoice;
    public PrintInvoice(int customId,Invoice invoice){
        this.customId=customId;
        this.invoice=invoice;
    }
    public boolean printInvoice() throws IOException {
        File Invoice=new File("InvoiceFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter("InvoiceFile.txt"));
        writer.write("顾客"+customId+'\n');
        writer.write(invoice.getRequestOnDate()+"-----"+invoice.getRequestOffDate()+'\n');
        writer.write("总费用:"+invoice.getTotalFee()+'\n');
//        writer.write("入住时间"+dateIn.toString()+'\n');
//        writer.write("退房时间"+dateOut.toString()+'\n');
        return true;
    }
}
