package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.utility.LangUtil;
import basic.tp.utility.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpListCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpListCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            MsgUtil.error(sender, LangUtil.get("only-players"));
            return true;
        }

        var request = plugin.getRequestManager().getIncomingRequest(player.getUniqueId());
        if (request == null) {
            MsgUtil.error(sender, LangUtil.get("tplist-none"));
            return true;
        }

        var requester = Bukkit.getOfflinePlayer(request.getSender());
        var name = requester.getName() != null ? requester.getName() : request.getSender().toString();

        if (request.isHere()) {
            MsgUtil.info(sender, LangUtil.get("tplist-here", name));
        } else {
            MsgUtil.info(sender, LangUtil.get("tplist-tpa", name));
        }

        return true;
    }
}
