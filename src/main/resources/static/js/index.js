const main = new Vue({
    el: "#main",
    data: {
        user: {},
        machineList: [],
        pageContext: {
            pageIndex: 0,
            pageSize: 0,
            pageTotal: 0,
            dataTotal: 0,
            data: []
        },
        name: "",
        patent: "全部",
        filterStatus: 0  // 0是无过滤，1是patent过滤，2是name过滤
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
        editStructureFeatureModalVisible: function () {
            editStructureFeatureModal.visible();
        },
        matchStructureModalVisible: function () {
            matchStructureModal.visible();
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
        queryMachineList: function (pageIndex, pageSize) {
            this.filterStatus = 0;
            this.patent = "全部";
            this.name = "";
            axios.get(requestContext + "api/machines?pageIndex=" + pageIndex + "&pageSize=" + pageSize)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.setMachineList(response.data.data.data);
                        main.pageContext = response.data.data;
                        progress.dismiss();
                    }
                }).catch(function () {
                popover.append("服务器访问失败", false);
                progress.dismiss();
            });
        },
        queryMachineListByPatent: function (patent, pageIndex, pageSize) {
            this.filterStatus = 1;
            this.name = "";
            axios.get(requestContext + "api/machines/patent/" + patent + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.setMachineList(response.data.data.data);
                        main.pageContext = response.data.data;
                        progress.dismiss();
                    }
                }).catch(function () {
                popover.append("服务器访问失败", false);
                progress.dismiss();
            });
        },
        queryMachineListByNameLike: function (name, pageIndex, pageSize) {
            this.filterStatus = 2;
            this.patent = "全部";
            axios.get(requestContext + "api/machines/nameLike/" + name + "?pageIndex=" + pageIndex + "&pageSize=" + pageSize)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.setMachineList(response.data.data.data);
                        main.pageContext = response.data.data;
                        progress.dismiss();
                    }
                }).catch(function () {
                popover.append("服务器访问失败", false);
                progress.dismiss();
            });
        },
        executeQueryMachineListByPatent: function () {
            this.filterStatus = 1;
            this.name = "";
            this.pageContext.pageIndex = 1;
            this.queryMachineListByPatent(this.patent, this.pageContext.pageIndex, this.pageContext.pageSize);
        },
        executeQueryMachineListByNameLike: function () {
            this.filterStatus = 2;
            this.patent = "全部";
            this.pageContext.pageIndex = 1;
            this.queryMachineListByNameLike(this.name, this.pageContext.pageIndex, this.pageContext.pageSize);
        },
        queryMachineListByPreviousPagination: function () {
            if (0 === this.filterStatus) {
                this.queryMachineList(this.pageContext.pageIndex - 1, this.pageContext.pageSize);
            } else if (1 === this.filterStatus) {
                this.queryMachineListByPatent(this.patent, this.pageContext.pageIndex - 1, this.pageContext.pageSize);
            } else if (2 === this.filterStatus) {
                this.queryMachineListByNameLike(this.name, this.pageContext.pageIndex - 1, this.pageContext.pageSize);
            }
        },
        queryMachineListByNextPagination: function () {
            if (0 === this.filterStatus) {
                this.queryMachineList(this.pageContext.pageIndex + 1, this.pageContext.pageSize);
            } else if (1 === this.filterStatus) {
                this.queryMachineListByPatent(this.patent, this.pageContext.pageIndex + 1, this.pageContext.pageSize);
            } else if (2 === this.filterStatus) {
                this.queryMachineListByNameLike(this.name, this.pageContext.pageIndex + 1, this.pageContext.pageSize);
            }
        }
    },
    mounted: function () {
        let user = JSON.parse(localStorage.getItem("user"));
        this.setUser(user);
        this.queryMachineList(1, 10);
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
                        createAndDownload(name + "导入结果.txt", content, null, true);
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
        importStructureFile: function () {
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

const editStructureFeatureModal = new Vue({
    el: "#editStructureFeatureModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "保存",
        structureStr: "",
        materialNo: "",
        version: "",
        structureList: [],
        versionList: [],
        structureFeature: {}
        //     efficiency: "",
        //     rotateRate: "",
        //     debugMode: "",
        //     cylinderAmount: "",
        //     superchargerType: "",
        //     iceAreaEnhance: "",
        //     superchargerArrange: "",
        //     exhaustBackPressure: "",
        //     hostRotateDirection: "",
        //     propellerType: "",
        //     hostElectric: "",
        //     fireExtMedium: "",
        //     topSupportMode: "",
        //     freeEndSecCompensator: "",
        //     outEndSecCompensator: "",
        //     stemMaterial: "",
        //     fivaValveManufacturer: "",
        //     electricStartPumpManufacturer: "",
        //     hydraulicPumpManufacturer: "",
        //     cylinderFuelInjectorManufacturer: "",
        //     egb: "",
        //     torsionalShockAbsorber: "",
        //     scavengerFireExtMethod: "",
        //     hydraulicOilFilterManufacturer: "",
        //     remoteControlManufacturer: "",
        //     pmiSensorManufacturer: "",
        //     oilMistDetectorManufacturer: "",
        //     pto: "",
        //     liftMethod: "",
        //     scr: "",
        //     exhaustValveGrinder: "",
        //     exhaustValveWorkbench: ""
        // }
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
            this.structureStr = "";
            this.materialNo = "";
            this.version = "";
            this.structureList = [];
            this.versionList = [];
            this.structureFeature = {};
        },
        setStructureList: function (structureList) {
            this.structureList = structureList;
        },
        setStructureFeature: function (structureFeature) {
            this.structureFeature = structureFeature;
        },
        selectStructure: function () {
            let latestVersion;
            for (let index = 0; index < this.structureList.length; index++) {
                if (this.structureList[index].materialNo === this.materialNo) {
                    latestVersion = this.structureList[index].latestVersion;
                    break;
                }
            }
            this.versionList = [];
            for (let version = 0; version <= latestVersion; version++) {
                this.versionList.push(version);
            }
        },
        queryStructure: function () {
            this.materialNo = "";
            this.version = "";
            this.structureList = [];
            axios.get(requestContext + "api/materials/query?materialNo=" + this.structureStr)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        editStructureFeatureModal.setStructureList(response.data.data);
                    } else {
                        popover.append("数据获取失败", false);
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                });
        },
        queryStructureFeature: function () {
            if ("" === this.materialNo) {
                popover.append("请选择部套的物料号", false);
                return;
            }
            if ("" === this.version) {
                popover.append("请选择部套版本", false);
                return;
            }
            axios.get(requestContext + "api/structureFeatures/materialNo?materialNo=" + this.materialNo
                + "&version=" + this.version)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        editStructureFeatureModal.setStructureFeature(response.data.data);
                    } else {
                        popover.append("数据获取失败", false);
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                });
        },
        saveStructureFeature: function () {
            if (null === this.structureFeature.id) {
                axios.post(requestContext + "api/structureFeatures", this.structureFeature)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            popover.append("保存成功", true);
                        } else {
                            popover.append("保存失败", false);
                        }
                    })
                    .catch(function () {
                        popover.append("服务器访问失败", false);
                    });
            } else {
                axios.put(requestContext + "api/structureFeatures", this.structureFeature)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            popover.append("保存成功", true);
                        } else {
                            popover.append("保存失败", false);
                        }
                    })
                    .catch(function () {
                        popover.append("服务器访问失败", false);
                    });
            }
        }
    }
});

const matchStructureModal = new Vue({
    el: "#matchStructureModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "保存",
        structureFeature: {},
        structureFeatureList: []
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        setStructureFeatureList: function (structureFeatureList) {
            this.structureFeatureList = structureFeatureList;
        },
        queryStructure: function () {
            this.structureFeatureList = [];
            axios.post(requestContext + "api/structureFeatures/property", this.structureFeature)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        matchStructureModal.setStructureFeatureList(response.data.data);
                    } else {
                        popover.append("查询失败", false);
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                })
        },
        resetStructureFeature: function () {
            this.structureFeature = {};
            this.structureFeatureList = [];
        },
        exportMatchedStructures: function () {
            axios({
                method: "post",
                url: requestContext + "api/structureFeatures/export",
                data: this.structureFeature,
                responseType: "blob"
            }).then(function (response) {
                download(response);
                exportMachineBOMModal.exportCallback();
                exportMachineBOMModal.invisible();
            }).catch(function () {
                popover.append("服务器访问失败", false);
                exportMachineBOMModal.exportCallback();
            });
        }
    }

});

var summerNav = new Vue({
    el: '#summer-nav',
    data: {
        masterItem: 0
    }
})
