const main = new Vue({
    el: "#main",
    data: {
        isVisible: false,
        machine: {
            name: ""
        },
        material: null,
        user: {}
    },
    methods: {
        setUser: function (user) {
            this.user = user;
        },
        show: function () {
            this.isVisible = true;
        },
        dismiss: function () {
            this.isVisible = false;
        },
        setMachineName: function (machineName) {
            this.machine.name = machineName;
        },
        setMachine: function (machine) {
            this.machine = machine;
        },
        setStructureList: function (structureList) {
            this.machine.structureList = structureList;
        },
        queryStructure: function (machineName) {
            this.show();
            axios.get(requestContext + "api/machines/" + machineName + "/structures")
                .then(function (response) {
                    let statusCode = response.data.statusCode;
                    if (200 === statusCode) {
                        main.setStructureList(response.data.data);
                        main.$forceUpdate();
                    } else {
                        popover.append("数据获取失败", false);
                    }
                    main.dismiss();
                })
                .catch(function () {
                    popover.append("服务器访问失败", false);
                    main.dismiss();
                });
        },
        queryMaterial: function (parent) {
            if (undefined !== parent.materialList && parent.materialList.length > 0) {
                parent.materialList = [];
                main.$forceUpdate();
            } else {
                if (0 !== parent.childCount) {
                    this.show();
                    axios.get(requestContext + "api/materials?parentId=" + parent.objectId)
                        .then(function (response) {
                            let statusCode = response.data.statusCode;
                            if (200 === statusCode) {
                                parent.materialList = response.data.data;
                                main.$forceUpdate();
                            } else {
                                popover.append("数据获取失败", false);
                            }
                            main.dismiss();
                        })
                        .catch(function () {
                            popover.append("服务器访问失败", false);
                            main.dismiss();
                        });
                }
            }
            this.showMaterialDetail(parent);
        },
        showMaterialDetail: function (material) {
            this.material = material;
        }
    },
    mounted: function () {
        this.show();
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
                    main.$forceUpdate();
                } else {
                    popover.append("数据获取失败", false);
                }
                main.dismiss();
            })
            .catch(function () {
                popover.append("服务器访问失败", false);
                main.dismiss();
            });
    }
});

Vue.component("material-node", {
    name: "material-node",
    props: ["material"],
    template: `
            <li class="item">
                <span class="icon icon-triangle-right" v-if="0 != material.childCount
                            && (undefined == material.materialList || material.materialList.length == 0)"></span>
                <span class="icon icon-triangle-bottom" v-if="0 != material.childCount
                            && undefined != material.materialList && material.materialList.length > 0"></span>
                <span class="icon" v-if="0 == material.childCount" style="margin-right: 12px"></span>
                <span class="code" @click="queryMaterial(material)">{{material.materialNo}}&nbsp;&nbsp;{{material.chinese}}</span>
                <ul class="list" v-if="material.materialList && material.materialList.length > 0">
                    <material-node v-for="childMaterial in material.materialList" :key="childMaterial.objectId" :material="childMaterial"></material-node>
                </ul>
            </li>
        `,
    methods: {
        queryMaterial: function (parent) {
            let materialNode = this;
            if (undefined !== parent.materialList && parent.materialList.length > 0) {
                parent.materialList = [];
                materialNode.$forceUpdate();
            } else {
                if (0 !== parent.childCount) {
                    main.show();
                    axios.get(requestContext + "api/materials?parentId=" + parent.objectId)
                        .then(function (response) {
                            let statusCode = response.data.statusCode;
                            if (200 === statusCode) {
                                parent.materialList = response.data.data;
                                materialNode.$forceUpdate();
                            } else {
                                popover.append("数据获取失败", false);
                            }
                            main.dismiss();
                        })
                        .catch(function () {
                            popover.append("服务器访问失败", false);
                            main.dismiss();
                        });
                }
            }
            main.showMaterialDetail(parent);
        }
    }
});