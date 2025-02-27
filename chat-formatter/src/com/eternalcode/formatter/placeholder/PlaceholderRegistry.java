package com.eternalcode.formatter.placeholder;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlaceholderRegistry {

    private final Map<String, Placeholder> placeholders = new HashMap<>();
    private final Map<String, PlayerPlaceholder> playerPlaceholders = new HashMap<>();
    private final Map<String, DoublePlayerPlaceholder> doublePlayerPlaceholders = new HashMap<>();

    private final Set<PlaceholderStack> stacks = new HashSet<>();
    private final Set<PlayerPlaceholderStack> playerStacks = new HashSet<>();
    private final Set<DoublePlayerPlaceholderStack> doublePlayerStacks = new HashSet<>();

    public void placeholder(String key, Placeholder placeholder) {
        this.placeholders.put(key, placeholder);
    }

    public void playerPlaceholder(String key, PlayerPlaceholder placeholder) {
        this.playerPlaceholders.put(key, placeholder);
    }

    public void doublePlayerPlaceholder(String key, DoublePlayerPlaceholder placeholder) {
        this.doublePlayerPlaceholders.put(key, placeholder);
    }

    public void stack(PlaceholderStack stack) {
        this.stacks.add(stack);
    }

    public void playerStack(PlayerPlaceholderStack stack) {
        this.playerStacks.add(stack);
    }

    public void doublePlayerStack(DoublePlayerPlaceholderStack stack) {
        this.doublePlayerStacks.add(stack);
    }

    public String format(String text) {
        for (PlaceholderStack stack : stacks) {
            text = stack.apply(text);
        }

        for (Map.Entry<String, Placeholder> entry : placeholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().extract());
        }

        return text;
    }

    public String format(String text, Player target) {
        for (PlayerPlaceholderStack stack : playerStacks) {
            text = stack.apply(text, target);
        }

        for (Map.Entry<String, PlayerPlaceholder> entry : playerPlaceholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().extract(target));
        }

        return this.format(text);
    }

    public String format(String text, Player target, Player otherTarget) {
        for (DoublePlayerPlaceholderStack stack : doublePlayerStacks) {
            text = stack.apply(text, target, otherTarget);
        }

        for (Map.Entry<String, DoublePlayerPlaceholder> entry : doublePlayerPlaceholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue().extract(target, otherTarget));
        }

        return this.format(text, target);
    }

}
