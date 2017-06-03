package ru.def.incantations.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.def.incantations.items.ItemIncantationsBook;

/**
 * Created by Defernus on 30.05.2017.
 */
public class GuiBook extends GuiScreen {
	private static final ResourceLocation TEX_BOOK = new ResourceLocation("incantations:textures/gui/book_inhand.png");

	private final int power_w = 24, power_h = 118,
			tex_size = 256;

	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			Minecraft mc = Minecraft.getMinecraft();

			ItemStack item = ItemStack.EMPTY;

			if(mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemIncantationsBook) {
				item = mc.player.getHeldItem(EnumHand.MAIN_HAND);
			}else if(mc.player.getHeldItem(EnumHand.OFF_HAND).getItem() instanceof ItemIncantationsBook) {
				item = mc.player.getHeldItem(EnumHand.OFF_HAND);
			}else {
				return;
			}

			if(!item.isEmpty()){
				mc.renderEngine.bindTexture(TEX_BOOK);

				this.width = event.getResolution().getScaledWidth();
				this.height = event.getResolution().getScaledHeight();

				GlStateManager.pushMatrix();
				{
					float k =item.getTagCompound().getFloat("cur_pow")/item.getTagCompound().getFloat("max_pow");

					GlStateManager.color(1,1,1);
					//GlStateManager.scale(2,2,2);

					draw(power_w*2, 0, power_w, power_h, tex_size);
					GlStateManager.translate(0,(int)(power_h*k),0);
					draw(0, (int)(power_h*k), power_w, (int)(power_h*(1-k)), tex_size);
					GlStateManager.translate(0,-(int)(power_h*k),0);
					//GlStateManager.color(0,.9f*(k*.5f+.5f),1f*(k*.5f+.5f));
					GlStateManager.color(0,.9f,1f);
					draw(power_w, 0, power_w, (int)(power_h*k), tex_size);

				}
				GlStateManager.popMatrix();
			}
		}
	}

	private void draw(int x, int y, int w, int h, int s){

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer wr = tessellator.getBuffer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(0, h, 0).tex(x/(double)s, (y+h)/(double)s).endVertex();
		wr.pos(w, h, 0).tex((x+w)/(double)s, (y+h)/(double)s).endVertex();
		wr.pos(w, 0, 0).tex((x+w)/(double)s, y/(double)s).endVertex();
		wr.pos(0, 0, 0).tex(x/(double)s, y/(double)s).endVertex();
		tessellator.draw();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
