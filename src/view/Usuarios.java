package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import util.Validador;

import javax.swing.JCheckBox;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Usuarios extends JDialog {

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField passwordSenha;
	private JTextField textID;
	private JTextField textNome;
	private JTextField textLogin;
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	@SuppressWarnings("rawtypes")
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	@SuppressWarnings("rawtypes")
	private JList lista;
	private JScrollPane scroll;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Usuarios dialog = new Usuarios();
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
	public Usuarios() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scroll.setVisible(false);
			}
		});
		setTitle("Jokers Rolex - usuarios");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/aplle.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		scroll = new JScrollPane();
		scroll.setVisible(false);
		scroll.setBounds(73, 76, 147, 75);
		getContentPane().add(scroll);
		
		lista = new JList();
		lista.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarLista();
			}
		});
		scroll.setViewportView(lista);
		lista.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			}
		});

		passwordSenha = new JPasswordField();
		passwordSenha.setBounds(73, 113, 108, 20);
		getContentPane().add(passwordSenha);

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setBounds(40, 34, 23, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setBounds(28, 59, 46, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setBounds(28, 87, 46, 14);
		getContentPane().add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Senha:");
		lblNewLabel_3.setBounds(28, 116, 46, 14);
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
				listarUsuarios();
			}
		});
		textNome.setBounds(74, 56, 133, 20);
		getContentPane().add(textNome);
		textNome.setColumns(10);
		textNome.setDocument(new Validador(30));

		textLogin = new JTextField();
		textLogin.setBounds(74, 84, 133, 20);
		getContentPane().add(textLogin);
		textLogin.setColumns(10);
		textLogin.setDocument(new Validador(20));

		btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarUsuario();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/ass347.png")));
		btnCreate.setBounds(28, 178, 48, 48);
		getContentPane().add(btnCreate);

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/delete317.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(230, 84, 48, 48);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarUsuario();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/search.png")));
		btnBuscar.setBounds(230, 34, 48, 48);
		getContentPane().add(btnBuscar);
		getRootPane().setDefaultButton(btnBuscar);

		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuario();
			}
		});
		btnDelete.setIcon(new ImageIcon(Usuarios.class.getResource("/img/lixo.png")));
		btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDelete.setContentAreaFilled(false);
		btnDelete.setBorderPainted(false);
		btnDelete.setBounds(83, 178, 48, 48);
		getContentPane().add(btnDelete);

		btnUpdate = new JButton("");
		btnUpdate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnUpdate.setEnabled(false);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					editarUsuarioSenha();
				} else {
					editarUsuario();
				}

			}
		});
		btnUpdate.setIcon(new ImageIcon(Usuarios.class.getResource("/img/att.png")));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBounds(141, 178, 48, 48);
		getContentPane().add(btnUpdate);

		JLabel lblNewLabel_4 = new JLabel("Perfil:");
		lblNewLabel_4.setBounds(28, 147, 46, 14);
		getContentPane().add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(73, 145, 108, 22);
		getContentPane().add(cboPerfil);

		chckSenha = new JCheckBox(" Editar senha");
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordSenha.setEditable(true);
				passwordSenha.setText(null);
				passwordSenha.requestFocus();
				passwordSenha.setBackground(Color.YELLOW);
			}
		});
		chckSenha.setVisible(false);
		chckSenha.setBounds(188, 189, 100, 23);
		getContentPane().add(chckSenha);

	}

	private void adicionarUsuario() {
		String capturaSenha = new String(passwordSenha.getPassword());
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			textNome.requestFocus();
		} else if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o login do usuario");
			textLogin.requestFocus();
		} else if ((capturaSenha.length() == 0)) {
			JOptionPane.showMessageDialog(null, "digite a senha do usuario");
			passwordSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "preencha o perfil do usuario com (admin ou user)");
			cboPerfil.requestFocus();
		} else {
			String create = "insert into usuarios(nome,login,senha,perfil) values (?,?,md5(?),?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "usuario adicionado com sucesso");

				limparCampos();
				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\nlogin duplicado, tente outro");
				textLogin.setText(null);
				textLogin.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void buscarUsuario() {
		if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do usuario");
			textLogin.requestFocus();
		} else {
			String read = "select * from usuarios where login = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, textLogin.getText());
				rs = pst.executeQuery();

				if (rs.next()) {
					textID.setText(rs.getString(1)); 
					textNome.setText(rs.getString(2)); 
					passwordSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));

					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					chckSenha.setVisible(true);

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
	
	@SuppressWarnings("unchecked")
	private void listarUsuarios() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		lista.setModel(modelo);
		String readlista = "select * from usuarios where nome like '" + textNome.getText() + "%'" + "order by nome";
		
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				while (rs.next()){
				scroll.setVisible(true);
				modelo.addElement(rs.getString(2));
				
				if (textNome.getText().isEmpty()) {
			scroll.setVisible(false);
		}
				}
				
			}else {
				scroll.setVisible(false);
				btnCreate.setEnabled(true);
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void buscarLista() {
		
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			textNome.requestFocus();
		} else {
			int linha = lista.getSelectedIndex();
			if (linha >= 0) {
			String read2 = "select * from usuarios where nome like '" + textNome.getText() + "%'" + "order by nome limit " + (linha + 1) + " , 1";
			
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();

				if (rs.next()) {
					textID.setText(rs.getString(1)); 
					textNome.setText(rs.getString(2)); 
					textLogin.setText(rs.getString(3)); 
					passwordSenha.setText(rs.getString(4)); 
					cboPerfil.setSelectedItem(rs.getString(5));

					scroll.setVisible(false);
					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					chckSenha.setVisible(true);

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

	private void editarUsuarioSenha() {
		String capturaSenha = new String(passwordSenha.getPassword());
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			textNome.requestFocus();
		} else if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o login do usuario");
			textLogin.requestFocus();
		} else if ((capturaSenha.length() == 0)) {
			JOptionPane.showMessageDialog(null, "digite a senha do usuario");
			passwordSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "preencha o perfil do usuario com (admin ou user)");
			cboPerfil.requestFocus();
		} else {
			String update = "update usuarios set nome = ?, login = ?, senha = md5(?), perfil = ? where iduser = ?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, capturaSenha);
				pst.setString(5, textID.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "usuario editado com sucesso");
				con.close();
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\nlogin duplicado, tente outro");
				textLogin.setText(null);
				textLogin.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}
	private void editarUsuario() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			textNome.requestFocus();
		} else if (textLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "digite o login do usuario");
			textLogin.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "preencha o perfil do usuario com (admin ou user)");
			cboPerfil.requestFocus();
		} else {
			String update = "update usuarios set nome = ?, login = ?, perfil = ? where iduser = ?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "usuario editado com sucesso");
				con.close();
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\nlogin duplicado, tente outro");
				textLogin.setText(null);
				textLogin.requestFocus();

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void excluirUsuario() {
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste usuario?", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from usuarios where iduser = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Contato excluido");
				limparCampos();
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void limparCampos() {
		textID.setText(null);
		textNome.setText(null);
		textLogin.setText(null);
		passwordSenha.setText(null);
		cboPerfil.setSelectedItem(null);
		scroll.setVisible(false);

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);
		chckSenha.setVisible(false);

	}
}
