package simulaattorinrunko5FX.src.simu.model;

import java.util.LinkedList;

import simulaattorinrunko5FX.src.eduni.distributions.ContinuousGenerator;
import simulaattorinrunko5FX.src.simu.framework.Kello;
import simulaattorinrunko5FX.src.simu.framework.Tapahtuma;
import simulaattorinrunko5FX.src.simu.framework.Tapahtumalista;
import simulaattorinrunko5FX.src.simu.framework.Trace;

/**
 * Luokka <code>Palvelupiste</code> .
 *
 * @author Eveliina, Juho, Katja, Sebastian
 * @version 1.0 (13.10.2022)
 */
public class Palvelupiste {
	private LinkedList<Asiakas> jono; // Tietorakennetoteutus
	Kello kello = Kello.getInstance();

	private ContinuousGenerator generator;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi skeduloitavanTapahtumanTyyppi;
	private boolean kaytossa;

	private int palveltujenLkm = 0;
	private int saapuneidenLkm = 0;
	private double palvelupisteenAktiiviaika = 0;
	private double ppKokonaisoleskeluaika = 0;
	private double ppKeskimaarainenLapimeno;
	private double ppKeskimaarainenJononPituus;
	private double ppSuoritusteho;

	// JonoStartegia strategia; //optio: asiakkaiden järjestys

	private boolean varattu = false;

	/**
	 * Konstruktori palvelupisteelle
	 * 
	 * @param generator      jakaumia varten
	 * @param tapahtumalista tapahtumien tallennusta varten
	 * @param tyyppi         poistumis tapahtuman tyyppi
	 */
	public Palvelupiste(ContinuousGenerator generator, Tapahtumalista tapahtumalista, TapahtumanTyyppi tyyppi) {
		this.tapahtumalista = tapahtumalista;
		this.generator = generator;
		this.skeduloitavanTapahtumanTyyppi = tyyppi;
		this.jono = new LinkedList<Asiakas>();
		this.kaytossa = false;
	}

	/*
	 * public Palvelupiste(ContinuousGenerator generator, Tapahtumalista
	 * tapahtumalista, TapahtumanTyyppi tyyppi, LinkedList<Asiakas> jono){
	 * this.tapahtumalista = tapahtumalista; this.generator = generator;
	 * this.skeduloitavanTapahtumanTyyppi = tyyppi; this.jono = jono; }
	 */
	/**
	 * Lisää jonoon uuden asiakkaan jonka saa parametrina ja lisää yhden lisää
	 * saapumis laskuriin
	 * 
	 * @param a uusi asiakas
	 */
	public void lisaaJonoon(Asiakas a) { // Jonon 1. asiakas aina palvelussa
		jono.add(a);
		saapuneidenLkm++;

	}

	/**
	 * Palauttaa tapahtuman tyypin
	 * 
	 * @return Tapahtumantyypin
	 */
	public TapahtumanTyyppi getTyyppi() {
		return skeduloitavanTapahtumanTyyppi;
	}

	/**
	 * Ottaa Asiakkaan jonosta
	 * 
	 * @return jonon ensimmäisen asiakkaan
	 */
	public Asiakas otaJonosta() { // Poistetaan palvelussa ollut
		varattu = false;
		palveltujenLkm++;
		return jono.poll();
	}

	/**
	 * Aloittaa palvelun asiakkaalle
	 */
	public void aloitaPalvelu() { // Aloitetaan uusi palvelu, asiakas on jonossa palvelun aikana

		Trace.out(Trace.Level.INFO, "Aloitetaan uusi palvelu asiakkaalle " + jono.peek().getId() + "tyyppi: "
				+ skeduloitavanTapahtumanTyyppi);

		varattu = true;
		double palveluaika = generator.sample();
		palvelupisteenAktiiviaika += palveluaika;
		tapahtumalista.lisaa(new Tapahtuma(skeduloitavanTapahtumanTyyppi, Kello.getInstance().getAika() + palveluaika));
	}

	/**
	 * Palauttaa onko palvelupiste vapaana
	 * 
	 * @return true tai false riippuen onko palvelupiste vapaa
	 */
	public boolean onVarattu() {
		return varattu;
	}

	/**
	 * Tarkistaa onko jonossa asiakasta
	 * 
	 * @return jos jono on isompi kuin 0 nii palauttaa true muuten false
	 */
	public boolean onJonossa() {
		return jono.size() != 0;
	}

	/**
	 * Palauttaa montako asiakasta on palveltu
	 * 
	 * @return palveltujen asiakkaiden lukumäärän
	 */
	public int getPalveltujenLkm() {
		return palveltujenLkm;
	}

	/**
	 * Palauttaaa montako asiakasta on saapunut
	 * 
	 * @return saapuneiden asiakkaiden lukumäärä
	 */
	public int getSaapuneidenLkm() {
		return saapuneidenLkm;
	}

	/**
	 * Palauttaa kuinka kauan palvelupiste on ollut aktiivisena
	 * 
	 * @return palvelupisteen aktiivi ajan
	 */
	public double getPalvelupisteenAktiiviaika() {
		return palvelupisteenAktiiviaika;
	}

	/**
	 * Palauttaa jonon pituudet
	 * 
	 * @return jonon pituus
	 */
	public int getJononPituus() {
		return jono.size();
	}

	/**
	 * Asettaa uuden generaattorin
	 * 
	 * @param generator
	 */
	public void setGeneraattori(ContinuousGenerator generator) {
		this.generator = generator;
	}

	/**
	 * Muuttaa palvelupisteen tilan
	 */
	public void muutaKaytossa() {
		kaytossa = !kaytossa;
	}

	/**
	 * Palauttaa palvelupisteen tilan
	 * 
	 * @return true jos palvelupiste on käytössä ja false jos ei ole
	 */
	public boolean getKaytossa() {
		return kaytossa;
	}

	/**
	 * Palauttaa palvelupisteen kokonaisoleskelu ajan
	 * 
	 * @return kuinka kauan palvelupisteessä on ollu asiakas
	 */
	public double getPpKokonaisoleskeluaika() {
		if (kaytossa) {
			return ppKokonaisoleskeluaika;
		}
		return 0;
	}

	/**
	 * Asettaa uuden oleskeluajan palvelupisteelle
	 * 
	 * @param oleskeluaika kuinka kauan palvelupisteellä on ollut asiakas
	 */
	public void setPpKokonaisoleskeluaika(double oleskeluaika) {
		this.ppKokonaisoleskeluaika += oleskeluaika;
	}

	/**
	 * Palauttaa palvelupisteen keskiarvon siitä kuinka kauan asiakkaalla on mennyt
	 * aikaa palvelupisteellä
	 * 
	 * @return keskiarvon läpimeno ajoista
	 */
	public double getPpKeskimaarainenLapimeno() {
		if (kaytossa) {
			return ppKeskimaarainenLapimeno;
		}
		return 0;
	}

	/**
	 * Palauttaa palvelupisteen jonon pituuden keskiarvon
	 * 
	 * @return jonon pituuden keskiarvon
	 */
	public double getPpKeskimaarainenJononPituus() {
		if (kaytossa) {
			return ppKeskimaarainenJononPituus;
		}
		return 0;
	}

	/**
	 * Palauuttaa palvelupisten suoritustehon
	 * 
	 * @return palvelupisteen suoritusteho
	 */
	public double getPpSuoritusteho() {
		if (kaytossa) {
			return ppSuoritusteho;
		}
		return 0;
	}

	/**
	 * Laskee johdetut suureet
	 */
	public void laskeJohdetutSuureet() {
		ppKeskimaarainenLapimeno = ppKokonaisoleskeluaika / palveltujenLkm;
		ppKeskimaarainenJononPituus = ppKokonaisoleskeluaika / kello.getAika();
		ppSuoritusteho = palveltujenLkm / kello.getAika();
	}

	/**
	 * Palauttaa raportin tuloksista
	 * 
	 * @return palauttaa double listan tuloksista
	 */
	public Double[] raportti() {
		Double[] tulokset = new Double[4];
		if (kaytossa) {
			tulokset[0] = ppKokonaisoleskeluaika;
			tulokset[1] = ppKeskimaarainenLapimeno;
			tulokset[2] = ppKeskimaarainenJononPituus;
			tulokset[3] = ppSuoritusteho;
		} else {
			tulokset[0] = 0.0;
			tulokset[1] = 0.0;
			tulokset[2] = 0.0;
			tulokset[3] = 0.0;
		}

		return tulokset;
	}
}
