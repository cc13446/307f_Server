package Domain;

import java.util.Date;

public class Invoice {

    private Date requestOnDate;
    private Date requestOffDate;
    private double totalFee;
    private int customId;

//    public Date getDateIn() {
//        return dateIn;
//    }
//
//    public void setDateIn(Date dateIn) {
//        this.dateIn = dateIn;
//    }
//
//    public Date getDateOut() {
//        return dateOut;
//    }
//
//    public void setDateOut(Date dateOut) {
//        this.dateOut = dateOut;
//    }
//
//    public double getTotalFee() {
//        return totalFee;
//    }
//
//    public void setTotalFee(double totalFee) {
//        this.totalFee = totalFee;
//    }
//
//    public int getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(int roomId) {
//        this.roomId = roomId;
//    }


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
}
