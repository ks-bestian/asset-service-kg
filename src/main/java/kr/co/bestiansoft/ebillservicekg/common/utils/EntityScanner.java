package kr.co.bestiansoft.ebillservicekg.common.utils;

import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ConfigurationBuilder;

import javax.persistence.Entity;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityScanner {

    public static Set<String> getEntityClassNames(String basePackage, String excludePrefix) {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .forPackages(basePackage)
            .setScanners(Scanners.TypesAnnotated)
        );

        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(Entity.class);
        
        return entities.stream()
            .filter(entity -> !entity.getSimpleName().startsWith(excludePrefix))
            .map(Class::getName)
            .collect(Collectors.toSet());
    }
}