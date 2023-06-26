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

public class Fornecedores extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textID;
	private JTextField textNome;
	private JTextField textFone;
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JTextField textEmail;
	private JTextField textCEP;
	private JTextField textEndereco;
	private JTextField textNum;
	private JTextField textComp;
	private JTextField textBairro;
	private JTextField textCidade;
	@SuppressWarnings("rawtypes")
	private JComboBox cboUF;
	@SuppressWarnings("rawtypes")
	private JList lista;
	private JScrollPane scroll;
	private JTextField textCnpj;
	private JTextField textSite;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Fornecedores dialog = new Fornecedores();
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
	public Fornecedores() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scroll.setVisible(false);
			}
		});
		setTitle("Jokers Rolex - Fornecedores");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Fornecedores.class.getResource("/img/aplle.png")));
		setBounds(100, 100, 571, 466);
		getContentPane().setLayout(null);

		scroll = new JScrollPane();
		scroll.setVisible(false);
		scroll.setBounds(73, 76, 169, 55);
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
		lblNewLabel.setBounds(40, 34, 23, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setBounds(28, 59, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Fone");
		lblNewLabel_2.setBounds(28, 145, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Email:");
		lblNewLabel_3.setBounds(28, 170, 46, 14);
		getContentPane().add(lblNewLabel_3);

		textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(73, 31, 86, 20);
		getContentPane().add(textID);
		textID.setColumns(10);

		textNome = new JTextField();
		textNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedores();
			}
		});
		textNome.setBounds(74, 56, 168, 20);
		getContentPane().add(textNome);
		textNome.setColumns(10);
		textNome.setDocument(new Validador(30));

		textFone = new JTextField();
		textFone.setBounds(76, 142, 133, 20);
		getContentPane().add(textFone);
		textFone.setColumns(10);
		textFone.setDocument(new Validador(11));

		btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarFornecedor();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/ass347.png")));
		btnCreate.setBounds(28, 319, 48, 48);
		getContentPane().add(btnCreate);

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/delete317.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(252, 82, 48, 48);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarFornecedor();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/search.png")));
		btnBuscar.setBounds(252, 34, 48, 48);
		getContentPane().add(btnBuscar);
		getRootPane().setDefaultButton(btnBuscar);

		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirFornecedor();
			}
		});
		btnDelete.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/lixo.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(86, 319, 48, 48);
		getContentPane().add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarFornecedor();

			}
		});
		btnUpdate.setIcon(new ImageIcon(Fornecedores.class.getResource("/img/att.png")));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBounds(144, 319, 48, 48);
		getContentPane().add(btnUpdate);

		textEmail = new JTextField();
		textEmail.setBounds(73, 167, 169, 20);
		getContentPane().add(textEmail);
		textEmail.setColumns(10);
		textEmail.setDocument(new Validador(20));

		textCEP = new JTextField();
		textCEP.setBounds(73, 195, 147, 20);
		getContentPane().add(textCEP);
		textCEP.setColumns(10);
		textCEP.setDocument(new Validador(8));

		textEndereco = new JTextField();
		textEndereco.setBounds(73, 226, 147, 20);
		getContentPane().add(textEndereco);
		textEndereco.setColumns(10);
		textEndereco.setDocument(new Validador(50));

		textNum = new JTextField();
		textNum.setBounds(73, 257, 57, 20);
		getContentPane().add(textNum);
		textNum.setColumns(10);
		textNum.setDocument(new Validador(5));

		textComp = new JTextField();
		textComp.setBounds(95, 288, 147, 20);
		getContentPane().add(textComp);
		textComp.setColumns(10);
		textComp.setDocument(new Validador(30));

		JButton btnCEP = new JButton("Buscar CEP");
		btnCEP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnCEP.setBounds(244, 194, 102, 23);
		getContentPane().add(btnCEP);

		textBairro = new JTextField();
		textBairro.setBounds(300, 226, 86, 20);
		getContentPane().add(textBairro);
		textBairro.setColumns(10);
		textBairro.setDocument(new Validador(20));

		textCidade = new JTextField();
		textCidade.setBounds(300, 257, 86, 20);
		getContentPane().add(textCidade);
		textCidade.setColumns(10);
		textCidade.setDocument(new Validador(20));

		cboUF = new JComboBox();
		cboUF.setBounds(300, 287, 46, 22);
		getContentPane().add(cboUF);
		cboUF.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

		JLabel lblNewLabel_4 = new JLabel("CEP:");
		lblNewLabel_4.setBounds(28, 198, 35, 14);
		getContentPane().add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Endereço:");
		lblNewLabel_5.setBounds(6, 229, 57, 14);
		getContentPane().add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Número:");
		lblNewLabel_6.setBounds(10, 260, 58, 14);
		getContentPane().add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Complemento:");
		lblNewLabel_7.setBounds(10, 294, 86, 14);
		getContentPane().add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Bairro:");
		lblNewLabel_8.setBounds(254, 229, 46, 14);
		getContentPane().add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Cidade:");
		lblNewLabel_9.setBounds(252, 260, 46, 14);
		getContentPane().add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("UF:");
		lblNewLabel_10.setBounds(265, 291, 35, 14);
		getContentPane().add(lblNewLabel_10);
				
				JLabel lblNewLabel_11 = new JLabel("CNPJ:");
				lblNewLabel_11.setBounds(28, 84, 46, 14);
				getContentPane().add(lblNewLabel_11);
				
				textCnpj = new JTextField();
				textCnpj.setBounds(73, 82, 169, 20);
				getContentPane().add(textCnpj);
				textCnpj.setColumns(10);
				
				JLabel lblNewLabel_12 = new JLabel("Site:");
				lblNewLabel_12.setBounds(28, 109, 46, 14);
				getContentPane().add(lblNewLabel_12);
				
				textSite = new JTextField();
				textSite.setBounds(73, 106, 169, 20);
				getContentPane().add(textSite);
				textSite.setColumns(10);

	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String cep = textCEP.getText();
		String resultado = null;
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					textCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					textBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("0")) {
						JOptionPane.showMessageDialog(null, "CEP inválido");
					}

				}

			}

			textEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (

		Exception e) {
			System.out.println(e);
		}
	}

	private void adicionarFornecedor() {

		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
			textNome.requestFocus();
			
		} else if (textCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o CNPJ do fornecedor");
			textCnpj.requestFocus();

		} else if (textFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o telefone do fornecedor");
			textFone.requestFocus();
		} else if (textEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o email do fornecedor");
			textEmail.requestFocus();
		} else if (textCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o CEP do fornecedor");
			textCEP.requestFocus();
		} else if (textEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Endereço do fornecedor");
			textEndereco.requestFocus();
		} else if (textNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Numero do endereço do fornecedor");
			textNum.requestFocus();
		} else if (textBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Bairro do endereço do fornecedor");
			textBairro.requestFocus();
		} else if (textCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite a Cidade do endereço do fornecedor");
			textCidade.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite o Estado do endereço do fornecedor");
			cboUF.requestFocus();

		} else {
			String create = "insert into fornecedores(nome,cnpj,site,fone,email,cep,endereco,numero,comp,bairro,cidade,uf) values (?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				con = dao.conectar();

				pst = con.prepareStatement(create);

				pst.setString(1, textNome.getText());
				pst.setString(2, textCnpj.getText());
				pst.setString(3, textSite.getText());
				pst.setString(4, textFone.getText());
				pst.setString(5, textEmail.getText());
				pst.setString(6, textCEP.getText());
				pst.setString(7, textEndereco.getText());
				pst.setString(8, textNum.getText());
				pst.setString(9, textComp.getText());
				pst.setString(10, textBairro.getText());
				pst.setString(11, textCidade.getText());
				pst.setString(12, cboUF.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "fornecedor adicionado com sucesso");

				limparCampos();

				con.close();
			}

			catch (java.sql.SQLIntegrityConstraintViolationException e3) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n CNPJ duplicado, tente outro");
				textCnpj.setText(null);
				textCnpj.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}


		}
	}

	private void buscarFornecedor() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
			textNome.requestFocus();
		} else {

			String read = "select * from fornecedores where nome = ?";
			try {
				
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, textNome.getText());
				rs = pst.executeQuery();

				if (rs.next()) {
			
					textID.setText(rs.getString(1)); // 
					textCnpj.setText(rs.getString(2));
					textSite.setText(rs.getString(3)); // 
					
					textFone.setText(rs.getString(5)); // 
					textEmail.setText(rs.getString(6));
					textCEP.setText(rs.getString(7));
					textEndereco.setText(rs.getString(8));
					textNum.setText(rs.getString(9));
					textComp.setText(rs.getString(10));
					textBairro.setText(rs.getString(11));
					textCidade.setText(rs.getString(12));
					cboUF.setSelectedItem(rs.getString(13));
					
					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);

				} else {
					JOptionPane.showMessageDialog(null, "Fornecedor não encontrado");
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
		String readlista = "select * from fornecedores where nome like '" + textNome.getText() + "%'" + "order by nome";

		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();

			
				while(rs.next()) {
					
				
				scroll.setVisible(true);
				modelo.addElement(rs.getString(4));

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
			JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
			textNome.requestFocus(); 
		} else {
			int linha = lista.getSelectedIndex();
			if (linha >= 0) {
				String read2 = "select * from fornecedores where nome like '" + textNome.getText() + "%'"
						+ "order by nome limit " + linha + " , 1";

				try {
					
					con = dao.conectar();
					pst = con.prepareStatement(read2);
					rs = pst.executeQuery();

					if (rs.next()) {
						textID.setText(rs.getString(1)); 
						textCnpj.setText(rs.getString(2));
						textSite.setText(rs.getString(3));
						textNome.setText(rs.getString(4));
						textFone.setText(rs.getString(5)); 
						textEmail.setText(rs.getString(6)); 
						textCEP.setText(rs.getString(7));
						textEndereco.setText(rs.getString(8));
						textNum.setText(rs.getString(9));
						textComp.setText(rs.getString(10));
						textBairro.setText(rs.getString(11));
						textCidade.setText(rs.getString(12));
						cboUF.setSelectedItem(rs.getString(13));

						scroll.setVisible(false);
						btnCreate.setEnabled(false);
						btnBuscar.setEnabled(false);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);

					} else {
						JOptionPane.showMessageDialog(null, "Fornecedor não encontrado");

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
	private void editarFornecedor() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do fornecedor");
			textNome.requestFocus();
		} else if (textCnpj.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o CNPJ do fornecedor");
			textCnpj.requestFocus();
		} else if (textFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o telefone do fornecedor");
			textFone.requestFocus();
		} else if (textEmail.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o email do fornecedor");
			textEmail.requestFocus();
		} else if (textCEP.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o CEP do fornecedor");
			textCEP.requestFocus();
		} else if (textEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Endereço do fornecedor");
			textEndereco.requestFocus();
		} else if (textNum.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Numero do endereço do fornecedor");
			textNum.requestFocus();
		} else if (textBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o Bairro do endereço do fornecedor");
			textBairro.requestFocus();
		} else if (textCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite a Cidade do endereço do fornecedor");
			textCidade.requestFocus();
		} else if (cboUF.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "digite o Estado do endereço do fornecedor");
			cboUF.requestFocus();

		} else {
			String update = "update fornecedores set nome = ?, cnpj = ?, site = ?, fone = ?, email = ?, cep = ?, endereco = ?, numero = ?, comp = ?, bairro = ?, cidade = ?, uf = ? where idfor = ?";

			try {
				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textCnpj.getText());
				pst.setString(3, textSite.getText());
				pst.setString(4, textFone.getText());
				pst.setString(5, textEmail.getText());
				pst.setString(6, textCEP.getText());
				pst.setString(7, textEndereco.getText());
				pst.setString(8, textNum.getText());
				pst.setString(9, textComp.getText());
				pst.setString(10, textBairro.getText());
				pst.setString(11, textCidade.getText());
				pst.setString(12, cboUF.getSelectedItem().toString());
				pst.setString(13, textID.getText());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Fornecedor editado com sucesso");
				con.close();
				
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n CNPJ duplicado, tente outro");
				textCnpj.setText(null);
				textCnpj.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}

		}
	}

	
	private void excluirFornecedor() {
		

		
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste fornecedor?", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			
			String delete = "delete from fornecedores where idfor = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "fornecedor excluido");
				limparCampos();
				con.close();

			} 
			catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n Fornecedor com peças");
				textFone.setText(null);
				textFone.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
			

		}

	}

	private void limparCampos() {
		textID.setText(null);
		textNome.setText(null);
		textFone.setText(null);
		textEmail.setText(null);
		textCEP.setText(null);
		textEndereco.setText(null);
		textNum.setText(null);
		textBairro.setText(null);
		textCidade.setText(null);
		cboUF.setSelectedItem(null);
		textComp.setText(null);
		textCnpj.setText(null);
		textSite.setText(null);

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);

	}
}
