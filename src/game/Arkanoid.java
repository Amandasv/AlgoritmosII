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
	private Bloco bloco;
	private Bloco bloco2;
	private Bloco blocos[];

	private Sprite paddle;
	private int widhtPadlle = 25;
	private int heightPaddle = 3;
	private int pontos;
	private boolean desenhaBloco=true;
	private Point positionPaddle,positionBola;
	private Image background1, background2, background3;
	
	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		canvas.drawImage(background1, 0,0);
		
		canvas.setBackground(Color.BLACK);
		canvas.setForeground(Color.WHITE);
		canvas.putText(262, 1, 9, ((String)"Pontuação").toString());
		canvas.putText(270, 12, 9, ((Integer)pontos).toString());
		canvas.putText(264, 30, 9, ((String)"Recorde").toString());
		canvas.putText(270, 40, 9, ((Double)positionPaddle.x).toString());
		canvas.putText(264, 60, 9, ((String)"Vidas").toString());
		canvas.putText(270, 70, 9, ((Double)positionPaddle.x).toString());
		canvas.putText(264, 90, 9, ((String)"Fase").toString());
		canvas.putText(270, 100, 9, ((Double)positionPaddle.x).toString());

		bola.draw(canvas);
//		bloco.draw(canvas);
//		bloco2.draw(canvas);
		
		for (int i = 0; i < 50; i++) {
			blocos[i].draw(canvas);
		}
	

		paddle.draw(canvas);
		
//		for(int i=0;i<arrayBlocos.length;i++){
//			if(desenhaBloco){
//				arrayBlocos[i].draw(canvas);
//			}
//		} 
		
	}

	@Override
	protected void setup() {
		setResolution(Resolution.MODE_X);
		setFramesPerSecond(60);
		carregarImagens();
		bola = new Bola();
		bola.setPosition(10, 30);
		
		bloco = new Bloco(Color.CYAN);
		bloco.setPosition(10, 10);

		bloco2 = new Bloco(Color.YELLOW);
		bloco2.setPosition(10, 2);
		
		desenhaBlocos();
//		testeArray();
		
		paddle = new Sprite(widhtPadlle,heightPaddle,Color.GRAY);
		
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
		colidiuPaddle(bola);
		bloco.colidiu(bola);
		bloco2.colidiu(bola);
		
		for (int i = 0; i < 50; i++) {
			blocos[i].colidiu(bola);
		}
		
//		
//		bola.move();
//		
//		Point position = bola.getPosition();
		
		//verifica no array se bateu em algum bloco
		
//		for(int i=0;i<arrayBlocos.length;i++){  
//			Point positionBlocos = arrayBlocos[i].getPosition();
//			
//			if(position.x+bola.getWidth() < positionBlocos.x){
//				desenhaBloco=true;
//			}
//			
//			else if(position.x > positionBlocos.x+bloco.getWidth() ){
//				desenhaBloco=true;
//			}
//			else if(position.y+bola.getHeight() < positionBlocos.y){
//				desenhaBloco=true;
//
//			}
//			else if(position.y > positionBlocos.y+bloco.getHeight()){
//				desenhaBloco=true;
//			}
//			else{
//				desenhaBloco = false;
//				bola.invertVertical();
//				pontos ++;
//			}
//		} 

		
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
	
	private void colidiuPaddle(Bola bola){
		positionBola = bola.getPosition();
		positionPaddle = paddle.getPosition();
		if(positionBola.x >= positionPaddle.x && positionBola.x <= positionPaddle.x+20 && positionBola.y == positionPaddle.y-heightPaddle){
			bola.invertVertical();

		}		
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
		blocos = new Bloco[50];
		for (int i = 0; i < blocos.length; i++) {
			blocos[i] = new Bloco(Color.RED);
			int x = (i%10)*20+10;
			int y = (i/8)*10+5;
			int bottom = blocos[i].getHeight();
			blocos[i].setPosition(x,y);
		}

	}
	
	
//	int deltaMax = res.width - 35*5;
//	enemies = new GameObject[15];
//	for (int i =0; i < 15; i++) {
//		enemies[i] = new GameObject(img);
//		enemies[i].resize(30, 30);
//		int x = (i%5)*35+25;
//		int y = (i/5)*35+5;
//		int bottom = enemies[i].getHeight();
//		enemies[i].setPosition(new Point(x,y));
//		enemies[i].setLimits(new Rect(x,y,x+deltaMax,y+bottom));
//	}			
	
	
//	private void testeArray(){
//		arrayBlocos = new Sprite[2];
//		arrayBlocos[0] = new Sprite(18,10,Color.BLUE);
//		arrayBlocos[0].setPosition(50, 50);
//		arrayBlocos[1] = new Sprite(18,10,Color.YELLOW);
//		arrayBlocos[1].setPosition(40, 35);
//	}
}
