package le.zavier.configs;

import javax.sql.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@Import({AopConfig.class, DBConfig.class})
@ComponentScan(basePackages = {"le.zavier"})
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
    public SqlSessionFactoryBean sqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(new ClassPathResource("mybatis-conf.xml"));
        return sessionFactoryBean;
    }

    /**
     * 配置接口所在的包名，将其中的接口创建成 MapperFactoryBean
     * @return
     */
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();
        configurer.setBasePackage("le.zavier.**.mapper");
        // 只扫描包中有 Repository 注解的类
        configurer.setAnnotationClass(Repository.class);
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
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
}
