package com.baomidou.mybatisplus.generator.run;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 测试生成代码
 *
 * @author K神
 * @date 2017/12/18
 */
public class Generator {

  public static void main(String[] args) {
    String packageName = "cn.wdcode.restful.person";
//    System.out.println(SuperMapper.class.getName());
//    System.out.println(System.getProperties().toString());
//    System.out.println(System.getenv());
//    System.out.println(System.getProperty("user.dir"));
//    System.out.println(System.getenv("USER"));
//    System.out.println(System.getenv("PWD"));
    generateByTables(packageName, "person");
    //    generateByTables(packageName, "sys_menu", "sys_role", "sys_role_menus", "sys_user",
    // "sys_user_roles");
  }

  private static void generateByTables(String packageName, String... tableNames) {
    GlobalConfig globalConfig = new GlobalConfig();
    String dbUrl = "jdbc:mysql://wdcode.cn:3306/test?useSSL=false";
    DataSourceConfig dataSourceConfig = new DataSourceConfig();
    dataSourceConfig
        .setDbType(DbType.MYSQL)
        .setUrl(dbUrl)
        .setUsername("test")
        .setPassword("test")
        .setDriverName("com.mysql.jdbc.Driver");
    StrategyConfig strategyConfig = new StrategyConfig();
    strategyConfig
        .setCapitalMode(true)
        .setTablePrefix("")
        .setEntityLombokModel(true)
        .setDbColumnUnderline(true)
        .setRestControllerStyle(true)
        .setDbColumnUnderline(true)
//        .setSuperEntityClass(SuperModel.class.getName())
//        .setSuperControllerClass(SuperController.class.getName())
//        .setSuperMapperClass(SuperMapper.class.getName())
//        .setSuperServiceClass(SuperService.class.getName())
//        .setSuperServiceImplClass(SuperServiceImpl.class.getName())
        .setNaming(NamingStrategy.underline_to_camel)
        .setInclude(tableNames);
    globalConfig
        .setActiveRecord(false)
        .setEnableCache(false)
        .setBaseColumnList(true)
        .setBaseResultMap(true)
        .setServiceName("%sService")
        .setRepositoryName("%sRepository")
        .setOpen(true)
        .setAuthor(System.getenv("USER"))
        .setOutputDir(System.getenv("PWD")+"/src/main/java/")
        .setFileOverride(true);
    new AutoGenerator()
        .setGlobalConfig(globalConfig)
        .setDataSource(dataSourceConfig)
        .setStrategy(strategyConfig)
        //        .setTemplate(templateConfig)
        .setPackageInfo(
            new PackageConfig()
                .setParent(packageName)
                .setEntity("model")
                .setRepository("repository")
                .setController("controller"))
        .execute();
  }
}
