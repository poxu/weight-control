package takred.weightcontrol.bot_commands;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import takred.weightcontrol.Bot;
import takred.weightcontrol.NotificationHandler;
import takred.weightcontrol.service.UserAccountService;

@Service
public class NotificationsByButton implements NotificationHandler {
    private final UserAccountService userAccountService;

    public NotificationsByButton(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Override
    public boolean process(Bot bot, Update update) {
        if (!update.hasMessage()) {
            if (update.hasCallbackQuery()) {
                if (update.getCallbackQuery().getData().equals("/n")) {
                    userAccountService.setNotifications(update);
                    String result = userAccountService.getNotificationsStatus(update.getCallbackQuery().getFrom().getId());
                    bot.sendMessage(update.getCallbackQuery().getMessage(), result);
                    return true;
                }
            }
        }
        return false;
    }
}
