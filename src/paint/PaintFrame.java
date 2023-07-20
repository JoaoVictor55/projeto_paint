package paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

/**
 * 
 * Programa em si
 *
 */

public class PaintFrame extends JFrame {
	
	private final JButton desfazer, refazer, limpar;
	private final JPanel painelSuperior;
	
	private final String[] coresDisponiveis = {"preto", "vermelho", "azul", "laranja"};
	private final String[] formasDisponiveis = {"reta", "retangulo", "elipse", "pincel quadrado", "pincel redondo"};
	private final Color[] cores = {Color.black, Color.red, Color.blue, Color.orange};
	private final Paint.tiposFormas[] tiposFormas = {Paint.tiposFormas.LINHA, Paint.tiposFormas.RETANGULO,Paint.tiposFormas.ELIPSE, Paint.tiposFormas.CURVA_LIVRE_QUADRADO, Paint.tiposFormas.CURVA_LIVRE_REDONDO};
	private final JComboBox<String> coresMenu, coresPreenchimentoMenu, formas;
	private final JRadioButton removerPreenchimento;
	private boolean preencher = false;
	
	
	public PaintFrame() {
		super("Paint");
		
		JLabel status = new JLabel();
		
		removerPreenchimento = new JRadioButton("Remover preenchimento", true); //botões para remover o preenchimento da figura
		
		Paint p = new Paint(status);//instancia a tela de desenho
		
		add(p, BorderLayout.CENTER);//posiciona a tela no centro da janela
		
		painelSuperior = new JPanel(); //painel com os botões e menu de seleção de cores e formas
		painelSuperior.setLayout(new GridLayout(1, 8, 2, 1));
		
		
		//cria o menu de seleção de cores
		coresMenu = new JComboBox<String>(coresDisponiveis);
		coresMenu.setMaximumRowCount(3);//determina quantas cores são exibidas por vez

		coresMenu.addItemListener(e ->{
			if(e.getStateChange() == ItemEvent.SELECTED) {
				p.setCorAtual(cores[coresMenu.getSelectedIndex()]);
			}
		});
		
		//cria o menu de seleção de formas
		formas = new JComboBox<String>(formasDisponiveis);
		formas.addItemListener(e -> {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				p.setTipoForma((tiposFormas[formas.getSelectedIndex()]));
			}
		});
		
		formas.setMaximumRowCount(3); //determina quantas formas são exibidas por vez
		

		//cria o menu de seleção de cores de preenchimento
		coresPreenchimentoMenu = new JComboBox<String>(coresDisponiveis);
		coresPreenchimentoMenu.setMaximumRowCount(3);
		
		
		coresPreenchimentoMenu.addItemListener(e ->{
			if(e.getStateChange() == ItemEvent.SELECTED && preencher) {
				p.setPreenchimento(cores[coresPreenchimentoMenu.getSelectedIndex()]);
			}

		});
		
		//botão para remover o preenchimento
		removerPreenchimento.addItemListener(e -> {
			p.setPreenchimento(null);
			preencher = !preencher;
			//permite que a cor selecionada no menu de cores de preenchimento seja aplicada de vez
			//sem isso, seria necessários selecioná-la de novo
			if(preencher) {
				p.setPreenchimento(cores[coresPreenchimentoMenu.getSelectedIndex()]);
			}
		});
		
		//adiciona os menus e botões
		
		painelSuperior.add(new JLabel("cor de contorno"));
		painelSuperior.add(coresMenu);
		painelSuperior.add(new JLabel("cor de preenchimento"));
		painelSuperior.add(coresPreenchimentoMenu);
		painelSuperior.add(removerPreenchimento);
		painelSuperior.add(formas);
	
		
		desfazer = new JButton("Desfazer");
		refazer = new JButton("Refazer");;
		limpar = new JButton("Limpar");
		
		painelSuperior.add(desfazer);
		painelSuperior.add(refazer);
		painelSuperior.add(limpar);
		
		add(painelSuperior, BorderLayout.NORTH);
		
		add(status, BorderLayout.SOUTH);
		
		desfazer.addActionListener(e ->{p.limparUltimaForma();});
		limpar.addActionListener(e->{p.limparTudo();});
		refazer.addActionListener(e ->{p.refazer();});
		
		
	}
	
	



public static void main(String [] args) {
	
	PaintFrame p = new PaintFrame();
	p.setSize(900, 900);
	p.setVisible(true); 
	p.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}