package runesmith;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import basemod.BaseMod;
import runesmith.patches.AbstractCardEnum;


@SpireInitializer
public class RunesmithMod {
	
	private static Color BEIGE = new Color(245f/255f, 245f/255f, 220f/255f, 1f);

	public RunesmithMod() {
		
	}
		
	public static void initialize() {
		BaseMod.addColor(
				AbstractCardEnum.RUNESMITH_BEIGE,
				BEIGE,
				"", //attackBg
				"", //skillBg
				"", //powerBg
				"", //energyOrb
				"", //attackBgPortrait
				"", //skillBgPortrait
				"", //powerBgPortrait
				"", //energyOrbPortrait
				""  ); //cardEnergyOrb
		new RunesmithMod();
	}
}
