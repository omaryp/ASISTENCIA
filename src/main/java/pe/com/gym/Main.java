package pe.com.gym;

import javax.swing.UIManager;

import pe.com.gym.frm.RegistroAsistencia;

/**
 * @author Omar Yarleque
 */
public class Main {
    
    public static void main( String[] args ) {
	 try {
	      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
        new RegistroAsistencia();
    }
}
