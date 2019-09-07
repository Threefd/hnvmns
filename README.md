#简介
本模块为基于Spring boot 2的通用微服务模块的示例程序

# 创建工程

- 使用Spring Tool Suite创建一个Spring Starter Project,根据服务模块具体功能用途,对服务模块进行命名,Group必须为com.hngd.vmns
- 勾选依赖:Feign,Eureka Discovery,Web,MyBatis,PostgreSQL;如果项目需要其他依赖,请自行勾选;如果没有使用到数据库就不需要勾选MyBatis,PostgreSQL

- 最后生成项目的pom主要内容如下

```xml
	<groupId>com.hngd.vmns</groupId>
	<artifactId>hnvmns-java-sample</artifactId>
	<version>0.0.1</version>
	<packaging>jar</packaging>

	<name>hnvmns-java-sample</name>
	<description>java sample project for hnvmns</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.4.RELEASE</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
		<spring-cloud.version>Finchley.RELEASE</spring-cloud.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.mybatis.spring.boot</groupId>
			<artifactId>mybatis-spring-boot-starter</artifactId>
			<version>2.0.1</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>

		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<dependencyManagement>
		<dependencies>
			<dependency>
				<groupId>org.springframework.cloud</groupId>
				<artifactId>spring-cloud-dependencies</artifactId>
				<version>${spring-cloud.version}</version>
				<type>pom</type>
				<scope>import</scope>
			</dependency>
		</dependencies>
	</dependencyManagement>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
```
# 工程结构

子模块内部采用典型的三层结构dao,service,web

```shell
 ├─build-config                         //Maven构建用到的配置文件存放目录
 ├─config                               //程序用到的配置文件存放目录
 ├─doc                                  //程序相关参考文档，说明文档存放目录
 ├─data                                 //程序内置的资源数据存放目录
 ├─logs                                 //程序调试运行日志的存放目录 
 ├─package-config                       //程序打包配置文件存放目录(已弃用)
 ├─src
 │  ├─main
 │  │  ├─java
 │  │  │  └─com
 │  │  │      └─hngd
 │  │  │          ├─dao                   //人工编写的Mapper类存放包
 │  │  │          ├─dto                   //传输用到的java实体类，包括表单，返回实体
 │  │  │          ├─mapping               //人工编写的表映射XML文件
 │  │  │          ├─model                 //人工编写的实体类存放包
 │  │  │          ├─service               //业务逻辑层接口类存放包
 │  │  │          │  └─impl               //业务逻辑层实现类存放包
 │  │  │          ├─vmns
 │  │  │          │  └─sample
 │  │  │          └─web                   //面向web的接口
 │  │  │              └─controller 
 │  │  │          └─android               //面向android的接口
 │  │  │              └─controller
 │  │  │          └─ios                   //面向ios的接口
 │  │  │              └─controlle
 │  │  │          └─desktop               //面向桌面(Linux,Windows,Mac)的接口
 │  │  │              └─controller
 │  │  └─resources
 │  │      └─static
 │  │          └─js
 │  └─test
 │      └─java                            //测试代码存放根路径
 ├─target
 │  ├─generated-sources
 │  │  └─mybatis-generator
 │  │      └─com
 │  │          └─hngd
 │  │              ├─dao                   //mybatis-generator自动生成Mapper类存放目录
 │  │              ├─mapping               //mybatis-generator自动生成表映射XML文件存放目录
 │  │              └─model                 //mybatis-generator自动生成实体类类存放目录
 └─test-data                               //单元测试使用数据存放目录
```

# 工程配置

## 模块基础信息配置

1. 配置文件修改,将项目的配置文件application.properties,移动到${project.basedir}/config/application.yml

   ```shell
   cd ${project.basedir}   //cd到项目根目录
   mkdir config
   mv src/main/resources/application.properties config/application.yml
   ```

2. 配置服务模块的基本信息

   ```yaml
   #http服务端口
   server:
     port: 8080
   #spring程序基本信息配置,注意此处的spring.application.name,将默认为服务的路由根路径
   spring:
     application:
       name: sample
     datasource:
           name: sample
           url: jdbc:postgresql://192.168.0.239:5432/hnvmns6000
           username: postgres
           password: hngd5618
           type: com.alibaba.druid.pool.DruidDataSource
           driver-class-name: org.postgresql.Driver
   #mybatis配置        
   mybatis:
     mapper-locations: 
       - /com/hngd/mapping/*.xml
     type-aliases-package: com.hngd.model
   #注册中心配置  
   eureka: 
     client:
       service-url:
         defaultZone: http://192.168.0.144:12331/eureka/,http://192.168.0.144:12332/eureka/
   #如果机器存在多个IP地址,必须配置hostname,单个IP地址可以使用prefer-ip-address
     instance:
       hostname: 192.168.0.239
       #prefer-ip-address: true,如果机器只有一个IP地址配置,这种方式也可以,如果存在多个IP地址,还是采用hostname配置
       
   #log4j2日志配置
   logging: 
     config: ./config/log4j2.xml
   ```


## Maven依赖配置

### Maven仓库配置

   ```xml
   	<repositories>
   		<repository>
   			<id>hnlocal</id>
   			<name>hnlocal maven</name>
   			<url>http://192.168.0.143:8082/nexus/content/groups/public/</url>
   			<snapshots>
   				<enabled>true</enabled>
   			</snapshots>
   			<releases>
   				<enabled>true</enabled>
   			</releases>
   		</repository>
   	</repositories>
   
   	<pluginRepositories>
   		<pluginRepository>
   			<id>hnlocal</id>
   			<name>hnlocal maven</name>
   			<url>http://192.168.0.143:8082/nexus/content/groups/public/</url>
   			<snapshots>
   				<enabled>true</enabled>
   			</snapshots>
   			<releases>
   				<enabled>true</enabled>
   			</releases>
   		</pluginRepository>
	</pluginRepositories>
   ```

### 通用web依赖配置

   ```xml
   <!--web通用依赖-->
   		<dependency>
   			<groupId>com.hngd.vmns</groupId>
   			<artifactId>hnvmns-web-spring-boot-starter</artifactId>
   			<version>0.0.3-SNAPSHOT</version>
   		</dependency>
         
   ```

### Log4j2依赖配置

   ```xml
   
   <!--使用log4j2作为日志输出-->
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-web</artifactId>
   			<exclusions>
   				<exclusion>
   					<groupId>org.springframework.boot</groupId>
   					<artifactId>spring-boot-starter-logging</artifactId>
   				</exclusion>
   			</exclusions>
   		</dependency>
   		<dependency>
   			<groupId>org.springframework.boot</groupId>
   			<artifactId>spring-boot-starter-log4j2</artifactId>
   		</dependency>
       <!--日志输出到Kafka需要此依赖-->
   		<dependency>
			<groupId>org.apache.kafka</groupId>
			<artifactId>kafka-clients</artifactId>
			<version>2.0.1</version>
		</dependency>

   ```

### 单元测试覆盖率配置

```
                <plugin>
					<groupId>org.jacoco</groupId>
					<artifactId>jacoco-maven-plugin</artifactId>
					<version>0.8.4</version>
					<executions>
						<execution>
							<id>jacoco-initialize</id>
							<goals>
								<goal>prepare-agent</goal>
							</goals>
						</execution>
						<execution>
							<id>jacoco-site</id>
							<phase>test</phase>
							<goals>
								<goal>report</goal>
							</goals>
						</execution>
					</executions>
				</plugin>
```


# 代码生成

## mybatis代码生成

### 配置Maven插件

```xml
<plugin>
				<groupId>org.mybatis.generator</groupId>
				<artifactId>mybatis-generator-maven-plugin</artifactId>
				<version>1.3.7</version>
				<dependencies>
					<dependency>
						<groupId>org.postgresql</groupId>
						<artifactId>postgresql</artifactId>
						<version>42.2.5</version>
					</dependency>
					<dependency>
						<groupId>com.hngd.tool</groupId>
						<artifactId>mybatis-generator-ext</artifactId>
						<version>0.0.2-SNAPSHOT</version>
					</dependency>
				</dependencies>
				<configuration>
					<overwrite>true</overwrite>
				</configuration>
				<executions>
					<execution>
						<id>generate</id>
						<goals>
							<goal>generate</goal>
						</goals>
						<phase>generate-sources</phase>
					</execution>
				</executions>
			</plugin>
```

插件依赖mybatis-generator-ext用于生成实体类注释(从数据库提取),字段验证注解

### 编写生成配置文件

文件默认在 ${project.basedir}/src/main/resources/

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN" "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>

	<context id="DB2Tables" targetRuntime="MyBatis3">
		<property name="javaFileEncoding" value="UTF-8" />
        <!--配置和这个插件是为了,多次运行代码生成后,xml会被覆盖,而不是merge-->
        <plugin type="org.mybatis.generator.plugins.UnmergeableXmlMappersPlugin" />
		<commentGenerator
			type="com.hngd.tool.mge.MyCommentGenerator">
			<property name="suppressAllComments" value="false" />
			<property name="addRemarkComments" value="true" />
			<property name="suppressDate" value="true" />
		</commentGenerator>
		<!-- oracle URL、username、password -->
		<jdbcConnection driverClass="org.postgresql.Driver"
			connectionURL="jdbc:postgresql://192.168.0.142:5432/hnvmns-base-data"
			userId="postgres" password="Admin12345">
		</jdbcConnection>
		<javaTypeResolver>
			<property name="forceBigDecimals" value="false" />
		</javaTypeResolver>
		<!-- model package and location -->
		<javaModelGenerator targetPackage="com.hngd.model"
			targetProject="MAVEN">
			<property name="enableSubPackages" value="true" />
			<property name="trimStrings" value="true" />
			<property name="javaFileEncoding" value="UTF-8" />
		</javaModelGenerator>
		<!-- mapping package and location -->
		<sqlMapGenerator targetPackage="com.hngd.mapping"
			targetProject="MAVEN">
			<property name="enableSubPackages" value="true" />
			<property name="javaFileEncoding" value="UTF-8" />
		</sqlMapGenerator>
		<!-- dao package and location -->
		<javaClientGenerator type="XMLMAPPER"
			targetPackage="com.hngd.dao" targetProject="MAVEN">
			<property name="enableSubPackages" value="true" />
			<property name="javaFileEncoding" value="UTF-8" />
		</javaClientGenerator>
		<!-- table name, change tableName and domainObjectName -->
		<table schema="system_config" tableName="tb_role" domainObjectName="Role"
			enableCountByExample="true" enableSelectByExample="true"
			selectByExampleQueryId="true" />

	</context>
</generatorConfiguration>
```

### 运行生成命令

```shell
cd ${project.basedir}   //cd到项目根目录
mvn generate-sources
```

可以在${project.basedir}/target/generated-sources/mybatis-generator目录下,看到生成的代码.

注意:如果是eclipse,需要手动将此目录加入到source folders on build path中.

## 前端代码&接口文档生成

### 配置生成插件

```xml
<plugin>
				<groupId>com.hngd.tool</groupId>
				<artifactId>codegen-maven-plugin</artifactId>
				<version>0.0.9-SNAPSHOT</version>
				<configuration>
				    <!--  模块根请求路径,如果是请求是从网关转发到此模块,则需要配置为'/'+模块名称,本机测试配置为'/'-->
					<serviceUrl>/sample</serviceUrl>
					<!-- 配置生成js 客户端代码的类型,目前支持ajax与axios -->
					<type>axios</type>
					<!-- <outputDirectory>${project.basedir}/src/main/resources/static/js</outputDirectory> -->
					<!-- 配置接口类所在包名称 -->
					<packageFilter>com.hngd.web.controller</packageFilter>
					<!-- 配置生成Java客户端代码的包名 -->
					<apiPackage>com.hngd.web.api</apiPackage>
					<!-- 配置生成java客户端代码的同步or异步调用,支持async,sync -->
					<invokeType>async</invokeType>
					<!-- 配置生成Swagger文档的基础信息配置文件所在位置 -->
					<confFilePath>${project.basedir}/build-config/swagger-config.json</confFilePath>
					<!--Swagger UI 服务地址,配置后接口文档将自动上传到该服务-->
                    <swaggerUIServer>192.168.0.140:8888</swaggerUIServer>
				</configuration>
			</plugin>
```

### 编写配置文件

位置在${project.basedir}/build-config/swagger-config.json

```json
{
  "servers": [
    {
      "url": "https://192.168.0.144:8080/sample",
      "description": "144网关"
    },
     {
      "url": "https://localhost:8080",
      "description": "本地测试"
    }
  ],
  "info": {
    "title": "Java示例模块接口文档",
    "description": "更新时间:2019/06/13 09:48:45",
    "termsOfService": "api",
    "contact": {
      "name": "谭奇栋",
      "url": "http://192.168.0.239/web",
      "email": "903843602@qq.com"
    },
    "license": {
      "name": "参考资料",
      "url": "http://192.168.0.239/hndoc/"
    },
    "version": "V1.0.1"
  }
}
```

### 生成代码

```shell
cd ${project.basedir}   //cd到项目根目录
mvn package -DskipTests //打包生成jar文件
mvn codegen:api-js      //生成js代码
mvn codegen:api-swagger //生成swagger文档json文件,配置swaggerUIServer后,接口文档将自动上传到该SwaggerUI服务
```

生成代码在${project.basedir}/target/api目录下.

# 程序打包

## Windows NT服务打包

1. 配置打包插件

   ```xml
   <!-- windows下应用程序打包依赖此插件的依赖包复制功能 -->
   			<plugin>
   				<groupId>org.apache.maven.plugins</groupId>
   				<artifactId>maven-dependency-plugin</artifactId>
   				<configuration>
   					<outputDirectory>${project.build.directory}/libs</outputDirectory>
   				</configuration>
   				<executions>
   					<execution>
   						<id>copy-dependencies</id>
   						<phase>prepare-package</phase>
   						<goals>
   							<goal>copy-dependencies</goal>
   						</goals>
   					</execution>
   				</executions>
   			</plugin>
       <plugin>
   				<groupId>com.hngd.tool</groupId>
   				<artifactId>winpackage-maven-plugin</artifactId>
   				<version>0.0.4-SNAPSHOT</version>
   				<configuration>
   					<scriptConfigFile>${basedir}/package-config/package.properties</scriptConfigFile>
   					<outputDirectory>${project.build.directory}/hnvmns-java-sample</outputDirectory>
   					<dependencyDirectory>${project.build.directory}/libs</dependencyDirectory>
   					<configAndDataDirectories>
   						<param>${basedir}/config</param>
   					</configAndDataDirectories>
   				</configuration>
   				<executions>
   					<execution>
   						<goals>
   							<goal>win-package</goal>
   						</goals>
   						<phase>package</phase>
   					</execution>
   				</executions>
   			</plugin>
   ```
   
2. 编写打包配置文件

   ```properties
   #服务名称
   serviceName=hnvmns-java-sample  
   #服务显示名称
   serviceDisplayName=hnvmns-java-sample          
   #服务描述
   serviceDescription=hnvmns java sample service
   #Java程序入口类
   mainClass=com.hngd.vmns.sample.NtServiceMain
   #服务启用时调用函数
   startMethod=onStart
   #服务停止时调用函数
   stopMethod=onStop
   #是否生成服务相关脚本(安装,启动,停止,卸载)
   supportService=true
   #java堆内存最小值
   jvmMs=512m
   #java堆内存最大值
   jvmMx=1024m
   ```

3. 执行打包

   ```shell
   cd ${project.basedir}   //cd到项目根目录
   mvn package -Dmaven.test.skip=true
   ```

4. 打包后文件结构

   ```
   .
   ├── config                          //程序运行依赖配置目录
   ├── hnvmns-java-sample-0.0.1.jar    //主Jar包
   ├── install.bat                     //NT服务安装脚本
   ├── jre                             //java运行时
   ├── libs                            //Java库依赖
   ├── prunsrv.exe                     //依赖Apache Daemon可执行文件
   ├── run.bat                         //控制台启动程序脚本
   ├── start.bat                       //启动NT服务脚本
   ├── stop.bat                        //停止NT服务脚本
   └── uninstall.bat                   //卸载NT服务脚本
   ```

## Docker镜像构建

1. 添加Maven属性配置

   ```xml
   <docker.image.prefix>hnvmns</docker.image.prefix>
   <maven.build.timestamp.format>yyyyMMddHHmmss</maven.build.timestamp.format>
   ```

2. 配置maven插件

```xml
			<plugin>
				<groupId>com.google.cloud.tools</groupId>
				<artifactId>jib-maven-plugin</artifactId>
				<version>1.3.0</version>
				<configuration>
					<to>
						<image>192.168.0.140:8082/${docker.image.prefix}/${project.artifactId}</image>
						<auth>
							<username>deployer</username>
							<password>hngd5618</password>
						</auth>
						<tags>
							<tag>${maven.build.timestamp}</tag>
							<tag>${project.version}</tag>
						</tags>
					</to>
					<from>
						<image>192.168.0.140:8084/anapsix/alpine-java:8u192b12_server-jre</image>
						<auth>
							<username>deployer</username>
							<password>hngd5618</password>
						</auth>
					</from>
					<container>
					    <!-- 如果工程中有多个入口类,则需要指定此配置项 -->
					    <mainClass>com.hngd.vmns.sample.HnvmnsJavaSampleApplication</mainClass>
					</container>
					<allowInsecureRegistries>true</allowInsecureRegistries>
				</configuration>
			</plugin>
```

3. 复制程序配置文件

   对于需要被打包到Docker镜像中的的配置文件,资源文件,可以复制到${project.basedir}/src/main/jib目录下.

4. 运行构建命令

   ```shell
   cd ${project.basedir}   //cd到项目根目录
   mvn jib:build -DsendCredentialsOverHttp=true 
   ```

# 参考

1. [Docker打包jib-maven-plugin](<https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin>)