package takred.weightcontrol;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface NotificationHandler {

    boolean process(Bot bot, Update update);
}
