package panzer;

import net.fabricmc.api.ModInitializer;

public class ClientInit implements ModInitializer {
    @Override
    public void onInitialize() {
        Client.init();
    }
}
