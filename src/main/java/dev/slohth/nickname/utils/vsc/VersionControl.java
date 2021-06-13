package dev.slohth.nickname.utils.vsc;

public class VersionControl {

    public static String version;
    public static String fieldName;

    public static Class<?> getNMSCLass(String c) {
        try {
            return Class.forName("net.minecraft.server." + version + "." + c);
        } catch (ClassNotFoundException e) { e.printStackTrace(); return null; }
    }



}
