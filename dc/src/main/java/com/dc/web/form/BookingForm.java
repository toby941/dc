/*
 * Copyright 2011 mitian Technology, Co., Ltd. All rights reserved.
 */
package com.dc.web.form;

import java.util.Date;

public class BookingForm extends AbstractForm {

    private Integer id;
    private Integer emmsCompanyId;
    private Integer emmsStorefrontId;
    private String content;
    private Date addTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmmsCompanyId() {
        return emmsCompanyId;
    }

    public void setEmmsCompanyId(Integer emmsCompanyId) {
        this.emmsCompanyId = emmsCompanyId;
    }

    public Integer getEmmsStorefrontId() {
        return emmsStorefrontId;
    }

    public void setEmmsStorefrontId(Integer emmsStorefrontId) {
        this.emmsStorefrontId = emmsStorefrontId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /*
     * (non-Javadoc)
     * @see com.mitian.emms.web.form.AbstractForm#form2domain()
     */
    @Override
    public void form2domain() {

    }

}
