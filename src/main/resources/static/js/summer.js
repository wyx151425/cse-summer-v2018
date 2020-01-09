let requestContext = "http://10.10.20.102:8080/dmspro/";

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
        case 600:
            return "参数错误";
        case 1001:
            return "用户未注册";
        case 1002:
            return "用户已存在";
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
        case 2003:
            return "Sheet解析错误";
        case 3001:
            return "部套不存在";
        case 3002:
            return "库中已存在该部套";
        case 3003:
            return "部套号解析错误";
        case 3004:
            return "机器已包含该部套";
        case 3005:
            return "请填写部套版本描述信息";
        case 4001:
            return "存在物料号为空的物料";
        case 4002:
            return "存在层级为空的物料";
        case 4003:
            return "存在数量为空的物料";
        default:
            return "系统错误";
    }
}

function createAndDownload(filename, content, contentType, reload) {
    if (!contentType) {
        contentType = "application/octet-stream";
    }
    let a = document.createElement("a");
    let blob = new Blob([content], {"type": contentType});
    a.href = window.URL.createObjectURL(blob);
    a.download = filename;
    document.body.appendChild(a);
    a.click();

    let userAgent = navigator.userAgent;
    if (reload && userAgent.indexOf("Edge") < 0) {
        window.location.reload();
    }
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

const header = new Vue({
    el: "#header",
    data: {
        user: {}
    },
    mounted: function () {
        this.user = JSON.parse(localStorage.user);
    }
});

const progress = new Vue({
    el: "#progress",
    data: {
        isVisible: true
    },
    methods: {
        show: function () {
            this.isVisible = true;
        },
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