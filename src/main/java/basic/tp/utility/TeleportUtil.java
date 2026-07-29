package basic.tp.utility;

import basic.tp.misc.TeleportEffects;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public final class TeleportUtil {

    private TeleportUtil() {}

    public static void teleport(Player player, Location location) {
        teleport(player, location, false);
    }

    public static void teleport(Player player, Location location, boolean particles) {
        var from = player.getLocation();
        TeleportEffects.playTeleportEffects(player, from, location, particles);
        player.teleportAsync(location);
    }
}
