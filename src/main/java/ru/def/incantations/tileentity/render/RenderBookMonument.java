package ru.def.incantations.tileentity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import ru.def.incantations.tileentity.TileEntityBookMonument;
import ru.def.incantations.items.ItemIncantationsBook;
import ru.def.incantations.items.renders.RenderIncantationsBook;

/**
 * Created by Defernus on 13.05.2017.
 */

public class RenderBookMonument extends TileEntitySpecialRenderer<TileEntityBookMonument> {

	@Override
	public void renderTileEntityAt(TileEntityBookMonument te, double x, double y, double z, float partialTicks, int destroyStage) {

		super.renderTileEntityAt(te,x,y,z,partialTicks,destroyStage);

		//EntityItem item=new EntityItem(te.getWorld(),0,0,0,te.inv.getStackInSlot(0));

		//item.hoverStart=0;

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		{
			GlStateManager.translate(x+0.5,y+.89,z+0.5);
			GlStateManager.scale(.6, .6, .6);
			GlStateManager.rotate(180-te.yaw,0,1,0);

			float light = te.getWorld().getLight(te.getPos())/(float)15;

			if(te.stack.getItem() instanceof ItemIncantationsBook){
				RenderIncantationsBook.renderBook(te.stack, light);
			}

			//Minecraft.getMinecraft().getRenderManager().doRenderEntity(item,0,0,0,0,0,false);
		}
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
	}

}
