package com.ecnu.compiler.parser.domain;

import com.ecnu.compiler.component.parser.domain.ParsingTable.ParsingTable;
import com.ecnu.compiler.component.parser.domain.PredictTable.PredictTable;
import com.ecnu.compiler.component.parser.domain.TD;

public class ParserVO {
    private TimeTableVO timeTableVO;
    private TDVO td;
    private ParsingTable parsingTable;
    private String parsingTableName;
    private PredictTable predictTable;

    public ParserVO(TimeTableVO timeTableVO, TDVO td, ParsingTable parsingTable, String parsingTableName, PredictTable predictTable) {
        this.timeTableVO = timeTableVO;
        this.td = td;
        this.parsingTable = parsingTable;
        this.parsingTableName = parsingTableName;
        this.predictTable = predictTable;
    }

    public TimeTableVO getTimeTableVO() {
        return timeTableVO;
    }

    public void setTimeTableVO(TimeTableVO timeTableVO) {
        this.timeTableVO = timeTableVO;
    }

    public TDVO getTd() {
        return td;
    }

    public void setTd(TDVO td) {
        this.td = td;
    }

    public ParsingTable getParsingTable() {
        return parsingTable;
    }

    public void setParsingTable(ParsingTable parsingTable) {
        this.parsingTable = parsingTable;
    }

    public String getParsingTableName() {
        return parsingTableName;
    }

    public void setParsingTableName(String parsingTableName) {
        this.parsingTableName = parsingTableName;
    }

    public PredictTable getPredictTable() {
        return predictTable;
    }

    public void setPredictTable(PredictTable predictTable) {
        this.predictTable = predictTable;
    }
}
