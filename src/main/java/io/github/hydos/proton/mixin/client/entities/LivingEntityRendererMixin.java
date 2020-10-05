package io.github.hydos.proton.mixin.client.entities;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import io.github.hydos.proton.module.ModuleManager;
import io.github.hydos.proton.module.client.AngryCreepersModule;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.CreeperEntity;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    public LivingEntityRendererMixin(EntityRenderDispatcher dispatcher) {
		super(dispatcher);
    }
    
    @Unique
    private float creeperColor = 1.0F;

    @Inject(method = "render", at = @At("HEAD"))
	public void getCreeperColor(T livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (ModuleManager.getInstance().isModuleEnabled(AngryCreepersModule.class)) {
            if (livingEntity instanceof CreeperEntity) {
                creeperColor = 1.0F - (((CreeperEntity) livingEntity).getClientFuseTime(g) / 1.0714285F);
            } else {
                creeperColor = 1.0F;
            }
        } else {
            creeperColor = 1.0F;
        }
    }

    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void modifyCreeperColor(Args args) {
        args.set(5, creeperColor);
        args.set(6, creeperColor);
    }
}
