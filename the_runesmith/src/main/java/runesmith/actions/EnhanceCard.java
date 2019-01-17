package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

import runesmith.helpers.AdditionalCardDescriptions;
import runesmith.patches.EnhanceCountField;

public abstract class EnhanceCard {
	
	public static void enhance(AbstractCard c) {
		//add 1 to enhance counter
		EnhanceCountField.enhanceCount.set(c,EnhanceCountField.enhanceCount.get(c) + 1);
		int currentEnhance = EnhanceCountField.enhanceCount.get(c);
		if(currentEnhance > 10) {
			EnhanceCountField.enhanceCount.set(c,10);
		}
		AdditionalCardDescriptions.modifyDescription(c);
//		if(currentEnhance == 1)	c.rawDescription += "NL (Enhanced "+currentEnhance+" time.)";
//		else c.rawDescription +=  "NL (Enhanced "+currentEnhance+" times.)";
//		c.initializeDescription();
	}
}
