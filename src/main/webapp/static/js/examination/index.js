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
        url: webpath + 'examination/upload-answer',
        type: 'POST',
        dataType: 'json',
        data: JSON.stringify(data),
        headers: {'Content-Type': 'application/json'},
        success: function(res) {
            if (res.code == 0) {
                window.location.href = webpath + 'examination/show-result';
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
    if (unCompleteNum != 0) {
        $("#confirmSubmitInfo").html('还有 <b>' + unCompleteNum + '</b> 道题没有完成，是否确定提交?');
    } else {
        $("#confirmSubmitInfo").text("题目全部完成，是够确定提交?");
    }
    $("#myModal").modal();
    return false;
}