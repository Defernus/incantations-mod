package ru.def.incantations.tileentity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.def.incantations.tileentity.TileEntityWritingTable;

/**
 * Created by Defernus on 14.05.2017.
 */
public class RenderWritingTable extends TileEntitySpecialRenderer<TileEntityWritingTable> {

	private static final ResourceLocation TEXTURE_PAPER = new ResourceLocation("incantations:textures/entity/writing_table_paper.png");
	private static final ResourceLocation TEXTURE_FONT = new ResourceLocation("incantations:textures/entity/rune_chars.png");

	@Override
	public void renderTileEntityAt(TileEntityWritingTable te, double x, double y, double z, float partialTicks, int destroyStage) {
		super.renderTileEntityAt(te,x,y,z,partialTicks,destroyStage);

		if(te.hasPaper){
			GlStateManager.pushMatrix();
			GlStateManager.disableLighting();
			{
				float light = te.getWorld().getLight(te.getPos())/(float)15;
				GlStateManager.color(light, light, light);

				GlStateManager.translate(x,y+0.94,z);

				Minecraft mc = Minecraft.getMinecraft();

				TextureManager re = mc.getTextureManager();

				GlStateManager.enableBlend();
				GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				re.bindTexture(TEXTURE_PAPER);

				GlStateManager.rotate(90,1,0,0);
				GlStateManager.scale(1/32., 1/32., 1/32.);

				if(te.getPos().getX()==te.posWN.getX()&&te.getPos().getZ()==te.posWN.getZ()) { //WN
					drawPaper(0,re);
				}
				else if(te.getPos().getX()==te.posES.getX()&&te.getPos().getZ()==te.posWN.getZ()) { //EN
					drawPaper(2,re);
				}
				else if(te.getPos().getX()==te.posWN.getX()&&te.getPos().getZ()==te.posES.getZ()) { //WS
					drawPaper(6,re);
				}
				else if(te.getPos().getX()==te.posES.getX()&&te.getPos().getZ()==te.posES.getZ()) { //ES
					drawPaper(8,re);
				}
				else if(te.getPos().getX()>te.posWN.getX()&&te.getPos().getX()<te.posES.getX()&&te.getPos().getZ()==te.posWN.getZ()) { //N
					drawPaper(1,re);
				}
				else if(te.getPos().getX()>te.posWN.getX()&&te.getPos().getX()<te.posES.getX()&&te.getPos().getZ()==te.posES.getZ()) { //S
					drawPaper(7,re);
				}
				else if(te.getPos().getZ()>te.posWN.getZ()&&te.getPos().getZ()<te.posES.getZ()&&te.getPos().getX()==te.posWN.getX()) { //W
					drawPaper(3,re);
				}
				else if(te.getPos().getZ()>te.posWN.getZ()&&te.getPos().getZ()<te.posES.getZ()&&te.getPos().getX()==te.posES.getX()) { //E
					drawPaper(5,re);
				}
				else { //M
					drawPaper(4,re);
				}

				re.bindTexture(TEXTURE_FONT);
				GlStateManager.color(0F, 0F, 0F, .5f+(1-light)/2);
				GlStateManager.translate(16, 16, -0.1);

				GlStateManager.rotate((te.facing+2)*90,0,0,1);

				GlStateManager.translate(-11, -12, 0);

				for(int i=0;i<4;i++){
					int ch=te.chars[i];
					drawChar(ch,i,re);
				}

			}
			GlStateManager.enableLighting();
			GlStateManager.popMatrix();
		}
	}

	private void drawPaper(int n, TextureManager re){
		re.bindTexture(TEXTURE_PAPER);

		int s=96;
		int tx=(s/3)*(n%3),ty=(s/3)*(n/3);

		VertexBuffer wr = Tessellator.getInstance().getBuffer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(0, s/3, 0).tex(tx/(double)s, (ty+s/3)/(double)s).endVertex();
		wr.pos(s/3, s/3, 0).tex((tx+s/3)/(double)s, (ty+s/3)/(double)s).endVertex();
		wr.pos(s/3, 0, 0).tex((tx+s/3)/(double)s, ty/(double)s).endVertex();
		wr.pos(0, 0, 0).tex(tx/(double)s, ty/(double)s).endVertex();
		Tessellator.getInstance().draw();
	}

	private void drawChar(int ch,int pos, TextureManager re){
		int tx=(8*ch)%128,ty=8*((8*ch)/128);
		int h=128,w=128;

		GlStateManager.translate(16*(pos%2), 16*(pos/2), 0);

		VertexBuffer wr = Tessellator.getInstance().getBuffer();
		wr.begin(7, DefaultVertexFormats.POSITION_TEX);
		wr.pos(0, 8, 0).tex(tx/(double)w, (ty+8)/(double)h).endVertex();
		wr.pos(8, 8, 0).tex((tx+8)/(double)w, (ty+8)/(double)h).endVertex();
		wr.pos(8, 0, 0).tex((tx+8)/(double)w, ty/(double)h).endVertex();
		wr.pos(0, 0, 0).tex(tx/(double)w, ty/(double)h).endVertex();
		Tessellator.getInstance().draw();
		GlStateManager.translate(-16*(pos%2), -16*(pos/2), 0);
	}
}
