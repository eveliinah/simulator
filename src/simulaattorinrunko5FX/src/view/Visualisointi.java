package simulaattorinrunko5FX.src.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Visualisointi extends Canvas implements IVisualisointi {

	private GraphicsContext gc;

	double i = 140;
	double j = 215;
	double x = 140;
	double y = 415;
	double a = 265;
	double b = 110;
	double c = 365;
	double d = 110;
	double e = 465;
	double f = 110;
	double g = 565;
	double h = 110;
	double k = 665;
	double l = 110;
	double m = 740;
	double n = 250;

	//

	public Visualisointi(int w, int h) {
		super(w, h);
		gc = this.getGraphicsContext2D();
		tyhjennaNaytto();
	}

	public void tyhjennaNaytto() {
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		luoCheckinPisteet();
		
	}

	// Turvapisteiden määrän päivitys
	public void tyhjennaNaytto(int maara) {
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());
		luoCheckinPisteet();
		luoUITurvaPisteet(maara);
	}

	public void laukkuAsiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(i, j, 20, 20);

		i = (i - 20);
		if (i <= 0) {
			i = 140;
			j -= 20;
		}
	}

	public void poistaLaukkuAsiakas() {
		if (i >= 140) {
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillOval(i, j, 20, 20);
			i = 20;
			j += 20;
		} else {
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillOval(i, j, 20, 20);
			i += 20;
		}
	}

	public void laukutonAsiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(x, y, 20, 20);

		x = (x - 20);
		if (x <= 0) {
			x = 140;
			y += 20;
		}
	}

	public void poistaLaukutonAsiakas() {
		if (x >= 140) {
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillOval(x, y, 20, 20);
			x = 20;
			y -= 20;
		} else {
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillOval(x, y, 20, 20);
			x += 20;
		}
	}

	public void turva1Asiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(a, b, 20, 20);

		b = (b + 20);
	}

	public void poistaTurva1Asiakas() {
		b -= 20;
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillOval(a, b, 20, 20);
	}
	
	public void turva2Asiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(c, d, 20, 20);

		d = (d + 20);
	}

	public void poistaTurva2Asiakas() {
		d -= 20;
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillOval(c, d, 20, 20);
	}
	
	public void turva3Asiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(e, f, 20, 20);

		f = (f + 20);
	}

	public void poistaTurva3Asiakas() {
		f -= 20;
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillOval(e, f, 20, 20);
		
	}
	
	public void turva4Asiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(g, h, 20, 20);

		h = (h + 20);
	}

	public void poistaTurva4Asiakas() {
		h -= 20;
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillOval(g, h, 20, 20);
	}
	
	public void turva5Asiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(k, l, 20, 20);

		l = (l + 20);
	}

	public void poistaTurva5Asiakas() {
		l -= 20;
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillOval(k, l, 20, 20);
	}
	
	public void eTurvaAsiakas() {
		gc.setFill(Color.DARKGREEN);
		gc.fillOval(m, n, 20, 20);

		n = (n + 20);
	}

	public void poistaETurvaAsiakas() {
		n -= 20;
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillOval(m, n, 20, 20);
	}
	
	public void visualisoiLaukutPalvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(171, 180, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 175, 195);
	}
	
	public void visualisoiCheckinPalvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(171, 380, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 175, 395);
	}
	
	public void visualisoiTurva1Palvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(261, 40, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 265, 55);
	}
	
	public void visualisoiTurva2Palvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(361, 40, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 365, 55);
	}
	
	public void visualisoiTurva3Palvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(461, 40, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 465, 55);
	}
	
	public void visualisoiTurva4Palvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(561, 40, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 565, 55);
	}
	
	public void visualisoiTurva5Palvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(661, 40, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 665, 55);
	}
	
	public void visualisoiETurvaPalvelumaara(int nro) {
		String txt = Integer.toString(nro);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(736, 180, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 740, 195);
	}
	
	public void visualisoiViive(long viive) {
		String txt = Long.toString(viive);
		gc.setFill(Color.rgb(208, 241, 218));
		gc.fillRect(736, 20, 60, 20);
		gc.setFill(Color.BLACK);
		gc.fillText(txt, 740, 35);
	}


	public void luoCheckinPisteet() {
		gc.setFont(new Font("Arial", 15));
		gc.setFill(Color.rgb(139, 150, 167));
		gc.fillRect(160, 200, 50, 50);
		gc.fillRect(160, 400, 50, 50);
		gc.setFill(Color.WHITE);
		gc.fillText("Ruuma", 161, 230);
		gc.setFill(Color.BLACK);
		gc.fillText("Palveltu:", 161, 180);
		gc.fillText("Palveltu:", 161, 380);
		gc.setFill(Color.WHITE);
		gc.fillText("Käsi-", 168, 425);
		gc.fillText("tavarat", 162, 437);

		gc.setFill(Color.rgb(92, 133, 173));
		gc.fillRect(250, 60, 50, 50);
		gc.setFill(Color.BLACK);
		gc.fillText("Palveltu:", 251, 40);
		gc.setFill(Color.WHITE);
		gc.fillText("Turva", 255, 82);
		gc.fillText("1", 271, 100);
		
		gc.setFill(Color.rgb(129, 166, 119));
		gc.fillRect(725, 200, 50, 50);
		gc.setFill(Color.BLACK);
		gc.fillText("Palveltu:", 726, 180);
		gc.setFill(Color.WHITE);
		gc.fillText("E-turva", 726, 230);
		
		gc.setFill(Color.BLACK);
		gc.fillText("Viive:", 730, 20);
	}

	public void luoUITurvaPisteet(int maara) {
		switch (maara) {
		case 0:
			gc.setFill(Color.rgb(92, 133, 173));
			gc.fillRect(250, 60, 50, 50);
			gc.setFill(Color.BLACK);
			gc.fillText("Palveltu:", 251, 40);
			gc.setFill(Color.WHITE);
			gc.fillText("Turva", 255, 82);
			gc.fillText("1", 271, 100);
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillRect(350, 60, 50, 50);
			gc.fillRect(450, 60, 50, 50);
			gc.fillRect(550, 60, 50, 50);
			gc.fillRect(650, 60, 50, 50);
			break;
		case 1:
			gc.setFill(Color.rgb(92, 133, 173));
			gc.fillRect(250, 60, 50, 50);
			gc.fillRect(350, 60, 50, 50);
			gc.setFill(Color.BLACK);
			gc.fillText("Palveltu:", 251, 40);
			gc.fillText("Palveltu:", 351, 40);
			gc.setFill(Color.WHITE);
			gc.fillText("Turva", 255, 82);
			gc.fillText("1", 271, 100);
			gc.fillText("Turva", 355, 82);
			gc.fillText("2", 371, 100);
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillRect(450, 60, 50, 50);
			gc.fillRect(460, 400, 50, 50);
			gc.fillRect(460, 500, 50, 50);
			break;
		case 2:
			gc.setFill(Color.rgb(92, 133, 173));
			gc.fillRect(250, 60, 50, 50);
			gc.fillRect(350, 60, 50, 50);
			gc.fillRect(450, 60, 50, 50);
			gc.setFill(Color.BLACK);
			gc.fillText("Palveltu:", 251, 40);
			gc.fillText("Palveltu:", 351, 40);
			gc.fillText("Palveltu:", 451, 40);
			gc.setFill(Color.WHITE);
			gc.fillText("Turva", 255, 82);
			gc.fillText("1", 271, 100);
			gc.fillText("Turva", 355, 82);
			gc.fillText("2", 371, 100);
			gc.fillText("Turva", 455, 82);
			gc.fillText("3", 471, 100);
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillRect(550, 60, 50, 50);
			gc.fillRect(650, 60, 50, 50);
			break;
		case 3:
			gc.setFill(Color.rgb(92, 133, 173));
			gc.fillRect(250, 60, 50, 50);
			gc.fillRect(350, 60, 50, 50);
			gc.fillRect(450, 60, 50, 50);
			gc.fillRect(550, 60, 50, 50);
			gc.setFill(Color.BLACK);
			gc.fillText("Palveltu:", 251, 40);
			gc.fillText("Palveltu:", 351, 40);
			gc.fillText("Palveltu:", 451, 40);
			gc.fillText("Palveltu:", 551, 40);
			gc.setFill(Color.WHITE);
			gc.fillText("Turva", 255, 82);
			gc.fillText("1", 271, 100);
			gc.fillText("Turva", 355, 82);
			gc.fillText("2", 371, 100);
			gc.fillText("Turva", 455, 82);
			gc.fillText("3", 471, 100);
			gc.fillText("Turva", 555, 82);
			gc.fillText("4", 571, 100);
			gc.setFill(Color.rgb(208, 241, 218));
			gc.fillRect(650, 60, 50, 50);
			break;
		case 4:
			gc.setFill(Color.rgb(92, 133, 173));
			gc.fillRect(250, 60, 50, 50);
			gc.fillRect(350, 60, 50, 50);
			gc.fillRect(450, 60, 50, 50);
			gc.fillRect(550, 60, 50, 50);
			gc.fillRect(650, 60, 50, 50);
			gc.setFill(Color.BLACK);
			gc.fillText("Palveltu:", 251, 40);
			gc.fillText("Palveltu:", 351, 40);
			gc.fillText("Palveltu:", 451, 40);
			gc.fillText("Palveltu:", 551, 40);
			gc.fillText("Palveltu:", 651, 40);
			gc.setFill(Color.WHITE);
			gc.fillText("Turva", 255, 82);
			gc.fillText("1", 271, 100);
			gc.fillText("Turva", 355, 82);
			gc.fillText("2", 371, 100);
			gc.fillText("Turva", 455, 82);
			gc.fillText("3", 471, 100);
			gc.fillText("Turva", 555, 82);
			gc.fillText("4", 571, 100);
			gc.fillText("Turva", 655, 82);
			gc.fillText("5", 671, 100);
			break;
		}
	}

	public double getI() {
		return i;
	}

	public double getJ() {
		return j;
	}

}
