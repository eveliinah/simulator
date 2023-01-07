package jUnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import simulaattorinrunko5FX.src.simu.model.OmaMoottori;
import simulaattorinrunko5FX.src.simu.model.Palvelupiste;

class OmaMoottoriTest {

	@Test
	void testOtaPalvelupisteetKayttoon1kpl() {
		var moottori = new OmaMoottori(null);
		moottori.otaPalvelupisteetKayttoon(0); // laskeminen alkaa 0:sta
		ArrayList<Palvelupiste> pisteet = moottori.getTurvatarkastuspisteet();
		assertEquals(true, pisteet.get(0).getKaytossa(), "Ensimmäinen palvelupiste ei ole käytössä.");
		assertEquals(false, pisteet.get(1).getKaytossa(), "Toinen palvelupiste on käytössä.");
		assertEquals(false, pisteet.get(2).getKaytossa(), "Kolmas palvelupiste on käytössä.");
		assertEquals(false, pisteet.get(3).getKaytossa(), "Neljäs palvelupiste on käytössä.");
		assertEquals(false, pisteet.get(4).getKaytossa(), "Viides palvelupiste on käytössä.");
	}

	@Test
	void testOtaPalvelupisteetKayttoon3kpl() {
		var moottori = new OmaMoottori(null);
		moottori.otaPalvelupisteetKayttoon(2); // laskeminen alkaa 0:sta
		ArrayList<Palvelupiste> pisteet = moottori.getTurvatarkastuspisteet();
		assertEquals(true, pisteet.get(0).getKaytossa(), "Ensimmäinen palvelupiste ei ole käytössä.");
		assertEquals(true, pisteet.get(1).getKaytossa(), "Toinen palvelupiste ei ole käytössä.");
		assertEquals(true, pisteet.get(2).getKaytossa(), "Kolmas palvelupiste ei ole käytössä.");
		assertEquals(false, pisteet.get(3).getKaytossa(), "Neljäs palvelupiste on käytössä.");
		assertEquals(false, pisteet.get(4).getKaytossa(), "Viides palvelupiste on käytössä.");
	}

	@Test
	void testOtaPalvelupisteetKayttoon5kpl() {
		var moottori = new OmaMoottori(null);
		moottori.otaPalvelupisteetKayttoon(4); // laskeminen alkaa 0:sta
		ArrayList<Palvelupiste> pisteet = moottori.getTurvatarkastuspisteet();
		assertEquals(true, pisteet.get(0).getKaytossa(), "Ensimmäinen palvelupiste ei ole käytössä.");
		assertEquals(true, pisteet.get(1).getKaytossa(), "Toinen palvelupiste ei ole käytössä.");
		assertEquals(true, pisteet.get(2).getKaytossa(), "Kolmas palvelupiste ei ole käytössä.");
		assertEquals(true, pisteet.get(3).getKaytossa(), "Neljäs palvelupiste ei ole käytössä.");
		assertEquals(true, pisteet.get(4).getKaytossa(), "Viides palvelupiste ei ole käytössä.");
	}

}
