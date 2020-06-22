package com.ethan.zgen;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.*;

/**
 * Created by ASUS on 2020/1/17.
 */
public class MybatisPlusGenerator {
    private static String packageName = "src/main";          //初始文件路径
    private static String customPath = "zgencode";            //自定义路径
    private static String authorName = "Ethan";              //作者
    private static String table = "sys_log";            //table名字
    private static String prefix = "sys_";                   //table前缀
    private static File file = new File(packageName);
    private static String path = file.getAbsolutePath();

    // 执行 main 方法自动生成代码到项目目录中
    public static void main(String[] args) {
        System.out.println("绝对路径" + path);

        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator generator = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig()
                .setOutputDir(path + "/java")//输出目录
                .setFileOverride(false)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setOpen(false)//生成后打开文件夹
                .setAuthor(authorName)
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setControllerName("%sController");
        generator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("mysql94")
                .setUrl("jdbc:mysql://localhost:3306/ryds?useSSL=true&serverTimezone=GMT%2B8&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8");
        generator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig()
                //.setModuleName("User")
                .setParent("com.ethan." + customPath)// 自定义包路径
                .setController("controller")// 这里是控制器包名，默认 web
                .setEntity("entity")
                .setMapper("dao")
                .setService("service")
                .setServiceImpl("service.impl");
        //.setXml("mapper")
        generator.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig()
                //.setTablePrefix(new String[]{prefix})// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(new String[]{table}) // 需要生成的表
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
                .setTableFillList(tableFillList);
        //.setExclude(new String[]{"test"}) // 排除生成的表
        // 自定义实体父类
        // .setSuperEntityClass("com.baomidou.demo.TestEntity")
        // 自定义实体，公共字段
        //.setSuperEntityColumns(new String[]{"test_id"})
        // 自定义 mapper 父类
        // .setSuperMapperClass("com.baomidou.demo.TestMapper")
        // 自定义 service 父类
        // .setSuperServiceClass("com.baomidou.demo.TestService")
        // 自定义 service 实现类父类
        // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
        // 自定义 controller 父类
        //.setSuperControllerClass("com.ethan"+packageName+".controller.AbstractController")
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // .setEntityColumnConstant(true)
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // .setEntityBuilderModel(true)
        // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
        // Boolean类型字段是否移除is前缀处理
        // .setEntityBooleanColumnRemoveIsPrefix(true)
        // .setControllerMappingHyphenStyle(true)
        // .setCapitalMode(true)// 全局大写命名
        //.setDbColumnUnderline(true)//全局下划线命名
        generator.setStrategy(strategy);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig("/templates/mapper.xml.vm") {
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
                return path + "/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        }));
        generator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig()
                // 关闭默认 xml 生成，调整生成 至 根目录
                .setXml(null);
        // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
        // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
        // .setController("...");
        // .setEntity("...");
        // .setMapper("...");
        // .setXml("...");
        // .setService("...");
        // .setServiceImpl("...");
        generator.setTemplate(templateConfig);

        // 执行生成
        generator.execute();

        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(generator.getCfg().getMap().get("abc"));
    }
}
