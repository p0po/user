package com.fangger.service;

import com.fangger.dao.mysql.model.App;
import com.fangger.dao.mysql.model.AppPermission;
import com.fangger.dao.mysql.model.Permission;

import java.util.Collection;
import java.util.List;

/**
 * Created by popo on 2014/10/25.
 * 测试一下，删除
 */
public interface AppService {
    public Permission getPermissionById(int id);
    public int getPermissionIdByName(String name);

    public int saveApk(App app);
    public App getAppById(int id);

    public int saveAppPermission(List<AppPermission> appPermissionList);
}
