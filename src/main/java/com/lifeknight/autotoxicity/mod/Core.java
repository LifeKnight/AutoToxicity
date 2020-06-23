package com.lifeknight.autotoxicity.mod;

import com.lifeknight.autotoxicity.gui.hud.EnhancedHudText;
import com.lifeknight.autotoxicity.utilities.Chat;
import com.lifeknight.autotoxicity.variables.*;
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

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.lifeknight.autotoxicity.gui.hud.EnhancedHudText.textToRender;
import static net.minecraft.util.EnumChatFormatting.*;

@net.minecraftforge.fml.common.Mod(modid = Core.modId, name = Core.modName, version = Core.modVersion, clientSideOnly = true)
public class Core {
    public static final String
            modName = "AutoToxicity",
            modVersion = "1.0",
            modId = "autotoxicity";
    public static final EnumChatFormatting modColor = RED;
    public static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool(new LifeKnightThreadFactory());
    public static boolean onHypixel = false;
    public static GuiScreen guiToOpen = null;
    public static final LifeKnightBoolean runMod = new LifeKnightBoolean("Mod", "Main", true);
    public static final LifeKnightBoolean hudTextShadow = new LifeKnightBoolean("HUDTextShadow", "Invisible", true);
    public static final LifeKnightCycle defaultSeparator = new LifeKnightCycle("DefaultSeparator", "Invisible", new ArrayList<>(Arrays.asList(" > ", ": ", " | ", " - "))) {
        @Override
        public void onValueChange() {
            for (EnhancedHudText enhancedHudText : textToRender) {
                enhancedHudText.setSeparator(this.getValue());
            }
        }
    };
    public static final LifeKnightCycle defaultPrefixColor = new LifeKnightCycle("DefaultPrefixColor", "Invisible", new ArrayList<>(Arrays.asList(
            "Red",
            "Gold",
            "Yellow",
            "Green",
            "Aqua",
            "Blue",
            "Light Purple",
            "Dark Red",
            "Dark Green",
            "Dark Aqua",
            "Dark Blue",
            "Dark Purple",
            "White",
            "Gray",
            "Dark Gray",
            "Black"
    )), 12) {
        @Override
        public void onValueChange() {
            for (EnhancedHudText enhancedHudText : textToRender) {
                enhancedHudText.setPrefixColor(this.getValue());
            }
        }
    };
    public static final LifeKnightCycle defaultContentColor = new LifeKnightCycle("DefaultContentColor", "Invisible", new ArrayList<>(Arrays.asList(
            "Red",
            "Gold",
            "Yellow",
            "Green",
            "Aqua",
            "Blue",
            "Light Purple",
            "Dark Red",
            "Dark Green",
            "Dark Aqua",
            "Dark Blue",
            "Dark Purple",
            "White",
            "Gray",
            "Dark Gray",
            "Black"
    )), 12) {
        @Override
        public void onValueChange() {
            for (EnhancedHudText enhancedHudText : textToRender) {
                enhancedHudText.setContentColor(this.getValue());
            }
        }
    };

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

    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent event) {
        if (guiToOpen != null) {
            Minecraft.getMinecraft().displayGuiScreen(guiToOpen);
            guiToOpen = null;
        }

        if (Minecraft.getMinecraft().inGameHasFocus) {
            EnhancedHudText.doRender();
        }
    }

    public static void openGui(GuiScreen guiScreen) {
        guiToOpen = guiScreen;
    }
}