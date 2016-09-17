package game;

import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Image;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Paddle extends Sprite {

	public Paddle(int largura) {
		super(largura,3,Color.GRAY);
	}
	
	public void colidiu(Bola bola){
		Point posicaoBola = bola.getPosition();	
		Rect posicaoTamanhoPaddle = getBounds();
		
		if(	posicaoBola.x >= posicaoTamanhoPaddle.x && 
			posicaoBola.x <= posicaoTamanhoPaddle.x +20 && 
			posicaoBola.y == posicaoTamanhoPaddle.y-posicaoTamanhoPaddle.height)
		{
			bola.invertVertical();
		}
	}

}
