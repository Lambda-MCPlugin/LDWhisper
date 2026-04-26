package lambda.lDWhisper.command

import lambda.lDWhisper.LDWhisper
import lambda.lDWhisper.config.ConfigManager
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class WhisperAdminCommand(
    private val plugin: LDWhisper,
    private val configManager: ConfigManager
) : CommandExecutor, TabCompleter {

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (!sender.isOp && !sender.hasPermission("ldwhisper.admin")) {
            sender.sendMessage(configManager.messageWithPrefix("no-permission"))
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage(configManager.messageWithPrefix("usage-admin"))
            return true
        }

        when (args[0].lowercase()) {
            "리로드", "reload" -> {
                configManager.reload()
                sender.sendMessage(configManager.messageWithPrefix("reload-success"))
            }

            else -> {
                sender.sendMessage(configManager.messageWithPrefix("usage-admin"))
            }
        }

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String> {
        if (!sender.isOp && !sender.hasPermission("ldwhisper.admin")) {
            return mutableListOf()
        }

        if (args.size == 1) {
            return listOf("리로드")
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .toMutableList()
        }

        return mutableListOf()
    }
}