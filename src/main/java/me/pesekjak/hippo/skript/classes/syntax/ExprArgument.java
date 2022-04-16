package me.pesekjak.hippo.skript.classes.syntax;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.pesekjak.hippo.utils.SkriptUtils;
import me.pesekjak.hippo.utils.events.classcontents.MethodCallEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public class ExprArgument extends SimpleExpression<Object> {

    static {
        Skript.registerExpression(ExprArgument.class, Object.class, ExpressionType.COMBINED,
                "arg[ument]-%integer%"
        );
    }

    private Expression<Integer> numberExpression;

    @Override
    protected Object @NotNull [] get(@NotNull Event event) {
        if(!(event instanceof MethodCallEvent)) return new Object[0];
        return new Object[] { ((MethodCallEvent) event).getArguments().get((Integer) numberExpression.getSingle(event)) };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public @NotNull Class<?> getReturnType() {
        return Object.class;
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "argument";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        numberExpression = SkriptUtils.defendExpression(expressions[0]);
        return getParser().isCurrentEvent(MethodCallEvent.class);
    }
}
