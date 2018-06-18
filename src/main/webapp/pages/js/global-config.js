/**
 * Please include this file after jquery
 * @type {string}
 */

var base_url = "/compiler/";
var api_login = "session/login/";
var api_register = "session/register/";
var api_current_user = "user/current/";
var api_re2nfa = "common/re2nfa/";
var api_re2dfa = "common/re2dfa/";
var api_lexer = "lexical/lexer/";
var api_parser = "parser/parser/";
var api_user_compiler = "user/compiler/";
var api_system_compiler = "common/system/compiler/";
var api_compiler_select = "common/compiler/option/";
var api_user_history = "history/list/";
var api_user_rank = "common/rank/";
var api_save_compiler = "user/compiler/modify/";
var api_create_compiler = "user/compiler/new/";
var api_system_compiler_config = "common/system/configuration/";
var api_semantic_analyser = "semantic/analyser/";

var url_dashboard = "pages/dashboard.html";
var url_login = "pages/login/login.html";
var url_compiler_list = "pages/compilers.html";
var colors = [
    "btn btn-primary btn-xs",
    "btn btn-success btn-xs",
    "btn btn-info btn-xs",
    "btn btn-warning btn-xs",
    "btn btn-danger btn-xs",
    "btn btn-default btn-xs",
    "btn btn-rose btn-xs",
    "btn btn-behance btn-xs"
];

/**
 * type = ['','info','success','warning','danger','rose','primary'];
 * @param from      移入方向(top,bottom)
 * @param align     位置（left，center，right）
 */
function showNotification(type,msg){
    $.notify({
        icon: "notifications",
        message: msg

    },{
        type: type,
        timer: 3000,
        placement: {
            from: 'top',
            align: 'right'
        }
    });
}

/**
 * 初始化用户信息
 * @param name
 */
function initUserInfo(){
    var node = $("#user-name");
    $.ajax({
        url: base_url + api_current_user,
        type : "get",
        dataType : "json",
        headers: {'Content-type': 'application/json;charset=UTF-8'},
        success : function(res){
            if(res.resCode === '200'){
                var arr = res.data;
                node.html("<a data-toggle=\"collapse\" href=\"#collapseExample\" class=\"collapsed\">" +
                    ""+arr.nickName+"<b class=\"caret\"></b></a>" +
                    "<div class=\"collapse\" id=\"collapseExample\"><ul class=\"nav\"><li>" +
                    "<a href=\"user.html\">Edit Profile</a></li><li><a href=\"#\">Settings</a>" +
                    "</li></ul></div>");
                return;
            }
            node.html("<a href='login/login.html'><button class=\"btn btn-info\">Login</button></a>");
        },
        error : function(data){
            node.html("<a href='login/login.html'><button class=\"btn btn-info\">Login</button></a>");
        }
    });
}


/**
 * TimeStamp转格式化String
 * @param time
 * @returns {string}
 */
function ts2String (time){
    var datetime = new Date();
    datetime.setTime(time);
    var year = datetime.getFullYear();
    var month = datetime.getMonth() + 1;
    var date = datetime.getDate();
    var hour = datetime.getHours();
    var minute = datetime.getMinutes();
    return year + "-" + month + "-" + date+" "+hour+":"+minute;
}

/**
 * 弹窗，无回调
 * @param type  'success'，'warning'，'error'
 * @param title 弹窗标题
 * @param text 弹窗内容文本
 */
function toast(type,title,text){
    swal({ title: title,
        text: text,
        timer: 3000,
        type: type,
        showConfirmButton: false
    }).then(function () {}, function (reason) {});
}

/**
 * 获取URL中的请求参数
 * @param name  参数名
 * @returns {*}
 */
function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}

/**
 * 显示请求登陆对话框
 */
function showLoginDialog(){
    swal({
        title: 'UNAUTHORIZED ERROR',
        text: "You need to login in to finish your operation!",
        type: 'warning',
        showCancelButton: true,
        confirmButtonClass: 'btn btn-success',
        cancelButtonClass: 'btn btn-danger',
        confirmButtonText: 'Login',
        // cancel
        buttonsStyling: false
    }).then(function() {
        swal({
            title: 'Redirecting!',
            text: 'Your will redirect to Login Page right now.',
            type: 'success',
            confirmButtonClass: "btn btn-success",
            buttonsStyling: false,
            showConfirmButton: false
        });
        location.href = base_url + url_login;
    });
}

