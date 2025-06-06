package site.equipable.consoleLogs;

import ch.njol.skript.SkriptAddon;
import net.itsthesky.disky.DiSky;
import net.itsthesky.disky.api.modules.DiSkyModule;
import net.itsthesky.disky.api.modules.DiSkyModuleInfo;
import site.equipable.consoleLogs.utils.LogAppender;
import site.equipable.consoleLogs.utils.RateLimitHandler;

import java.io.File;
import java.util.concurrent.Executors;


public final class ConsoleLogs extends DiSkyModule {

    public ConsoleLogs(DiSkyModuleInfo info, File moduleJar) {
        super(info, moduleJar);
        new LogAppender().start();
    }

    @Override
    public void init(DiSky diSky, SkriptAddon skriptAddon) {
        RateLimitHandler.initializeExecutor();
        loadClasses("site.equipable.consoleLogs.elements");
    }

    @Override
    public void shutdown() {
        RateLimitHandler.shutdownExecutor();
        super.shutdown();
    }
}
