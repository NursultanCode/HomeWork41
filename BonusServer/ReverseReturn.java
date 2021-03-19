package BonusServer;

public class ReverseReturn implements Printable{
    @Override
    public String action(String input) {
        StringBuilder input1 = new StringBuilder();
        input1.append(input);
        input1.reverse();
        return input1.toString();
    }
}
