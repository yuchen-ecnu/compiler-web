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
    private String compilerName;
    private String compileText;
    private Integer operator;
    @TableField(update="now()")
    private Timestamp gmtCreated;

    public History() {
    }

    public History(Integer id, Integer userId, Integer compilerId, String compilerName, String compileText, Integer operator, Timestamp gmtCreated) {
        this.id = id;
        this.userId = userId;
        this.compilerId = compilerId;
        this.compilerName = compilerName;
        this.compileText = compileText;
        this.operator = operator;
        this.gmtCreated = gmtCreated;
    }

    public History(Integer id) {
        this.id = id;
    }

    public History(Integer userId, Integer compilerId, String compileText, Integer operator) {
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

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Timestamp getGmtCreated() {
        return gmtCreated;
    }

    public void setGmtCreated(Timestamp gmtCreated) {
        this.gmtCreated = gmtCreated;
    }

    public String getCompilerName() {
        return compilerName;
    }

    public void setCompilerName(String compilerName) {
        this.compilerName = compilerName;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
