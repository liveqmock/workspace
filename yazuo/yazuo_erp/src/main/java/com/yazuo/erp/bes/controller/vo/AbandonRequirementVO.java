package com.yazuo.erp.bes.controller.vo;

import java.util.Arrays;

/**
 *  批量放弃对象
 */
public class AbandonRequirementVO {
    private Integer[] ids;
    private String content;

    public Integer[] getIds() {
        return ids;
    }

    public void setIds(Integer[] ids) {
        this.ids = ids;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "AbandonRequirementVO{" +
                "ids=" + Arrays.toString(ids) +
                ", content='" + content + '\'' +
                '}';
    }
}
