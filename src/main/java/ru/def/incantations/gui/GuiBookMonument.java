package ru.def.incantations.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import ru.def.incantations.Core;
import ru.def.incantations.containers.ContainerBookMonument;

/**
 * Created by Defernus on 12.05.2017.
 */
public class GuiBookMonument extends GuiContainer {

	final ResourceLocation texture = new ResourceLocation(Core.MODID,"textures/gui/book_monument.png");

	public GuiBookMonument(ContainerBookMonument container){
		super(container);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		Minecraft mc=Minecraft.getMinecraft();

		mc.renderEngine.bindTexture(texture);
		drawTexturedModalRect((width-xSize)/2,(height-ySize)/2,0,0,xSize,ySize);
	}
}
