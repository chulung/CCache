package com.chulung.ccache.annotation;
import java.lang.annotation.*;
import java.lang.annotation.ElementType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CCache {
    int liveSeconds();
}
