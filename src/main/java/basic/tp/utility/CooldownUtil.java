package basic.tp.utility;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class CooldownUtil {

    private final long defaultCooldown;
    private final Map<UUID, Long> cooldowns = new ConcurrentHashMap<>();

    public CooldownUtil(long defaultCooldownMillis) {
        this.defaultCooldown = defaultCooldownMillis;
    }

    public boolean hasCooldown(UUID uuid) {
        var expiry = cooldowns.get(uuid);
        if (expiry == null) {
            return false;
        }
        if (System.currentTimeMillis() >= expiry) {
            cooldowns.remove(uuid);
            return false;
        }
        return true;
    }

    public long remaining(UUID uuid) {
        var expiry = cooldowns.get(uuid);
        if (expiry == null) {
            return 0;
        }
        var remaining = expiry - System.currentTimeMillis();
        return Math.max(0, remaining);
    }

    public void apply(UUID uuid) {
        apply(uuid, defaultCooldown);
    }

    public void apply(UUID uuid, long cooldownMillis) {
        cooldowns.put(uuid, System.currentTimeMillis() + cooldownMillis);
    }

    public void remove(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public void clear() {
        cooldowns.clear();
    }
}
