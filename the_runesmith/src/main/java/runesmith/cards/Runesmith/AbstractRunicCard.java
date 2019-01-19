package runesmith.cards.Runesmith;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import basemod.abstracts.CustomCard;
import runesmith.RunesmithMod;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.EnhanceCountField;

public abstract class AbstractRunicCard extends CustomCard {
	public AbstractRunicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
            CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type,
				color, rarity, target);
	}
	
	public int potency;
	public int basePotency;
	public boolean potencyUpgraded;
	public boolean isPotencyModified;
	
	public void triggerWhenDrawn() {
		this.upgradePotency(0);
	}
	
	@Override
	public void applyPowers() {
		this.upgradePotency(0);
		super.applyPowers();
		
	}
	
	public void upgradePotency(int amount) {
		this.basePotency += amount; 
		this.potency = this.basePotency + getPotentialBonus();
		if (this.potency < 0) this.potency = 0;
		this.potency = this.potency + MathUtils.floor(this.potency * (0.5F * EnhanceCountField.enhanceCount.get(this)));
		if(this.potency > this.basePotency || amount>0 || this.potency < this.basePotency) isPotencyModified = true;
	}
	
	private int getPotentialBonus() {
		AbstractPlayer p = AbstractDungeon.player;
		if (p != null) {
			if (p.hasPower("Runesmith:PotentialPower")) {
				return p.getPower("Runesmith:PotentialPower").amount;
			}
		}
		return 0;
	}
	
	public boolean checkElements(int ignis, int terra, int aqua) {
		return checkElements(ignis, terra, aqua, false);
	}
	
	public boolean checkElements(int ignis, int terra, int aqua, boolean checkOnly) {
		Logger logger = LogManager.getLogger(RunesmithMod.class.getName());
		logger.info("Start checking elements.");
		AbstractPlayer p = AbstractDungeon.player;
		
		if (/*freeToPlayOnce == true || */p.hasPower("Runesmith:UnlimitedPowerPower")) return true;
		
		int pIgnis = 0, pTerra = 0, pAqua = 0;
		if (p.hasPower("Runesmith:IgnisPower")) {
			pIgnis = p.getPower("Runesmith:IgnisPower").amount;
		}
		if (p.hasPower("Runesmith:TerraPower")) {
			pTerra = p.getPower("Runesmith:TerraPower").amount;
		}
		if (p.hasPower("Runesmith:AquaPower")) {
			pAqua = p.getPower("Runesmith:AquaPower").amount;
		}
		if (pIgnis >= ignis && pTerra >= terra && pAqua >= aqua) {
			logger.info("Have enough elements.");
			if(!checkOnly) {
				if (pIgnis > 0 && ignis > 0) {
					p.getPower("Runesmith:IgnisPower").reducePower(ignis);
					if (p.getPower("Runesmith:IgnisPower").amount == 0)
						AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Runesmith:IgnisPower"));
				}
				if (pTerra > 0 && terra > 0) {
					p.getPower("Runesmith:TerraPower").reducePower(terra);
					if (p.getPower("Runesmith:TerraPower").amount == 0)
						AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Runesmith:TerraPower"));
				}
				if (pAqua > 0 && aqua > 0) {
					p.getPower("Runesmith:AquaPower").reducePower(aqua);
					if (p.getPower("Runesmith:AquaPower").amount == 0)
						AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Runesmith:AquaPower"));
				}
			}
			return true;
		}
		logger.info("Not enough elements.");
		if(!checkOnly) {
			AbstractDungeon.actionManager.addToBottom(
					new ApplyElementsPowerAction(p,p,ignis,terra,aqua));
		}
		return false;
	}
	
//	public void addPower(String element, int value) {
//		AbstractPlayer p = AbstractDungeon.player;
//		if (element.equals("IgnisPower")) {
//			AbstractDungeon.actionManager.addToTop(
//			          new ApplyPowerAction(
//			              p,
//			              p,
//			              new IgnisPower(p, value),
//			              value
//			          )
//			      );
//		}
//		else if (element.equals("TerraPower")) {
//			AbstractDungeon.actionManager.addToTop(
//			          new ApplyPowerAction(
//			              p,
//			              p,
//			              new TerraPower(p, value),
//			              value
//			          )
//			      );
//		}
//		else {
//			AbstractDungeon.actionManager.addToTop(
//			          new ApplyPowerAction(
//			              p,
//			              p,
//			              new AquaPower(p, value),
//			              value
//			          )
//			      );
//		}
//	}
}
