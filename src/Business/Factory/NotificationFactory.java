package Business.Factory;

import Model.Cliente;

public class NotificationFactory {

    public Notifica getCanaleNotifica(Cliente.CanalePreferitoType type) {

        if(type == null) type = Cliente.CanalePreferitoType.EMAIL;

        switch (type) {
            case EMAIL: return new NotificaEmail();
            case SMS: return new NotificaSMS();

        }
        return null;
    }
}
