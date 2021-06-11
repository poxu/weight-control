package takred.weightcontrol.bot_commands;

import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.ChartCreator;
import takred.weightcontrol.MessageHandler;
import takred.weightcontrol.dto.WeightDto;
import takred.weightcontrol.service.WeightService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class GetChartByButton implements MessageHandler {
    private final WeightService weightService;

    public GetChartByButton(WeightService weightService) {
        this.weightService = weightService;
    }

    public boolean process(Bot bot, Update update) throws IOException {
        if (!update.hasMessage()) {
            if (update.hasCallbackQuery()) {
                if (update.getCallbackQuery().getData().equals("/gwc")) {
                    ChartCreator chartCreator = new ChartCreator();
                    List<WeightDto> dtos = weightService.getMyWeight(update.getCallbackQuery().getFrom().getId().toString());
                    CategoryDataset categoryDataset = chartCreator.createDataset(dtos);
                    JFreeChart chart = chartCreator.createChart(categoryDataset, getLowConfines(dtos), getHighConfines(dtos));
                    BufferedImage bufferedImage = chart.createBufferedImage(1000, 1000);
                    File outputfile = new File("chart.png");
                    ImageIO.write(bufferedImage, "png", outputfile);
                    bot.sendPhoto(update.getCallbackQuery(), outputfile);
                    return true;
                }
            }
        }
        return false;
    }

    public int getLowConfines(List<WeightDto> dtos) {
        int lowConfines = dtos.get(0).getWeight().intValue();
        for (int i = 1; i < dtos.size(); i++) {
            WeightDto weightDto = dtos.get(i);
            if (weightDto.getWeight() < lowConfines) {
                lowConfines = weightDto.getWeight().intValue();
            }
        }
        if (lowConfines <= 10) {
            return 0;
        }
        return lowConfines - 10;
    }

    public int getHighConfines(List<WeightDto> dtos) {
        int highConfines = 0;
        for (int i = 0; i < dtos.size(); i++) {
            WeightDto weightDto = dtos.get(i);
            if (weightDto.getWeight() > highConfines) {
                highConfines = weightDto.getWeight().intValue();
            }
        }

        return highConfines + 10;
    }
}
