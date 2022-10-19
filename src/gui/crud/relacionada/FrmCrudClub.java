package gui.crud.relacionada;

import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

import util.JComboBoxBD;
import util.Validaciones;

import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entidad.Club;
import entidad.Pais;
import model.ClubModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.awt.event.MouseEvent;

public class FrmCrudClub extends JFrame implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtFecha;
	private JButton btnIngresar;
	private JButton btnActualizar;
	private JButton btnEliminar;
	private JCheckBox chkEstado;
	// -1 indica que no se ha selecionado nada en la grilla o Jtable
	int idSeleccionado = -1;

	int hoveredRow = -1, hoveredColumn = -1;
	private JComboBoxBD cboPais;
	private ResourceBundle rb = ResourceBundle.getBundle("combo");
	private JLabel lblMantenimientoClub;
	private JTable table;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmCrudClub frame = new FrmCrudClub();
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
	public FrmCrudClub() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(50, 95, 130, 14);
		contentPane.add(lblNombre);

		JLabel lblFechaDeNacimiento = new JLabel("Fecha de creaci\u00F3n");
		lblFechaDeNacimiento.setBounds(50, 135, 130, 14);
		contentPane.add(lblFechaDeNacimiento);

		txtNombre = new JTextField();
		txtNombre.setBounds(219, 92, 322, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		txtFecha = new JTextField();
		txtFecha.setBounds(219, 132, 186, 20);
		contentPane.add(txtFecha);
		txtFecha.setColumns(10);

		btnIngresar = new JButton("Ingresar");
		btnIngresar.addActionListener(this);
		btnIngresar.setIcon(new ImageIcon(FrmCrudClub.class.getResource("/iconos/add.gif")));
		btnIngresar.setBounds(637, 83, 130, 30);
		contentPane.add(btnIngresar);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(this);
		btnActualizar.setIcon(new ImageIcon(FrmCrudClub.class.getResource("/iconos/edit.gif")));
		btnActualizar.setBounds(637, 168, 130, 30);
		contentPane.add(btnActualizar);

		btnEliminar = new JButton("Eliminar");
		btnEliminar.addActionListener(this);
		btnEliminar.setIcon(new ImageIcon(FrmCrudClub.class.getResource("/iconos/delete.gif")));
		btnEliminar.setBounds(637, 127, 130, 30);
		contentPane.add(btnEliminar);
		
		chkEstado = new JCheckBox("Activo");
		chkEstado.setSelected(true);
		chkEstado.setBounds(218, 214, 97, 30);
		contentPane.add(chkEstado);
		
		JLabel lblPas = new JLabel("Pa\u00EDs");
		lblPas.setBounds(50, 176, 130, 14);
		contentPane.add(lblPas);
		
		cboPais = new JComboBoxBD(rb.getString("SQL_PAIS"));
		cboPais.setBounds(219, 172, 322, 22);
		getContentPane().add(cboPais);
		
		lblMantenimientoClub = new JLabel("Mantenimiento Club");
		lblMantenimientoClub.setOpaque(true);
		lblMantenimientoClub.setHorizontalAlignment(SwingConstants.CENTER);
		lblMantenimientoClub.setForeground(Color.WHITE);
		lblMantenimientoClub.setFont(new Font("Tahoma", Font.BOLD, 19));
		lblMantenimientoClub.setBackground(Color.RED);
		lblMantenimientoClub.setBounds(23, 11, 744, 59);
		contentPane.add(lblMantenimientoClub);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 251, 744, 323);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(this);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Nombre", "Fecha Creaci\u00F3n", "Estado", "Id Pa\u00EDs", "Pa\u00EDs"
			}
		));
		table.getColumnModel().getColumn(1).setPreferredWidth(164);
		table.getColumnModel().getColumn(2).setPreferredWidth(133);
		scrollPane.setViewportView(table);
		
		lista();
	}
	
	void mensaje(String m) {
		JOptionPane.showMessageDialog(this, m);
	}

	void limpiarCajasTexto() {
		txtNombre.setText("");
		txtFecha.setText("");
		txtNombre.requestFocus();
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnActualizar) {
			actionPerformedBtnActualizarJButton(e);
		}
		if (e.getSource() == btnEliminar) {
			actionPerformedBtnEliminarJButton(e);
		}
		if (e.getSource() == btnIngresar) {
			actionPerformedBtnIngresarJButton(e);
		}
	}
	protected void actionPerformedBtnIngresarJButton(ActionEvent e) {
		registra();
	}
	protected void actionPerformedBtnEliminarJButton(ActionEvent e) {
		elimina();
	}
	protected void actionPerformedBtnActualizarJButton(ActionEvent e) {
		actualiza();
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			mouseClickedTableJTable(e);
		}
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
	}
	protected void mouseClickedTableJTable(MouseEvent e) {
		busca();
	}
	
	public void lista() {
		DefaultTableModel dt = (DefaultTableModel) table.getModel();
		dt.setRowCount(0);
		
		ClubModel model = new ClubModel();
		List<Club> lista = model.listaTodos();
		
		for (Club x : lista) {
			Object[] f = {x.getIdClub(), x.getNombre(), x.getFechaCreacion(), 
						  getDesEstado(x.getEstado()) , x.getPais().getIdPais(), x.getPais().getNombre()};
			dt.addRow(f);
		}
	}
	
	public String getDesEstado(int x) {
		if (x == 0) return "Inactivo";
		else		return "Activo";
	}
	
	public void registra() {
		String nom = txtNombre.getText().trim();
		String fec = txtFecha.getText().trim();
		int posPais = cboPais.getSelectedIndex();
		boolean est = chkEstado.isSelected();
		
		if (!nom.matches(Validaciones.TEXTO)) {
			mensaje("El nombre es de 2 a 20 caracteres");
		}else if (!fec.matches(Validaciones.FECHA)) {
			mensaje("la fecha tiene formato YYYY-MM-dd");
		}else if (posPais == 0) {
			mensaje("Selecciona un Pa�s");
		}else {
			String pais = cboPais.getSelectedItem().toString();
			String idPais = pais.split(":")[0];
			
			Pais objPais = new Pais();
			objPais.setIdPais(Integer.parseInt(idPais));
			
			Club objClub = new Club();
			objClub.setNombre(nom);
			objClub.setFechaCreacion(Date.valueOf(fec));
			objClub.setPais(objPais);
			if (est) 
				objClub.setEstado(1);
			else 	 
				objClub.setEstado(0);
			
			ClubModel model = new ClubModel();
			int salida = model.insertaClub(objClub);
			if (salida > 0) {
				lista();
				mensaje("Se insert� correctamente");
			}else {
				mensaje("Error en el Registro");
			}
			
		}
		
	}
	public void busca() {}
	public void actualiza() {}
	public void elimina() {}
	
}





