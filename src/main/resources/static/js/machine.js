$(document).ready(function () {
    $("li.list-item").click(function (event) {
        let li = $(this);
        li.attr('disabled', false);
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
                        li.attr('disabled', true);
                    } else {
                        alert("数据获取失败");
                        li.attr('disabled', true);
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

    $("li").on('click', ".list-item", function (event) {
        let li = $(this);
        li.attr('disabled', false);
        event.stopPropagation();
        let childUl = li.children("ul");
        let idStr = li.children("span:last").attr("id");
        let currentNode = JSON.parse(idStr);
        let table = $("#table");
        table.empty();
        let _detail = '';
        _detail +=
            '<thead>' +
            '   <tr>' +
            '   <th class="col-lg-2 col-md-2">属性</th>' +
            '   <th class="col-lg-10 col-md-10">值</th>' +
            '   </tr>' +
            '</thead>' +
            '<tbody>' +
            '<tr hidden="hidden">' +
            '   <td>ID</td>' +
            '   <td>' + currentNode.id + '</td>' +
            '<tr/>' +
            '<tr>' +
            '   <td>名称</td>' +
            '   <td>' + currentNode.name + '</td>' +
            '<tr/>' +
            '<tr>' +
            '   <td>部套号</td>' +
            '   <td>' + currentNode.structureNo + '</td>' +
            '<tr/>';
        if (1 === currentNode.type) {
            _detail +=
                '<tr>' +
                '   <td>版本号</td>' +
                '   <td>' + currentNode.revision + '</td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr hidden="hidden">' +
                '   <td>版本号</td>' +
                '   <td>' + currentNode.revision + '</td>' +
                '<tr/>';
        }

        if (currentNode.materialNo === null) {
            _detail +=
                '<tr>' +
                '   <td>物料号</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>物料号</td>' +
                '   <td>' + currentNode.materialNo + '</td>' +
                '<tr/>';
        }

        if (currentNode.materialVersion === null || currentNode.materialVersion === "-") {
            _detail +=
                '<tr>' +
                '   <td>物料号版本</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail += '<tr>' +
                '   <td>物料号版本</td>' +
                '   <td>' + currentNode.materialVersion + '</td>' +
                '<tr/>';
        }

        if (currentNode.material === null) {
            _detail +=
                '<tr>' +
                '   <td>材料</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>材料</td>' +
                '   <td>' + currentNode.material + '</td>' +
                '<tr/>';
        }

        if (currentNode.materialJis === null) {
            _detail +=
                '<tr>' +
                '   <td>材料2</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>材料2</td>' +
                '   <td>' + currentNode.materialJis + '</td>' +
                '<tr/>';
        }

        if (currentNode.materialWin === null) {
            _detail +=
                '<tr>' +
                '   <td>材料3</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>材料3</td>' +
                '   <td>' + currentNode.materialWin + '</td>' +
                '<tr/>';
        }

        if (currentNode.drawingNo === null) {
            _detail +=
                '<tr>' +
                '   <td>图号</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>图号</td>' +
                '   <td>' + currentNode.drawingNo + '</td>' +
                '<tr/>';
        }

        if (currentNode.drawingVersion === null || currentNode.drawingVersion === "-") {
            _detail +=
                '<tr>' +
                '   <td>图号版本</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>图号版本</td>' +
                '   <td>' + currentNode.drawingVersion + '</td>' +
                '<tr/>';
        }

        if (currentNode.drawingSize === null) {
            _detail +=
                '<tr>' +
                '   <td>图幅</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>图幅</td>' +
                '   <td>' + currentNode.drawingSize + '</td>' +
                '<tr/>';
        }

        if (currentNode.positionNo === null) {
            _detail +=
                '<tr>' +
                '   <td>部件号</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>部件号</td>' +
                '   <td>' + currentNode.positionNo + '</td>' +
                '<tr/>';
        }


        if (currentNode.weight === null) {
            _detail +=
                '<tr>' +
                '   <td>重量</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>重量</td>' +
                '   <td>' + currentNode.weight + '</td>' +
                '<tr/>';
        }

        if (currentNode.amount === null) {
            _detail +=
                '<tr>' +
                '   <td>总数量</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>总数量</td>' +
                '   <td>' + currentNode.amount + '</td>' +
                '<tr/>';
        }

        if (currentNode.absoluteAmount === null) {
            _detail +=
                '<tr>' +
                '   <td>数量</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>数量</td>' +
                '   <td>' + currentNode.absoluteAmount + '</td>' +
                '<tr/>';
        }

        if (currentNode.sequenceNo === null) {
            _detail +=
                '<tr>' +
                '   <td>排序号</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>排序号</td>' +
                '   <td>' + currentNode.sequenceNo + '</td>' +
                '<tr/>';
        }

        if (currentNode.modifyNote === null) {
            _detail +=
                '<tr>' +
                '   <td>更改通知</td>' +
                '   <td></td>' +
                '<tr/>';
        } else {
            _detail +=
                '<tr>' +
                '   <td>更改通知</td>' +
                '   <td>' + currentNode.modifyNote + '</td>' +
                '<tr/>';
        }

        _detail +=
            '<tr>' +
            '   <td>子组件数</td>' +
            '   <td>' + currentNode.childCount + '</td>' +
            '<tr/>';

        _detail += '</tbody>';
        table.append(_detail);
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
                            li.attr('disabled', true);
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
                            li.attr('disabled', true);
                        }
                    } else {
                        alert("数据获取失败");
                        li.attr('disabled', true);
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
});

function useLatestVersion(machineName, structureNo) {
    $.ajax({
        url: "api/materials/useLatestVersion",
        dataType: "json", // 预期服务器返回的数据类型
        type: "put", // 请求方式PUT
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
                alert("更新成功，请刷新界面");
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

