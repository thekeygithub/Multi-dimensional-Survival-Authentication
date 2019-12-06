package com.xcuni.guizhouyl.data.database.entity;

import com.xcuni.guizhouyl.data.database.common.annotation.WhereSQL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TBL_USER_STATUS")
public class TblUserStatusEntity implements Serializable {
    //private static final long serialVersionUID = 123456789L;

    private String userId;

    @Id
    @Column(name = "user_id")
    @WhereSQL(sql = "user_id=:userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private int pointS;

    @Column(name = "point_s")
    @WhereSQL(sql = "point_s=:pointS")
    public int getPointS() {
        return pointS;
    }

    public void setPointS(int pointS) {
        this.pointS = pointS;
    }

    private int pointT;

    @Column(name = "point_t")
    @WhereSQL(sql = "point_t=:pointT")
    public int getPointT() {
        return pointT;
    }

    public void setPointT(int pointT) {
        this.pointT = pointT;
    }

    private int userStatus;

    @Column(name = "user_status")
    @WhereSQL(sql = "user_status=:userStatus")
    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    private int statusDesc;

    @Column(name = "status_desc")
    @WhereSQL(sql = "status_desc=:statusDesc")
    public int getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(int statusDesc) {
        this.statusDesc = statusDesc;
    }

    private String aliveDataSrc;

    @Column(name = "alive_data_src")
    @WhereSQL(sql = "alive_data_src=:aliveDataSrc")
    public String getAliveDataSrc() {
        return aliveDataSrc;
    }

    public void setAliveDataSrc(String aliveDataSrc) {
        this.aliveDataSrc = aliveDataSrc;
    }

    private String note;

    @Column(name = "note")
    @WhereSQL(sql = "note=:note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    private String updateTime;

    @Column(name = "update_time")
    @WhereSQL(sql = "update_time=:updateTime")
    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
