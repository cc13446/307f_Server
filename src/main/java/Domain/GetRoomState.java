package Domain;

import Enum.*;

public class GetRoomState {
    private State state;
    private double currentTemp;
    private double targetTemp;
    private FanSpeed fanSpeed;
    private double feeRate;
    private double fee;
    private double duration;

    public Object[][] flushRoomState() {

        System.out.println("刷新数据");
        return null;
    }

    public State getState() {
        return state;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public double getTargetTemp() {
        return targetTemp;
    }

    public FanSpeed getFanSpeed() {
        return fanSpeed;
    }

    public double getFeeRate() {
        return feeRate;
    }

    public double getFee() {
        return fee;
    }

    public double getDuration() {
        return duration;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    public void setTargetTemp(double targetTemp) {
        this.targetTemp = targetTemp;
    }

    public void setFanSpeed(FanSpeed fanSpeed) {
        this.fanSpeed = fanSpeed;
    }

    public void setFeeRate(double feeRate) {
        this.feeRate = feeRate;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }
}
