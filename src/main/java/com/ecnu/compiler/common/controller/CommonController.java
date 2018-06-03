/**
 * Controller编写规则：
 *   1、所有路径均按节划分，每一节均为 单个 名词
 *   2、路径结尾以反斜杠结束
 *   3、符合Restful接口规范
 *        GET：请求资源，不修改服务器数据
 *        POST：向服务器新增资源或修改资源
 *        DELETE：删除服务器资源
 *   4、返回结果均以Resp对象返回，框架统一转换为json
 *   5、代码中不得出现汉字，返回信息统一规范到HttpRespCode中
 *      若Resp中没有所需要的文字，请在ConstantsMsg中添加文字
 *   6、所有函数请在头部标明作者，以便代码回溯
 *   7、使用 @Resource 注解自动装配 Service
 *
 *   @author Michael Chen
 */
package com.ecnu.compiler.common.controller;

import com.ecnu.compiler.common.domain.DfaVO;
import com.ecnu.compiler.common.domain.NfaVO;
import com.ecnu.compiler.common.service.CommonService;
import com.ecnu.compiler.component.storage.SymbolTable;
import com.ecnu.compiler.lexical.domain.LexerParam;
import com.ecnu.compiler.lexical.service.LexicalService;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.utils.UserUtils;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 *
 * @author Michael Chen
 */
@RestController
@RequestMapping(value = "/common")
public class CommonController {
    @Resource
    private CommonService commonService;
    @Resource
    private LexicalService lexicalService;

    /**
     *  登录
     */
    @RequestMapping(value = "/re2nfa/", method = RequestMethod.GET)
    public ResponseEntity<Resp> RE2NFA(@RequestParam("re") String re) {
        //params error
        if(ObjectUtils.isEmpty(re)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        NfaVO nfa = commonService.RE2NFA(re);
        if(ObjectUtils.isEmpty(nfa)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,nfa));
        }
    }
    @RequestMapping(value = "/re2dfa/", method = RequestMethod.GET)
    public ResponseEntity<Resp> RE2DFA(@RequestParam("re") String re) {
        //params error
        if(ObjectUtils.isEmpty(re)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        DfaVO dfa = commonService.RE2DFA(re);
        if(ObjectUtils.isEmpty(dfa)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,dfa));
        }
    }

    @RequestMapping(value = "/lexer/", method = RequestMethod.POST)
    public ResponseEntity<Resp> text2symboltable(@RequestBody LexerParam lexerParam) {
        //params error
        if(ObjectUtils.isEmpty(lexerParam.getTxt())||ObjectUtils.isEmpty(lexerParam.getLan())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        SymbolTable sb = lexicalService.generateSymbolTable(lexerParam.getTxt(),lexerParam.getLan());
        if(ObjectUtils.isEmpty(sb)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Resp());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS,sb));
        }
    }

    @RequestMapping(value = "/system/compiler/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getSystemCompilers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Resp(HttpRespCode.SUCCESS,commonService.getSystemCompilers()));
    }
}
