package com.ecnu.compiler.common.domain;

import com.ecnu.compiler.component.lexer.domain.RE;
import com.ecnu.compiler.component.parser.domain.CFG;
import com.ecnu.compiler.lexical.domain.Regex;
import com.ecnu.compiler.rbac.domain.Compiler;

import java.util.List;

/**
 * 编译器配置详情
 * @author michaelchen
 */
public class CompilerConfiguration {
    private Compiler compiler;
    private List<Regex> reList;
    private List<Cfg> cfgList;
    private List<Ag> agList;

    public CompilerConfiguration(Compiler compiler, List<Regex> reList, List<Cfg> cfgList, List<Ag> agList) {
        this.compiler = compiler;
        this.reList = reList;
        this.cfgList = cfgList;
        this.agList = agList;
    }

    public Compiler getCompiler() {
        return compiler;
    }

    public void setCompiler(Compiler compiler) {
        this.compiler = compiler;
    }

    public List<Regex> getReList() {
        return reList;
    }

    public void setReList(List<Regex> reList) {
        this.reList = reList;
    }

    public List<Cfg> getCfgList() {
        return cfgList;
    }

    public void setCfgList(List<Cfg> cfgList) {
        this.cfgList = cfgList;
    }

    public List<Ag> getAgList() {
        return agList;
    }

    public void setAgList(List<Ag> agList) {
        this.agList = agList;
    }
}
