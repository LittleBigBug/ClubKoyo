package net.yasfu.clubkoyo.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MinecraftClient.class)
abstract class DontUnloadServerResourcePack {

    @Redirect(method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/ClientBuiltinResourcePackProvider;clear()V"))
    public void dontActuallyClearLol(ClientBuiltinResourcePackProvider clientBuiltinResourcePackProvider) { }

}
