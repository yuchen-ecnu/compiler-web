var base_url = "/compiler/";
var api_login = "session/login/";
var api_register = "session/register/";
var api_re2nfa = "common/re2nfa/";
var api_re2dfa = "common/re2dfa/";
var api_lexer = "lexical/lexer/";
var api_user_compiler = "user/compiler/";
var api_system_compiler = "common/system/compiler/";
var api_compiler_select = "common/compiler/option/";
var api_user_history = "history/list/";
var api_system_compiler_config = "common/system/configuration/";

var url_dashboard = "pages/dashboard.html";
var url_login = "pages/login/login.html";
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

function toast(type,title,text){
    swal({ title: title,
        text: text,
        timer: 3000,
        type: type,
        showConfirmButton: false
    }).then(function () {}, function (reason) {});
}