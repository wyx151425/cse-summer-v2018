const main = new Vue({
    el: "#main",
    data: {
        resultList: []
    },
    methods: {
        setResultList: function (resultList) {
            this.resultList = resultList;
        }
    },
    mounted: function () {
        let url = window.location;
        let machineName = getUrlParam(url, "machineName");
        axios.get(requestContext + "api/machines/" + machineName + "/results")
            .then(function (response) {
                let statusCode = response.data.statusCode;
                if (200 === statusCode) {
                    main.setResultList(response.data.data);
                    popover.append("服务器访问成功", true);
                    progress.dismiss();
                }
            })
            .catch(function () {
                popover.append("服务器访问失败", false);
            });
    }
});