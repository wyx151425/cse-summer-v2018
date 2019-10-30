package com.cse.summer.model.entity;

import com.cse.summer.util.Constant;
import com.cse.summer.util.Generator;

import javax.persistence.Entity;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "cse_name")
public class Name extends SummerEntity {

    private String english;
    private String chinese;

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public static Name newInstance() {
        Name name = new Name();
        name.setObjectId(Generator.getObjectId());
        name.setStatus(Constant.Status.ENABLE);
        LocalDateTime dateTime = LocalDateTime.now().withNano(0);
        name.setCreateAt(dateTime);
        name.setUpdateAt(dateTime);
        return name;
    }

    @Override
    public String toString() {
        return "Name{" +
                "english='" + english + '\'' +
                ", chinese='" + chinese + '\'' +
                '}' + '\n';
    }
}
