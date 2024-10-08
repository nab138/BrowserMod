package me.nabdev.browsermod;

import com.github.puzzle.core.PuzzleRegistries;
import com.github.puzzle.core.localization.ILanguageFile;
import com.github.puzzle.core.localization.LanguageManager;
import com.github.puzzle.core.localization.files.LanguageFileVersion1;
import com.github.puzzle.core.resources.ResourceLocation;
import com.github.puzzle.game.events.OnPreLoadAssetsEvent;
import com.github.puzzle.loader.entrypoint.interfaces.ModInitializer;
import finalforeach.cosmicreach.entities.EntityCreator;
import me.nabdev.browsermod.entities.Screen;
import me.nabdev.browsermod.commands.Commands;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class BrowserMod implements ModInitializer {
    @Override
    public void onInit() {
        PuzzleRegistries.EVENT_BUS.register(this);

        EntityCreator.registerEntityCreator("browser-mod:screen", Screen::new);

        Commands.register();
    }

    @Subscribe
    public void onEvent(OnPreLoadAssetsEvent event) {
        ILanguageFile lang = null;
        try {
            lang = LanguageFileVersion1.loadLanguageFromString(new ResourceLocation(Constants.MOD_ID, "languages/en-US.json").locate().readString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        LanguageManager.registerLanguageFile(lang);
    }

}
