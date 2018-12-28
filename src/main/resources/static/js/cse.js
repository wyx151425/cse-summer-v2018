let requestContext = "http://localhost:8080/dmsdev/";

function getUrlParam(url, name) {
    let pattern = new RegExp("[?&]" + name + "\=([^&]+)", "g");
    let matcher = pattern.exec(url);
    let items = null;
    if (null !== matcher) {
        try {
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        } catch (e) {
            try {
                items = decodeURIComponent(matcher[1]);
            } catch (e) {
                items = matcher[1];
            }
        }
    }
    return items;
}

function getMessage(statusCode) {
    switch (statusCode) {
        case 500:
            return "系统错误";
        case 1001:
            return "用户未注册";
        case 1003:
            return "用户被禁用";
        case 1004:
            return "登录超时";
        case 1005:
            return "密码错误";
        case 1006:
            return "缺少权限";
        case 2001:
            return "文件格式错误";
        case 2002:
            return "文件解析错误";
        case 3001:
            return "机器已包含该部套";
        case 3002:
            return "部套不存在";
        case 3003:
            return "输入部套号与文件内部套号不对应";
        case 3004:
            return "库中已存在该部套";
        case 4002:
            return "存在层级为空的物料";
        case 4003:
            return "部套的物料号为空";
        case 4004:
            return "存在数量为空的物料";
        default:
            return "系统错误";
    }
}

function createAndDownload(filename, content, contentType) {
    if (!contentType) {
        contentType = "application/octet-stream";
    }
    let a = document.createElement("a");
    let blob = new Blob([content], {"type": contentType});
    a.href = window.URL.createObjectURL(blob);
    a.download = filename;
    a.click();
}

function download(file) {
    if (!file) {
        return;
    }
    let url = window.URL.createObjectURL(new Blob([file.data],
        {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8"}));
    let link = document.createElement("a");
    link.style.display = "none";
    link.download = decodeURI((file.headers["content-disposition"].split("="))[1]);
    link.href = url;

    document.body.appendChild(link);
    link.click();
}

const progress = new Vue({
    el: "#progress",
    data: {
        isVisible: true
    },
    methods: {
        dismiss: function () {
            this.isVisible = false;
        }
    }
});

Vue.component("popover", {
    props: ["prompt"],
    template: `
            <div class="popover-box" :class="{success: prompt.status, error: !prompt.status}">
                <span class="icon icon-ok" v-if="prompt.status"></span>
                <span class="icon icon-remove" v-if="!prompt.status"></span>
                <span>{{prompt.message}}</span>
            </div>
        `
});

const popover = new Vue({
    el: "#popover",
    data: {
        prompts: [],
        index: 0
    },
    methods: {
        append: function (message, status) {
            let prompt = {id: this.index, status: status, message: message};
            this.index++;
            this.prompts.push(prompt);
            setTimeout(function () {
                popover.prompts.shift(prompt);
            }, 5000);
        }
    }
});