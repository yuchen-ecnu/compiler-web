package com.ecnu.compiler.utils.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Resp
 */
public class Resp implements Serializable {
    private static final long serialVersionUID = -8660197629749596025L;

    protected String resCode;
    protected String resMsg;

    /**
     * @return the resTime
     */
    public Long getResTime() {
        if (resTime == null) {
            return 0L;
        }
        return resTime.getTime();
    }

    /**
     * @param resTime the resTime to set
     */
    public void setResTime(Date resTime) {
        this.resTime = resTime;
    }

    protected Date resTime;
    protected Object data;

    public Resp() {

    }


    public Resp(HttpRespCode resCode) {
        this.resCode = resCode.getCode();
        this.resMsg = resCode.getText();
        this.resTime = new Date();
    }

    public Resp(HttpRespCode resCode, Object data) {
        this.resCode = resCode.getCode();
        this.resMsg = resCode.getText();
        this.resTime = new Date();
        this.data = data;
    }


    public Resp(String resCode, String resMsg) {
        this.resCode = resCode;
        this.resMsg = resMsg;

    }

    public Resp(String resCode, String resMsg, Date resTime) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resTime = resTime;

    }

    public Resp(String resCode, String resMsg, Date resTime, Object data) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resTime = resTime;
        this.data = data;
    }

    public Resp(String resCode, String resMsg, Object data) {
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.data = data;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
