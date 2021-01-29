package com.toptal.project.inputcaloriesapis.util;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class TypeConverterRegistry {

    Map<Class, Converter> registry = new HashMap<>();

    public TypeConverterRegistry() {

        registry.put(Boolean.class, (String literal) -> Boolean.valueOf(literal));

        registry.put(boolean.class, (String literal) -> Boolean.valueOf(literal));

        registry.put(UUID.class, (String literal) -> UUID.fromString(literal));

        registry.put(Timestamp.class, (String literal) -> Timestamp.valueOf(literal));

        registry.put(double.class, (String literal) -> Double.valueOf(literal));

        registry.put(Double.class, (String literal) -> Double.valueOf(literal));

        registry.put(Float.class, (String literal) -> Float.valueOf(literal));

        registry.put(float.class, (String literal) -> Float.valueOf(literal));

        registry.put(String.class, (String literal) -> String.valueOf(literal));

        registry.put(Long.class, (String literal) -> Long.valueOf(literal));

        registry.put(long.class, (String literal) -> Long.valueOf(literal));

        registry.put(Integer.class, (String literal) -> Integer.valueOf(literal));

        registry.put(int.class, (String literal) -> Integer.valueOf(literal));

    }

    public Converter get(Class klass){
        return registry.get(klass);
    }

    public interface Converter {
        Object convert(String literal);
    }
}
