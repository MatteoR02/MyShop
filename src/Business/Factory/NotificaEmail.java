package Business.Factory;

import Business.Email.Email;
import Business.Email.MyShopEmail;

public class NotificaEmail extends Notifica{

    @Override
    public boolean inviaNotifica() {
        Email email = new MyShopEmail(this.getCliente().getPersona().getEmail(),this.getTitolo(),this.getTesto(),null);
        email.inviaEmail();
        return true;
    }
}
