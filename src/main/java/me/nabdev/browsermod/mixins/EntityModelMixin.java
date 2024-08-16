package me.nabdev.browsermod.mixins;

import com.badlogic.gdx.graphics.Texture;
import finalforeach.cosmicreach.rendering.entities.EntityModel;
import me.nabdev.browsermod.ITexturableModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityModel.class)
public class EntityModelMixin implements ITexturableModel {
    @Shadow Texture diffuseTexture;

    @Override
    public void browserMod$setTexture(com.badlogic.gdx.graphics.Texture texture) {
        this.diffuseTexture = texture;
    }
}
