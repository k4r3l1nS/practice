package com.practice.demo.custom_annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Check if entity DTO is correct via AOP
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DtoCorrectnessCheck {

    /**
     * Whether must DTO be filled in or not
     *
     * @return corresponding boolean value
     */
    boolean filled() default false;
}
