package me.deecaad.compatibility;

import me.deecaad.core.utils.ReflectionUtil;
import org.bukkit.Bukkit;

import javax.annotation.Nullable;

public class CompatibilitySetup {

    /**
     * Example return values:
     * <pre>
     * v1_8_R2
     * v1_11_R1
     * v1_13_R3
     * </pre>
     *
     * @return the server version as string
     */
    public String getVersionAsString() {
        try {
            return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param interfaceClazz the compatibility interface type
     * @param directory      the directory in code where compatibility should exist
     * @return the compatible version from given directory
     */
    @Nullable
    public <T> T getCompatibleVersion(Class<T> interfaceClazz, String directory) {
        String version = getVersionAsString();
        try {
            Class<?> compatibilityClass = Class.forName(directory + "." + version);
            Object compatibility = ReflectionUtil.newInstance(ReflectionUtil.getConstructor(compatibilityClass));
            return compatibility != null ? interfaceClazz.cast(compatibility) : getReflectionVersion(directory, interfaceClazz);
        } catch (ClassNotFoundException | ClassCastException e) {
            // Do nothing
        }
        return getReflectionVersion(directory, interfaceClazz);
    }

    private <T> T getReflectionVersion(String directory, Class<T> interfaceClazz) {
        try {
            Class<?> reflectionCompatibilityClass = Class.forName(directory + ".Reflection");
            Object reflectionCompatibility = ReflectionUtil.newInstance(ReflectionUtil.getConstructor(reflectionCompatibilityClass));
            if (reflectionCompatibility == null) return null;
            return interfaceClazz.cast(reflectionCompatibility);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}