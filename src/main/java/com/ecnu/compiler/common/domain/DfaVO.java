package com.ecnu.compiler.common.domain;

import com.ecnu.compiler.common.Utils;
import com.ecnu.compiler.component.lexer.domain.DFA;
import com.ecnu.compiler.component.lexer.domain.graph.Edge;
import com.ecnu.compiler.component.lexer.domain.graph.State;
import org.springframework.util.ObjectUtils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DfaVO {
    private NodeVO startState;
    private List<NodeVO> endState;
    private List<NodeVO> stateList;
    private List<EdgeVO> edgeList;

    public DfaVO(DFA dfa) {
        transform(dfa);
    }

    public DfaVO(NodeVO startState, List<NodeVO> endState, List<NodeVO> stateList, List<EdgeVO> edgeList) {
        this.startState = startState;
        this.endState = endState;
        this.stateList = stateList;
        this.edgeList = edgeList;
    }

    public NodeVO getStartState() {
        return startState;
    }

    public void setStartState(NodeVO startState) {
        this.startState = startState;
    }

    public List<NodeVO> getEndState() {
        return endState;
    }

    public void setEndState(List<NodeVO> endState) {
        this.endState = endState;
    }

    public List<NodeVO> getStateList() {
        return stateList;
    }

    public void setStateList(List<NodeVO> stateList) {
        this.stateList = stateList;
    }

    public List<EdgeVO> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<EdgeVO> edgeList) {
        this.edgeList = edgeList;
    }

    private void transform(DFA dfa){
        this.startState = new NodeVO(dfa.getStartState());
        this.endState = new ArrayList<>();
        for(State s : dfa.getEndStateList()){
            NodeVO node = new NodeVO(s);
            this.endState.add(node);
        }
        this.stateList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        Utils.travelGraph(stateList,edgeList,dfa.getStartState());
    }
}
