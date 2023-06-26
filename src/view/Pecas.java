package view;

import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import util.Validador;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("unused")
public class Pecas extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textID;
	private JTextField textFor;
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTextField textDesc;
	private JTextField textQuant;
	private JTextField textMin;
	private JTextField textValor;
	private JTextField textLugar;
	@SuppressWarnings("rawtypes")
	private JList lista;
	private JScrollPane scroll;
	private JTextField textCodigo;
	private JLabel lblNewLabel_11;
	private JTextField textIDFor;
	private JTextField textPeca;
	private JLabel lblNewLabel_12;
	private JDateChooser textVali;
	private JDateChooser textData;
	private JScrollPane scrollPane;
	@SuppressWarnings("rawtypes")
	private JList list;
	@SuppressWarnings("rawtypes")
	private JComboBox cboMed;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Pecas dialog = new Pecas();
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Pecas() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scroll.setVisible(false);
				scrollPane.setVisible(false);
			}
		});
		setTitle("Jokers Rolex - Peças");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Pecas.class.getResource("/img/aplle.png")));
		setBounds(100, 100, 571, 446);
		getContentPane().setLayout(null);

		scroll = new JScrollPane();
		scroll.setVisible(false);
		
		scrollPane = new JScrollPane();
		scrollPane.setVisible(false);
		scrollPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarPecas();
			}
		});
		scrollPane.setBounds(106, 50, 114, 67);
		getContentPane().add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarListaPeca();
			}
		});
		scroll.setBounds(106, 101, 169, 55);
		getContentPane().add(scroll);
		
				lista = new JList();
				scroll.setViewportView(lista);
				lista.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarLista();
					}
				});

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(73, 11, 23, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Código de barras:");
		lblNewLabel_1.setBounds(6, 59, 106, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fornecedor:");
		lblNewLabel_2.setBounds(28, 87, 68, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Descrição:");
		lblNewLabel_3.setBounds(38, 116, 68, 14);
		getContentPane().add(lblNewLabel_3);

		textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(106, 8, 75, 20);
		getContentPane().add(textID);
		textID.setColumns(10);

		textFor = new JTextField();
		textFor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		textFor.setBounds(106, 84, 168, 20);
		getContentPane().add(textFor);
		textFor.setColumns(10);
		textFor.setDocument(new Validador(30));

		btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarPeca();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Pecas.class.getResource("/img/ass347.png")));
		btnCreate.setBounds(28, 328, 48, 48);
		getContentPane().add(btnCreate);

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Pecas.class.getResource("/img/delete317.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(202, 328, 48, 48);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarPeca();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Pecas.class.getResource("/img/search.png")));
		btnBuscar.setBounds(230, 3, 48, 48);

		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirCliente();
			}
		});
		btnDelete.setIcon(new ImageIcon(Pecas.class.getResource("/img/lixo.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(86, 328, 48, 48);
		getContentPane().add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarPeca();

			}
		});
		btnUpdate.setIcon(new ImageIcon(Pecas.class.getResource("/img/att.png")));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBounds(144, 328, 48, 48);
		getContentPane().add(btnUpdate);

		textDesc = new JTextField();
		textDesc.setBounds(105, 113, 207, 20);
		getContentPane().add(textDesc);
		textDesc.setColumns(10);
		textDesc.setDocument(new Validador(200));

		textQuant = new JTextField();
		textQuant.setBounds(106, 145, 86, 20);
		getContentPane().add(textQuant);
		textQuant.setColumns(10);
		textQuant.setDocument(new Validador(8));

		textMin = new JTextField();
		textMin.setBounds(106, 176, 86, 20);
		getContentPane().add(textMin);
		textMin.setColumns(10);
		textMin.setDocument(new Validador(8));

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
		textValor.setBounds(106, 207, 57, 20);
		getContentPane().add(textValor);
		textValor.setColumns(10);
		textValor.setDocument(new Validador(10));

		textLugar = new JTextField();
		textLugar.setBounds(106, 266, 133, 20);
		getContentPane().add(textLugar);
		textLugar.setColumns(10);
		textLugar.setDocument(new Validador(20));

		JLabel lblNewLabel_4 = new JLabel("Quantidade:");
		lblNewLabel_4.setBounds(28, 148, 68, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Estoque minimo:");
		lblNewLabel_5.setBounds(6, 179, 100, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Valor:");
		lblNewLabel_6.setBounds(64, 210, 48, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Medida:");
		lblNewLabel_7.setBounds(55, 241, 57, 14);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Validade:");
		lblNewLabel_8.setBounds(38, 300, 58, 14);
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Lugar:");
		lblNewLabel_9.setBounds(64, 269, 46, 14);
		getContentPane().add(lblNewLabel_9);
				
				textCodigo = new JTextField();
				textCodigo.addKeyListener(new KeyAdapter() {
					@Override
					public void keyPressed(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER) {
							buscarBarcode();
						}
							
					}
				});
				textCodigo.setBounds(106, 56, 114, 20);
				getContentPane().add(textCodigo);
				textCodigo.setColumns(10);
				textCodigo.setDocument(new Validador(50));
						
						JLabel lblNewLabel_10 = new JLabel("Data de entrada:");
						lblNewLabel_10.setBounds(323, 62, 98, 14);
						getContentPane().add(lblNewLabel_10);
						
						lblNewLabel_11 = new JLabel("ID:");
						lblNewLabel_11.setBounds(284, 87, 35, 14);
						getContentPane().add(lblNewLabel_11);
						
						textIDFor = new JTextField();
						textIDFor.setEditable(false);
						textIDFor.setBounds(306, 84, 68, 20);
						getContentPane().add(textIDFor);
						textIDFor.setColumns(10);
						
						textPeca = new JTextField();
						textPeca.addKeyListener(new KeyAdapter() {
							@Override
							public void keyReleased(KeyEvent e) {
								listarPecas();
							}
						});
						textPeca.setBounds(106, 31, 114, 20);
						getContentPane().add(textPeca);
						textPeca.setColumns(10);
						textPeca.setDocument(new Validador(50));
						
						lblNewLabel_12 = new JLabel("Peça:");
						lblNewLabel_12.setBounds(66, 34, 46, 14);
						getContentPane().add(lblNewLabel_12);
						
						textVali = new JDateChooser();
						textVali.setBounds(106, 297, 133, 20);
						getContentPane().add(textVali);
						
						textData = new JDateChooser();
						textData.setEnabled(false);
						textData.setBounds(419, 59, 136, 20);
						getContentPane().add(textData);
						
						cboMed = new JComboBox();
						cboMed.setModel(new DefaultComboBoxModel(new String[] {"", "CX", "UNI", "PÇ"}));
						cboMed.setBounds(106, 237, 57, 22);
						getContentPane().add(cboMed);

	}

	private void adicionarPeca() {
		if (textIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
			textFor.requestFocus();
		} else if (textPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o nome da peça");
			textPeca.requestFocus();
		} else if (textCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o código de barras da peça");
			textCodigo.requestFocus();
		} else if (textQuant.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite a quantidade de peças");
			textQuant.requestFocus();
		} else if (textMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o estoque minimo desta peça");
			textMin.requestFocus();
		} else if (textValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o valor da peça");
			textValor.requestFocus();
		} else if (cboMed.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite a unidade de medida da peça");
			cboMed.requestFocus();
		

		} else {
			String create = "insert into pecas(peca,idfor,barra,descri,estoque,estoquemin,valor,medida,lugar,validade) values (?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);

				pst.setString(1, textPeca.getText());
				pst.setString(2, textIDFor.getText());
				pst.setString(3, textCodigo.getText());
				pst.setString(4, textDesc.getText());
				pst.setString(5, textQuant.getText());
				pst.setString(6, textMin.getText());       
				pst.setString(7, textValor.getText());
				pst.setString(8, cboMed.getSelectedItem().toString());
				pst.setString(9, textLugar.getText());
				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (textVali.getDate() == null) {
					  pst.setString(10, null);
					  
					} else {
					 String dataFormatada = formatador.format(textVali.getDate());
					pst.setString(10, dataFormatada);
					}
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Peça adicionada com sucesso");

				limparCampos();

				con.close();
			}
			 catch (Exception e2) {
				System.out.println(e2);
			}

		}
	} 

	private void buscarPeca() {
		if (textPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome da peça");
			textPeca.requestFocus();
		} else {

			String read = "select * from pecas where peca = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, textPeca.getText());
				rs = pst.executeQuery();

				if (rs.next()) {
					String setarData = rs.getString(3);
					System.out.println(setarData);
					Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
					textData.setDate(dataFormatada);
					
					String setarData2 = rs.getString(12);
					if (setarData2 == null) {
						textVali.setDate(null);
				  } else {
						
					
					System.out.println(setarData2);
					Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
					textVali.setDate(dataFormatada2);
					}
					textID.setText(rs.getString(2));
					textCodigo.setText(rs.getString(5));
					textDesc.setText(rs.getString(6));
					textQuant.setText(rs.getString(7));
					textMin.setText(rs.getString(8));
					textValor.setText(rs.getString(9));
					cboMed.setSelectedItem(rs.getString(10));
					textLugar.setText(rs.getString(11));
					textIDFor.setText(rs.getString(4));
					
					textFor.setEditable(false);
					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Peça não encontrado");
					scroll.setVisible(false);
					btnCreate.setEnabled(true);
					btnBuscar.setEnabled(false);
				}
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void listarFornecedores() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		lista.setModel(modelo);
		String readlista = "select * from fornecedores where nome like '" + textFor.getText() + "%'" + "order by nome";

		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();

				while(rs.next()) {
				scroll.setVisible(true);
				modelo.addElement(rs.getString(4));

				if (textFor.getText().isEmpty()) {
					scroll.setVisible(false);
				}

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarLista() {

		if (textFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			textFor.requestFocus();
		} else {
			int linha = lista.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from fornecedores where nome like '" + textFor.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";

				try {
					con = dao.conectar();
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();

					if (rs.next()) {
						textIDFor.setText(rs.getString(1));
						textFor.setText(rs.getString(4));

						scroll.setVisible(false);
						
						

					} else {
						JOptionPane.showMessageDialog(null, "Usuario não encontrado");

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
	private void listarPecas() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		list.setModel(modelo);
		String readlista = "select * from pecas where peca like '" + textPeca.getText() + "%'" + "order by peca";

		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();

				while(rs.next()) {
				scrollPane.setVisible(true);
				modelo.addElement(rs.getString(1));
				
				if (textPeca.getText().isEmpty()) {
					scrollPane.setVisible(false);
				}

			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarListaPeca() {

		if (textPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome da peça");
			textPeca.requestFocus();
		} else {
			int linha = list.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from pecas where peca like '" + textPeca.getText() + "%'"
						+ "order by peca limit " + linha + " , 1";

				try {
					con = dao.conectar();
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();
					if (rs.next()) {
						String setarData = rs.getString(3);
						System.out.println(setarData);
						Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
						textData.setDate(dataFormatada);
						
						String setarData2 = rs.getString(12);
						if (setarData2 == null) {
							textVali.setDate(null);
					  } else {
							
						
						System.out.println(setarData2);
						Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
						textVali.setDate(dataFormatada2);
						}
						textPeca.setText(rs.getString(1));
						textID.setText(rs.getString(2));
						textCodigo.setText(rs.getString(5));
						textDesc.setText(rs.getString(6));
						textQuant.setText(rs.getString(7));
						textMin.setText(rs.getString(8));
						textValor.setText(rs.getString(9));
						cboMed.setSelectedItem(rs.getString(10));
						textLugar.setText(rs.getString(11));
						textIDFor.setText(rs.getString(4));
						
						textFor.setEditable(false);
						btnCreate.setEnabled(false);
						btnBuscar.setEnabled(false);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						scrollPane.setVisible(false);
						
						

					} else {
						JOptionPane.showMessageDialog(null, "Peça não encontrada");

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
	private void editarPeca() {
		if (textIDFor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
			textFor.requestFocus();
		} else if (textPeca.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o nome da peça");
			textPeca.requestFocus();
		} else if (textCodigo.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o código de barras da peça");
			textCodigo.requestFocus();
		} else if (textQuant.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite a quantidade de peças");
			textQuant.requestFocus();
		} else if (textMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o estoque minimo desta peça");
			textMin.requestFocus();
		} else if (textValor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o valor da peça");
			textValor.requestFocus();
		} else if (cboMed.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite a unidade de medida da peça");
			cboMed.requestFocus();
		

		} else {
			String update = "update pecas set peca = ?, barra = ?, descri = ?, estoque = ?, estoquemin = ?, valor = ?, medida = ?, lugar = ?, validade = ? where idpecas = ?";
			try {
				con = dao.conectar();
				
				pst = con.prepareStatement(update);
				pst.setString(1, textPeca.getText());
				pst.setString(2, textCodigo.getText());
				pst.setString(3, textDesc.getText());
				pst.setString(4, textQuant.getText());
				pst.setString(5, textMin.getText());
				pst.setString(6, textValor.getText());
				pst.setString(7, cboMed.getSelectedItem().toString());
				pst.setString(8, textLugar.getText());
				pst.setString(10, textID.getText());
				
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				if (textVali.getDate() == null) {
					  pst.setString(9, null);
					} else {
					 String dataFormatada = formatador.format(textVali.getDate());
					pst.setString(9, dataFormatada);
					}

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Peça editada com sucesso");
				con.close();
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n Código de barras duplicado, produto já cadastrado");
				textCodigo.setText(null);
				textCodigo.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}

		}
	}

			private void buscarBarcode() {
				System.out.println("teste do buscar codigo");
				
				if (textCodigo.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Digite o codigo da peça");
					textCodigo.requestFocus();
				} else {

					String read = "select * from pecas where barra = ?";
					try {
						con = dao.conectar();
						pst = con.prepareStatement(read);
						pst.setString(1, textCodigo.getText());
						rs = pst.executeQuery();

						if (rs.next()) {
							String setarData = rs.getString(3);
							System.out.println(setarData);
							Date dataFormatada = new SimpleDateFormat("yyyy-MM-dd").parse(setarData);
							textData.setDate(dataFormatada);
							
							String setarData2 = rs.getString(12);
							if (setarData2 == null) {
								textVali.setDate(null);
						  } else {
								
							
							System.out.println(setarData2);
							Date dataFormatada2 = new SimpleDateFormat("yyyy-MM-dd").parse(setarData2);
							textVali.setDate(dataFormatada2);
							}
							textPeca.setText(rs.getString(1));
							textID.setText(rs.getString(2));
							textDesc.setText(rs.getString(6));
							textQuant.setText(rs.getString(7));
							textMin.setText(rs.getString(8));
							textValor.setText(rs.getString(9));
							cboMed.setSelectedItem(rs.getString(10));
							textLugar.setText(rs.getString(11));
							textIDFor.setText(rs.getString(4));
							
							textFor.setEditable(false);
							btnCreate.setEnabled(false);
							btnBuscar.setEnabled(false);
							btnUpdate.setEnabled(true);
							btnDelete.setEnabled(true);

						} else {
							JOptionPane.showMessageDialog(null, "Peça não encontrado");
							scroll.setVisible(false);
							btnCreate.setEnabled(true);
							btnBuscar.setEnabled(false);
						}
						con.close();

					} catch (Exception e) {
						System.out.println(e);
					}
				}
			}
	/**
	 * Método excluir cliente
	 */
	private void excluirCliente() {
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão desta peça?", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from pecas where idpecas = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Peça excluida");
				limparCampos();
				con.close();

			} 
	         catch (Exception e2) {
				System.out.println(e2);
				
			}
			

		}

	}

	private void limparCampos() {
		textID.setText(null);
		textFor.setText(null);
		textVali.setDate(null);
		textDesc.setText(null);
		textQuant.setText(null);
		textMin.setText(null);
		textValor.setText(null);
		textData.setDate(null);
		textLugar.setText(null);
		cboMed.setSelectedItem(null);
		textPeca.setText(null);
		textCodigo.setText(null);
		textIDFor.setText(null);
		textFor.setEditable(true);
		
		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);

	}
}
