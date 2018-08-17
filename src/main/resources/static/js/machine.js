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
        $("#chinese0").text(currentNode.chinese);
        $("#structureNo0").text(currentNode.structureNo);
        $("#materialNo0").text(currentNode.materialNo);
        $("#materialVersion0").text(currentNode.revision);
        $("#material0").text(currentNode.material);
        $("#drawingNo0").text(currentNode.drawingNo);
        $("#drawingVersion0").text(currentNode.drawingVersion);
        $("#drawingSize0").text(currentNode.drawingSize);
        $("#positionNo0").text(currentNode.positionNo);
        $("#weight0").text(currentNode.weight);
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
                    location.reload();
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
        let materialNo = $("#materialNo5").val();
        let revision = $("#revision5").val();
        let version = $("#version5").val();
        let url = "api/files/export/structure?structureNo=" + structureNo + "&materialNo=" + materialNo + "&revision=" + revision + "&version=" + version;
        let link = $('<a href="' + url + '"></a>');
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
                materialNo: $("#materialNo7").val(),
                revision: $("#revision7").val(),
                version: $("#version7").val(),
                amount: $("#amount7").val()
            }),
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                if (200 === data.statusCode) {
                    location.reload();
                } else if (9001 === data.statusCode) {
                    $("#addDbStructureProgress").text("该物料已经添加为该机器的部套");
                } else if (10001 === data.statusCode) {
                    $("#addDbStructureProgress").text("物料不存在");
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

    let list;

    $("#search").click(function () {
        let search = $(this);
        search.text("查询中...");
        $.ajax({
            url: "api/materials/search?materialNo=" + $("#materialNo7").val(),
            dataType: "json", // 预期服务器返回的数据类型
            type: "get", // 请求方式GET
            contentType: "application/json", // 发送信息至服务器时的内容编码类型
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                search.text("查询");
                list = data.data;
                let revisionSel = $("#revision7");
                revisionSel.empty();
                let versionSel = $("#version7");
                versionSel.empty();
                $.each(list, function (index, value) {
                    revisionSel.append('<option value="' + value.revision + '">' + value.revision + '</option>');
                });
                let latestVersion = list[0].latestVersion;
                for (let index = 0; index <= latestVersion; index++) {
                    versionSel.append('<option value="' + index + '">' + index + '</option>');
                }
            },
            // 请求出错时调用的函数
            error: function () {
                search.text("查询");
                alert("数据发送失败");
            }
        });
    });

    $("#revision7").change(function () {
        let revision7 = $(this);
        let revision = revision7.val();
        let latestVersion;
        for (let index = 0; index < list.length; index++) {
            if (revision == list[index].revision) {
                latestVersion = list[index].latestVersion;
                break;
            }
        }
        let versionSel = $("#version7");
        versionSel.empty();
        for (let index = 0; index <= latestVersion; index++) {
            versionSel.append('<option value="' + index + '">' + index + '</option>');
        }
    });

    $("#publishBtn").click(function () {
        let publishBtn = $(this);
        let publishCancel = $("#publishCancel");
        let structurePublishProgress = $("#structurePublishProgress");
        publishBtn.text("发布中...");
        publishBtn.attr("disabled", "disabled");
        $.ajax({
            url: "api/structures/" + $("#structureId9").val() + "/confirm",
            dataType: "json", // 预期服务器返回的数据类型
            type: "put", // 请求方式GET
            contentType: "application/json", // 发送信息至服务器时的内容编码类型
            async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
            // 请求成功后的回调函数。
            success: function (data) {
                if (200 === data.statusCode) {
                    // structurePublishProgress.text("发布成功，刷新界面查看更新");
                    // publishBtn.css("display", "none");
                    // publishCancel.text("确定");
                    location.reload();
                } else {
                    structurePublishProgress.text("系统错误");
                    publishBtn.css("display", "none");
                    publishCancel.text("确定");
                }
            },
            // 请求出错时调用的函数
            error: function () {
                structurePublishProgress.text("系统错误");
                publishBtn.css("display", "none");
                publishCancel.text("确定");
            }
        });
    });
});

function deleteStructure(id) {
    $.ajax({
        url: "api/structures",
        dataType: "json", // 预期服务器返回的数据类型
        type: "delete", // 请求方式DELETE
        contentType: "application/json", // 发送信息至服务器时的内容编码类型
        // 发送到服务器的数据。
        data: JSON.stringify({
            id: id
        }),
        async: true, // 默认设置下，所有请求均为异步请求。如果设置为false，则发送同步请求
        // 请求成功后的回调函数。
        success: function (data) {
            if (200 === data.statusCode) {
                location.reload();
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

function versionList(structureNo, materialNo, revision, latestVersion) {
    $("#versionChooseForm").css("display", "block");
    $("#versionChooseConfirm").css("display", "none");
    $("#structureNo4").attr("value", structureNo);
    $("#materialNo4").attr("value", materialNo);
    $("#revision4").attr("value", revision);
    let select = $("#version4");
    select.empty();
    for (let index = 0; index <= latestVersion; index++) {
        select.append('<option value="' + index + '">' + index + '</option>');
    }
}

function structureExport(structureNo, materialNo, revision, latestVersion) {
    $("#structureNo5").attr("value", structureNo);
    $("#materialNo5").attr("value", materialNo);
    $("#revision5").attr("value", revision);
    let select = $("#version5");
    select.empty();
    for (let index = 0; index <= latestVersion; index++) {
        select.append('<option value="' + index + '">' + index + '</option>');
    }
}

function publishStructure(structId, structNo) {
    $("#structurePublishProgress").text("您确定要发布" + structNo + "部套吗？");
    $("#structureId9").attr("value", structId);

    $("#publishBtn").removeAttr("disabled").text("发布");
    $("#publishCancel").text("取消");
}
