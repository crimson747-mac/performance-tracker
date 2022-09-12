package plugin.performancetracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import plugin.performancetracker.tracker.interceptor.TrackerInterceptor;

@Configuration
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
