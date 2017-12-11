package net.yongpo.mybatiesgen;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

/**
 * Created by fengjr on 2015/6/1 0001.
 */
public class RenameExampleAndPrimaryKeyPlugin extends PluginAdapter {
    public boolean validate(List<String> warnings) {
        return true;
    }

    private String exampleToCondition(String stringWithExample) {
        return stringWithExample.replace("Example", "Condition");
    }

    /**
     * 这个方法不好，容易产生歧义
     * @param stringWithPrimaryKey
     * @return
     */
    private String primaryKeyToId(String stringWithPrimaryKey) {
        return stringWithPrimaryKey.replace("PrimaryKey", "Id");
    }

    /**
     * 这个方法不好，容易产生歧义
     * @param stringWithPrimaryKeyType
     * @return
     */
    private String primaryKeyTypeToId(String stringWithPrimaryKeyType) {
        return stringWithPrimaryKeyType.replaceAll("Key$", "Id");
    }

    @Override
    public void initialized(IntrospectedTable intrp) {
        intrp.setCountByExampleStatementId(exampleToCondition(intrp.getCountByExampleStatementId()));
        intrp.setDeleteByExampleStatementId(exampleToCondition(intrp.getDeleteByExampleStatementId()));

        intrp.setExampleWhereClauseId(exampleToCondition(intrp.getExampleWhereClauseId()));
        intrp.setMyBatis3UpdateByExampleWhereClauseId(exampleToCondition(intrp.getMyBatis3UpdateByExampleWhereClauseId()));

        intrp.setSelectByExampleStatementId(exampleToCondition(intrp.getSelectByExampleStatementId()));
        intrp.setSelectByExampleWithBLOBsStatementId(exampleToCondition(intrp.getSelectByExampleWithBLOBsStatementId()));

        intrp.setUpdateByExampleStatementId(exampleToCondition(intrp.getUpdateByExampleStatementId()));
        intrp.setUpdateByExampleSelectiveStatementId(exampleToCondition(intrp.getUpdateByExampleSelectiveStatementId()));
        intrp.setUpdateByExampleWithBLOBsStatementId(exampleToCondition(intrp.getUpdateByExampleWithBLOBsStatementId()));

        intrp.setDeleteByPrimaryKeyStatementId(primaryKeyToId(intrp.getDeleteByPrimaryKeyStatementId()));
        intrp.setSelectByPrimaryKeyStatementId(primaryKeyToId(intrp.getSelectByPrimaryKeyStatementId()));

        intrp.setUpdateByPrimaryKeySelectiveStatementId(primaryKeyToId(intrp.getUpdateByPrimaryKeySelectiveStatementId()));
        intrp.setUpdateByPrimaryKeyStatementId(primaryKeyToId(intrp.getUpdateByPrimaryKeyStatementId()));
        intrp.setUpdateByPrimaryKeyWithBLOBsStatementId(primaryKeyToId(intrp.getUpdateByPrimaryKeyWithBLOBsStatementId()));

        intrp.setPrimaryKeyType(primaryKeyTypeToId(intrp.getPrimaryKeyType()));
    }
}