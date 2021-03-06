package persistence;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import Util.Constantes;
import Util.Date_solver;
import bussines.Actividad;
import bussines.Asignatura;
import bussines.Clase;
import persistence.dao.ClaseDAO;
import persistence.dto.ActividadDTO;

public class ClaseDAOImp implements ClaseDAO {

	protected ConnectionManager connectionManager;

	public ClaseDAOImp(){
		try{
			System.out.println("\n\t######## ClaseDAOImp ########  ");
			connectionManager = new ConnectionManager(Constantes.DATABASE);
		}catch(Exception e){
			System.err.println("Error en persistencia, ClaseDAOImp: "+e.getLocalizedMessage());
		}
	}

	@Override
	public Clase obtenerInformacionDeClase(int id_clase) {
		Clase clas = null;
		try{
			connectionManager.connect();
			ResultSet claseResultSet = connectionManager.queryDB("SELECT * from CLASE where id_clase = '"+id_clase+"'");
			connectionManager.close();


			if (claseResultSet.next()){
				ActividadDTO acti = new ActividadDAOImp().obtenerInformacionDeActividad(claseResultSet.getInt("id_actividad"));

				clas = new Clase(claseResultSet.getInt("id_clase"),
								acti.getId_actividad(),
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
								 claseResultSet.getBoolean("puntuable"));
			}
		}catch(Exception e){
			System.err.println("Ha ocurrido un error al buscar el practicas: "+e.getLocalizedMessage() );
		}
		return clas;

	}

	@Override
	public void eliminarClase(int id_clase) {
		Clase clas = obtenerInformacionDeClase(id_clase);
		try{
			connectionManager.connect();
			String str = "DELETE FROM CLASE WHERE id_clase ='"+ id_clase +"'";
			connectionManager.updateDB(str);
			connectionManager.close();


		}catch(Exception e){
			System.err.println("Ha ocurrido un error al eliminar Clase: "+e.getLocalizedMessage() );
		}
		new ActividadDAOImp().eliminarActividad(clas.getId_actividad());
	}

	@Override
	public Clase crearClase(Clase clase) {

		Clase clas= clase;
		try{
			connectionManager.connect();
			int id = crearSecuencia(Constantes.CLASE_SQ);
			if(id>0){
				String str = "INSERT INTO CLASE (ID_CLASE, ID_ACTIVIDAD, PUNTUABLE) " +
							 "VALUES ("
							 +id+","
							 +new ActividadDAOImp().crearActividad(clas).getId_actividad()+","
							 +clas.isPuntuable()
							 +")";

				if(clas!=null)
					clas.setId_clase(id);

				connectionManager.updateDB(str);
				System.out.println("\nClase creadas con éxito: " + clas);
			}
			connectionManager.close();

		}catch(Exception e){
			System.err.println("Ha ocurrido un error al crear la clase: "+e.getLocalizedMessage() );
		}

		return clas;
	}

	@Override
	public void editarClase(Clase clase) {
		try{
			connectionManager.connect();
			String str = "UPDATE CLASE "+
						 "SET id_practicas = "+clase.getId_clase()+", "+
						 "SET id_actividad = "+clase.getId_actividad()+", "+
						 "SET puntuable = "+clase.isPuntuable()+", "+
						 " WHERE id_clase =" +clase.getId_clase()+")";
			connectionManager.updateDB(str);
			System.out.println("\nClase editado con exito: " + clase);
			connectionManager.close();

		}catch(Exception e){
			System.err.println("Ha ocurrido un error al editar el clase: "+e.getLocalizedMessage() );
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
			ResultSet claseResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, CLASE C where A.id_asignatura = '"+asignatura.getId_asignatura()+"' AND A.id_actividad = C.id_actividad  AND A.finalizada = FALSE  AND A.para_despues = FALSE");
			connectionManager.close();

			while(claseResultSet.next()){
				Clase clase = obtenerInformacionDeClase(claseResultSet.getInt("id_clase"));
				listaActividades.add( clase );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las activdades-clase de la asignatura :" + asignatura + " -> "  + e.getLocalizedMessage());
		}

		return listaActividades;
	}

	@Override
	public List<Actividad> obtenerTodasActividades() {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet claseResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, CLASE C where A.id_actividad = C.id_actividad  AND A.finalizada = FALSE  AND A.para_despues = FALSE");
			connectionManager.close();

			while(claseResultSet.next()){
				Clase clase = obtenerInformacionDeClase(claseResultSet.getInt("id_clase"));
				listaActividades.add( clase );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las activdades-clase -> "  + e.getLocalizedMessage());
		}

		return listaActividades;
	}

	@Override
	public List<Actividad> obtenerActividadesHoy() {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet claseResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, CLASE C WHERE A.id_actividad = C.id_actividad AND A.fecha_finalizacion = '" + Date_solver.convertirLocalDateEnSQL(Date_solver.fechaDeHoy())+ "' AND A.finalizada = FALSE AND A.para_despues = FALSE");
			connectionManager.close();

			while(claseResultSet.next()){
				Clase clase = obtenerInformacionDeClase(claseResultSet.getInt("id_clase"));
				listaActividades.add( clase );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las todas activdades-clase -> "  + e.getLocalizedMessage());
		}
		return listaActividades;
	}

	@Override
	public List<Actividad> obtenerActividadesParaDespues() {
		List<Actividad> listaActividades = new ArrayList<Actividad>();
		try{
			connectionManager.connect();
			ResultSet claseResultSet = connectionManager.queryDB("SELECT * from ACTIVIDAD A, CLASE C WHERE A.id_actividad = C.id_actividad AND A.para_despues = TRUE AND A.finalizada = FALSE ");
			connectionManager.close();

			while(claseResultSet.next()){
				Clase clase = obtenerInformacionDeClase(claseResultSet.getInt("id_clase"));
				listaActividades.add( clase );
			}

		}catch (Exception e){
			System.err.println("\nError al recuperar las todas activdades-clase -> "  + e.getLocalizedMessage());
		}
		return listaActividades;
	}


}
