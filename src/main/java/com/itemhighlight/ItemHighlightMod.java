package com.itemhighlight;

import com.itemhighlight.config.HighlightConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemHighlightMod implements ClientModInitializer {

    public static final String MOD_ID = "itemhighlight";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static HighlightConfig CONFIG;

    @Override
    public void onInitializeClient() {
        CONFIG = HighlightConfig.load();
        LOGGER.info("Item Highlight Mod loaded! {} items configured.",
                CONFIG.getEntries().size());
    }
}
