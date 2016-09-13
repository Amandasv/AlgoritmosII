package game;

import java.io.IOException;

import com.senac.SimpleJava.Console;
import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.GraphicApplication;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;
import com.senac.SimpleJava.Graphics.events.KeyboardAction;

public class Arkanoid extends GraphicApplication {

	private Bola bola;
	private Sprite bloco;
	private Sprite paddle;
	private int widhtPadlle = 25;
	private int heightPaddle = 3;
	private boolean desenhaBloco=true;
	private Point positionPaddle,positionBola;
	private Image background;
	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		canvas.drawImage(background, 0,0);
		canvas.setBackground(Color.BLACK);
		canvas.setForeground(Color.WHITE);
		canvas.putText(256, 1, 9, ((Double)positionPaddle.x).toString());
//		canvas.putText(70, 10, 12, ((Double)positionPaddle.y).toString());
//		canvas.putText(70, 30, 12, ((Double)positionBola.x).toString());
//		canvas.putText(70, 40, 12, ((Double)positionBola.y).toString());

		bola.draw(canvas);
		
		if(desenhaBloco){
			bloco.draw(canvas);
		}
		
		paddle.draw(canvas);
		
	}

	@Override
	protected void setup() {
		setResolution(Resolution.MODE_X);
		setFramesPerSecond(60);
		importaImagens();
		bola = new Bola();
		bola.setPosition(0, 30);
		bloco = new Sprite(18,10,Color.RED);
		bloco.setPosition(10, 10);
		
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
//		
		bola.move();
//		
		Point position = bola.getPosition();
		Point blocoPosition = bloco.getPosition();
		
		if(position.x+bola.getWidth() < blocoPosition.x){
			desenhaBloco=true;
		}
		
		else if(position.x > blocoPosition.x+bloco.getWidth() ){
			desenhaBloco=true;
		}
		else if(position.y+bola.getHeight() < blocoPosition.y){
			desenhaBloco=true;

		}
		else if(position.y > blocoPosition.y+bloco.getHeight()){
			desenhaBloco=true;
		}
		else
			desenhaBloco = false;
		
		redraw();
	}
	
	private void colidiuParede(Bola bola) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width-5 ){
			bola.invertHorizontal();
		}
		if (posicao.y < 0 || posicao.y >= Resolution.MSX.height-5 || posicao.y <= 10) {
			bola.invertVertical();
		}	
	}
	
	private void colidiuPaddle(Bola bola){
		positionBola = bola.getPosition();
		positionPaddle = paddle.getPosition();
		if(positionBola.x >= positionPaddle.x && positionBola.x <= positionPaddle.x+20 && positionBola.y == positionPaddle.y-3){
			bola.invertVertical();

		}		
	}
	private void importaImagens() {
		try {
			background = new Image("imagens/bg_dia_verde.jpg");
		} catch (IOException e) {
			e.printStackTrace(System.err);
		}
	}
}
