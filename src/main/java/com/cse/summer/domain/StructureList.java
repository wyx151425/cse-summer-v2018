package com.cse.summer.domain;

import com.cse.summer.context.exception.SummerException;
import com.cse.summer.util.StatusCode;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StructureList {
    private String machineName;
    private String structureNo1;
    private Integer amount1;
    private String structureNo2;
    private Integer amount2;
    private String structureNo3;
    private Integer amount3;
    private String structureNo4;
    private Integer amount4;
    private String structureNo5;
    private Integer amount5;
    private String structureNo6;
    private Integer amount6;
    private String structureNo7;
    private Integer amount7;
    private String structureNo8;
    private Integer amount8;
    private String structureNo9;
    private Integer amount9;

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public String getStructureNo1() {
        return structureNo1;
    }

    public void setStructureNo1(String structureNo1) {
        this.structureNo1 = structureNo1;
    }

    public Integer getAmount1() {
        return amount1;
    }

    public void setAmount1(Integer amount1) {
        this.amount1 = amount1;
    }

    public String getStructureNo2() {
        return structureNo2;
    }

    public void setStructureNo2(String structureNo2) {
        this.structureNo2 = structureNo2;
    }

    public Integer getAmount2() {
        return amount2;
    }

    public void setAmount2(Integer amount2) {
        this.amount2 = amount2;
    }

    public String getStructureNo3() {
        return structureNo3;
    }

    public void setStructureNo3(String structureNo3) {
        this.structureNo3 = structureNo3;
    }

    public Integer getAmount3() {
        return amount3;
    }

    public void setAmount3(Integer amount3) {
        this.amount3 = amount3;
    }

    public String getStructureNo4() {
        return structureNo4;
    }

    public void setStructureNo4(String structureNo4) {
        this.structureNo4 = structureNo4;
    }

    public Integer getAmount4() {
        return amount4;
    }

    public void setAmount4(Integer amount4) {
        this.amount4 = amount4;
    }

    public String getStructureNo5() {
        return structureNo5;
    }

    public void setStructureNo5(String structureNo5) {
        this.structureNo5 = structureNo5;
    }

    public Integer getAmount5() {
        return amount5;
    }

    public void setAmount5(Integer amount5) {
        this.amount5 = amount5;
    }

    public String getStructureNo6() {
        return structureNo6;
    }

    public void setStructureNo6(String structureNo6) {
        this.structureNo6 = structureNo6;
    }

    public Integer getAmount6() {
        return amount6;
    }

    public void setAmount6(Integer amount6) {
        this.amount6 = amount6;
    }

    public String getStructureNo7() {
        return structureNo7;
    }

    public void setStructureNo7(String structureNo7) {
        this.structureNo7 = structureNo7;
    }

    public Integer getAmount7() {
        return amount7;
    }

    public void setAmount7(Integer amount7) {
        this.amount7 = amount7;
    }

    public String getStructureNo8() {
        return structureNo8;
    }

    public void setStructureNo8(String structureNo8) {
        this.structureNo8 = structureNo8;
    }

    public Integer getAmount8() {
        return amount8;
    }

    public void setAmount8(Integer amount8) {
        this.amount8 = amount8;
    }

    public String getStructureNo9() {
        return structureNo9;
    }

    public void setStructureNo9(String structureNo9) {
        this.structureNo9 = structureNo9;
    }

    public Integer getAmount9() {
        return amount9;
    }

    public void setAmount9(Integer amount9) {
        this.amount9 = amount9;
    }

    public Structure getStructure(int index) {
        index += 1;
        try {
            Class clazz = Class.forName(this.getClass().getName());
            Method getStructureNo = clazz.getMethod("getStructureNo" + index);
            Method getAmount = clazz.getMethod("getAmount" + index);
            String structureNo = (String) getStructureNo.invoke(this);
            Integer amount = (Integer) getAmount.invoke(this);
            Structure structure = new Structure();
            structure.setMachineName(this.machineName);
            structure.setStructureNo(structureNo);
            structure.setAmount(amount);
            return structure;
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new SummerException(StatusCode.SYSTEM_ERROR);
        }
    }

    @Override
    public String toString() {
        return "StructureList{" +
                "structureNo1='" + structureNo1 + ", amount1=" + amount1 +
                ", structureNo2='" + structureNo2 + ", amount2=" + amount2 +
                ", structureNo3='" + structureNo3 + ", amount3=" + amount3 +
                ", structureNo4='" + structureNo4 + ", amount4=" + amount4 +
                ", structureNo5='" + structureNo5 + ", amount5=" + amount5 +
                ", structureNo6='" + structureNo6 + ", amount6=" + amount6 +
                ", structureNo7='" + structureNo7 + ", amount7=" + amount7 +
                ", structureNo8='" + structureNo8 + ", amount8=" + amount8 +
                ", structureNo9='" + structureNo9 + ", amount9=" + amount9 +
                '}';
    }
}
