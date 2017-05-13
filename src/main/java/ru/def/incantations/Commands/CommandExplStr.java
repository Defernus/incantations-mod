package ru.def.incantations.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import ru.def.incantations.incantations.IncantationHandler;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Defernus on 12.05.2017.
 */
public class CommandExplStr extends CommandBase {

	@Override
	public String getName() {
		return "expl";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return null;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length!=1){
			sender.sendMessage(new TextComponentString("invalid sintaxis"));
			return;
		}

		IncantationHandler.explosionStr=new Float(args[0]);
		sender.sendMessage(new TextComponentString("Succes"));
	}
}
