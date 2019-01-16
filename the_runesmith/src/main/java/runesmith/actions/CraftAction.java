package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.orbs.FirestoneRune;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.TerraPower;

public class CraftAction extends AbstractGameAction {
	
	private int ignis, terra, aqua, potency;
	private String runeID;
	
	public CraftAction(AbstractPlayer p, String runeID, int Ignis, int Terra, int Aqua, int potency) {
		this.duration = Settings.ACTION_DUR_FAST;
		this.source = p;
		this.ignis = Ignis;
		this.terra = Terra;
		this.aqua = Aqua;
		this.potency = potency;
		
	}

	@Override
	public void update() {
		int pIgnis = 0, pTerra = 0, pAqua = 0;
		if (source.hasPower("IgnisPower")) {
			pIgnis = source.getPower("IgnisPower").amount;
		}
		if (source.hasPower("TerraPower")) {
			pIgnis = source.getPower("TerraPower").amount;
		}
		if (source.hasPower("AquaPower")) {
			pAqua = source.getPower("AquaPower").amount;
		}
		if (pIgnis >= ignis && pTerra >= terra && pAqua >= aqua) {
			
			runeSelction(runeID);
			
			if (pIgnis != 0 && ignis != 0) {
				source.getPower("IgnisPower").reducePower(ignis);
				if (this.source.getPower("IgnisPower").amount == 0)
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "IgnisPower"));
			}
			if (pTerra != 0 && terra != 0) {
				source.getPower("TerraPower").reducePower(terra);
				if (this.source.getPower("TerraPower").amount == 0)
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "TerraPower"));
			}
			if (pAqua != 0 && aqua != 0) {
				source.getPower("AquaPower").reducePower(aqua);
				if (this.source.getPower("AquaPower").amount == 0)
					AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.source, this.source, "AquaPower"));
			}
		}
		else {
			if (ignis != 0) {
				addPower("IgnisPower",ignis);
			}
			if (terra != 0) {
				addPower("TerraPower",terra);
			}
			if (aqua != 0) {
				addPower("AquaPower",aqua);
			}
		}
		
		
	}
	
	public void runeSelction (String id) {
		if (id.equals("Runesmith:CraftFirestone")) {
			AbstractDungeon.actionManager.addToBottom(
					new RuneChannelAction(
							new FirestoneRune(potency)));
		}
		else if (id.equals("")) {
			
		}
	}
	
	public void addPower(String element, int value) {
		if (element.equals("IgnisPower")) {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              source,
			              source,
			              new IgnisPower(source, value),
			              value
			          )
			      );
		}
		else if (element.equals("TerraPower")) {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              source,
			              source,
			              new TerraPower(source, value),
			              value
			          )
			      );
		}
		else {
			AbstractDungeon.actionManager.addToTop(
			          new ApplyPowerAction(
			              source,
			              source,
			              new AquaPower(source, value),
			              value
			          )
			      );
		}
	}

}
