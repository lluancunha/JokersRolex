package view;

import java.awt.Cursor;
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

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.DAO;
import util.Validador;

public class Tecnicos extends JDialog {

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
	private JButton btnBuscar;
	private JButton btnCreate;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JScrollPane scroll;
	@SuppressWarnings("rawtypes")
	private JList lista;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tecnicos dialog = new Tecnicos();
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
	@SuppressWarnings("rawtypes")
	public Tecnicos() {
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scroll.setVisible(false);
			}
		});
		setTitle("Jokers Rolex - Tecnicos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Tecnicos.class.getResource("/img/aplle.png")));
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		scroll = new JScrollPane();
		scroll.setVisible(false);
		scroll.setBounds(73, 75, 134, 40);
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

		textID = new JTextField();
		textID.setEditable(false);
		textID.setBounds(73, 31, 86, 20);
		getContentPane().add(textID);
		textID.setColumns(10);

		textNome = new JTextField();
		textNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarTecnicos();
			}
		});
		textNome.setBounds(73, 56, 134, 20);
		getContentPane().add(textNome);
		textNome.setColumns(10);
		textNome.setDocument(new Validador(30));

		btnCreate = new JButton("");
		btnCreate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreate.setEnabled(false);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionarTecnico();
			}
		});
		btnCreate.setContentAreaFilled(false);
		btnCreate.setBorderPainted(false);
		btnCreate.setIcon(new ImageIcon(Tecnicos.class.getResource("/img/ass347.png")));
		btnCreate.setBounds(28, 178, 48, 48);
		getContentPane().add(btnCreate);

		JButton btnLimpar = new JButton("");
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setIcon(new ImageIcon(Tecnicos.class.getResource("/img/delete317.png")));
		btnLimpar.setContentAreaFilled(false);
		btnLimpar.setBorderPainted(false);
		btnLimpar.setBounds(230, 84, 48, 48);
		getContentPane().add(btnLimpar);

		btnBuscar = new JButton("");
		btnBuscar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarTecnico();
			}
		});
		btnBuscar.setContentAreaFilled(false);
		btnBuscar.setBorderPainted(false);
		btnBuscar.setIcon(new ImageIcon(Tecnicos.class.getResource("/img/search.png")));
		btnBuscar.setBounds(230, 34, 48, 48);
		getContentPane().add(btnBuscar);
		getRootPane().setDefaultButton(btnBuscar);

		btnDelete = new JButton("");
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirTecnico();
			}
		});
		btnDelete.setIcon(new ImageIcon(Tecnicos.class.getResource("/img/lixo.png")));
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
				editarTecnico();
			}
		});
		btnUpdate.setIcon(new ImageIcon(Tecnicos.class.getResource("/img/att.png")));
		btnUpdate.setBorderPainted(false);
		btnUpdate.setContentAreaFilled(false);
		btnUpdate.setBounds(141, 178, 48, 48);
		getContentPane().add(btnUpdate);

	}

	private void adicionarTecnico() {

		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do tecnico");
			textNome.requestFocus();

		} else {
			String create = "insert into tecnicos(nome) values (?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(create);
				pst.setString(1, textNome.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "usuario adicionado com sucesso");

				limparCampos();

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\nlogin duplicado, tente outro");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void buscarTecnico() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do tecnico");
			textNome.requestFocus();
		} else {

			String read = "select * from tecnicos where nome = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, textNome.getText());
				rs = pst.executeQuery();

				if (rs.next()) {
					textID.setText(rs.getString(1));
					textNome.setText(rs.getString(2));

					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);

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

	@SuppressWarnings("unchecked")
	private void listarTecnicos() {
		DefaultListModel<String> modelo = new DefaultListModel<>();
		lista.setModel(modelo);
		String readlista = "select * from tecnicos where nome like '" + textNome.getText() + "%'" + "order by nome";
		
		try {
			con = dao.conectar();
			pst = con.prepareStatement(readlista);
			rs = pst.executeQuery();
		
				while (rs.next()){
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
			JOptionPane.showMessageDialog(null, "Digite o nome do usuario");
			textNome.requestFocus();
		} else {
			int linha = lista.getSelectedIndex();
			if (linha >= 0) {
			String read2 = "select * from tecnicos where nome like '" + textNome.getText() + "%'" + "order by nome limit " + (linha) + " , 1";
			
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read2);
				rs = pst.executeQuery();

				if (rs.next()) {
					textID.setText(rs.getString(1)); 
					textNome.setText(rs.getString(2));
					
					scroll.setVisible(false);
					btnCreate.setEnabled(false);
					btnBuscar.setEnabled(false);
					btnUpdate.setEnabled(true);
					btnDelete.setEnabled(true);
					

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

	private void editarTecnico() {
		if (textNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome do tecnico");
			textNome.requestFocus();
		} else {
			String update = "update tecnicos set nome = ? where idtec = ?";

			try {
				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, textNome.getText());
				pst.setString(2, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "tecnico editado com sucesso");
				con.close();
				
				limparCampos();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\nome duplicado, tecnico ja cadastrado");

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	
	private void excluirTecnico() {
		int confirma = JOptionPane.showConfirmDialog(null, "confirma a exclusão deste tecnico?", "ATENÇÂO",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from tecnicos where idtec = ?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(delete);
				pst.setString(1, textID.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "tecnico excluido");
				limparCampos();
				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "ERRO!!!\n Tecnico com ordem de serviço vinculada");

			} catch (Exception e2) {
				System.out.println(e2);
			}
		}

	}

	private void limparCampos() {
		textID.setText(null);
		textNome.setText(null);
		scroll.setVisible(false);

		btnCreate.setEnabled(false);
		btnUpdate.setEnabled(false);
		btnDelete.setEnabled(false);
		btnBuscar.setEnabled(true);

	}
}
