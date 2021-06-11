package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.service.WeightService;

@Service
public class GetWeightList implements MessageHandler {
    private final WeightService weightService;

    public GetWeightList(WeightService weightService) {
        this.weightService = weightService;
    }

    public boolean process(Bot bot, Update update) {
        if (!update.hasCallbackQuery()) {
            if (update.hasMessage()) {
                if (update.getMessage().getText().equals("/gw")) {
                    bot.sendMessage(update.getMessage(), weightService.getMyWeight(update.getMessage().getFrom().getId().toString()).toString());
                    return true;
                }
            }
        }
        return false;
    }
}
