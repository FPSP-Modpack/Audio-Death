package portablejim.audiodeath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;

@Mod(
    acceptableRemoteVersions = "*",
    acceptedMinecraftVersions = "[1.7.10]",
    modid = AudioDeath.MODID,
    name = "Audio Death",
    version = Tags.VERSION)
public class AudioDeath {

    static final String MODID = "audiodeath";
    private static final Logger LOGGER = LogManager.getLogger(MODID);
    private static final List<String> SOUNDS_JSON_TEXT;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if (event.getSide() != Side.CLIENT) {
            LOGGER.info(
                "Server environment detected: Audio Death is client-only and can safely be removed on the server.");
            return;
        }

        MinecraftForge.EVENT_BUS.register(new ClientHandler());

        Path rootDir = event.getModConfigurationDirectory()
            .toPath()
            .getParent();

        if (Loader.isModLoaded("additionalresources")) {
            createSoundsJSON(
                rootDir.resolve("mods-resourcepacks")
                    .resolve(MODID));
            return;
        }
        if (Loader.isModLoaded("txloader")) {
            createSoundsJSON(
                rootDir.resolve("config")
                    .resolve("txloader")
                    .resolve("load")
                    .resolve(MODID));
            return;
        }
        if (Loader.isModLoaded("resourceloader")) {
            createSoundsJSON(
                rootDir.resolve("resources")
                    .resolve(MODID));
        }
    }

    private static void createSoundsJSON(Path path) {
        try {
            Files.createDirectories(path.resolve("sounds"));
            Path soundsJson = path.resolve("sounds.json");
            if (Files.notExists(soundsJson)) {
                Files.write(soundsJson, SOUNDS_JSON_TEXT);
            }
        } catch (IOException e) {
            LOGGER.warn("Failed to create sounds dir or sounds.json!", e);
        }
    }

    static {
        SOUNDS_JSON_TEXT = Arrays.asList(
            "{",
            "  \"audiodeath.death\": {",
            "    \"category\": \"record\",",
            "    \"sounds\": [ \"audiodeath:deathSound\" ]",
            "  }",
            "}");
    }
}
