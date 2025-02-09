package site.equipable.consoleLogs;

import ch.njol.skript.SkriptAddon;
import net.itsthesky.disky.DiSky;
import net.itsthesky.disky.api.modules.DiSkyModule;
import net.itsthesky.disky.api.modules.DiSkyModuleInfo;
import org.bukkit.Bukkit;
import site.equipable.consoleLogs.utils.LogAppender;

import java.io.File;
import java.util.logging.Logger;


public final class ConsoleLogs extends DiSkyModule {

    public static final Logger logger = Logger.getLogger("ConsoleLogs");
    public String lastLoggedMessage;
    private static ConsoleLogs instance;

    public ConsoleLogs(DiSkyModuleInfo info, File moduleJar) {
        super(info, moduleJar);
        new LogAppender().start();
        instance = this;
    }

    public static ConsoleLogs getInstance() {
        return instance;
    }

    @Override
    public void init(DiSky diSky, SkriptAddon skriptAddon) {
        loadClasses("site.equipable.consoleLogs.elements");
    }

}
