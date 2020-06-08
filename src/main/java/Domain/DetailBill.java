package Domain;

import java.util.ArrayList;
import java.util.Date;

public class DetailBill {

    private int customId;
    private Date requestOnDate;
    private Date requestOffDate;
    //private String information;
    private ArrayList<DetailBillItem> detailBillList;

//    public int getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(int roomId) {
//        this.roomId = roomId;
//    }


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
