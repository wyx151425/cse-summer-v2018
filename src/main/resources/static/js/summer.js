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
            fileElementId: ['strFile81', 'strFile82', 'strFile83', 'strFile84', 'strFile85', 'strFile86', 'strFile87', 'strFile88', 'strFile89'],
            dataType: 'json',  // 返回值类型 一般设置为json
            data: {
                machineName: $("#machineName8").val(),
                structureNo1: $("#structureNo81").val(), amount1: $("#amount81").val(),
                structureNo2: $("#structureNo82").val(), amount2: $("#amount82").val(),
                structureNo3: $("#structureNo83").val(), amount3: $("#amount83").val(),
                structureNo4: $("#structureNo84").val(), amount4: $("#amount84").val(),
                structureNo5: $("#structureNo85").val(), amount5: $("#amount85").val(),
                structureNo6: $("#structureNo86").val(), amount6: $("#amount86").val(),
                structureNo7: $("#structureNo87").val(), amount7: $("#amount87").val(),
                structureNo8: $("#structureNo88").val(), amount8: $("#amount88").val(),
                structureNo9: $("#structureNo89").val(), amount9: $("#amount89").val()
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
        } else if (602 === data.statusCode) {
            $(".progress-prompt").text("上传文件缺少部套号或数量");
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
        } else if (9003 === data.statusCode) {
            $(".progress-prompt").text("库中不存在该部套，无法更新版本");
        } else if (10002 === data.statusCode) {
            $(".progress-prompt").text("存在层级为空的物料");
        } else if (10003 === data.statusCode) {
            $(".progress-prompt").text("顶层物料的物料号为空");
        } else if (10004 === data.statusCode) {
            $(".progress-prompt").text("存在层数量为空的物料");
        } else if (20001 === data.statusCode) {
            $(".progress-prompt").text("某顶层物料的物料号为空，因此部分文件导入失败");
        } else if (20002 === data.statusCode) {
            $(".progress-prompt").text("缺少某部套的部套号或数量，因此部分文件导入失败");
        } else if (20003 === data.statusCode) {
            $(".progress-prompt").text("某部套已存在，因此部分文件导入失败");
        } else if (20004 === data.statusCode) {
            $(".progress-prompt").text("某部套存在空层级物料，因此部分文件导入失败");
        } else if (20005 === data.statusCode) {
            $(".progress-prompt").text("某部套输入部套号与文件内部套号不一致，因此部分文件导入失败");
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
                patent: $("#patent").val(),
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

    $("#searchStrB").click(function () {
        let search = $(this);
        search.text("查询中...");
        $.ajax({
            url: "api/structures/search?materialNo=" + $("#materialNoI").val(),
            dataType: "json", // 预期服务器返回的数据类型
            type: "get", // 请求方式GET
            contentType: "application/json", // 发送信息至服务器时的内容编码类型
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                if (200 === data.statusCode) {
                    search.text("精确查询");
                    list = data.data;
                    let machineNameSel = $("#structureList");
                    machineNameSel.empty();
                    $.each(list, function (index, value) {
                        let _html = '';
                        _html += '<tr>' +
                            '   <td>' + value.machineName + '</td>' +
                            '   <td>' + value.version + '</td>';
                        if (null !== value.amount) {
                            _html += '   <td>' + value.amount + '</td>';
                        }
                        _html += '</tr>';
                        machineNameSel.append(_html);
                    });
                } else if (7004 === data.statusCode) {
                    location.reload();
                }
            },
            // 请求出错时调用的函数
            error: function () {
                search.text("模糊查询");
                alert("数据发送失败");
            }
        });
    });
});

function toUpdateMachine(machineId, machineName, machineNo, machineType, patent, cylinderAmount, shipNo, classificationSociety) {
    $("#id").val(machineId);
    $("#name").val(machineName);
    $("#machineNo").val(machineNo);
    $("#type").val(machineType);
    $("#patent").val(patent);
    $("#cylinderAmount").val(cylinderAmount);
    $("#shipNo").val(shipNo);
    $("#classificationSociety").val(classificationSociety);
    $("#machineEditForm").css("display", "block");
    $("#machineEditConfirm").css("display", "none");
}

function toView(machineId, machineNo, machineType, patent, machineCylinder, shipNo, cs) {
    if (null == machineNo || null == machineType || null == machineCylinder || null == shipNo || null == cs || null == patent || "" === patent
        || "" === machineNo || "" === machineType || "" === machineCylinder || "" === shipNo || "" === cs) {
        alert("请完善机器信息");
    } else {
        let url = "machine?machineId=" + machineId;
        let link = $('<a href="' + url + '"></a>');
        link.get(0).click();
    }
}

function toExportMachineBom(machineName, machineNo, machineType, patent, machineCylinder, shipNo, cs, status) {
    if (null == machineNo || null == machineType || null == machineCylinder || null == shipNo || null == cs || null == patent || "" === patent
        || "" === machineNo || "" === machineType || "" === machineCylinder || "" === shipNo || "" === cs) {
        alert("请完善机器信息");
    } else {
        let url = "api/files/export/machine?machineName=" + machineName + "&status=" + status;
        let link = $('<a href="' + url + '"></a>');
        link.get(0).click();
    }
}