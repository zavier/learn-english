function loginSubmit(event) {
    event.preventDefault();
    //TODO:数据校验
    var accountOrEmail = $.trim($("#accountOrEmail").val());
    var password = $.trim($("#password").val());
    var param = generateLoginParam(accountOrEmail, password);
    var url = $("#webpath").val() + "login";
    var ajaxParam = getAjaxJsonParamObject(url, 'POST', param);
    ajaxParam.success = function(data) {
        console.log(JSON.stringify(data));
        if (isAjaxSuccess(data)) {
            location.href = $("#webpath").val() + '/list';
        } else {
            logAndAlertErrorMsg('登录失败');
        }
    };
    $.ajax(ajaxParam);
}

function generateLoginParam(accountOrEmail, password) {
    var param = {};
    if (isEmail(accountOrEmail)) {
        param.email = accountOrEmail;
        param.password = password;
    } else {
        param.account = accountOrEmail;
        param.password = password;
    }
    return param;
}

// 校验是否是Email地址
function isEmail(str) {
    // TODO:校验Email方式需要完善
    if (str.indexOf("@") != -1) {
        return true;
    }
    return false;
}