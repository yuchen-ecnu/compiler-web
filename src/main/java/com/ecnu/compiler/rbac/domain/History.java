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
    private User user;
    private Compiler compiler;
    private String compileText;
    private String operator;
    @TableField(update="now()")
    private Timestamp gmtCreated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Compiler getCompiler() {
        return compiler;
    }

    public void setCompiler(Compiler compiler) {
        this.compiler = compiler;
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
