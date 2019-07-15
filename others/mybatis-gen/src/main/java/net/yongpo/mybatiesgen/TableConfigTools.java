package net.yongpo.mybatiesgen;

import com.google.common.base.CaseFormat;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengjr on 2015/6/1 0001.
 */
public class TableConfigTools {

    public static void main(String[] args) {
        List<String> list = new ArrayList<String>(0);
        try {
            list = FileUtils.readLines(FileUtils.toFile(TableConfigTools.class.getResource("/table")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String l:list){
            l = l.trim();
            //l = l.substring(1, l.length()-1);
            //l = l.trim();
            int index = l.indexOf('_')+1;
            String tableObject = l.substring(index);
            String aaa = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL,tableObject);

            //System.out.println(l+"  -->  "+aaa);

            if("TB_USER".equals(l)){
                continue;
            }
            if("TB_LOANREQUEST_2015_1201".equals(l)){
                continue;
            }
            if("user_conversion".equals(l)){
                continue;
            }
            if(l.contains("TB_CORPORATION_USER")){
                continue;
            }
            if(l.contains("TB_CMS")){
                continue;
            }
            if(l.contains("ZC_XIAOHUAJIA")){
                continue;
            }
            if(l.contains("TB_VEHICLE")){
                continue;
            }
            if(l.contains("TB_ACTIVITY")){
                continue;
            }
            if(!l.startsWith("TB_")) {
                continue;
            }

            String xml = "<table tableName=\""+l+"\" domainObjectName=\""+aaa+"\"></table>";
            System.out.println(xml);
        }
    }
}
