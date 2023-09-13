package Test;

import Business.Factory.Notifica;
import Business.Factory.NotificaEmail;
import Business.Factory.NotificationFactory;
import Model.Cliente;
import org.junit.Assert;
import org.junit.Test;

public class NotificaFactoryTest {
    @Test
    public void getCanaleNotificaTest(){

        Cliente c = new Cliente();

        //creo la factory di notifiche
        NotificationFactory factory = new NotificationFactory();
        //imposto email e push come tipi di notifiche preferiti dell'utente
        c.setCanalePreferito(Cliente.CanalePreferitoType.EMAIL);
        //prendo il canale preferito
        Notifica n = factory.getCanaleNotifica(c.getCanalePreferito());
        n.setCliente(c);

        if(n != null){
            String nome = "Messaggio di test";
            String corpo = "Test";
            n.setTitolo(nome);
            n.setTesto(corpo);
            boolean esito = n.inviaNotifica();

            Assert.assertTrue(esito);
        }


    }
}
