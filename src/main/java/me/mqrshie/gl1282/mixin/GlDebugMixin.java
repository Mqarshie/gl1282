package me.mqrshie.gl1282.mixin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gl.GlDebug;

@Mixin(GlDebug.class)
public abstract class GlDebugMixin {
    private static boolean hasPostedMessage1282 = false;

    private static Logger LOGGER = LoggerFactory.getLogger("Suppress OpenGL Error 1282");

    @Inject(at = @At(value = "HEAD"), method = "info(IIIIIJJ)V", cancellable = true)
    private static void suppressMessage(int source, int type, int id, int severity, int messageLength, long message,
                                        long l,
                                        CallbackInfo ci) {
        if (id == 1282) {
            if (hasPostedMessage1282) {
                ci.cancel();
            } else {
                LOGGER.info("This mod has been configured to suppress OpenGL error 1282.");
                LOGGER.info("This error will not be shown again for this run of Minecraft.");
                hasPostedMessage1282 = true;
            }
        }
    }
}
