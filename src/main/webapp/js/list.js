$(document).ready(function() {
    $('#knowledgeData').bootstrapTable({
        columns: [{
            checkbox: true,
        }, {
            field: 'id',
            title: 'id',
            visible: false,
        }, {
            field: 'chinese',
            title: '中文',
            align: 'center',
        }, {
            field: 'english',
            title: '英文',
            align: 'center',
        }, {
            field: 'id',
            title: '操作',
            formatter: operateFormatter,
            align: 'center',
            width: '20%',
        }],

        url: '/list',
        method: 'post',
        cache: false,
        uniqueId: 'id',
        pagination: true,
        pageNumber: 1,
        pageSize: 10,
        queryParams: queryParams,
        sidePagination: "server",
        contentType: 'application/x-www-form-urlencoded',
        dataType: 'json',
        responseHandler: responseHandler,
        striped: true,
        showRefresh: true,
        showToggle: true,
        search: true,
    });
})

// 调整后台查询数据时传递的参数
function queryParams(params) {
    var param = {
        page: (params.offset / params.limit) + 1,
        size: params.limit,
        search: params.search,
    };
    return param;
}

// 处理服务器返回的数据，转化为bootstrap-table识别的格式
function responseHandler(data) {
    var res = {};
    res['rows'] = data['data']['list'];
    res['total'] = data['data']['total'];
    return res;
}

//
function operateFormatter(value, row, index) {
    var data = [
        row.id,
        trim_escape(row.chinese),
        trim_escape(row.english),
    ].join("\',\'");
    return [
              '<button class="btn btn-info btn-sm rightSize detailBtn" type="button" onclick="showUpdateKnowledgeDialog(\'' + data +  '\')"><i class="glyphicon glyphicon-pencil"></i> 修改</button>',
              '<button style="margin-left:5px;" class="btn btn-danger btn-sm rightSize packageBtn" type="button"><i class="glyphicon glyphicon-trash"></i> 删除</button>'
          ].join('');
}

function showUpdateKnowledgeDialog(id, chinese, english) {
    $("#id").text(id);
    $("#chinese").val(unescape(chinese));
    $("#english").val(unescape(english));
    $("#updateDialog").modal();
}

function submitUpdateKnowledge() {
    var data = {
        id: $("#id").text(),
        english: $("#english").val(),
    };
    $.ajax({
        url: $("#webpath").val() + 'update-knowledge',
        method: 'POST',
        data: JSON.stringify(data),
        cache: false,
        contentType: 'application/json;charset=UTF-8',
        success: function(res) {
            console.log(JSON.stringify(res));
            $("#updateDialog").modal('toggle');
        },
        error: function(res) {
            console.log(JSON.stringify(res))
        }
    })
}