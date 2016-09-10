package game;

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
	private boolean desenhaBloco=true;

	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		bola.draw(canvas);
		
		if(desenhaBloco){
			bloco.draw(canvas);
		}
		
		paddle.draw(canvas);
		
	}

	@Override
	protected void setup() {
		setResolution(Resolution.MSX);
		setFramesPerSecond(60);
		
		bola = new Bola();
		
		bloco = new Sprite(18,10,Color.RED);
		
		paddle = new Sprite(25,5,Color.BLACK);
		
		paddle.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-10
				);
		
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.move(-3,0);				
			}
		});
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.move(3,0);				
			}
		});
	}

	@Override
	protected void loop() {
		colidiuParede(bola);
		
		bola.move();
		
		Point position = bola.getPosition();
		Point blocoPosition = bloco.getPosition();
		
		if(position.x+bola.getWidth() < blocoPosition.x){
			desenhaBloco=true;
			// não bateu
		}
		
		else if(position.x > blocoPosition.x+bloco.getWidth() ){
			desenhaBloco=true;
			// não bateu
		}
		else if(position.y+bola.getHeight() < blocoPosition.y){
			desenhaBloco=true;
			// não bateu
		}
		else if(position.y > blocoPosition.y+bloco.getHeight()){
			desenhaBloco=true;
			// não bateu
		}
		else
			desenhaBloco = false;
		
		redraw();
	}
	
	private void colidiuParede(Bola bola) {
		Point posicao = bola.getPosition();
		if (posicao.x < 0 || posicao.x >= Resolution.MSX.width-5){
			bola.invertHorizontal();
		}
		if (posicao.y < 0 || posicao.y >= Resolution.MSX.height-5) {
			bola.invertVertical();
		}	
	}
}




