$(document).ready(function() {
    initWebpath();
    initSubmitBtn();
})

var webpath;
function initWebpath() {
    if (location.port == '') { // 默认80端口
        webpath = `${location.protocol}//${location.host}/`
    } else {
        webpath = `${location.protocol}//${location.host}:${location.port}/`
    }
}

function initSubmitBtn() {
    $("#submit").on('click', function() {
        var params = [];
        $(".english-data").each(function() {
            var param = {
                "id": $(this).data("id"),
                "english": $(this).val(),
            }
            params.push(param)
        })
        console.log(JSON.stringify(params))
        $.ajax({
            url: 'http://localhost/upload-answer',
            type: 'POST',
            dataType: 'json',
            data: JSON.stringify(params),
            headers: {'Content-Type': 'application/json'},
            success: function(res) {
                if (res.code == 0) {
                    layer.msg("提交成功");
                    window.location.href = webpath + 'show-result';
                } else {
                    alert('提交失败\n' + res.msg);
                }
            }
        })
    })
}