package cn.edu.nbpt.facenet.singin.entity;

import java.io.Serializable;

public class Meeting implements Serializable {
    private Integer mId;
    private String title;
    private Long createTime;
    private String createUser;
    private Integer state;
    private Long startTime;
    private Long endTime;

    @Override
    public String toString() {
        return "Meeting{" +
                "mId=" + mId +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", state=" + state +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
