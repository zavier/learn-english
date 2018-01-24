$("#loginForm").validate({
    debug: true,
    onkeyup: function (element, event) {
        //去除左侧空格
        var value = this.elementValue(element).replace(/^\s+/g, "");
        $(element).val(value);
    },
    submitHandler: function () {
        // 校验通过
        var accountOrEmail = $("#accountOrEmail").val();
        var password = $("#password").val();
        var param = generateLoginParam(accountOrEmail, password);
        var url = "/user/login";
        var ajaxParam = getAjaxJsonParamObject(url, 'POST', param);
        ajaxParam.success = function (data) {
            console.log(JSON.stringify(data));
            if (isAjaxSuccess(data)) {
                location.href = '/knowledge/list';
            } else {
                logAndAlertErrorMsg(data, '登录失败: ' + data.msg);
            }
        };
        $.ajax(ajaxParam);
    }
});

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