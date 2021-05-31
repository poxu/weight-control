package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.dto.WeightDto;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SetWeight implements MessageHandler {
    public boolean process(Bot bot, Update update) {

        if (!update.hasCallbackQuery()) {
            if (update.hasMessage()) {
                try {
                    Double.parseDouble(update.getMessage().getText().replace(" ", "."));
                } catch (NumberFormatException e) {
                    System.out.println("Couldn't turn string into number");
                    return false;
                }
                LocalDateTime dateTime = LocalDateTime.now();
                LocalTime midday = LocalTime.of(12, 00);

                List<WeightDto> weights = bot.weightService.getMyWeight(update.getMessage().getFrom().getId().toString());
                if (bot.addWeight.addWeight(bot, update.getMessage(), weights)) {
                    return true;
                }
                WeightDto obj = weights.get(weights.size() - 1);
                boolean now = dateTime.toLocalTime().getHour() >= midday.getHour();
                boolean inRecording = obj.getDate().toLocalTime().getHour() >= midday.getHour();
                boolean condition = dateTime.toLocalDate().equals(obj.getDate().toLocalDate())
                        && now == inRecording;
                if (bot.addWeight.addWeight(bot, update.getMessage(), condition)) {
                    return true;
                }
                bot.redactWeight.redactWeight(bot, update.getMessage(), obj);
                return true;
            }
        }
        return false;
    }
}
