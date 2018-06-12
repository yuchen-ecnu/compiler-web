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
package com.ecnu.compiler.rbac.controller;

import com.ecnu.compiler.common.domain.CompilerConfiguration;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.service.SessionService;
import com.ecnu.compiler.rbac.service.UserService;
import com.ecnu.compiler.rbac.utils.UserUtils;
import com.ecnu.compiler.utils.domain.Constants;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * 用户控制器，用于处理用户账户内信息处理
 *
 * @author Michael Chen
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {
    @Resource
    private UserService userServices;

    /**
     *  获取用户自定义的编译器
     */
    @RequestMapping(value = "/compiler/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getUserCompiler() {
        User user = UserUtils.getCurrentUser();
        if(ObjectUtils.isEmpty(user)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Resp());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Resp(HttpRespCode.SUCCESS,userServices.getUserCompilers(user.getId())));
    }

    /**
     *  修改用户自定义的编译器
     */
    @RequestMapping(value = "/compiler/modify/", method = RequestMethod.POST)
    public ResponseEntity<Resp> modifyUserCompiler(@RequestBody CompilerConfiguration compilerConfiguration) {
        //param check
        if(!compilerConfiguration.isModifyValid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        // 校验用户权限
        User user = UserUtils.getCurrentUser();
        if(ObjectUtils.isEmpty(user)|| !user.getId().equals(compilerConfiguration.getCompiler().getUserId())){
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.NEED_CREATE_ERROR));
        }
        boolean status = userServices.modifyUserCompiler(compilerConfiguration);
        if(!status){
            return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.COMPILER_MODIFIED_ERROR));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS));
    }

    /**
     * 新增用户自定义的编译器
     */
    @RequestMapping(value = "/compiler/new/", method = RequestMethod.POST)
    public ResponseEntity<Resp> addUserCompiler(@RequestBody CompilerConfiguration compilerConfiguration) {
        //param check
        if(!compilerConfiguration.isNewValid()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Resp());
        }
        User user = UserUtils.getCurrentUser();
        if(ObjectUtils.isEmpty(user)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Resp());
        }
        compilerConfiguration.getCompiler().setUserId(user.getId());
        boolean status = userServices.addUserCompiler(compilerConfiguration);
        if(!status){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new Resp(HttpRespCode.COMPILER_ADD_ERROR,new Resp())
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(new Resp(HttpRespCode.SUCCESS));
    }

}
