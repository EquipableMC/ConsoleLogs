package site.equipable.consoleLogs.elements.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.doc.Since;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import site.equipable.consoleLogs.utils.BukkitLogEvent;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Blueyescat
 */
public class ExprLogLevel extends SimpleExpression<StandardLevel> {
  
  static {
    Skript.registerExpression(ExprLogLevel.class, StandardLevel.class, ExpressionType.SIMPLE, "[the] log[ged] [message] level");
  }
  
  @Override
  public boolean init(final Expression<?> @NotNull [] exprs, final int matchedPattern, final @NotNull Kleenean isDelayed, final @NotNull ParseResult parseResult) {
    return true;
  }
  
  @Override
  protected StandardLevel @NotNull [] get(final @NotNull Event e) {
    return CollectionUtils.array(((BukkitLogEvent) e).getLogEvent().getLevel().getStandardLevel());
  }
  
  @Override
  public boolean isSingle() {
    return true;
  }
  
  @Override
  public @NotNull Class<? extends StandardLevel> getReturnType() {
    return StandardLevel.class;
  }
  
  @Override
  public @NotNull String toString(final @Nullable Event e, final boolean debug) {
    return "log level";
  }
  
}
