package basic.tp.utility;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.UUID;

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
    private static Method getUserByUuidMethod;
    private static Method asBooleanMethod;
    private static Boolean loaded;

    private LuckPermsUtil() {}

    public static boolean isLoaded() {
        if (loaded != null) return loaded;
        try {
            var lpClass = Class.forName("net.luckperms.api.LuckPermsProvider");
            var getMethod = lpClass.getMethod("get");
            api = getMethod.invoke(null);
            var apiClass = Class.forName("net.luckperms.api.LuckPerms");
            var userManagerMethod = apiClass.getMethod("getUserManager");
            getUserMethod = userManagerMethod;
            var userClass = Class.forName("net.luckperms.api.model.user.User");
            getCachedDataMethod = userClass.getMethod("getCachedData");
            var cachedDataClass = Class.forName("net.luckperms.api.cacheddata.CachedData");
            getMetaDataMethod = cachedDataClass.getMethod("getMetaData");
            var metaDataClass = Class.forName("net.luckperms.api.metadata.MetaData");
            getPrefixMethod = metaDataClass.getMethod("getPrefix");
            getSuffixMethod = metaDataClass.getMethod("getSuffix");
            getPrimaryGroupMethod = userClass.getMethod("getPrimaryGroup");
            getPermissionDataMethod = cachedDataClass.getMethod("getPermissionData");
            var permissionDataClass = Class.forName("net.luckperms.api.cacheddata.PermissionData");
            checkPermissionMethod = permissionDataClass.getMethod("checkPermission", String.class);

            var userManager = getUserMethod.invoke(api);
            getUserByUuidMethod = userManager.getClass().getMethod("getUser", UUID.class);

            var resultClass = Class.forName("net.luckperms.api.util.Tristate");
            asBooleanMethod = resultClass.getMethod("asBoolean");

            loaded = true;
            return true;
        } catch (Exception e) {
            loaded = false;
            return false;
        }
    }

    @Nullable
    public static String getPrefix(Player player) {
        if (!isLoaded()) return null;
        try {
            var userManager = getUserMethod.invoke(api);
            var user = getUserByUuidMethod.invoke(userManager, player.getUniqueId());
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
            var user = getUserByUuidMethod.invoke(userManager, player.getUniqueId());
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
            var user = getUserByUuidMethod.invoke(userManager, player.getUniqueId());
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
                var user = getUserByUuidMethod.invoke(userManager, player.getUniqueId());
                if (user != null) {
                    var data = getCachedDataMethod.invoke(user);
                    var permData = getPermissionDataMethod.invoke(data);
                    var result = checkPermissionMethod.invoke(permData, permission);
                    return (boolean) asBooleanMethod.invoke(result);
                }
            } catch (Exception ignored) {}
        }
        return player.hasPermission(permission);
    }
}
