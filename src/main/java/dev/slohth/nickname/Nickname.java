package dev.slohth.nickname;

import dev.slohth.nickname.database.SQLManager;
import dev.slohth.nickname.user.listener.UserListener;
import dev.slohth.nickname.user.manager.UserManager;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nickname extends JavaPlugin {

    private String fieldName = "";
    private String version = "";
    private LuckPerms lp;

    private SQLManager sqlManager;
    private UserManager userManager;

    @Override
    public void onEnable() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) { this.lp = provider.getProvider(); } else { Bukkit.getPluginManager().disablePlugin(this); }

        this.sqlManager = new SQLManager(this);
        this.userManager = new UserManager(this);
        new UserListener(this);

        String a = Bukkit.getServer().getClass().getPackage().getName();
        this.version = a.substring(a.lastIndexOf('.') + 1);

        switch (this.version) {
            case "v1_8_R1": {
                this.fieldName = "bF"; break;
            }
            case "v1_8_R2":
            case "v1_8_R3": {
                this.fieldName = "bH"; break;
            }
            case "v1_9_R1": {
                this.fieldName = "bR"; break;
            }
            case "v1_9_R2":
            case "v1_11_R1": {
                this.fieldName = "bS"; break;
            }
            case "v1_10_R1":
            case "v1_15_R1": {
                this.fieldName = "bT"; break;
            }
            case "v1_12_R1": {
                this.fieldName = "g"; break;
            }
            case "v1_13_R1":
            case "v1_13_R2": {
                this.fieldName = "h"; break;
            }
            case "v1_14_R1": {
                this.fieldName = "bV"; break;
            }
            case "v1_16_R1": {
                this.fieldName = "bQ"; break;
            }
            case "v1_16_R2":
            case "v1_16_R3": {
                this.fieldName = "bJ"; break;
            }

        }
        this.lp.getGroupManager().loadAllGroups();

    }

    public String getFieldName() { return this.fieldName; }

    public LuckPerms getLp() { return this.lp;  }

    public SQLManager getSqlManager() { return this.sqlManager; }

    public UserManager getUserManager() { return this.userManager; }

}
