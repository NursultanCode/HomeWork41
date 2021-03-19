package BonusServer;


import server.EchoServer;

public class Main {

    public static void main(String[] args) {
        System.out.println("Bonus Server");
        BonusServer bonusServer = BonusServer.bindToPort(9000);
        bonusServer.run();
    }

}