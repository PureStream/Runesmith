package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.patches.EnhanceCountField;

public abstract class EnhanceCard {
	
	public static void enhance(AbstractCard c) {
		enhance(c,1);
	}
	
	public static void enhance(AbstractCard c, int enhanceCounts) {
		//add 1 to enhance counter
		for (int i=0; i<enhanceCounts; i++) 
			doEnhance(c);
		
		if (AbstractDungeon.player.hasPower("Runesmith:PoweredAnvilPower")) {
			doEnhance(c);
			AbstractDungeon.player.getPower("Runesmith:PoweredAnvilPower").flashWithoutSound();
		}
		
	}
	
	private static void doEnhance(AbstractCard c) {
		EnhanceCountField.enhanceCount.set(c,EnhanceCountField.enhanceCount.get(c) + 1);
		int currentEnhance = EnhanceCountField.enhanceCount.get(c);
		if(currentEnhance > 99) {
			EnhanceCountField.enhanceCount.set(c,99);
		}
		if(c instanceof AbstractRunicCard) {
			((AbstractRunicCard) c).upgradePotency(0);
		}
//		AdditionalCardDescriptions.modifyDescription(c);
		c.initializeDescription();
		c.calculateCardDamage(null);
//		if(currentEnhance == 1)	c.rawDescription += "NL (Enhanced "+currentEnhance+" time.)";
//		else c.rawDescription +=  "NL (Enhanced "+currentEnhance+" times.)";
//		c.initializeDescription();
	}
	
	public static boolean canEnhance(AbstractCard c) {
		boolean isPotency = false;
		if (c instanceof AbstractRunicCard)
			if (((AbstractRunicCard)c).basePotency > 0)
				isPotency = true;
		return (c.baseDamage > 0 || c.baseBlock > 0 || isPotency);
//		boolean isNotPotency = true;
//		if (c instanceof AbstractRunicCard)
//			if (((AbstractRunicCard)c).basePotency > 0)
//				isNotPotency = false;
//		boolean isNotEnhanceableType = c.baseDamage == -1 && c.baseBlock == -1 && isNotPotency;
//		return !(c.type == CardType.CURSE || c.type == CardType.STATUS || isNotEnhanceableType);
	}
}
