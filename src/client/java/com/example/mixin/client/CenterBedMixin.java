package com.example.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.tools.obfuscation.ObfuscationEnvironment;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntity.class)
public abstract class CenterBedMixin {

	@Inject(method = "tick", at = @At("TAIL"))
	private void stopBedSliding(CallbackInfo ci) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null) return;

		LivingEntity self = (LivingEntity)(Object)this;

		// Only affect the local player
		if (self != mc.player) return;

		// Only while sleeping
		if (!self.isSleeping()) return;

		// Kill residual client velocity
		self.setDeltaMovement(Vec3.ZERO);
		self.setPos(self.position());
	}
}