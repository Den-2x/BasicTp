package basic.tp.utility;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public final class LuckPermsUtil {

    private static Object api;
    private static Method getUserMethod;
    private static Method getCachedDataMethod;
    private static Method getMetaDataMethod;
    private static Method getPrefixMethod;
    private static Method getSuffixMethod;
    private static Method getPrimaryGroupMethod;
    private static Method getPermissionDataMethod;
    private static Method checkPermissionMethod;
    private static boolean loaded = false;

    private LuckPermsUtil() {}

    public static boolean isLoaded() {
        if (loaded) return true;
        try {
            var lpClass = Class.forName("net.luckperms.api.LuckPermsProvider");
            var getMethod = lpClass.getMethod("get");
            api = getMethod.invoke(null);
            var apiClass = Class.forName("net.luckperms.api.LuckPerms");
            getUserMethod = apiClass.getMethod("getUserManager");
            getCachedDataMethod = Class.forName("net.luckperms.api.model.user.User").getMethod("getCachedData");
            getMetaDataMethod = Class.forName("net.luckperms.api.cacheddata.CachedData").getMethod("getMetaData");
            getPrefixMethod = Class.forName("net.luckperms.api.metadata.MetaData").getMethod("getPrefix");
            getSuffixMethod = Class.forName("net.luckperms.api.metadata.MetaData").getMethod("getSuffix");
            getPrimaryGroupMethod = Class.forName("net.luckperms.api.model.user.User").getMethod("getPrimaryGroup");
            getPermissionDataMethod = Class.forName("net.luckperms.api.cacheddata.CachedData").getMethod("getPermissionData");
            checkPermissionMethod = Class.forName("net.luckperms.api.cacheddata.PermissionData").getMethod("checkPermission", String.class);
            loaded = true;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Nullable
    public static String getPrefix(Player player) {
        if (!isLoaded()) return null;
        try {
            var userManager = getUserMethod.invoke(api);
            var getUserByUuid = userManager.getClass().getMethod("getUser", java.util.UUID.class);
            var user = getUserByUuid.invoke(userManager, player.getUniqueId());
            if (user == null) return null;
            var data = getCachedDataMethod.invoke(user);
            var meta = getMetaDataMethod.invoke(data);
            return (String) getPrefixMethod.invoke(meta);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String getSuffix(Player player) {
        if (!isLoaded()) return null;
        try {
            var userManager = getUserMethod.invoke(api);
            var getUserByUuid = userManager.getClass().getMethod("getUser", java.util.UUID.class);
            var user = getUserByUuid.invoke(userManager, player.getUniqueId());
            if (user == null) return null;
            var data = getCachedDataMethod.invoke(user);
            var meta = getMetaDataMethod.invoke(data);
            return (String) getSuffixMethod.invoke(meta);
        } catch (Exception e) {
            return null;
        }
    }

    @Nullable
    public static String getPrimaryGroup(Player player) {
        if (!isLoaded()) return null;
        try {
            var userManager = getUserMethod.invoke(api);
            var getUserByUuid = userManager.getClass().getMethod("getUser", java.util.UUID.class);
            var user = getUserByUuid.invoke(userManager, player.getUniqueId());
            if (user == null) return null;
            return (String) getPrimaryGroupMethod.invoke(user);
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean hasPermission(Player player, String permission) {
        if (isLoaded()) {
            try {
                var userManager = getUserMethod.invoke(api);
                var getUserByUuid = userManager.getClass().getMethod("getUser", java.util.UUID.class);
                var user = getUserByUuid.invoke(userManager, player.getUniqueId());
                if (user != null) {
                    var data = getCachedDataMethod.invoke(user);
                    var permData = getPermissionDataMethod.invoke(data);
                    var result = checkPermissionMethod.invoke(permData, permission);
                    return (boolean) result.getClass().getMethod("asBoolean").invoke(result);
                }
            } catch (Exception ignored) {}
        }
        return player.hasPermission(permission);
    }
}
