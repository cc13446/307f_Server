package Domain;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import jxl.*;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import Enum.*;

public class PrintReport {
    private Report report = new Report();
    //构造方法
    public PrintReport(Report report) {
        this.report = report;
    }

    //打印报表，存储到文件，文件名为ReportForm加上当前时间
    public boolean printReport() {
        try {
            //打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File("报表" + new SimpleDateFormat(" yyyy年MM月dd日 HH时mm分ss秒").format(new Date()) + ".xls"));
            //生成名为“第一页”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet("报表", 0);

            //（0,0）处写入报表类型
            Label label = new Label(0, 0, "日报");
            if (report.getTypeReport().equals(TypeReport.DAILY)) {
                label = new Label(0, 0, "日报");
            } else if (report.getTypeReport().equals(TypeReport.WEEKLY)) {
                label = new Label(0, 0, "周报");
            } else if (report.getTypeReport().equals(TypeReport.MONTHLY)) {
                label = new Label(0, 0, "月报");
            } else if (report.getTypeReport().equals(TypeReport.ANNUAL)) {
                label = new Label(0, 0, "年报");
            }
            sheet.addCell(label);//将定义好的单元格添加到工作表中

            //写入报表日期
            sheet.addCell(new Label(0, 1, new SimpleDateFormat(" yyyy-MM-dd").format(report.getDate())));

            sheet.addCell(new Label(0, 2, "房间号"));
            sheet.addCell(new Label(1, 2, "空调开关次数"));
            sheet.addCell(new Label(2, 2, "空调服务时长"));
            sheet.addCell(new Label(3, 2, "总费用/元"));
            sheet.addCell(new Label(4, 2, "被调度的次数"));
            sheet.addCell(new Label(5, 2, "旅客人数"));
            sheet.addCell(new Label(6, 2, "调温次数"));
            sheet.addCell(new Label(7, 2, "调风次数"));

            //写入各个房间的报表信息
            ReportForm reportForm;
            for (int i = 0; i < report.getReportFormList().size(); i++) {
                reportForm = report.getReportFormList().get(i);
                sheet.addCell(new jxl.write.Number(0, 3 + i, reportForm.getRoomId()));
                sheet.addCell(new jxl.write.Number(1, 3 + i, reportForm.getTurnTimes()));
                sheet.addCell(new Label(2, 3 + i, reportForm.getStringUseTime()));
                sheet.addCell(new jxl.write.Number(3, 3 + i, reportForm.getTotalFee()));
                sheet.addCell(new jxl.write.Number(4, 3 + i, reportForm.getSchedulerTimes()));
                sheet.addCell(new jxl.write.Number(5, 3 + i, reportForm.getCustomerNumber()));
                sheet.addCell(new jxl.write.Number(6, 3 + i, reportForm.getChangeTempTimes()));
                sheet.addCell(new jxl.write.Number(7, 3 + i, reportForm.getChangeFanSpeedTimes()));
            }

            //写入数据并关闭文件
            book.write();
            book.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
