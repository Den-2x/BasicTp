package basic.tp.data;

import java.util.UUID;

public class TeleportRequest {

    private final UUID sender;
    private final UUID target;
    private final boolean here;
    private final long expiresAt;

    public TeleportRequest(UUID sender, UUID target, boolean here, long expiresAt) {
        this.sender = sender;
        this.target = target;
        this.here = here;
        this.expiresAt = expiresAt;
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getTarget() {
        return target;
    }

    public boolean isHere() {
        return here;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiresAt;
    }
}
