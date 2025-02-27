package me.pesekjak.hippo.utils.events;

import lombok.Getter;
import me.pesekjak.hippo.classes.ISkriptClass;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class NewSkriptClassEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Getter @NotNull
    private final ISkriptClass skriptClass;

    public NewSkriptClassEvent(@NotNull ISkriptClass skriptClass) {
        this.skriptClass = skriptClass;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
