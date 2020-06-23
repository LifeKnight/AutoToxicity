package com.lifeknight.autotoxicity.mod;

import com.lifeknight.autotoxicity.utilities.Chat;
import com.lifeknight.autotoxicity.utilities.Miscellaneous;
import com.lifeknight.autotoxicity.variables.LifeKnightBoolean;
import com.lifeknight.autotoxicity.variables.LifeKnightList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

import java.util.Timer;
import java.util.TimerTask;

import static net.minecraft.util.EnumChatFormatting.RED;

@net.minecraftforge.fml.common.Mod(modid = Core.modId, name = Core.modName, version = Core.modVersion, clientSideOnly = true)
public class Core {
    public static final String
            modName = "AutoToxicity",
            modVersion = "1.0",
            modId = "autotoxicity";
    public static final EnumChatFormatting modColor = RED;
    public static boolean onHypixel = false;
    public static GuiScreen guiToOpen = null;
    public static final LifeKnightBoolean runMod = new LifeKnightBoolean("Mod", "Main", true);
    public static final LifeKnightList.LifeKnightStringList killMessages = new LifeKnightList.LifeKnightStringList("KillMessage", "Messages");
    public static final LifeKnightList.LifeKnightStringList bedBreakMessages = new LifeKnightList.LifeKnightStringList("BedBreakMessage", "Messages");
    public static final LifeKnightList.LifeKnightStringList winMessages = new LifeKnightList.LifeKnightStringList("WinMessages", "Messages");
    public static boolean type = false;
    public static Configuration configuration;

    @EventHandler
    public void init(FMLInitializationEvent initEvent) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new ModCommand());

        configuration = new Configuration();
    }

    @SubscribeEvent
    public void onConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Chat.sendQueuedChatMessages();
                onHypixel = !Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hypixel.net");
            }
        }, 2000);
    }

    @SubscribeEvent
    public void onChatMessageReceived(ClientChatReceivedEvent event) {
        if (onHypixel) {
            String message = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getFormattedText());
            if (!message.contains(":")) {
                if (message.contains("Cross Teaming") || message.contains("/16)!") || message.contains("/12)!")) {
                    type = false;
                } else if (message.contains("Teaming is not") || message.contains("(8/8)!") || message.contains("(2/2)!")) {
                    type = true;
                }
                if (runMod.getValue()) {
                    if (message.contains("Kill") && message.startsWith("+") && message.contains("coins") && !message.contains("Time Played") && !message.contains("Teammate") && !message.contains("Win") && !message.contains("Final Kill") && !message.contains("Bed Destroyed") && !message.contains("Assist")) {
                        if (killMessages.getValue().isEmpty()) {
                            if (type) {
                                Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(killMessages.getValue().toArray()), Chat.ALL);
                            } else {
                                Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(killMessages.getValue().toArray()), Chat.SHOUT);
                            }
                        }
                    } else if (message.startsWith("+") && message.contains("coins") && !message.contains("Time Played") && !message.contains("Win") && message.contains("Final Kill")) {
                        if (killMessages.getValue().isEmpty()) {
                            if (type) {
                                Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(killMessages.getValue().toArray()), Chat.ALL);
                            } else {
                                Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(killMessages.getValue().toArray()), Chat.SHOUT);
                            }
                        }
                    } else if (message.startsWith("BED DESTRUCTION") && message.contains(Miscellaneous.getUsername())) {
                        if (bedBreakMessages.getValue().isEmpty()) {
                            if (type) {
                                Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(bedBreakMessages.getValue().toArray()), Chat.ALL);
                            } else {
                                Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(bedBreakMessages.getValue().toArray()), Chat.SHOUT);
                            }
                        }
                    } else if (message.startsWith("+") && message.contains("coins") && !message.contains("Time Played") && message.contains("Win") && !message.contains("Final Kill")) {
                        if (!winMessages.getValue().isEmpty()) {
                            Chat.sendChatMessage((String) Miscellaneous.selectRandomItemFromArray(winMessages.getValue().toArray()), Chat.ALL);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }
    }

    public static void openGui(GuiScreen guiScreen) {
        guiToOpen = guiScreen;
    }
}