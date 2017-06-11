package ru.def.incantations.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Defernus on 10.06.2017.
 */

@SideOnly(Side.CLIENT)
public class ParticleCharging extends Particle {

	private double x, y, z, bx, by, bz;

	public ParticleCharging(World world, double x, double y, double z, double bx, double by, double bz) {
		super(world, x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
		this.bx = bx;
		this.by = by;
		this.bz = bz;
		this.particleRed = .1f;
		this.particleGreen = 1;
		this.particleBlue = .1f;
		this.particleMaxAge = 40;
	}

	@Override
	public void move(double x, double y, double z) {
		this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
		this.resetPositionToBB();
	}

	/**
	 * Renders the particle
	 */
	@Override
	public void renderParticle(VertexBuffer buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		this.particleTextureIndexX = 11;
		this.particleTextureIndexX = (int)(this.particleAge/2.5);
		this.particleTextureIndexX = this.particleTextureIndexX<8?this.particleTextureIndexX:15-this.particleTextureIndexX;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		float f = ((float) this.particleAge + p_189214_1_) / (float) this.particleMaxAge;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		int i = super.getBrightnessForRender(p_189214_1_);
		int j = i & 255;
		int k = i >> 16 & 255;
		j = j + (int) (f * 15.0F * 16.0F);

		if (j > 240) {
			j = 240;
		}

		return j | k << 16;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge) {
			this.setExpired();
		}

		this.motionX = (this.bx - this.x)*.05;
		this.motionY = (this.by - this.y)*.05;
		this.motionZ = (this.bz - this.z)*.05;

		double l = (this.posX-this.bx)*(this.posX-this.bx) + (this.posY-this.by)*(this.posY-this.by) + (this.posZ-this.bz)*(this.posZ-this.bz);

		if(l<.25) {
			this.motionX*=l*4;
			this.motionY*=l*4;
			this.motionZ*=l*4;
		}

		this.move(this.motionX, this.motionY, this.motionZ);
	}
}