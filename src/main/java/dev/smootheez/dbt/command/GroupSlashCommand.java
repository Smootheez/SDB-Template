package dev.smootheez.dbt.command;

import lombok.*;
import lombok.extern.slf4j.*;
import net.dv8tion.jda.api.events.interaction.command.*;
import net.dv8tion.jda.api.interactions.commands.build.*;

import java.util.*;

@Slf4j
@Getter
public abstract class GroupSlashCommand implements ISlashCommand {
    private final String name;
    private final String description;
    private final Map<String, SubSlashCommand> subcommands = new HashMap<>();

    protected GroupSlashCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public SubcommandGroupData subcommandGroupData() {
        if (subcommands.isEmpty())
            throw new IllegalStateException("No subcommands found for group: " + name);

        SubcommandGroupData data = new SubcommandGroupData(name, description);
        for (SubSlashCommand subcommand : subcommands.values()) {
            log.debug("Adding subcommand {} to group {}", subcommand.getName(), name);
            data.addSubcommands(subcommand.subcommandData());
        }
        return data;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        String subcommandName = event.getSubcommandName();
        SubSlashCommand subcommand = subcommands.get(subcommandName);
        if (subcommand != null) {
            subcommand.execute(event);
        } else {
            log.error("Unknown subcommand: {} for group: {}", subcommandName, name);
            event.reply("An unknown subcommand was used.").setEphemeral(true).queue();
        }
    }

    public GroupSlashCommand addSubcommand(SubSlashCommand subcommand) {
        this.subcommands.put(subcommand.getName(), subcommand);
        return this;
    }
}
