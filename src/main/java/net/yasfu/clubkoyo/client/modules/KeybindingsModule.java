package net.yasfu.clubkoyo.client.modules;

import net.yasfu.clubkoyo.client.utils.ServerUtils;
import net.yasfu.clubkoyo.module.Module;
import net.yasfu.clubkoyo.module.ModuleData;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import org.lwjgl.glfw.GLFW;

@ModuleData(
        name = "keybindings",
        author = "LittleBigBug",
        desc = "MineClub-related shortcut keybindings"
)
public class KeybindingsModule implements Module {

    // Tele keybinds
    private static KeyBinding homeKeybind;
    private static KeyBinding spawnKeybind;

    // Gui keybinds
    private static KeyBinding phoneKeybind;
    private static KeyBinding friendsKeybind;
    private static KeyBinding inventoryKeybind;

    @Override
    public void onModuleInit() {
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
