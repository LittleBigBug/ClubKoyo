package net.yasfu.clubkoyo.client.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin {

    private static final Identifier MINECLUB_TITLE_TEXTURE = new Identifier("clubkoyo:textures/gui/mineclub.png");

    @ModifyArg(method = "render", slice = @Slice(
            from = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/MathHelper;ceil(F)I"),
            to = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;method_29343(IILjava/util/function/BiConsumer;)V")
    ),
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/texture/TextureManager;bindTexture(Lnet/minecraft/util/Identifier;)V"))
    private Identifier titleTexture(Identifier identifier) {
        return MINECLUB_TITLE_TEXTURE;
    }

    @ModifyArg(method = "render",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/TitleScreen;drawStringWithShadow(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V", ordinal = 0))
    private String versionString(String string) {
        return I18n.translate("clubkoyo.version");
    }

}
