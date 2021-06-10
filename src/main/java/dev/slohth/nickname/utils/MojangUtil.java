package dev.slohth.nickname.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.Bukkit;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class MojangUtil {

    private static Map<String, List<String>> cache = new HashMap<>();

    public static boolean isValidProfile(String name) {

        if (cache.containsKey(name.toLowerCase())) return cache.get(name.toLowerCase()) != null;

        UUID uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
        String sUrl = "https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString() + "?unsigned=false";

        try {

            URLConnection req = new URL(sUrl).openConnection();
            req.connect();

            InputStreamReader reader = new InputStreamReader((InputStream) req.getContent());
            JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
            JsonArray array = json.get("properties").getAsJsonArray();

            List<String> data = new ArrayList<>();

            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                if (obj.get("name") != null && obj.get("name").getAsString().equals("textures")) {
                    data.add(obj.get("value").getAsString()); data.add(obj.get("signature").getAsString());
                }
            }

            cache.put(name.toLowerCase(), data);
            return true;

        } catch (Exception e) { cache.put(name.toLowerCase(), null); return false; }

    }

    public static String[] getData(String name) {
        return new String[] { cache.get(name.toLowerCase()).get(0), cache.get(name.toLowerCase()).get(1) };
    }

}
