package com.nave.spring356.nave.aspect;

import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class NaveAspect {

    private static final Logger logger = LoggerFactory.getLogger(NaveAspect.class);

    @Before("execution(* com.nave.spring356.nave.service.NaveService.getCarResponse(..)) && args(id)")
    public void logIfNegativeId(JoinPoint joinPoint, UUID id) {       
        if (id == null) {
            logger.warn("⚠️ Se ha solicitado una Nave con un UUID nulo");
        } else if (id.version() != 4 && id.version() != 1) {            
            logger.warn("⚠️ UUID con formato no esperado: {} (versión {})", id, id.version());
        }
    }
    
}
