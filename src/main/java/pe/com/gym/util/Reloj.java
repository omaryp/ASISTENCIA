package pe.com.gym.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;

public class Reloj extends Thread implements Runnable {

	private JLabel mostrar;
	private StringBuilder hora ;

	public Reloj(JLabel mostrar) {
		this.mostrar = mostrar;
	}

	public void run(){
		hora = new StringBuilder(); 
		synchronized (this) {
			while(true){
				 hora.setLength(0);
				 Date now = new Date();
				 hora.append("<html><span style='color:red;font-size:35px;'>");
				 hora.append(new SimpleDateFormat("hh:mm:ss aa").format(now)); 
				 hora.append("</span></html>");
				 mostrar.setText(hora.toString());
				 mostrar.repaint();
				 esperar();
			}
		}
	}

	public void esperar() {
		try {
			this.wait(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String retornaHora(){
		return hora.toString();
	}

}
