package basic.tp.utility;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public final class LangUtil {

    private static YamlConfiguration messages;

    private LangUtil() {}

    public static void init(JavaPlugin plugin) {
        var folder = new File(plugin.getDataFolder(), "lang");
        folder.mkdirs();

        var lang = plugin.getConfig().getString("lang");
        if (lang == null || lang.isBlank()) {
            lang = "en";
        }

        messages = new YamlConfiguration();

        // Load from JAR resource as defaults
        loadJarDefaults(plugin, "messages_" + lang + ".yml");

        // Fallback to English
        if (messages.getDefaults() == null && !"en".equals(lang)) {
            loadJarDefaults(plugin, "messages_en.yml");
        }

        // Save disk file if missing
        var file = new File(folder, "messages_" + lang + ".yml");
        if (!file.exists()) {
            try {
                plugin.saveResource("lang/messages_" + lang + ".yml", false);
            } catch (Exception ignored) {}
        }

        // Load from disk (user overrides)
        if (file.exists()) {
            var disk = YamlConfiguration.loadConfiguration(file);
            disk.setDefaults(messages.getDefaults());
            messages = disk;
        }
    }

    private static void loadJarDefaults(JavaPlugin plugin, String name) {
        try (var in = plugin.getResource("lang/" + name)) {
            if (in != null) {
                var reader = new InputStreamReader(in, StandardCharsets.UTF_8);
                messages.setDefaults(YamlConfiguration.loadConfiguration(reader));
            }
        } catch (Exception ignored) {}
    }

    public static String get(String key, Object... args) {
        if (messages == null) return key;
        var msg = messages.getString(key);
        if (msg == null) return key;
        return String.format(msg, args);
    }
}