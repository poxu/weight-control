package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.NotificationHandler;
import takred.weightcontrol.service.UserAccountService;

@Service
public class NotificationsByCommand implements NotificationHandler {
    private final UserAccountService userAccountService;

    public NotificationsByCommand(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }


    @Override
    public boolean process(Bot bot, Update update) {
        if (!update.hasCallbackQuery()) {
            if (update.hasMessage()) {
                if (update.getMessage().getText().equals("/n")) {
                    userAccountService.setNotifications(update);
                    String result = userAccountService.getNotificationsStatus(update.getMessage().getFrom().getId());
                    bot.sendMessage(update.getMessage(), result);
                    return true;
                }
            }
        }
        return false;
    }
}
