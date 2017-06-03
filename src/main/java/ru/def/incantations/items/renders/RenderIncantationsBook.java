package ru.def.incantations.items.renders;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderSpecificHandEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import ru.def.incantations.items.ItemIncantationsBook;

/**
 * Created by Defernus on 02.06.2017.
 */
public class RenderIncantationsBook {
	private static ResourceLocation TEX_BOOK = new ResourceLocation("incantations:textures/gui/book_inhand.png");
	//private static ResourceLocation TEX_CHARS = new ResourceLocation("incantations:textures/entity/rune_chars.png");

	private final int book_w = 16, book_h = 16, power_w = 24, power_h = 7, tex_size = 32;

	private static double timer=0;
	private static float lastPartialTick=0;

	@SubscribeEvent
	public void renderItem(RenderSpecificHandEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		ItemStack item = event.getItemStack();

		if(!(item.getItem() instanceof ItemIncantationsBook)) return;

		event.setCanceled(true);

		mc.renderEngine.bindTexture(TEX_BOOK);

		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		{
			int hand = event.getHand() == EnumHand.MAIN_HAND ? 1:-1;

			if(lastPartialTick!=mc.getRenderPartialTicks()){
				timer+=mc.getRenderPartialTicks();
			}
			GlStateManager.translate(1.4*hand, -.75, -2);
			GlStateManager.rotate(2, 0, 0, hand);
			GlStateManager.rotate(60, 1, 0, 0);

			GlStateManager.rotate(10, 0, 0, -1);
			draw(-.5, -.5, 0, .5, book_w,0, book_w/2, book_h, tex_size);
			GlStateManager.rotate(-20, 0, 0, -1);
			draw(0, -.5, .5, .5, book_w+book_w/2,0, book_w/2, book_h, tex_size);
			GlStateManager.rotate(10, 0, 0, -1);

			GlStateManager.translate(0, .01, 0);

			float a = ((float)Math.sin(timer/11)+1)+((float)Math.cos(timer/7)+1)*.6f+((float)Math.cos(timer/19)+1)*.8f;

			float k =item.getTagCompound().getFloat("cur_pow")/item.getTagCompound().getFloat("max_pow");

			GlStateManager.rotate(10+a, 0, 0, -1);
			draw(-.45, -.45, 0, .45, 0,0, book_w/2, book_h, tex_size);

			GlStateManager.translate(0, .01, 0);
			GlStateManager.color( .5f, .5f, .5f);
			draw(-.425+.4*(int)(power_w*k)/(double)power_w, -.425, -.025, -0.425+0.1473, (int)(power_w*k),book_h, (int)(power_w*(1-k)+1), power_h, tex_size);
			GlStateManager.color( .5f, .9f, 1);
			draw(-.425, -.425, -.425+.4*(int)(power_w*k)/(double)power_w, -0.425+0.1473, 0,book_h, (int)(power_w*k), power_h, tex_size);
			GlStateManager.color( 1, 1, 1);
			GlStateManager.translate(0, -.01, 0);

			GlStateManager.rotate(-20-a*2, 0, 0, -1);
			draw(0, -.45, .45, .45, book_w/2,0, book_w/2, book_h, tex_size);
			GlStateManager.rotate(10+a, 0, 0, -1);

		}
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		lastPartialTick = mc.getRenderPartialTicks();
	}

	private void draw(double x1, double y1, double x2, double y2, int tx, int ty, int w, int h, int s){

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer wr = tessellator.getBuffer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(x1, 0, y2).tex(tx/(double)s, (ty+h)/(double)s).endVertex();
		wr.pos(x2, 0, y2).tex((tx+w)/(double)s, (ty+h)/(double)s).endVertex();
		wr.pos(x2, 0, y1).tex((tx+w)/(double)s, ty/(double)s).endVertex();
		wr.pos(x1, 0, y1).tex(tx/(double)s, ty/(double)s).endVertex();
		tessellator.draw();

		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(x2, 0, y1).tex((tx+w)/(double)s, ty/(double)s).endVertex();
		wr.pos(x2, 0, y2).tex((tx+w)/(double)s, (ty+h)/(double)s).endVertex();
		wr.pos(x1, 0, y2).tex(tx/(double)s, (ty+h)/(double)s).endVertex();
		wr.pos(x1, 0, y1).tex(tx/(double)s, ty/(double)s).endVertex();
		tessellator.draw();
	}

	public static void reload(){
		TEX_BOOK = new ResourceLocation("incantations:textures/gui/book_inhand.png");
	}
}
