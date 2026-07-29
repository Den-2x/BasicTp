package basic.tp.data;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RequestManager {

    private final Map<UUID, TeleportRequest> requests = new ConcurrentHashMap<>();
    private final Map<UUID, Set<UUID>> ignored = new ConcurrentHashMap<>();
    private final long timeoutMillis;

    public RequestManager(long timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }

    public TeleportRequest createRequest(UUID sender, UUID target, boolean here) {
        var request = new TeleportRequest(sender, target, here, System.currentTimeMillis() + timeoutMillis);
        requests.put(target, request);
        return request;
    }

    public TeleportRequest getRequest(UUID target) {
        var request = requests.get(target);
        if (request == null || request.isExpired()) {
            if (request != null) requests.remove(target);
            return null;
        }
        return request;
    }

    public TeleportRequest getRequestFrom(UUID target, UUID sender) {
        var request = getRequest(target);
        if (request != null && request.getSender().equals(sender)) {
            return request;
        }
        return null;
    }

    public TeleportRequest removeRequest(UUID target) {
        return requests.remove(target);
    }

    public TeleportRequest removeRequestFrom(UUID target, UUID sender) {
        var request = requests.get(target);
        if (request != null && request.getSender().equals(sender)) {
            requests.remove(target);
            return request;
        }
        return null;
    }

    public TeleportRequest getOutgoingRequest(UUID sender) {
        for (var request : requests.values()) {
            if (request.getSender().equals(sender) && !request.isExpired()) {
                return request;
            }
        }
        return null;
    }

    public TeleportRequest getIncomingRequest(UUID target) {
        return getRequest(target);
    }

    public void cleanup() {
        requests.values().removeIf(TeleportRequest::isExpired);
    }

    public boolean isIgnored(UUID target, UUID sender) {
        var set = ignored.get(target);
        return set != null && set.contains(sender);
    }

    public boolean toggleIgnore(UUID target, UUID sender) {
        var set = ignored.computeIfAbsent(target, k -> ConcurrentHashMap.newKeySet());
        if (set.contains(sender)) {
            set.remove(sender);
            if (set.isEmpty()) {
                ignored.remove(target);
            }
            return false;
        }
        set.add(sender);
        return true;
    }

    public Set<UUID> getIgnored(UUID target) {
        return ignored.getOrDefault(target, Set.of());
    }
}
