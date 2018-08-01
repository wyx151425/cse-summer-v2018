$(document).ready(function () {
    $("#xmlImport").click(function () {
        $("#manXmlForm").css("display", "none");
        $("#manXmlConfirm").css("display", "block");
        $.ajaxFileUpload({
            url: 'api/files/import/xml',  // 用于文件上传的服务器端请求地址
            secureuri: false,  // 是否需要安全协议，一般设置为false
            fileElementId: 'manXml',  // 文件上传域的ID
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {machineName: $("#machineName1").val()},
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                $(".progress-prompt").text("系统错误");
            }
        });
    });

    $("#excelImport").click(function () {
        $("#winGDExcelForm").css("display", "none");
        $("#winGDExcelConfirm").css("display", "block");
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
                $(".progress-prompt").text("系统错误");
            }
        });
    });

    $("#importFile").click(function () {
        $("#structureExcelForm").css("display", "none");
        $("#structureExcelConfirm").css("display", "block");
        $.ajaxFileUpload({
            url: 'api/files/import/structure/newVersion',  // 用于文件上传的服务器端请求地址
            secureuri: false,  // 是否需要安全协议，一般设置为false
            fileElementId: 'structureExcel',  // 文件上传域的ID
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {machineName: $("#machineName3").val(), structureNo: $("#structureNo").val()},
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                $(".progress-prompt").text("系统错误");
            }
        });
    });

    function successCallback(data) {
        if (200 === data.statusCode) {
            $(".progress-prompt").text("上传成功");
            window.location.href = 'index';
        } else if (8001 === data.statusCode) {
            $(".progress-prompt").text("文件格式错误");
        } else if (8002 === data.statusCode) {
            $(".progress-prompt").text("文件解析错误");
        } else {
            $(".progress-prompt").text("系统错误");
        }
    }
});