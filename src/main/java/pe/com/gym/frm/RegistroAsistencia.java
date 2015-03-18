package pe.com.gym.frm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import pe.com.gym.delegate.Gym;
import pe.com.gym.dto.ClienteDTO;
import pe.com.gym.entidades.PlantillaHuella;
import pe.com.gym.util.Reloj;

import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.event.DPFPDataAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPErrorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPErrorEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.capture.event.DPFPSensorAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPSensorEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;

/**
 * Aplicativo para el registro de asistencia para los clientes
 * 
 * @author Omar Yarleque
 * 
 */
public class RegistroAsistencia extends JFrame {

	private static final long serialVersionUID = 1L;
	private int cant_car;
	private JLabel lbl_nombre;
	private JLabel lbl_hora;
	private JLabel lbl_dni;
	private JLabel lbl_fecha;
	private JLabel lbl_txt_ini;
	private JLabel lbl_txt_fin;
	private JLabel lbl_hora_ini;
	private JLabel lbl_hora_fin;
	private JTextField txt_dni;
	private JPanel png_datos;
	private JPanel png_dat_salida;
	private JPanel png_dni;
	private JPanel png_salida;
	private JPanel png_salida1;
	private JPanel png_datos1;
	private JLabel lbl_area;
	private Reloj reloj;
	private String fecha;

	// Variables del lector
	// Varible que permite iniciar el dispositivo de lector de huella conectado
	// con sus distintos metodos.
	private DPFPCapture Lector = DPFPGlobal.getCaptureFactory().createCapture();

	public static String TEMPLATE_PROPERTY = "template";
	private DPFPFeatureSet featuresverificacion;

	public RegistroAsistencia() {
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("REGISTRO DE ASISTENCIA");
		this.setLayout(new BorderLayout());
		cant_car = 0;
		init_componentes();
		crea_pantalla();
		registra_componentes();
		reloj = new Reloj(lbl_hora);
		reloj.start();
		setVisible(true);
	}

	public void init_componentes() {
		lbl_nombre = new JLabel("Gladiator-Fitnes");
		lbl_hora = new JLabel();
		lbl_dni = new JLabel("<html><span style='font-size : 12px'>DNI : </span></html>");
		lbl_area = new JLabel();
		lbl_area.setText("<html><span style = 'color : blue;font-size:40px;font-weight:bold;' >Ingrese su DNI.</span></html>");
		//para detalle de la hora de marcación
		lbl_txt_ini = new JLabel("<html><span style='font-size : 12px;'>INGRESO</span></html>");
		lbl_txt_ini.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_txt_ini.setPreferredSize(new Dimension(150, 30));
		lbl_hora_ini = new JLabel();
		lbl_hora_ini.setPreferredSize(new Dimension(150, 30));
		lbl_txt_fin = new JLabel("<html><span style='font-size : 12px;'>SALIDA</span></html>");
		lbl_txt_fin.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_txt_fin.setPreferredSize(new Dimension(150, 30));
		lbl_hora_fin = new JLabel();
		lbl_hora_fin.setPreferredSize(new Dimension(150, 30));
		//
		formateaFecha();
		lbl_fecha = new JLabel(fecha);
		txt_dni = new JTextField();
		txt_dni.setPreferredSize(new Dimension(150, 30));
		txt_dni.setFont ( txt_dni.getFont().deriveFont(16f));
		png_datos = new JPanel();
		png_datos1 = new JPanel();
		png_dni = new JPanel();
		png_salida = new JPanel();
		png_salida1 = new JPanel();
		png_dat_salida = new JPanel();
		
	}

	public void crea_pantalla() {
		png_datos.setLayout(new BorderLayout());
		lbl_fecha.setBorder(BorderFactory.createTitledBorder(""));
		png_datos.add(lbl_fecha, BorderLayout.NORTH);
		png_datos1.setLayout(new BorderLayout());
		png_datos1.add(lbl_nombre, BorderLayout.CENTER);
		png_datos1.add(lbl_hora, BorderLayout.EAST);
		
		png_datos.add(png_datos1, BorderLayout.CENTER);

		png_dni.setLayout(new FlowLayout());
		png_dni.add(lbl_dni);
		png_dni.add(txt_dni);
		
		png_dat_salida.setLayout(new GridLayout(2,2));
		png_dat_salida.add(lbl_txt_ini);
		png_dat_salida.add(lbl_txt_fin);
		png_dat_salida.add(lbl_hora_ini);
		png_dat_salida.add(lbl_hora_fin);
		
		png_salida.setLayout(new FlowLayout());
		png_salida.add(png_dat_salida);

		png_salida1.setLayout(new BorderLayout());
		png_salida1.add(png_dni, BorderLayout.NORTH);
		png_salida1.add(png_salida, BorderLayout.CENTER);
		png_salida1.setBorder(BorderFactory.createTitledBorder(""));

		png_datos.add(png_salida1, BorderLayout.SOUTH);
		this.add(png_datos, BorderLayout.NORTH);
		this.add(lbl_area, BorderLayout.CENTER);
	}

	public void formateaFecha() {
		Date now = new Date();
		DateFormat df4 = DateFormat.getDateInstance(DateFormat.FULL);
		fecha = "<html><span style='color:BLUE;font-size : 12px;font-weight:bold;'>"
				+ df4.format(now) + "</span></html>";
	}

	public void registra_componentes() {
		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				stop();
				System.exit(0);
			}

			public void windowOpened(java.awt.event.WindowEvent evt) {
				eventosLector();
				iniciar();
			}
		});
		txt_dni.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (cant_car < 8) {
					char car = e.getKeyChar();
					if (Character.isDigit(car))
						cant_car++;
					else
						e.consume();
				} else
					e.consume();
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int code = e.getKeyCode();
				if (code == KeyEvent.VK_BACK_SPACE && cant_car > 0)
					cant_car--;
			}
		});
	}

	public void marcarAsistencia() {
		try {
			String dni = txt_dni.getText().trim();
			PlantillaHuella huella = null;
			int res = 0;
			String mensaje = "";
			int intensidad = 0;
			if (!dni.equals("")) {
				ClienteDTO cliente = Gym.INSTANCE.obtenerClienteDNI(dni);
				if (cliente != null) {
					huella = new PlantillaHuella();
					huella.setPlantilla(featuresverificacion.serialize());
					res = Gym.INSTANCE.marcarAsistencia(cliente, huella);
					switch (res) {
					case 0:
						intensidad = 1;
						mensaje = "Bienvenido(a) " + cliente.getNombreCliente()
								+ " " + cliente.getApellidoCliente()+" !!!";
						lbl_hora_ini.setText(reloj.retornaHora());
						break;
					case 1:
						intensidad = 1;
						mensaje = "Hasta luego " + cliente.getNombreCliente()
								+ " " + cliente.getApellidoCliente()+" . Vuelva pronto. !!!";
						
						lbl_hora_fin.setText(reloj.retornaHora());
						break;
					case 2:
						intensidad = 2;
						mensaje = "Esta huella no le pertenece !!!";
						lbl_hora_ini.setText("");
						lbl_hora_fin.setText("");
						break;
					}
				} else{
					intensidad = 2;
					mensaje = "Cliente no registrado.";
					lbl_hora_ini.setText("");
					lbl_hora_fin.setText("");
				}
				mostrarMensaje(intensidad, mensaje);
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex);
			ex.printStackTrace();
		}
	}
	
	public void mostrarMensaje(int intensidad,String mensaje){
		String ini_aviso = "<html><span style = 'color : blue;font-size:40px;font-weight:bold;' >";
		String ini_error = "<html><span style = 'color : red ;font-size:40px;font-weight:bold;' >";
		String fin = "</span></html>";
		switch (intensidad) {
			case 1:
				lbl_area.setText(ini_aviso+mensaje+fin);
				break;
			case 2 :
				lbl_area.setText(ini_error+mensaje+fin);
				break;
		}
	}

	protected void eventosLector() {
		Lector.addDataListener(new DPFPDataAdapter() {
			@Override
			public void dataAcquired(final DPFPDataEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ProcesarCaptura(e.getSample());
					}
				});
			}
		});

		Lector.addReaderStatusListener(new DPFPReaderStatusAdapter() {
			@Override
			public void readerConnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
					}
				});
			}

			@Override
			public void readerDisconnected(final DPFPReaderStatusEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
					}
				});
			}
		});

		Lector.addSensorListener(new DPFPSensorAdapter() {
			@Override
			public void fingerTouched(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
					}
				});
			}

			@Override
			public void fingerGone(final DPFPSensorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
					}
				});
			}
		});

		Lector.addErrorListener(new DPFPErrorAdapter() {
			@SuppressWarnings("unused")
			public void errorReader(final DPFPErrorEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {

					}
				});
			}
		});
	}

	public void ProcesarCaptura(DPFPSample sample) {
		// Procesar la muestra de la huella y crear un conjunto de
		// características con el propósito de verificacion.
		featuresverificacion = extraerCaracteristicas(sample,DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
		marcarAsistencia();
	}

	public DPFPFeatureSet extraerCaracteristicas(DPFPSample sample, DPFPDataPurpose purpose) {
		DPFPFeatureExtraction extractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
		try {
			return extractor.createFeatureSet(sample, purpose);
		} catch (DPFPImageQualityException e) {
			return null;
		}
	}

	public void iniciar() {
		Lector.startCapture();
	}

	public void stop() {
		Lector.stopCapture();
	}

}