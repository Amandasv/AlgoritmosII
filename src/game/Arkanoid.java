package game;

import java.io.IOException;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private Paddle paddle;
	
	private Bloco blocosBrancos[];
	private Bloco blocosAzuis[];
	private Bloco blocosPretos[];

	private int pontos;
	private Image background1, background2, background3;
	private int vidas = 3;
	
	public String teste;
	
	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		canvas.drawImage(background3, 0,0);
		
		canvas.setBackground(Color.BLACK);
		canvas.setForeground(Color.WHITE);
		canvas.putText(262, 1, 9, ((String)"Pontuação").toString());
		canvas.putText(270, 12, 9, ((Integer)pontos).toString());
		canvas.putText(264, 30, 9, ((String)"Recorde").toString());
//		canvas.putText(270, 40, 9, ((Double)positionPaddle.x).toString());
		canvas.putText(264, 60, 9, ((String)"Vidas").toString());
		canvas.putText(270, 70, 9, ((Integer)vidas).toString());
		canvas.putText(264, 90, 9, ((String)"Fase").toString());
//		canvas.putText(270, 100, 9, ((Double)positionPaddle.x).toString());

		bola.draw(canvas);
		
		for (int i = 0; i < 12; i++) {
			blocosBrancos[i].draw(canvas);
		}
		for (int i = 0; i < 12; i++) {
			blocosAzuis[i].draw(canvas);
		}
		for (int i = 0; i < 12; i++) {
			blocosPretos[i].draw(canvas);
		}		

		paddle.draw(canvas);
		
	}

	@Override
	protected void setup() {
		setResolution(Resolution.MODE_X);
		setFramesPerSecond(60);
		carregarImagens();
		
		
		bola = new Bola();
		
		bola.setPosition(Resolution.MSX.width/2-5,
				Resolution.MSX.height-60);
			
		desenhaBlocos();
		
		paddle = new Paddle(25);
		
		paddle.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-50
				);
		
		
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point positionPaddle = paddle.getPosition();
				if(positionPaddle.x < 4){
					paddle.move(0, 0);
				}else{
					paddle.move(-20,0);
				}
			}
		});
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				Point positionPaddle = paddle.getPosition();
				if(positionPaddle.x > Resolution.MSX.width - 34){
					paddle.move(0,0);
				}else{
					paddle.move(20,0);
				}
								
			}
		});		
		
	}

	@Override
	protected void loop() {
		colidiuParede(bola);
		paddle.colidiu(bola);
		passouPaddle(bola);
		
		
		for (int i = 0; i < 12; i++) {
			blocosBrancos[i].colidiu(bola, 1);
		}
		
		for (int i = 0; i < 12; i++) {
			blocosAzuis[i].colidiu(bola, 2);
		}

		for (int i = 0; i < 12; i++) {
			blocosPretos[i].colidiu(bola, 1);
		}
		
		bola.move();
	 	
		redraw();
	}
	
	private void colidiuParede(Bola bola) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width-5 ){
			bola.invertHorizontal();
		}
		if (posicao.y < 0 || posicao.y >= Resolution.MSX.height-5) {
			bola.invertVertical();
		}	
	}
	
	private void passouPaddle(Bola bola){
		Point posicaoBola = bola.getPosition();
		Point posicaoPaddle = paddle.getPosition();
		
		if(posicaoBola.y > posicaoPaddle.y){
			vidas--;
			iniciaJogo();
		}	
		
	}
	
	private void iniciaJogo(){
		paddle.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-50
				);
		
		bola.setPosition(Resolution.MSX.width/2-5,
				Resolution.MSX.height-60);
	}

	private void carregarImagens() {
		try {
			background1 = new Image("imagens/background_1.jpg");
			background2 = new Image("imagens/background_2.jpg");
			background3 = new Image("imagens/background_3.jpg");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
	
	private void desenhaBlocos(){
		blocosBrancos = new Bloco[12];
		blocosAzuis = new Bloco[12];
		blocosPretos = new Bloco[12];
		
		for (int i = 0; i < blocosBrancos.length; i++) {
			blocosBrancos[i] = new Bloco(Color.WHITE);
			int x = (i%12)*20+10;
			int y = 10;
			blocosBrancos[i].setPosition(x,y);
		}
		
		for (int i = 0; i < blocosAzuis.length; i++) {
			blocosAzuis[i] = new Bloco(Color.BLUE);
			int x = (i%12)*20+10;
			int y = 25;
			blocosAzuis[i].setPosition(x,y);
		}
		
		for (int i = 0; i < blocosPretos.length; i++) {
			blocosPretos[i] = new Bloco(Color.BLACK);
			int x = (i%12)*20+10;
			int y = 40;
			blocosPretos[i].setPosition(x,y);
		}
		
	}

}
