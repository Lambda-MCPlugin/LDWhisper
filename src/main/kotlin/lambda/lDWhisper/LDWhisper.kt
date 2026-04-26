package lambda.lDWhisper

import lambda.lDWhisper.command.ReplyCommand
import lambda.lDWhisper.command.WhisperAdminCommand
import lambda.lDWhisper.command.WhisperCommand
import lambda.lDWhisper.config.ConfigManager
import lambda.lDWhisper.service.WhisperService
import org.bukkit.plugin.java.JavaPlugin

class LDWhisper : JavaPlugin() {

    lateinit var configManager: ConfigManager
        private set

    lateinit var whisperService: WhisperService
        private set

    override fun onEnable() {
        saveDefaultConfig()

        configManager = ConfigManager(this)
        whisperService = WhisperService(this, configManager)

        getCommand("귓속말")?.setExecutor(WhisperCommand(whisperService, configManager))
        getCommand("답장")?.setExecutor(ReplyCommand(whisperService, configManager))
        getCommand("귓속말관리")?.setExecutor(WhisperAdminCommand(this, configManager))

        logger.info("LDWhisper enabled.")
    }

    override fun onDisable() {
        logger.info("LDWhisper disabled.")
    }
}