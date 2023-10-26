package cn.edu.nbpt.facenet.singin.entity;

import java.io.Serializable;

public class MeetingHistory implements Serializable {
    private Integer mhId;
    private Integer mId;
    private Integer uId;
    private Long regTime;
    private Integer mod;

    private Meeting meeting;
    private User user;

    @Override
    public String toString() {
        return "MeetingHistory{" +
                "mhId=" + mhId +
                ", mId=" + mId +
                ", uId=" + uId +
                ", regTime=" + regTime +
                ", mod=" + mod +
                ", meeting=" + meeting +
                ", user=" + user +
                '}';
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getMhId() {
        return mhId;
    }

    public void setMhId(Integer mhId) {
        this.mhId = mhId;
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Long getRegTime() {
        return regTime;
    }

    public void setRegTime(Long regTime) {
        this.regTime = regTime;
    }

    public Integer getMod() {
        return mod;
    }

    public void setMod(Integer mod) {
        this.mod = mod;
    }
}
