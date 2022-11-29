package app.log;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Log4j2
public class LoggerInterceptor {

    @Before("@annotation(Logger)")
    public void log(JoinPoint joinPoint) {
        logProcess(joinPoint);
    }


    @AfterThrowing(value = "@annotation(Logger)", throwing = "e")
    public void throwLog(JoinPoint joinPoint, Throwable e) {
        throwLogProcess(joinPoint, e);
    }

    private void logProcess(JoinPoint joinPoint) {
        log.info("Log on method: " + joinPoint.getSignature().getName()
                + "\n\twith args: " + Arrays.toString(joinPoint.getArgs())
                + "\n\ton Object: " + joinPoint.getTarget()
        );
    }

    private void throwLogProcess(JoinPoint joinPoint, Throwable e) {
        log.error("Object " + joinPoint.getTarget()
                + "\n\tthow an exception: " + e
        );
    }

}
