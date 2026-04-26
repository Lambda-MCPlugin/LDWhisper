package lambda.lDWhisper.config

import lambda.lDWhisper.LDWhisper
import lambda.lDWhisper.util.MessageUtil

class ConfigManager(
    private val plugin: LDWhisper
) {

    fun reload() {
        plugin.reloadConfig()
    }

    fun message(path: String): String {
        val prefix = plugin.config.getString("messages.prefix") ?: ""
        val raw = plugin.config.getString("messages.$path") ?: "&c메시지 설정 없음: messages.$path"
        return MessageUtil.color(raw.replace("%prefix%", prefix))
    }

    fun messageWithPrefix(path: String): String {
        val prefix = plugin.config.getString("messages.prefix") ?: ""
        val raw = plugin.config.getString("messages.$path") ?: "&c메시지 설정 없음: messages.$path"
        return MessageUtil.color(prefix + raw)
    }
}