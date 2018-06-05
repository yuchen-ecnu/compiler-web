package com.ecnu.compiler.lexical.domain;

import java.util.List;

public class SymbolTableVO {
    private List<SymbolVO> symbolList;
    private List<Regex> regexList;

    public SymbolTableVO(List<SymbolVO> symbolList, List<Regex> regexList) {
        this.symbolList = symbolList;
        this.regexList = regexList;
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
}
