$(document).ready(function () {
    $("li.list-item").click(function (event) {
        let li = $(this);
        li.attr("disabled", "disabled");
        event.stopPropagation();
        let childUl = li.children("ul");
        if (0 === childUl.length) {
            $.ajax({
                url: "api/machines/" + li.children("span:last").text() + "/materials",
                dataType: "json",
                type: "get",
                contentType: "application/json",
                async: true,
                success: function (data) {
                    if (200 === data.statusCode) {
                        let _html = '<ul class="list">';
                        $.each(data.data, function (index, value) {
                            _html += '<li class="list-item">';
                            if (0 === value.childCount) {
                                _html += '   <span class="glyphicon glyphicon-cog"></span>';
                            } else {
                                _html += '   <span class="glyphicon glyphicon-triangle-right"></span>';
                            }
                            _html +=
                                "   <span id='" + JSON.stringify(value) + "'>" + value.materialNo + '</span>' +
                                '</li>';
                        });
                        _html += '</ul>';
                        li.append(_html);
                        li.children("span:first").removeClass("glyphicon glyphicon-triangle-right");
                        li.children("span:first").addClass("glyphicon glyphicon-triangle-bottom");
                        li.removeAttr("disabled");
                    } else {
                        alert("数据获取失败");
                        li.removeAttr("disabled");
                    }
                }
            });
        } else {
            if (childUl.is(":hidden")) {
                li.children("span:first").removeClass("glyphicon glyphicon-triangle-right");
                li.children("span:first").addClass("glyphicon glyphicon-triangle-bottom");
                childUl.show();
            } else {
                li.children("span:first").removeClass("glyphicon glyphicon-triangle-bottom");
                li.children("span:first").addClass("glyphicon glyphicon-triangle-right");
                childUl.hide();
                $("#table").removeClass("hidden");
                $("#material-info").addClass("hidden");
            }
        }
    });

    $("li").on('click', ".list-item", function (event) {
        let li = $(this);
        $("#table").addClass("hidden");
        $("#material-info").removeClass("hidden");
        li.attr("disabled", "disabled");
        event.stopPropagation();
        let childUl = li.children("ul");
        let idStr = li.children("span:last").attr("id");
        let currentNode = JSON.parse(idStr);
        $("#id0").text(currentNode.id);
        $("#name0").text(currentNode.name);
        $("#structureNo0").text(currentNode.structureNo);
        $("#materialNo0").text(currentNode.materialNo);
        $("#materialVersion0").text(currentNode.materialVersion);
        $("#material0").text(currentNode.material);
        $("#drawingNo0").text(currentNode.drawingNo);
        $("#drawingVersion0").text(currentNode.drawingVersion);
        $("#drawingSize0").text(currentNode.drawingSize);
        $("#positionNo0").text(currentNode.positionNo);
        $("#weight0").text(currentNode.weight);
        $("#amount0").text(currentNode.amount);
        $("#absoluteAmount0").text(currentNode.absoluteAmount);
        $("#sequenceNo0").text(currentNode.sequenceNo);
        $("#modifyNote0").text(currentNode.modifyNote);
        $("#childCount0").text(currentNode.childCount);
        if (0 === childUl.length) {
            $.ajax({
                url: "api/materials?parentId=" + currentNode.objectId,
                dataType: "json",
                type: "get",
                contentType: "application/json",
                async: true,
                success: function (data) {
                    if (200 === data.statusCode) {
                        if (0 === data.data.length) {
                            li.children("span:first").removeClass("glyphicon glyphicon-triangle-right");
                            li.children("span:first").addClass("glyphicon glyphicon-cog");
                            li.removeAttr("disabled");
                        } else {
                            let _html = '<ul class="list">';
                            $.each(data.data, function (index, value) {
                                value.parentMaterialNo = currentNode.materialNo;
                                _html += '<li class="list-item">';
                                if (0 === value.childCount) {
                                    _html += '   <span class="glyphicon glyphicon-cog"></span>';
                                } else {
                                    _html += '   <span class="glyphicon glyphicon-triangle-right"></span>';
                                }
                                _html +=
                                    "   <span id='" + JSON.stringify(value) + "'>" + value.materialNo + '</span>' +
                                    '</li>';
                            });
                            _html += '</ul>';
                            li.append(_html);
                            li.children("span:first").removeClass("glyphicon glyphicon-triangle-right");
                            li.children("span:first").addClass("glyphicon glyphicon-triangle-bottom");
                            li.removeAttr("disabled");
                        }
                    } else {
                        alert("数据获取失败");
                        li.removeAttr("disabled");
                    }
                }
            });
        } else {
            if (childUl.is(":hidden")) {
                li.children("span:first").removeClass("glyphicon glyphicon-triangle-right");
                li.children("span:first").addClass("glyphicon glyphicon-triangle-bottom");
                childUl.show();
            } else {
                li.children("span:first").removeClass("glyphicon glyphicon-triangle-bottom");
                li.children("span:first").addClass("glyphicon glyphicon-triangle-right");
                childUl.hide();
            }
        }
    });

    $("#updateVersion").click(function () {
        $("#versionChooseForm").css("display", "none");
        $("#versionChooseConfirm").css("display", "block");
        $.ajax({
            url: "api/structures/version",
            dataType: "json", // 预期服务器返回的数据类型
            type: "put", // 请求方式PUT
            contentType: "application/json", // 发送信息至服务器时的内容编码类型
            // 发送到服务器的数据。
            data: JSON.stringify({
                machineName: $("#machineName4").val(),
                structureNo: $("#structureNo4").val(),
                version: $("#version4").val()
            }),
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                if (200 === data.statusCode) {
                    $("#versionChooseProgress").text("更新成功，请刷新界面查看更新");
                } else {
                    $("#versionChooseProgress").text("系统错误");
                }
            },
            // 请求出错时调用的函数
            error: function () {
                $("#versionChooseProgress").text("系统错误");
            }
        });
    });

    $("#exportStructure").click(function () {
        let structureNo = $("#structureNo5").val();
        let version = $("#version5").val();
        let url = "api/files/export/structure?structureNo=" + structureNo + "&version=" + version;
        let link= $('<a href="'+ url +'"></a>');
        link.get(0).click();
    });

    $("#addDbButton").click(function () {
        $("#addDbStructureForm").css("display", "block");
        $("#addDbStructureConfirm").css("display", "none");
    });

    $("#addDbStructureFile").click(function () {
        $("#addDbStructureForm").css("display", "none");
        $("#addDbStructureConfirm").css("display", "block");
        $.ajax({
            url: "api/structures/db",
            dataType: "json", // 预期服务器返回的数据类型
            type: "post", // 请求方式PUT
            contentType: "application/json", // 发送信息至服务器时的内容编码类型
            // 发送到服务器的数据。
            data: JSON.stringify({
                machineName: $("#machineName7").val(),
                structureNo: $("#structureNo7").val(),
                version: $("#version7").val()
            }),
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                if (200 === data.statusCode) {
                    $("#addDbStructureProgress").text("更新成功，请刷新页面查看更新");
                } else if (9001 === data.statusCode) {
                    $("#addDbStructureProgress").text("部套不存在");
                } else {
                    $("#addDbStructureProgress").text("系统错误");
                }
            },
            // 请求出错时调用的函数
            error: function () {
                $("#addDbStructureProgress").text("系统错误");
            }
        });
    });
});

function deleteStructure(machineName, structureNo) {
    $.ajax({
        url: "api/structures",
        dataType: "json", // 预期服务器返回的数据类型
        type: "delete", // 请求方式DELETE
        contentType: "application/json", // 发送信息至服务器时的内容编码类型
        // 发送到服务器的数据。
        data: JSON.stringify({
            machineName: machineName,
            structureNo: structureNo
        }),
        async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
        // 请求成功后的回调函数。
        success: function (data) {
            if (200 === data.statusCode) {
                alert("删除成功，请刷新界面");
            } else {
                alert("系统错误");
            }
        },
        // 请求出错时调用的函数
        error: function () {
            alert("数据发送失败");
        }
    });
}

function versionList(structureNo, latestVersion) {
    $("#versionChooseForm").css("display", "block");
    $("#versionChooseConfirm").css("display", "none");
    $("#structureNo4").attr("value", structureNo);
    let select = $("#version4");
    select.empty();
    for (let index = 0; index <= latestVersion; index++) {
        select.append('<option value="' + index + '">' + index +'</option>');
    }
}

function structureExport(structureNo, latestVersion) {
    $("#structureNo5").attr("value", structureNo);
    let select = $("#version5");
    select.empty();
    for (let index = 0; index <= latestVersion; index++) {
        select.append('<option value="' + index + '">' + index +'</option>');
    }
}

