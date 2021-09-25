Vue.component("summer-nav", {
    props: ['masterItem'],
    template: "" +
        "<aside class=\"nav\">\n" +
        "    <nav class=\"nav-master\">\n" +
        "        <h1 class=\"nav-master-title\"><span class=\"icon icon-copyright-mark\"></span></h1>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 0 === masterItem}\" th:href=\"@{/index}\"><span class=\"icon icon-print\"></span><span>机器</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 1 === masterItem}\" th:href=\"@{/structureList}\"><span class=\"icon icon-hdd\"></span><span>部套</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 2 === masterItem}\" th:href=\"@{/materialList}\"><span class=\"icon icon-list\"></span><span>物料</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 3 === masterItem}\" th:href=\"@{/dictionaryList}\"><span class=\"icon icon-book\"></span><span>词典</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 4 === masterItem}\" th:href=\"@{/taskList}\"><span class=\"icon icon-tasks\"></span><span>任务</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 5 === masterItem}\" th:href=\"@{/messageList}\"><span class=\"icon icon-comment\"></span><span>消息</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 6 === masterItem}\" th:href=\"@{/accountManage}\"><span class=\"icon icon-user\"></span><span>账号</span></a>\n" +
        "        <a class=\"nav-master-item\" v-bind:class=\"{active: 7 === masterItem}\" th:href=\"@{/systemManage}\"><span class=\"icon icon-cog\"></span><span>系统</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"0 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">机器信息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>机器列表</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"1 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">部套信息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>部套列表</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"2 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">物料信息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>物料列表</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"3 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">词典信息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>词典列表</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"4 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">待办任务</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>任务列表</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"5 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">通知消息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>消息列表</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"6 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">账号信息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>个人账号</span></a>\n" +
        "    </nav>\n" +
        "    <nav class=\"nav-detail\" v-if=\"7 === masterItem\">\n" +
        "        <h1 class=\"nav-detail-title\">系统信息</h1>\n" +
        "        <a class=\"nav-detail-item active\" href=\"\"><span>系统设置</span></a>\n" +
        "    </nav>\n" +
        "</aside>"
});
