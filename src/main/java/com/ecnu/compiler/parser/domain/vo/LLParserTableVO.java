package com.ecnu.compiler.parser.domain.vo;

import com.ecnu.compiler.component.parser.domain.ParsingTable.LLParsingTable;
import com.ecnu.compiler.component.parser.domain.ParsingTable.LLTableItem;

import java.util.*;

public class LLParserTableVO extends ParserTableVO{
    boolean isOK;
    List<String> termList;
    List<String> nontermList;
    List<String> tdList;
    Map<String, String> tntmap;
    int colNum;
    public LLParserTableVO (LLParsingTable llParsingTable){
        Set<LLTableItem> itemSet = llParsingTable.getItemSet();

        termList = new ArrayList<String>();
        nontermList = new ArrayList<String>();
        tdList = new ArrayList<String>();
        tntmap = new HashMap<String, String>();


        String term, nonterm;
        for(LLTableItem llTableItem : itemSet){
            term = llTableItem.getTerm().getName();
            nonterm = llTableItem.getNonTerm().getName();
            if(termList.indexOf(term) == -1) termList.add(term);
            if(nontermList.indexOf(nonterm) == -1) nontermList.add(nonterm);
        }
        if(termList.indexOf("$") == -1) termList.add("$");

        for(String nt : nontermList){
            
        }

        colNum = termList.size();


    }
}
