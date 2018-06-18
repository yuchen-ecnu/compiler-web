package com.ecnu.compiler.parser.domain.vo;

import com.ecnu.compiler.component.parser.domain.ParsingTable.LRParsingTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LRParserTableVO extends ParserTableVO{

    private List<String> tableHeadList;
    private int divOfTowParts;
    private int tableHeadSize;
    private List<LRParsingTable.TableItem[]> table;

    public LRParserTableVO(LRParsingTable lrParsingTable){
        table = lrParsingTable.getTable();
        Map<String, Integer> mColMap = lrParsingTable.getColMap();
        divOfTowParts = lrParsingTable.getDivOfTowParts();

        int tableHeadSize = mColMap.size();
        tableHeadList = new ArrayList<>();
        for(int i = 0; i < tableHeadSize; i++){
            for(String key : mColMap.keySet()){
                if(mColMap.get(key).equals(i)) {
                    tableHeadList.add(i, key);
                    break;
                }
            }
        }
        this.tableHeadSize = tableHeadSize;
    }

    public int getTableHeadSize() {
        return tableHeadSize;
    }

    public void setTableHeadSize(int tableHeadSize) {
        this.tableHeadSize = tableHeadSize;
    }

    public List<String> getTableHeadList() {
        return tableHeadList;
    }

    public void setTableHeadList(List<String> tableHeadList) {
        this.tableHeadList = tableHeadList;
    }

    public int getDivOfTowParts() {
        return divOfTowParts;
    }

    public void setDivOfTowParts(int divOfTowParts) {
        this.divOfTowParts = divOfTowParts;
    }

    public List<LRParsingTable.TableItem[]> getTable() {
        return table;
    }

    public void setTable(List<LRParsingTable.TableItem[]> table) {
        this.table = table;
    }
}
