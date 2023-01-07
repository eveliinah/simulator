package jUnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import entity.Tulokset;
import simulaattorinrunko5FX.src.simu.model.OmaMoottori;

class TuloksetTest {

	@Test
	void tuleekoIndeksienArvotOikein() {
		// huom! testi ei testaa jokaista muuttujaa joten kirjoitusvirheitä ei kannata
		// tehdä
		var moottori = new OmaMoottori(null);
		moottori.pyydaPalvelupisteenTulokset();
		moottori.jUnitTestiTulokset();
		moottori.tuloksetOlioon();
		Tulokset tulokset = moottori.getTulokset();
		assertEquals(1.0, tulokset.getLahtoselvitysKokonaisoleskelu(),
				"Väärä indeksi OmaMoottorin tuloksetOlioon metodin kokonaisoleskelussa");
		assertEquals(2.0, tulokset.getMatkatavarapisteLapimeno(),
				"Väärä indeksi OmaMoottorin tuloksetOlioon metodin läpimenossa");
		assertEquals(3.0, tulokset.getTurvatarkastus1KaJononPituus(),
				"Väärä indeksi OmaMoottorin tuloksetOlioon metodin jonon pituudessa");
		assertEquals(4.0, tulokset.getTurvatarkastus2Suoritusteho(),
				"Väärä indeksi OmaMoottorin tuloksetOlioon metodin suoritustehossa");
	}

	@Test
	void toimiikoMuokatutSetteritOikein() {
		// testaa kaikki setterit
		var moottori = new OmaMoottori(null);
		moottori.pyydaPalvelupisteenTulokset();
		moottori.jUnitTestiTulokset2();
		moottori.tuloksetOlioon();
		Tulokset tulokset = moottori.getTulokset();
		assertEquals(0.0, tulokset.getLahtoselvitysLapimeno(), "Lähtöselvityksen läpimenosetteri ei toimi");
		assertEquals(0.0, tulokset.getMatkatavarapisteLapimeno(), "Matkatavarapisteen läpimenosetteri ei toimi");
		assertEquals(0.0, tulokset.getTurvatarkastus1Lapimeno(), "Turvatarkastus1 läpimenosetteri ei toimi.");
		assertEquals(0.0, tulokset.getTurvatarkastus2Lapimeno(), "Turvatarkastus2 läpimenosetteri ei toimi.");
		assertEquals(0.0, tulokset.getTurvatarkastus3Lapimeno(), "Turvatarkastus3 läpimenosetteri ei toimi.");
		assertEquals(0.0, tulokset.getTurvatarkastus4Lapimeno(), "Turvatarkastus4 läpimenosetteri ei toimi.");
		assertEquals(0.0, tulokset.getTurvatarkastus5Lapimeno(), "Turvatarkastus5 läpimenosetteri ei toimi.");
		assertEquals(0.0, tulokset.getEritysturvaLapimeno(), "Erityisturvan läpimenosetteri ei toimi.");
	}
}
