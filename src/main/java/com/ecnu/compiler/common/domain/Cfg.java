package com.ecnu.compiler.common.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 数据库映射实体 -- cfg
 * @author michaelchen
 */
@TableName("cfg")
public class Cfg extends Model<Cfg> {
    private Integer id;
    private String cfgName;
    private String cfgContent;
    private Integer compilerId;
    private Timestamp gmtCreated;
    @TableField(update="now()")
    private Timestamp gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCfgName() {
        return cfgName;
    }

    public void setCfgName(String cfgName) {
        this.cfgName = cfgName;
    }

    public String getCfgContent() {
        return cfgContent;
    }

    public void setCfgContent(String cfgContent) {
        this.cfgContent = cfgContent;
    }

    public Integer getCompilerId() {
        return compilerId;
    }

    public void setCompilerId(Integer compilerId) {
        this.compilerId = compilerId;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public Timestamp getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Timestamp gmtModified) {
        this.gmtModified = gmtModified;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
