package net.yongpo.mybatiesgen;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * Created by p0po on 2016/1/15 0015.
 */
public class ForceIndexPlugin extends PluginAdapter {
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean sqlMapSelectByPrimaryKeyElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapSelectByPrimaryKeyElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        appendForceIndexBeforeWhereXml(element,introspectedTable);
        return super.sqlMapSelectByExampleWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        appendForceIndexBeforeWhereXml(element,introspectedTable);
        return super.sqlMapSelectByExampleWithoutBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean sqlMapResultMapWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapResultMapWithBLOBsElementGenerated(element, introspectedTable);
    }

    @Override
    public boolean providerSelectByExampleWithBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.providerSelectByExampleWithBLOBsMethodGenerated(method, topLevelClass, introspectedTable);
    }

    @Override
    public boolean providerSelectByExampleWithoutBLOBsMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        return super.providerSelectByExampleWithoutBLOBsMethodGenerated(method, topLevelClass, introspectedTable);
    }

    @Override
    public boolean sqlMapSelectAllElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {
        return super.sqlMapSelectAllElementGenerated(element, introspectedTable);
    }

    private void appendForceIndexBeforeWhereXml(XmlElement element, IntrospectedTable introspectedTable){
        XmlElement fIndex = new XmlElement("if");
        fIndex.addAttribute(new Attribute("test", "index"));
        fIndex.addElement(new TextElement(" FORCE INDEX(`${index}`) "));
        element.addElement(4,fIndex);
    }

    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        // add field, getter, setter for page info
        addForceIndexExampleClass(topLevelClass, introspectedTable);
        return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
    }

    private void addForceIndexExampleClass(TopLevelClass topLevelClass, IntrospectedTable introspectedTable){
        CommentGenerator commentGenerator = context.getCommentGenerator();

        Field forceIndexField = new Field();
        forceIndexField.setVisibility(JavaVisibility.PROTECTED);
        forceIndexField.setType(FullyQualifiedJavaType.getStringInstance());
        forceIndexField.setName("index");
        //forceIndexField.setInitializationString("\"PRIMARY\"");
        commentGenerator.addFieldComment(forceIndexField, introspectedTable);
        topLevelClass.addField(forceIndexField);

        // add setter method
        Method indexSetter = new Method();
        indexSetter.setVisibility(JavaVisibility.PUBLIC);
        indexSetter.setName("setIndex");
        indexSetter.addParameter(new Parameter(FullyQualifiedJavaType.getStringInstance(), "index"));
        indexSetter.addBodyLine("this.index=index;");
        commentGenerator.addGeneralMethodComment(indexSetter, introspectedTable);
        topLevelClass.addMethod(indexSetter);

        // add getter method
        Method indexGetter = new Method();
        indexGetter.setVisibility(JavaVisibility.PUBLIC);
        indexGetter.setReturnType(FullyQualifiedJavaType.getStringInstance());
        indexGetter.setName("getIndex");
        indexGetter.addBodyLine("return this.index;");
        commentGenerator.addGeneralMethodComment(indexGetter, introspectedTable);
        topLevelClass.addMethod(indexGetter);
    }
}
