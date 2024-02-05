package portablejim.audiodeath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientHandler {

    private static int audio = 0;
    private final ISound deathSound;

    public ClientHandler() {
        this.deathSound = PositionedSoundRecord
            .func_147673_a(new ResourceLocation(AudioDeath.MODID, "audiodeath.death"));
    }

    @SubscribeEvent
    public void handleDeath(GuiOpenEvent event) {
        if (event.gui instanceof GuiGameOver && Minecraft.getMinecraft().currentScreen == null
            && !Minecraft.getMinecraft().thePlayer.isDead) {
            if (audio == 0) {
                Minecraft.getMinecraft()
                    .getSoundHandler()
                    .playSound(this.deathSound);
                audio = 1;
            }
        } else {
            Minecraft.getMinecraft()
                .getSoundHandler()
                .stopSound(this.deathSound);
            audio = 0;
        }
    }

}
