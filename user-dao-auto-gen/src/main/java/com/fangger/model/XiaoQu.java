package com.fangger.model;

import java.io.Serializable;

/**
 * 
 *
 * @author p0po
 * @date 2015-3-18
 *
 */
public class XiaoQu implements Serializable {
    /**  */
    private Integer id;

    /**  */
    private String name;

    /**  */
    private String districtId;

    /**  */
    private Integer source;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId == null ? null : districtId.trim();
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}