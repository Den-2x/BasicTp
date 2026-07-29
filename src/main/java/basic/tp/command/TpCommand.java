package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.utility.LangUtil;
import basic.tp.utility.LuckPermsUtil;
import basic.tp.utility.MsgUtil;
import basic.tp.utility.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) {
            MsgUtil.error(sender, LangUtil.get("tp-usage"));
            return true;
        }

        if (sender instanceof Player player && !LuckPermsUtil.hasPermission(player, "basictp.admin")) {
            MsgUtil.error(sender, LangUtil.get("no-permission"));
            return true;
        }

        var target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MsgUtil.error(sender, LangUtil.get("player-not-found"));
            return true;
        }

        if (args.length == 1) {
            if (!(sender instanceof Player player)) {
                MsgUtil.error(sender, LangUtil.get("tp-console-target"));
                return true;
            }
            TeleportUtil.teleport(player, target.getLocation(), plugin.hasParticleEffects());
            MsgUtil.success(sender, LangUtil.get("tp-teleported", target.getName()));
        } else {
            var toTeleport = Bukkit.getPlayer(args[1]);
            if (toTeleport == null) {
                MsgUtil.error(sender, LangUtil.get("tp-target-not-found"));
                return true;
            }
            TeleportUtil.teleport(toTeleport, target.getLocation(), plugin.hasParticleEffects());
            MsgUtil.success(sender, LangUtil.get("tp-teleported-other", toTeleport.getName(), target.getName()));
        }

        return true;
    }
}
