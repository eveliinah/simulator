package simulaattorinrunko5FX.src.simu.model;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import dao.IDao;
import dao.TuloksetDao;
import entity.Tulokset;
import simulaattorinrunko5FX.src.controller.IKontrolleri;
import simulaattorinrunko5FX.src.controller.Kontrolleri;
import simulaattorinrunko5FX.src.eduni.distributions.Negexp;
import simulaattorinrunko5FX.src.simu.framework.Kello;
import simulaattorinrunko5FX.src.simu.framework.Moottori;
import simulaattorinrunko5FX.src.simu.framework.Saapumisprosessi;
import simulaattorinrunko5FX.src.simu.framework.Tapahtuma;

/**
 * Luokka <code>OmaMoottori</code> ????.
 *
 * @author Eveliina, Juho, Katja, Sebastian
 * @version 1.0 (13.10.2022)
 */
public class OmaMoottori extends Moottori {

	private Kello kello;
	private Saapumisprosessi s_laukut;
	private Saapumisprosessi s_checkin;
	private Palvelupiste laukut1;
	private Palvelupiste turva1;
	private Palvelupiste turva2;
	private Palvelupiste turva3;
	private Palvelupiste turva4;
	private Palvelupiste turva5;
	private Palvelupiste checkin1;
	private Palvelupiste erityisturva1;
	private ArrayList<Palvelupiste> turvatarkastuspisteet;
	private int turvaKa1 = 15;
	private int turvaKa2 = 15;
	private int turvaKa3 = 15;
	private int turvaKa4 = 15;
	private int turvaKa5 = 15;

	private Tulokset tulokset = new Tulokset();
	private int summa = 0;
	private Double[] checkin1Tulokset;
	private Double[] laukut1Tulokset;
	private Double[] turva1Tulokset;
	private Double[] turva2Tulokset;
	private Double[] turva3Tulokset;
	private Double[] turva4Tulokset;
	private Double[] turva5Tulokset;
	private Double[] erityisturva1Tulokset;

	private int poistuneetT1 = 0;
	private int poistuneetT2 = 0;
	private int poistuneetT3 = 0;
	private int poistuneetT4 = 0;
	private int poistuneetT5 = 0;
	private int poistuneetErityis = 0;

	IDao tuloksetDao = new TuloksetDao();

	/**
	 * Konstruktori OmaMoottorille.
	 * 
	 * @param kontrolleri moottoriluokan ohjausta varten
	 */
	public OmaMoottori(IKontrolleri kontrolleri) { // UUSI

		super(kontrolleri); // UUSI
		// LinkedList<Asiakas> jono = new LinkedList<>();

		palvelupisteet = new Palvelupiste[8];

		laukut1 = new Palvelupiste(new Negexp(10, 15), tapahtumalista, TapahtumanTyyppi.P_LAUKUT);
		turva1 = new Palvelupiste(new Negexp(turvaKa1, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA1);
		turva2 = new Palvelupiste(new Negexp(turvaKa2, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA2);
		turva3 = new Palvelupiste(new Negexp(turvaKa3, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA3);
		turva4 = new Palvelupiste(new Negexp(turvaKa4, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA4);
		turva5 = new Palvelupiste(new Negexp(turvaKa5, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA5);
		checkin1 = new Palvelupiste(new Negexp(5, 15), tapahtumalista, TapahtumanTyyppi.P_CHECKIN);
		erityisturva1 = new Palvelupiste(new Negexp(20, 15), tapahtumalista, TapahtumanTyyppi.P_ERITYISTURVA);
		palvelupisteet[0] = laukut1;
		palvelupisteet[1] = turva1;
		palvelupisteet[2] = turva2;
		palvelupisteet[3] = turva3;
		palvelupisteet[4] = turva4;
		palvelupisteet[5] = turva5;
		palvelupisteet[6] = checkin1;
		palvelupisteet[7] = erityisturva1;

		s_checkin = new Saapumisprosessi(new Negexp(5, 15), tapahtumalista, TapahtumanTyyppi.S_CHECKIN);
		s_laukut = new Saapumisprosessi(new Negexp(5, 15), tapahtumalista, TapahtumanTyyppi.S_LAUKUT);

		turvatarkastuspisteet = new ArrayList<>();
		turvatarkastuspisteet.add(turva1);
		turvatarkastuspisteet.add(turva2);
		turvatarkastuspisteet.add(turva3);
		turvatarkastuspisteet.add(turva4);
		turvatarkastuspisteet.add(turva5);

		kello = Kello.getInstance();

		// muutetaan default palvelupisteet käytössä oleviksi (tarvitaan suureiden
		// näyttämiseen tulosnäkymässä)
		laukut1.muutaKaytossa();
		checkin1.muutaKaytossa();
		erityisturva1.muutaKaytossa();
	}

	@Override
	protected void alustukset() {
		s_checkin.generoiSeuraava();
		s_laukut.generoiSeuraava();// Ensimmäinen saapuminen järjestelmään
	}

	@Override
	protected void suoritaTapahtuma(Tapahtuma t) { // B-vaiheen tapahtumat

		Asiakas a;
		switch (t.getTyyppi()) {

		case S_CHECKIN:
			a = new Asiakas();
			checkin1.lisaaJonoon(a);
			a.setPpJonoonSaapumisaika(kello.getAika());
			s_checkin.generoiSeuraava();
			((Kontrolleri) kontrolleri).visualisoiLaukutonAsiakas();
			break;
		case S_LAUKUT:
			a = new Asiakas();
			laukut1.lisaaJonoon(a);
			a.setPpJonoonSaapumisaika(kello.getAika());
			s_laukut.generoiSeuraava();
			((Kontrolleri) kontrolleri).visualisoiLaukkuAsiakas();
			break;
		case P_CHECKIN:
			a = checkin1.otaJonosta();
			((Kontrolleri) kontrolleri).poistaLaukutonAsiakas();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaCheckin = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			checkin1.setPpKokonaisoleskeluaika(oleskeluaikaCheckin);
			Palvelupiste lisaaTahan = getLyhinJono();
			visualisoiTurvajono(lisaaTahan);
			lisaaTahan.lisaaJonoon(a);
			a.setPpJonoonSaapumisaika(kello.getAika());
			break;
		case P_LAUKUT:
			a = laukut1.otaJonosta();
			((Kontrolleri) kontrolleri).poistaLaukkuAsiakas();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaLaukut = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			laukut1.setPpKokonaisoleskeluaika(oleskeluaikaLaukut);
			Palvelupiste lisaaTahan1 = getLyhinJono();
			visualisoiTurvajono(lisaaTahan1);
			lisaaTahan1.lisaaJonoon(a);
			a.setPpJonoonSaapumisaika(kello.getAika());
			break;
		case P_TURVA1:
			a = turva1.otaJonosta();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaTurva1 = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			turva1.setPpKokonaisoleskeluaika(oleskeluaikaTurva1);
			Random rand = new Random();
			int mihin = rand.nextInt(20);
			if (mihin == 1) {
				erityisturva1.lisaaJonoon(a);
				((Kontrolleri) kontrolleri).poistaTurva1Asiakas();
				((Kontrolleri) kontrolleri).visualisoiETurvaAsiakas();
				a.setPpJonoonSaapumisaika(kello.getAika());
				break;
			} else {
				((Kontrolleri) kontrolleri).poistaTurva1Asiakas();
				a.setPoistumisaika(Kello.getInstance().getAika());

				a.raportti();
				poistuneetT1++;
			}
			break;
		case P_TURVA2:
			a = turva2.otaJonosta();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaTurva2 = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			turva2.setPpKokonaisoleskeluaika(oleskeluaikaTurva2);
			Random rand1 = new Random();
			int mihin1 = rand1.nextInt(20);
			if (mihin1 == 1) {
				erityisturva1.lisaaJonoon(a);
				a.setPpJonoonSaapumisaika(kello.getAika());
				((Kontrolleri) kontrolleri).poistaTurva2Asiakas();
				((Kontrolleri) kontrolleri).visualisoiETurvaAsiakas();
			} else {
				a.setPoistumisaika(Kello.getInstance().getAika());
				((Kontrolleri) kontrolleri).poistaTurva2Asiakas();
				a.raportti();
				poistuneetT2++;
			}
			break;
		case P_TURVA3:
			a = turva3.otaJonosta();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaTurva3 = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			turva3.setPpKokonaisoleskeluaika(oleskeluaikaTurva3);
			Random rand2 = new Random();
			int mihin2 = rand2.nextInt(20);
			if (mihin2 == 1) {
				erityisturva1.lisaaJonoon(a);
				a.setPpJonoonSaapumisaika(kello.getAika());
				((Kontrolleri) kontrolleri).poistaTurva3Asiakas();
				((Kontrolleri) kontrolleri).visualisoiETurvaAsiakas();
			} else {
				a.setPoistumisaika(Kello.getInstance().getAika());
				((Kontrolleri) kontrolleri).poistaTurva3Asiakas();
				a.raportti();
				poistuneetT3++;
			}
			break;
		case P_TURVA4:
			a = turva4.otaJonosta();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaTurva4 = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			turva4.setPpKokonaisoleskeluaika(oleskeluaikaTurva4);
			Random rand3 = new Random();
			int mihin3 = rand3.nextInt(20);
			if (mihin3 == 1) {
				erityisturva1.lisaaJonoon(a);
				a.setPpJonoonSaapumisaika(kello.getAika());
				((Kontrolleri) kontrolleri).poistaTurva4Asiakas();
				((Kontrolleri) kontrolleri).visualisoiETurvaAsiakas();
			} else {
				a.setPoistumisaika(Kello.getInstance().getAika());
				((Kontrolleri) kontrolleri).poistaTurva4Asiakas();
				a.raportti();
				poistuneetT4++;
			}
			break;
		case P_TURVA5:
			a = turva5.otaJonosta();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaTurva5 = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			turva5.setPpKokonaisoleskeluaika(oleskeluaikaTurva5);
			Random rand4 = new Random();
			int mihin4 = rand4.nextInt(20);
			if (mihin4 == 1) {
				erityisturva1.lisaaJonoon(a);
				a.setPpJonoonSaapumisaika(kello.getAika());
				((Kontrolleri) kontrolleri).poistaTurva5Asiakas();
				((Kontrolleri) kontrolleri).visualisoiETurvaAsiakas();
			} else {
				a.setPoistumisaika(Kello.getInstance().getAika());
				((Kontrolleri) kontrolleri).poistaTurva5Asiakas();
				a.raportti();
				poistuneetT5++;
			}
			break;
		case P_ERITYISTURVA:
			a = erityisturva1.otaJonosta();
			a.setPpPoistumisaika(kello.getAika());
			double oleskeluaikaErityisturva = a.getPpPoistumisaika() - a.getPpJonoonSaapumisaika();
			erityisturva1.setPpKokonaisoleskeluaika(oleskeluaikaErityisturva);
			((Kontrolleri) kontrolleri).poistaETurvaAsiakas();

			a.setPoistumisaika(Kello.getInstance().getAika());
			a.raportti();
			poistuneetErityis++;

		}
	}

	/**
	 * metodi joka käy turvatarkastuspisteet läpi ja valitsee niitä käyttöön
	 * parametrina saadun arvon verran.
	 * 
	 * @param monta turvapistettä otetaan käyttöön
	 */
	public void otaPalvelupisteetKayttoon(int monta) {
		for (int i = 0; i <= monta; i++) {
			turvatarkastuspisteet.get(i).muutaKaytossa();
		}
	}

	@Override
	protected void tulokset() {

		summa = poistuneetT1 + poistuneetT2 + poistuneetT3 + poistuneetT4 + poistuneetT5 + poistuneetErityis;

		laskeJohdetutSuureet();

		pyydaPalvelupisteenTulokset();

		tulostaTulokset();

		tuloksetOlioon();

		tuloksetDao.lisaaTulos(tulokset);
		((Kontrolleri) kontrolleri).resetButtonKayttoon();

		System.out.println("Simulointi päättyi kello " + Kello.getInstance().getAika());
		System.out.println("Tulokset ... puuttuvat vielä");

		// palvelupistekohtaisesti: palveltujen + saapuneiden LKM
		// koko järjestelmän tasolla edelliset
	}

	/**
	 * Metodi joka tarkistaa missä palvelupisteessä on lyhin jono
	 * 
	 * @return lyhimmän Palvelupisteen
	 */
	public Palvelupiste getLyhinJono() {

		Palvelupiste lyhin = null;
		int l = 10000000;

		for (Palvelupiste p : turvatarkastuspisteet) {
			if (p.getKaytossa() && (p.getJononPituus() < l)) {
				l = p.getJononPituus();
				lyhin = p;
			}
		}

		ArrayList<Palvelupiste> lyhimmat = new ArrayList<Palvelupiste>();

		for (Palvelupiste p : turvatarkastuspisteet) {
			if (p.getKaytossa() && (p.getJononPituus() == lyhin.getJononPituus())) {
				lyhimmat.add(p);
			}
		}

		int arvotaanNaista = lyhimmat.size();
		Random randArvonta = new Random();

		if (arvotaanNaista == 1) {
			return lyhimmat.get(0);
		} else if (arvotaanNaista == 2) {
			int luku2 = (randArvonta.nextInt(2));
			return lyhimmat.get(luku2);
		} else if (arvotaanNaista == 3) {
			int luku3 = (randArvonta.nextInt(3));
			return lyhimmat.get(luku3);
		} else if (arvotaanNaista == 4) {
			int luku4 = (randArvonta.nextInt(4));
			return lyhimmat.get(luku4);
		} else {
			int luku5 = (randArvonta.nextInt(5));
			return lyhimmat.get(luku5);
		}
	}

	/**
	 * Metodi joka asettaa turvatarkastuspisteille uudet jakaumat
	 * 
	 * @param kesk1 uusi jakauma turva1:selle
	 * @param kesk2 uusi jakauma turva2:selle
	 * @param kesk3 uusi jakauma turva3:selle
	 * @param kesk4 uusi jakauma turva4:selle
	 * @param kesk5 uusi jakauma turva5:selle
	 */
	public void setKeskiarvot(int kesk1, int kesk2, int kesk3, int kesk4, int kesk5) {
		turva1.setGeneraattori(new Negexp(kesk1, 15));
		turva2.setGeneraattori(new Negexp(kesk2, 15));
		turva3.setGeneraattori(new Negexp(kesk3, 15));
		turva4.setGeneraattori(new Negexp(kesk4, 15));
		turva5.setGeneraattori(new Negexp(kesk5, 15));
		tulokset.setParametriTurva1(kesk1);
		if (turva2.getKaytossa()) {
			tulokset.setParametriTurva2(kesk2);
		} else {
			tulokset.setParametriTurva2(0);
		}
		if (turva3.getKaytossa()) {
			tulokset.setParametriTurva3(kesk3);
		} else {
			tulokset.setParametriTurva3(0);
		}
		if (turva4.getKaytossa()) {
			tulokset.setParametriTurva4(kesk4);
		} else {
			tulokset.setParametriTurva4(0);
		}
		if (turva5.getKaytossa()) {
			tulokset.setParametriTurva5(kesk5);
		} else {
			tulokset.setParametriTurva5(0);
		}
	}

	/**
	 * Metodi joka asettaa uudet arvot saapumis prosesseille
	 * 
	 * @param laukut  uusi jakauma laukku saapumisille
	 * @param checkin uusi jakauma checkin saapumisille
	 */
	public void setRuuhkaArvot(int laukut, int checkin) {
		s_laukut.setSaapumisprosessinGeneraattori(new Negexp(laukut, 15));
		s_checkin.setSaapumisprosessinGeneraattori(new Negexp(checkin, 15));
		tulokset.setParametriLaukut(laukut);
		tulokset.setParametriCheckin(checkin);
	}

	/**
	 * Metodi joka hoitaa jonojen visualisoinnin
	 * 
	 * @param p parametrina Palvelupiste
	 */
	private void visualisoiTurvajono(Palvelupiste p) {
		if (p.getTyyppi() == TapahtumanTyyppi.P_TURVA1) {
			((Kontrolleri) kontrolleri).visualisoiTurva1Asiakas();
		} else if (p.getTyyppi() == TapahtumanTyyppi.P_TURVA2) {
			((Kontrolleri) kontrolleri).visualisoiTurva2Asiakas();
		} else if (p.getTyyppi() == TapahtumanTyyppi.P_TURVA3) {
			((Kontrolleri) kontrolleri).visualisoiTurva3Asiakas();
		} else if (p.getTyyppi() == TapahtumanTyyppi.P_TURVA4) {
			((Kontrolleri) kontrolleri).visualisoiTurva4Asiakas();
		} else if (p.getTyyppi() == TapahtumanTyyppi.P_TURVA5) {
			((Kontrolleri) kontrolleri).visualisoiTurva5Asiakas();
		}
	}

	/**
	 * Metodi palauttaa kaikki palvelupisteet
	 * 
	 * @return Lista palvelupisteistä
	 */
	public Palvelupiste[] getPalvelupisteet() {
		return palvelupisteet;
	}

	public ArrayList<Palvelupiste> getTurvatarkastuspisteet() {
		return turvatarkastuspisteet;
	}

	/**
	 * Laskee kaikkien palvelupisteiteiden suureet
	 */
	public void laskeJohdetutSuureet() {
		checkin1.laskeJohdetutSuureet();
		laukut1.laskeJohdetutSuureet();
		turva1.laskeJohdetutSuureet();
		turva2.laskeJohdetutSuureet();
		turva3.laskeJohdetutSuureet();
		turva4.laskeJohdetutSuureet();
		turva5.laskeJohdetutSuureet();
		erityisturva1.laskeJohdetutSuureet();
	}

	/**
	 * Hakee palvelupisteiden raportit
	 */
	public void pyydaPalvelupisteenTulokset() {
		checkin1Tulokset = checkin1.raportti();
		laukut1Tulokset = laukut1.raportti();
		turva1Tulokset = turva1.raportti();
		turva2Tulokset = turva2.raportti();
		turva3Tulokset = turva3.raportti();
		turva4Tulokset = turva4.raportti();
		turva5Tulokset = turva5.raportti();
		erityisturva1Tulokset = erityisturva1.raportti();
	}

	/**
	 * Tulostaa palvelupisteiden tulokset
	 */
	public void tulostaTulokset() {
		final DecimalFormat df = new DecimalFormat("0.00");
		System.out.println("\nCheckin1:\nkokonaisoleskeluaika: " + df.format(checkin1Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(checkin1Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(checkin1Tulokset[2]) + "\nsuoritusteho: " + df.format(checkin1Tulokset[3]));
		System.out.println("\nLaukut1:\nkokonaisoleskeluaika: " + df.format(laukut1Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(laukut1Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(laukut1Tulokset[2]) + "\nsuoritusteho: " + df.format(laukut1Tulokset[3]));
		System.out.println("\nTurva1:\nkokonaisoleskeluaika: " + df.format(turva1Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(turva1Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(turva1Tulokset[2]) + "\nsuoritusteho: " + df.format(turva1Tulokset[3]));
		System.out.println("\nTurva2:\nkokonaisoleskeluaika: " + df.format(turva2Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(turva2Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(turva2Tulokset[2]) + "\nsuoritusteho: " + df.format(turva2Tulokset[3]));
		System.out.println("\nTurva3:\nkokonaisoleskeluaika: " + df.format(turva3Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(turva3Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(turva3Tulokset[2]) + "\nsuoritusteho: " + df.format(turva3Tulokset[3]));
		System.out.println("\nTurva4:\nkokonaisoleskeluaika: " + df.format(turva4Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(turva4Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(turva4Tulokset[2]) + "\nsuoritusteho: " + df.format(turva4Tulokset[3]));
		System.out.println("\nTurva5:\nkokonaisoleskeluaika: " + df.format(turva5Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(turva5Tulokset[1]) + "\nkeskimääräinen jononpituus: "
				+ df.format(turva5Tulokset[2]) + "\nsuoritusteho: " + df.format(turva5Tulokset[3]));
		System.out.println("\nErityisturva:\nkokonaisoleskeluaika: " + df.format(erityisturva1Tulokset[0])
				+ "\nkeskimääräinen läpimenoaika: " + df.format(erityisturva1Tulokset[1])
				+ "\nkeskimääräinen jononpituus: " + df.format(erityisturva1Tulokset[2]) + "\nsuoritusteho: "
				+ df.format(erityisturva1Tulokset[3]) + "\n");
	}

	/**
	 * lisää simulaattorin tulokset tulos olioon.
	 */
	public void tuloksetOlioon() {
		tulokset.setKokonaisaika(Kello.getInstance().getAika());
		tulokset.setPalvellutAsiakkaat(summa);

		tulokset.setMatkatavarapisteSuoritusteho(laukut1Tulokset[3]);
		tulokset.setLahtosselvitysSuoritusteho(checkin1Tulokset[3]);
		tulokset.setTurvatarkastus1Suoritusteho(turva1Tulokset[3]);
		tulokset.setTurvatarkastus2Suoritusteho(turva2Tulokset[3]);
		tulokset.setTurvatarkastus3Suoritusteho(turva3Tulokset[3]);
		tulokset.setTurvatarkastus4Suoritusteho(turva4Tulokset[3]);
		tulokset.setTurvatarkastus5Suoritusteho(turva5Tulokset[3]);
		tulokset.setErityisturvaSuoritusteho(erityisturva1Tulokset[3]);

		tulokset.setMatkatavarapisteLapimeno(laukut1Tulokset[1]);
		tulokset.setLahtoselvitysLapimeno(checkin1Tulokset[1]);
		tulokset.setTurvatarkastus1Lapimeno(turva1Tulokset[1]);
		tulokset.setTurvatarkastus2Lapimeno(turva2Tulokset[1]);
		tulokset.setTurvatarkastus3Lapimeno(turva3Tulokset[1]);
		tulokset.setTurvatarkastus4Lapimeno(turva4Tulokset[1]);
		tulokset.setTurvatarkastus5Lapimeno(turva5Tulokset[1]);
		tulokset.setEritysturvaLapimeno(erityisturva1Tulokset[1]);

		tulokset.setMatkatavarapisteKokonaisoleskelu(laukut1Tulokset[0]);
		tulokset.setLahtoselvitysKokonaisoleskelu(checkin1Tulokset[0]);
		tulokset.setTurvatarkastus1Kokonaisoleskelu(turva1Tulokset[0]);
		tulokset.setTurvatarkastus2Kokonaisoleskelu(turva2Tulokset[0]);
		tulokset.setTurvatarkastus3Kokonaisoleskelu(turva3Tulokset[0]);
		tulokset.setTurvatarkastus4Kokonaisoleskelu(turva4Tulokset[0]);
		tulokset.setTurvatarkastus5Kokonaisoleskelu(turva5Tulokset[0]);
		tulokset.setErityisturvaKokonaisoleskelu(erityisturva1Tulokset[0]);

		tulokset.setMatkatavarapisteKaJononPituus(laukut1Tulokset[2]);
		tulokset.setLahtoselvitysKaJononPituus(checkin1Tulokset[2]);
		tulokset.setTurvatarkastus1KaJononPituus(turva1Tulokset[2]);
		tulokset.setTurvatarkastus2KaJononPituus(turva2Tulokset[2]);
		tulokset.setTurvatarkastus3KaJononPituus(turva3Tulokset[2]);
		tulokset.setTurvatarkastus4KaJononPituus(turva4Tulokset[2]);
		tulokset.setTurvatarkastus5KaJononPituus(turva5Tulokset[2]);
		tulokset.setErityisturvaKaJononPituus(erityisturva1Tulokset[2]);

	}

	/**
	 * Palauttaa tulokset
	 * 
	 * @return tulokset
	 */
	public Tulokset getTulokset() {
		return tulokset;
	}

	/**
	 * Asettaa arvot junit testejä varten
	 */
	public void jUnitTestiTulokset() {
		checkin1Tulokset[0] = 1.0;
		laukut1Tulokset[1] = 2.0;
		turva1Tulokset[2] = 3.0;
		turva2Tulokset[3] = 4.0;
	}

	/**
	 * Asettaa arvot junit testejä varten
	 */
	public void jUnitTestiTulokset2() {
		turva3Tulokset[1] = -1.0;
		checkin1Tulokset[1] = -1.0;
		laukut1Tulokset[1] = -1.0;
		turva1Tulokset[1] = -1.0;
		turva2Tulokset[1] = -1.0;
		turva3Tulokset[1] = -1.0;
		turva4Tulokset[1] = -1.0;
		turva5Tulokset[1] = -1.0;
		erityisturva1Tulokset[1] = -1.0;
	}

}
