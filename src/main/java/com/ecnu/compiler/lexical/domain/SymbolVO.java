package com.ecnu.compiler.lexical.domain;

import com.ecnu.compiler.component.storage.domain.Token;

public class SymbolVO {
    private int id;
    private String attr;
    private String type;

    public SymbolVO(int id, String attr, String type) {
        this.id = id;
        this.attr = attr;
        this.type = type;
    }

    public SymbolVO(Token token, int id){
        this.id = id;
        this.attr = (String)token.getAttrs().get(0);
        this.type = token.getType();
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
}
