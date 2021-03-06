package bussines;

import java.util.ArrayList;
import persistence.dto.ActividadDTO;

public class Fabrica_Actividad {
	 private static Fabrica_Actividad instance;
	
	 public static Fabrica_Actividad getInstance() {
		  if(instance == null){
			  instance = new Fabrica_Actividad();
		  }return instance;
	  }
	private Fabrica_Actividad() {
		
	}
/*
 * TIPO 1 PRACTICA
 * TIPO 2 CLASE
 * TIPO 3 EXAMEN
 */
	public Actividad crearActividad(int tipo,
			ActividadDTO actividaddto,
			ArrayList<Boolean> especificaciones
			){
		System.err.println("La actividad DTO que llega a la fabrica:" + actividaddto);
		switch(tipo){
			
		case 1:
			Actividad practica = crearPractica(
					actividaddto,
					especificaciones);
			return practica;
			
		case 2:
			Actividad clase = crearClase(
					actividaddto,
					especificaciones);
			return clase;
			
		case 3:
			Actividad examen_concreto = crearExamen(
					actividaddto,
					especificaciones);
			return examen_concreto;
			
		default: return null;
			
		
		}
		
	}
	
	private Actividad crearPractica(ActividadDTO actividaddto,ArrayList<Boolean> especificaciones){
		System.err.println("La actividad DTO que llega a la fabrica 1 :" + actividaddto);

		Practicas practica = new Practicas(Unidad_Logica.getInstance().obtenerInformacionAsignatura(actividaddto.getId_asignatura()),
				actividaddto.getTitulo(), 
				actividaddto.getDescripcion(), 
				actividaddto.getFechaFinalizacion(), 
				actividaddto.getTiempoEstimado(), 
				actividaddto.getPorcentaje(), 
				actividaddto.getPrioridadUsuario(), 
				actividaddto.isFinalizada(),
				actividaddto.isPara_despues(),
				especificaciones.get(0),
				especificaciones.get(1));
		practica = Unidad_Logica.getInstance().crearPracticas(practica);
		return practica;
	}
	
	private Actividad crearClase(ActividadDTO actividaddto,ArrayList<Boolean> especificaciones){
		System.err.println("La actividad DTO que llega a la fabrica 2: "  + actividaddto);

		Clase clase = new Clase(Unidad_Logica.getInstance().obtenerInformacionAsignatura(actividaddto.getId_asignatura()),
				actividaddto.getTitulo(), 
				actividaddto.getDescripcion(), 
				actividaddto.getFechaFinalizacion(), 
				actividaddto.getTiempoEstimado(), 
				actividaddto.getPorcentaje(), 
				actividaddto.getPrioridadUsuario(), 
				actividaddto.isFinalizada(),
				actividaddto.isPara_despues(),
				
				especificaciones.get(0));
		clase = Unidad_Logica.getInstance().crearClase(clase);
		return clase;
	}

	private Actividad crearExamen(ActividadDTO actividaddto,ArrayList<Boolean> especificaciones){
		System.err.println("La actividad DTO que llega a la fabrica 3:" + actividaddto );

		//EXAMEN CONCRETO
		Actividad_Examen examen = new Examen(Unidad_Logica.getInstance().obtenerInformacionAsignatura(actividaddto.getId_asignatura()),
				actividaddto.getTitulo(), 
				actividaddto.getDescripcion(), 
				actividaddto.getFechaFinalizacion(), 
				actividaddto.getTiempoEstimado(), 
				actividaddto.getPorcentaje(), 
				actividaddto.getPrioridadUsuario(), 
				actividaddto.isFinalizada(),
				actividaddto.isPara_despues(),
				especificaciones.get(1),
				especificaciones.get(0));
		System.err.println("especificaciones en fabrica: " + especificaciones);
		return examen;
	}
}
