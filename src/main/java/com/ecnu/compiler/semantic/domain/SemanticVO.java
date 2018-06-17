package com.ecnu.compiler.semantic.domain;

import com.ecnu.compiler.parser.domain.vo.TDVO;
import com.ecnu.compiler.parser.domain.vo.TimeTableVO;

import java.util.List;

public class SemanticVO {
    private TimeTableVO timeTableVO;
    List<String> actionList;
    TDVO td;

    public SemanticVO(TimeTableVO timeTableVO, List<String> actionList,TDVO td) {
        this.timeTableVO = timeTableVO;
        this.actionList = actionList;
        this.td = td;
    }

    public TimeTableVO getTimeTableVO() {
        return timeTableVO;
    }

    public void setTimeTableVO(TimeTableVO timeTableVO) {
        this.timeTableVO = timeTableVO;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    public TDVO getTd() {
        return td;
    }

    public void setTd(TDVO td) {
        this.td = td;
    }
}
