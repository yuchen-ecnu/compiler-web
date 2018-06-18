package com.ecnu.compiler.parser.domain.vo;

import com.ecnu.compiler.component.storage.domain.ErrorMsg;

import java.util.List;

public class ErrorMsgVO {
    List<ErrorMsg> errorMsgList;

    public ErrorMsgVO() {
    }

    public ErrorMsgVO(List<ErrorMsg> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }

    public List<ErrorMsg> getErrorMsgList() {
        return errorMsgList;
    }

    public void setErrorMsgList(List<ErrorMsg> errorMsgList) {
        this.errorMsgList = errorMsgList;
    }
}
