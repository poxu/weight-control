package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;

@Service
public class GetWeightList {

    public boolean process(Bot bot, Update update) {
        if (update.getMessage().getText().equals("/gw")) {
            bot.sendMessage(update.getMessage(), bot.weightService.getMyWeight(update.getMessage().getFrom().getId().toString()).toString());
            return true;
        }
        return false;
    }
}
