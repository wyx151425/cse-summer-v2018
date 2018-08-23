$(document).ready(function () {
    $("#CSEBOMImport").click(function () {
        $("#CSEBOMForm").css("display", "none");
        $("#CSEBOMConfirm").css("display", "block");
        $.ajaxFileUpload({
            url: 'api/files/import/cse',  // 用于文件上传的服务器端请求地址
            secureuri: false,  // 是否需要安全协议，一般设置为false
            fileElementId: 'csebom',  // 文件上传域的ID
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {machineName: $("#machineName3").val()},
            success: function (data) {
                successCallback(data);
            },
            error: function () {
                $(".progress-prompt").text("系统错误");
            }
        });
    });

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

    $("#importNewStructureFile").click(function () {
        $("#newStructureExcelForm").css("display", "none");
        $("#newStructureExcelConfirm").css("display", "block");

        $.ajaxFileUpload({
            url: 'api/files/import/structure/new',  // 用于文件上传的服务器端请求地址
            secureuri: false,  // 是否需要安全协议，一般设置为false
            fileElementId: 'newStructureExcel',  // 文件上传域的ID
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {
                machineName: $("#machineName8").val(),
                structureNo: $("#structureNo8").val(),
                amount: $("#amount8").val()
            },
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
            data: {
                machineName: $("#machineName3").val(),
                structureNo: $("#structureNo3").val()
            },
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
            $(".progress-prompt").text("上传成功, 请刷新页面查看更新");
            location.reload();
        } else if (7004 === data.statusCode) {
            location.reload();
        } else if (8001 === data.statusCode) {
            $(".progress-prompt").text("文件格式错误");
        } else if (8002 === data.statusCode) {
            $(".progress-prompt").text("文件解析错误");
        } else if (9001 === data.statusCode) {
            $(".progress-prompt").text("部套已存在");
        } else if (9002 === data.statusCode) {
            $(".progress-prompt").text("输入部套号与文件内部套号不相同");
        } else {
            $(".progress-prompt").text("系统错误");
        }
    }

    $("#updateBtn").click(function () {
        $("#machineEditForm").css("display", "none");
        $("#machineEditConfirm").css("display", "block");
        let machineEditProgress = $("#machineEditProgress");
        machineEditProgress.css("display", "block")
            .text("数据修改中...");
        $.ajax({
            url: "api/machines",
            dataType: "json", // 预期服务器返回的数据类型
            type: "put", // 请求方式PUT
            contentType: "application/json", // 发送信息至服务器时的内容编码类型
            // 发送到服务器的数据。
            data: JSON.stringify({
                id: $("#id").val(),
                machineNo: $("#machineNo").val(),
                type: $("#type").val(),
                cylinderAmount: $("#cylinderAmount").val(),
                shipNo: $("#shipNo").val(),
                classificationSociety: $("#classificationSociety").val()
            }),
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                if (200 === data.statusCode) {
                    location.reload();
                } else if (7004 === data.statusCode) {
                    location.reload();
                } else if (9001 === data.statusCode) {
                    $("#machineEditProgress").text("部套不存在");
                } else {
                    $("#machineEditProgress").text("系统错误");
                }
            },
            // 请求出错时调用的函数
            error: function () {
                $("#machineEditProgress").text("系统错误");
            }
        });
    });

    $("#logout").click(function () {
        $.ajax({
            url: "api/users/logout",
            dataType: "json",
            type: "post",
            contentType: "application/json",
            async: true,
            success: function (data) {
                if (200 === data.statusCode) {
                    location.reload();
                }
            }
        });
    });

    $("#pwdBtn").click(function () {
        let pwdBtn = $(this);
        pwdBtn.attr("disabled", "disabled");
        let response = $("#response");
        response.text("");
        let newPwd = $("#newPwd").val();
        let confirmPwd = $("#confirmPwd").val();
        if (confirmPwd !== newPwd) {
            response.text("两次密码不一致");
            pwdBtn.removeAttr("disabled");
            return;
        }
        if (newPwd.length < 6) {
            response.text("密码长度应不少于6位");
            pwdBtn.removeAttr("disabled");
            return;
        }
        pwdBtn.text("修改中...");
        $.ajax({
            url: "api/users/password",
            dataType: "json",
            type: "put",
            contentType: "application/json",
            data: JSON.stringify({
                password: newPwd
            }),
            async: true,
            success: function (data) {
                if (200 === data.statusCode) {
                    window.location.href = "index";
                }
            },
            error: function () {
                response.text("系统错误");
            }
        });
    });
});

function toUpdateMachine(machineId, machineName, machineNo, machineType, cylinderAmount, shipNo, classificationSociety) {
    $("#id").val(machineId);
    $("#name").val(machineName);
    $("#machineNo").val(machineNo);
    $("#type").val(machineType);
    $("#cylinderAmount").val(cylinderAmount);
    $("#shipNo").val(shipNo);
    $("#classificationSociety").val(classificationSociety);
    $("#machineEditForm").css("display", "block");
    $("#machineEditConfirm").css("display", "none");
}

function toExportMachineBom(machineName, machineNo, machineType, machineCylinder, shipNo, cs, status) {
    if (null == machineNo || null == machineType || null == machineCylinder || null == shipNo || null == cs
        || "" === machineNo || "" === machineType || "" === machineCylinder || "" === shipNo || "" === cs) {
        alert("请完善机器信息");
    } else {
        let url = "api/files/export/machine?machineName=" + machineName + "&status=" + status;
        let link = $('<a href="' + url + '"></a>');
        link.get(0).click();
    }
}