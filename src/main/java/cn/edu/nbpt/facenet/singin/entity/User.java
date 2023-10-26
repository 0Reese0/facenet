package cn.edu.nbpt.facenet.singin.entity;

import java.io.Serializable;

public class User implements Serializable {
    private Integer uId;
    private String phone;
    private String password;
    private String salt;
    private String faceId;
    private String avatar;
    private String username;
    private String address;
    private Long createTime;
    private Long changeTime;
    private Integer age;
    private Integer sex;
    private Integer admin;
    private Integer audit;

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", faceId='" + faceId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", createTime=" + createTime +
                ", changeTime=" + changeTime +
                ", age=" + age +
                ", sex=" + sex +
                ", admin=" + admin +
                ", audit=" + audit +
                '}';
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFaceId() {
        return faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Long changeTime) {
        this.changeTime = changeTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAdmin() {
        return admin;
    }

    public void setAdmin(Integer admin) {
        this.admin = admin;
    }

    public Integer getAudit() {
        return audit;
    }

    public void setAudit(Integer audit) {
        this.audit = audit;
    }
}
