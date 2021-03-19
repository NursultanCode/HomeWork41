package BonusServer;

public class UpperReturn implements Printable{
    @Override
    public String action(String message) {
        return message.substring(6,message.length()).toUpperCase();
    }
}
