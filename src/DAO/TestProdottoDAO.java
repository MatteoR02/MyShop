package DAO;

import Model.*;

import java.sql.Timestamp;
import java.util.ArrayList;

public class TestProdottoDAO {
    public static void main(String[] args) {
        System.out.println("Stampo tutti i prodotti che ho nel database");
        ArticoloDAO articoloDAO = ArticoloDAO.getInstance();
        ArrayList<Articolo> prodotti = articoloDAO.loadAllProdotti();
        for (Articolo prodotto : prodotti){
            System.out.println(prodotto);
        }


        System.out.println("Stampo prodotto composito");
        ProdottoComposito prodottoComposito = articoloDAO.loadProdottoComposito(11);
        System.out.println(prodottoComposito);


        /*System.out.println("Stampo tutti i produttori che ho nel database");
        ProduttoreDAO produttoreDAO = ProduttoreDAO.getInstance();
        ArrayList<Produttore> produttori = produttoreDAO.loadAllProduttori();
        for (Produttore produttore : produttori){
            System.out.println(produttore);
        }



        CategoriaDAO categoriaDAO = CategoriaDAO.getInstance();

        ProdottoComposito mobiliCamera = new ProdottoComposito();
        Prodotto letto = new Prodotto();
        Prodotto comodino = new Prodotto();

        letto.setNome("letto");
        letto.setPrezzo(900F);
        letto.setCategoria(categoriaDAO.loadCategoria(22));
        letto.setProduttore(produttoreDAO.loadProduttore(1));

        comodino.setNome("comodino");
        comodino.setPrezzo(200F);
        comodino.setCategoria(categoriaDAO.loadCategoria(5));
        comodino.setProduttore(produttoreDAO.loadProduttore(1));

        mobiliCamera.addSottoProdotti(letto);
        mobiliCamera.addSottoProdotti(comodino);
        mobiliCamera.setNome("set camera da letto");
        mobiliCamera.setCategoria(categoriaDAO.loadCategoria(5));

        //articoloDAO.addProdotto(mobiliCamera);
        ArrayList<Integer> idProdotti = new ArrayList<>();
        idProdotti.add(1);
        idProdotti.add(2);

        articoloDAO.createComposition(idProdotti, "set tavolo e lampada", 5);*/

    }
}
