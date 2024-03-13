package biz.picosoft.demo.config.logging;

import biz.picosoft.demo.client.kernel.intercomm.StringResourceService;
import biz.picosoft.demo.client.kernel.model.global.CurrentUser;
import biz.picosoft.demo.config.logging.domain.AopLogging;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import tech.jhipster.config.JHipsterConstants;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Aspect for logging execution of service and repository Spring components.
 *
 * By default, it only runs with the "dev" profile.
 */
@Component
@Aspect
@ComponentScan("biz.picosoft.demo.client.kernel.model.global")
public class LoggingAspect {

    private final Environment env;

    @Value("${enableAop}")
    private Boolean startEnableAop;


    private final CurrentUser currentUser;
    private final StringResourceService stringResourceService;

    public static Boolean enableAop = null;
    public static LinkedList<AopLogging> aopLoggings = new LinkedList<>();

    @Inject
    public LoggingAspect(Environment env, CurrentUser currentUser, StringResourceService stringResourceService) {
        this.env = env;
        this.currentUser = currentUser;
        this.stringResourceService = stringResourceService;
    }

    /**
     * Pointcut that matches all repositories, services and Web REST endpoints.
     */
    @Pointcut(
            "within(@org.springframework.stereotype.Repository *)" +
                    " || within(@org.springframework.stereotype.Service *)" +
                    " || within(@org.springframework.web.bind.annotation.RestController *)"
    )
    public void springBeanPointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    /**
     * Pointcut that matches all Spring beans in the application's main packages.
     */
    @Pointcut(
            "within(biz.picosoft.demo.repository..*)" +
                    " || within(biz.picosoft.demo.service..*)" +
                    " || within(biz.picosoft.demo.controller..*)"
    )
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Pointcut("@annotation(biz.picosoft.demo.config.PSTranslate)")
    public void annotatedMethod() {
    }

    @Around("annotatedMethod()")
    public Object afterAnnotatedMethodExecution(ProceedingJoinPoint pointJoint) throws Throwable {
        // Logique à exécuter après chaque méthode annotée avec @MyAnnotation
        ResponseEntity originalResponse = (ResponseEntity) pointJoint.proceed();

        if (pointJoint.proceed() instanceof ResponseEntity) {
            Object result = stringResourceService.stringRessourceTrd(originalResponse.getBody());

            ResponseEntity<?> modifiedResponse = new ResponseEntity<>(result, originalResponse.getHeaders(), originalResponse.getStatusCode());
            return modifiedResponse;
        } else return stringResourceService.stringRessourceTrd(originalResponse);
    }

    /**
     * Retrieves the {@link Logger} associated to the given {@link JoinPoint}.
     *
     * @param joinPoint join point we want the logger for.
     * @return {@link Logger} associated to the given {@link JoinPoint}.
     */
    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * Advice that logs methods throwing exceptions.
     *
     * @param joinPoint join point for advice.
     * @param e exception.
     */
    @AfterThrowing(pointcut = "applicationPackagePointcut() && springBeanPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        if (env.acceptsProfiles(Profiles.of(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT))) {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = '{}' and exception = '{}'",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL",
                            e.getMessage(),
                            e
                    );
        } else {
            logger(joinPoint)
                    .error(
                            "Exception in {}() with cause = {}",
                            joinPoint.getSignature().getName(),
                            e.getCause() != null ? e.getCause() : "NULL"
                    );
        }
    }

    /**
     * Advice that logs when a method is entered and exited.
     *
     * @param joinPoint join point for advice.
     * @return result.
     * @throws Throwable throws {@link IllegalArgumentException}.
     */
    @Around("applicationPackagePointcut() && springBeanPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);
        if (log.isDebugEnabled()) {
            log.debug("Enter: {}() with argument[s] = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {}() with result = {}", joinPoint.getSignature().getName(), result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal argument: {} in {}()", Arrays.toString(joinPoint.getArgs()), joinPoint.getSignature().getName());
            throw e;
        }
    }


    @Around(value = "execution(* biz.picosoft.demo.controller.*.*(..))")
    public Object logAroundExecutorController(ProceedingJoinPoint pointJoint) throws Throwable {
        Object responseEntity = null;
        responseEntity = pointJoint.proceed();

        if (enableAop == null)
            enableAop = startEnableAop;
        if (enableAop) {
            AopLogging aopLogging = new AopLogging();
            Boolean loggingLevel = false;
            long timeInitial = System.currentTimeMillis();
            long elapsedTime = System.currentTimeMillis() - timeInitial;
            try {
                Class<?> act = Class.forName(pointJoint.getSignature().getDeclaringType().getName());
                Logger l = LoggerFactory.getLogger(act);
                if (l.isDebugEnabled())
                    loggingLevel = true;
            } catch (Exception e) {
            }
            if (loggingLevel) {

                try {
                    aopLogging.setDuration((System.currentTimeMillis() - timeInitial));
                    aopLogging.setMethodName(pointJoint.getSignature().getName());
                    aopLogging.setMicroserviceName(pointJoint.getSignature().getDeclaringType().getPackage().getImplementationTitle());
                    aopLogging.setPackageTitle(pointJoint.getSignature().getDeclaringType().getSimpleName());
                    aopLogging.setUniteName(pointJoint.toString());
                    aopLogging.setPackageFullName(pointJoint.getSignature().getDeclaringType().getPackageName());
                    aopLogging.setUuid(currentUser.getUuid());
                    aopLogging.setUserName(currentUser.getDisplayName());
                    aopLoggings.offer(aopLogging);

                } catch (Exception e) {
                    aopLogging.setDuration((System.currentTimeMillis() - timeInitial));
                    aopLogging.setMethodName(pointJoint.getSignature().getName());
                    aopLogging.setMicroserviceName(pointJoint.getSignature().getDeclaringType().getPackage().getImplementationTitle());
                    aopLogging.setPackageTitle(pointJoint.getSignature().getDeclaringType().getSimpleName());
                    aopLogging.setUniteName(pointJoint.toString());
                    aopLogging.setPackageFullName(pointJoint.getSignature().getDeclaringType().getPackageName());
                    aopLogging.setUserName("sys");
                    aopLoggings.offer(aopLogging);

                }
            }
        }
        return responseEntity;
    }


    @Around(value = "execution(* biz.picosoft.demo.service.*.*(..))")
    public Object logAroundExecutorService(ProceedingJoinPoint pointJoint) throws Throwable {
        Object responseEntity = null;
        responseEntity = pointJoint.proceed();

        if (enableAop == null)
            enableAop = startEnableAop;
        if (enableAop) {
            AopLogging aopLogging = new AopLogging();
            Boolean loggingLevel = false;
            long timeInitial = System.currentTimeMillis();
            long elapsedTime = System.currentTimeMillis() - timeInitial;
            try {
                Class<?> act = Class.forName(pointJoint.getSignature().getDeclaringType().getName());
                Logger l = LoggerFactory.getLogger(act);
                if (l.isDebugEnabled())
                    loggingLevel = true;
            } catch (Exception e) {
            }
            if (loggingLevel) {

                try {
                    aopLogging.setDuration((System.currentTimeMillis() - timeInitial));
                    aopLogging.setMethodName(pointJoint.getSignature().getName());
                    aopLogging.setMicroserviceName(pointJoint.getSignature().getDeclaringType().getPackage().getImplementationTitle());
                    aopLogging.setPackageTitle(pointJoint.getSignature().getDeclaringType().getSimpleName());
                    aopLogging.setUniteName(pointJoint.toString());
                    aopLogging.setPackageFullName(pointJoint.getSignature().getDeclaringType().getPackageName());
                    aopLogging.setUuid(currentUser.getUuid());
                    aopLogging.setUserName(currentUser.getDisplayName());
                    aopLoggings.offer(aopLogging);

                } catch (Exception e) {
                    aopLogging.setDuration((System.currentTimeMillis() - timeInitial));
                    aopLogging.setMethodName(pointJoint.getSignature().getName());
                    aopLogging.setMicroserviceName(pointJoint.getSignature().getDeclaringType().getPackage().getImplementationTitle());
                    aopLogging.setPackageTitle(pointJoint.getSignature().getDeclaringType().getSimpleName());
                    aopLogging.setUniteName(pointJoint.toString());
                    aopLogging.setPackageFullName(pointJoint.getSignature().getDeclaringType().getPackageName());
                    aopLogging.setUserName("sys");
                    aopLoggings.offer(aopLogging);

                }
            }
        }
        return responseEntity;

    }

    public void enableAop() {
        enableAop = true;
    }

    public void disableAop() {
        enableAop = false;
    }

    public static Boolean getEnableAop() {
        return enableAop;
    }

    public static void setEnableAop(Boolean enableAop) {
        LoggingAspect.enableAop = enableAop;
    }


}
