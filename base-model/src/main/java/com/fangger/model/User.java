package com.fangger.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author benben
 * @date 2015-9-5
 *
 */
public class User implements Serializable {
    /**  */
    private Integer id;

    /** 昵称 */
    private String nickname;

    /** 真实姓名 */
    private String realName;

    /** 手机号 */
    private Long phone;

    /** 邮箱 */
    private String email;

    /** 性别 */
    private Boolean gender;

    /** 出生日期 */
    private Date birthday;

    /** 来源 */
    private String source;

    /**  */
    private Date createTime;

    /**  */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}