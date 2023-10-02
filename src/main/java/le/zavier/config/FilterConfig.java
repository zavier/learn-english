package le.zavier.config;

import le.zavier.filter.LoginFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;

@Configuration
public class FilterConfig {

    private Logger logger = LoggerFactory.getLogger(FilterConfig.class);

    @Bean
    public Filter loginFilter() {
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setNoNeedLoginUrlStarts(Arrays.asList("/le/user", "/static"));
        logger.info("loginFilter init");
        return loginFilter;
    }
}
