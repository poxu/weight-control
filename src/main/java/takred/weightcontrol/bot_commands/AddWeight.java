package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import takred.weightcontrol.Bot;
import takred.weightcontrol.dto.UserNameAndWeightDto;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

import java.util.List;

@Service
public class AddWeight{
    private final WeightService weightService;

    public AddWeight(WeightService weightService) {
        this.weightService = weightService;
    }

    public boolean addWeight(Bot bot, Message message, List<WeightDto> weights) {
        if (weights.isEmpty()) {
            weightService.addWeight(new UserNameAndWeightDto(message.getFrom().getId().toString(), Double.parseDouble(message.getText().replace(" ", "."))));
            bot.sendMessage(message, "Показатель веса добавлен в список.");
            return true;
        }
        return false;
    }

    public boolean addWeight(Bot bot, Message message, boolean condition) {
        if (!condition) {
            weightService.addWeight(new UserNameAndWeightDto(message.getFrom().getId().toString(), Double.parseDouble(message.getText().replace(" ", "."))));
            bot.sendMessage(message, "Показатель веса добавлен в список.");
            return true;
        }
        return false;
    }
}
