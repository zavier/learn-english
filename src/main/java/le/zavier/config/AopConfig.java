package le.zavier.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // 启用 AspectJ 自动代理
@ComponentScan(basePackages = "le.zavier")
public class AopConfig {

}
