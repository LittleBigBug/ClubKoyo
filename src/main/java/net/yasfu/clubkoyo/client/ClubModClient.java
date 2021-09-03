package net.yasfu.clubkoyo.client;

import net.yasfu.clubkoyo.module.ModuleRegistry;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ClubModClient implements ClientModInitializer {

    public static final boolean DEBUG = true;

    public static final String MOD_ID = "clubkoyo";
    public static final String MOD_NAME = "ClubKoyo";

    public static String MOD_VERSION;

    private static final Logger LOG = LogManager.getLogger(MOD_NAME);

    public static Logger getLogger() {
        return LOG;
    }

    @Override
    public void onInitializeClient() {
        LOG.info("{} Client mod loading..", MOD_NAME);

        FabricLoader loader = FabricLoader.getInstance();

        ModContainer container = loader.getModContainer(MOD_ID).orElseThrow(NullPointerException::new);

        MOD_VERSION = container.getMetadata().getVersion().getFriendlyString();

        ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, "clubkoyo"), container, ResourcePackActivationType.ALWAYS_ENABLED);

        ModuleRegistry.initAutoModuleSystem();
        ModuleRegistry.startModules();
    }

}
