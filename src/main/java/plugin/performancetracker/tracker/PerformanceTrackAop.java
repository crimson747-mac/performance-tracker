package plugin.performancetracker.tracker;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceTrackAop {

    @Around("execution(* plugin.performancetracker.controller..*(..)) || " +
            "execution(* plugin.performancetracker.service..*(..)) || " +
            "execution(* org.springframework.data.jpa.repository.JpaRepository..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        ThreadLocal<PerformanceTracker> threadLocal = TrackerContext.THREAD_LOCAL;
        PerformanceTracker tracker = threadLocal.get();
        if(tracker == null) {
            return joinPoint.proceed();
        } {

            long start = System.currentTimeMillis();
            try {
                return joinPoint.proceed();
            } finally {
                TrackData trackData = new TrackData(getClassWithMethodName(joinPoint));
                trackData.setConsumeMillis(System.currentTimeMillis() - start);
                tracker.addTraceData(trackData);
            }
        }
    }

    private String getClassWithMethodName(ProceedingJoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();

        return sb
                .append(getClassName(joinPoint))
                .append(".")
                .append(getMethodName(joinPoint))
                .toString();
    }

    private String getClassName(ProceedingJoinPoint joinPoint) {
        return joinPoint.getTarget().getClass().getSimpleName();
    }

    private String getMethodName(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod().getName();
    }
}
