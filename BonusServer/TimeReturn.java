package BonusServer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeReturn implements Printable{
    @Override
    public String action(String message) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
