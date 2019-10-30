const main = new Vue({
    el: "#main",
    data: {
        exportNamesAction: "导出对照表",
        isExportNamesBtnDisabled: false,
        exportNoChineseAction: "导出物料信息",
        isExportNoChineseBtnDisabled: false,
        user: {},
        pageInfo: {
            pageNum: 0,
            pages: 0
        },
        pageNum: "",
        materialList: []
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        setPageInfo: function (pageInfo) {
            this.pageInfo = pageInfo;
            this.materialList = pageInfo.data;
        },
        setMaterialList: function (materialList) {
            this.materialList = materialList;
        },
        getMaterialList: function () {
            return this.materialList;
        },
        showImportMaterialNamesModal: function () {
            importMaterialNamesModal.visible();
        },
        showImportNoChineseMaterialsModal: function () {
            importNoChineseMaterialsModal.visible();
        },
        queryPreviousPage: function () {
            if (this.pageInfo.pageNum > 1) {
                const pageNum = this.pageInfo.pageNum - 1;
                this.queryNoChineseMaterials(pageNum);
            }
        },
        queryNextPage: function () {
            if (this.pageInfo.pageNum < this.pageInfo.pages) {
                const pageNum = this.pageInfo.pageNum + 1;
                this.queryNoChineseMaterials(pageNum);
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
            this.queryNoChineseMaterials(this.pageNum);
        },
        perfectChineseName: function (material) {
            perfectChineseNameModal.visible(material);
        },
        queryNoChineseMaterials: function (pageNum) {
            axios.get(requestContext + "api/materials/queryNoChinese?pageNum=" + pageNum)
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
        exportMaterialNames: function () {
            this.isExportNamesBtnDisabled = true;
            this.exportNamesAction = "正在导出";
            axios({
                method: "post",
                url: requestContext + "api/materialNames/export",
                responseType: "blob"
            }).then(function (response) {
                download(response);
                main.exportNamesCallback();
            }).catch(function (error) {
                popover.append("服务器访问失败", false);
                main.exportNamesCallback();
            });
        },
        exportNamesCallback: function () {
            this.exportNamesAction = "导出对照表";
            this.isExportNamesBtnDisabled = false;
        },
        exportNoChineseMaterials: function () {
            this.isExportNoChineseBtnDisabled = true;
            this.exportNoChineseAction = "正在导出";
            axios({
                method: "post",
                url: requestContext + "api/materials/exportNoChinese",
                responseType: "blob"
            }).then(function (response) {
                download(response);
                main.exportNoChineseCallback();
            }).catch(function (error) {
                popover.append("服务器访问失败", false);
                main.exportNoChineseCallback();
            });
        },
        exportNoChineseCallback: function () {
            this.exportNoChineseAction = "导出物料信息";
            this.isExportNoChineseBtnDisabled = false;
        },
    },
    mounted: function () {
        let user = JSON.parse(localStorage.getItem("user"));
        this.setUser(user);
        axios.get(requestContext + "api/materials/queryNoChinese?pageNum=1")
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

const perfectChineseNameModal = new Vue({
    el: "#perfectChineseNameModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "保存",
        material: {}
    },
    methods: {
        visible: function (material) {
            this.material = material;
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        perfectChineseName: function () {
            if (null === this.material.chinese || "" === this.material.chinese) {
                popover.append("请填写物料的中文名称", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在保存";
            axios.put(requestContext + "api/materials/chineseName", this.material)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("保存成功", true);
                        perfectChineseNameModal.invisible();
                    } else {
                        popover.append("保存失败", false);
                    }
                    perfectChineseNameModal.saveCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    perfectChineseNameModal.saveCallback();
                });
        },
        saveCallback: function () {
            this.action = "保存";
            this.isDisabled = false;
        }
    }
});

const importMaterialNamesModal = new Vue({
    el: "#importMaterialNamesModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "导入"
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        importMaterialNames: function () {
            let file = document.getElementById("materialNamesFile").files[0];
            if (!file) {
                popover.append("请选择文件", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在导入";
            let param = new FormData();  // 创建Form对象
            // 通过append向Form对象添加数据
            param.append("file", file, file.name);
            let config = {
                headers: {"Content-Type": "multipart/form-data"}
            };  // 添加请求头
            axios.post(requestContext + "api/materialNames/import", param, config)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("导入成功", true);
                    } else {
                        popover.append("导入失败", false);
                    }
                    importMaterialNamesModal.importCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    importMaterialNamesModal.importCallback();
                });
        },
        importCallback: function () {
            this.action = "导入";
            this.isDisabled = false;
        }
    }
});

const importNoChineseMaterialsModal = new Vue({
    el: "#importNoChineseMaterialsModal",
    data: {
        isVisible: false,
        isDisabled: false,
        action: "导入"
    },
    methods: {
        visible: function () {
            this.isVisible = true;
        },
        invisible: function () {
            this.isVisible = false;
        },
        importNoChineseMaterials: function () {
            let file = document.getElementById("noChineseMaterialsFile").files[0];
            if (!file) {
                popover.append("请选择文件", false);
                return;
            }
            this.isDisabled = true;
            this.action = "正在导入";
            let param = new FormData();  // 创建Form对象
            // 通过append向Form对象添加数据
            param.append("file", file, file.name);
            let config = {
                headers: {"Content-Type": "multipart/form-data"}
            };  // 添加请求头
            axios.post(requestContext + "api/materials/importPerfectChinese", param, config)
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        popover.append("导入成功", true);
                        importNoChineseMaterialsModal.invisible();
                    } else {
                        popover.append("导入失败", false);
                    }
                    importNoChineseMaterialsModal.importCallback();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    importNoChineseMaterialsModal.importCallback();
                });
        },
        importCallback: function () {
            this.action = "导入";
            this.isDisabled = false;
        }
    }
});