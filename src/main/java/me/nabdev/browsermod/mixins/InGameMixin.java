package me.nabdev.browsermod.mixins;

import finalforeach.cosmicreach.gamestates.InGame;
import me.nabdev.browsermod.entities.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGame.class)
public class InGameMixin {
    @Inject(method = "unloadWorld", at = @At(value = "INVOKE", target = "Lfinalforeach/cosmicreach/ClientWorldLoader;requestExit()V", shift = At.Shift.AFTER))
    private void unload(CallbackInfo ci) {
       Screen.driver.quit();
    }
}
