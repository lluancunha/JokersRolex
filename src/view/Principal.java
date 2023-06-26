package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Toolkit;

@SuppressWarnings("unused")
public class Principal extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblData;
	public JLabel lblUsuario;
	public JButton btnUsuarios;
	public JButton btnRelatorio;
	public JPanel panelRodape;
	public JButton btnTec;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Principal.class.getResource("/img/aplle.png")));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				setarData();
			}
		});
		setResizable(false);
		setTitle("Jokers Rolex");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 695, 480);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panelRodape = new JPanel();
		panelRodape.setBackground(SystemColor.desktop);
		panelRodape.setBounds(0, 395, 679, 46);
		contentPane.add(panelRodape);
		panelRodape.setLayout(null);
		
		lblData = new JLabel("");
		lblData.setBounds(433, 11, 345, 33);
		panelRodape.add(lblData);
		lblData.setForeground(SystemColor.text);
		lblData.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblUsuario = new JLabel("");
		lblUsuario.setForeground(new Color(255, 255, 255));
		lblUsuario.setBounds(75, 17, 172, 14);
		panelRodape.add(lblUsuario);
		lblUsuario.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		JLabel lblNewLabel = new JLabel("Usuario:");
		lblNewLabel.setBounds(10, 11, 79, 24);
		panelRodape.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setIcon(new ImageIcon(Principal.class.getResource("/img/joker3.jpg")));
		lblLogo.setBounds(327, 124, 342, 260);
		contentPane.add(lblLogo);
		
		btnUsuarios = new JButton("");
		btnUsuarios.setEnabled(false);
		btnUsuarios.setBorderPainted(false);
		btnUsuarios.setContentAreaFilled(false);
		btnUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//evento clicar no botão
				Usuarios usuarios = new Usuarios();
				usuarios.setVisible(true);
			}
		});
		btnUsuarios.setToolTipText("Usuários");
		btnUsuarios.setIcon(new ImageIcon(Principal.class.getResource("/img/user75.png")));
		btnUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUsuarios.setBounds(47, 45, 64, 64);
		contentPane.add(btnUsuarios);
		
		JButton btnSobre = new JButton("");
		btnSobre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSobre.setContentAreaFilled(false);
		btnSobre.setBorderPainted(false);
		btnSobre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Sobre sobre = new Sobre();
				sobre.setVisible(true);
			}
		});
		btnSobre.setIcon(new ImageIcon(Principal.class.getResource("/img/about1.png")));
		btnSobre.setBounds(600, 23, 48, 48);
		contentPane.add(btnSobre);
		
		btnRelatorio = new JButton("");
		btnRelatorio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Relatorios relatorios = new Relatorios();
				relatorios.setVisible(true);
			}
		});
		btnRelatorio.setEnabled(false);
		btnRelatorio.setBorderPainted(false);
		btnRelatorio.setToolTipText("Relatórios");
		btnRelatorio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRelatorio.setIcon(new ImageIcon(Principal.class.getResource("/img/relat.png")));
		btnRelatorio.setContentAreaFilled(false);
		btnRelatorio.setBounds(79, 120, 48, 48);
		contentPane.add(btnRelatorio);
		
		JButton btnCliente = new JButton("");
		btnCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clientes clientes = new Clientes();
				clientes.setVisible(true);
			}
		});
		btnCliente.setBorderPainted(false);
		btnCliente.setToolTipText("Clientes");
		btnCliente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCliente.setIcon(new ImageIcon(Principal.class.getResource("/img/clientes75.png")));
		btnCliente.setContentAreaFilled(false);
		btnCliente.setBounds(145, 120, 48, 48);
		contentPane.add(btnCliente);
		
		JButton btnServico = new JButton("");
		btnServico.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Servico servicos = new Servico();
				servicos.setVisible(true);
				servicos.usuario = lblUsuario.getText();
			}
		});
		btnServico.setBorderPainted(false);
		btnServico.setToolTipText("Ordem de Serviço");
		btnServico.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnServico.setIcon(new ImageIcon(Principal.class.getResource("/img/engre.png")));
		btnServico.setContentAreaFilled(false);
		btnServico.setBounds(10, 120, 48, 48);
		contentPane.add(btnServico);
		
		btnTec = new JButton("");
		btnTec.setToolTipText("Tecnicos");
		btnTec.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTec.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Tecnicos tecnicos = new Tecnicos();
				tecnicos.setVisible(true);
			}
		});
		btnTec.setEnabled(false);
		btnTec.setIcon(new ImageIcon(Principal.class.getResource("/img/tec.png")));
		btnTec.setBorderPainted(false);
		btnTec.setContentAreaFilled(false);
		btnTec.setBounds(121, 45, 64, 64);
		contentPane.add(btnTec);
		
		JButton btnPecas = new JButton("");
		btnPecas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pecas pecas = new Pecas();
				pecas.setVisible(true);
			}
		});
		btnPecas.setToolTipText("Peças");
		btnPecas.setIcon(new ImageIcon(Principal.class.getResource("/img/engrenagem.png")));
		btnPecas.setContentAreaFilled(false);
		btnPecas.setBorderPainted(false);
		btnPecas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPecas.setBounds(47, 179, 64, 64);
		contentPane.add(btnPecas);
		
		JButton btnFor = new JButton("");
		btnFor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Fornecedores fornecedores = new Fornecedores();
				fornecedores.setVisible(true);
			}
		});
		btnFor.setToolTipText("Fornecedores");
		btnFor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnFor.setBorderPainted(false);
		btnFor.setContentAreaFilled(false);
		btnFor.setIcon(new ImageIcon(Principal.class.getResource("/img/user12.png")));
		btnFor.setBounds(121, 170, 64, 73);
		contentPane.add(btnFor);
	}
	
	/**
	 * Método para setar a data atual
	 */
	private void setarData() {
		Date data = new Date();
		DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
		lblData.setText(formatador.format(data));
	}
}
