package net.yasfu.clubkoyo.client;

import net.yasfu.clubkoyo.module.ModuleRegistry;

import net.minecraft.util.Identifier;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class ClubModClient implements ClientModInitializer {

    public static final String MOD_ID = "clubkoyo";
    public static final String MOD_NAME = "ClubKoyo";

    private static final Logger LOG = LogManager.getLogger(MOD_NAME);

    public static Logger getLogger() {
        return LOG;
    }

    @Override
    public void onInitializeClient() {
        LOG.info("{} Client mod loading..", MOD_NAME);

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, "clubkoyo"), modContainer, ResourcePackActivationType.ALWAYS_ENABLED));

        ModuleRegistry.initAutoModuleSystem();
        ModuleRegistry.startModules();
    }

}
