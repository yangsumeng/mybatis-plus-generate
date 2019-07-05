package com.baomidou.mybatisplus.generator;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代码自动生成
 * fileOverride : 覆盖文件开关 true开启:将覆盖原来的文件
 * includeArr : 表
 * tablePrefix ： 前缀  将被截取掉
 * logicDeleteFieldName 逻辑删除字段  ， 没有课不配置
 * map : 自定义的参数 ，用来实现自定义功能 模板中可直接使用
 * 数据源信息： url       userName   password
 * outPutDir ： 生成路径：输出路径 outPutDir        C:\\WorkspaceWebstrom\\device-management-service\\src\\main\\java
 * author： 作者 用于生成注释
 * Itype： 主键策略
 * mapperPath：mapp.xml生成的路径 不设置默认是 ../resources/mapper
 */
public class CodeGenerator {
    //    private static String url = "jdbc:mysql://localhost:3306/qpi_ys";
//    private static String userName = "root";
//    private static  String password = "root";
    private static String url = "jdbc:mysql://192.168.50.116:3306/cps";
    private static String userName = "root";
    private static  String password = "ysten123";
    /**"D:\\generator";*/
    private static String outPutDir = "C:\\workspace\\cps\\cps-generator\\src\\main\\java\\";
    private static boolean fileOverride = true;
    private static String[] includeArr = {"cps_sys_dictionary_type"};
    private static String tablePrefix = "cps_sys_";
    private static String modelName = "system";
    private static String logicDeleteFieldName  = "";
    private static Map<String, Object> map = new HashMap<>();
    static {
        map.put("version","auto code version 1.4");
        map.put("getLabelAndValueList", false);
        //map.put("value", "area_id"); // 可设置 默认主键={X}_id
        //map.put("label", "area_name");//可设置 默认名称={X}_name  X根据主键获取
        //map.put("order", "operate_date desc");//非必填
        map.put("needEmptyMethod",false);
        map.put("emptyMethods","getDictIdNameList,getDictCodeNameList".split(","));
    }
    private static String author = "yangsm";
    private static IdType type = IdType.AUTO;
    private static String mapperPath = null; //不设置默认是 ../resources/mapper
    private static String sngleName = ""; //单表生成替换名称 暂时无用
    private static String parent = "com.ysten.conts.cps";

	public static void main(String[] args) {


		AutoGenerator autoGenerator = new AutoGenerator();
		autoGenerator.setTemplateEngine(new VelocityTemplateEngine());/**选择模板引擎，默认 Veloctiy 可选 freemarker */

		// 1. 全局配置
		GlobalConfig gc = new GlobalConfig();
        gc.setActiveRecord(false)               /* 是否支持AR模式*/
                .setOutputDir(outPutDir)        /* 生成路径*/
                .setFileOverride(fileOverride)  /* 文件覆盖*/
                .setIdType(type)               /* 主键策略*/
                .setEntityName("%sEntity")
                .setServiceName("I%sService")    /* 设置生成的service接口的名字的首字母是否为I,例:IEmployeeService*/
                .setRepositoryName("%sRepository")
                .setVoName("%sListResp")
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setEnableCache(false)
                .setAuthor(author);
		autoGenerator.setGlobalConfig(gc);

		// 2. 数据源配置
		DataSourceConfig dsc = new DataSourceConfig();
		dsc .setDriverName("com.mysql.jdbc.Driver")
                //.setDriverName("com.mysql.cj.jdbc.Driver")
                .setDbType(DbType.MYSQL).setUrl(url).setUsername(userName).setPassword(password);
		autoGenerator.setDataSource(dsc);

		// 3. 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setCapitalMode(true)
                .setDbColumnUnderline(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setTablePrefix(tablePrefix)
                .setInclude(includeArr);
		strategy.setRestControllerStyle(true);
        strategy.setEntityLombokModel(true);
		strategy.setLogicDeleteFieldName(logicDeleteFieldName);
        strategy.setSuperServiceClass("com.ysten.conts.cps.service.base.IBaseService");
        strategy.setSuperServiceImplClass("com.ysten.conts.cps.service.base.impl.JpaBaseService");
        strategy.setSuperRepositoryClass("com.ysten.conts.cps.repository.base.IJpaBaseRepository");
        strategy.setSuperControllerClass("com.ysten.conts.cps.controller.base.BaseController");
		autoGenerator.setStrategy(strategy);

		// 4. 包名策略配置
		PackageConfig pc = new PackageConfig();
		pc.setParent(parent)
                .setMapper(String.format("repository.%s.mapper",modelName))
				.setService(String.format("service.%s",modelName))
                .setServiceImpl(String.format("service.%s.impl",modelName))
				.setController(String.format("controller.%s",modelName))
				.setEntity(String.format("repository.%s.entity",modelName))
                .setRepository(String.format("repository.%s",modelName))
                .setForm(String.format("controller.%s.form",modelName))
                .setVo(String.format("controller.%s.vo",modelName))
				.setXml(String.format("repository.%s.mapper",modelName));
		autoGenerator.setPackageInfo(pc);

		// 5. 自定义模版配置
		TemplateConfig tc = new TemplateConfig();
		tc.setController("/template/controller.java.vm");
        tc.setService("/template/service.java.vm");
        tc.setServiceImpl("/template/serviceImpl.java.vm");
        tc.setEntity("/template/entity.java.vm");
        tc.setMapper("/template/mapper.java.vm");
        tc.setEntity("/template/entity.java.vm");
        tc.setRepository("/template/repository.java.vm");
        tc.setForm("/template/form.java.vm");
        tc.setVo("/template/vo.java.vm");
        tc.setXml("/template/mapper.xml.vm");

		autoGenerator.setTemplate(tc);

        // 6.自定义配置
        // 自定义属性(可无)
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                this.setMap(map);
            }
        };

        /* 自定义生成路径(可无) Mapper为自定义目录 */
//		if(null == mapperPath || "".equals(mapperPath)){
//			mapperPath = outPutDir + "\\..\\resources\\mapper";
//		}
//        mkDir(new File(mapperPath));
//
        /* 添加文件*/
//		final String mapperPath1 = mapperPath;
        List<FileOutConfig> fileOutList = new ArrayList<>();
//        fileOutList.add(new FileOutConfig("/template/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return outPutDir + "\\com\\ysten\\conts\\cps\\repository\\" +modelName+"\\mapper\\"+ tableInfo.getOriginalEntityName()+ "Mapper.xml";
//            }
//        });
//        fileOutList.add(new FileOutConfig("/template/vo.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return outPutDir + "\\com\\ysten\\conts\\cps\\controller\\"+modelName+"\\vo\\" + tableInfo.getOriginalEntityName() + "ListResp.java";
//            }
//        });
//        fileOutList.add(new FileOutConfig("/template/form.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return outPutDir + "\\com\\ysten\\conts\\cps\\controller\\" +modelName+"\\form\\"+ tableInfo.getOriginalEntityName() + "Form.java";
//            }
//        });


		injectionConfig.setFileOutConfigList(fileOutList);
		autoGenerator.setCfg(injectionConfig);

		// 5. 执行
		autoGenerator.execute();

		System.err.println("全局变量"+autoGenerator.getCfg().getMap());
	}

	// 递归创建文件夹
	public static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}
}
