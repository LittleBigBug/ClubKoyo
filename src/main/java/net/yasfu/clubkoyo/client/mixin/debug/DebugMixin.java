package net.yasfu.clubkoyo.client.mixin.debug;

import net.yasfu.clubkoyo.client.ClubModClient;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Formatting;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.ArrayList;

@Mixin(DebugHud.class)
public class DebugMixin {

    @Redirect(method = "getRightText",
            at = @At(value = "INVOKE", target = "Lcom/google/common/collect/Lists;newArrayList([Ljava/lang/Object;)Ljava/util/ArrayList;", remap = false))
    private ArrayList<String> clubDebugRedirect(Object[] elements) {
        ArrayList<String> strings = Lists.newArrayList((String[]) elements);

        if (!ClubModClient.DEBUG) {
            return strings;
        }

        strings.add("");
        strings.add(Formatting.DARK_AQUA + "ClubKoyo | " + ClubModClient.MOD_VERSION);

        MinecraftClient client = MinecraftClient.getInstance();

        Screen curScreen = client.currentScreen;

        if (curScreen != null) {
            String screenClassName = curScreen.getClass().getName();
            String screenTitle = curScreen.getTitle().asString();

            strings.add(Formatting.AQUA + "Current screen info:");
            strings.add("ClassName: " + screenClassName);
            strings.add("Title: " + screenTitle);
        }

        ServerInfo serverInfo = client.getCurrentServerEntry();

        if (serverInfo != null) {
            strings.add(Formatting.AQUA + "Current server info:");
            strings.add("Name: " + serverInfo.name);
            strings.add("Address: " + serverInfo.address);
            strings.add("Label: " + serverInfo.label.asString());
        }

        ClientWorld world = client.world;

        if (world != null) {
            strings.add(Formatting.AQUA + "Current world info:");
            strings.add("Scoreboard Objectives: " + StringUtils.join(world.getScoreboard().getObjectiveNames().toArray(), ", "));
            strings.add("Next Map ID: " + world.getNextMapId());

            MinecraftServer server = world.getServer();

            if (server != null) {
                String statusMsg = server.getModdedStatusMessage().orElse("");

                strings.add(Formatting.AQUA + "Server World info:");
                strings.add("Modded status message: " + statusMsg);
            }
        }

        return strings;
    }

}
