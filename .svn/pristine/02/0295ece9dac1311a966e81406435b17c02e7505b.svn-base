package jUnitTests;

import static org.junit.Assert.assertFalse;

import org.junit.jupiter.api.Test;

import simulaattorinrunko5FX.src.eduni.distributions.Negexp;
import simulaattorinrunko5FX.src.simu.framework.Tapahtumalista;
import simulaattorinrunko5FX.src.simu.model.Palvelupiste;
import simulaattorinrunko5FX.src.simu.model.TapahtumanTyyppi;

class PalvelupisteTesti {

	private Tapahtumalista tapahtumalista;

	@Test
	void palvelupisteOnKaytossa() {
		var palvelupiste = new Palvelupiste(new Negexp(5, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA1);
		assertFalse(palvelupiste.getKaytossa());
	}

	/*
	 * @Test void jonoonlisays() { var a = new Asiakas(); var palvelupiste = new
	 * Palvelupiste(new Negexp(5, 15), tapahtumalista, TapahtumanTyyppi.P_TURVA1);
	 * palvelupiste.lisaaJonoon(a);
	 * 
	 * }
	 */

}
