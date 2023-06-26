package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.toedter.calendar.JDateChooser;

import model.DAO;
import util.Validador;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileOutputStream;

public class Servico extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textID;
	private JTextField textRelogio;
	private JTextField textSerie;
	private JButton btnBuscar;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTextField textTec;
	private JTextField textDefeito;
	private JTextField textDiag;
	private JTextField textOBS;
	private JTextField textOs;
	@SuppressWarnings("rawtypes")
	private JComboBox CboStatus;
	private JLabel lblNewLabel_12;
	private JButton btnCreate;
	private JTextField textNome;
	private JScrollPane scroll;
	@SuppressWarnings("rawtypes")
	private JList lista;
	private JTextField textValor;
	private JDateChooser textData;
	private JDateChooser textEntrega;
	private JPanel panel_2;
	
	public String usuario;
	@SuppressWarnings("rawtypes")
	private JList listaTec;
	private JScrollPane scrollTec;
	private JTextField textUser;
	private JTextField textIDTec;
	private JButton btnOS;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Servico dialog = new Servico();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Servico() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				textUser.setText(usuario);
			}
		});
		setTitle("Jokers Rolex - Ordem de serviço");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Servico.class.getResource("/img/aplle.png")));
		setBounds(100, 100, 667, 525);
		getContentPane().setLayout(null);
		
		scrollTec = new JScrollPane();
		scrollTec.setVisible(false);
		scrollTec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarTecnicos();
			}
		});
		scrollTec.setBounds(70, 258, 169, 48);
		getContentPane().add(scrollTec);
		
		listaTec = new JList();
		scrollTec.setViewportView(listaTec);
		listaTec.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarTec();
			}
		});

		JLabel lblNewLabel_1 = new JLabel("Modelo:");
		lblNewLabel_1.setBounds(10, 106, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Nº de Serie:");
		lblNewLabel_2.setBounds(222, 106, 74, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Status:");
		lblNewLabel_3.setBounds(451, 74, 46, 14);
		getContentPane().add(lblNewLabel_3);

		textRelogio = new JTextField();
		textRelogio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

			}
		});
		textRelogio.setBounds(57, 103, 155, 20);
		getContentPane().add(textRelogio);
		textRelogio.setColumns(10);
		textRelogio.setDocument(new Validador(30));

		textSerie = new JTextField();
		textSerie.setBounds(289, 103, 96, 20);
		getContentPane().add(textSerie);
		textSerie.setColumns(10);
		textSerie.setDocument(new Validador(20));

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Servico.class.getResource("/img/delete317.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(21, 365, 48, 48);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarOS();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Servico.class.getResource("/img/search.png")));
		btnBuscar.setBounds(79, 365, 48, 48);
		getContentPane().add(btnBuscar);

		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirOS();
			}
		});
		btnDelete.setIcon(new ImageIcon(Servico.class.getResource("/img/lixo.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(82, 306, 48, 48);
		getContentPane().add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarOS();

			}
		});
		btnUpdate.setIcon(new ImageIcon(Servico.class.getResource("/img/att.png")));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBounds(140, 306, 48, 48);
		getContentPane().add(btnUpdate);

		textTec = new JTextField();
		textTec.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarTecnicos();
			}
		});
		textTec.setBounds(70, 240, 169, 20);
		getContentPane().add(textTec);
		textTec.setColumns(10);
		textTec.setDocument(new Validador(20));

		textDefeito = new JTextField();
		textDefeito.setBounds(460, 103, 181, 20);
		getContentPane().add(textDefeito);
		textDefeito.setColumns(10);
		textDefeito.setDocument(new Validador(50));

		textOBS = new JTextField();
		textOBS.setBounds(294, 275, 347, 20);
		getContentPane().add(textOBS);
		textOBS.setColumns(10);
		textOBS.setDocument(new Validador(200));

		JLabel lblNewLabel_4 = new JLabel("OBS:");
		lblNewLabel_4.setBounds(249, 278, 35, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Defeito:");
		lblNewLabel_5.setBounds(406, 106, 57, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_9 = new JLabel("Data abertura:");
		lblNewLabel_9.setBounds(185, 320, 96, 14);
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Valor:");
		lblNewLabel_10.setBounds(6, 278, 35, 14);
		getContentPane().add(lblNewLabel_10);

		JLabel lblNewLabel_11 = new JLabel("Nº da OS:");
		lblNewLabel_11.setBounds(530, 11, 68, 14);
		getContentPane().add(lblNewLabel_11);

		textOs = new JTextField();
		textOs.setColumns(10);
		textOs.setBounds(599, 8, 29, 20);
		getContentPane().add(textOs);

		CboStatus = new JComboBox();
		CboStatus.setModel(new DefaultComboBoxModel(new String[] {"", "Aguardando técnico", "Aguardando peças", "Aguardando aprovação", "Orçamento recusado", "Na bancada", "Finalizada"}));
		CboStatus.setBounds(495, 70, 146, 22);
		getContentPane().add(CboStatus);

		lblNewLabel_12 = new JLabel("Data de entrega:");
		lblNewLabel_12.setBounds(417, 320, 102, 14);
		getContentPane().add(lblNewLabel_12);

		btnCreate = new JButton("");
		btnCreate.setEnabled(false);
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Servico.class.getResource("/img/plus.png")));
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarOS();
			}
		});
		btnCreate.setBounds(21, 306, 48, 48);
		getContentPane().add(btnCreate);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "cliente", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 0, 282, 101);
		getContentPane().add(panel);
		panel.setLayout(null);

		scroll = new JScrollPane();
		scroll.setBounds(113, 30, 159, 48);
		panel.add(scroll);

		lista = new JList();
		scroll.setViewportView(lista);
		lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarLista();
			}
		});
		scroll.setVisible(false);

		JLabel lblNewLabel = new JLabel("ID do cliente:");
		lblNewLabel.setBounds(10, 60, 77, 14);
		panel.add(lblNewLabel);

		textID = new JTextField();
		textID.setBounds(89, 57, 29, 20);
		panel.add(textID);
		textID.setEditable(false);
		textID.setColumns(10);

		textNome = new JTextField();
		textNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		textNome.setBounds(113, 11, 159, 20);
		panel.add(textNome);
		textNome.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("Nome do cliente");
		lblNewLabel_7.setBounds(10, 14, 93, 14);
		panel.add(lblNewLabel_7);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Diagnostico", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(6, 123, 635, 101);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		textDiag = new JTextField();
		textDiag.setBounds(10, 20, 615, 70);
		panel_1.add(textDiag);
		textDiag.setColumns(10);
		textDiag.setDocument(new Validador(200));
		
		textValor = new JTextField();
		textValor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0987654321.";
				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}
		});
		textValor.setText("0");
		textValor.setBounds(44, 275, 86, 20);
		getContentPane().add(textValor);
		textValor.setColumns(10);
		
		textData = new JDateChooser();
		textData.setEnabled(false);
		textData.setBounds(271, 314, 136, 20);
		getContentPane().add(textData);
		
		textEntrega = new JDateChooser();
		textEntrega.setBounds(508, 314, 133, 20);
		getContentPane().add(textEntrega);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Usuario", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_2.setBounds(302, 0, 146, 73);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel_6 = new JLabel("User:");
		lblNewLabel_6.setBounds(10, 24, 39, 14);
		panel_2.add(lblNewLabel_6);
		
		textUser = new JTextField();
		textUser.setEnabled(false);
		textUser.setBounds(40, 21, 86, 20);
		panel_2.add(textUser);
		textUser.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "Tecnico", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_3.setBounds(10, 224, 333, 43);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
				JLabel lblNewLabel_8 = new JLabel("Nome:");
				lblNewLabel_8.setBounds(10, 18, 49, 14);
				panel_3.add(lblNewLabel_8);
				
				JLabel lblNewLabel_13 = new JLabel("ID:");
				lblNewLabel_13.setBounds(240, 18, 34, 14);
				panel_3.add(lblNewLabel_13);
				
				textIDTec = new JTextField();
				textIDTec.setEditable(false);
				textIDTec.setBounds(264, 15, 26, 20);
				panel_3.add(textIDTec);
				textIDTec.setColumns(10);
				
				btnOS = new JButton("");
				btnOS.setBorderPainted(false);
				btnOS.setContentAreaFilled(false);
				btnOS.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						imprimirOS();
					}
				});
				btnOS.setIcon(new ImageIcon(Servico.class.getResource("/img/printer.png")));
				btnOS.setBounds(137, 365, 48, 48);
				getContentPane().add(btnOS);

	}

	private void adicionarOS() {

		if (CboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite o Status da ordem de serviço");
			CboStatus.requestFocus();

		} else if (textRelogio.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o modelo do relógio");
			textRelogio.requestFocus();

		}  else if (textDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o defeito do relógio");
			textDefeito.requestFocus();

		} else {
			
			String create = "insert into servicos (relogio,serie,idtec,defeito,diagnostico,obs,valor,statusOS,idcli,dataSaida,usuario) values (?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				
				pst.setString(1, textRelogio.getText());
				pst.setString(2, textSerie.getText());
				pst.setString(4, textDefeito.getText());
				pst.setString(5, textDiag.getText());
				pst.setString(6, textOBS.getText());
				pst.setString(7, textValor.getText());
				pst.setString(8, CboStatus.getSelectedItem().toString());
				pst.setString(9, textID.getText());
				pst.setString(11, textUser.getText());
				
				if (! CboStatus.getSelectedItem().equals("Aguardando técnico") && textTec.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "digite o nome do tecnico");
			} else {
				pst.setString(3, textIDTec.getText());
				if (textIDTec.getText().equals("")) {
						  pst.setString(3, null);
					  }
				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (textEntrega.getDate() == null) {
					  pst.setString(10, null);
					  
					} else {
						
						
					 String dataFormatada = formatador.format(textEntrega.getDate());
					pst.setString(10, dataFormatada);
					}
				pst.executeUpdate();
				
				JOptionPane.showMessageDialog(null, "Ordem de serviço adicionada com sucesso");
				btnOS.setEnabled(true);
				String os = "select max(os) from servicos";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(os);
					rs = pst.executeQuery();

					if (rs.next()) {
						textOs.setText(rs.getString(1));
						
					}
					con.close();
				} catch (Exception e) {
					System.out.println(e);
				}
			
				con.close();
			}
				
			}

			catch (java.sql.SQLIntegrityConstraintViolationException e3) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n Telefone duplicado, tente outro");
				textSerie.setText(null);
				textSerie.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
			
		}
			
			
		
		
	}

	@SuppressWarnings("unchecked")
	private void listarClientes() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		lista.setModel(modelo);
		String readlista = "select * from clientes where nome like '" + textNome.getText() + "%'" + "order by nome";

		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();

			while (rs.next()) {

				scroll.setVisible(true);
				modelo.addElement(rs.getString(2));

				if (textNome.getText().isEmpty()) {
					scroll.setVisible(false);
				}

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarLista() {

		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do cliente");
			textNome.requestFocus();
		} else {
			int linha = lista.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from clientes where nome like '" + textNome.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();

					if (rs.next()) {
						textID.setText(rs.getString(1));
						textNome.setText(rs.getString(2));
						
						btnCreate.setEnabled(true);
						scroll.setVisible(false);
						btnUpdate.setEnabled(false);
						btnDelete.setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Cliente não encontrado");
						btnCreate.setEnabled(true);
						btnBuscar.setEnabled(false);
					}
					con.close();

				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void listarTecnicos() {
		DefaultListModel<String> modelo2 = new DefaultListModel<>();
		listaTec.setModel(modelo2);
		String readlista2 = "select * from tecnicos where nome like '" + textTec.getText() + "%'" + "order by nome";
		
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista2);
			rs = pst.executeQuery();
				while (rs.next()){
				scrollTec.setVisible(true);
				modelo2.addElement(rs.getString(2));
				
				if (textTec.getText().isEmpty()) {
			scrollTec.setVisible(false);
		}
				}
				
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
private void buscarTec() {
		
		if (textTec.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do tecnico");
			textTec.requestFocus(); 
		} else {
			int linha = listaTec.getSelectedIndex();
			if (linha >= 0) {
			String read3 = "select * from tecnicos where nome like '" + textTec.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read3);
				rs = pst.executeQuery();

				if (rs.next()) {
					textIDTec.setText(rs.getString(1));
					textTec.setText(rs.getString(2)); 
					scrollTec.setVisible(false);
					

				} else {
					JOptionPane.showMessageDialog(null, "Tecnico não encontrado");
					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
			}
		}
	}

	

	private void buscarOS() {
		String numOS = JOptionPane.showInputDialog("Numero da OS");
		String read = "select * from servicos where os = ?";
		try {
			con = dao.conectar();
			pst = con.prepareStatement(read);
			pst.setString(1, numOS);
			rs = pst.executeQuery();

			if (rs.next()) {
				
				
				String setarData = rs.getString(10);
				System.out.println(setarData);
				Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
				textData.setDate(dataFormatada);
				
				
				String setarData2 = rs.getString(11);
				if (setarData2 == null) {
					textEntrega.setDate(null);
			  } else {
					
				
				System.out.println(setarData);
				Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
				textEntrega.setDate(dataFormatada2);
				}
				textOs.setText(numOS);
				textRelogio.setText(rs.getString(2));
				textSerie.setText(rs.getString(3));
				textDefeito.setText(rs.getString(4));
				textDiag.setText(rs.getString(5));
				textIDTec.setText(rs.getString(6));
				CboStatus.setSelectedItem(rs.getString(7));
				textValor.setText(rs.getString(8));
				textOBS.setText(rs.getString(9));
				textID.setText(rs.getString(12));
				textUser.setText(rs.getString(13));

				btnCreate.setEnabled(false);
				btnBuscar.setEnabled(false);
				btnUpdate.setEnabled(true);
				btnDelete.setEnabled(true);

			} else {
				JOptionPane.showMessageDialog(null, "Ordem de serviço não encontrada");

				btnCreate.setEnabled(true);
				btnBuscar.setEnabled(false);
			}
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private void editarOS() {
		if (textRelogio.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o modelo do relógio");
			textRelogio.requestFocus();

		}  else if (textDefeito.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o defeito do relógio");
			textDefeito.requestFocus();

		} else if (CboStatus.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite o Status da ordem de serviço");
			CboStatus.requestFocus();

		} else {
			String update = "update servicos set relogio = ?, serie = ?, idtec = ?, defeito = ?, diagnostico = ?, obs = ?, valor = ?, statusOS = ?, dataSaida = ? where os = ?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, textRelogio.getText());
				pst.setString(2, textSerie.getText());
				pst.setString(4, textDefeito.getText());
				pst.setString(5, textDiag.getText());
				pst.setString(6, textOBS.getText());
				pst.setString(7, textValor.getText());
				pst.setString(8, CboStatus.getSelectedItem().toString());
				
				if (! CboStatus.getSelectedItem().equals("Aguardando técnico") && textIDTec.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "digite o nome do tecnico");
					
				} else {
				
				
				pst.setString(3, textIDTec.getText());
				if (textIDTec.getText().equals("")) {
						  pst.setString(3, null);
					  }
				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (textEntrega.getDate() == null) {
					  pst.setString(9, null);
					} else {
					 String dataFormatada = formatador.format(textEntrega.getDate());
					pst.setString(9, dataFormatada);
					}
				
				pst.setString(10, textOs.getText());
				
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Ordem de serviço editada com sucesso");
				con.close();
				limparCampos();
				
				}
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n telefone duplicado, tente outro");
				textSerie.setText(null);
				textSerie.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}

		}
	}
	
	private void imprimirOS(){
		if (textOs.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a OS que deseja imprimir");
			textOs.requestFocus();
		}
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, new FileOutputStream("Os.pdf"));
			document.open();
			String readOS = "select servicos.os,servicos.relogio,servicos.defeito,servicos.statusOS,servicos.diagnostico,servicos.usuario,servicos.serie,servicos.valor,tecnicos.nome,date_format(servicos.dataOS,'%d/%m/%Y'),date_format(servicos.dataSaida,'%d/%m/%Y'), clientes.nome, clientes.fone, clientes.email, clientes.idcli, clientes.cep, clientes.endereco, clientes.numero, clientes.comp, clientes.bairro, clientes.cidade, clientes.uf from servicos inner join tecnicos on servicos.idtec = tecnicos.idtec inner join clientes on servicos.idcli = clientes.idcli where OS = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readOS);
				pst.setString(1, textOs.getText());
				rs = pst.executeQuery();
				
				if (rs.next()) {
					Paragraph ordem = new Paragraph ("Informações da OS");
					ordem.setAlignment(Element.ALIGN_CENTER);
					document.add(ordem);
					
					Paragraph linha = new Paragraph (" ");
					document.add(linha);
					
					Paragraph data = new Paragraph ("Data de entrada: " + rs.getString(10));
					data.setAlignment(Element.ALIGN_LEFT);
					document.add(data);
					
					Paragraph os = new Paragraph ("Ordem de serviço: " + rs.getString(1));
					os.setAlignment(Element.ALIGN_LEFT);
					document.add(os);
					
					Paragraph status = new Paragraph ("Status:  " + rs.getString(4));
					status.setAlignment(Element.ALIGN_LEFT);
					document.add(status);
					
					Paragraph usuario = new Paragraph ("Usuario:  " + rs.getString(6));
					usuario.setAlignment(Element.ALIGN_LEFT);
					document.add(usuario);
					
					Paragraph relogio = new Paragraph ("Modelo:  " + rs.getString(2));
					relogio.setAlignment(Element.ALIGN_LEFT);
					document.add(relogio);
					
					Paragraph serie = new Paragraph ("N° de série:  " + rs.getString(7));
					serie.setAlignment(Element.ALIGN_LEFT);
					document.add(serie);
					
					Paragraph defeito = new Paragraph ("Defeito:  " + rs.getString(3));
					defeito.setAlignment(Element.ALIGN_LEFT);
					document.add(defeito);
					
					Paragraph diagnostico = new Paragraph ("Diagnostico:  " + rs.getString(5));
					diagnostico.setAlignment(Element.ALIGN_LEFT);
					document.add(diagnostico);
					
					Paragraph valor = new Paragraph ("Valor:  " + rs.getString(8));
					valor.setAlignment(Element.ALIGN_LEFT);
					document.add(valor);
					
					Paragraph tecnico = new Paragraph ("Tecnico: " + rs.getString(9));
					tecnico.setAlignment(Element.ALIGN_LEFT);
					document.add(tecnico);
					
					Paragraph dataSaida = new Paragraph ("Data de saida: " + rs.getString(11));
					dataSaida.setAlignment(Element.ALIGN_LEFT);
					document.add(dataSaida);
					
					document.add(linha);
					
					Paragraph cliente = new Paragraph ("Informações do cliente");
					cliente.setAlignment(Element.ALIGN_CENTER);
					document.add(cliente);
					
					document.add(linha);
					
					Paragraph idcli = new Paragraph ("ID do cliente: " + rs.getString(15));
					idcli.setAlignment(Element.ALIGN_LEFT);
					document.add(idcli);
					
					Paragraph nome = new Paragraph ("Nome do cliente: " + rs.getString(12));
					nome.setAlignment(Element.ALIGN_LEFT);
					document.add(nome);
					
					Paragraph fone = new Paragraph ("Telefone: " + rs.getString(13));
					fone.setAlignment(Element.ALIGN_LEFT);
					document.add(fone);
					
					Paragraph email = new Paragraph ("E-mail: " + rs.getString(14));
					email.setAlignment(Element.ALIGN_LEFT);
					document.add(email);
					
					Paragraph cep = new Paragraph ("CEP: " + rs.getString(16));
					cep.setAlignment(Element.ALIGN_LEFT);
					document.add(cep);
					
					Paragraph rua = new Paragraph ("Endereço: " + rs.getString(17));
					rua.setAlignment(Element.ALIGN_LEFT);
					document.add(rua);
					
					Paragraph num = new Paragraph ("Número: " + rs.getString(18));
					num.setAlignment(Element.ALIGN_LEFT);
					document.add(num);
					
					Paragraph comp = new Paragraph ("Complemento: " + rs.getString(19));
					comp.setAlignment(Element.ALIGN_LEFT);
					document.add(comp);
					
					Paragraph bairro = new Paragraph ("Bairro: " + rs.getString(20));
					bairro.setAlignment(Element.ALIGN_LEFT);
					document.add(bairro);
					
					Paragraph cidade = new Paragraph ("Cidade: " + rs.getString(21));
					cidade.setAlignment(Element.ALIGN_LEFT);
					document.add(cidade);
					
					Paragraph uf = new Paragraph ("Estado: " + rs.getString(22));
					uf.setAlignment(Element.ALIGN_LEFT);
					document.add(uf);
					
					Image imagem = Image.getInstance(Servico.class.getResource("/img/joker3.jpg"));
					imagem.scaleToFit(250,386);
					imagem.setAbsolutePosition(350, 550);
					document.add(imagem);
				}
				con.close();
				
				} catch (Exception e) {
				 System.out.println(e);
			}
		}catch (Exception e) {
		 System.out.println(e);
		}
		document.close();
		try {
			Desktop.getDesktop().open(new File("Os.pdf"));
		} catch (Exception e) {
			System.out.println(e);
		}

		
	}

	private void excluirOS() {
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão desta ordem de serviço?", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from servicos where os = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Ordem de serviço excluida");
				limparCampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {
		textID.setText(null);
		textRelogio.setText(null);
		textSerie.setText(null);
		textTec.setText(null);
		textDefeito.setText(null);
		textDiag.setText(null);
		textValor.setText(null);
		CboStatus.setSelectedItem(null);
		textOBS.setText(null);
		textNome.setText(null);
		textUser.setText(null);
		textData.setDate(null);
		textEntrega.setDate(null);
		textOs.setText(null);
		textIDTec.setText(null);

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
		

	}
}
