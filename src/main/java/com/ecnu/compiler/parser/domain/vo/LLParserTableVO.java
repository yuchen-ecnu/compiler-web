package com.ecnu.compiler.parser.domain.vo;

import com.ecnu.compiler.component.parser.domain.ParsingTable.LLParsingTable;
import com.ecnu.compiler.component.parser.domain.ParsingTable.LLTableItem;
import com.ecnu.compiler.component.parser.domain.Production;
import com.ecnu.compiler.component.parser.domain.Symbol;

import java.util.*;

public class LLParserTableVO extends ParserTableVO{

    private Map<String, Integer> nonTermRow;
    private Map<String, Integer> termCol;
    private int nonTermRowNum;
    private int termColNum;
    private String[][] tableArr;
    private String[] rowArr;
    private String[] colArr;

    public LLParserTableVO (LLParsingTable llParsingTable){
        Map<Symbol, List<Integer>> nonTerminalMap = llParsingTable.getNonTerminalMap();
        Set<Symbol> terminalSet = llParsingTable.getTerminalSet();
        rowArr = new String[nonTerminalMap.size()];
        colArr = new String[terminalSet.size()];

        nonTermRow = new HashMap<String, Integer>();
        termCol = new HashMap<String, Integer>();

        nonTermRowNum = 0;
        termColNum = 0;
        for(Symbol symbol : nonTerminalMap.keySet()){
            nonTermRow.put(symbol.getName(), new Integer(nonTermRowNum));
            rowArr[nonTermRowNum] = symbol.getName();
            nonTermRowNum++;
        }
        for(Symbol symbol : terminalSet){
            termCol.put(symbol.getName(), new Integer(termColNum));
            colArr[termColNum] = symbol.getName();
            termColNum++;
        }

        tableArr = new String[nonTermRowNum][termColNum];

        Set<LLTableItem> itemSet = llParsingTable.getItemSet();
        for(LLTableItem llTableItem : itemSet){
            String productString = llTableItem.getValue().toString();
            Integer rowSym = nonTermRow.get(llTableItem.getNonTerm().getName()),
                    colSym = termCol.get(llTableItem.getTerm().getName());
            if(tableArr[ rowSym ][ colSym ] == null){
                tableArr[ rowSym ][ colSym ] = productString;
            }else{
                tableArr[ rowSym ][ colSym ] += (" <br> " + productString);
            }
        }
    }

    public Map<String, Integer> getNonTermRow() {
        return nonTermRow;
    }

    public void setNonTermRow(Map<String, Integer> nonTermRow) {
        this.nonTermRow = nonTermRow;
    }

    public Map<String, Integer> getTermCol() {
        return termCol;
    }

    public void setTermCol(Map<String, Integer> termCol) {
        this.termCol = termCol;
    }

    public int getNonTermRowNum() {
        return nonTermRowNum;
    }

    public void setNonTermRowNum(int nonTermRowNum) {
        this.nonTermRowNum = nonTermRowNum;
    }

    public int getTermColNum() {
        return termColNum;
    }

    public void setTermColNum(int termColNum) {
        this.termColNum = termColNum;
    }

    public String[][] getTableArr() {
        return tableArr;
    }

    public void setTableArr(String[][] tableArr) {
        this.tableArr = tableArr;
    }

    public String[] getRowArr() {
        return rowArr;
    }

    public void setRowArr(String[] rowArr) {
        this.rowArr = rowArr;
    }

    public String[] getColArr() {
        return colArr;
    }

    public void setColArr(String[] colArr) {
        this.colArr = colArr;
    }
}
