package com.fangger.crawer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by p0po on 15-3-2.
 */
public class Sql {
    private static String connectStr = "jdbc:mysql://localhost:3306/fangger";
    private static String username = "root";
    private static String password = "root";


    public static void batchInsert(List<Map<String,Object>> data) throws ClassNotFoundException, SQLException {

        String insert_sql = makeInsertSql(data,"xiaoqu");

        Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(connectStr, username, password);
        conn.setAutoCommit(false); // 设置手动提交
        int count = 0;
        PreparedStatement psts = conn.prepareStatement(insert_sql);

        for (Map<String,Object> row:data){
            int index = 1;
            for(String key:row.keySet()){
                if(row.get(key) instanceof String){
                    psts.setString(index,(String)row.get(key));
                }
                if(row.get(key) instanceof Boolean){
                    psts.setBoolean(index, (Boolean) row.get(key));
                }
                if(row.get(key) instanceof Byte){
                    psts.setByte(index, (Byte) row.get(key));
                }
                if(row.get(key) instanceof Integer){
                    psts.setInt(index, (Integer) row.get(key));
                }
                if(row.get(key) instanceof Long){
                    psts.setLong(index, (Integer) row.get(key));
                }
                if(row.get(key) instanceof Float){
                    psts.setFloat(index, (Float) row.get(key));
                }
                if(row.get(key) instanceof Double){
                    psts.setDouble(index, (Double) row.get(key));
                }
                if(row.get(key) instanceof Date){
                    psts.setDate(index, (Date) row.get(key));
                }
                index++;
            }
            psts.addBatch();
        }

        psts.executeBatch();

        conn.commit();
    }

    private static String makeInsertSql(List<Map<String,Object>> data,String table){
        String insert_sql = "INSERT INTO "+table+" (";
        Map<String,Object> row = data.get(0);
        for(String key:row.keySet()){
            insert_sql +=  key+",";
        }

        insert_sql = insert_sql.substring(0,insert_sql.length()-1)+") VALUES (";


        for(String key:row.keySet()){
            insert_sql +=  "?,";
        }

        insert_sql = insert_sql.substring(0,insert_sql.length()-1)+")";
        return insert_sql;
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        //map.put("id",1);
        map.put("name","test----");
        map.put("district_id","id12324");
        map.put("source",1);
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(map);

        /*String sql = makeInsertSql(list, "xiaoqu");

        System.out.println(sql);*/

        try {
            batchInsert(list);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
