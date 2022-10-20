package plugin.performancetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Profile("test")
@Configuration()
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public TrackerInterceptor trackerInterceptor() {
        return new TrackerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(trackerInterceptor())
                .order(1)
                .addPathPatterns("/**");
    }
}
