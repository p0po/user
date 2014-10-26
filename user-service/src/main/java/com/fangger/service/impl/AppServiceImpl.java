package com.fangger.service.impl;

import com.fangger.dao.mysql.mapper.AppMapper;
import com.fangger.dao.mysql.mapper.AppPermissionMapper;
import com.fangger.dao.mysql.mapper.PermissionMapper;
import com.fangger.dao.mysql.model.*;
import com.fangger.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by popo on 2014/10/25.
 */
@Service
public class AppServiceImpl implements AppService {
    private static final Map<String,Integer> permissionCacheMap = new ConcurrentHashMap<String,Integer>();
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    AppMapper appMapper;
    @Autowired
    AppPermissionMapper appPermissionMapper;

    private void initCache(){
        PermissionExample permissionExample = new PermissionExample();
        permissionExample.createCriteria().andIdIsNotNull();
        List<Permission> permissionList = permissionMapper.selectByExample(permissionExample);
        if(permissionList!=null){
            for(Permission permission:permissionList){
                permissionCacheMap.put(permission.getName(), permission.getId());
            }
        }

    }
    @Override
    public Permission getPermissionById(int id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int getPermissionIdByName(String name) {
        if(permissionCacheMap.size() == 0){
            initCache();
        }
        return permissionCacheMap.get(name);
    }

    @Override
    public int saveApk(App app) {
        return appMapper.insertSelective(app);
    }

    @Override
    public App getAppById(int id) {
        return appMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor=Exception.class)
    public int saveAppPermission(List<AppPermission> appPermissionList) {
        App app = new App();
        app.setStatus((byte)1);
        List<Integer> idList = new ArrayList<Integer>();
        for(AppPermission appPermission:appPermissionList){
            idList.add(appPermission.getAppId());
        }
        appMapper.batchUpdateStatusByPrimaryKey(app, idList);

        int num = appPermissionMapper.insertBath(appPermissionList);
        return num;
    }
}
