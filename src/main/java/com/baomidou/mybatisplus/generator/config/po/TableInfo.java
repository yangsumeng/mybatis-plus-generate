/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.generator.config.po;

import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.*;

/**
 * <p>
 * 表信息，关联到当前字段信息
 * </p>
 *
 * @author YangHu
 * @since 2016/8/30
 */
public class TableInfo {

    private boolean convert;
    private String name;
    private String comment;
    /**sys_user SysUser*/
    private String originalEntityName;
    private String formName;
    private String voName;
    private String entityName;
    private String mapperName;
    private String repositoryName;
    private String xmlName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;

    private List<TableField> fields;

    private List<StaticCell> staticCells;
    // 公共字段
    private List<TableField> commonFields;
    private List<String> importPackages = new ArrayList<>();
    private String fieldNames;

    public boolean isConvert() {
        return convert;
    }

    protected void setConvert(StrategyConfig strategyConfig) {
        if (strategyConfig.containsTablePrefix(name)) {
            // 包含前缀
            this.convert = true;
        } else if (strategyConfig.isCapitalModeNaming(name)) {
            // 包含
            this.convert = false;
        } else {
            // 转换字段
            if (StrategyConfig.DB_COLUMN_UNDERLINE) {
                // 包含大写处理
                if (StringUtils.containsUpperCase(name)) {
                    this.convert = true;
                }
            } else if (!entityName.equalsIgnoreCase(name)) {
                this.convert = true;
            }
        }
    }

    public void setConvert(boolean convert) {
        this.convert = convert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *@descption  表 sys_user  originalEntityName是 SysUser  这里修改为sysUser
     *      用于拼接  ${table.entityPath}Form
     *@author yangsm
     *@date  2019/9/5
     */
    public String getEntityPath() {
        StringBuilder ep = new StringBuilder();
        ep.append(originalEntityName.substring(0, 1).toLowerCase());
        ep.append(originalEntityName.substring(1));
        return ep.toString();
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(StrategyConfig strategyConfig, String entityName) {
        this.entityName = entityName;
        this.setConvert(strategyConfig);
    }

    public void setEntityName( String entityName) {
        this.entityName = entityName;
    }

    public String getOriginalEntityName() {
        return originalEntityName;
    }

    public void setOriginalEntityName(String originalEntityName) {
        this.originalEntityName = originalEntityName;
    }

    public String getMapperName() {
        return mapperName;
    }

    public void setMapperName(String mapperName) {
        this.mapperName = mapperName;
    }

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public String getXmlName() {
        return xmlName;
    }

    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceImplName() {
        return serviceImplName;
    }

    public void setServiceImplName(String serviceImplName) {
        this.serviceImplName = serviceImplName;
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public List<TableField> getFields() {
        return fields;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getVoName() {
        return voName;
    }

    public void setVoName(String voName) {
        this.voName = voName;
    }

    public List<StaticCell> getStaticCells() {
        return staticCells;
    }

    public void setStaticCells(List<StaticCell> staticCells) {
        this.staticCells = staticCells;
    }

    public void setFields(List<TableField> fields) {
        if (CollectionUtils.isNotEmpty(fields)) {
            this.fields = fields;
            // 收集导入包信息
            Set<String> pkgSet = new HashSet<>();
            for (TableField field : fields) {
                if(TableField.baseFieldSet.contains(field.getName())){
                    field.setBaseField(true);
                }
                if (null != field.getColumnType() && null != field.getColumnType().getPkg()) {
                    pkgSet.add(field.getColumnType().getPkg());
                }
                if (field.isKeyFlag()) {
                    // 主键
                    if (field.isConvert() || field.isKeyIdentityFlag()) {
                        pkgSet.add("com.baomidou.mybatisplus.annotations.TableId");
                    }
                    // 自增
                    if (field.isKeyIdentityFlag()) {
                        pkgSet.add("com.baomidou.mybatisplus.enums.IdType");
                    }
                } else if (field.isConvert()) {
                    // 普通字段
                    pkgSet.add("com.baomidou.mybatisplus.annotations.TableField");
                }
                if (null != field.getFill()) {
                    // 填充字段
                    pkgSet.add("com.baomidou.mybatisplus.annotations.TableField");
                    pkgSet.add("com.baomidou.mybatisplus.enums.FieldFill");
                }
            }
            if (!pkgSet.isEmpty()) {
                this.importPackages = new ArrayList<>(Arrays.asList(pkgSet.toArray(new String[]{})));
            }
        }
    }

    public List<TableField> getCommonFields() {
        return commonFields;
    }

    public void setCommonFields(List<TableField> commonFields) {
        this.commonFields = commonFields;
    }

    public List<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(String pkg) {
        importPackages.add(pkg);
    }

    /**
     * 逻辑删除
     */
    public boolean isLogicDelete(String logicDeletePropertyName) {
        for (TableField tableField : fields) {
            if (tableField.getName().equals(logicDeletePropertyName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 转换filed实体为xmlmapper中的basecolumn字符串信息
     *
     * @return
     */
    public String getFieldNames() {
        if (StringUtils.isEmpty(fieldNames)) {
            StringBuilder names = new StringBuilder();
            for (int i = 0; i < fields.size(); i++) {
                TableField fd = fields.get(i);
                if (i == fields.size() - 1) {
                    names.append(fd.getName());
                } else {
                    names.append(fd.getName()).append(", ");
                }
            }
            fieldNames = names.toString();
        }
        return fieldNames;
    }

}
