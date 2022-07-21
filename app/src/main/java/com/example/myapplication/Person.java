package com.example.myapplication;

/**
 * Created by Administrator on 2016-06-07.
 */
public class Person {
    private String name;
    private String SleepTime;
    private String WakeUpTime;
    private String UsrCode;
    private String Alarm_Q;
    private String Alarm_A;

    public String getWakeUpTime() {
        return WakeUpTime;
    }

    public void setWakeUpTime(String WakeUpTime) {
        this.WakeUpTime = WakeUpTime;
    }

   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   public String getSleepTime() {
        return SleepTime;
    }

    public void setSleepTime(String SleepTime) {
        this.SleepTime = SleepTime;
    }

    public String getUsrCode() {
        return UsrCode;
    }

    public void setUsrCode(String UsrCode) {
        this.UsrCode = UsrCode;
    }
    public String getAlarm_Q() {
        return Alarm_Q;
    }

    public void setAlarm_Q(String Alarm_Q) {
        this.Alarm_Q = Alarm_Q;
    }
    public String getAlarm_A() {
        return Alarm_A;
    }

    public void setAlarm_A(String Alarm_A) {
        this.Alarm_A = Alarm_A;
    }
    @Override
    public String toString() {
        /*return "Person [name=" + name + ", country=" + country + ", twitter="
                + twitter + "]";*/
        return "Person [name=" + WakeUpTime +"]";
    }
}
