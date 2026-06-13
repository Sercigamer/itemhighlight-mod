package com.itemhighlight.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class HighlightConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH =
            FabricLoader.getInstance().getConfigDir().resolve("itemhighlight.json");

    private List<HighlightEntry> entries = new ArrayList<>();

    public static class HighlightEntry {
        public String item;
        public float scale;

        public HighlightEntry() {}
        public HighlightEntry(String item, float scale) {
            this.item  = item;
            this.scale = scale;
        }
    }

    public List<HighlightEntry> getEntries() { return entries; }

    public float getScale(String itemId) {
        if (entries == null) return 1.0f;
        for (HighlightEntry e : entries) {
            if (e != null && e.item != null && e.item.equals(itemId)) {
                return Math.max(0.1f, e.scale);
            }
        }
        return 1.0f;
    }

    public boolean isHighlighted(String itemId) {
        if (entries == null) return false;
        for (HighlightEntry e : entries) {
            if (e != null && e.item != null && e.item.equals(itemId)) return true;
        }
        return false;
    }

    public static HighlightConfig load() {
        if (Files.exists(CONFIG_PATH)) {
            try (Reader r = Files.newBufferedReader(CONFIG_PATH)) {
                HighlightConfig cfg = GSON.fromJson(r, HighlightConfig.class);
                if (cfg != null && cfg.entries != null) return cfg;
            } catch (Exception e) {
                System.err.println("[ItemHighlight] Failed to read config: " + e.getMessage());
            }
        }
        HighlightConfig def = new HighlightConfig();
        def.entries.add(new HighlightEntry("minecraft:enchanted_golden_apple", 2.5f));
        def.entries.add(new HighlightEntry("minecraft:nether_star",            3.0f));
        def.entries.add(new HighlightEntry("minecraft:diamond",                1.8f));
        def.entries.add(new HighlightEntry("minecraft:totem_of_undying",       2.0f));
        def.entries.add(new HighlightEntry("minecraft:elytra",                 2.0f));
        def.save();
        return def;
    }

    public void save() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            try (Writer w = Files.newBufferedWriter(CONFIG_PATH)) {
                GSON.toJson(this, w);
            }
        } catch (Exception e) {
            System.err.println("[ItemHighlight] Failed to save config: " + e.getMessage());
        }
    }
}
