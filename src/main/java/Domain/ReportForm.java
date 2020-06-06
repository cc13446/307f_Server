package Domain;

public class ReportForm {
    private int turnTimes;//空调开关次数
    private long useTime;//空调服务时长
    private double totalFee;//总费用
    private int schedulerTimes;//被调度的次数
    private int customerNumber;//旅客人数
    private int changeTempTimes;//调温次数
    private int changeFanSpeedTimes;//调风次数
    private int roomId;//房间号

    public ReportForm(int turnTimes, long useTime, double totalFee, int schedulerTimes, int customerNumber, int changeTempTimes, int changeFanSpeedTimes, int roomId) {
        this.turnTimes = turnTimes;
        this.useTime = useTime;
        this.totalFee = totalFee;
        this.schedulerTimes = schedulerTimes;
        this.customerNumber = customerNumber;
        this.changeTempTimes = changeTempTimes;
        this.changeFanSpeedTimes = changeFanSpeedTimes;
        this.roomId = roomId;
    }

    //构造方法
    public ReportForm() {
        turnTimes = 0;
        totalFee = 0;
        schedulerTimes = 0;
        customerNumber = 0;
        changeTempTimes = 0;
        changeFanSpeedTimes = 0;
        roomId = 0;
        useTime = 0;
    }

    //get和set方法
    public int getTurnTimes() {
        return turnTimes;
    }

    public void setTurnTimes(int turnTimes) {
        this.turnTimes = turnTimes;
    }

    public long getUseTime() {
        return useTime;
    }

    public String getStringUseTime() {
        return useTime / 1000 / 3600 + "时" + useTime / 1000 / 60 % 60 + "分" + useTime / 1000 % 60 + "秒";
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(double totalFee) {
        this.totalFee = totalFee;
    }

    public int getSchedulerTimes() {
        return schedulerTimes;
    }

    public void setSchedulerTimes(int schedulerTimes) {
        this.schedulerTimes = schedulerTimes;
    }

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public int getChangeTempTimes() {
        return changeTempTimes;
    }

    public void setChangeTempTimes(int changeTempTimes) {
        this.changeTempTimes = changeTempTimes;
    }

    public int getChangeFanSpeedTimes() {
        return changeFanSpeedTimes;
    }

    public void setChangeFanSpeedTimes(int changeFanSpeedTimes) {
        this.changeFanSpeedTimes = changeFanSpeedTimes;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public String toString() {
        return "roomId=" + roomId +
                ", turnTimes=" + turnTimes +
                ", useTime=" + useTime / 1000 / 3600 + "时" + useTime / 1000 / 60 % 60 + "分" + useTime / 1000 % 60 + "秒" +
                ", totalFee=" + totalFee +
                ", schedulerTimes=" + schedulerTimes +
                ", detailBillNumber=" + customerNumber +
                ", changeTempTimes=" + changeTempTimes +
                ", changeFanSpeedTimes=" + changeFanSpeedTimes +
                '.';
    }
}
