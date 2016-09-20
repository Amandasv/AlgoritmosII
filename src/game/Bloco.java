package game;

import com.senac.SimpleJava.Graphics.Canvas;
import com.senac.SimpleJava.Graphics.Color;
import com.senac.SimpleJava.Graphics.Point;
import com.senac.SimpleJava.Graphics.Rect;
import com.senac.SimpleJava.Graphics.Sprite;

public class Bloco extends Sprite{

	private boolean aparecer = true;
	private int contN2;
	
	public Bloco(Color cor) {
		super(18, 10, cor);
		
	}
	
	public boolean colidiu(Bola bola,int numeroColisao){
		
		if(!aparecer)
			return false;
		
		Point posicaoBola = bola.getPosition();	
		Rect posicaoTamanhoBloco = getBounds();
		
		if(posicaoBola.x + bola.getWidth() >= posicaoTamanhoBloco.x && posicaoBola.x <= posicaoTamanhoBloco.x + posicaoTamanhoBloco.width){
			if(posicaoBola.y + bola.getHeight() >= posicaoTamanhoBloco.y && posicaoBola.y <= posicaoTamanhoBloco.y + posicaoTamanhoBloco.height){
				mudaComportamento(numeroColisao);
				return true;
			}
		}
		return false;

		}

	
	public boolean mudaComportamento(int numeroColisao){
		
		switch (numeroColisao) {
		case 1:
			aparecer = false;
			break;
		case 2:			
			contN2++;
			if(contN2 == 2){
				aparecer = false;				
			}
			break;

		default:
			break;
		}
		return aparecer;
	}
	
	
	
	@Override
	public void draw(Canvas canvas) {
		if(aparecer){
			super.draw(canvas); 
		}
	}

}


