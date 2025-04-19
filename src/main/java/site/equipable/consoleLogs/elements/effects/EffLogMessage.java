package site.equipable.consoleLogs.elements.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EffLogMessage extends Effect {

    static {
        Skript.registerEffect(EffLogMessage.class,
                "(print|send|log) %strings% with [the|a] severity [of] fatal to [the] console",
                "(print|send|log) %strings% with [the|a] severity [of] error to [the] console",
                "(print|send|log) %strings% with [the|a] severity [of] warn[ing] to [the] console",
                "(print|send|log) %strings% with [the|a] severity [of] info[rmation] to [the] console",
                "(print|send|log) %strings% with [the|a] severity [of] debug to [the] console",
                "(print|send|log) %strings% with [the|a] severity [of] trace to [the] console");
    }

    public enum LogLevelEnum {
        FATAL, ERROR, WARN, INFO, DEBUG, TRACE
    }

    private Expression<String> messages;

    private LogLevelEnum logLevelEnum;

    @Override
    public boolean init(final Expression<?> @NotNull [] exprs, final int matchedPattern, final @NotNull Kleenean isDelayed, final @NotNull ParseResult parser) {
        messages = (Expression<String>) exprs[0];

        logLevelEnum = LogLevelEnum.values()[matchedPattern];
        return true;
    }

    @Override
    protected void execute(final @NotNull Event event) {
        Logger logger = LogManager.getRootLogger();
        for (String message : messages.getArray(event)) {
            switch (logLevelEnum) {
                case FATAL -> logger.fatal(message);
                case ERROR -> logger.error(message);
                case WARN -> logger.warn(message);
                case INFO -> logger.info(message);
                case DEBUG -> logger.debug(message);
                case TRACE -> logger.trace(message);
            }
        }
    }

    @Override
    public @NotNull String toString(final @Nullable Event event, final boolean debug) {
        return "log " + messages.toString(event, debug) + " to the console";
    }
}
