

$.ajax({
    url: '/upload-csvfile',
    type: 'POST',
    cache: false,
    data: new FormData($('#csvFile')[0]),
    processData: false,
    contentType: false
}).done(function(res) {

}).fail(function(res) {

});