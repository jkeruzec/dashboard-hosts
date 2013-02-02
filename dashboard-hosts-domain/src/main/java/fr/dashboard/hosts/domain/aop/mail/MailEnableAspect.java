package fr.dashboard.hosts.domain.aop.mail;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MailEnableAspect
{
    @Value("${mail.enabled}")
    private boolean mailEnabled;

    // @Around("execution(* *(..))")
    @Around("execution(* fr.dashboard.hosts.mail.service.DashboardHostsMailService.*(..))")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable
    {
        if (mailEnabled) { return joinPoint.proceed(); }
        return null;
    }

}
