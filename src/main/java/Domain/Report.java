package Domain;

import Enum.*;

import java.util.ArrayList;
import java.util.Date;

/*
 *  报表类，存储各个房间的报表列表和其他报表信息
 */

public class Report {
    //报表类型
    private TypeReport typeReport;
    //报表日期
    private Date date = new Date();
    //报表属性列表
    private ArrayList<ReportForm> reportFormList = new ArrayList<>();


    //构造函数
    public Report() {
        this.typeReport = TypeReport.DAILY;
    }

    public Report(TypeReport typeReport) {
        this.typeReport = typeReport;
    }

    public Report(TypeReport typeReport, Date date, ArrayList<ReportForm> reportFormList) {
        this.typeReport = typeReport;
        this.date = date;
        this.reportFormList = reportFormList;
    }

    //get和set方法
    public TypeReport getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(TypeReport typeReport) {
        this.typeReport = typeReport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ArrayList<ReportForm> getReportFormList() {
        return reportFormList;
    }

    public void setReportFormList(ArrayList<ReportForm> reportFormList) {
        this.reportFormList = reportFormList;
    }

    //对列表的add方法
    public void addReportForm(ReportForm reportForm) {
        this.reportFormList.add(reportForm);
    }

    @Override
    public String toString() {
        return "Report{" +
                "typeReport=" + typeReport +
                ", date=" + date +
                ", reportFormList=" + reportFormList.toString() +
                '}';
    }
}
