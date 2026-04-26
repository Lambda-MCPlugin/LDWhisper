package lambda.lDWhisper.service

import lambda.lDWhisper.LDWhisper
import lambda.lDWhisper.config.ConfigManager
import lambda.lDWhisper.util.MessageUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.UUID

class WhisperService(
    private val plugin: LDWhisper,
    private val configManager: ConfigManager
) {

    private val replyTargets = mutableMapOf<UUID, UUID>()

    fun sendWhisper(sender: Player, receiver: Player, message: String) {
        if (sender.uniqueId == receiver.uniqueId) {
            sendSafe(sender, configManager.messageWithPrefix("cannot-send-self"))
            return
        }

        val senderFormat = MessageUtil.applyPlaceholders(
            configManager.message("whisper-to-sender"),
            sender.name,
            receiver.name,
            message
        )

        val receiverFormat = MessageUtil.applyPlaceholders(
            configManager.message("whisper-to-receiver"),
            sender.name,
            receiver.name,
            message
        )

        sendSafe(sender, senderFormat)
        sendSafe(receiver, receiverFormat)

        replyTargets[sender.uniqueId] = receiver.uniqueId
        replyTargets[receiver.uniqueId] = sender.uniqueId
    }

    fun sendReply(sender: Player, message: String) {
        val targetId = replyTargets[sender.uniqueId]

        if (targetId == null) {
            sendSafe(sender, configManager.messageWithPrefix("no-reply-target"))
            return
        }

        val receiver = Bukkit.getPlayer(targetId)

        if (receiver == null || !receiver.isOnline) {
            sendSafe(sender, configManager.messageWithPrefix("reply-target-offline"))
            return
        }

        sendWhisper(sender, receiver, message)
    }

    private fun sendSafe(player: Player, message: String) {
        player.scheduler.run(plugin, { _ ->
            player.sendMessage(message)
        }, null)
    }
}