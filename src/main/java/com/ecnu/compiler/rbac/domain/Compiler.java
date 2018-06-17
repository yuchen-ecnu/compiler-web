package com.ecnu.compiler.rbac.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author michaelchen
 */
@TableName("compiler")
public class Compiler extends Model<Compiler> {
    @TableId
    private Integer id;
    private Integer userId;
    private String compilerName;
    private String languageName;
    private String introduce;
    private Integer usedTime;
    private Integer lexerModel;
    private Integer parserModel;
    private Timestamp gmtCreated;
    @TableField(update="now()")
    private Timestamp gmtModified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompilerName() {
        return compilerName;
    }

    public void setCompilerName(String compilerName) {
        this.compilerName = compilerName;
    }

    public Integer getLexerModel() {
        return lexerModel;
    }

    public void setLexerModel(Integer lexerModel) {
        this.lexerModel = lexerModel;
    }

    public Integer getParserModel() {
        return parserModel;
    }

    public void setParserModel(Integer parserModel) {
        this.parserModel = parserModel;
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

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public Integer getUsedTime() {
        return usedTime;
    }

    public void setUsedTime(Integer usedTime) {
        this.usedTime = usedTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
