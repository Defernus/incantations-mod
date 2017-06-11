package ru.def.incantations.net;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFlame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import ru.def.incantations.particles.ParticleCharging;

import java.util.Random;

/**
 * Created by Defernus on 10.06.2017.
 */
public class PacketChargingParticle extends LocationDoublePacket<PacketChargingParticle>
{
	private static Random rnd = new Random();

	public PacketChargingParticle() {
		super();
	}

	public PacketChargingParticle(final double px, final double py, final double pz, final double lx, final double ly, final double lz)
	{
		super(px, py, pz, lx, ly, lz);
		this.px = px;
		this.py = py;
		this.pz = pz;
		this.lx = lx;
		this.ly = ly;
		this.lz = lz;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void handleClientSide(final EntityPlayer player)
	{
		System.out.println("test px = "+px);
		double pos_x, pos_y, pos_z;
		double r = .5;

		lx += .5;
		ly += 1;
		lz += .5;

		int n = rnd.nextInt(10);

		for(int i = 0; i < n; i++) {
			double pitch = rnd.nextDouble()*Math.PI*2;
			pos_x = this.px + r*Math.sin(pitch);
			pos_y = this.py + rnd.nextInt(1000)/1000.+.5;
			pos_z = this.pz + r*Math.cos(pitch);

			Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleCharging(player.world, pos_x, pos_y, pos_z, lx, ly, lz));
		}
	}

	@Override
	public void handleServerSide(final EntityPlayer player) {}
}
