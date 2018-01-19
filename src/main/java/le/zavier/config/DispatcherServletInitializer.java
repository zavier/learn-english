package le.zavier.config;

import java.util.Arrays;
import javax.servlet.Filter;
import le.zavier.filter.LoginFilter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 指定 Spring 应用上下文配置类（主要配置 web 组件的 Bean，如Controller）
      */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        // AOP Controller 需配置在此处，在 RootConfig 处配置无效， 具体可见 https://segmentfault.com/q/1010000003901757
        return new Class[]{WebConfig.class, AopConfig.class};
    }

    /**
     * 相对应的另一个应用上下文配置类（应用中的其他 Bean，如 Service、Repository）
      */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{RootConfig.class};
    }

    /**
     * 将 DispatcherServlet 映射到 "/"
      */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    /**
     * filter
     * @return
     */
    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter("UTF-8",
            true);
        LoginFilter loginFilter = new LoginFilter();
        loginFilter.setNoNeedLoginUrlStarts(Arrays.asList("/user", "/static"));
        return new Filter[]{characterEncodingFilter, loginFilter};
    }
}
