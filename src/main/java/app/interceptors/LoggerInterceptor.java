package app.interceptors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggerInterceptor {

    @Before("@annotation(LogInvocation)")
    public void log(JoinPoint joinPoint) {
        logProcess(joinPoint);
    }


    @AfterThrowing(value = "@annotation(LogInvocation)", throwing = "e")
    public void throwLog(JoinPoint joinPoint, Throwable e) {
        throwLogProcess(joinPoint , e);
    }

    private void logProcess(JoinPoint joinPoint) {
        System.out.println("Log on method: " + joinPoint.getSignature().getName()
                + "\n\twith args: " + Arrays.toString(joinPoint.getArgs())
                + "\n\ton Object: " + joinPoint.getTarget()
        );
    }

    private void throwLogProcess(JoinPoint joinPoint, Throwable e) {
        System.err.println("Object " + joinPoint.getTarget()
                + "\n\tthow an exception: " + e
        );
        e.printStackTrace();
    }

}
