package com.ecnu.compiler.common.domain;

import com.ecnu.compiler.component.lexer.domain.graph.Edge;

public class EdgeVO {
    private int from;
    private int to;
    private String arrows = "to";
    private String label;

    public EdgeVO(Edge e) {
        this.from = e.getStartState().getId();
        this.to = e.getEndState().getId();
        this.label = e.getWeight()+"";
        if(this.label.equals("\u0000")){
            this.label = "ε";
        }
    }

    public EdgeVO(int from, int to){
        this.from = from;
        this.to = to;
    }

    public String getArrows() {
        return arrows;
    }

    public void setArrows(String arrows) {
        this.arrows = arrows;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
