package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;

import runesmith.cards.Runesmith.AbstractRunicCard;
import runesmith.patches.EnhanceCountField;

public abstract class EnhanceCard {
	
	public static void enhance(AbstractCard c) {
		//add 1 to enhance counter
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
		return !(c.type == CardType.CURSE || c.type == CardType.STATUS);
	}
}
