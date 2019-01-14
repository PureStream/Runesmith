package runesmith;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.CardCrawlGame;

import basemod.BaseMod;
import basemod.interfaces.EditCharactersSubscriber;
import runesmith.character.player.RunesmithCharacter;
import runesmith.patches.AbstractCardEnum;
import runesmith.patches.PlayerClassEnum;


@SpireInitializer
public class RunesmithMod implements EditCharactersSubscriber{
	
	private static final String RUNESMITH_BUTTON = null;
	private static final String RUNESMITH_PORTRAIT = null;
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
	
	@Override
	public void receiveEditCharacters() {
		BaseMod.addCharacter(new RunesmithCharacter(CardCrawlGame.playerName),
				RUNESMITH_BUTTON,
				RUNESMITH_PORTRAIT,
				PlayerClassEnum.RUNESMITH_CLASS);
	}
}
