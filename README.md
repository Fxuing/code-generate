### code-generate

  code-generate 可以快速生成基础增删改查代码，使用 `thymeleaf` 作为模板引擎。 

#### 项目结构

```
├── java
│   └── com
│       └── fxuing
│           └── codegenerate
│               ├── constant
│               ├── core
│               │   └── config
│               ├── model
│               │   └── mapper
│               ├── service
│               │   └── impl
│               └── utils
└── resources
    └── templates
```



#### demo

```java
public static void main(String[] args) throws SQLException {
    GenerateConfig config = GenerateConfig.getInstance();
    config.setUrl("jdbc:mysql://127.0.0.1:3306/fop_new_data?useUnicode=true&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&allowMultiQueries=true");
    config.setUsername("db_username");
    config.setPassword("db_password");
    config.setTables("third_part_login");
    config.setOperType(OperType.CDUR);
    config.setPackageName("com.fxuing.codegenerate.abcde");
    config.setOutputType(GenerateConfig.FILE);
    config.setGenerateType(GenerateConfig.MYBATIS_PLUS);
    CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
    codeGenerate.config(config).entity().dao().service();
}
```

#### 主要思路

1. 通过配置生成代码 CRUD，可选类型参考 `OperType.java`

2. 通过表名，查找表字段信息 `show full columns from %s`

3. 根据 jdbc类型映射成java类型

4. 生成实体类、dao、mapper、service、impl、controller

5. 导入所需依赖包

#### 需要注意的地方

生成代码后， 可能需要刷新一下目录才可以看到。

##### 其他项目使用

下载源码， install 到本地仓库

```xml
<dependency>
    <groupId>com.fxuing</groupId>
    <artifactId>code-generate</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```