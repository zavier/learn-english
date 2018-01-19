function trim_escape(str) {
    var trimStr = str.replace(/(^\s*)|(\s*$)/g, "");
    return escape(trimStr);
}

// 生成基础的Ajax请求需要的参数对象（参数类型为JSON）
function getAjaxJsonParamObject(url, method, data) {
    var ajaxData = {
        url: url,
        method: method,
        data: JSON.stringify(data),
        cache: false,
        contentType: 'application/json;charset=UTF-8',
        error: function(data) {
            console.error(JSON.stringify(data));
            alertErrorMsg('操作失败');
        }
    };
    return ajaxData;
}

function isAjaxSuccess(data) {
    if (data.code == 0) {
        return true;
    }
    return false;
}

function alertSuccessMsg(msg) {
    $.confirm({
        title: '提示',
        content: msg,
        autoClose: '确定|1000',
        buttons: {
            确定: {}
        }
    });
}

function logAndAlertErrorMsg(data, msg) {
    console.error(JSON.stringify(data));
    alertErrorMsg(msg);
}

function alertErrorMsg(msg) {
    $.alert({
        title: '错误',
        content: msg,
    });
}

function logout() {
    // TODO:临时测试用
    $.ajax('/user/logout', function(data) {

    } )
}