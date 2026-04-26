package lambda.lDWhisper.command

import lambda.lDWhisper.config.ConfigManager
import lambda.lDWhisper.service.WhisperService
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player

class WhisperCommand(
    private val whisperService: WhisperService,
    private val configManager: ConfigManager
) : CommandExecutor, TabCompleter {

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

        if (args.size < 2) {
            sender.sendMessage(configManager.messageWithPrefix("usage-whisper"))
            return true
        }

        val target = Bukkit.getPlayer(args[0])

        if (target == null || !target.isOnline) {
            sender.sendMessage(configManager.messageWithPrefix("player-not-found"))
            return true
        }

        val message = args.drop(1).joinToString(" ")
        whisperService.sendWhisper(sender, target, message)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (args.size == 1) {
            return Bukkit.getOnlinePlayers()
                .map { it.name }
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .toMutableList()
        }

        return mutableListOf()
    }
}