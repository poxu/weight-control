package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

@Service
public class RedactWeight {
    private final WeightService weightService;

    public RedactWeight(WeightService weightService) {
        this.weightService = weightService;
    }

    public void redactWeight(Bot bot, Message message, WeightDto obj) {
        weightService.setWeight(obj, Double.parseDouble(message.getText().replace(" ", ".")));
        bot.sendMessage(message, "Показатель веса отредактирован.");
    }
}
