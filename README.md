# Item Highlight Mod

Hebt konfigurierbare Items auf dem Boden hervor, indem sie vergrößert dargestellt werden.

## Features
- Items werden **visuell vergrößert** auf dem Boden angezeigt
- Vollständig konfigurierbar per JSON-Datei
- Nur clientseitig – funktioniert auf jedem Server
- Standard: Enchanted Golden Apple (2.5×), Nether Star (3×), Diamond (1.8×), Totem (2×), Elytra (2×)

## Config

Datei: `.minecraft/config/itemhighlight.json`

```json
{
  "entries": [
    { "item": "minecraft:enchanted_golden_apple", "scale": 2.5 },
    { "item": "minecraft:nether_star", "scale": 3.0 },
    { "item": "minecraft:diamond", "scale": 1.8 },
    { "item": "minecraft:totem_of_undying", "scale": 2.0 },
    { "item": "minecraft:elytra", "scale": 2.0 }
  ]
}
```

Nach Änderungen Minecraft neu starten.

## JAR herunterladen (nach Build)

1. Geh zu **Actions** Tab auf GitHub
2. Klick auf den letzten erfolgreichen Build
3. Unter **Artifacts** → `itemhighlight-mod` herunterladen
4. JAR in `.minecraft/mods/` kopieren

## Requirements
- Minecraft 1.21.1
- Fabric Loader 0.16.5+
- Fabric API
