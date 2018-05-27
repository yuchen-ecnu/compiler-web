package com.ecnu.compiler.common.domain;

import com.ecnu.compiler.component.lexer.domain.graph.Edge;
import com.ecnu.compiler.component.lexer.domain.graph.State;

import java.util.List;

public class NodeVO {
    private int id;
    private String label;

    public NodeVO() {
    }

    public NodeVO(State state) {
        this.id = state.getId();
        this.label = state.getId()+"";
    }

    public NodeVO(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
