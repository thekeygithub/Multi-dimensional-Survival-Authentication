package com.xcuni.guizhouyl.data.database.entity;

import com.xcuni.guizhouyl.data.database.common.annotation.WhereSQL;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "TBL_DEATH_DATA_SRC")
public class TblDeathDataSrcEntity {
    //private static final long serialVersionUID = 1234567L;

//    private long id;
//    @Id
//    @Column(name = "id")
//    @WhereSQL(sql = "id=:id")
//    public long getId(){return  id;}

//    public void setId(long id){this.id = id;}

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

    private String deathTime;

    @Column(name = "death_time")
    @WhereSQL(sql = "death_time=:deathTime")
    public String getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(String deathTime) {
        this.deathTime = deathTime;
    }

    private String dataSrc;

    @Column(name = "data_src")
    @WhereSQL(sql = "data_src=:dataSrc")
    public String getDataSrc() {
        return dataSrc;
    }

    public void setDataSrc(String dataSrc) {
        this.dataSrc = dataSrc;
    }

    private String dataId;

    @Column(name = "data_id")
    @WhereSQL(sql = "data_id=:dataId")
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
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
