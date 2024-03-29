package simulaattorinrunko5FX.src.view;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import simulaattorinrunko5FX.src.controller.IKontrolleri;
import simulaattorinrunko5FX.src.controller.Kontrolleri;
import simulaattorinrunko5FX.src.simu.framework.Kello;
import simulaattorinrunko5FX.src.simu.framework.Trace;
import simulaattorinrunko5FX.src.simu.framework.Trace.Level;

/**
 * Luokka <code>SimulaattorinGUI</code> simulaattorin käyttöliittymä.
 *
 * @author Eveliina, Juho, Katja, Sebastian
 * @version 1.0 (13.10.2022)
 */
public class SimulaattorinGUI extends Application implements ISimulaattorinUI {

	private IKontrolleri kontrolleri;

	// Käyttöliittymäkomponentit:
	private TextField aika;
	private TextField viive;
	private Label kokAika = new Label();
	private Label asiakkaat = new Label();
	private Label t1 = new Label();
	private Label t2 = new Label();
	private Label t3 = new Label();
	private Label t4 = new Label();
	private Label t5 = new Label();
	private Label paraT1 = new Label();
	private Label paraT2 = new Label();
	private Label paraT3 = new Label();
	private Label paraT4 = new Label();
	private Label paraT5 = new Label();
	private Label paraET = new Label();
	private Label paraCheck = new Label();
	private Label paraLaukut = new Label();

	private Label erityis = new Label();
	private Label laukut = new Label();
	private Label checkin = new Label();
	private Label viiveLabel;
	private Label aikaLabel;
	private Label valintaLabel;
	private Label ahkeruusLabel;
	private Button resetButton;
	private Button kaynnistaButton;
	private Button hidastaButton;
	private Button nopeutaButton;
	private Button tuloksetButton;
	private Button skippaaButton;
	private ChoiceBox<String> turvapisteValinta;
	private ChoiceBox<String> ahkeruusValinta1;
	private ChoiceBox<String> ahkeruusValinta2;
	private ChoiceBox<String> ahkeruusValinta3;
	private ChoiceBox<String> ahkeruusValinta4;
	private ChoiceBox<String> ahkeruusValinta5;
	private ChoiceBox<String> ruuhkaValinta1;
	private ChoiceBox<String> ruuhkaValinta2;
	private String[] ahkeruus = { "Ahkera", "Reipas", "Perus", "Laiska", "Vetelä" };
	private ObservableList<String> ahkeruusList = FXCollections.observableArrayList(ahkeruus);
	private String[] ruuhka = { "Todella paljon", "Paljon", "Normaalimäärä", "Vähän", "Todella vähän" };
	private ObservableList<String> ruuhkaList = FXCollections.observableArrayList(ruuhka);
	private BarChart<String, Number> lapimenoajatBC;
	private BarChart<String, Number> kokonaisajatBC;
	private BarChart<String, Number> jonoBC;
	private XYChart.Series<String, Number> seriesJono;
	private XYChart.Series<String, Number> seriesLapiMenoAjat;
	private XYChart.Series<String, Number> seriesOleskelu;
	// diagrammien arvot
	private double lapiCheck;
	private double lapiLaukut;
	private double lapiT1;
	private double lapiT2;
	private double lapiT3;
	private double lapiT4;
	private double lapiT5;
	private double lapiErityis;
	private double kokOleskeluCheck;
	private double kokOleskeluLaukut;
	private double kokOleskeluT1;
	private double kokOleskeluT2;
	private double kokOleskeluT3;
	private double kokOleskeluT4;
	private double kokOleskeluT5;
	private double kokOleskeluErityis;
	private double jononPituusCheck;
	private double jononPituusLaukut;
	private double jononPituusT1;
	private double jononPituusT2;
	private double jononPituusT3;
	private double jononPituusT4;
	private double jononPituusT5;
	private double jononPituusErityis;

	final String checkInS = "Lähtösel.";
	final String laukutS = "Matkatavarat";
	final String turva1S = "T1";
	final String turva2S = "T2";
	final String turva3S = "T3";
	final String turva4S = "T4";
	final String turva5S = "T5";
	final String erityisS = "ErityisT";

	private int viimeisenAjonId;
	private Kello kello;
	private ArrayList<String> tietokantaLista;
	private IVisualisointi naytto;

	@Override
	public void init() {

		Trace.setTraceLevel(Level.INFO);
		kontrolleri = new Kontrolleri(this);
	}

	@Override
	public void start(Stage primaryStage) {
		startSimu(primaryStage);
	}

	/**
	 * Käynnistää simulaattorin uudestaan
	 * 
	 * @param stage uusi stage
	 */
	void restartSimu(Stage stage) {
		alustus();
		startSimu(stage);

	}

	/**
	 * Alustaa simulaattorin kontrollerin ja asettaa kellon nollaan
	 */
	void alustus() {

		int lukuAhkera = ((Kontrolleri) kontrolleri).getLukuAhkera();
		int lukuReipas = ((Kontrolleri) kontrolleri).getLukuReipas();
		int lukuPerus = ((Kontrolleri) kontrolleri).getLukuPerus();
		int lukuLaiska = ((Kontrolleri) kontrolleri).getLukuLaiska();
		int lukuVetelä = ((Kontrolleri) kontrolleri).getLukuVetelä();

		int lukuRuuhkaTodPaljon = ((Kontrolleri) kontrolleri).getLukuRuuhkaTodPaljon();
		int lukuRuuhkaPaljon = ((Kontrolleri) kontrolleri).getLukuRuuhkaPaljon();
		int lukuRuuhkaNormaali = ((Kontrolleri) kontrolleri).getLukuRuuhkaNormaali();
		int lukuRuuhkaVahan = ((Kontrolleri) kontrolleri).getLukuRuuhkaVahan();
		int lukuRuuhkaTodVahan = ((Kontrolleri) kontrolleri).getLukuRuuhkaTodVahan();

		Trace.setTraceLevel(Level.INFO);
		kontrolleri = new Kontrolleri(this);

		((Kontrolleri) kontrolleri).setLukuAhkera(lukuAhkera);
		((Kontrolleri) kontrolleri).setLukuReipas(lukuReipas);
		((Kontrolleri) kontrolleri).setLukuPerus(lukuPerus);
		((Kontrolleri) kontrolleri).setLukuLaiska(lukuLaiska);
		((Kontrolleri) kontrolleri).setLukuVetelä(lukuVetelä);

		((Kontrolleri) kontrolleri).setLukuRuuhkaTodPaljon(lukuRuuhkaTodPaljon);
		((Kontrolleri) kontrolleri).setLukuRuuhkaPaljon(lukuRuuhkaPaljon);
		((Kontrolleri) kontrolleri).setLukuRuuhkaNormaali(lukuRuuhkaNormaali);
		((Kontrolleri) kontrolleri).setLukuRuuhkaVahan(lukuRuuhkaVahan);
		((Kontrolleri) kontrolleri).setLukuRuuhkaTodVahan(lukuRuuhkaTodVahan);

		kello = Kello.getInstance();
		kello.setAika(0.0);
	}

	/**
	 * Käynnistää simulaattorin
	 * 
	 * @param stage käynnistää stagen
	 */
	void startSimu(Stage stage) {
		// Käyttöliittymän rakentaminen
		try {

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.exit();
					System.exit(0);
				}
			});

			stage.setTitle("Lentokenttä simulaattori");

			kaynnistaButton = new Button();
			kaynnistaButton.setText("Käynnistä simulointi");
			kaynnistaButton.setPrefSize(190, 43);
			kaynnistaButton.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
			kaynnistaButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (getAika() > 0 && getViive() > 0) {
						kontrolleri.palvelupisteidenMaara();
						kontrolleri.palvelupisteidenAhkeruus();
						((Kontrolleri) kontrolleri).ruuhkaArvot();
						kontrolleri.kaynnistaSimulointi();
						kaynnistaButton.setDisable(true);
						aika.setDisable(true);
						viive.setDisable(true);
						resetButton.setDisable(true);

					} else {
						Alert alert = new Alert(AlertType.ERROR, "Korjaa arvot, vain positiivisia lukuja!.",
								ButtonType.CLOSE);
						alert.setTitle("Lentokenttäsimu");
						alert.setHeaderText("Virheellinen arvo.");
						alert.showAndWait();
					}
				}
			});

			resetButton = new Button();
			resetButton.setText("Nollaa simulointi");
			resetButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					restartSimu(stage);
				}
			});

			tuloksetButton = new Button("Tulokset");
			tuloksetButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					((Kontrolleri) kontrolleri).asetaViimeisinAjokerta();
					kontrolleri.naytaLoppuaika(viimeisenAjonId);
					kontrolleri.naytaPalvellutAsiakkkat(viimeisenAjonId);
					((Kontrolleri) kontrolleri).asetaLapimenoajat(viimeisenAjonId);
					((Kontrolleri) kontrolleri).asetaParametriTulokset(viimeisenAjonId);
					((Kontrolleri) kontrolleri).asetaKokonaisoleskeluajat(viimeisenAjonId);
					((Kontrolleri) kontrolleri).asetaJononpituudet(viimeisenAjonId);
					((Kontrolleri) kontrolleri).asetaSuoritustehot(viimeisenAjonId);
					setTuloksetScene();
				}
			});

			turvapisteValinta = new ChoiceBox<String>();
			turvapisteValinta.getItems().addAll("1", "2", "3", "4", "5");
			turvapisteValinta.setValue("1");

			turvapisteValinta.setOnAction(event -> {
				((Kontrolleri) kontrolleri).visuPaivitaNaytto();

				int valitunIndeksi = getMontakoPalvelupistettä();
				ArrayList<ChoiceBox<String>> checkBoxlista = new ArrayList<ChoiceBox<String>>(
						List.of(ahkeruusValinta2, ahkeruusValinta3, ahkeruusValinta4, ahkeruusValinta5));

				for (int i = 0; i < valitunIndeksi; i++) {
					checkBoxlista.get(i).setDisable(false);
				}

				for (int i = valitunIndeksi; i < checkBoxlista.size(); i++) {
					checkBoxlista.get(i).setDisable(true);
				}

			});

			valintaLabel = new Label("Turvatarkastuspisteiden määrä:");
			valintaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

			ahkeruusValinta1 = new ChoiceBox<String>();
			ahkeruusValinta1.setItems(ahkeruusList);
			ahkeruusValinta1.setValue("Perus");

			ahkeruusValinta2 = new ChoiceBox<String>();
			ahkeruusValinta2.setItems(ahkeruusList);
			ahkeruusValinta2.setValue("Perus");
			ahkeruusValinta2.setDisable(true);

			ahkeruusValinta3 = new ChoiceBox<String>();
			ahkeruusValinta3.setItems(ahkeruusList);
			ahkeruusValinta3.setValue("Perus");
			ahkeruusValinta3.setDisable(true);

			ahkeruusValinta4 = new ChoiceBox<String>();
			ahkeruusValinta4.setItems(ahkeruusList);
			ahkeruusValinta4.setValue("Perus");
			ahkeruusValinta4.setDisable(true);

			ahkeruusValinta5 = new ChoiceBox<String>();
			ahkeruusValinta5.setItems(ahkeruusList);
			ahkeruusValinta5.setValue("Perus");
			ahkeruusValinta5.setDisable(true);

			ahkeruusLabel = new Label("Valitse työntekijöiden ahkeruus:");
			ahkeruusLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

			Label turva1 = new Label("Turva 1");
			turva1.setFont(Font.font("Tahoma", FontWeight.LIGHT, 12));

			Label turva2 = new Label("Turva 2");
			turva2.setFont(Font.font("Tahoma", FontWeight.LIGHT, 12));

			Label turva3 = new Label("Turva 3");
			turva3.setFont(Font.font("Tahoma", FontWeight.LIGHT, 12));

			Label turva4 = new Label("Turva 4");
			turva4.setFont(Font.font("Tahoma", FontWeight.LIGHT, 12));

			Label turva5 = new Label("Turva 5");
			turva5.setFont(Font.font("Tahoma", FontWeight.LIGHT, 12));

			HBox turvaTekstit = new HBox();
			turvaTekstit.setPadding(new Insets(0, 12, 0, 12));
			turvaTekstit.setSpacing(40);
			turvaTekstit.getChildren().addAll(turva1, turva2, turva3, turva4, turva5);

			hidastaButton = new Button();
			hidastaButton.setText("Hidasta");
			hidastaButton.setOnAction(e -> kontrolleri.hidasta());

			nopeutaButton = new Button();
			nopeutaButton.setText("Nopeuta");
			nopeutaButton.setOnAction(e -> kontrolleri.nopeuta());

			skippaaButton = new Button();
			skippaaButton.setText("Skippaa loppuun");
			skippaaButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					if (onkoAdmin()) {
						setAdminScene();
					} else {
						kontrolleri.skippaa();
					}
				}
			});

			aikaLabel = new Label("Simulointiaika:");
			aikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			aika = new TextField();
			aika.setPromptText("Syötä aika");
			// aika.setFocusTraversable(false);
			aika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			aika.setPrefWidth(150);

			viiveLabel = new Label("Viive:");
			viiveLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			viive = new TextField();
			viive.setPromptText("Syötä viive");
			viive.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			viive.setPrefWidth(150);

			ruuhkaValinta1 = new ChoiceBox<String>();
			ruuhkaValinta1.setItems(ruuhkaList);
			ruuhkaValinta1.setValue("Normaalimäärä");

			ruuhkaValinta2 = new ChoiceBox<String>();
			ruuhkaValinta2.setItems(ruuhkaList);
			ruuhkaValinta2.setValue("Normaalimäärä");

			Label ruuhkaLabel = new Label("Saapuvien ihmisten keskimääräinen määrä:");
			ruuhkaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

			Label ruuhka1Label = new Label("Ruumaan menevien tavaroiden kanssa:");
			ruuhka1Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

			Label ruuhka2Label = new Label("Ilman ruumatavaroita:");
			ruuhka2Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));

			HBox hBox = new HBox();
			hBox.setStyle("-fx-background-color: #BAC9C1;");
			hBox.setPadding(new Insets(30, 30, 30, 30)); // marginaalit ylä, oikea, ala, vasen
			hBox.setSpacing(30); // noodien välimatka 10 pikseliä
			hBox.setPrefSize(1200, 650); // ikkunan koko
			hBox.setAlignment(Pos.TOP_LEFT);

			HBox ahkeruusNappulat = new HBox();
			ahkeruusNappulat.setSpacing(10);
			ahkeruusNappulat.getChildren().addAll(ahkeruusValinta1, ahkeruusValinta2, ahkeruusValinta3,
					ahkeruusValinta4, ahkeruusValinta5);

			HBox vauhtiNappulat = new HBox();
			vauhtiNappulat.setSpacing(10);
			vauhtiNappulat.setAlignment(Pos.TOP_CENTER);
			vauhtiNappulat.setMinWidth(400);
			vauhtiNappulat.getChildren().addAll(nopeutaButton, hidastaButton, skippaaButton);

			HBox nappulat = new HBox();
			nappulat.setSpacing(10);
			nappulat.setAlignment(Pos.TOP_CENTER);
			nappulat.setMinWidth(400);
			nappulat.getChildren().addAll(tuloksetButton, resetButton);

			HBox kaynnistaHB = new HBox();
			kaynnistaHB.setMinWidth(400);
			kaynnistaHB.setAlignment(Pos.CENTER);
			kaynnistaHB.getChildren().add(kaynnistaButton);

			GridPane grid = new GridPane();
			grid.setAlignment(Pos.TOP_CENTER);
			grid.setVgap(10);
			grid.setHgap(5);
			GridPane.setMargin(valintaLabel, new Insets(35, 0, 0, 0));
			GridPane.setMargin(turvapisteValinta, new Insets(35, 0, 0, 0));
			GridPane.setMargin(ahkeruusLabel, new Insets(30, 0, 0, 0));
			GridPane.setMargin(turvaTekstit, new Insets(5, 0, 0, 0));
			GridPane.setMargin(ruuhkaLabel, new Insets(30, 0, 0, 0));
			GridPane.setMargin(ruuhkaValinta1, new Insets(5, -20, 0, 0));
			GridPane.setMargin(ruuhkaValinta2, new Insets(5, -20, 0, 0));
			GridPane.setMargin(kaynnistaHB, new Insets(45, 0, 0, 0));

			ColumnConstraints c1 = new ColumnConstraints();
			c1.setMinWidth(150);
			grid.getColumnConstraints().add(c1);

			grid.add(aikaLabel, 0, 0); // sarake, rivi
			grid.add(aika, 1, 0); // sarake, rivi
			grid.add(viiveLabel, 0, 1); // sarake, rivi
			grid.add(viive, 1, 1); // sarake, rivi
			grid.add(valintaLabel, 0, 2, 4, 1);
			grid.add(turvapisteValinta, 3, 2);
			grid.add(ahkeruusLabel, 0, 3, 4, 1);
			grid.add(turvaTekstit, 0, 4, 4, 1);
			grid.add(ahkeruusNappulat, 0, 5, 4, 1); // sarake, rivi, montako saraketta kattaa, montako riviä
			grid.add(ruuhkaLabel, 0, 6, 4, 1);
			grid.add(ruuhka1Label, 0, 7, 2, 1);
			grid.add(ruuhkaValinta1, 3, 7);
			grid.add(ruuhka2Label, 0, 8, 2, 1);
			grid.add(ruuhkaValinta2, 3, 8);
			grid.add(kaynnistaHB, 0, 9, 4, 1); // sarake, rivi
			grid.add(vauhtiNappulat, 0, 12, 4, 1);
			grid.add(nappulat, 0, 13, 4, 1);

			naytto = new Visualisointi(800, 600);

			// Täytetään boxi:
			hBox.getChildren().addAll(grid, (Canvas) naytto);

			Scene scene = new Scene(hBox);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Käyttöliittymän rajapintametodit (kutsutaan kontrollerista)

	@Override
	public double getAika() {
		try {
			return Double.parseDouble(aika.getText());
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR, "Anna vain numeroita.", ButtonType.CLOSE);
			alert.setTitle("Lentokenttäsimu");
			alert.setHeaderText("Virheellinen arvo ajassa.");
			alert.showAndWait();
			return 0;
		}
	}

	/**
	 * Tarkistetaan haluaako käyttäjä avata admin-ikkunan
	 */
	public boolean onkoAdmin() {
		if (aika.getText().equals("admin")) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public long getViive() {
		try {
			((Kontrolleri) kontrolleri).visualisoiViive();
			return Long.parseLong(viive.getText());
		} catch (NumberFormatException ex) {
			Alert alert = new Alert(AlertType.ERROR, "Anna vain numeroita.", ButtonType.CLOSE);
			alert.setTitle("Lentokenttäsimu");
			alert.setHeaderText("Virheellinen arvo viiveessä.");
			alert.showAndWait();
			return 0;
		}

	}

	@Override
	public void setLoppuaika(double aika) {
		DecimalFormat formatter = new DecimalFormat("#0.00");
		this.kokAika.setText(formatter.format(aika));
	}

	@Override
	public void setPalvellutAsiakkaat(int asiakkaat) {
		// DecimalFormat formatter = new DecimalFormat("#0");
		this.asiakkaat.setText(String.valueOf(asiakkaat));

	}

	/**
	 * Asettaa tulokset näkymään näkyville ajokerralla käytössä olleet jakaumien
	 * parametrit
	 * 
	 * @param check   palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param laukut  palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param t1      palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param t2      palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param t3      palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param t4      palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param t5      palvelupisteen jakaumassa käytetty keskiarvoparametri
	 * @param erityis palvelupisteen jakaumassa käytetty keskiarvoparametri
	 */
	public void setParametriTiedot(int check, int laukut, int t1, int t2, int t3, int t4, int t5) {

		this.paraCheck.setText(String.valueOf(check));
		this.paraLaukut.setText(String.valueOf(laukut));
		this.paraT1.setText(String.valueOf(t1));
		this.paraT2.setText(String.valueOf(t2));
		this.paraT3.setText(String.valueOf(t3));
		this.paraT4.setText(String.valueOf(t4));
		this.paraT5.setText(String.valueOf(t5));

	}

	/**
	 * Asettaa suoritustehot näkyviin
	 * 
	 * @param laukut  palvelupisteen suoritusteho
	 * @param t1      palvelupisteen suoritusteho
	 * @param t2      palvelupisteen suoritusteho
	 * @param t3      palvelupisteen suoritusteho
	 * @param t4      palvelupisteen suoritusteho
	 * @param t5      palvelupisteen suoritusteho
	 * @param check   palvelupisteen suoritusteho
	 * @param erityis palvelupisteen suoritusteho
	 */
	public void setSuoritustehot(double laukut, double t1, double t2, double t3, double t4, double t5, double check,
			double erityis) {
		final DecimalFormat df = new DecimalFormat("0.00");
		this.t1.setText(df.format(t1));
		this.t2.setText(df.format(t2));
		this.t3.setText(df.format(t3));
		this.t4.setText(df.format(t4));
		this.t5.setText(df.format(t5));
		this.erityis.setText(df.format(erityis));
		this.laukut.setText(df.format(laukut));
		this.checkin.setText(df.format(check));
	}

	/**
	 * Asettaa palvelupisteiden läpimenoajat näkyville
	 * 
	 * @param laukut  palvelupisteen läpimenoaika
	 * @param t1      palvelupisteen läpimenoaika
	 * @param t2      palvelupisteen läpimenoaika
	 * @param t3      palvelupisteen läpimenoaika
	 * @param t4      palvelupisteen läpimenoaika
	 * @param t5      palvelupisteen läpimenoaika
	 * @param check   palvelupisteen läpimenoaika
	 * @param erityis palvelupisteen läpimenoaika
	 */
	public void setLapimenoajat(double laukut, double t1, double t2, double t3, double t4, double t5, double check,
			double erityis) {
		lapiCheck = check;
		lapiLaukut = laukut;
		lapiT1 = t1;
		lapiT2 = t2;
		lapiT3 = t3;
		lapiT4 = t4;
		lapiT5 = t5;
		lapiErityis = erityis;

		lapimenoajatBC.getData().remove(seriesLapiMenoAjat);
		setLapimenoSeries();
	}

	/**
	 * Asettaa kokonaisoleskeluajat näkyville
	 * 
	 * @param laukut  palvelupisteen kokonaisoleskeluaika
	 * @param t1      palvelupisteen kokonaisoleskeluaika
	 * @param t2      palvelupisteen kokonaisoleskeluaika
	 * @param t3      palvelupisteen kokonaisoleskeluaika
	 * @param t4      palvelupisteen kokonaisoleskeluaika
	 * @param t5      palvelupisteen kokonaisoleskeluaika
	 * @param check   palvelupisteen kokonaisoleskeluaika
	 * @param erityis palvelupisteen kokonaisoleskeluaika
	 */
	public void setKokonaisoleskeluajat(double laukut, double t1, double t2, double t3, double t4, double t5,
			double check, double erityis) {
		kokOleskeluCheck = check;
		kokOleskeluLaukut = laukut;
		kokOleskeluT1 = t1;
		kokOleskeluT2 = t2;
		kokOleskeluT3 = t3;
		kokOleskeluT4 = t4;
		kokOleskeluT5 = t5;
		kokOleskeluErityis = erityis;

		kokonaisajatBC.getData().remove(seriesOleskelu);
		setOleskeluSeries();
	}

	/**
	 * Asettaa jononpituudet näkyville
	 * 
	 * @param laukut  palvelupisteen jonopituudet
	 * @param t1      palvelupisteen jonopituudet
	 * @param t2      palvelupisteen jonopituudet
	 * @param t3      palvelupisteen jonopituudet
	 * @param t4      palvelupisteen jonopituudet
	 * @param t5      palvelupisteen jonopituudet
	 * @param check   palvelupisteen jonopituudet
	 * @param erityis palvelupisteen jonopituudet
	 */
	public void setJononpituudet(double laukut, double t1, double t2, double t3, double t4, double t5, double check,
			double erityis) {
		jononPituusCheck = check;
		jononPituusLaukut = laukut;
		jononPituusT1 = t1;
		jononPituusT2 = t2;
		jononPituusT3 = t3;
		jononPituusT4 = t4;
		jononPituusT5 = t5;
		jononPituusErityis = erityis;

		jonoBC.getData().remove(seriesJono);
		setJonoSeries();
	}

	@Override
	public IVisualisointi getVisualisointi() {
		return naytto;
	}

	@Override
	public int getMontakoPalvelupistettä() {
		int luku = turvapisteValinta.getSelectionModel().getSelectedIndex();
		return luku;
	}

	@Override
	public int[] getTurvapisteAhkeruus() {
		int[] valinnat = new int[5];
		valinnat[0] = ahkeruusValinta1.getSelectionModel().getSelectedIndex();
		valinnat[1] = ahkeruusValinta2.getSelectionModel().getSelectedIndex();
		valinnat[2] = ahkeruusValinta3.getSelectionModel().getSelectedIndex();
		valinnat[3] = ahkeruusValinta4.getSelectionModel().getSelectedIndex();
		valinnat[4] = ahkeruusValinta5.getSelectionModel().getSelectedIndex();
		return valinnat;
	}

	/**
	 * Tarkistaa checkboxista valinnat ja palauttaa indeksit integer listana
	 * 
	 * @return valinnat listan joka sisältää indeksit valinnoista
	 */
	public int[] getRuuhkaArvot() {
		int[] valinnat = new int[2];
		valinnat[0] = ruuhkaValinta1.getSelectionModel().getSelectedIndex();
		valinnat[1] = ruuhkaValinta2.getSelectionModel().getSelectedIndex();
		return valinnat;
	}

	/**
	 * JavaFX-sovelluksen (käyttöliittymän) käynnistäminen
	 * 
	 * @param args mainia varten
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Luo tulokset scenen
	 */
	private void setTuloksetScene() {
		Stage newWindow = new Stage();
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #BAC9C1;");
		GridPane grid = new GridPane();
		root.setCenter(grid);
		root.setPadding(new Insets(30, 30, 20, 10));
		grid.setAlignment(Pos.CENTER);
		grid.setVgap(10);
		grid.setHgap(50);

		Scene secondScene = new Scene(root, 1500, 700);

		secondScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		newWindow.setScene(secondScene);

		newWindow.setTitle("Tulokset");

		VBox vasOtsikotVBox = new VBox();
		VBox vasTuloksetVBox = new VBox();
		vasOtsikotVBox.setSpacing(10);
		vasTuloksetVBox.setSpacing(10);
		vasTuloksetVBox.setMaxWidth(100);

		// Keskimääräinene läpimenoaika pylväsdiagrammi
		CategoryAxis xAxisAjat = new CategoryAxis();
		NumberAxis yAxisAjat = new NumberAxis();
		lapimenoajatBC = new BarChart<String, Number>(xAxisAjat, yAxisAjat);
		lapimenoajatBC.setTitle("Palvelupisteiden keskimääräiset läpimenoajat");
		xAxisAjat.setLabel("Palvelupiste");
		yAxisAjat.setLabel("Aika");
		seriesLapiMenoAjat = new XYChart.Series<>();

		// Kokonaisoleskeluajat diagrammi

		CategoryAxis xAxisKok = new CategoryAxis();
		NumberAxis yAxisKok = new NumberAxis();
		kokonaisajatBC = new BarChart<String, Number>(xAxisKok, yAxisKok);
		kokonaisajatBC.setTitle("Palvelupisteiden kokonaisoleskeluajat");
		xAxisAjat.setLabel("Palvelupiste");
		yAxisAjat.setLabel("Aika");

		seriesOleskelu = new XYChart.Series<>();

		// .setStyle("-fx-bar-fill: navy;");

		// Jonon pituus pylväsdiagrammi
		CategoryAxis xAxisJono = new CategoryAxis();
		NumberAxis yAxisJono = new NumberAxis();
		jonoBC = new BarChart<String, Number>(xAxisJono, yAxisJono);
		jonoBC.setTitle("Palvelupisteiden keskimääräiset jonojen pituudet");
		xAxisJono.setLabel("Palvelupiste");
		yAxisJono.setLabel("Pituus");
		seriesJono = new XYChart.Series<>();

		//

		Label labelParaCheck = new Label("Lähtöselvitys saapumisjakauma: ");
		Label labelParaLaukut = new Label("Matkalaukkujen saapumisjakauma: ");
		Label labelParaT1 = new Label("Turva1 jakauma: ");
		Label labelParaT2 = new Label("Turva2 jakauma: ");
		Label labelParaT3 = new Label("Turva3 jakauma: ");
		Label labelParaT4 = new Label("Turva4 jakauma: ");
		Label labelParaT5 = new Label("Turva5 jakauma: ");

		GridPane parametrit = new GridPane();
		parametrit.setHgap(10);
		parametrit.setPadding(new Insets(0, 10, 0, 30));
		VBox otsikotP = new VBox();
		VBox arvotP = new VBox();
		otsikotP.setSpacing(8);
		arvotP.setSpacing(8);
		otsikotP.getChildren().addAll(labelParaCheck, labelParaLaukut, labelParaT1, labelParaT2, labelParaT3,
				labelParaT4, labelParaT5);
		arvotP.getChildren().addAll(paraCheck, paraLaukut, paraT1, paraT2, paraT3, paraT4, paraT5);

		parametrit.add(otsikotP, 0, 0);
		parametrit.add(arvotP, 1, 0);

		// Lisätätään pylväsdiagrammit VBoxiin
		VBox kuvaajat = new VBox();
		kuvaajat.setPrefWidth(600);
		kuvaajat.setPrefHeight(1200);
		kuvaajat.setSpacing(50);
		kuvaajat.setPadding(new Insets(30, 5, 30, 5));
		kuvaajat.getChildren().addAll(lapimenoajatBC, kokonaisajatBC, jonoBC, parametrit);

		jonoBC.setAnimated(false);
		lapimenoajatBC.setAnimated(false);
		kokonaisajatBC.setAnimated(false);

		// muut tulokset
		Node otsikko = new Label("Tulokset");
		((Label) otsikko).setFont(Font.font("Arial", FontWeight.BOLD, 50));
		BorderPane.setAlignment(otsikko, Pos.TOP_CENTER);

		// Labelit joissa pysyvät tekstit
		Label asiakkaatLabel = new Label("Palvellut asiakkaat:");
		asiakkaatLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label kokonaisAikaLabel = new Label("Kokonaisaika:");
		kokonaisAikaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Node suoritustehoLabel = new Label("Palvelupisteiden suoritustehot:");
		VBox.setMargin(suoritustehoLabel, new Insets(20, 0, 0, 0));
		((Label) suoritustehoLabel).setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));

		Label selitysLabel = new Label("(palveltujen asiakkaiden lukumäärä aikayksikössä)");
		selitysLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));

		Label laukutLabel = new Label("Matkatavaroiden luovutuspiste:");
		VBox.setMargin(laukutLabel, new Insets(10, 0, 0, 0));
		laukutLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label checkinLabel = new Label("Lähtöselvitys:");
		checkinLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label t1Label = new Label("Turvatarkastuspiste 1:");
		t1Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label t2Label = new Label("Turvatarkastuspiste 2:");
		t2Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label t3Label = new Label("Turvatarkastuspiste 3:");
		t3Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label t4Label = new Label("Turvatarkastuspiste 4:");
		t4Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label t5Label = new Label("Turvatarkastuspiste 5:");
		t5Label.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		Label erityisLabel = new Label("Erityisturvatarkastus:");
		erityisLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		vasOtsikotVBox.getChildren().addAll(kokonaisAikaLabel, asiakkaatLabel, suoritustehoLabel, selitysLabel,
				laukutLabel, checkinLabel, t1Label, t2Label, t3Label, t4Label, t5Label, erityisLabel);

		// Labelit joihin asetetaan tulokset
		Label tyhja = new Label("");
		Label tyhja2 = new Label("");
		VBox.setMargin(tyhja, new Insets(20, 0, 0, 0));
		tyhja.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
		tyhja2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));

		asiakkaat.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		kokAika.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		t1.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		t2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		t3.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		t4.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		t5.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		erityis.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		laukut.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		VBox.setMargin(laukut, new Insets(10, 0, 0, 0));

		checkin.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));

		vasTuloksetVBox.getChildren().addAll(kokAika, asiakkaat, tyhja, tyhja2, laukut, checkin, t1, t2, t3, t4, t5,
				erityis);

		ScrollPane sp = new ScrollPane();
		sp.setMaxHeight(340);
		sp.setPrefSize(620, 340);
		sp.setContent(kuvaajat);
		Button tietokantaBtn = new Button("Hae tietokannasta");
		tietokantaBtn.setFont(Font.font("Tahoma", FontWeight.BOLD, 15));
		tietokantaBtn.setPrefSize(200, 30);
		tietokantaBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setValintaScene();
			}
		});
		BorderPane.setAlignment(tietokantaBtn, Pos.BOTTOM_RIGHT);
		root.setTop(otsikko);
		root.setBottom(tietokantaBtn);
		GridPane.setMargin(sp, new Insets(0, 0, 0, 100));
		grid.add(vasOtsikotVBox, 0, 1);
		grid.add(vasTuloksetVBox, 1, 1);
		grid.add(sp, 2, 1);
		newWindow.setScene(secondScene);
		newWindow.show();
	}

	/**
	 * Luo admin-scenen
	 */
	private void setAdminScene() {
		Stage newWindow = new Stage();
		BorderPane root = new BorderPane();
		root.setPadding(new Insets(30, 30, 30, 30));

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.TOP_CENTER);
		grid.setVgap(10);
		grid.setHgap(5);

		Label ohje = new Label(
				"Aseta turvapisteiden (palvelukesto)\nja saapumispisteiden (saapumistiheys)\njakaumille uudet arvot:");
		ohje.setFont(Font.font("Tahoma", FontWeight.NORMAL, 16));

		Label ahkeraLabel = new Label("Ahkera");
		ahkeraLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField ahkeraValue = new TextField();
		ahkeraValue.setPromptText("Syötä arvo");

		Label reipasLabel = new Label("Reipas");
		reipasLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField reipasValue = new TextField();
		reipasValue.setPromptText("Syötä arvo");

		Label perusLabel = new Label("Perus ");
		perusLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField perusValue = new TextField();
		perusValue.setPromptText("Syötä arvo");

		Label laiskaLabel = new Label("Laiska");
		laiskaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField laiskaValue = new TextField();
		laiskaValue.setPromptText("Syötä arvo");

		Label vetelaLabel = new Label("Vetelä");
		vetelaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField vetelaValue = new TextField();
		vetelaValue.setPromptText("Syötä arvo");

		Label todPRuuhkaLabel = new Label("Todella paljon");
		todPRuuhkaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField todPRuuhkaValue = new TextField();
		todPRuuhkaValue.setPromptText("Syötä arvo");

		Label paljonRuuhkaLabel = new Label("Paljon");
		paljonRuuhkaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField paljonRuuhkaValue = new TextField();
		paljonRuuhkaValue.setPromptText("Syötä arvo");

		Label normaaliRuuhkaLabel = new Label("Normaali");
		normaaliRuuhkaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField normaaliRuuhkaValue = new TextField();
		normaaliRuuhkaValue.setPromptText("Syötä arvo");

		Label vahanRuuhkaLabel = new Label("Vähän");
		vahanRuuhkaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField vahanRuuhkaValue = new TextField();
		vahanRuuhkaValue.setPromptText("Syötä arvo");

		Label todVahanRuuhkaLabel = new Label("Todella vähän");
		todVahanRuuhkaLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 14));
		TextField todVahanRuuhkaValue = new TextField();
		todVahanRuuhkaValue.setPromptText("Syötä arvo");

		Label ahkeraCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuAhkera()));
		Label reipasCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuReipas()));
		Label perusCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuPerus()));
		Label laiskaCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuLaiska()));
		Label vetelaCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuVetelä()));

		Label todPaljonCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuRuuhkaTodPaljon()));
		Label paljonCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuRuuhkaPaljon()));
		Label normaaliCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuRuuhkaNormaali()));
		Label vahanCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuRuuhkaVahan()));
		Label todVahanCurrent = new Label(Integer.toString(((Kontrolleri) kontrolleri).getLukuRuuhkaTodVahan()));

		Button okButton = new Button("OK");
		okButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					if (ahkeraValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri).setLukuAhkera(((Kontrolleri) kontrolleri).getLukuAhkera());
					} else {
						((Kontrolleri) kontrolleri).setLukuAhkera(Integer.parseInt(ahkeraValue.getText()));
					}

					if (reipasValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri).setLukuReipas(((Kontrolleri) kontrolleri).getLukuReipas());
					} else {
						((Kontrolleri) kontrolleri).setLukuReipas(Integer.parseInt(reipasValue.getText()));
					}

					if (perusValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri).setLukuPerus(((Kontrolleri) kontrolleri).getLukuPerus());
					} else {
						((Kontrolleri) kontrolleri).setLukuPerus(Integer.parseInt(perusValue.getText()));
					}

					if (laiskaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri).setLukuLaiska(((Kontrolleri) kontrolleri).getLukuLaiska());
					} else {
						((Kontrolleri) kontrolleri).setLukuLaiska(Integer.parseInt(laiskaValue.getText()));
					}

					if (vetelaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri).setLukuVetelä(((Kontrolleri) kontrolleri).getLukuVetelä());
					} else {
						((Kontrolleri) kontrolleri).setLukuVetelä(Integer.parseInt(vetelaValue.getText()));
					}

					if (todPRuuhkaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaTodPaljon(((Kontrolleri) kontrolleri).getLukuRuuhkaTodPaljon());
					} else {
						((Kontrolleri) kontrolleri).setLukuRuuhkaTodPaljon(Integer.parseInt(todPRuuhkaValue.getText()));
					}

					if (paljonRuuhkaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaPaljon(((Kontrolleri) kontrolleri).getLukuRuuhkaPaljon());
					} else {
						((Kontrolleri) kontrolleri).setLukuRuuhkaPaljon(Integer.parseInt(paljonRuuhkaValue.getText()));
					}

					if (normaaliRuuhkaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaNormaali(((Kontrolleri) kontrolleri).getLukuRuuhkaNormaali());
					} else {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaNormaali(Integer.parseInt(normaaliRuuhkaValue.getText()));
					}

					if (vahanRuuhkaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaVahan(((Kontrolleri) kontrolleri).getLukuRuuhkaVahan());
					} else {
						((Kontrolleri) kontrolleri).setLukuRuuhkaVahan(Integer.parseInt(vahanRuuhkaValue.getText()));
					}

					if (todVahanRuuhkaValue.getText().isEmpty()) {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaTodVahan(((Kontrolleri) kontrolleri).getLukuRuuhkaTodVahan());
					} else {
						((Kontrolleri) kontrolleri)
								.setLukuRuuhkaTodVahan(Integer.parseInt(todVahanRuuhkaValue.getText()));
					}

					Stage stage = (Stage) okButton.getScene().getWindow();

					stage.close();
				} catch (NumberFormatException ex) {
					Alert alert = new Alert(AlertType.ERROR, "Anna vain numeroita.", ButtonType.CLOSE);
					alert.setTitle("Lentokenttäsimu");
					alert.setHeaderText("Virheellinen arvo.");
					alert.showAndWait();
				}
			}
		});

		System.out.println();
		Button defaultit = new Button("Palauta oletusarvot");
		defaultit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					((Kontrolleri) kontrolleri).setLukuAhkera(5);
					((Kontrolleri) kontrolleri).setLukuReipas(10);
					((Kontrolleri) kontrolleri).setLukuPerus(15);
					((Kontrolleri) kontrolleri).setLukuLaiska(20);
					((Kontrolleri) kontrolleri).setLukuVetelä(25);

					((Kontrolleri) kontrolleri).setLukuRuuhkaTodPaljon(5);
					((Kontrolleri) kontrolleri).setLukuRuuhkaPaljon(10);
					((Kontrolleri) kontrolleri).setLukuRuuhkaNormaali(15);
					((Kontrolleri) kontrolleri).setLukuRuuhkaVahan(25);
					((Kontrolleri) kontrolleri).setLukuRuuhkaTodVahan(100);

					Stage stage = (Stage) okButton.getScene().getWindow();

					stage.close();
				} catch (NumberFormatException ex) {
					Alert alert = new Alert(AlertType.ERROR, "Virhe", ButtonType.CLOSE);
					alert.setTitle("Lentokenttäsimu");
					alert.setHeaderText("Virheellinen arvo");
					alert.showAndWait();
				}
			}
		});

		grid.add(ahkeraLabel, 0, 0);
		grid.add(ahkeraValue, 1, 0);
		grid.add(ahkeraCurrent, 2, 0);
		grid.add(reipasLabel, 0, 1);
		grid.add(reipasValue, 1, 1);
		grid.add(reipasCurrent, 2, 1);
		grid.add(perusLabel, 0, 2);
		grid.add(perusValue, 1, 2);
		grid.add(perusCurrent, 2, 2);
		grid.add(laiskaLabel, 0, 3);
		grid.add(laiskaValue, 1, 3);
		grid.add(laiskaCurrent, 2, 3);
		grid.add(vetelaLabel, 0, 4);
		grid.add(vetelaValue, 1, 4);
		grid.add(vetelaCurrent, 2, 4);

		grid.add(todPRuuhkaLabel, 0, 7);
		grid.add(todPRuuhkaValue, 1, 7);
		grid.add(todPaljonCurrent, 2, 7);
		grid.add(paljonRuuhkaLabel, 0, 8);
		grid.add(paljonRuuhkaValue, 1, 8);
		grid.add(paljonCurrent, 2, 8);
		grid.add(normaaliRuuhkaLabel, 0, 9);
		grid.add(normaaliRuuhkaValue, 1, 9);
		grid.add(normaaliCurrent, 2, 9);
		grid.add(vahanRuuhkaLabel, 0, 10);
		grid.add(vahanRuuhkaValue, 1, 10);
		grid.add(vahanCurrent, 2, 10);
		grid.add(todVahanRuuhkaLabel, 0, 11);
		grid.add(todVahanRuuhkaValue, 1, 11);
		grid.add(todVahanCurrent, 2, 11);

		grid.add(defaultit, 0, 14);
		grid.add(okButton, 1, 14);

		VBox vBox = new VBox();
		VBox.setMargin(ohje, new Insets(10, 10, 10, 10));
		vBox.getChildren().addAll(ohje, grid);

		Scene adminScene = new Scene(root, 400, 600);
		root.setCenter(vBox);
		newWindow.setScene(adminScene);

		newWindow.setTitle("Admin");

		newWindow.setScene(adminScene);
		newWindow.show();
	}

	/**
	 * Luo valinta scenen, josta mahdollista valita tietokannasta näytettävät
	 * tulokset
	 */
	private void setValintaScene() {
		Stage newWindow = new Stage();
		BorderPane root = new BorderPane();

		ListView<String> lw = new ListView<>(getTietokantaLista());
		Node otsikko = new Label("Valitse simulointikerta");
		((Label) otsikko).setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 20));
		BorderPane.setAlignment(otsikko, Pos.TOP_CENTER);
		BorderPane.setMargin(otsikko, new Insets(0, 0, 20, 0));
		Button ok = new Button("OK");
		ok.setPrefSize(70, 10);
		BorderPane.setAlignment(ok, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(ok, new Insets(20, 0, 10, 0));

		ok.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				int ajoId = Integer.valueOf(lw.getSelectionModel().getSelectedItem());

				kontrolleri.naytaLoppuaika(ajoId);
				kontrolleri.naytaPalvellutAsiakkkat(ajoId);
				((Kontrolleri) kontrolleri).asetaLapimenoajat(ajoId);

				((Kontrolleri) kontrolleri).asetaKokonaisoleskeluajat(ajoId);
				((Kontrolleri) kontrolleri).asetaJononpituudet(ajoId);
				((Kontrolleri) kontrolleri).asetaSuoritustehot(ajoId);
				((Kontrolleri) kontrolleri).asetaParametriTulokset(ajoId);

				Stage stage = (Stage) ok.getScene().getWindow();

				stage.close();
			}
		});

		root.setCenter(lw);
		root.setTop(otsikko);
		root.setBottom(ok);
		root.setPadding(new Insets(30, 30, 30, 30));

		Scene secondScene = new Scene(root, 300, 500);

		newWindow.setScene(secondScene);

		newWindow.setTitle("Tietokanta");

		newWindow.setScene(secondScene);
		newWindow.show();
	}

	/**
	 * Asettaa uuden listan joka saadaan parametrina
	 * 
	 * @param lista uusi lista
	 */
	public void setTietokantaLista(ArrayList<String> lista) {
		tietokantaLista = lista;
	}

	/**
	 * Palauttaa tietokantalistan
	 * 
	 * @return palauttaa listan
	 */
	private ObservableList<String> getTietokantaLista() {

		((Kontrolleri) kontrolleri).asetaListViewTietokanta();

		ObservableList<String> vaihtoehdot = FXCollections.observableArrayList(tietokantaLista);
		return vaihtoehdot;
	}

	/**
	 * Asettaa päivitetyn datan keskimääräiset läpimenoajat näyttävään
	 * pylväsdiagrammiin
	 */
	private void setLapimenoSeries() {
		seriesLapiMenoAjat = new XYChart.Series<>();
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(checkInS, lapiCheck));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(laukutS, lapiLaukut));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(turva1S, lapiT1));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(turva2S, lapiT2));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(turva3S, lapiT3));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(turva4S, lapiT4));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(turva5S, lapiT5));
		seriesLapiMenoAjat.getData().add(new XYChart.Data<>(erityisS, lapiErityis));

		seriesLapiMenoAjat.setName("Keskimääräinen läpimenoaika");
		lapimenoajatBC.getData().add(seriesLapiMenoAjat);

	}

	/**
	 * Asettaa päivitetyn datan keskimääräiset oleskeluajat näyttävään
	 * pylväsdiagrammiin
	 */
	private void setOleskeluSeries() {
		seriesOleskelu = new XYChart.Series<>();

		seriesOleskelu.getData().add(new XYChart.Data<>(checkInS, kokOleskeluCheck));
		seriesOleskelu.getData().add(new XYChart.Data<>(laukutS, kokOleskeluLaukut));
		seriesOleskelu.getData().add(new XYChart.Data<>(turva1S, kokOleskeluT1));
		seriesOleskelu.getData().add(new XYChart.Data<>(turva2S, kokOleskeluT2));
		seriesOleskelu.getData().add(new XYChart.Data<>(turva3S, kokOleskeluT3));
		seriesOleskelu.getData().add(new XYChart.Data<>(turva4S, kokOleskeluT4));
		seriesOleskelu.getData().add(new XYChart.Data<>(turva5S, kokOleskeluT5));
		seriesOleskelu.getData().add(new XYChart.Data<>(erityisS, kokOleskeluErityis));

		seriesOleskelu.setName("Kokonaisoleskeluaika");
		kokonaisajatBC.getData().add(seriesOleskelu);
	}

	/**
	 * Asettaa päivitetyn datan jononpituudet näyttävään pylväsdiagrammiin
	 */
	private void setJonoSeries() {
		seriesJono = new XYChart.Series<>();

		seriesJono.getData().add(new XYChart.Data<>(checkInS, jononPituusCheck));
		seriesJono.getData().add(new XYChart.Data<>(laukutS, jononPituusLaukut));
		seriesJono.getData().add(new XYChart.Data<>(turva1S, jononPituusT1));
		seriesJono.getData().add(new XYChart.Data<>(turva2S, jononPituusT2));
		seriesJono.getData().add(new XYChart.Data<>(turva3S, jononPituusT3));
		seriesJono.getData().add(new XYChart.Data<>(turva4S, jononPituusT4));
		seriesJono.getData().add(new XYChart.Data<>(turva5S, jononPituusT5));
		seriesJono.getData().add(new XYChart.Data<>(erityisS, jononPituusErityis));

		seriesJono.setName("Keskimääräinen jononpituus");
		jonoBC.getData().add(seriesJono);
	}

	/**
	 * Asettaa arvon viimeisenAjonId-muuttujaan
	 */
	public void setViimeisenAjonId(int id) {
		viimeisenAjonId = id;
	}

	/**
	 * Asettaa käyttöliittymän päänäkymässä näkyvän simuloinnin nollaukseen
	 * käytettävät nappulan aktiiviseksi
	 */
	public void resetButtonKayttoon() {
		resetButton.setDisable(false);
	}
}
