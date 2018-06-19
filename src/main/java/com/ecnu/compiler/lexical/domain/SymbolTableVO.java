package com.ecnu.compiler.lexical.domain;

import com.ecnu.compiler.component.storage.ErrorList;
import com.ecnu.compiler.parser.domain.vo.TimeTableVO;

import java.util.List;

public class SymbolTableVO {
    private TimeTableVO timeTableVO;
    private List<SymbolVO> symbolList;
    private List<Regex> regexList;
//    private ErrorList errorList;

    public SymbolTableVO(TimeTableVO timeTableVO,List<SymbolVO> symbolList, List<Regex> regexList, ErrorList errorList) {
        this.timeTableVO = timeTableVO;
        this.symbolList = symbolList;
        this.regexList = regexList;
//        this.errorList = errorList;
    }

    public TimeTableVO getTimeTableVO() {
        return timeTableVO;
    }

    public void setTimeTableVO(TimeTableVO timeTableVO) {
        this.timeTableVO = timeTableVO;
    }

    public List<SymbolVO> getSymbolList() {
        return symbolList;
    }

    public void setSymbolList(List<SymbolVO> symbolList) {
        this.symbolList = symbolList;
    }

    public List<Regex> getRegexList() {
        return regexList;
    }

    public void setRegexList(List<Regex> regexList) {
        this.regexList = regexList;
    }

//    public ErrorList getErrorList() {
//        return errorList;
//    }
//
//    public void setErrorList(ErrorList errorList) {
//        this.errorList = errorList;
//    }
}
