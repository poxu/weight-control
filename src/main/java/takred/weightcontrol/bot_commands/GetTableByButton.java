package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetTableByButton implements MessageHandler {
    private final WeightService weightService;

    public GetTableByButton(WeightService weightService) {
        this.weightService = weightService;
    }

    @Override
    public boolean process(Bot bot, Update update){
        if (!update.hasMessage()) {
            if (update.hasCallbackQuery()) {
                if (update.getCallbackQuery().getData().equals("/gwt")) {
                    List<WeightDto> lastTenWeights = getLastTenWeights(weightService.getMyWeight(update.getCallbackQuery().getFrom().getId().toString()));
                    List<String> table = getTable(lastTenWeights);
                    bot.sendMessage(update.getCallbackQuery(), table);
                }
            }
        }
        return false;
    }

    public List<WeightDto> getLastTenWeights(List<WeightDto> dtos) {
        if (dtos.size() <= 10) {
            return dtos;
        }
        List<WeightDto> lastTenWeights = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            lastTenWeights.add(dtos.get(dtos.size() - i));
        }
        return lastTenWeights;
    }

    public List<String> getTable(List<WeightDto> dtos) {
        List<String> table = new ArrayList<>();
        table.add("``` |------------------|-------|```");
        table.add("``` | Дата             | Вес   |```");
        table.add("``` |------------------|-------|```");
        for (int i = 0; i < dtos.size(); i++) {
            WeightDto dto = dtos.get(i);
            int hour = dto.getDate().getHour();
            int minute = dto.getDate().getMinute();
            String line;
            line = "``` | " + dto.getDate().toLocalDate() + " ";
            if (hour < 10){
                line = line + "0" + hour + ":";
            } else {
                line = line + hour + ":";
            }
            if (minute < 10){
                line = line + "0" + minute;
            } else {
                line = line + minute;
            }
            line = line + " | " + dto.getWeight();
            int iteration = 6 - dto.getWeight().toString().length();
            for (int j = 0; j < iteration; j++) {
                line = line + " ";
            }
            line = line + "|```";
            table.add(line);
            table.add("``` |------------------|-------|```");
        }
        return table;
    }

}
