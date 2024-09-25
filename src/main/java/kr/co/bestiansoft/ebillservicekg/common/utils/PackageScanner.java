package kr.co.bestiansoft.ebillservicekg.common.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PackageScanner {

    public static List<String> findRepositoryPackages(String basePackage) {
        String basePath = basePackage.replace('.', '/');
        List<String> packages = new ArrayList<>();
        try {
            File directory = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(basePath)).getFile());
            findPackages(directory, basePackage, packages);
        } catch (IOException e) {
            throw new RuntimeException("Failed to scan classpath for base package: " + basePackage, e);
        }
        return packages;
    }

    private static void findPackages(File directory, String packageName, List<String> packages) throws IOException {
        if (!directory.exists()) {
            return;
        }

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.isDirectory()) {
                findPackages(file, packageName + "." + file.getName(), packages);
            } else if (file.getName().endsWith(".class") && packageName.contains("entity")) {
                packages.add(packageName);
            }
        }
    }
}