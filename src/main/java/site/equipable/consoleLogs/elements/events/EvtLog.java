package site.equipable.consoleLogs.elements.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import site.equipable.consoleLogs.utils.BukkitLogEvent;

public class EvtLog extends SkriptEvent {

    static {
        Skript.registerEvent("Log", EvtLog.class, BukkitLogEvent.class, "[console] log")
                .description("This is called when a message is sent in console. Note that using broadcast on the on log event will infinitely loop.")
                .examples("on log:")
                .since("1.0.0");
    }


    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof BukkitLogEvent;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "on log";
    }
}
