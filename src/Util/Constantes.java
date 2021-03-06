package Util;

public class Constantes {
	//nombre de la aplicación
	public final static String ARDUM = "Ardum";
	public static final String ATELIEROMEGA ="src/resources/fonts/AtelierOmega.ttf"; //10

	//database name
	public static final String DATABASE = "ardumBD";
	
	public static final String EDIT = "Editar";
	
	//generico
	public static final String ACTIVIDADES_PARA = "Actividades por hacer para ";
	public static final String BANDEJA = "Bandeja de entrada ";
	public static final String BANDEJA_HOY = "Lo que tienes para hoy";
	public static final String BANDEJA_PARA_DESPUES = "Ya lo harás después... ";

	//fonts and id of fonts in switch
	public static final String ROBOTOREGULAR ="src/resources/fonts/Roboto-Regular.ttf"; //1
	public static final String ROBOTOBOLD ="src/resources/fonts/Roboto-Bold.ttf"; //2
	public static final String ROBOTOLIGHT ="src/resources/fonts/Roboto-Light.ttf"; //3
	public static final String ROBOTOTHIN ="src/resources/fonts/Roboto-Thin.ttf"; //4
	public static final String ROBOTOCONDENSEDLIGHT ="src/resources/fonts/RobotoCondensed-Light.ttf"; //5
	public static final String ROBOTOCONDENSEDREGULAR ="src/resources/fonts/RobotoCondensed-Regular.ttf"; //6
	public static final String ROBOTOSLABBOLD ="src/resources/fonts/RobotoSlab-Bold.ttf"; //7
	public static final String ROBOTOSLABLIGHT ="src/resources/fonts/RobotoSlab-Light.ttf"; //8
	public static final String ROBOTOSLABREGULAR ="src/resources/fonts/RobotoSlab-Regular.ttf"; //9
	public static final String ROBOTOSLABTHIN ="src/resources/fonts/RobotoSlab-Thin.ttf"; //10
	
	//Nombre secuencias de identificadores para tablas en bbdd
	public static final String USUARIO_SQ = "PUBLIC.USUARIO_SQ";
	public static final String UNIVERSIDAD_SQ = "PUBLIC.UNIVERSIDAD_SQ";
	public static final String GRADO_SQ = "PUBLIC.GRADO_SQ";
	public static final String CURSO_SQ = "PUBLIC.CURSO_SQ";
	public static final String ASIGNATURA_SQ = "PUBLIC.ASIGNATURA_SQ";
	public static final String CUATRIMESTRE_SQ = "PUBLIC.CUATRIMESTRE_SQ";
	public static final String ACTIVIDAD_SQ = "PUBLIC.ACTIVIDAD_SQ";
	public static final String PRACTICAS_SQ = "PUBLIC.PRACTICAS_SQ";
	public static final String CLASE_SQ = "PUBLIC.CLASE_SQ";
	public static final String EXAMEN_SQ = "PUBLIC.EXAMEN_SQ";
	public static final String NOTIFICACION_SQ = "PUBLIC.NOTIFICACION_SQ";
	public static final String EXAMEN_CLASE_SQ = "PUBLIC.EXAMEN_CLASE_SQ";
	public static final String EXAMEN_POLIFORMAT_SQ = "PUBLIC.EXAMEN_POLIFORMAT_SQ";
	public static final String EXAMEN_PRACTICA_SQ = "PUBLIC.EXAMEN_PRACTICA_SQ";

	public static String descripcionExamen(){
		String des = null;
		int valor = (int) Math.round(Math.random()*2);
		switch (valor) {
		case 0: des = "Esta pa' mirarselo eh?!..."; break;
		case 1: des = "Bueno, esta pa' una cervecitas y fuera"; break;
		case 2:	des = "Ya es hora de que vayas estudiando... No?"; break;
		}
	return des;
	}
	public static String descripcionClase(){
		String des = null;
		int valor = (int) Math.round(Math.random()*2);
		switch (valor) {
		case 0: des = "Acuerdate de lo de clase que cuenta para nota!!!"; break;
		case 1: des = "Bueno, puede que tampoco sea tan importante, o que?"; break;
		case 2:	des = "Va, ya me lo pasara algun coleguita jeje..."; break;
		}
	return des;
	}
	public static String descripcionPracticas(){
		String des = null;
		int valor = (int) Math.round(Math.random()*2);
		switch (valor) {
		case 0: des = "Lo puedes hacer antes de entrar, vamo a calmarno..."; break;
		case 1: des = "Es que esto tampoco contaba mucho... No?"; break;
		case 2:	des = "Bueno... Ya lo haras jeje"; break;
		}
	return des;
	}
}
