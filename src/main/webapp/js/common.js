var webpath;
function initWebpath() {
    if (location.port == '') { // 默认80端口
        webpath = `${location.protocol}//${location.host}/`
    } else {
        webpath = `${location.protocol}//${location.host}:${location.port}/`
    }
}

function trim_escape(str) {
    var trimStr = str.replace(/(^\s*)|(\s*$)/g, "");
    return escape(trimStr);
}