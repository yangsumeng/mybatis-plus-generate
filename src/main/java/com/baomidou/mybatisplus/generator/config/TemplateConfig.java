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
package com.baomidou.mybatisplus.generator.config;

/**
 * 模板路径配置项
 *
 * @author tzg hubin
 * @since 2017-06-17
 */
public class TemplateConfig {

  private String entity = ConstVal.TEMPLATE_ENTITY_JAVA;

  private String service = ConstVal.TEMPLATE_SERVICE;

  private String serviceImpl = ConstVal.TEMPLATE_SERVICEIMPL;

  private String mapper = ConstVal.TEMPLATE_MAPPER;

  private String repository = ConstVal.TEMPLATE_REPOSITORY;

  private String form = ConstVal.TEMPLATE_FORM;
  private String vo = ConstVal.TEMPLATE_VO;

  private String xml = ConstVal.TEMPLATE_XML;

  private String controller = ConstVal.TEMPLATE_CONTROLLER;

  public String getEntity(boolean kotlin) {
    return kotlin ? ConstVal.TEMPLATE_ENTITY_KT : entity;
  }

  public TemplateConfig setEntity(String entity) {
    this.entity = entity;
    return this;
  }

  public String getService() {
    return service;
  }

  public TemplateConfig setService(String service) {
    this.service = service;
    return this;
  }

  public String getServiceImpl() {
    return serviceImpl;
  }

  public TemplateConfig setServiceImpl(String serviceImpl) {
    this.serviceImpl = serviceImpl;
    return this;
  }

  public String getMapper() {
    return mapper;
  }

  public TemplateConfig setMapper(String mapper) {
    this.mapper = mapper;
    return this;
  }

  public String getRepository() {
    return repository;
  }

  public TemplateConfig setRepository(String repository) {
    this.repository = repository;
    return this;
  }

    public String getForm() {
        return form;
    }

    public TemplateConfig setForm(String form) {
        this.form = form;
        return this;
    }

    public String getVo() {
        return vo;
    }

    public TemplateConfig setVo(String vo) {
        this.vo = vo;
        return this;
    }

    public String getXml() {
    return xml;
  }

  public TemplateConfig setXml(String xml) {
    this.xml = xml;
    return this;
  }

  public String getController() {
    return controller;
  }

  public TemplateConfig setController(String controller) {
    this.controller = controller;
    return this;
  }
}
