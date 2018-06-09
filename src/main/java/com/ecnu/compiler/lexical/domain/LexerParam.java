package com.ecnu.compiler.lexical.domain;

import org.springframework.util.ObjectUtils;

public class LexerParam {
    Integer lan;
    String txt;

    public Integer getLan() {
        return lan;
    }

    public void setLan(Integer lan) {
        this.lan = lan;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public boolean isVaild() {
        return !(ObjectUtils.isEmpty(lan)||ObjectUtils.isEmpty(txt));
    }
}
