package com.cse.summer.model.dto;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author WangZhenqi
 */
public class MachineRequest {
    /**
     * 机器名
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 专利方类型
     */
    private String patent;
    /**
     * 文件
     */
    private MultipartFile file;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPatent() {
        return patent;
    }

    public void setPatent(String patent) {
        this.patent = patent;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
