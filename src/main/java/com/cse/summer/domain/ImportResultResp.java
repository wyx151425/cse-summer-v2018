package com.cse.summer.domain;

/**
 * @author WangZhenqi
 */
public class ImportResultResp {
    /**
     * 部套号
     */
    private String structureNo;
    /**
     * 导入结果标识
     */
    private Boolean result;

    public ImportResultResp(String structureNo, Boolean result) {
        this.structureNo = structureNo;
        this.result = result;
    }

    public String getStructureNo() {
        return structureNo;
    }

    public void setStructureNo(String structureNo) {
        this.structureNo = structureNo;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
}
