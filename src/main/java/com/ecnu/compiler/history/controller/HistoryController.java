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
package com.ecnu.compiler.history.controller;

import com.ecnu.compiler.history.service.HistoryService;
import com.ecnu.compiler.rbac.domain.User;
import com.ecnu.compiler.rbac.utils.UserUtils;
import com.ecnu.compiler.utils.domain.HttpRespCode;
import com.ecnu.compiler.utils.domain.Resp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用户控制器，用于处理用户账户内信息处理
 *
 * @author Michael Chen
 */
@RestController
@RequestMapping(value = "/history")
public class HistoryController {
    @Resource
    private HistoryService historyService;
    /**
     *  获取用户的操作历史记录
     */
    @RequestMapping(value = "/list/", method = RequestMethod.GET)
    public ResponseEntity<Resp> getUserHistory() {
        User user = UserUtils.getCurrentUser();
        if(ObjectUtils.isEmpty(user)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Resp());
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new Resp(HttpRespCode.SUCCESS,historyService.getUserHistory(user.getId())));
    }


}
