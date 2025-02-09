package site.equipable.consoleLogs.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;
import site.equipable.consoleLogs.utils.BukkitLogEvent;

public class ExprLogMessage extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprLogMessage.class, String.class, ExpressionType.SIMPLE, "[the] log[ged] message");
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, ParseResult parseResult) {
        if (!getParser().isCurrentEvent(BukkitLogEvent.class)) {
            Skript.error("The 'logged message' expression can't be used outside of an on log event");
            return false;
        }
        return true;
    }

    @Override
    protected String @Nullable [] get(Event event) {
        return CollectionUtils.array(((BukkitLogEvent) event).getMessage());
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean debug) {
        return "the logged message";
    }
}
