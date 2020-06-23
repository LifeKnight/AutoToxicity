package com.lifeknight.autotoxicity.variables;

import com.lifeknight.autotoxicity.utilities.Chat;
import net.minecraft.util.EnumChatFormatting;

import java.io.IOException;
import java.util.ArrayList;

import static com.lifeknight.autotoxicity.mod.Core.configuration;

public abstract class LifeKnightList<E> extends LifeKnightVariable {
    private final ArrayList<E> defaultValues;
    private ArrayList<E> values;

    public LifeKnightList(String name, String group, ArrayList<E> values) {
        super(name, group, true);
        defaultValues = values;
        this.values = new ArrayList<>();
        getVariables().add(this);
    }

    public LifeKnightList(String name, String group) {
        this(name, group, new ArrayList<>());
    }

    @Override
    public ArrayList<E> getValue() {
        return new ArrayList<>(values);
    }

    public void setValue(ArrayList<E> values) {
        this.values = values;
        configuration.updateConfigurationFromVariables();
        onSetValue();
    }

    public E get(int index) throws IOException {
        if (index > -1 && index < values.size()) {
            return values.get(index);
        }
        throw new IOException("Invalid index! (Requested: " + index + "; size: " + values.size());
    }

    public void addElement(E element) throws IOException {
        if (!values.contains(element)) {
            values.add(element);
            configuration.updateConfigurationFromVariables();
            onAddElement();
        } else {
            throw new IOException(super.getName() + " already contains " + element + "!");
        }
    }

    public void removeElement(E element) throws IOException {
        if (values.contains(element)) {
            values.remove(element);
            configuration.updateConfigurationFromVariables();
            onRemoveElement();
        } else {
            throw new IOException(super.getName() + " does not contain " + element + "!");
        }
    }

    public void clear() {
        values.clear();
        configuration.updateConfigurationFromVariables();
        onClear();
    }

    public void setValueFromCSV(String CSV) {
        if (CSV.contains(",")) {
            try {
                String[] elements = CSV.split(",");
                for (String element : elements) {
                    try {
                        values.add(fromString(element));
                    } catch (Exception e) {
                        Chat.queueChatMessageForConnection(EnumChatFormatting.RED + "An error occurred when trying to parse the value of " +
                                EnumChatFormatting.YELLOW + "\"" + element + "\"" + EnumChatFormatting.RED + ". It will not be added to " + super.getName() + ".");
                    }
                }
            } catch (Exception e) {
                Chat.queueChatMessageForConnection(EnumChatFormatting.RED + "An error occurred while trying to parse the value of " + super.getName() + ". It will assume its default value.");
            }
        }
        if (values.size() == 0) {
            values = new ArrayList<>(defaultValues);
        }
    }

    public abstract E fromString(String string);

    public String toCSV() {
        StringBuilder result = new StringBuilder();

        for (E element : values) {
            result.append(asString(element)).append(",");
        }

        return result.toString();
    }

    public abstract String asString(E element);

    @Override
    public void reset() {
        values = defaultValues;
    }

    public void onAddElement() {
    }

    public void onRemoveElement() {
    }

    public void onClear() {
    }

    public void onSetValue() {
    }

    public static class LifeKnightStringList extends LifeKnightList<String> {

        public LifeKnightStringList(String name, String group, ArrayList<String> values) {
            super(name, group, values);
        }

        public LifeKnightStringList(String name, String group) {
            this(name, group, new ArrayList<>());
        }

        @Override
        public String fromString(String string) {
            return string;
        }

        @Override
        public String asString(String element) {
            return element;
        }
    }

    public static class LifeKnightIntegerList extends LifeKnightList<Integer> {

        public LifeKnightIntegerList(String name, String group, ArrayList<Integer> values) {
            super(name, group, values);
        }

        public LifeKnightIntegerList(String name, String group) {
            this(name, group, new ArrayList<>());
        }

        @Override
        public Integer fromString(String string) {
            return Integer.parseInt(string);
        }

        @Override
        public String asString(Integer element) {
            return String.valueOf(element);
        }
    }

    public static class LifeKnightDoubleList extends LifeKnightList<Double> {

        public LifeKnightDoubleList(String name, String group, ArrayList<Double> values) {
            super(name, group, values);
        }

        public LifeKnightDoubleList(String name, String group) {
            this(name, group, new ArrayList<>());
        }

        @Override
        public Double fromString(String string) {
            return Double.parseDouble(string);
        }

        @Override
        public String asString(Double element) {
            return String.valueOf(element);
        }
    }
}
