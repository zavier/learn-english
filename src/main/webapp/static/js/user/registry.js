function registry() {
    if (!checkData()) {
        alertErrorMsg('请检查数据是否填写完整');
        return false;
    }
    var ajaxParam = getAjaxJsonParamObject('/user/register', 'POST', getData())
    ajaxParam.success = function(data) {
        console.log(JSON.stringify(data));
        if (isAjaxSuccess(data)) {
            location.href = '/user/login';
        } else {
            logAndAlertErrorMsg(data, '注册失败,' + data.msg);
        }
    };
    $.ajax(ajaxParam);
}

function checkData() {
    var data = getData();
    for (var key in data) {
        if (!data[key]) {
            return false;
        }
    }
    return true;
}

function getData() {
    var data = {};
    var account = $.trim($("#account").val());
    var email = $.trim($("#email").val());
    var password = $.trim($("#password").val());
    data.account = account;
    data.email = email;
    data.password = password;
    return data;
}