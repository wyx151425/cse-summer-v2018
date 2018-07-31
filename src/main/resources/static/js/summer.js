$(document).ready(function () {
    $("#xmlImport").click(function () {
        showProgress();
        $.ajaxFileUpload({
            url: 'api/files/import/xml',  // 用于文件上传的服务器端请求地址
            secureuri: false,  // 是否需要安全协议，一般设置为false
            fileElementId: 'manXml',  // 文件上传域的ID
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {machineName: $("#machineName1").val()},
            success: function (data) {
                successCallback(data);
                $("#importManXml").modal();
            },
            error: function () {
                $("#progress-prompt").text("系统错误");
            }
        });
    });

    $("#excelImport").click(function () {
        showProgress();
        $.ajaxFileUpload({
            url: 'api/files/import/excel',  // 用于文件上传的服务器端请求地址
            secureuri: false,  // 是否需要安全协议，一般设置为false
            fileElementId: 'winGDExcel',  // 文件上传域的ID
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {machineName: $("#machineName2").val()},
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                $("#progress-prompt").text("系统错误");
            }
        });
    });

    function showProgress() {
        $('#progress').modal({
            relatedTarget: this,
            onConfirm: function () {

            },
            onCancel: function (e) {

            }
        });
    }

    function successCallback(data) {
        if (200 === data.statusCode) {
            $("#progress-prompt").text("上传成功");
        } else if (8001 === data.statusCode) {
            $("#progress-prompt").text("文件格式错误");
        } else if (8002 === data.statusCode) {
            $("#progress-prompt").text("文件解析错误");
        } else {
            $("#progress-prompt").text("系统错误");
        }
        $("#modal-footer").css("display", "block");
    }
});