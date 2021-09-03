package net.yasfu.clubkoyo.client.utils;

import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.network.ClientConnection;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerUtils {

    public static final String address = "clubkoyo.mineclub.com";
    public static final String address_trunc = ".mineclub.com";

    public static void connectMineClub()
            throws UnknownHostException {

        ClientConnection.connect(InetAddress.getByName(address), 25565, true);
    }

    public static boolean connectedToMineClub() {
        MinecraftClient client = MinecraftClient.getInstance();
        return connectedToMineClub(client);
    }

    public static boolean connectedToMineClub(MinecraftClient client) {
        ServerInfo serverInfo = client.getCurrentServerEntry();

        if (serverInfo == null) return false;

        return serverInfo.address.endsWith(address_trunc);
    }

    public static void openMineClubInventory(MinecraftClient client) {
        ClientPlayNetworkHandler handler = client.getNetworkHandler();

        if (handler == null) return;

        int sync_id = 0;
        short action_id = 0;

        ClientPlayerEntity ply = client.player;

        if (ply != null) {
            sync_id = ply.currentScreenHandler.syncId;
            action_id = ply.currentScreenHandler.getNextActionId(ply.inventory);
        }

        handler.sendPacket(new ClickSlotC2SPacket(sync_id, 45, 1, SlotActionType.PICKUP, ItemStack.EMPTY, action_id));
    }

}
