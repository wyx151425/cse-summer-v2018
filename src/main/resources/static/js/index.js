const main = new Vue({
    el: "#main",
    data: {
        user: {},
        machineList: []
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        setMachineList: function (machineList) {
            this.machineList = machineList;
        },
        deleteAllMachineModalVisible: function () {
            deleteAllMachineModal.visible();
        },
        importMachineBOMModalVisible: function () {
            importMachineBOMModal.visible(this.user, this.machineList);
        },
        exportMachineBOMModalVisible: function (name) {
            exportMachineBOMModal.visible(name);
        },
        editMachineInfoModalVisible: function (machine) {
            editMachineInfoModal.visible(machine);
        },
        queryRelationModalVisible: function () {
            queryRelationModal.visible();
        },
        verifyStructureListModalVisible: function () {
            verifyStructureListModal.visible();
        },
        checkMachineDetail: function (machine) {
            if (machine.complete) {
                let link = document.createElement("a");
                link.href = "machine?machineName=" + machine.name;
                link.click();
            } else {
                popover.append("请完善机器信息", false);
            }
        },
    },
    mounted: function () {
        let user = JSON.parse(localStorage.getItem("user"));
        this.setUser(user);
        axios.get(requestContext + "api/machines")
            .then(function (response) {
                let statusCode = response.data.statusCode;
                if (200 === statusCode) {
                    main.setMachineList(response.data.data);
                    progress.dismiss();
                }
            }).catch(function () {
            popover.append("服务器访问失败", false);
            progress.dismiss();
        });
    }
});

const importMachineBOMModal = new Vue({
    el: "#importMachineBOMModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "导入",
        name: "",
        patent: "请选择",
        user: {},
        machineList: []
    },
    methods: {
        visible: function (user, machineList) {
            this.user = user;
            this.machineList = machineList;
            if (!this.user.permissions.IMPORT_NEW_MACHINE_BOM) {
                this.name = "请选择";
            }
            this.isVisible = true;
        },
        invisible: function () {
            if (!this.isDisabled) {
                this.name = "";
                this.patent = "请选择";
                this.isVisible = false;
            }
        },
        importBOMFile: function () {
            let name;
            if ("" === this.name || "请选择" === this.name) {
                popover.append("请填写机器名称", false);
                return;
            }
            name = this.name;
            if ("请选择" === this.patent) {
                popover.append("请选择BOM文件格式", false);
                return;
            }
            let file = document.getElementById("file").files[0];
            if (!file) {
                popover.append("请选择BOM文件", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在导入";
            let param = new FormData();  // 创建Form对象
            // 通过append向Form对象添加数据
            param.append("name", this.name);
            param.append("patent", this.patent);
            param.append("file", file, file.name);
            let config = {
                headers: {"Content-Type": "multipart/form-data"}
            };  // 添加请求头
            axios.post(requestContext + "api/machines/import", param, config)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        let content = "";
                        for (let index = 0; index < response.data.data.length; index++) {
                            content += response.data.data[index].structureNo;
                            content += " ";
                            if (response.data.data[index].result) {
                                content += "导入成功";
                            } else {
                                content += "使用库中部套";
                            }
                            content += "\r\n";
                        }
                        createAndDownload(name + "导入结果.txt", content);
                        window.location.reload();
                    } else {
                        let message = getMessage(statusCode);
                        popover.append(message, false);
                    }
                    importMachineBOMModal.importCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    importMachineBOMModal.importCallback();
                });
        },
        importCallback: function () {
            this.action = "导入";
            this.isDisabled = false;
        }
    }
});

const exportMachineBOMModal = new Vue({
    el: "#exportMachineBOMModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "导出",
        name: "",
        type: "请选择"
    },
    methods: {
        visible: function (name) {
            this.name = name;
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        exportBOMFile: function () {
            if ("请选择" === this.type) {
                popover.append("请选择BOM类型", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在导出";
            axios({
                method: "post",
                url: requestContext + "api/machines/export",
                data: {
                    name: this.name,
                    status: this.type
                },
                responseType: "blob"
            }).then(function (response) {
                download(response);
                exportMachineBOMModal.exportCallback();
                exportMachineBOMModal.invisible();
            }).catch(function () {
                popover.append("服务器访问失败", false);
                exportMachineBOMModal.exportCallback();
            });
        },
        exportCallback: function () {
            this.action = "导出";
            this.isDisabled = false;
        }
    }
});

const editMachineInfoModal = new Vue({
    el: "#editMachineInfoModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "保存",
        machine: {
            name: "",
            machineNo: "",
            type: "",
            patent: "请选择",
            cylinderAmount: "",
            shipNo: "",
            classificationSociety: "",
            isComplete: false
        }
    },
    methods: {
        visible: function (machine) {
            this.machine.id = machine.id;
            this.machine.name = machine.name;
            if (null != machine.machineNo) {
                this.machine.machineNo = machine.machineNo;
            }
            if (null != machine.type) {
                this.machine.type = machine.type;
            }
            this.machine.patent = machine.patent;
            this.machine.cylinderAmount = machine.cylinderAmount;
            if (null != machine.shipNo) {
                this.machine.shipNo = machine.shipNo;
            }
            if (null != machine.classificationSociety) {
                this.machine.classificationSociety = machine.classificationSociety;
            }
            this.machine.isComplete = machine.complete;
            this.isVisible = true;
        },
        invisible: function () {
            if (!this.isDisabled) {
                this.isVisible = false;
            }
        },
        saveMachine: function () {
            if ("" === this.machine.machineNo) {
                popover.append("请填写机号", false);
                return;
            }
            if ("" === this.machine.type) {
                popover.append("请填写适用机型", false);
                return;
            }
            if ("请选择" === this.machine.patent) {
                popover.append("请选择专利机型", false);
                return;
            }
            if ("" === this.machine.cylinderAmount) {
                popover.append("请填写缸数", false);
                return;
            }
            if ("" === this.machine.shipNo) {
                popover.append("请填写船号", false);
                return;
            }
            if ("" === this.machine.classificationSociety) {
                popover.append("请填写船级社", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在保存";
            axios.put(requestContext + "api/machines", this.machine)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("修改成功", true);
                        location.reload();
                    } else {
                        popover.append("修改失败", false);
                    }
                    editMachineInfoModal.saveCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    editMachineInfoModal.saveCallback();
                });
        },
        saveCallback: function () {
            this.action = "保存";
            this.isDisabled = false;
        }
    }
});

const queryRelationModal = new Vue({
    el: "#queryRelationModal",
    data: {
        isVisible: false,
        materialNo: "",
        structureList: {}
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.materialNo = "";
            this.structureList = {};
            this.isVisible = false;
        },
        setStructureList: function (structureList) {
            this.structureList = structureList;
        },
        queryRelation: function () {
            axios.get(requestContext + "api/structures/query?materialNo=" + this.materialNo)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        if (0 === response.data.data.length) {
                            popover.append("未查询到关联机器", true);
                        }
                        queryRelationModal.setStructureList(response.data.data);
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                });
        }
    }
});

const verifyStructureListModal = new Vue({
    el: "#verifyStructureListModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "导入",
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        importNewVersionStructureFile: function () {
            let file = document.getElementById("structureFile").files[0];
            if (!file) {
                popover.append("请选择文件", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在检查";
            let param = new FormData();  // 创建Form对象
            // 通过append向Form对象添加数据
            param.append("file", file, file.name);
            let config = {
                headers: {"Content-Type": "multipart/form-data"}
            };  // 添加请求头
            axios.post(requestContext + "api/structures/verify", param, config)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        let content = "";
                        for (let index = 0; index < response.data.data.length; index++) {
                            content += response.data.data[index].structureNo;
                            content += " ";
                            if (response.data.data[index].result) {
                                content += "新部套，库中不存在";
                            } else {
                                content += "库中已存在";
                            }
                            content += "\r\n";
                        }
                        verifyStructureListModal.invisible();
                        createAndDownload("检查结果.txt", content);
                    } else {
                        let message = getMessage(statusCode);
                        popover.append(message, false);
                    }
                    verifyStructureListModal.importCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    verifyStructureListModal.importCallback();
                });
        },
        importCallback: function () {
            this.action = "导入";
            this.isDisabled = false;
        }
    }
});

const deleteAllMachineModal = new Vue({
    el: "#deleteAllMachineModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "确定",
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        deleteAllMachine: function () {
            this.isDisabled = true;
            this.action = "正在删除";
            axios.delete(requestContext + "api/machines")
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        window.location.reload();
                    } else {
                        popover.append("数据删除失败", false);
                        deleteAllMachineModal.deleteCallback();
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    deleteAllMachineModal.deleteCallback();
                });
        },
        deleteCallback: function () {
            this.action = "确定";
            this.isDisabled = false;
        }
    }
});