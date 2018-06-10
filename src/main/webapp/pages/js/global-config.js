var base_url = "/compiler/";
var api_login = "session/login/";
var api_register = "session/register/";
var api_re2nfa = "common/re2nfa/";
var api_re2dfa = "common/re2dfa/";
var api_lexer = "lexical/lexer/";
var api_parser = "parser/parser/";
var api_user_compiler = "user/compiler/";
var api_system_compiler = "common/system/compiler/";
var api_compiler_select = "common/compiler/option/";
var api_user_history = "history/list/";
var api_save_compiler = "user/compiler/modify/";
var api_create_compiler = "user/compiler/new/";
var api_system_compiler_config = "common/system/configuration/";

var url_dashboard = "pages/dashboard.html";
var url_login = "pages/login/login.html";
var colors = new Array(6);
colors[0]="btn btn-primary btn-xs";
colors[1]="btn btn-success btn-xs";
colors[2]="btn btn-info btn-xs";
colors[3]="btn btn-warning btn-xs";
colors[4]="btn btn-danger btn-xs";
colors[5]="btn btn-default btn-xs";

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

function initTable() {
    //init Table
    $('#datatables').DataTable({
        "pagingType": "full_numbers",
        "lengthMenu": [
            [10, 25, 50, -1],
            [10, 25, 50, "All"]
        ],
        responsive: true,
        language: {
            search: "_INPUT_",
            searchPlaceholder: "Search records",
        }

    });


    var table = $('#datatables').DataTable();

    // Edit record
    table.on('click', '.edit', function () {
        $tr = $(this).closest('tr');

        var data = table.row($tr).data();
        showProcess(data);
    });

    $('.card .material-datatables label').addClass('form-group');
}

function showProcess(data) {
    console.log('You press on Row: ' + data[0] + ' ' + data[1] + ' ' + data[2] + '\'s row.');
}

$(document).ready(function() {
    ToolTip.init({
        delay: 400,
        fadeDuration: 250,
        fontSize: '0.8em',
        theme: 'light',
        textColor: '#757575',
        shadowColor: '#000',
        fontFamily: "'Roboto-Medium', 'Roboto-Regular', Arial"
    });
});