package com.ecnu.compiler.utils.domain;
import java.io.Serializable;
import java.util.Date;

/**
 * Http请求返回状态码:
 * 1xx - 信息提示
 * 2xx - 成功
 * 3xx - 重定向
 * 4xx - 客户端错误
 * 5xx|6xx - 服务器错误
 */
public enum HttpRespCode implements Serializable {
    //标准HTTP协议
    CONTINUE("100", "等待后续请求"),

    SUCCESS("200", "请求成功"),
    CREATED("201", "创建成功"),
    ACCEPTED("202", "请求已接收，等待处理"),
    NO_CONTENT("204", "请求成功"),

    BAD_REQUEST("400", "语义或请求参数有误"),
    UNAUTHORIZED("401", "需要用户身份验证"),
    PAYMENT_REQUIRED("402", "预留状态码"),
    FORBIDDEN("403", "服务器拒绝执行"),
    NOT_FOUND("404", "请求资源不存在"),
    METHOD_NOT_ALLOWED("405", "请求方法无法响应"),
    REQUEST_TIMEOUT("408", "请求超时"),
    LENGTH_REQUIRED("411", "未找到请求Content-Length头"),
    PRECONDITION_FAILED("412", "先决条件不满足"),
    REQUEST_ENTITY_TOO_LARGE("413", "请求资源过大"),
    UNSUPPORTED_MEDIA_TYPE("415", "提交或请求数据格式不支持"),
    EXPECTATION_FAILED("417", "服务器无法满足预期内容"),
    TOO_MANY_CONNECTIONS("421", "连接用户数过多"),
    UNPROCESSABLE_ENTITY("422", "语义错误"),
    LOCKED("423", "资源被锁定"),
    FAILED_DEPENDENCY("424", "前序请求出错"),
    UNAVAILABLE_FOR_LEGAL_REASONS("451", "该请求因法律原因不可用"),

    INTERNAL_SERVER_ERROR("500", "服务器内部出错"),
    NOT_IMPLEMENT("501", "服务器不支持当前请求"),
    BAD_GATEWAY("502", "网关出错"),
    SERVICE_UNAVAILABLE("503", "临时服务器维护"),
    GATEWAY_TIMEOUT("504", "网关超时"),
    HTTP_VERSION_NOT_SUPPORTED("505", "服务器不支持该http版本"),
    BANDWIDTH_LIMIT_EXCEEDED("509", "服务器达到带宽限制"),
    NOT_EXTENDED("510", "获取资源所需要的策略未满足"),

    UNPARSEABLE_RESPONSE_HEADERS("600", "只返回实体内容"),


    //系统自定义协议
    SAVE_SUCCESS("200", "保存成功"),
    UPDATE_SUCCESS("200", "更新成功"),
    ADD_SUCCESS("200", "添加成功"),
    SEND_EMAIL_SUCCESS("200", "邮件已发送到您邮箱"),
    IMAGE_UPLOAD_SUCCESS("200", "图片上传成功"),
    DELETE_SUCCESS("200", "删除成功"),
    OPERATE_SUCCESS("200", "操作成功"),
    COLLECTION_SUCCESS("200", "收藏成功"),
    CANCEL_COLLECTION_SUCCESS("200", "取消收藏成功"),
    ADD_REQUIRE_SUCCESS("200", "需求发布成功"),
    LOGIN_SUCCESS("200", "登录成功"),
    SEND_AUTH_CODE_SUCCESS("200", "验证码发送成功"),
    CLOSE_REQUIRE_SUCCESS("200", "关闭需求成功"),

    PARAM_ERROR("400", "参数不正确!"),
    PARAM_MISSING("400", "缺少参数!"),
    USER_PASS_NOT_MATCH("400", "用户名密码不匹配"),
    USER_NOT_LOGIN("401", "用户未登陆"),
    USER_AUTHENTICATION_FAIL("401", "用户验证失败"),
    PERMISSION_ERROR("403", "权限不足"),
    FILE_NOT_EXIST("404", "文件不存在！"),
    DATA_NOT_EXIST("404", "数据不存在"),
    ERROR_UPLOAD_NUMBER("415", "上传文件中数据量不能低于5000！"),
    FILE_TYPE_NOT_MATCH("415", "上传文件类型不匹配"),
    FILE_CONTENT_EMPTY("415", "上传文件内容为空！"),

    TASK_START_ERROR("520", "定时任务启动失败"),
    UPLOAD_FILE_ERROR("521", "上传文件失败"),
    UPLOAD_IMAGE_ERROR("522", "图片上传失败"),
    TASK_RECORD_ADD_ERROR("523", "任务记录插入失败"),
    //以下可继续补充

    COMPILER_MODIFIED_ERROR("400","请检查编译器状态及登陆状态"),
    COMPILER_ADD_ERROR("400","请检查编译器参数状态及登陆状态"),
    NEED_CREATE_ERROR("412","编译器不可修改，是否尝试新建编译器？")
    ;


    private String code;
    private String text;
    private Date time;

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    HttpRespCode(String code, String text, Date time) {
        this.code = code;
        this.text = text;
        this.time = time;
    }

    HttpRespCode(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String toString() {
        return "HttpRespCode{" +
                "code='" + code + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }

}