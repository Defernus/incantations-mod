package ru.def.incantations.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.def.incantations.incantations.IncantationHandler;
import ru.def.incantations.items.renders.RenderIncantationsBook;

/**
 * Created by Defernus on 12.05.2017.
 */
public class CommandReloadMod extends CommandBase {

	@Override
	public String getName() {
		return "inc_reload";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {

		RenderIncantationsBook.reload();

		sender.sendMessage(new TextComponentString("Succes"));
	}
}
