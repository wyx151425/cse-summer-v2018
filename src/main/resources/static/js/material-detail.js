const main = new Vue({
    el: "#main",
    data: {
        isVisible: false,
        machine: {
            name: ""
        },
        material: null
    },
    methods: {
        show: function () {
            this.isVisible = true;
        },
        dismiss: function () {
            this.isVisible = false;
        },
        setMachineName: function (machineName) {
            this.machine.name = machineName;
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
        },
        showMaterialDetail: function (material) {
            this.material = material;
        }
    },
    mounted: function () {
        let url = window.location;
        let machineName = getUrlParam(url, "machineName");
        this.setMachineName(machineName);
    }
});

Vue.component("material-node", {
    name: "material-node",
    props: ["material"],
    template: `
            <li class="item">
                <span class="icon icon-triangle-right"></span>
                <span class="code" @click="queryMaterial(material)">{{material.materialNo}}&nbsp;&nbsp;[{{material.chinese}}]</span>
                <span class="icon icon-cog" @click="showMaterialDetail(material)"></span>
                <ul class="list" v-if="material.materialList && material.materialList.length > 0">
                    <material-node v-for="childMaterial in material.materialList" :key="childMaterial.objectId" :material="childMaterial"></material-node>
                </ul>
            </li>
        `,
    methods: {
        queryMaterial: function (parent) {
            main.show();
            let materialNode = this;
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
        },
        showMaterialDetail: function (material) {
            main.showMaterialDetail(material);
        }
    }
});