package com.ecnu.compiler.parser.domain.vo;

import com.ecnu.compiler.controller.Compiler;

public class TimeTableVO {
    private long preprocessorTime;
    private long lexerTime;
    private long parserTime;

    public TimeTableVO(long preprocessorTime, long lexerTime, long parserTime) {
        this.preprocessorTime = preprocessorTime;
        this.lexerTime = lexerTime;
        this.parserTime = parserTime;
    }

    public TimeTableVO(Compiler.TimeHolder timeHolder) {
        if(timeHolder==null){ return; }
        this.preprocessorTime = timeHolder.getPreprocessorTime();
        this.lexerTime = timeHolder.getLexerTime();
        this.parserTime = timeHolder.getParserTime();
    }

    public long getPreprocessorTime() {
        return preprocessorTime;
    }

    public void setPreprocessorTime(long preprocessorTime) {
        this.preprocessorTime = preprocessorTime;
    }

    public long getLexerTime() {
        return lexerTime;
    }

    public void setLexerTime(long lexerTime) {
        this.lexerTime = lexerTime;
    }

    public long getParserTime() {
        return parserTime;
    }

    public void setParserTime(long parserTime) {
        this.parserTime = parserTime;
    }
}
