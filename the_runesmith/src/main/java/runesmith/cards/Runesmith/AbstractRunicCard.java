package runesmith.cards.Runesmith;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.red.HeavyBlade;
import com.megacrit.cardcrawl.cards.red.PerfectedStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import runesmith.RunesmithMod;
import runesmith.patches.EnhanceCountField;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

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
	
	public void upgradePotency(int amount) {
		this.basePotency += amount; 
		this.potency = this.basePotency + getPotentialBonus();
		this.potency = this.potency + MathUtils.floor(this.potency * (0.5F * EnhanceCountField.enhanceCount.get(this)));
		if(this.potency > this.basePotency || amount>0 || this.potency < this.basePotency) isPotencyModified = true;
	}
	
	public int getPotentialBonus() {
		AbstractPlayer p = AbstractDungeon.player;
		if (p != null) {
			if (p.hasPower("PotentialPower")) {
				return p.getPower("PotentialPower").amount;
			}
		}
		return 0;
	}
	
	public boolean checkElements(int ignis, int terra, int aqua) {
		Logger logger = LogManager.getLogger(RunesmithMod.class.getName());
		logger.info("Start checking elements.");
		AbstractPlayer p = AbstractDungeon.player;
		
		if (freeToPlayOnce == true) return true;
		
		int pIgnis = 0, pTerra = 0, pAqua = 0;
		if (p.hasPower("IgnisPower")) {
			pIgnis = p.getPower("IgnisPower").amount;
		}
		if (p.hasPower("TerraPower")) {
			pTerra = p.getPower("TerraPower").amount;
		}
		if (p.hasPower("AquaPower")) {
			pAqua = p.getPower("AquaPower").amount;
		}
		if (pIgnis >= ignis && pTerra >= terra && pAqua >= aqua) {
			logger.info("Have enough elements.");
			if (pIgnis > 0 && ignis > 0) {
				p.getPower("IgnisPower").reducePower(ignis);
				if (p.getPower("IgnisPower").amount == 0)
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "IgnisPower"));
			}
			if (pTerra > 0 && terra > 0) {
				p.getPower("TerraPower").reducePower(terra);
				if (p.getPower("TerraPower").amount == 0)
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "TerraPower"));
			}
			if (pAqua > 0 && aqua > 0) {
				p.getPower("AquaPower").reducePower(aqua);
				if (p.getPower("AquaPower").amount == 0)
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "AquaPower"));
			}
			return true;
		}
		logger.info("Not enough elements.");
		if (ignis != 0) {
			addPower("IgnisPower",ignis);
		}
		if (terra != 0) {
			addPower("TerraPower",terra);
		}
		if (aqua != 0) {
			addPower("AquaPower",aqua);
		}
		return false;
	}
	
	public void addPower(String element, int value) {
		AbstractPlayer p = AbstractDungeon.player;
		if (element.equals("IgnisPower")) {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              p,
			              p,
			              new IgnisPower(p, value),
			              value
			          )
			      );
		}
		else if (element.equals("TerraPower")) {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              p,
			              p,
			              new TerraPower(p, value),
			              value
			          )
			      );
		}
		else {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              p,
			              p,
			              new AquaPower(p, value),
			              value
			          )
			      );
		}
	}
}
