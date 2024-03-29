package com.lifeknight.autotoxicity.utilities;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static net.minecraft.util.EnumChatFormatting.*;

public class Miscellaneous {
	public static int getRandomIntBetweenRange(int min, int max) {
		return ThreadLocalRandom.current().nextInt(min, max + 1);
	}

	public static int scale(int toScale) {
		switch (Minecraft.getMinecraft().gameSettings.guiScale) {
			case 0:
				return (int) ((toScale * 2) / (double)getScaleFactor());
			case 1: {
				return toScale * 2;
			}
			case 2: {
				return toScale;
			}
			default: {
				return (int) (toScale / 1.5);
			}
		}
	}

	public static int get2ndPanelCenter() {
		return getScaledHeight(300) + (getGameWidth()- getScaledWidth(300)) / 2;
	}

	public static int getSupposedWidth() {
		return 1920 / getScaleFactor();
	}

	public static int getSupposedHeight() {
		return 1080 / getScaleFactor();
	}

	public static int getScaledWidth(int widthIn) {
		return scale((int) (widthIn * ((double) getGameWidth() / (double) getSupposedWidth())));
	}

	public static int getScaledHeight(int heightIn) {
		return scale((int) (heightIn * ((double) getGameHeight() / (double) getSupposedHeight())));
	}

	private static int getScaleFactor() {
		int scaledWidth = Minecraft.getMinecraft().displayWidth;
		int scaledHeight = Minecraft.getMinecraft().displayHeight;
		int scaleFactor = 1;
		boolean flag = Minecraft.getMinecraft().isUnicode();
		int i = Minecraft.getMinecraft().gameSettings.guiScale;

		if (i == 0) {
			i = 1000;
		}

		while (scaleFactor < i && scaledWidth / (scaleFactor + 1) >= 320 && scaledHeight / (scaleFactor + 1) >= 240) {
			++scaleFactor;
		}

		if (flag && scaleFactor % 2 != 0 && scaleFactor != 1) {
			--scaleFactor;
		}

		return scaleFactor;
	}

	public static int getGameWidth() {
		if (Minecraft.getMinecraft().gameSettings.guiScale != 0) {
			return Minecraft.getMinecraft().displayWidth / Minecraft.getMinecraft().gameSettings.guiScale;
		}
		return (int) (Math.ceil(Minecraft.getMinecraft().displayWidth / (double)getScaleFactor()));
	}

	public static int getGameHeight() {
		if (Minecraft.getMinecraft().gameSettings.guiScale != 0) {
			return Minecraft.getMinecraft().displayHeight / Minecraft.getMinecraft().gameSettings.guiScale;
		}
		return (int) (Math.ceil(Minecraft.getMinecraft().displayHeight / (double)getScaleFactor()));
	}

	public static int scaleFrom1080pWidth(int widthIn) {
		int i = widthIn / getScaleFactor();
		return (int) (i * (getGameWidth() / (double) getSupposedWidth()));
	}

	public static int scaleFrom1080pHeight(int heightIn) {
		int i = heightIn / getScaleFactor();
		return (int) (i * (getGameHeight() / (double) getSupposedHeight()));
	}

	public static int scaleTo1080pWidth(int widthIn) {
		int i = widthIn * getScaleFactor();
		return (int) (i * (getSupposedWidth() / (double) getGameWidth()));
	}

	public static int scaleTo1080pHeight(int heightIn) {
		int i = heightIn * getScaleFactor();
		return (int) (i * (getSupposedHeight() / (double) getGameHeight()));
	}

	public static Object selectRandomItemFromArray(Object[] toArray) {
		return toArray[getRandomIntBetweenRange(0, toArray.length - 1)];
	}

	public static String getUsername() {
		return Minecraft.getMinecraft().thePlayer.getName();
	}
}
