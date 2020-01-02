const main = new Vue({
    el: "#main",
    data: {
        user: {},
        machine: {
            name: ""
        },
        structureList: []
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        setMachineName: function (machineName) {
            this.machine.name = machineName;
        },
        getMachineName: function () {
            return this.machine.name;
        },
        setMachine: function (machine) {
            this.machine = machine;
        },
        getMachineType: function () {
            return this.machine.type;
        },
        setStructureList: function (structureList) {
            this.structureList = structureList;
        },
        getStructureList: function () {
            return this.structureList;
        },
        importStructureModalVisible: function () {
            importStructureModal.visible();
        },
        exportStructureModalVisible: function (structure) {
            exportStructureModal.visible(structure);
        },
        appendStructureModalVisible: function () {
            appendStructureModal.visible();
        },
        releaseStructureModalVisible: function (structure, index) {
            releaseStructureModal.visible(structure, index);
        },
        deleteStructureModalVisible: function (structure, index) {
            deleteStructureModal.visible(structure, index);
        },
        updateStructureVersionModalVisible: function (structure, index) {
            updateStructureVersionModal.visible(structure, index);
        },
        showUpdateStructureAmountModal: function (structure, index) {
            updateStructureAmountModal.visible(structure, index);
        },
        releaseStructure: function (index) {
            this.structureList[index].status = 2;
        },
        deleteStructure: function (index) {
            // this.structureList.splice(index, 1);
            this.structureList[index].status = 1;
        },
        updateStructureVersion: function (index, version) {
            this.structureList[index].status = 1;
            this.structureList[index].version = version;
        },
        updateStructureAmount: function (index, amount) {
            this.structureList[index].amount = amount;
        },
        editStructureFeatureModalVisible: function (material) {
            editStructureFeatureModal.visible(material);
        }
    },
    mounted: function () {
        let user = JSON.parse(localStorage.getItem("user"));
        this.setUser(user);
        let url = window.location;
        let machineName = getUrlParam(url, "machineName");
        this.setMachineName(machineName);
        axios.get(requestContext + "api/machines/" + machineName)
            .then(function (response) {
                let statusCode = response.data.statusCode;
                if (200 === statusCode) {
                    main.setMachine(response.data.data);
                    main.setStructureList(response.data.data.structureList);
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

const importStructureModal = new Vue({
    el: "#importStructureModal",
    data: {
        isVisible: false,
        isNewStructureFileChosen: false,
        isImportNewStructureDisabled: false,
        isImportNewVersionStructureDisabled: false,
        importNewStructureAction: "导入",
        importNewVersionStructureAction: "导入",

        tabIndex: 1,

        machineName: "",
        newStructureName: "",
        newStructureAmount: "",
        newStructureList: [],
        importResult: [],

        structure: {},
        structureList: []
    },
    methods: {
        visible: function () {
            this.machineName = main.getMachineName();
            this.structure.machineName = main.getMachineName();
            this.structure.structureNo = "请选择";
            this.structureList = main.getStructureList();
            this.isVisible = true;
        },
        invisible: function () {
            this.isNewStructureFileChosen = false;
            this.isVisible = false;
        },
        pushResult: function (result) {
            this.importResult.push(result);
            if (this.importResult.length === this.newStructureList.length) {
                importStructureModal.importNewStructureCallback();
                let content = "";
                for (let index = 0; index < this.importResult.length; index++) {
                    content += this.importResult[index].structureNo;
                    content += " ";
                    content += this.importResult[index].result;
                    content += "\r\n";
                }
                createAndDownload("部套导入结果.txt", content, null, true);
            }
            importStructureModal.invisible();
        },
        changeTab: function (index) {
            this.tabIndex = index;
        },
        analyzeFile: function () {
            let file = document.getElementById("newStructureFile").files[0];
            if (!file) {
                this.isNewStructureFileChosen = false;
            } else {
                this.isNewStructureFileChosen = true;
            }
        },
        confirmFile: function () {
            if ("" === this.newStructureName) {
                popover.append("请填写部套号");
                return;
            }
            if ("" === this.newStructureAmount) {
                popover.append("请填写总数量");
                return;
            }

            let fileElement = document.getElementById("newStructureFile");
            let structure = {};
            structure.structureNo = this.newStructureName;
            structure.amount = this.newStructureAmount;
            structure.file = fileElement.files[0];

            this.newStructureList.push(structure);

            this.newStructureName = "";
            this.newStructureAmount = "";
            fileElement.value = "";
            this.isNewStructureFileChosen = false;
        },
        importNewStructure: function () {
            if (0 === this.newStructureList.length && !this.isNewStructureFileChosen) {
                popover.append("请选择部套文件", false);
                return;
            }
            if (this.isNewStructureFileChosen) {
                popover.append("请确认部套号和总数量", false);
                return;
            }
            this.isImportNewStructureDisabled = true;
            this.importNewStructureAction = "正在导入";
            this.importResult = [];
            for (let index = 0; index < this.newStructureList.length; index++) {
                let file = this.newStructureList[index];
                let param = new FormData();  // 创建Form对象
                // 通过append向Form对象添加数据
                param.append("machineName", this.machineName);
                param.append("structureNo", file.structureNo);
                param.append("amount", file.amount);
                param.append("file", file.file, file.file.name);
                let config = {
                    headers: {"Content-Type": "multipart/form-data"}
                };  // 添加请求头
                axios.post(requestContext + "api/structures/import", param, config)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            importStructureModal.pushResult({structureNo: file.structureNo, result: "导入成功"});
                        } else {
                            let message = getMessage(statusCode);
                            importStructureModal.pushResult({structureNo: file.structureNo, result: message});
                        }
                    })
                    .catch(function () {
                        popover.append("服务器访问失败", false);
                        importStructureModal.importNewStructureCallback();
                    });
            }
        },
        importNewStructureCallback: function () {
            this.newStructureList = [];
            this.importNewStructureAction = "导入";
            this.isImportNewStructureDisabled = false;
        },
        importNewVersionStructure: function () {
            if ("请选择" === this.structure.structureNo) {
                popover.append("请选择部套号", false);
                return;
            }
            let file = document.getElementById("newVersionStructureFile").files[0];
            if (!file) {
                popover.append("请选择部套BOM文件", false);
                return;
            }
            this.isImportNewVersionStructureDisabled = true;
            this.importNewVersionStructureAction = "正在导入";
            let param = new FormData();  // 创建Form对象
            // 通过append向Form对象添加数据
            param.append("structureNo", this.structure.structureNo);
            param.append("file", file, file.name);
            let config = {
                headers: {"Content-Type": "multipart/form-data"}
            };  // 添加请求头
            axios.post(requestContext + "api/structures/import/version", param, config)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("导入成功", true);
                        window.location.reload();
                    } else {
                        let message = getMessage(statusCode);
                        popover.append(message, false);
                    }
                    importStructureModal.importNewVersionStructureCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    importStructureModal.importNewVersionStructureCallback();
                });
        },
        importNewVersionStructureCallback: function () {
            this.importNewVersionStructureAction = "导入";
            this.isImportNewVersionStructureDisabled = false;
        }
    }
});

const exportStructureModal = new Vue({
    el: "#exportStructureModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "导出",
        note: "",
        structure: {},
        versionList: []
    },
    methods: {
        visible: function (structure) {
            this.setStructure(structure);
            let latestVersion = structure.material.latestVersion;
            this.versionList = [];
            for (let version = latestVersion; version >= 0; version--) {
                this.versionList.push(version);
            }
            this.isVisible = true;
            this.queryStructureNote();
        },
        invisible: function () {
            this.isVisible = false;
        },
        setStructure: function (structure) {
            this.structure.machineName = structure.machineName;
            this.structure.structureNo = structure.structureNo;
            this.structure.materialNo = structure.materialNo;
            this.structure.version = structure.material.latestVersion;
        },
        exportStructureFile: function () {
            this.isDisabled = true;
            this.action = "正在导出";
            axios({
                method: "post",
                url: requestContext + "api/structures/export",
                data: this.structure,
                responseType: "blob"
            }).then(function (response) {
                download(response);
                exportStructureModal.exportCallback();
            }).catch(function (error) {
                popover.append("服务器访问失败", false);
                exportStructureModal.exportCallback();
            });
        },
        exportCallback: function () {
            this.action = "导出";
            this.isDisabled = false;
        },
        queryStructureNote: function () {
            let url = requestContext + "api/structureNotes?materialNo=" + this.structure.materialNo + "&version=" + this.structure.version;
            axios.get(url).then(function (response) {
                if (200 === response.data.statusCode && null != response.data.data) {
                    exportStructureModal.note = response.data.data.note;
                } else {
                    exportStructureModal.note = "";
                }
            });
        }
    }
});

const appendStructureModal = new Vue({
    el: "#appendStructureModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "添加",
        materialNoDef: "请选择",
        versionDef: "请选择",
        structure: {
            machineName: "",
            materialNo: "请选择",
            version: "请选择",
            structureNo: "",
            amount: "",
        },
        structureStr: "",
        structureList: [],
        versionList: []
    },
    methods: {
        visible: function () {
            this.structure.machineName = main.getMachineName();
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        initStructure: function () {
            this.materialNoDef = "请选择";
            this.versionDef = "请选择";
            this.structure.materialNo = "请选择";
            this.structure.version = "请选择";
            this.structure.structureNo = "";
            this.structure.amount = "";
            this.versionList = [];
        },
        setStructureList: function (structureList) {
            this.structureList = structureList;
        },
        selectStructure: function () {
            let latestVersion;
            for (let index = 0; index < this.structureList.length; index++) {
                if (this.structureList[index].materialNo === this.structure.materialNo) {
                    latestVersion = this.structureList[index].latestVersion;
                    break;
                }
            }
            this.structure.version = "请选择";
            this.versionList = [];
            for (let version = 0; version <= latestVersion; version++) {
                this.versionList.push(version);
            }
        },
        queryStructure: function () {
            this.materialNoDef = "正在查询";
            this.versionDef = "正在查询";
            this.structure.materialNo = "正在查询";
            this.structure.version = "正在查询";
            this.structureList = [];
            axios.get(requestContext + "api/materials/query?materialNo=" + this.structureStr)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        appendStructureModal.setStructureList(response.data.data);
                        appendStructureModal.initStructure();
                    } else {
                        popover.append("数据获取失败", false);
                    }
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                });
        },
        appendStructure: function () {
            if ("请选择" === this.structure.materialNo) {
                popover.append("请选择物料号", false);
                return;
            }
            if ("请选择" === this.structure.version) {
                popover.append("请选择版本号", false);
                return;
            }
            if ("" === this.structure.structureNo) {
                popover.append("请填写部套号", false);
                return;
            }
            if ("" === this.structure.amount) {
                popover.append("请选择总数量", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在添加";
            axios.post(requestContext + "api/structures/append", this.structure)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("添加成功", true);
                        location.reload();
                    } else {
                        let message = getMessage(statusCode);
                        popover.append(message, false);
                    }
                    appendStructureModal.appendCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    appendStructureModal.appendCallback();
                });
        },
        appendCallback: function () {
            this.action = "添加";
            this.isDisabled = false;
        }
    }
});

const releaseStructureModal = new Vue({
    el: "#releaseStructureModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "发布",
        index: 0,
        structure: {}
    },
    methods: {
        visible: function (structure, index) {
            this.index = index;
            this.structure = structure;
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        releaseStructure: function () {
            this.isDisabled = true;
            this.action = "正在发布";
            axios.put(requestContext + "api/structures/" + this.structure.id + "/release")
                .then(function (response) {
                    if (200 === response.data.statusCode) {
                        main.releaseStructure(releaseStructureModal.index);
                        releaseStructureModal.invisible();
                        popover.append("发布成功", true);
                    } else {
                        popover.append("发布失败", false);
                    }
                    releaseStructureModal.releaseCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    releaseStructureModal.releaseCallback();
                });
        },
        releaseCallback: function () {
            this.action = "发布";
            this.isDisabled = false;
        }
    }
});

const deleteStructureModal = new Vue({
    el: "#deleteStructureModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "删除",
        index: 0,
        structure: {}
    },
    methods: {
        visible: function (structure, index) {
            this.index = index;
            this.structure = structure;
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        deleteStructure: function () {
            this.isDisabled = true;
            this.action = "正在删除";
            axios.delete(requestContext + "api/structures/" + this.structure.id)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.deleteStructure(deleteStructureModal.index);
                        deleteStructureModal.invisible();
                        popover.append("删除成功", true);
                    } else {
                        popover.append("删除失败", false);
                    }
                    deleteStructureModal.deleteCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    deleteStructureModal.deleteCallback();
                });
        },
        deleteCallback: function () {
            this.action = "删除";
            this.isDisabled = false;
        }
    }
});

const updateStructureVersionModal = new Vue({
    el: "#updateStructureVersionModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "修改",
        note: "",
        index: 0,
        version: 0,
        structure: {},
        versionList: []
    },
    methods: {
        visible: function (structure, index) {
            this.index = index;
            this.setStructure(structure);
            let latestVersion = structure.material.latestVersion;
            this.versionList = [];
            for (let version = 0; version <= latestVersion; version++) {
                this.versionList.push(version);
            }
            this.isVisible = true;
            this.queryStructureNote();
        },
        invisible: function () {
            this.isVisible = false;
        },
        setStructure: function (structure) {
            this.structure.machineName = structure.machineName;
            this.structure.structureNo = structure.structureNo;
            this.structure.materialNo = structure.materialNo;
            this.structure.version = structure.version;
            this.version = structure.version;
        },
        updateStructureVersion: function () {
            if (this.version === this.structure.version) {
                popover.append("修改成功", true);
                this.invisible();
            } else {
                this.isDisabled = true;
                this.action = "正在修改";
                axios.put(requestContext + "api/structures/version", this.structure)
                    .then(function (response) {
                        let statusCode = response.data.statusCode;
                        if (200 === statusCode) {
                            popover.append("修改成功", true);
                            main.updateStructureVersion(updateStructureVersionModal.index, updateStructureVersionModal.structure.version);
                            updateStructureVersionModal.invisible();
                        } else {
                            popover.append("修改失败", false);
                        }
                        updateStructureVersionModal.updateCallback();
                    })
                    .catch(function () {
                        popover.append("服务器访问失败", false);
                        updateStructureVersionModal.updateCallback();
                    });
            }
        },
        updateCallback: function () {
            this.action = "修改";
            this.isDisabled = false;
        },
        queryStructureNote: function () {
            let url = requestContext + "api/structureNotes?materialNo=" + this.structure.materialNo + "&version=" + this.structure.version;
            axios.get(url).then(function (response) {
                if (200 === response.data.statusCode && null != response.data.data) {
                    updateStructureVersionModal.note = response.data.data.note;
                } else {
                    updateStructureVersionModal.note = "";
                }
            });
        }
    }
});

const updateStructureAmountModal = new Vue({
    el: "#updateStructureAmountModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "修改",
        index: 0,
        structure: {}
    },
    methods: {
        visible: function (structure, index) {
            this.index = index;
            this.setStructure(structure);
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        setStructure: function (structure) {
            this.structure.id = structure.id;
            this.structure.machineName = structure.machineName;
            this.structure.structureNo = structure.structureNo;
            this.structure.materialNo = structure.materialNo;
            this.structure.version = structure.version;
            this.structure.amount = structure.amount;
        },
        updateStructureAmount: function () {
            this.isDisabled = true;
            this.action = "正在修改";
            axios.put(requestContext + "api/structures/amount", this.structure)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("修改成功", true);
                        main.updateStructureAmount(updateStructureAmountModal.index, updateStructureAmountModal.structure.amount);
                        updateStructureAmountModal.invisible();
                    } else {
                        popover.append("修改失败", false);
                    }
                    updateStructureAmountModal.updateCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    updateStructureAmountModal.updateCallback();
                });
        },
        updateCallback: function () {
            this.action = "修改";
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
        material: {},
        structureFeature: {}
    },
    methods: {
        visible: function (material) {
            this.material = material;
            this.isVisible = true;
            axios.get(requestContext + "api/structureFeatures/materialNo?materialNo=" + this.material.materialNo
                + "&version=" + this.material.version)
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
        invisible: function () {
            this.isVisible = false;
            this.structureStr = "";
            this.materialNo = "";
            this.version = "";
            this.structureList = [];
            this.versionList = [];
            this.structureFeature = {};
        },
        setStructureFeature: function (structureFeature) {
            this.structureFeature = structureFeature;
        },
        saveStructureFeature: function () {
            this.structureFeature.machineType = main.getMachineType();
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