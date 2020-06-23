package com.lifeknight.autotoxicity.gui;

import com.lifeknight.autotoxicity.utilities.Miscellaneous;
import com.lifeknight.autotoxicity.variables.LifeKnightInteger;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;

public abstract class Manipulable {
    public static final ArrayList<Manipulable> manipulableComponents = new ArrayList<>();
    public final ArrayList<Object> connectedComponents = new ArrayList<>();
    public final LifeKnightInteger positionX;
    public final LifeKnightInteger positionY;

    public Manipulable(String name, int defaultX, int defaultY) {
        manipulableComponents.add(this);
        this.positionX = new LifeKnightInteger(name + "PositionX", "Invisible", defaultX, 0, 1920);
        this.positionY = new LifeKnightInteger(name + "PositionY", "Invisible", defaultY, 0, 1080);
    }

    public void updatePosition(int x, int y) {
        positionX.setValue(Miscellaneous.scaleTo1080pWidth(x));
        positionY.setValue(Miscellaneous.scaleTo1080pHeight(y));
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public int getXCoordinate() {
        int returnValue;
        if ((returnValue = Miscellaneous.scaleFrom1080pWidth(positionX.getValue())) < -3) {
            returnValue = -3;
            positionX.setValue(returnValue);
        } else if (returnValue + getWidth() > Miscellaneous.getGameWidth() + 3) {
            returnValue = Miscellaneous.getGameWidth() + 3 - getWidth();
            positionX.setValue(returnValue);
        }
        return returnValue;
    }

    public int getYCoordinate() {
        int returnValue;
        if ((returnValue = Miscellaneous.scaleFrom1080pHeight(positionY.getValue())) < -3) {
            returnValue = -3;
            positionY.setValue(returnValue);
        } else if (returnValue + getHeight() > Miscellaneous.getGameHeight() + 3) {
            returnValue = Miscellaneous.getGameHeight() + 3 - getHeight();
            positionY.setValue(returnValue);
        }
        return returnValue < 0 ? -3 : returnValue;
    }

    public void resetPosition() {
        positionX.reset();
        positionY.reset();
    }

    public String getDisplayText() {
        return "";
    }

    public abstract void drawButton(Minecraft minecraft, int mouseX, int mouseY, int xPosition, int yPosition, int width, int height, boolean isSelectedButton);
}
