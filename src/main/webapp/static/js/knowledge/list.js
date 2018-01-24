$(document).ready(function () {
    $('#knowledgeData').bootstrapTable({
        columns: [{
            checkbox: true,
        }, {
            field: 'id',
            title: 'id',
            visible: false,
            cardVisible: false,
            switchable: false,
        }, {
            field: 'chinese',
            title: '中文',
            align: 'center',
        }, {
            field: 'english',
            title: '英文',
            align: 'center',
        }, {
            field: 'knowledge',
            title: '知识点',
            align: 'center',
        }, {
            field: 'id',
            title: '操作',
            formatter: operateFormatter,
            align: 'center',
            width: '20%',
        }],

        url: '/knowledge/list-user-create',
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
        toolbar: '#toolbar',
        showColumns: true,
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
        trim_escape(row.knowledge),
    ].join("\',\'");
    return [
        '<button class="btn btn-info btn-sm rightSize detailBtn" type="button" onclick="showUpdateKnowledgeDialog(\'' + data + '\')"><i class="glyphicon glyphicon-pencil"></i> 修改</button>',
        '<button style="margin-left:5px;" class="btn btn-danger btn-sm rightSize packageBtn" type="button" onclick="deleteKnowledgeConfirm(\'' + row.id + '\');"><i class="glyphicon glyphicon-trash"></i> 删除</button>'
    ].join('');
}

function showUpdateKnowledgeDialog(id, chinese, english, knowledge) {
    $("#id").text(id);
    $("#update-chinese").val(unescape(chinese));
    $("#update-english").val(unescape(english));
    $("#update-knowledge").val(unescape(knowledge));
    $("#update-dialog").modal();
}

$("#updateKnowledgeForm").validate({
    debug: true,
    onkeyup: function (element, event) {
        //去除左侧空格
        var value = this.elementValue(element).replace(/^\s+/g, "");
        $(element).val(value);
    },
    submitHandler: function () {
        // 校验通过
        var data = {
            id: $("#id").text(),
            chinese: $("#update-chinese").val(),
            english: $("#update-english").val(),
            knowledge: $("#update-knowledge").val(),
        };
        var url = '/knowledge/update-knowledge';
        var params = getAjaxJsonParamObject(url, 'POST', data);
        params.success = function (res) {
            if (isAjaxSuccess(res)) {
                $("#update-dialog").modal('toggle');
                $('#knowledgeData').bootstrapTable('refresh');
            } else {
                logAndAlertErrorMsg(res, '更新失败');
            }
        }
        $.ajax(params);
    }
});

function submitUpdateKnowledge() {
    // 调用form表单中的提交按钮，以触发校验中的提交事件
   $("#realSubmitUpdateKnowledge").click(); 
}

function deleteKnowledgeConfirm(id) {
    $.confirm({
        title: '注意!',
        content: '删除后无法恢复，是否确定删除？',
        buttons: {
            确定: function () {
                $.get('/knowledge/delete-knowledge/' + id, function (data) {
                    if (isAjaxSuccess(data)) {
                        alertSuccessMsg('删除成功');
                        $('#knowledgeData').bootstrapTable('refresh');
                    } else {
                        logAndAlertErrorMsg(data, '删除失败');
                    }
                });
            },
            取消: function () {

            }
        }
    });
}

function showAddKnowledgeDialog() {
    $("#add-chinese").val('');
    $("#add-english").val('');
    $("#add-knowledge").val('');
    $("#add-dialog").modal();
}

$("#addKnowledgeForm").validate({
    debug: true,
    onkeyup: function (element, event) {
        //去除左侧空格
        var value = this.elementValue(element).replace(/^\s+/g, "");
        $(element).val(value);
    },
    submitHandler: function () {
        // 校验通过
        var data = {
            chinese: $("#add-chinese").val(),
            english: $("#add-english").val(),
            type: $("#knowledge-type").val(),
            knowledge: $("#add-knowledge").val(),
        };

        var url = '/knowledge/save-knowledge';
        var params = getAjaxJsonParamObject(url, 'POST', data);
        params.success = function (data) {
            if (isAjaxSuccess(data)) {
                alertSuccessMsg('添加成功');
                $("#add-dialog").modal('toggle');
                $('#knowledgeData').bootstrapTable('refresh');
            } else {
                logAndAlertErrorMsg(data, '添加失败');
            }
        }
        $.ajax(params);
    }
});

function submitSaveKnowledge() {
    // 调用form表单中的提交按钮，以触发校验中的提交事件
    $("#realSubmitSaveKnowledge").click();
}

function getKnowledgeTypeName(value, row, index) {
    // 0-单词 1-短语 2-句子
    switch (value) {
        case 0:
            return "单词";
        case 1:
            return "短语";
        case 2:
            return "句子";
        default:
            return "未识别的类型";
    }
}