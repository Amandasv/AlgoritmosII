package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Resolution;
import com.senac.SimpleJava.Graphics.Sprite;

public class Paddle extends Sprite {

	public Paddle(int largura) {
		super(largura,3,Color.GRAY);
	}
	
	public void moveEsquerda(){
		Rect posicaoTamanhoPaddle = getBounds();
		if(posicaoTamanhoPaddle.x < 4){
			super.move(0,0);
		} else{
			super.move(-20, 0);
		}
	}
	
	public void moveDireita(){
		Rect posicaoTamanhoPaddle = getBounds();
		if(posicaoTamanhoPaddle.x > Resolution.MSX.width - 34){
			super.move(0, 0);
		}else{
			super.move(20, 0);
		}
	}
	
	public void colidiu(Bola bola){
		Rect posicaoBola = bola.getBounds();	
		Rect posicaoTamanhoPaddle = getBounds();
		
		if(	posicaoBola.x >= posicaoTamanhoPaddle.x && 
			posicaoBola.x <= posicaoTamanhoPaddle.x +posicaoTamanhoPaddle.width && 
			posicaoBola.y == posicaoTamanhoPaddle.y-posicaoTamanhoPaddle.height)
		{
			bola.invertVertical();
		}
		
	}

}
