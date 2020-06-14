package Domain;

import java.util.Date;

public class Invoice {
    //空调开始使用时间
    private Date requestOnDate;
    //空调结束使用时间
    private Date requestOffDate;
    //总费用
    private double totalFee;
    //顾客Id
    private int customId;


    public Date getRequestOnDate() {
        return requestOnDate;
    }

    public void setRequestOnDate(Date requestOnDate) {
        this.requestOnDate = requestOnDate;
    }

    public Date getRequestOffDate() {
        return requestOffDate;
    }

    public void setRequestOffDate(Date requestOffDate) {
        this.requestOffDate = requestOffDate;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "requestOnDate=" + requestOnDate +
                ", requestOffDate=" + requestOffDate +
                ", totalFee=" + totalFee +
                ", customId=" + customId +
                '}';
    }
}
