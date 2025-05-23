package site.equipable.consoleLogs.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import site.equipable.consoleLogs.utils.BukkitLogEvent;
import site.equipable.consoleLogs.utils.RateLimitHandler;

public class ExprLastLogMessage extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLastLogMessage.class, String.class, ExpressionType.SIMPLE, "[the] last log[ged] message");
    }

    @Override
    public boolean init(final Expression<?> @NotNull [] exprs, final int matchedPattern, final @NotNull Kleenean isDelayed, final @NotNull ParseResult parseResult) {
        if (getParser().isCurrentEvent(BukkitLogEvent.class)) {
            Skript.error("The 'last logged message' expression can't be used inside of an on log event.");
            return false;
        }
        return true;
    }

    @Override
    protected String @NotNull [] get(final @NotNull Event e) {
        return new String[]{RateLimitHandler.lastLoggedMessage};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public @NotNull String toString(final @Nullable Event e, final boolean debug) {
        return "the last logged message";
    }


}
