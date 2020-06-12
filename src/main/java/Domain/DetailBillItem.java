package Domain;

import java.util.Date;

import Enum.Mode;
import Enum.FanSpeed;
import Enum.ScheduleType;

public class DetailBillItem {
    //服务开始时间
    private Date startTime;
    //服务结束时间
    private Date endTime;
    //目标温度
    private double targetTemp;
    //费用
    private double fee;
    //费率
    private double feeRate;
    //服务持续时间
    private long duration;
    //风速
    private FanSpeed fanSpeed;
    //模式
    private Mode mode;
    //调度类型
    private ScheduleType scheduleType;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public void setTargetTemp(double targetTemp) {
        this.targetTemp = targetTemp;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public FanSpeed getFanSpeed() {
        return fanSpeed;
    }

    public void setFanSpeed(FanSpeed fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public ScheduleType getScheduleType() {
        return scheduleType;
    }

    public void setScheduleType(ScheduleType scheduleType) {
        this.scheduleType = scheduleType;
    }
}
