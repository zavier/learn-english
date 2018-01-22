function registry() {
    $("#registryForm").validate({
        debug: true,
        onkeyup: function(element, event) {
            //去除左侧空格
            var value = this.elementValue(element).replace(/^\s+/g, "");
            $(element).val(value);
        },
        submitHandler: function () {
            // 校验通过
            var ajaxParam = getAjaxJsonParamObject('/user/register', 'POST', getData());
            ajaxParam.success = function (data) {
                console.log(JSON.stringify(data));
                if (isAjaxSuccess(data)) {
                    location.href = '/user/login';
                } else {
                    logAndAlertErrorMsg(data, '注册失败,' + data.msg);
                }
            };
            $.ajax(ajaxParam);
        }
    });
    return false;
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