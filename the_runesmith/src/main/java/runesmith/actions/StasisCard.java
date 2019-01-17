package runesmith.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;

import runesmith.helpers.AdditionalCardDescriptions;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

public abstract class StasisCard {
	
	public static void stasis(AbstractCard c) {
		CardStasisStatus.isStatis.set(c, true);
		//update card desc
		AdditionalCardDescriptions.modifyDescription(c);
	}
}
