package com.fayaz.taskmanagement.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ConvertWrapper {

    public <S, T> T convert(S source, Class<T> targetClass) {
        if (source == null) {
            return null;
        }

        try {
            T target = targetClass.getDeclaredConstructor().newInstance();

            BeanUtils.copyProperties(source, target);

            return target;
        } catch (Exception e) {
            throw new RuntimeException("Conversion failed", e);
        }
    }
}
