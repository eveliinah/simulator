package simulaattorinrunko5FX.src.simu.framework;

import simulaattorinrunko5FX.src.eduni.distributions.ContinuousGenerator;
import simulaattorinrunko5FX.src.simu.model.TapahtumanTyyppi;

public class Saapumisprosessi {

	private ContinuousGenerator generaattori;
	private Tapahtumalista tapahtumalista;
	private TapahtumanTyyppi tyyppi;

	public Saapumisprosessi(ContinuousGenerator g, Tapahtumalista tl, TapahtumanTyyppi tyyppi) {
		this.generaattori = g;
		this.tapahtumalista = tl;
		this.tyyppi = tyyppi;
	}

	public void generoiSeuraava() {
		Tapahtuma t = new Tapahtuma(tyyppi, Kello.getInstance().getAika() + generaattori.sample());
		tapahtumalista.lisaa(t);
	}

	public void setSaapumisprosessinGeneraattori(ContinuousGenerator generator) {
		this.generaattori = generator;
	}

}
