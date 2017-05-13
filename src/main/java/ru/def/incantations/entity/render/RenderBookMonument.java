package ru.def.incantations.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import ru.def.incantations.entity.TileEntityBookMonument;

/**
 * Created by Defernus on 13.05.2017.
 */

public class RenderBookMonument extends TileEntitySpecialRenderer<TileEntityBookMonument> {

	EntityItem item;

	@Override
	public void renderTileEntityAt(TileEntityBookMonument te, double x, double y, double z, float partialTicks, int destroyStage) {
		item=new EntityItem(te.getWorld(),0,0,0,te.inv.getStackInSlot(0));

		item.hoverStart=0;

		super.renderTileEntityAt(te, x, y, z, partialTicks, destroyStage);

		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(x+0.5,y+0.5,z+0.5);

			Minecraft.getMinecraft().getRenderManager().doRenderEntity(item,0,0,0,0,0,false);
		}
		GlStateManager.popMatrix();
	}

}
