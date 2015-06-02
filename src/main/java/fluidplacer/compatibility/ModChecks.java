package fluidplacer.compatibility;

import cpw.mods.fml.common.Loader;

public class ModChecks {

	private ModChecks() {}
	
	private static Boolean hasBotania;
	
	public static boolean hasBotania() {
		Boolean flag = hasBotania;
		if(flag == null) {
			flag = hasBotania = Loader.isModLoaded("Botania");
		}
		return flag;
	}
	
}
