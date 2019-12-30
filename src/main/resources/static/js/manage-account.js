const main = new Vue({
    el: "#main",
    data: {
        user: {},
        pageInfo: {
            pageNum: 0,
            pages: 0
        },
        pageNum: "",
        accountList: []
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        setPageInfo: function (pageInfo) {
            this.pageInfo = pageInfo;
            this.accountList = pageInfo.data;
        },
        setUserList: function (userList) {
            this.accountList = userList;
        },
        getUserList: function () {
            return this.accountList;
        },
        queryPreviousPage: function () {
            if (this.pageInfo.pageNum > 1) {
                const pageNum = this.pageInfo.pageNum - 1;
                this.queryAccounts(pageNum);
            }
        },
        queryNextPage: function () {
            if (this.pageInfo.pageNum < this.pageInfo.pages) {
                const pageNum = this.pageInfo.pageNum + 1;
                this.queryAccounts(pageNum);
            }
        },
        queryByPagination: function () {
            if (null === this.pageNum || '' === this.pageNum) {
                popover.append('请填写查询页码', false);
                return;
            }
            if (this.pageNum < 1 || this.pageNum > this.pageInfo.pages) {
                popover.append('超出数据可查询范围', false);
                return;
            }
            this.queryAccounts(this.pageNum);
        },
        queryAccounts: function (pageNum) {
            axios.get(requestContext + "api/accounts/query?pageNum=" + pageNum)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.setPageInfo(response.data.data);
                    } else {
                        popover.append("数据获取失败", false);
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                });
        },
        showManageAccountRoleModal: function (account) {
            manageAccountRoleModal.visible(account);
        },
        showManageAccountPasswordModal: function (account) {
            manageAccountPasswordModal.visible(account);
        },
        showManageAccountStatusModal: function (account) {
            manageAccountStatusModal.visible(account);
        },
    },
    mounted: function () {
        let user = JSON.parse(localStorage.getItem("user"));
        this.setUser(user);
        axios.get(requestContext + "api/accounts/query?pageNum=1")
            .then(function (response) {
                let statusCode = response.data.statusCode;
                if (200 === statusCode) {
                    main.setPageInfo(response.data.data);
                } else {
                    popover.append("数据获取失败", false);
                }
                progress.dismiss();
            })
            .catch(function () {
                popover.append("服务器访问失败", false);
                progress.dismiss();
            });
    }
});

const manageAccountRoleModal = new Vue({
    el: "#manageAccountRoleModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "保存",
        account: {}
    },
    methods: {
        visible: function (account) {
            this.isVisible = true;
            this.account = account;
        },
        invisible: function () {
            this.isVisible = false;
        },
        saveAccountRole: function () {

        }
    }
});

const manageAccountPasswordModal = new Vue({
    el: "#manageAccountPasswordModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "保存",
        account: {}
    },
    methods: {
        visible: function (account) {
            this.isVisible = true;
            this.account = account;
        },
        invisible: function () {
            this.isVisible = false;
        },
        saveAccountPassword: function () {

        }
    }
});

const manageAccountStatusModal = new Vue({
    el: "#manageAccountStatusModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "确定",
        account: {}
    },
    methods: {
        visible: function (account) {
            this.isVisible = true;
            this.account = account;
        },
        invisible: function () {
            this.isVisible = false;
        },
        saveAccountStatus: function () {

        }
    }
});