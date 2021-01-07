package net.ugudango.luum;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.ugudango.luum.setup.Registration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Luum.MOD_ID)
public class Luum
{
    public static final String MOD_ID = "luum";
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public Luum() {
        Registration.register();
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }
}
