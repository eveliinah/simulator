package dao;

import java.util.ArrayList;

import entity.Tulokset;

public interface IDao {
	public void lisaaTulos(Tulokset ajokerta);

	public Tulokset haeTulokset(int ajokertaId);

	public void poistaTulos(int ajokertaId);

	public ArrayList<String> haeAjokertaLista();
}
