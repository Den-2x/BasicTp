package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.data.TeleportRequest;
import basic.tp.misc.TeleportEffects;
import basic.tp.utility.LangUtil;
import basic.tp.utility.MsgUtil;
import basic.tp.utility.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpAcceptCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpAcceptCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            MsgUtil.error(sender, LangUtil.get("only-players"));
            return true;
        }

        var manager = plugin.getRequestManager();
        TeleportRequest request;

        if (args.length > 0) {
            var requester = Bukkit.getPlayer(args[0]);
            if (requester == null) {
                MsgUtil.error(sender, LangUtil.get("player-not-found"));
                return true;
            }
            request = manager.getRequestFrom(player.getUniqueId(), requester.getUniqueId());
        } else {
            request = manager.getRequest(player.getUniqueId());
        }

        if (request == null) {
            MsgUtil.error(sender, LangUtil.get("no-request"));
            return true;
        }

        manager.removeRequest(player.getUniqueId());

        var requester = Bukkit.getPlayer(request.getSender());
        if (requester == null) {
            MsgUtil.error(sender, LangUtil.get("player-offline"));
            return true;
        }

        var particles = plugin.hasParticleEffects();

        if (request.isHere()) {
            TeleportUtil.teleport(player, requester.getLocation(), particles);
            MsgUtil.success(player, LangUtil.get("tpaccept-here", requester.getName()));
            MsgUtil.info(requester, LangUtil.get("tpaccept-notify", player.getName()));
            TeleportEffects.playAcceptSound(requester);
        } else {
            TeleportUtil.teleport(requester, player.getLocation(), particles);
            MsgUtil.success(player, LangUtil.get("tpaccept-done", requester.getName()));
            MsgUtil.info(requester, LangUtil.get("tpaccept-notify", player.getName()));
            TeleportEffects.playAcceptSound(requester);
        }

        return true;
    }
}
