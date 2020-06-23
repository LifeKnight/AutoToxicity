package com.lifeknight.autotoxicity.mod;

import com.lifeknight.autotoxicity.gui.LifeKnightGui;
import com.lifeknight.autotoxicity.gui.ManipulableGui;
import com.lifeknight.autotoxicity.gui.components.LifeKnightButton;
import com.lifeknight.autotoxicity.utilities.Chat;
import com.lifeknight.autotoxicity.utilities.Miscellaneous;
import com.lifeknight.autotoxicity.utilities.Text;
import com.lifeknight.autotoxicity.variables.LifeKnightVariable;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static com.lifeknight.autotoxicity.mod.Core.*;
import static net.minecraft.util.EnumChatFormatting.*;

public class ModCommand extends CommandBase {
    private final List<String> aliases = Collections.singletonList("at");

    public String getCommandName() {
        return modId;
    }

    public String getCommandUsage(ICommandSender arg0) {
        return modId;
    }


    public boolean canCommandSenderUseCommand(ICommandSender arg0) {
        return true;
    }

    public List<String> getCommandAliases() {
        return aliases;
    }

    public boolean isUsernameIndex(String[] arg0, int arg1) {
        return false;
    }

    public int compareTo(ICommand o) {
        return 0;
    }

    public void processCommand(ICommandSender arg0, String[] arg1) throws CommandException {
        openGui(new LifeKnightGui("[" + modVersion + "] " + modName, LifeKnightVariable.getVariables(), new ArrayList<>(Arrays.asList(
                new LifeKnightButton("Edit Kill Messages") {
                    @Override
                    public void work() {

                    }
                }
        ))));
    }

    public void addMainCommandMessage() {
        StringBuilder result = new StringBuilder(DARK_GREEN + "/" + modId);

        for (String command : mainCommands) {
            result.append(" ").append(command).append(",");
        }

        Chat.addChatMessage(result.substring(0, result.length() - 1));
    }
}
