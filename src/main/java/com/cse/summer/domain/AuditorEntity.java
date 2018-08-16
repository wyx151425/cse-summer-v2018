package com.cse.summer.domain;

/**
 * @author 王振琦
 */
public class AuditorEntity extends Entity {
    private String createBy;
    private String updateBy;

    public AuditorEntity(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
