/**
 * Copyright (c) 2011-2020, hubin (jobob@qq.com).
 *
 * <p>Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * <p>http://www.apache.org/licenses/LICENSE-2.0
 *
 * <p>Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.baomidou.mybatisplus.generator.engine;

import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.ConstVal;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 模板引擎抽象类
 *
 * @author hubin
 * @since 2018-01-10
 */
public abstract class AbstractTemplateEngine {

  protected static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
  /** 配置信息 */
  private ConfigBuilder configBuilder;

  /** 模板引擎初始化 */
  public AbstractTemplateEngine init(ConfigBuilder configBuilder) {
    this.configBuilder = configBuilder;
    return this;
  }

  /** 输出 java xml 文件 */
  public AbstractTemplateEngine batchOutput() {
    try {
      List<TableInfo> tableInfoList = this.getConfigBuilder().getTableInfoList();
      for (TableInfo tableInfo : tableInfoList) {
        Map<String, Object> objectMap = this.getObjectMap(tableInfo);
        Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
        TemplateConfig template = this.getConfigBuilder().getTemplate();
        // 自定义内容
        InjectionConfig injectionConfig = this.getConfigBuilder().getInjectionConfig();
        if (null != injectionConfig) {
          injectionConfig.initMap();
          objectMap.put("cfg", injectionConfig.getMap());
          List<FileOutConfig> focList = injectionConfig.getFileOutConfigList();
          if (CollectionUtils.isNotEmpty(focList)) {
            for (FileOutConfig foc : focList) {
              if (this.isCreate(foc.outputFile(tableInfo))) {
                this.writer(objectMap, foc.getTemplatePath(), foc.outputFile(tableInfo));
              }
            }
          }
        }
        // Mp.java
        String entityName = tableInfo.getEntityName();
        if (null != entityName && null != pathInfo.get(ConstVal.ENTITY_PATH)) {
          String entityFile =
              String.format(
                  (pathInfo.get(ConstVal.ENTITY_PATH)
                      + File.separator
                      + "%s"
                      + this.suffixJavaOrKt()),
                  entityName);
          if (this.isCreate(entityFile)) {
            this.writer(
                objectMap,
                this.templateFilePath(
                    template.getEntity(this.getConfigBuilder().getGlobalConfig().isKotlin())),
                entityFile);
          }
        }
        // MpMapper.java
        if (null != tableInfo.getMapperName() && null != pathInfo.get(ConstVal.MAPPER_PATH)) {
          String mapperFile =
              String.format(
                  (pathInfo.get(ConstVal.MAPPER_PATH)
                      + File.separator
                      + tableInfo.getMapperName()
                      + this.suffixJavaOrKt()),
                  entityName);
          if (this.isCreate(mapperFile)) {
            this.writer(objectMap, this.templateFilePath(template.getMapper()), mapperFile);
          }
        } // MpMapper.java
        if (null != tableInfo.getRepositoryName()
            && null != pathInfo.get(ConstVal.REPOSITORY_PATH)) {
          String repositoryFile =
              String.format(
                  (pathInfo.get(ConstVal.REPOSITORY_PATH)
                      + File.separator
                      + tableInfo.getRepositoryName()
                      + this.suffixJavaOrKt()),
                  entityName);
          if (this.isCreate(repositoryFile)) {
            this.writer(objectMap, this.templateFilePath(template.getRepository()), repositoryFile);
          }
        }
        // MpMapper.xml
        if (null != tableInfo.getXmlName() && null != pathInfo.get(ConstVal.XML_PATH)) {
          String xmlFile =
              String.format(
                  (pathInfo.get(ConstVal.XML_PATH)
                      + File.separator
                      + tableInfo.getXmlName()
                      + ConstVal.XML_SUFFIX),
                  entityName);
          if (this.isCreate(xmlFile)) {
            this.writer(objectMap, this.templateFilePath(template.getXml()), xmlFile);
          }
        }
        // IMpService.java
        if (null != tableInfo.getServiceName() && null != pathInfo.get(ConstVal.SERVICE_PATH)) {
          String serviceFile =
              String.format(
                  (pathInfo.get(ConstVal.SERVICE_PATH)
                      + File.separator
                      + tableInfo.getServiceName()
                      + this.suffixJavaOrKt()),
                  entityName);
          if (this.isCreate(serviceFile)) {
            this.writer(objectMap, this.templateFilePath(template.getService()), serviceFile);
          }
        }
        // MpServiceImpl.java
        if (null != tableInfo.getServiceImplName()
            && null != pathInfo.get(ConstVal.SERVICEIMPL_PATH)) {
          String implFile =
              String.format(
                  (pathInfo.get(ConstVal.SERVICEIMPL_PATH)
                      + File.separator
                      + tableInfo.getServiceImplName()
                      + this.suffixJavaOrKt()),
                  entityName);
          if (this.isCreate(implFile)) {
            this.writer(objectMap, this.templateFilePath(template.getServiceImpl()), implFile);
          }
        }
        // MpController.java
        if (null != tableInfo.getControllerName()
            && null != pathInfo.get(ConstVal.CONTROLLER_PATH)) {
          String controllerFile =
              String.format(
                  (pathInfo.get(ConstVal.CONTROLLER_PATH)
                      + File.separator
                      + tableInfo.getControllerName()
                      + this.suffixJavaOrKt()),
                  entityName);
          if (this.isCreate(controllerFile)) {
            this.writer(objectMap, this.templateFilePath(template.getController()), controllerFile);
          }
        }
          // MpVo.java
          if (null != tableInfo.getVoName() && null != pathInfo.get(ConstVal.VO_PATH)) {
              String voFile =
                      String.format(
                              (pathInfo.get(ConstVal.VO_PATH)
                                      + File.separator
                                      + tableInfo.getVoName()
                                      + this.suffixJavaOrKt()),
                              entityName);
              if (this.isCreate(voFile)) {
                  this.writer(objectMap, this.templateFilePath(template.getVo()), voFile);
              }
          }
          // MpForm.java
          if (null != tableInfo.getFormName() && null != pathInfo.get(ConstVal.FORM_PATH)) {
              String formFile =
                      String.format(
                              (pathInfo.get(ConstVal.FORM_PATH)
                                      + File.separator
                                      + tableInfo.getFormName()
                                      + this.suffixJavaOrKt()),
                              entityName);
              if (this.isCreate(formFile)) {
                  this.writer(objectMap, this.templateFilePath(template.getForm()), formFile);
              }
          }
      }
    } catch (Exception e) {
      logger.error("无法创建文件，请检查配置信息！", e);
    }
    return this;
  }

  /**
   * 将模板转化成为文件
   *
   * @param objectMap 渲染对象 MAP 信息
   * @param templatePath 模板文件
   * @param outputFile 文件生成的目录
   */
  public abstract void writer(Map<String, Object> objectMap, String templatePath, String outputFile)
      throws Exception;

  /** 处理输出目录 */
  public AbstractTemplateEngine mkdirs() {
    Map<String, String> pathInfo = this.getConfigBuilder().getPathInfo();
    for (Map.Entry<String, String> entry : pathInfo.entrySet()) {
      File dir = new File(entry.getValue());
      if (!dir.exists()) {
        boolean result = dir.mkdirs();
        if (result) {
          logger.debug("创建目录： [" + entry.getValue() + "]");
        }
      }
    }
    return this;
  }

  /** 打开输出目录 */
  public void open() {
    if (this.getConfigBuilder().getGlobalConfig().isOpen()) {
      try {
        String osName = System.getProperty("os.name");
        if (osName != null) {
          if (osName.contains("Mac")) {
            Runtime.getRuntime()
                .exec("open " + this.getConfigBuilder().getGlobalConfig().getOutputDir());
          } else if (osName.contains("Windows")) {
            Runtime.getRuntime()
                .exec("cmd /c start " + this.getConfigBuilder().getGlobalConfig().getOutputDir());
          } else {
            logger.debug("文件输出目录:" + this.getConfigBuilder().getGlobalConfig().getOutputDir());
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 渲染对象 MAP 信息
   *
   * @param tableInfo 表信息对象
   * @return
   */
  public Map<String, Object> getObjectMap(TableInfo tableInfo) {
    Map<String, Object> objectMap = new HashMap<>();
    ConfigBuilder config = this.getConfigBuilder();
    if (config.getStrategyConfig().isControllerMappingHyphenStyle()) {
      objectMap.put("controllerMappingHyphenStyle", config.getStrategyConfig().isControllerMappingHyphenStyle());
      objectMap.put("controllerMappingHyphen", StringUtils.camelToHyphen(tableInfo.getEntityPath()));
    }
    objectMap.put("restControllerStyle", config.getStrategyConfig().isRestControllerStyle());
    objectMap.put("package", config.getPackageInfo());
    GlobalConfig globalConfig = config.getGlobalConfig();
    objectMap.put("author", globalConfig.getAuthor());
    objectMap.put("version", globalConfig.getVersion());
    objectMap.put("idType", globalConfig.getIdType() == null ? null : globalConfig.getIdType().toString());
    objectMap.put("logicDeleteFieldName", config.getStrategyConfig().getLogicDeleteFieldName());
    objectMap.put("versionFieldName", config.getStrategyConfig().getVersionFieldName());
    objectMap.put("activeRecord", globalConfig.isActiveRecord());
    objectMap.put("kotlin", globalConfig.isKotlin());
    objectMap.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
    objectMap.put("table", tableInfo);
    /*设置静态属性 */
    objectMap.put("staticCells", tableInfo.getStaticCells());
    objectMap.put("enableCache", globalConfig.isEnableCache());
    objectMap.put("baseResultMap", globalConfig.isBaseResultMap());
    objectMap.put("baseColumnList", globalConfig.isBaseColumnList());
    objectMap.put("entity", tableInfo.getEntityName());
    objectMap.put("entityColumnConstant", config.getStrategyConfig().isEntityColumnConstant());
    objectMap.put("entityBuilderModel", config.getStrategyConfig().isEntityBuilderModel());
    objectMap.put("entityLombokModel", config.getStrategyConfig().isEntityLombokModel());
    objectMap.put("entityBooleanColumnRemoveIsPrefix", config.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix());
    objectMap.put("superEntityClass", this.getSuperClassName(config.getSuperEntityClass()));
    objectMap.put("superMapperClassPackage", config.getSuperMapperClass());
    objectMap.put("superMapperClass", this.getSuperClassName(config.getSuperMapperClass()));
    objectMap.put("superRepositoryClassPackage", config.getSuperRepositoryClass());
    objectMap.put("superRepositoryClass", this.getSuperClassName(config.getSuperRepositoryClass()));
    objectMap.put("superServiceClassPackage", config.getSuperServiceClass());
    objectMap.put("superServiceClass", this.getSuperClassName(config.getSuperServiceClass()));
    objectMap.put("superServiceImplClassPackage", config.getSuperServiceImplClass());
    objectMap.put("superServiceImplClass", this.getSuperClassName(config.getSuperServiceImplClass()));
    objectMap.put("superControllerClassPackage", config.getSuperControllerClass());
    objectMap.put("superControllerClass", this.getSuperClassName(config.getSuperControllerClass()));
    return objectMap;
  }

  /**
   * 获取类名
   *
   * @param classPath
   * @return
   */
  private String getSuperClassName(String classPath) {
    if (StringUtils.isEmpty(classPath)) {
      return null;
    }
    return classPath.substring(classPath.lastIndexOf(".") + 1);
  }

  /**
   * 模板真实文件路径
   *
   * @param filePath 文件路径
   * @return
   */
  public abstract String templateFilePath(String filePath);

  /**
   * 检测文件是否存在
   *
   * @return 是否
   */
  protected boolean isCreate(String filePath) {
    File file = new File(filePath);
    boolean exist = file.exists();
    if (!exist) {
      this.mkDir(file.getParentFile());
    }
    return !exist || this.getConfigBuilder().getGlobalConfig().isFileOverride();
  }

  protected void mkDir(File file) {
    if (null != file) {
      if (file.getParentFile().exists()) {
        file.mkdir();
      } else {
        mkDir(file.getParentFile());
        file.mkdir();
      }
    }
  }

  /** 文件后缀 */
  protected String suffixJavaOrKt() {
    return this.getConfigBuilder().getGlobalConfig().isKotlin()
        ? ConstVal.KT_SUFFIX
        : ConstVal.JAVA_SUFFIX;
  }

  public ConfigBuilder getConfigBuilder() {
    return configBuilder;
  }

  public AbstractTemplateEngine setConfigBuilder(ConfigBuilder configBuilder) {
    this.configBuilder = configBuilder;
    return this;
  }
}
