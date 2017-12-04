package le.zavier.config;

import com.github.pagehelper.PageInterceptor;
import java.io.IOException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import({DBConfig.class})
@ComponentScan(basePackages = {"le.zavier"}, excludeFilters = {
    @Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class),
    @Filter(type = FilterType.ANNOTATION, value = Controller.class)})
@EnableTransactionManagement
public class RootConfig {
    /**
     * <p>配置用于生成 SqlSession 的 SqlSessionFactoryBean</p>
     * <p>可以直接在此设置 SqlSessionFactory 需要的属性(比较简单,也只能设置简单的属性)</p>
     * <p>也可以通过 setConfigLocation 设置 mybatis 配置文件的位置，在其中设置需要的属性(可以在配置文件中配置复杂属性)</p>
     * <p>也可以结合使用，在此处配置一部分，配置文件中配置一部分</p>
     * @param dataSource 数据源
     * @return
     */
    @Bean
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        //
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mappers/*.xml"));
        // 添加 PageHelper 插件
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", "mysql");
        pageInterceptor.setProperties(properties);
        sessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor});
//        sessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-conf.xml"));
        return sessionFactoryBean;
    }

    /**
     * 配置接口所在的包名，将其中的接口创建成 MapperFactoryBean
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("le.zavier.dao");
        // 只扫描包中有 Repository 注解的类
//        configurer.setAnnotationClass(Repository.class);
        return configurer;
    }

    /**
     * 事务管理
     * @param dataSource
     * @return
     */
    @Bean
    public DataSourceTransactionManager txManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    /**
     * 文件上传
     * @return
     * @throws IOException
     */
    @Bean
    public MultipartResolver multipartResolver() throws IOException {
        // 使用 CommonsMultipartResolver 解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        // 文件临时保存目录，非必须，默认为 Servlet 容器的临时目录
//        multipartResolver.setUploadTempDir(new FileSystemResource("aa/bb"));
        multipartResolver.setMaxUploadSize(2048000); // 上传文件的大小上限
        multipartResolver.setMaxInMemorySize(0); // 当文件达到这个设置大小时，将写入到临时文件路径中
        return multipartResolver;
    }
}
