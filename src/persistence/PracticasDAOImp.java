package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Util.Constantes;
import Util.Date_solver;
import bussines.Actividad;
import bussines.Asignatura;
import bussines.Practicas;
import persistence.dao.PracticasDAO;
import persistence.dto.ActividadDTO;

public class PracticasDAOImp implements PracticasDAO {

	protected ConnectionManager connectionManager;

	public PracticasDAOImp(){
		try{
			System.out.println("\n\t######## PracticasDAOImp ########  ");
			connectionManager= new ConnectionManager(Constantes.DATABASE);
		}catch(Exception e){
			System.err.println("Error en persistencia, PracticasDAOImp: "+e.getLocalizedMessage());
		}
	}

	@Override
	public Practicas obtenerInformacionDePracticas(int id_Practicas) {		
		Practicas prac = null;
		try{
			connectionManager.connect();
			ResultSet practicasResultSet = connectionManager.queryDB("SELECT * from PRACTICAS where id_practicas = '"+id_Practicas+"'");
			connectionManager.close();


			if (practicasResultSet.next()){
				ActividadDTO acti = new ActividadDAOImp().obtenerInformacionDeActividad(practicasResultSet.getInt("id_actividad"));

				prac = new Practicas(practicasResultSet.getInt("id_practicas"),
									 practicasResultSet.getInt("id_actividad"),
							         new AsignaturaDAOImp().obtenerInformacionAsignatura(acti.getId_asignatura()),
								     acti.getTitulo(),
									 acti.getDescripcion(),
									 acti.getFechaFinalizacion(),
									 acti.getTiempoEstimado(),
									 acti.getPorcentaje(),
									 acti.getPrioridadUsuario(),
									 acti.getPrioridadTotal(),
									 acti.isFinalizada(),
									 acti.isPara_despues(),
									 practicasResultSet.getBoolean("grupal"),
									 practicasResultSet.getBoolean("recuperable"));
			}
		}catch(Exception e){
			System.err.println("Ha ocurrido un error al buscar el practicas: "+e.getLocalizedMessage() );
		}

		return prac;
	}

	@Override
	public void eliminarPracticas(int id_Practicas) {
		Practicas prac = obtenerInformacionDePracticas(id_Practicas);
		try{
			connectionManager.connect();
			String str = "DELETE FROM PRACTICAS WHERE id_practicas ='"+ id_Practicas +"'";
			connectionManager.updateDB(str);
			connectionManager.close();
		}catch(Exception e){
			System.err.println("Ha ocurrido un error al eliminar la Practica: "+e.getLocalizedMessage() );
		}
		new ActividadDAOImp().eliminarActividad(prac.getId_actividad());
	}

	@Override
	public Practicas crearPracticas(Practicas practicas) {
		Practicas practicasAux = practicas;
		try{
			connectionManager.connect();
			int id = crearSecuencia(Constantes.PRACTICAS_SQ);
			if(id>0){
				String str = "INSERT INTO PRACTICAS (ID_PRACTICAS, ID_ACTIVIDAD, GRUPAL, RECUPERABLE) " +
							 "VALUES ("
							 +id+","
							 +new ActividadDAOImp().crearActividad(practicas).getId_actividad()+","
							 +practicas.isGrupal()+","
							 +practicas.isRecuperable()
							 +")";

				if(practicasAux!=null)
					practicasAux.setId_practicas(id);

				connectionManager.updateDB(str);
				System.out.println("\nPracticas creadas con éxito: " + practicasAux);
			}
			connectionManager.close();

		}catch(Exception e){
			System.err.println("Ha ocurrido un error al crear la práctica: "+e.getLocalizedMessage() );
		}

		return practicasAux;
	}

	@Override
	public void editarPracticas(Practicas Practicas) {
		try{
			connectionManager.connect();
			String str = "UPDATE PRACTICAS "+
						 "SET id_practicas = "+Practicas.getId_practicas()+", "+
						 "SET id_actividad = "+Practicas.getId_actividad()+", "+
						 "SET grupal = "+Practicas.isGrupal()+", "+
						 "SET recuperable ="+Practicas.isRecuperable()+
						 " WHERE id_practicas =" +Practicas.getId_practicas()+")";
			connectionManager.updateDB(str);
			System.out.println("\nPracticas editado con éxito: " + Practicas);
			connectionManager.close();

		}catch(Exception e){
			System.err.println("Ha ocurrido un error al editar el practicas: "+e.getLocalizedMessage() );
		}

	}

    private int crearSecuencia(String nombreSecuencia){
		try{
		ResultSet sq = connectionManager.queryDB("CALL NEXT VALUE FOR " + nombreSecuencia);

		if (sq.next())
			return sq.getInt(1);

		}catch(Exception e){
		   System.err.println("Ha ocurrido un error al generar la secuencia de id "+e.getLocalizedMessage());
		}
		return -1;

	}


	@Override
	public List<Actividad> obtenerActividadesDeAsignatura(Asignatura asignatura) {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet practicasResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, PRACTICAS P where A.id_asignatura = '"+asignatura.getId_asignatura()+"' AND A.id_actividad = P.id_actividad AND A.finalizada = FALSE AND A.para_despues = FALSE");
			connectionManager.close();

			while(practicasResultSet.next()){
				Practicas practica = obtenerInformacionDePracticas(practicasResultSet.getInt("id_practicas"));
				listaActividades.add( practica );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las activdades-practicas de la asignatura :" + asignatura + " -> "  + e.getLocalizedMessage());
		}

		return listaActividades;
	}

	@Override
	public List<Actividad> obtenerTodasActividades() {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet practicasResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, PRACTICAS P WHERE A.id_actividad = P.id_actividad AND A.para_despues = FALSE AND A.finalizada = FALSE");
			connectionManager.close();

			while(practicasResultSet.next()){
				Practicas practica = obtenerInformacionDePracticas(practicasResultSet.getInt("id_practicas"));
				listaActividades.add( practica );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las todas activdades-practicas -> "  + e.getLocalizedMessage());
		}

		return listaActividades;
	}

	@Override
	public List<Actividad> obtenerActividadesHoy() {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet practicasResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, PRACTICAS P WHERE A.id_actividad = P.id_actividad AND A.fecha_finalizacion = '" + Date_solver.convertirLocalDateEnSQL(Date_solver.fechaDeHoy())+ "' AND A.para_despues = FALSE AND A.finalizada = FALSE");
			connectionManager.close();

			while(practicasResultSet.next()){
				Practicas practica = obtenerInformacionDePracticas(practicasResultSet.getInt("id_practicas"));
				listaActividades.add( practica );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las todas activdades-practicas -> "  + e.getLocalizedMessage());
		}
		return listaActividades;
	}

	@Override
	public List<Actividad> obtenerActividadesParaDespues() {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet practicasResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, PRACTICAS P WHERE A.id_actividad = P.id_actividad AND A.para_despues = TRUE AND A.finalizada = FALSE ");
			connectionManager.close();

			while(practicasResultSet.next()){
				Practicas practica = obtenerInformacionDePracticas(practicasResultSet.getInt("id_practicas"));
				listaActividades.add( practica );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las todas activdades-practicas -> "  + e.getLocalizedMessage());
		}
		return listaActividades;	
	}

}
