package net.yongpo.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 *
 * @author benben
 * @date 2017-12-10
 *
 */
public class Passport implements Serializable {
    /**  */
    private Integer id;

    /** 用户ID */
    private Integer userId;

    /** 密码 */
    private String password;

    /** 密码盐 */
    private String salt;

    /** 上次登录时间 */
    private Date lastLoginTime;

    /** 上次登录IP */
    private String lastLoginIp;

    /** 上次登录UA */
    private String lastLoginUa;

    /** 更新时间 */
    private Date updateTime;

    /** 密码更新时间 */
    private Date passwordSetTime;

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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getPasswordSetTime() {
        return passwordSetTime;
    }

    public void setPasswordSetTime(Date passwordSetTime) {
        this.passwordSetTime = passwordSetTime;
    }
}