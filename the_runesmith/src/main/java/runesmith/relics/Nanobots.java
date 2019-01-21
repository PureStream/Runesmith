package runesmith.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.abstracts.CustomRelic;
import runesmith.actions.cards.GrandSlamAction;

public class Nanobots extends CustomRelic {
	
	public static final String ID = "Runesmith:Nanobots";
	private static final String IMG = "images/relics/Nanobots.png"; //<--------- Need some img
	
	public Nanobots() {
		super(ID, ImageMaster.loadImage(IMG), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToTop(
				new GrandSlamAction());
	}

	public AbstractRelic makeCopy() {
		return new Nanobots();
	}
	
}
