package simulaattorinrunko5FX.src.controller;

public interface IKontrolleri {

	// Rajapinta, joka tarjotaan käyttöliittymälle:

	public void kaynnistaSimulointi();

	public void nopeuta();

	public void hidasta();
	
	public void skippaa();

	// Rajapinta, joka tarjotaan moottorille:

	public void naytaLoppuaika(int ajoId);

	public void visualisoiLaukutonAsiakas();

	public void naytaPalvellutAsiakkkat(int ajoId);

	public void palvelupisteidenMaara();

	void palvelupisteidenAhkeruus();

}
