package Util;

import javafx.scene.paint.Color;

public class Basics {

	public static String RGBToHex(Color color){
		return String.format( "#%02X%02X%02X",
	            (int)( color.getRed() * 255 ),
	            (int)( color.getGreen() * 255 ),
	            (int)( color.getBlue() * 255 ) );
	}
}
