package com.gigaspaces.model;

import com.gigaspaces.annotation.pojo.SpaceId;

/**
 * Created by kobi on 12/10/14.
 */
public class Foods {
    private Integer itemId;
    private String itemName;
    private String itemUnit;
    private Integer companyId;

    @SpaceId
    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }
}
