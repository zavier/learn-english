package le.zavier.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DBConfig {

    private static final String URL = "jdbc:mysql://localhost:3306/learn_english??useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @Bean(initMethod="init", destroyMethod="close")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);

        // 配置 druid 监听
//        try {
//            dataSource.setFilters("stat");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

        dataSource.setMaxActive(20);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);

        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setMinEvictableIdleTimeMillis(300000);

        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);

        dataSource.setPoolPreparedStatements(false);
        dataSource.setMaxOpenPreparedStatements(20);
        return dataSource;
    }
}
