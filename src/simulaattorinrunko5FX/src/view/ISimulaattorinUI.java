package simulaattorinrunko5FX.src.view;

public interface ISimulaattorinUI {

	// Kontrolleri tarvitsee syötteitä, jotka se välittää Moottorille
	public double getAika();

	public long getViive();

	// Kontrolleri antaa käyttöliittymälle tuloksia, joita Moottori tuottaa
	public void setLoppuaika(double aika);

	public void setPalvellutAsiakkaat(int asiakkaat);

	// Kontrolleri tarvitsee
	public IVisualisointi getVisualisointi();

	int getMontakoPalvelupistettä();

	int[] getTurvapisteAhkeruus();

}
