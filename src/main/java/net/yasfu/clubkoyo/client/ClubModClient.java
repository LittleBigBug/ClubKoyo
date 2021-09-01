package net.yasfu.clubkoyo.client;

import net.yasfu.clubkoyo.client.utils.ServerUtils;

import net.minecraft.util.Identifier;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.lwjgl.glfw.GLFW;

public class ClubModClient implements ClientModInitializer {

    public static final String MOD_ID = "clubkoyo";
    public static final String MOD_NAME = "ClubKoyo";

    private static final Logger LOG = LogManager.getLogger(MOD_NAME);

    // Tele keybinds
    private static KeyBinding homeKeybind;
    private static KeyBinding spawnKeybind;

    // Gui keybinds
    private static KeyBinding phoneKeybind;
    private static KeyBinding friendsKeybind;
    private static KeyBinding inventoryKeybind;

    public static Logger getLogger() {
        return LOG;
    }

    @Override
    public void onInitializeClient() {
        LOG.info("{} Client mod loading..", MOD_NAME);

        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer ->
            ResourceManagerHelper.registerBuiltinResourcePack(new Identifier(MOD_ID, "clubkoyo"), modContainer, ResourcePackActivationType.ALWAYS_ENABLED));

        homeKeybind = registerKeybinding("home", GLFW.GLFW_KEY_H);
        spawnKeybind = registerKeybinding("spawn", GLFW.GLFW_KEY_Y);
        phoneKeybind = registerKeybinding("phone", GLFW.GLFW_KEY_R);
        friendsKeybind = registerKeybinding("friends", GLFW.GLFW_KEY_O);
        inventoryKeybind = registerKeybinding("inventory", GLFW.GLFW_KEY_I);

        ClientTickEvents.END_CLIENT_TICK.register(this::onClientEndTick);
    }

    private KeyBinding registerKeybinding(String name, int keyCode) {
        return KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "clubkoyo.bind." + name,
                InputUtil.Type.KEYSYM,
                keyCode,
                "clubkoyo.bind.category"
        ));
    }

    private void onClientEndTick(MinecraftClient client) {
        if (client.player == null) return;

        // Todo; do checks if in a game
        if (homeKeybind.wasPressed()) {
            client.player.sendChatMessage("/home");
            return;
        } else if (spawnKeybind.wasPressed()) {
            client.player.sendChatMessage("/spawn");
            return;
        }

        if (inventoryKeybind.wasPressed()) {
            ServerUtils.openMineClubInventory(client);
            return;
        }

        if (phoneKeybind.wasPressed()) {
            client.player.sendChatMessage("/phone");
        } else if (friendsKeybind.wasPressed()) {
            client.player.sendChatMessage("/f");
        }
    }

}
