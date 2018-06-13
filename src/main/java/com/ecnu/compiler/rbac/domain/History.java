package com.ecnu.compiler.rbac.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 历史记录
 * @author Michael Chen
 */
@TableName("history")
public class History extends Model<History> {
    private Integer id;
    private Integer userId;
    private Integer compilerId;
    private String compileText;
    private String operator;
    @TableField(update="now()")
    private Timestamp gmtCreated;

    public History(Integer id) {
        this.id = id;
    }

    public History(Integer userId, Integer compilerId, String compileText, String operator) {
        this.userId = userId;
        this.compilerId = compilerId;
        this.compileText = compileText;
        this.operator = operator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCompilerId() {
        return compilerId;
    }

    public void setCompilerId(Integer compilerId) {
        this.compilerId = compilerId;
    }

    public String getCompileText() {
        return compileText;
    }

    public void setCompileText(String compileText) {
        this.compileText = compileText;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
