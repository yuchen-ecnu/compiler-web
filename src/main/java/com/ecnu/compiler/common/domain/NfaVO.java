package com.ecnu.compiler.common.domain;

import com.ecnu.compiler.common.Utils;
import com.ecnu.compiler.component.lexer.domain.NFA;

import java.util.ArrayList;
import java.util.List;

/**
 * NfaVO for nfa
 */
public class NfaVO {
    private NodeVO startState;
    private NodeVO endState;
    private List<NodeVO> stateList;
    private List<EdgeVO> edgeList;

    public NfaVO() {
    }

    public NfaVO(NFA nfa) {
        transform(nfa);
    }

    public NodeVO getStartState() {
        return startState;
    }

    public void setStartState(NodeVO startState) {
        this.startState = startState;
    }

    public NodeVO getEndState() {
        return endState;
    }

    public void setEndState(NodeVO endState) {
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

    private void transform(NFA nfa){
        this.startState = new NodeVO(nfa.getStartState());
        this.endState = new NodeVO(nfa.getEndState());
        this.stateList = new ArrayList<>();
        this.edgeList = new ArrayList<>();
        Utils.travelGraph(stateList,edgeList,nfa.getStartState());
    }

}
