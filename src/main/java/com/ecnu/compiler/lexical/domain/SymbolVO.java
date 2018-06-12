package com.ecnu.compiler.lexical.domain;

import com.ecnu.compiler.component.storage.domain.Token;

public class SymbolVO {
    private int id;
    private String attr;
    private String type;
    private Integer rowNumber;
    private Integer colNumber;

    public SymbolVO(int id, String attr, String type) {
        this.id = id;
        this.attr = attr;
        this.type = type;
    }

    public SymbolVO(Token token, int id){
        this.id = id;
        this.attr = (String)token.getStr();
        this.type = token.getType();
        this.colNumber = token.getColPosition();
        this.rowNumber = token.getRowNumber();
    }

    public SymbolVO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    public Integer getColNumber() {
        return colNumber;
    }

    public void setColNumber(Integer colNumber) {
        this.colNumber = colNumber;
    }
}
