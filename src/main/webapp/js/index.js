$(document).ready(function() {
    initWebpath();
})

var webpath;
function initWebpath() {
    if (location.port == '') { // 默认80端口
        webpath = `${location.protocol}//${location.host}/`
    } else {
        webpath = `${location.protocol}//${location.host}:${location.port}/`
    }
}

function checkData() {
    var data = getData();
    checkCompleteInfoData(data);
}

function getData() {
    var params = [];
    $(".english-data").each(function() {
        var param = {
            "id": $(this).data("id"),
            "english": $.trim($(this).val()),
        }
        params.push(param)
    })
    return params;
}

function submitResult() {
    var data = getData();
    $.ajax({
        url: webpath + 'upload-answer',
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(data),
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
}

function checkCompleteInfoData(datas) {
    var unCompleteNum = 0;
    for (var i = 0; i < datas.length; i++) {
        if (!datas[i].english) {
            unCompleteNum++;
        }
    }
    $("#confirmSubmitInfo").text('还有' + unCompleteNum + '道题没有完成，是否确定提交?');
    $("#myModal").modal();
    return false;
}