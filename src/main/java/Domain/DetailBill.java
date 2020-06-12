package Domain;

import java.util.ArrayList;
import java.util.Date;

public class DetailBill {
    //顾客Id
    private int customId;
    //空调开始使用时间
    private Date requestOnDate;
    //空调结束使用时间
    private Date requestOffDate;
    //详单列表
    private ArrayList<DetailBillItem> detailBillList;


    public int getCustomId() {
        return customId;
    }

    public void setCustomId(int customId) {
        this.customId = customId;
    }

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

    public ArrayList<DetailBillItem> getDetailBillList() {
        return detailBillList;
    }

    public void setDetailBillList(ArrayList<DetailBillItem> detailBillList) {
        this.detailBillList = detailBillList;
    }
}
