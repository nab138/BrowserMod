package me.nabdev.browsermod.entities;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.github.puzzle.core.Identifier;
import finalforeach.cosmicreach.GameSingletons;
import finalforeach.cosmicreach.Threads;
import finalforeach.cosmicreach.entities.Entity;
import io.github.bonigarcia.wdm.WebDriverManager;
import me.nabdev.browsermod.Constants;
import me.nabdev.browsermod.ITexturableModel;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class Screen extends Entity {

    static Identifier id = new Identifier(Constants.MOD_ID, "screen");

    public static WebDriver driver;

    public Screen() {
        super(id.toString());
        Threads.runOnMainThread(
                () -> this.model = GameSingletons.entityModelLoader
                        .load(this, "model_screen.json", "screen.animation.json", "animation.screen.idle", "saturn.png")
        );

        if(driver == null) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--headless");
            driver = new FirefoxDriver(options);
            driver.manage().window().setSize(new Dimension(854, 480));
            driver.get("https://www.youtube.com/watch?v=LDU_Txk06tM");
            // Click play button
            var video = driver.findElement(By.id("movie_player"));
            video.click();
        }
        this.hasGravity = false;

    }

    @Override
    public void render(Camera camera) {
        if(this.model == null) return;
        this.localBoundingBox.max.set(854F / 16.0F, 480F / 16.0F, 0.1F);
        this.localBoundingBox.update();
        byte[] browserScreenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        // mirror the image horizontally
        Pixmap p = new Pixmap(browserScreenshot, 0, browserScreenshot.length);
        Pixmap flipped = new Pixmap(p.getWidth(), p.getHeight(), p.getFormat());
        for(int x = 0; x < p.getWidth(); x++){
            for(int y = 0; y < p.getHeight(); y++){
                flipped.drawPixel(x, y, p.getPixel(p.getWidth() - x - 1, y));
            }
        }
        Texture texture = new Texture(flipped);

        ((ITexturableModel) this.model).browserMod$setTexture(texture);
        super.render(camera);
    }
}