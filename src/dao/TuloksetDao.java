package dao;

import java.util.ArrayList;
import java.util.List;

import entity.Tulokset;
import jakarta.persistence.EntityManager;

/**
 * Luokka <code>TuloksetDao</code> .
 *
 * @author Eveliina, Juho, Katja, Sebastian
 * @version 1.0 (13.10.2022)
 */
public class TuloksetDao implements IDao {
	/**
	 * Lisää ajokerran tulokset tietokantaan
	 * 
	 * @param ajokerta yhden ajokerran tulokset-olio
	 */
	public void lisaaTulos(Tulokset ajokerta) {
		EntityManager em = datasource.JpaConn.getInstance();
		em.getTransaction().begin();
		em.persist(ajokerta);
		em.getTransaction().commit();
	}

	/**
	 * Hae ajokerran tulokset tietokannasta
	 * 
	 * @param ajokertaId haettavan ajokerran id
	 */
	public Tulokset haeTulokset(int ajokertaId) {
		EntityManager em = datasource.JpaConn.getInstance();
		em.getTransaction().begin();
		Tulokset t = em.find(Tulokset.class, ajokertaId);
		em.getTransaction().commit();
		return t;
	}

	/**
	 * Poista ajokerran tulokset tietokannasta
	 * 
	 * @param ajokertaId haettavan ajokerran id
	 */
	public void poistaTulos(int ajokertaId) {
		EntityManager em = datasource.JpaConn.getInstance();
		em.getTransaction().begin();
		Tulokset t = em.find(Tulokset.class, ajokertaId);
		if (t != null) {
			em.remove(t);
		}
		em.getTransaction().commit();
	}

	/**
	 * Hae kaikki tulokset tietokannasta ja luo niistä pelkät ajoId:t sisältävä
	 * lista
	 * 
	 */
	public ArrayList<String> haeAjokertaLista() {
		EntityManager em = datasource.JpaConn.getInstance();
		String jpqlQuery = "SELECT t FROM Tulokset t";
		jakarta.persistence.Query q = em.createQuery(jpqlQuery);
		List<Tulokset> tuloksetLista = q.getResultList();
		ArrayList<String> listViewLista = new ArrayList<>();
		for (int i = 0; i < tuloksetLista.size(); i++) {
			String ajokerta = Integer.toString(tuloksetLista.get(i).getAjokertaId());
			listViewLista.add(ajokerta);
		}

		return listViewLista;
	}
}
