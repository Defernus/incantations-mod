package ru.def.incantations.tileentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import ru.def.incantations.items.ItemIncantationsBook;
import ru.def.incantations.items.renders.RenderIncantationsBook;
import ru.def.incantations.tileentity.TileEntityBookMonument;
import ru.def.incantations.tileentity.TileEntitySkyChargingTable;

/**
 * Created by Defernus on 13.05.2017.
 */

public class RenderSkyChargingTable extends TileEntitySpecialRenderer<TileEntitySkyChargingTable> {

	private float timer = 0;

	@Override
	public void renderTileEntityAt(TileEntitySkyChargingTable te, double x, double y, double z, float partialTicks, int destroyStage) {

		super.renderTileEntityAt(te,x,y,z,partialTicks,destroyStage);

		EntityItem item=new EntityItem(te.getWorld(),0,0,0,te.stack);

		item.hoverStart=0;

		timer += partialTicks;

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		{
			GlStateManager.translate( x,y+.952,z );
			GlStateManager.rotate( 90, -1, 0 ,0 );
			GlStateManager.translate( .5, -1, 0 );
			//GlStateManager.rotate( 180-te.yaw, 0, 0, 1 );

			Minecraft.getMinecraft().getRenderManager().doRenderEntity(item, 0, 0, 0, 0, 0, false);
		}
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

}
