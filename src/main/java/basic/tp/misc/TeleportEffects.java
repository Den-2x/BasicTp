package basic.tp.misc;

import basic.tp.utility.LuckPermsUtil;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public final class TeleportEffects {

    private TeleportEffects() {}

    public static void playRequestSound(Player target) {
        target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 1.5f);
    }

    public static void playAcceptSound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }

    public static void playDenySound(Player player) {
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0f, 0.8f);
    }

    public static void playTeleportEffects(Player player, Location from, Location to, boolean particles) {
        player.playSound(from, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);

        if (particles && LuckPermsUtil.hasPermission(player, "basictp.effects")) {
            from.getWorld().spawnParticle(Particle.PORTAL, from, 50, 0.5, 0.5, 0.5, 0.1);
            to.getWorld().spawnParticle(Particle.PORTAL, to, 50, 0.5, 0.5, 0.5, 0.1);
        }

        player.playSound(to, Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 1.0f);
    }
}
