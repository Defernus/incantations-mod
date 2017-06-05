package ru.def.incantations.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import ru.def.incantations.world.generator.IslandGenerator;

/**
 * Created by Defernus on 12.05.2017.
 */
public class CommandSpawnIsland extends CommandBase {

	@Override
	public String getName() {
		return "spawnIsland";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		/*if(args.length!=3){
			sender.sendMessage(new TextComponentString("invalid sintaxis"));
			return;
		}*/

		//IslandGenerator.generateIsland(sender.getPosition().getX(), sender.getPosition().getY(), sender.getPosition().getZ(), sender.getEntityWorld());

		sender.sendMessage(new TextComponentString("Succes"));
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 4;
	}
}
