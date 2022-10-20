package plugin.performancetracker.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import plugin.performancetracker.tracker.TrackerContext;

@Aspect
@Component
@Profile("test")
public class PerformanceTrackAop{

    @Around("execution(* plugin.performancetracker.controller..*(..)) || " +
            "execution(* plugin.performancetracker.service..*(..)) || " +
            "execution(* org.springframework.data.jpa.repository.JpaRepository..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        return TrackerContext.saveFunctionCallInfo(joinPoint);
    }
}
