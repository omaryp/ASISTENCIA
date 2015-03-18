
package pe.com.gym.delegate;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import pe.com.gym.dto.ClienteDTO;
import pe.com.gym.entidades.PlantillaHuella;
import pe.com.gym.facade.FacadeGYMRemote;



/**
 * @author Omar Yarleque
 */
public enum Gym {
	INSTANCE;
    FacadeGYMRemote facadeRemote;
    Logger logger = Logger.getLogger(Gym.class.getName());
    
    private Gym() {
        try {
        	Properties properties = new Properties();
            properties.setProperty("org.omg.CORBA.ORBInitialHost", "127.0.0.1");
            properties.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
            Context ic = new InitialContext(properties);
            //windows
            //facadeRemote =(FacadeGYMRemote) ic.lookup("java:global/GYM_EAR/GYM_EJB-1.0-SNAPSHOT/FacadeGYM");
            facadeRemote =(FacadeGYMRemote) ic.lookup("java:global/GYM_EAR/GYM_EJB-1.0-SNAPSHOT/FacadeGYM!pe.com.gym.facade.FacadeGYMRemote");
            //linux
            //facadeLocal =(FacadeGYMLocal) ic.lookup("java:global/COBRA-EAR-1.0-SNAPSHOT/COBRA-EJB-1.0-SNAPSHOT/FacadeCOBRA!pe.cajapiura.cobra.facade.FacadeCOBRALocal");
            Logger.getLogger(Gym.class.getName()).log(Level.INFO, "Cargando recurso ....");
        } catch (NamingException ex) {
        	Logger.getLogger(Gym.class.getName()).log(Level.SEVERE, "No se pudo ubicar el recurso", ex);
        	ex.printStackTrace();
        }
    }
    
    public int marcarAsistencia(ClienteDTO cliente, PlantillaHuella template) {
		return facadeRemote.marcarAsistencia(cliente, template);
	}
    
    public ClienteDTO obtenerClienteDNI(String dni){
    	return facadeRemote.obtenerClienteDNI(dni);
    }
    
}
