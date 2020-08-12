### code-generate

  code-generate 是快速基础增删改查代码，使用 `thymeleaf` 作为模板生成 

#### 项目结构

├── java
│   └── com
│       └── 9h
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
    ├── orm
    └── templates



#### demo

```java
GenerateConfig config = new GenerateConfig();
config.setTableName("banners");
config.setOperType(OperType.CDUR);
config.setPackageName("com.9h.codegenerate.test");
CodeGenerate codeGenerate = CodeGenerate.getInstance(config);
codeGenerate.config(config).entity().dao().service().controller();
```

#### 主要思路

1.  通过配置生成代码 CRUD，可选类型参考 `OperType.java`

2.  通过表名，查找表字段信息 `show full columns from %s`

3.  根据 jdbc类型映射成java类型

4.  生成实体类、dao、mapper、service、impl、controller

5.  导入所需依赖包

#### 需要注意的地方

如在当前项目使用的话，依赖spring，如单独使用，需要配置 数据库信息，生成代码后，可能需要刷新一下目录才可以看到。




