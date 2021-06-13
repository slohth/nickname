package dev.slohth.nickname;

import dev.slohth.nickname.command.Test;
import dev.slohth.nickname.database.SQLManager;
import dev.slohth.nickname.user.listener.UserListener;
import dev.slohth.nickname.user.manager.UserManager;
import dev.slohth.nickname.utils.vsc.NMSHandler;
import dev.slohth.nickname.utils.vsc.VersionControl;
import dev.slohth.nickname.utils.framework.command.Framework;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Nickname extends JavaPlugin {

    private Framework framework;
    private NMSHandler nms;

    private LuckPerms lp;

    private SQLManager sqlManager;
    private UserManager userManager;

    @Override
    public void onEnable() {
        String a = Bukkit.getServer().getClass().getPackage().getName();
        VersionControl.version = a.substring(a.lastIndexOf('.') + 1);

        this.framework = new Framework(this);
        try {
            this.nms = (NMSHandler) Class.forName("dev.slohth.nickname.utils.vsc.handlers.NMSHandler_" + VersionControl.version.substring(1)).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) { this.lp = provider.getProvider(); } else { Bukkit.getPluginManager().disablePlugin(this); }

        this.sqlManager = new SQLManager(this);
        this.userManager = new UserManager(this);
        new UserListener(this);

        new Test(this);

        switch (VersionControl.version) {
            case "v1_8_R1": {
                VersionControl.fieldName = "bF"; break;
            }
            case "v1_8_R2":
            case "v1_8_R3": {
                VersionControl.fieldName = "bH"; break;
            }
            case "v1_9_R1": {
                VersionControl.fieldName = "bR"; break;
            }
            case "v1_9_R2":
            case "v1_11_R1": {
                VersionControl.fieldName = "bS"; break;
            }
            case "v1_10_R1":
            case "v1_15_R1": {
                VersionControl.fieldName = "bT"; break;
            }
            case "v1_12_R1": {
                VersionControl.fieldName = "g"; break;
            }
            case "v1_13_R1":
            case "v1_13_R2": {
                VersionControl.fieldName = "h"; break;
            }
            case "v1_14_R1": {
                VersionControl.fieldName = "bV"; break;
            }
            case "v1_16_R1": {
                VersionControl.fieldName = "bQ"; break;
            }
            case "v1_16_R2":
            case "v1_16_R3": {
                VersionControl.fieldName = "bJ"; break;
            }
            default:
                VersionControl.fieldName = "cs";
        }
        this.lp.getGroupManager().loadAllGroups();

    }

    public LuckPerms getLp() { return this.lp;  }

    public SQLManager getSqlManager() { return this.sqlManager; }

    public UserManager getUserManager() { return this.userManager; }

    public Framework getFramework() { return this.framework; }

    public NMSHandler getNms() {
        return nms;
    }
}
