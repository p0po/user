package net.yongpo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author benben
 * @date 2016-1-6
 *
 */
public class Passport implements Serializable {
    /**  */
    private Integer id;

    /**  */
    private Integer userId;

    /**  */
    private String password;

    /**  */
    private String salt;

    /**  */
    private Date lastLoginTime;

    /**  */
    private String lastLoginIp;

    /**  */
    private String lastLoginUa;

    /**  */
    private Integer failureCount;

    /**  */
    private String source;

    /**  */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    public String getLastLoginUa() {
        return lastLoginUa;
    }

    public void setLastLoginUa(String lastLoginUa) {
        this.lastLoginUa = lastLoginUa == null ? null : lastLoginUa.trim();
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}