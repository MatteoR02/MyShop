package Business.Factory;

public class NotificaSMS extends Notifica{

    @Override
    public boolean inviaNotifica() {
        System.out.println("Invio tramite notifica SMS");
        return true;
    }
}
