package basic.tp;

import basic.tp.command.*;
import basic.tp.data.RequestManager;
import basic.tp.utility.CooldownUtil;
import basic.tp.utility.LangUtil;
import basic.tp.utility.LuckPermsUtil;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicTp extends JavaPlugin {

    private RequestManager requestManager;
    private CooldownUtil cooldownUtil;
    private boolean particleEffects;

    @Override
    public void onEnable() {
        try {
            saveDefaultConfig();
            reloadConfig();

            var timeout = getConfig().getInt("request-timeout", 120);
            var cooldown = getConfig().getInt("cooldown", 5);
            particleEffects = getConfig().getBoolean("particle-effects", true);

            requestManager = new RequestManager(timeout * 1000L);
            cooldownUtil = new CooldownUtil(cooldown * 1000L);
            LangUtil.init(this);

            register("tpa", new TpaCommand(this));
            register("tpahere", new TpaHereCommand(this));
            register("tpaccept", new TpAcceptCommand(this));
            register("tpdeny", new TpDenyCommand(this));
            register("tpcancel", new TpCancelCommand(this));
            register("tp", new TpCommand(this));
            register("tphere", new TpHereCommand(this));
            register("s", new SCommand(this));
            register("tplist", new TpListCommand(this));
            register("tpignore", new TpIgnoreCommand(this));

            getServer().getGlobalRegionScheduler().runAtFixedRate(
                this,
                task -> requestManager.cleanup(),
                20 * 30,
                20 * 30
            );

            if (LuckPermsUtil.isLoaded()) {
                getLogger().info("LuckPerms detected!");
            }

            getLogger().info("BasicTp enabled!");
        } catch (Exception e) {
            getLogger().severe("Failed to enable BasicTp: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void register(String name, CommandExecutor executor) {
        var cmd = getCommand(name);
        if (cmd != null) {
            cmd.setExecutor(executor);
            if (executor instanceof TabCompleter completer) {
                cmd.setTabCompleter(completer);
            }
        } else {
            getLogger().warning("Command /" + name + " not found in plugin.yml");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("BasicTp disabled!");
    }

    public RequestManager getRequestManager() {
        return requestManager;
    }

    public CooldownUtil getCooldownUtil() {
        return cooldownUtil;
    }

    public boolean hasParticleEffects() {
        return particleEffects;
    }
}
