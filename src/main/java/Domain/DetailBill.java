package Domain;

import java.util.ArrayList;
import java.util.Date;

public class DetailBill {

    private int roomId;
    private Date dateIn;
    private Date dateOut;
    //private String information;
    private ArrayList<DetailBillItem> detailBillList;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getDateIn() {
        return dateIn;
    }

    public void setDateIn(Date dateIn) {
        this.dateIn = dateIn;
    }

    public Date getDateOut() {
        return dateOut;
    }

    public void setDateOut(Date dateOut) {
        this.dateOut = dateOut;
    }

    public ArrayList<DetailBillItem> getDetailBillList() {
        return detailBillList;
    }

    public void setDetailBillList(ArrayList<DetailBillItem> detailBillList) {
        this.detailBillList = detailBillList;
    }
}
