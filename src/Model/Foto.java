package Model;

import java.sql.Blob;

public class Foto {

    private int id;
    private Blob immagine;

    public Foto(Blob immagine) {
        this.immagine = immagine;
    }

    public Foto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Blob getImmagine() {
        return immagine;
    }

    public void setImmagine(Blob immagine) {
        this.immagine = immagine;
    }
}
