package lambda.lDWhisper.util

import org.bukkit.ChatColor

object MessageUtil {

    fun color(message: String): String {
        return ChatColor.translateAlternateColorCodes('&', message)
    }

    fun applyPlaceholders(
        message: String,
        sender: String,
        receiver: String,
        content: String
    ): String {
        return message
            .replace("%sender%", sender)
            .replace("%receiver%", receiver)
            .replace("%message%", content)
    }
}