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
	
	private static final String RUNESMITH_BUTTON = "images/character/runesmithButton.png";
	private static final String RUNESMITH_PORTRAIT = "images/character/runesmithPortrait.png";
	private static Color BEIGE = new Color(245f/255f, 245f/255f, 220f/255f, 1f);

	public RunesmithMod() {
		
	}
		
	public static void initialize() {
		BaseMod.addColor(
				AbstractCardEnum.RUNESMITH_BEIGE,
				BEIGE,
				"images/cardui/512/bg_attack_beige.png", //attackBg
				"images/cardui/512/bg_skill_beige.png", //skillBg
				"images/cardui/512/bg_power_beige.png", //powerBg
				"images/cardui/512/card_beige_orb.png", //energyOrb
				"images/cardui/1024/bg_attack_beige.png", //attackBgPortrait
				"images/cardui/1024/bg_skill_beige.png", //skillBgPortrait
				"images/cardui/1024/bg_power_beige.png", //powerBgPortrait
				"images/cardui/1024/card_beige_orb", //energyOrbPortrait
				"images/cardui/description_beige_orb.png"  ); //cardEnergyOrb
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
