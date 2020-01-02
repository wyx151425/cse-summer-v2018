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
        showAddAccountModal: function () {
            addAccountModal.visible();
        }
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
        action: "修改",
        account: {},
        newRole: ''
    },
    methods: {
        visible: function (account) {
            this.isVisible = true;
            this.account = account;
            this.newRole = this.account.roles;
        },
        invisible: function () {
            this.isVisible = false;
        },
        saveAccountRole: function () {
            this.isDisabled = true;
            this.action = "正在修改";
            let target = {username: this.account.username, roles: this.newRole};
            axios.put(requestContext + "api/accounts/role", target)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("修改成功", true);
                        manageAccountRoleModal.account.roles = target.roles;
                        manageAccountRoleModal.invisible();
                    } else {
                        popover.append("修改失败", false);
                    }
                    manageAccountRoleModal.isDisabled = false;
                    manageAccountRoleModal.action = "修改";
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    manageAccountRoleModal.isDisabled = false;
                    manageAccountRoleModal.action = "修改";
                });
        }
    }
});

const manageAccountPasswordModal = new Vue({
    el: "#manageAccountPasswordModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "修改",
        account: {},
        newPassword: '',
        confirmPassword: ''
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
            if ("" === this.newPassword) {
                popover.append("请填写新密码", false);
                return;
            }
            if (this.newPassword.length < 6 || this.newPassword.length > 32) {
                popover.append("请填写正确格式的新密码", false);
                return;
            }
            if ("" === this.confirmPassword) {
                popover.append("请确认新密码", false);
                return;
            }
            if (this.confirmPassword !== this.newPassword) {
                popover.append("两次填写的密码不匹配", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在修改";
            let target = {username: this.account.username, password: this.newPassword};
            axios.put(requestContext + "api/accounts/password", target)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("修改成功", true);
                        manageAccountPasswordModal.account.password = target.password;
                        manageAccountPasswordModal.invisible();
                    } else {
                        popover.append("修改失败", false);
                    }
                    manageAccountPasswordModal.isDisabled = false;
                    manageAccountPasswordModal.action = "修改";
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    manageAccountPasswordModal.isDisabled = false;
                    manageAccountPasswordModal.action = "修改";
                });
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
            this.isDisabled = true;
            this.action = "正在修改";
            let newStatus;
            if (1 === this.account.status) {
                newStatus = 0;
            } else if (0 === this.account.status) {
                newStatus = 1;
            } else {
                return;
            }
            let target = {username: this.account.username, status: newStatus};
            axios.put(requestContext + "api/accounts/status", target)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("修改成功", true);
                        manageAccountStatusModal.account.status = target.status;
                        manageAccountStatusModal.invisible();
                    } else {
                        popover.append("修改失败", false);
                    }
                    manageAccountStatusModal.isDisabled = false;
                    manageAccountStatusModal.action = "确定";
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    manageAccountStatusModal.isDisabled = false;
                    manageAccountStatusModal.action = "确定";
                });
        }
    }
});

const addAccountModal = new Vue({
    el: "#addAccountModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "添加",
        account: {}
    },
    methods: {
        visible: function () {
            this.isVisible = true;
            this.account = {name: "", username: "", password: "", roles: "ROLE_STRUCTURE_MANAGER"};
        },
        invisible: function () {
            this.isVisible = false;
        },
        addAccount: function () {
            if ("" === this.account.name) {
                popover.append("请填写账号的姓名", false);
                return;
            }
            if ("" === this.account.username) {
                popover.append("请填写账号的用户名", false);
                return;
            }
            if ("" === this.account.password) {
                popover.append("请填写账号的密码", false);
                return;
            }
            if (this.account.password.length < 6 || this.account.password.length > 32) {
                popover.append("请填写正确格式的的密码", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在保存";
            axios.post(requestContext + "api/accounts", this.account)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("添加成功", true);
                        addAccountModal.invisible();
                    } else {
                        popover.append(getMessage(statusCode), false);
                    }
                    addAccountModal.isDisabled = false;
                    addAccountModal.action = "添加";
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    addAccountModal.isDisabled = false;
                    addAccountModal.action = "添加";
                });
        }
    }
});