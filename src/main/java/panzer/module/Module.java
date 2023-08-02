package panzer.module;

import panzer.Client;
import panzer.event.Event;
import panzer.event.events.EventGui;
import panzer.event.events.EventLivingUpdate;
import panzer.event.events.EventMotionUpdate;
import panzer.event.events.EventUpdate;
import panzer.setting.SettingManager;
import net.minecraft.client.MinecraftClient;

public class Module {
    public final SettingManager settingManager = Client.settingManager;
    public final MinecraftClient mc = MinecraftClient.getInstance();
    private Category category;
    private String name;
    private int key;
    public String info;
    private boolean toggle;
    public float arrayListX, arrayListY;
    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
        if (isToggle()) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void onEventMotionUpdate(EventMotionUpdate event) {
    }

    public void onLivingUpdate(EventLivingUpdate event) {
    }
    public void onUpdate(EventUpdate event) {
    }

    public void onGuiRender(EventGui event) {
    }

    public enum Category {
        COMBAT, MOVEMENT, RENDER, WORLD, SETTING, HIDE
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
