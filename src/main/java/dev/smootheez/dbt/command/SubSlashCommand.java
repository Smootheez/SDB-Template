package dev.smootheez.dbt.command;

import jakarta.annotation.*;
import lombok.*;
import lombok.extern.slf4j.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.interactions.commands.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.*;

@Slf4j
@Getter
public abstract class SubSlashCommand implements ISlashCommand {
    private final String name;
    private final String description;
    private final List<OptionData> options = new ArrayList<>();

    protected SubSlashCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubcommandData subcommandData() {
        SubcommandData data = new SubcommandData(name, description);
        if (!options.isEmpty()) {
            log.debug("Adding {} options to subcommand: {}", options.size(), name);
            data.addOptions(options);
        }
        return data;
    }

    public SubSlashCommand addOption(OptionType type, String name, String description, boolean required) {
        this.options.add(new OptionData(type, name, description, required));
        return this;
    }
}
