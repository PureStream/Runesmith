package runesmith.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.abstracts.CustomRelic;
import runesmith.powers.PotentialPower;

public class MiniCore extends CustomRelic {
	
	public static final String ID = "Runesmith:MiniCore";
	private static final String IMG = "images/relics/MiniCore.png"; //<--------- Need some img
	private static final String IMG_O = "images/relics/MiniCore_o.png";
	private static final int POWER_AMT = 1;
	
	public MiniCore() {
		super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	public void atBattleStart() {
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToTop(
				new ApplyPowerAction(p, p, 
						new PotentialPower(p,POWER_AMT),POWER_AMT));
	}

	public AbstractRelic makeCopy() {
		return new MiniCore();
	}
	
}
