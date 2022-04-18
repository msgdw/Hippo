package me.pesekjak.hippo.skript.classes.syntax.contents.methods;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import me.pesekjak.hippo.classes.Constant;
import me.pesekjak.hippo.classes.Modifier;
import me.pesekjak.hippo.classes.Type;
import me.pesekjak.hippo.classes.contents.Method;
import me.pesekjak.hippo.skript.classes.SkriptClassBuilder;
import me.pesekjak.hippo.skript.classes.Pair;
import me.pesekjak.hippo.utils.SkriptUtils;
import me.pesekjak.hippo.utils.events.NewSkriptClassEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class EffMethod extends Effect {

    static {
        Skript.registerEffect(EffMethod.class,
                "%javamodifiers% %pair%\\([%-pairs%]\\) [throws %-asmtypes%] [default %-constant%]"
        );
    }

    private Expression<Modifier> modifierExpression;
    private Expression<Pair> pairExpression;
    private Expression<Pair> argumentExpression;
    private Expression<Type> exceptionExpression;
    private Expression<Constant> constantExpression;

    @Override
    protected void execute(@NotNull Event event) {
    }

    @Override
    public @NotNull String toString(Event event, boolean b) {
        return "new not code running method";
    }

    @Override
    public boolean init(Expression<?> @NotNull [] expressions, int i, @NotNull Kleenean kleenean, SkriptParser.@NotNull ParseResult parseResult) {
        modifierExpression = SkriptUtils.defendExpression(expressions[0]);
        pairExpression = SkriptUtils.defendExpression(expressions[1]);
        argumentExpression = SkriptUtils.defendExpression(expressions[2]);
        exceptionExpression = SkriptUtils.defendExpression(expressions[3]);
        constantExpression = SkriptUtils.defendExpression(expressions[4]);
        if (!getParser().isCurrentEvent(NewSkriptClassEvent.class)) return false;
        build(SkriptClassBuilder.getCurrentEvent());
        return true;
    }

    protected void build(@NotNull Event event) {
        Pair pair = pairExpression.getSingle(event);
        Method method = new Method(pair.getPrimitiveType(), pair.getType(), pair.getName());
        method.setRunnable(false);
        if(modifierExpression != null) {
            Arrays.stream(modifierExpression.getAll(event)).toList().forEach(method::addModifier);
        }
        if(argumentExpression != null) {
            Arrays.stream(argumentExpression.getAll(event)).toList().forEach((argumentPair) -> method.addArgument(argumentPair.asArgument()));
        }
        if(exceptionExpression != null) {
            Arrays.stream(exceptionExpression.getAll(event)).toList().forEach(method::addException);
        }
        if(constantExpression != null) {
            method.setDefaultConstant(constantExpression.getSingle(event));
        }
        ((NewSkriptClassEvent) event).getStackedAnnotations().forEach(method::addAnnotation);
        ((NewSkriptClassEvent) event).clearStackedAnnotations();
        SkriptClassBuilder.getRegisteringClass().addMethod(pair.getName() + ":" + method.getDescriptor(), method);
    }
}
