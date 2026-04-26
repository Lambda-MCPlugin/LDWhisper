package lambda.lDWhisper.command

import lambda.lDWhisper.config.ConfigManager
import lambda.lDWhisper.service.WhisperService
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ReplyCommand(
    private val whisperService: WhisperService,
    private val configManager: ConfigManager
) : CommandExecutor {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            sender.sendMessage(configManager.messageWithPrefix("only-player"))
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(configManager.messageWithPrefix("usage-reply"))
            return true
        }

        val message = args.joinToString(" ")
        whisperService.sendReply(sender, message)

        return true
    }
}