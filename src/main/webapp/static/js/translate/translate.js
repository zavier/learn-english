$(document).ready(function () {
    activeNav('translate-nav');
    $("#translate").on('click', translate);
})

function translate() {
    var src = $.trim($("#src").val());
    if (!src) {
        alert('中文不能为空');
    }
    $.post('/learn-english/translate/api', { 'query': src }, function (res) {
        console.log(JSON.stringify(res));
        var dest = res.data.dst;
        $("#dst").val(dest);
        return true;
    });
}