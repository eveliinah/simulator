package simulaattorinrunko5FX.src.controller;

import java.util.ArrayList;

import dao.IDao;
import dao.TuloksetDao;
import entity.Tulokset;
import javafx.application.Platform;
import simulaattorinrunko5FX.src.simu.framework.IMoottori;
import simulaattorinrunko5FX.src.simu.model.OmaMoottori;
import simulaattorinrunko5FX.src.simu.model.Palvelupiste;
import simulaattorinrunko5FX.src.view.ISimulaattorinUI;
import simulaattorinrunko5FX.src.view.SimulaattorinGUI;

/**
 * Luokka <code>Kontrolleri</code> simulaattorin ohjain luokka.
 *
 * @author Eveliina, Juho, Katja, Sebastian
 * @version 1.0 (13.10.2022)
 */
public class Kontrolleri implements IKontrolleri { // UUSI

	private IMoottori moottori = new OmaMoottori(this);
	private ISimulaattorinUI ui;
	private IDao tDao = new TuloksetDao();

	private int lukuAhkera = 15;
	private int lukuReipas = 30;
	private int lukuPerus = 40;
	private int lukuLaiska = 65;
	private int lukuVetelä = 85;

	private int lukuRuuhkaTodPaljon = 5;
	private int lukuRuuhkaPaljon = 10;
	private int lukuRuuhkaNormaali = 15;
	private int lukuRuuhkaVahan = 25;
	private int lukuRuuhkaTodVahan = 100;

	public Kontrolleri(ISimulaattorinUI ui) {
		this.ui = ui;
	}

	// Moottorin ohjausta:

	@Override
	public void kaynnistaSimulointi() {
		// moottori = new OmaMoottori(this); // luodaan uusi moottorisäie jokaista
		// simulointia varten
		moottori.setSimulointiaika(ui.getAika());
		moottori.setViive(ui.getViive());
		int maara = ui.getMontakoPalvelupistettä();
		ui.getVisualisointi().tyhjennaNaytto(maara);
		((Thread) moottori).start();
		// ((Thread)moottori).run(); // Ei missään tapauksessa näin. Miksi?
	}

	@Override
	public void hidasta() { // hidastetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 1.10));
		visualisoiViive();
	}

	@Override
	public void nopeuta() { // nopeutetaan moottorisäiettä
		moottori.setViive((long) (moottori.getViive() * 0.9));
		visualisoiViive();
	}

	@Override
	public void skippaa() {
		moottori.setViive(0);
	}

	public void visualisoiViive() {
		Platform.runLater(new Runnable() {
			public void run() {
				long viive = moottori.getViive();
				ui.getVisualisointi().visualisoiViive(viive);
			}
		});
	}

	// Simulointitulosten välittämistä käyttöliittymään.
	// Koska FX-ui:n päivitykset tulevat moottorisäikeestä, ne pitää ohjata
	// JavaFX-säikeeseen:

	@Override
	public void naytaLoppuaika(int ajoId) {
		Tulokset tulokset = tDao.haeTulokset(ajoId);
		double aika = tulokset.getKokonaisaika();
		Platform.runLater(() -> ui.setLoppuaika(aika));
	}

	@Override
	public void naytaPalvellutAsiakkkat(int ajoId) {
		Tulokset tulokset = tDao.haeTulokset(ajoId);
		int asiakkaat = tulokset.getPalvellutAsiakkaat();
		Platform.runLater(() -> ui.setPalvellutAsiakkaat(asiakkaat));
	}

	/**
	 * Luo visualisoinnin laukkuasiakkaista
	 */
	public void visualisoiLaukkuAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().laukkuAsiakas();
			}
		});
	}

	/**
	 * Poistaa laukkuasiakkaan visualisoinnin
	 */
	public void poistaLaukkuAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaLaukkuAsiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiLaukutPalvelumaara(visualisointiJono[0].getPalveltujenLkm());
			}
		});
	}

	@Override
	public void palvelupisteidenAhkeruus() {
		int[] luvut = ui.getTurvapisteAhkeruus();
		((OmaMoottori) moottori).setKeskiarvot(luvunMuunnin(luvut[0]), luvunMuunnin(luvut[1]), luvunMuunnin(luvut[2]),
				luvunMuunnin(luvut[3]), luvunMuunnin(luvut[4]));
	}

	/**
	 * Asettaa uudet keskijakaumat saapumis tapahtumille, perustuen simulaattori
	 * guissa tehdyille valinnoille
	 */
	public void ruuhkaArvot() {
		int[] luvut = ((SimulaattorinGUI) ui).getRuuhkaArvot();
		((OmaMoottori) moottori).setRuuhkaArvot(luvunMuunninRuuhka(luvut[0]), luvunMuunninRuuhka(luvut[1]));
	}

	@Override
	public void palvelupisteidenMaara() {
		moottori = new OmaMoottori(this);
		int luku = ui.getMontakoPalvelupistettä();
		((OmaMoottori) moottori).otaPalvelupisteetKayttoon(luku);
	}

	/**
	 * Luo visiualisoinnin laukuttomalle asiakkaalle
	 */
	public void visualisoiLaukutonAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().laukutonAsiakas();
			}
		});
	}

	/**
	 * Päivittää visualisoinnin
	 */
	public void visuPaivitaNaytto() {
		Platform.runLater(new Runnable() {
			public void run() {
				int luku = ui.getMontakoPalvelupistettä();
				ui.getVisualisointi().tyhjennaNaytto(luku);
			}
		});
	}

	/**
	 * Poistaa laukuttoman asiakkaan visualisoinnin
	 */
	public void poistaLaukutonAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaLaukutonAsiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiCheckinPalvelumaara(visualisointiJono[6].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Visualisoi turva1 asiakkaan
	 */
	public void visualisoiTurva1Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().turva1Asiakas();
			}
		});
	}

	/**
	 * Poistaa turva1 asiakkaan visualisoinnin
	 */
	public void poistaTurva1Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaTurva1Asiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiTurva1Palvelumaara(visualisointiJono[1].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Visualisoi turva2 asiakkaan
	 */
	public void visualisoiTurva2Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().turva2Asiakas();
			}
		});
	}

	/**
	 * Poistaa turva2 asiakkaan visualisoinnin
	 */
	public void poistaTurva2Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaTurva2Asiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiTurva2Palvelumaara(visualisointiJono[2].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Visualisoi turva3 asiakkaan
	 */
	public void visualisoiTurva3Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().turva3Asiakas();
			}
		});
	}

	/**
	 * Poistaa turva3 asiakkaan visualisoinnin
	 */
	public void poistaTurva3Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaTurva3Asiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiTurva3Palvelumaara(visualisointiJono[3].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Visualisoi turva4 asiakkaan
	 */
	public void visualisoiTurva4Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().turva4Asiakas();
			}
		});
	}

	/**
	 * Poistaa turva4 asiakkaan visualisoinnin
	 */
	public void poistaTurva4Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaTurva4Asiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiTurva4Palvelumaara(visualisointiJono[4].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Visualisoi turva5 asiakkaan
	 */
	public void visualisoiTurva5Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().turva5Asiakas();
			}
		});
	}

	/**
	 * Poistaa turva5 asiakkaan visualisoinnin
	 */
	public void poistaTurva5Asiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaTurva5Asiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiTurva5Palvelumaara(visualisointiJono[5].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Visualisoi erityisturva asiakkaan
	 */
	public void visualisoiETurvaAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().eTurvaAsiakas();
			}
		});
	}

	/**
	 * Poistaa eriturva asiakkaan visualisoinnin
	 */
	public void poistaETurvaAsiakas() {
		Platform.runLater(new Runnable() {
			public void run() {
				ui.getVisualisointi().poistaETurvaAsiakas();
				Palvelupiste[] visualisointiJono = ((OmaMoottori) moottori).getPalvelupisteet();
				ui.getVisualisointi().visualisoiETurvaPalvelumaara(visualisointiJono[7].getPalveltujenLkm());
			}
		});
	}

	/**
	 * Muuntaa vetovalikon indeksi arvon vastaamaan sopivaa lukua käytettäväksi
	 * jakaumien asetukseen
	 * 
	 * @param luku indeksi arvo vetovalikosta
	 * @return muutetun arvon
	 */
	public int luvunMuunnin(int luku) {
		if (luku == 0) {
			return lukuAhkera;
		} else if (luku == 1) {
			return lukuReipas;
		} else if (luku == 2) {
			return lukuPerus;
		} else if (luku == 3) {
			return lukuLaiska;
		} else {
			return lukuVetelä;
		}
	}

	/**
	 * Muuntaa vetovalikon indeksi arvon vastaamaan sopivaa lukua käytettäväksi
	 * jakaumien asetukseen
	 * 
	 * @param luku indeksi arvo vetovalikosta
	 * @return muutetun arvon
	 */
	public int luvunMuunninRuuhka(int luku) {
		if (luku == 0) {
			return lukuRuuhkaTodPaljon;
		} else if (luku == 1) {
			return lukuRuuhkaPaljon;
		} else if (luku == 2) {
			return lukuRuuhkaNormaali;
		} else if (luku == 3) {
			return lukuRuuhkaVahan;
		} else {
			return lukuRuuhkaTodVahan;
		}
	}

	/**
	 * Haetaan tietokannasta halutun ajokerran tulos-olio. Haetaan tulos-oliosta
	 * kaikkien palvelupisteiden läpimenoajat ja annetaan ne parametrina GUI:lle
	 * 
	 * @param ajoId Tietokannasta haettavan ajokerran tuloksien id
	 */
	public void asetaLapimenoajat(int ajoId) {

		Tulokset tulokset = tDao.haeTulokset(ajoId);
		Double[] palautettavat = new Double[8];

//double laukut, double t1, double t2, double t3, double t4, double t5, double check, double erityis
		palautettavat[0] = tulokset.getMatkatavarapisteLapimeno();
		palautettavat[1] = tulokset.getTurvatarkastus1Lapimeno();
		palautettavat[2] = tulokset.getTurvatarkastus2Lapimeno();
		palautettavat[3] = tulokset.getTurvatarkastus3Lapimeno();
		palautettavat[4] = tulokset.getTurvatarkastus4Lapimeno();
		palautettavat[5] = tulokset.getTurvatarkastus5Lapimeno();
		palautettavat[6] = tulokset.getLahtoselvitysLapimeno();
		palautettavat[7] = tulokset.getEritysturvaLapimeno();

		Platform.runLater(
				() -> ((SimulaattorinGUI) ui).setLapimenoajat(palautettavat[0], palautettavat[1], palautettavat[2],
						palautettavat[3], palautettavat[4], palautettavat[5], palautettavat[6], palautettavat[7]));
	}

	/**
	 * Haetaan tietokannasta halutun ajokerran tulos-olio. Haetaan tulos-oliosta
	 * kaikkien palvelupisteiden kokonaisoleskeluajat ja annetaan ne parametrina
	 * GUI:lle
	 * 
	 * @param ajoId Tietokannasta haettavan ajokerran tuloksien id
	 */
	public void asetaKokonaisoleskeluajat(int ajoId) {
		Tulokset tulokset = tDao.haeTulokset(ajoId);
		Double[] palautettavat = new Double[8];

		// double laukut, double t1, double t2, double t3, double t4, double t5, double
		// check, double erityis
		palautettavat[0] = tulokset.getMatkatavarapisteKokonaisoleskelu();
		palautettavat[1] = tulokset.getTurvatarkastus1Kokonaisoleskelu();
		palautettavat[2] = tulokset.getTurvatarkastus2Kokonaisoleskelu();
		palautettavat[3] = tulokset.getTurvatarkastus3Kokonaisoleskelu();
		palautettavat[4] = tulokset.getTurvatarkastus4Kokonaisoleskelu();
		palautettavat[5] = tulokset.getTurvatarkastus5Kokonaisoleskelu();
		palautettavat[6] = tulokset.getLahtoselvitysKokonaisoleskelu();
		palautettavat[7] = tulokset.getErityisturvaKokonaisoleskelu();

		Platform.runLater(() -> ((SimulaattorinGUI) ui).setKokonaisoleskeluajat(palautettavat[0], palautettavat[1],
				palautettavat[2], palautettavat[3], palautettavat[4], palautettavat[5], palautettavat[6],
				palautettavat[7]));

	}

	/**
	 * Haetaan tietokannasta halutun ajokerran tulos-olio. Haetaan tulos-oliosta
	 * kaikkien palvelupisteiden keskimääräiset jononpituudet ja annetaan ne
	 * parametrina GUI:lle
	 * 
	 * @param ajoId Tietokannasta haettavan ajokerran tuloksien id
	 */
	public void asetaJononpituudet(int ajoId) {
		Tulokset tulokset = tDao.haeTulokset(ajoId);
		Double[] palautettavat = new Double[8];

		// double laukut, double t1, double t2, double t3, double t4, double t5, double
		// check, double erityis
		palautettavat[0] = tulokset.getMatkatavarapisteKaJononPituus();
		palautettavat[1] = tulokset.getTurvatarkastus1KaJononPituus();
		palautettavat[2] = tulokset.getTurvatarkastus2KaJononPituus();
		palautettavat[3] = tulokset.getTurvatarkastus3KaJononPituus();
		palautettavat[4] = tulokset.getTurvatarkastus4KaJononPituus();
		palautettavat[5] = tulokset.getTurvatarkastus5KaJononPituus();
		palautettavat[6] = tulokset.getLahtoselvitysKaJononPituus();
		palautettavat[7] = tulokset.getErityisturvaKaJononPituus();
		Platform.runLater(
				() -> ((SimulaattorinGUI) ui).setJononpituudet(palautettavat[0], palautettavat[1], palautettavat[2],
						palautettavat[3], palautettavat[4], palautettavat[5], palautettavat[6], palautettavat[7]));

	}

	/**
	 * Haetaan tietokannasta halutun ajokerran tulos-olio. Haetaan tulos-oliosta
	 * kaikkien palvelupisteiden suoritustehot ja annetaan ne parametrina GUI:lle
	 * 
	 * @param ajoId Tietokannasta haettavan ajokerran tuloksien id
	 */
	public void asetaSuoritustehot(int ajoId) {
		Tulokset tulokset = tDao.haeTulokset(ajoId);
		Double[] palautettavat = new Double[8];

		// double laukut, double t1, double t2, double t3, double t4, double t5, double
		// check, double erityis
		palautettavat[0] = tulokset.getMatkatavarapisteSuoritusteho();
		palautettavat[1] = tulokset.getTurvatarkastus1Suoritusteho();
		palautettavat[2] = tulokset.getTurvatarkastus2Suoritusteho();
		palautettavat[3] = tulokset.getTurvatarkastus3Suoritusteho();
		palautettavat[4] = tulokset.getTurvatarkastus4Suoritusteho();
		palautettavat[5] = tulokset.getTurvatarkastus5Suoritusteho();
		palautettavat[6] = tulokset.getLahtosselvitysSuoritusteho();
		palautettavat[7] = tulokset.getErityisturvaSuoritusteho();

		Platform.runLater(
				() -> ((SimulaattorinGUI) ui).setSuoritustehot(palautettavat[0], palautettavat[1], palautettavat[2],
						palautettavat[3], palautettavat[4], palautettavat[5], palautettavat[6], palautettavat[7]));

	}

	public void asetaParametriTulokset(int ajoId) {

		Tulokset tulokset = tDao.haeTulokset(ajoId);

		Platform.runLater(() -> ((SimulaattorinGUI) ui).setParametriTiedot(tulokset.getParametriCheckin(),
				tulokset.getParametriLaukut(), tulokset.getParametriTurva1(), tulokset.getParametriTurva2(),
				tulokset.getParametriTurva3(), tulokset.getParametriTurva4(), tulokset.getParametriTurva5()));
	}

	/**
	 * Ottaa ajokertalistan ja asettaa sen uuteen listaan jonka asettaa GUI:hin
	 *
	 */
	public void asetaListViewTietokanta() {
		ArrayList<String> lista = tDao.haeAjokertaLista();
		((SimulaattorinGUI) ui).setTietokantaLista(lista);
	}

	/**
	 * Lisää ajokerta listan uuteen listaan ja ottaa listan viimeisessä indeksissä
	 * olevan ajokerraan jonka asettaa GUI:hin
	 */
	public void asetaViimeisinAjokerta() {
		ArrayList<String> lista = tDao.haeAjokertaLista();
		int viimeisinId = Integer.valueOf(lista.get(lista.size() - 1));
		((SimulaattorinGUI) ui).setViimeisenAjonId(viimeisinId);

	}

	public void resetButtonKayttoon() {
		((SimulaattorinGUI) ui).resetButtonKayttoon();
	}

	public void setLukuAhkera(int lukuAhkera) {
		this.lukuAhkera = lukuAhkera;
	}

	public void setLukuReipas(int lukuReipas) {
		this.lukuReipas = lukuReipas;
	}

	public void setLukuPerus(int lukuPerus) {
		this.lukuPerus = lukuPerus;
	}

	public void setLukuLaiska(int lukuLaiska) {
		this.lukuLaiska = lukuLaiska;
	}

	public void setLukuVetelä(int lukuVetelä) {
		this.lukuVetelä = lukuVetelä;
	}

	public void setLukuRuuhkaTodPaljon(int lukuRuuhkaTodPaljon) {
		this.lukuRuuhkaTodPaljon = lukuRuuhkaTodPaljon;
	}

	public void setLukuRuuhkaPaljon(int lukuRuuhkaPaljon) {
		this.lukuRuuhkaPaljon = lukuRuuhkaPaljon;
	}

	public void setLukuRuuhkaNormaali(int lukuRuuhkaNormaali) {
		this.lukuRuuhkaNormaali = lukuRuuhkaNormaali;
	}

	public void setLukuRuuhkaVahan(int lukuRuuhkaVahan) {
		this.lukuRuuhkaVahan = lukuRuuhkaVahan;
	}

	public void setLukuRuuhkaTodVahan(int lukuRuuhkaTodVahan) {
		this.lukuRuuhkaTodVahan = lukuRuuhkaTodVahan;
	}

	public int getLukuAhkera() {
		return lukuAhkera;
	}

	public int getLukuReipas() {
		return lukuReipas;
	}

	public int getLukuPerus() {
		return lukuPerus;
	}

	public int getLukuLaiska() {
		return lukuLaiska;
	}

	public int getLukuVetelä() {
		return lukuVetelä;
	}

	public int getLukuRuuhkaTodPaljon() {
		return lukuRuuhkaTodPaljon;
	}

	public int getLukuRuuhkaPaljon() {
		return lukuRuuhkaPaljon;
	}

	public int getLukuRuuhkaNormaali() {
		return lukuRuuhkaNormaali;
	}

	public int getLukuRuuhkaVahan() {
		return lukuRuuhkaVahan;
	}

	public int getLukuRuuhkaTodVahan() {
		return lukuRuuhkaTodVahan;
	}

}
