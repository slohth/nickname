package dev.slohth.nickname.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.List;

public class ColoredString {

    public static List<ChatColor> getColors(String str) {
        //let say str = "§ahello"  which is green color, but the § sign is invisible

        List<ChatColor> list = new ArrayList<>();

        //loop through the text
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == ChatColor.COLOR_CHAR) {
                char id = str.charAt(i + 1);//getting the color code id, &a is green, &b is aqua ....
                list.add(ChatColor.getByChar(id));
            }
        }
        return list;
    }

    public static Color getColor(ChatColor chatColor) {
        switch (chatColor) {
            case AQUA:
                return Color.AQUA;
            case BLACK:
                return Color.BLACK;
            case BLUE:
                return Color.BLUE;
            case DARK_AQUA:
                return Color.TEAL;
            case DARK_BLUE:
                return Color.NAVY;
            case DARK_GRAY:
                return Color.SILVER;
            case GRAY:
                return Color.GRAY;
            case DARK_GREEN:
                return Color.GREEN;
            case GREEN:
                return Color.LIME;
            case DARK_PURPLE:
                return Color.PURPLE;
            case LIGHT_PURPLE:
                return Color.FUCHSIA;
            case DARK_RED:
            case RED:
                return Color.RED;
            case GOLD:
                return Color.ORANGE;
            case YELLOW:
                return Color.YELLOW;
            default:
                return Color.WHITE;
        }

    }
}
