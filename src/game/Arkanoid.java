package game;

import java.io.IOException;

import javax.swing.JOptionPane;

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
	
	private int pontos;
	private Image background1, background2, background3;
	private int vidas = 3;
	private int fase = 1, recorde = 2300;
	public String teste;
	private int tamanhoArray = 12;
	
	private Bloco[] blocosAzuis = new Bloco[tamanhoArray];
	private Bloco[] blocosBrancos = new Bloco[tamanhoArray];
	private Bloco[] blocosPretos = new Bloco[tamanhoArray];
	
	private Bloco[] blocosRosas = new Bloco[tamanhoArray];
	private Bloco[] blocosCinzas = new Bloco[tamanhoArray];
	private Bloco[] blocosCinzaClaro = new Bloco[tamanhoArray];
	
	private Bloco[] blocosVerdes = new Bloco[tamanhoArray];
	
	
	
	@Override
	protected void draw(Canvas canvas) {
		canvas.clear();
		
		if (fase == 1){
			canvas.drawImage(background1, 0,0);		
			for (int i = 0; i < 12; i++) {
				blocosBrancos[i].draw(canvas);
				blocosAzuis[i].draw(canvas);								
			}
			
		} else if(fase == 2){
			canvas.drawImage(background2, 0,0);		
			for (int i = 0; i < 12; i++) {				
				blocosRosas[i].draw(canvas);
				blocosCinzas[i].draw(canvas);
				blocosCinzaClaro[i].draw(canvas);				
			}

		} else if(fase == 3){
			canvas.drawImage(background3, 0,0);
			for (int i = 0; i < 12; i++) {	
				blocosVerdes[i].draw(canvas);
				blocosPretos[i].draw(canvas);
			}
		}
		
		canvas.setBackground(Color.BLACK);
		canvas.setForeground(Color.WHITE);
		canvas.putText(262, 1, 9, ((String)"Pontos").toString());
		canvas.putText(270, 12, 9, ((Integer)pontos).toString());
		canvas.putText(264, 30, 9, ((String)"Vidas").toString());
		canvas.putText(270, 40, 9, ((Integer)vidas).toString());
		canvas.putText(264, 60, 9, ((String)"Fase").toString());
		canvas.putText(270, 70, 9, ((Integer)fase).toString());		
		canvas.putText(264, 90, 9, ((String)"Recorde").toString());
		canvas.putText(270, 100, 9, ((Integer)recorde).toString());

		bola.draw(canvas);		
		
		paddle.draw(canvas);
		
	}

	@Override
	protected void setup() {
		setResolution(Resolution.MODE_X);
		setFramesPerSecond(60);
		carregarImagens();		
		
		bola = new Bola();
		paddle = new Paddle(35);
				
		criaBlocos(blocosAzuis, Color.BLUE, 15);
		criaBlocos(blocosBrancos, Color.WHITE, 40);		
		
		criaBlocos(blocosRosas, Color.MAGENTA, 10);
		criaBlocos(blocosCinzas, Color.GRAY, 25);
		criaBlocos(blocosCinzaClaro, Color.LIGHTGRAY, 40);
		
		criaBlocos(blocosVerdes, Color.GREEN, 5);
		criaBlocos(blocosPretos, Color.BLACK, 25);		
				
		iniciaJogo();
		
		bindKeyPressed("LEFT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.moveEsquerda();
			}
		});
		
		bindKeyPressed("RIGHT", new KeyboardAction() {
			@Override
			public void handleEvent() {
				paddle.moveDireita();
								
			}
		});		
		
	}

	@Override
	protected void loop() {
		colidiuParede(bola);
		paddle.colidiu(bola);
		passouPaddle(bola);		
		
		controlaFases(pontos);

		
		if (fase == 1){
			verificaColisao(blocosBrancos, 1);
			verificaColisao(blocosAzuis, 1);
			
		} else if (fase == 2){
			verificaColisao(blocosRosas, 2);
			verificaColisao(blocosCinzas, 1);
			verificaColisao(blocosCinzaClaro, 1);
		} else if (fase == 3){
			verificaColisao(blocosVerdes, 2);
			verificaColisao(blocosPretos, 2);
		}
		
		bola.move();
		
		if(pontos >= recorde){
			recorde = pontos;
		}
		
		redraw();
	}
	
	public void controlaFases(int pontos){
		if(pontos < 2400){
			fase = 1;
		} else if(pontos >= 2400 && pontos < 7200){
			fase = 2;			
		} else{
			fase = 3;
		}
	}
		
	public void verificaColisao(Bloco[] blocos, int numeroColisao){
		Rect posicaoBola = bola.getBounds();		
		for (int i = 0; i < blocos.length; i++) {
			Rect posicaoTamanhoBloco = blocos[i].getBounds();
			if(blocos[i].colidiu(bola, numeroColisao)){
				if(posicaoBola.y + posicaoBola.height == posicaoTamanhoBloco.y ||
						posicaoBola.y == posicaoTamanhoBloco.y + posicaoTamanhoBloco.height){
					bola.invertVertical();
				}else{
					bola.invertHorizontal();
				}
				pontos = pontos +100;
	
			}
		}
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
			if(vidas > 0){
				vidas--;
				JOptionPane.showMessageDialog(null, "Cuidado! Você perdeu uma vida, agora só restam:"+(vidas));
				iniciaJogo();
			} else {
				JOptionPane.showMessageDialog(null, "Fim de jogo!");
				System.exit(0);
			}
			
		}		
	}
	
	private void iniciaJogo(){
		paddle.setPosition(
				Resolution.MSX.width/2-5,
				Resolution.MSX.height-20
				);
		
		bola.setPosition(Resolution.MSX.width/2-5,
				Resolution.MSX.height-100);
		
		bola.move();
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
	
	private void criaBlocos(Bloco[] blocos, Color cor, int posicaoY){		
		for (int i = 0; i < blocos.length; i++) {
			blocos[i] = new Bloco(cor);
			int x = (i)*20+10;
			int y = posicaoY;
			blocos[i].setPosition(x,y);
		}		
	}

}
