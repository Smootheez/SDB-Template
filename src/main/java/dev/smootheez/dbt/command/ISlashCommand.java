package dev.smootheez.dbt.command;

import net.dv8tion.jda.api.events.interaction.command.*;

public interface ISlashCommand {
    void execute(SlashCommandInteractionEvent event);
}
