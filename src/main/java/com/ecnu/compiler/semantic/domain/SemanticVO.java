package com.ecnu.compiler.semantic.domain;

import com.ecnu.compiler.parser.domain.vo.TimeTableVO;

import java.util.List;

public class SemanticVO {
    private TimeTableVO timeTableVO;
    List<String> actionList;

    public SemanticVO(TimeTableVO timeTableVO, List<String> actionList) {
        this.timeTableVO = timeTableVO;
        this.actionList = actionList;
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
}
