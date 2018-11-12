$(document).ready(function () {
    $("#remove-text").click(function () {
        $("#name").val("");
    });

    $("#decode-pwd").click(function () {
        let passwordView = $("#password");
        let type = passwordView.attr("type");
        if ("text" === type) {
            passwordView.attr("type", "password");
        } else if ("password" === type) {
            passwordView.attr("type", "text");
        }
    });

    $("#name").focus(function () {
        $("#icon-user").css("color", "#337ab7");
    }).blur(function () {
        $("#icon-user").css("color", "#999");
    });

    $("#password").focus(function () {
        $("#icon-lock").css("color", "#337ab7");
    }).blur(function () {
        $("#icon-lock").css("color", "#999");
    });

    $("#loginBtn").click(function () {
        let loginBtn = $(this);
        loginBtn.attr("disabled", "disabled");
        let response = $("#response");
        response.text("");
        let nameView = $("#name");
        let passwordView = $("#password");
        let name = nameView.val();
        let password = passwordView.val();
        if ("" === name) {
            response.text("请输入用户名");
            nameView.focus();
            loginBtn.removeAttr("disabled");
            return;
        }
        if ("" === password) {
            passwordView.focus();
            response.text("请输入密码");
            loginBtn.removeAttr("disabled");
            return;
        }
        if (password.length < 6) {
            passwordView.focus();
            response.text("请输入正确格式的密码");
            loginBtn.removeAttr("disabled");
            return;
        }
        loginBtn.text("登录中...");
        $.ajax({
            url: "api/users/login",
            dataType: "json",
            type: "post",
            contentType: "application/json",
            data: JSON.stringify({
                name: name,
                password: password
            }),
            async: true,
            success: function (data) {
                if (200 === data.statusCode) {
                    window.location.href = "index";
                } else if (7000 === data.statusCode) {
                    response.text("用户不存在");
                } else if (7002 === data.statusCode) {
                    response.text("该用户被禁用");
                } else if (7003 === data.statusCode) {
                    response.text("密码错误");
                } else {
                    response.text("系统错误");
                }
                loginBtn.text("登录");
                loginBtn.removeAttr("disabled");
            },
            error: function () {
                response.text("系统错误");
                loginBtn.text("登录");
                loginBtn.removeAttr("disabled");
            }
        });
    });

    document.body.addEventListener('keyup', function (e) {
        console.log(e);
        if (e.keyCode == '13') {
            $("#loginBtn").click();
        }
    });
});