package panzer.setting;

import java.util.ArrayList;
import panzer.module.Module;
public class Setting {
    private String settingName, mode;
    private ArrayList<String> modes;

    private boolean enable;

    private double value, min, max, step;

    private Module module;

    private Type type;

    public Setting(String settingName, boolean enable, Module module) {
        this.settingName = settingName;
        this.enable = enable;
        this.type = Type.BOOLEAN;
        this.module = module;
    }

    public Setting(String settingName, String mode, ArrayList<String> modes, Module module) {
        this.settingName = settingName;
        this.mode = mode;
        this.modes = modes;
        this.type = Type.MODES;
        this.module = module;
    }

    public Setting(String settingName, double value, double min, double max, double step, Module module) {
        this.settingName = settingName;
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
        this.type = Type.VALUES;
        this.module = module;
    }

    public boolean isBoolean() {
        return type == Type.BOOLEAN;
    }

    public boolean isMode() {
        return type == Type.MODES;
    }

    public boolean isValue() {
        return type == Type.VALUES;
    }

    public String getSettingName() {
        return settingName;
    }

    public void setSettingName(String settingName) {
        this.settingName = settingName;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public ArrayList<String> getModes() {
        return modes;
    }

    public void setModes(ArrayList<String> modes) {
        this.modes = modes;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        BOOLEAN, MODES, VALUES;
    }
}
