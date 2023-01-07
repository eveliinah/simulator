package jUnitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import simulaattorinrunko5FX.src.controller.Kontrolleri;
import simulaattorinrunko5FX.src.view.ISimulaattorinUI;

class KontorolleriTest {
	private ISimulaattorinUI ui;

	@Test
	void nollaPalauttaaViisi() {
		var kontrolleri = new Kontrolleri(ui);
		assertEquals(5, kontrolleri.luvunMuunnin(0));
	}

	@Test
	void yksiPalauttaaKymmenen() {
		var kontrolleri = new Kontrolleri(ui);
		assertEquals(10, kontrolleri.luvunMuunnin(1));
	}

	@Test
	void kaksiPalauttaaViisitoista() {
		var kontrolleri = new Kontrolleri(ui);
		assertEquals(15, kontrolleri.luvunMuunnin(2));
	}

	@Test
	void kolmePalauttaaKaksikymmenta() {
		var kontrolleri = new Kontrolleri(ui);
		assertEquals(20, kontrolleri.luvunMuunnin(3));
	}

	@Test
	void neljaPalauttaaKaksiviisi() {
		var kontrolleri = new Kontrolleri(ui);
		assertEquals(25, kontrolleri.luvunMuunnin(4));
	}

}
