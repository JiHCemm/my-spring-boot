package com.my_springboot.generator;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * MP代码生成器
 */
public class MpGenerator {

    public static void main(String[] args) {
        String tableNames = "rbac_admin,rbac_admin_role,rbac_role,rbac_role_permission,rbac_permission";// 通过表名生成相关类,多个用英文逗号隔开
        String tablePrefix = "rbac_";// 去掉表名前缀
        String packageName = "com.my_springboot.rbac";// 自定义自己的包名，后续的代码生成会在这个包下
        String projectPath = "C:\\Users\\shanhemei\\Desktop\\MP";// 生成文件的输出目录 我一般放在桌面,避免原文件被覆盖
        new AutoGenerator()
                .setGlobalConfig(getGlobalConfig(projectPath))  //全局配置
                .setDataSource(getDataSource()) //数据源配置
                .setPackageInfo(getPackageConfig(packageName)) //包配置
                .setStrategy(getStrategyConfig(tableNames, tablePrefix))  //策略配置
                .setTemplate(getTemplateConfig())  //模板配置
                .execute();
    }

    /**
     * 全局配置
     *
     * @param projectPath 生成文件的输出目录
     */
    private static GlobalConfig getGlobalConfig(String projectPath) {
        //全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(projectPath)// 生成文件的输出目录
                .setAuthor("JiHC")// 作者
                .setOpen(true)// 是否打开输出目录 默认值:true
                .setFileOverride(true)// 是否覆蓋已有文件 默认值：false
                .setSwagger2(true)// 开启 swagger2 模式 默认false
                .setBaseColumnList(true)// 开启 baseColumnList 默认false
                .setBaseResultMap(true)// 开启 BaseResultMap 默认false
                .setIdType(IdType.ASSIGN_UUID)// 主键策略 ASSIGN_UUID:主键生成32位字符串ID
                .setDateType(DateType.ONLY_DATE)// 设置时间类型使用哪个包下的
//                .setEntityName("%s")// entity 命名方式 默认值：null 例如：%sEntity 生成 UserEntity
                .setMapperName("%sDAO")// dao 命名方式 默认值：null 例如：%sDao 生成 UserDao
                /*.setXmlName("%sMapper")// Mapper xml 命名方式   默认值：null 例如：%sDao 生成 UserDao.xml
                .setServiceName("%sService")// service 命名方式   默认值：null 例如：%sBusiness 生成 UserBusiness
                .setServiceImplName("%sServiceImpl")// service impl 命名方式  默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
                .setControllerName("%sController")*/// controller 命名方式    默认值：null 例如：%sAction 生成 UserAction
        ;
        return gc;
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig getDataSource() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(
                "jdbc:mysql://localhost:3306/my_springboot?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC"
        );
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        return dsc;
    }

    /**
     * 包配置
     *
     * @param packageName 自定义包名，后续的代码生成会在这个包下
     */
    private static PackageConfig getPackageConfig(String packageName) {
        PackageConfig pc = new PackageConfig();
        pc.setParent(packageName)
                .setController("controller")
                .setService("service")
                .setMapper("dao")
                .setEntity("pojo");
        return pc;
    }

    /**
     * 策略配置
     *
     * @param tableNames 需要生成的表名
     */
    private static StrategyConfig getStrategyConfig(String tableNames, String tablePrefix) {
        // 策略配置	数据库表配置，通过该配置，可指定需要生成哪些表或者排除哪些表
        StrategyConfig strategy = new StrategyConfig();
        strategy.setCapitalMode(false)//驼峰命名
                .setNaming(NamingStrategy.underline_to_camel)//表名生成策略(下划线转驼峰)
                .setColumnNaming(NamingStrategy.underline_to_camel)//列名生成策略(下划线转驼峰)
                .setEntityLombokModel(true)//【实体】是否为lombok模型（默认 false）
                .setRestControllerStyle(true)//生成 @RestController 控制器
                .setInclude((tableNames).split(","))//通过表名生成相关类
                .setTablePrefix(tablePrefix)//去掉表的前缀
                .setControllerMappingHyphenStyle(true);//驼峰转连字符
        //自定义继承的类全称，带包名
    /*strategy.setSuperControllerClass("com.common.BaseController");
    strategy.setSuperServiceClass((String) null);
    strategy.setSuperServiceImplClass((String) null);
    strategy.setSuperMapperClass(null);
    strategy.setSuperEntityClass("com.common.BaseEntity")
    strategy.setSuperEntityColumns("id");*/
        return strategy;
    }

    /**
     * 模板配置：velocity模板
     */
    private static TemplateConfig getTemplateConfig() {
        TemplateConfig tc = new TemplateConfig();
        //使用resource下的自定义模板，不想要生成就设置为null,如果不设置null会使用默认模板
        tc.setController("templates/controller.java.vm")
                .setService("templates/service.java")
                .setServiceImpl("templates/serviceImpl.java")
                .setEntity("templates/entity.java")
                .setMapper("templates/mapper.java")
                .setXml("templates/mapper.xml");
        return tc;
    }

}
