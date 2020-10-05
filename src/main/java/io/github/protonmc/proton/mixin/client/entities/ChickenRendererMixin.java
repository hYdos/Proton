package io.github.protonmc.proton.mixin.client.entities;

import io.github.protonmc.proton.module.ModuleManager;
import io.github.protonmc.proton.module.client.VariantAnimalTexturesModule;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.ChickenEntityRenderer;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(ChickenEntityRenderer.class)
public class ChickenRendererMixin {

    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTexture(ChickenEntity chickenEntity, CallbackInfoReturnable<Identifier> cir){
        if(ModuleManager.getInstance().isModuleEnabled(VariantAnimalTexturesModule.class)){
            cir.setReturnValue(VariantAnimalTexturesModule.getTextureOrShiny(chickenEntity, VariantAnimalTexturesModule.VariantTextureType.CHICKEN, VariantAnimalTexturesModule.enableChicken));
        }
    }

}
