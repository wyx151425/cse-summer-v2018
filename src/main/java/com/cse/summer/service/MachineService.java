package com.cse.summer.service;

import com.cse.summer.model.dto.AnalyzeResult;
import com.cse.summer.model.entity.Machine;
import com.cse.summer.model.entity.User;
import org.dom4j.DocumentException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author 王振琦
 */
public interface MachineService {

    /**
     * 导入并解析MAN XML文件
     *
     * @param machineName 机器名
     * @param file        MAN XML文件
     * @return 导入结果标识
     * @throws DocumentException 文档处理异常
     * @throws IOException       输入输出异常
     */
    List<AnalyzeResult> importMANMachineBOM(String machineName, MultipartFile file) throws DocumentException, IOException;

    /**
     * 获得机器数据
     *
     * @param machineName 机器名称
     * @return 机器数据
     */
    Machine findMachine(String machineName);

    /**
     * 获取所有机器数据
     *
     * @return 机器数据列表
     */
    List<Machine> findMachineList();

    /**
     * 删除所有机器数据
     *
     * @param user 用户对象
     */
    void deleteAllMachine(User user);

    /**
     * 更新机器信息
     *
     * @param machine 机器数据
     */
    void updateMachine(Machine machine);
}
