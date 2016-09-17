package game;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bloco extends Sprite{

	private boolean aparecer = true;
	public Bloco() {
		super(18, 10, Color.BLUE);
		
	}
	
	public boolean colidiu(Bola bola){
		
		if(!aparecer)
			return false;
		
		Point posicaoBola = bola.getPosition();	
		Rect posicaoTamanhoBloco = getBounds();

		
		if(posicaoBola.x+bola.getWidth() < posicaoTamanhoBloco.x ){
			return true;
		} else if(posicaoBola.x > posicaoTamanhoBloco.x+posicaoTamanhoBloco.width){
			return true;
		} else if(posicaoBola.y + bola.getHeight() < posicaoTamanhoBloco.y){
			return true;
		} else if (posicaoBola.y > posicaoTamanhoBloco.y+posicaoTamanhoBloco.height){
			return true;
		} else{
			aparecer = false;
			bola.invertVertical();
			return false;

		}
			
	}
	
	@Override
	public void draw(Canvas canvas) {
		if(aparecer){
			super.draw(canvas); 
		}
	}

}

//Point position = bola.getPosition();

//else if(position.y > blocoPosition.y+bloco.getHeight()){
//	desenhaBloco=true;
//}
//else{
//	desenhaBloco = false;
//	bola.invertVertical();
//	pontos ++;
//}
