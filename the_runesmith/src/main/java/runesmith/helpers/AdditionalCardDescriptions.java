package runesmith.helpers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;

import runesmith.RunesmithMod;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.EnhanceCountField;

public abstract class AdditionalCardDescriptions {
	public static final Logger logger = LogManager.getLogger(AdditionalCardDescriptions.class.getName());
	
	private static final UIStrings uiStrings1 = CardCrawlGame.languagePack.getUIString("Runesmith:StasisAction");
	public static final String[] STASIS_TEXT = uiStrings1.TEXT;
	
	private static final UIStrings uiStrings2 = CardCrawlGame.languagePack.getUIString("Runesmith:CardEnhanced");
	public static final String[] ENHANCE_TEXT = uiStrings2.TEXT;
	
	
	public static void modifyDescription(AbstractCard c) {
		String addString = "";
		
		logger.info("now modifying: "+c.rawDescription);
		logger.info("enhanceCount: "+EnhanceCountField.enhanceCount.get(c));
		if(!EnhanceCountField.enhanceReset.get(c)) {
			if(EnhanceCountField.enhanceCount.get(c)!=0) {
				if(EnhanceCountField.enhanceCount.get(c)==1) {
					if(!c.rawDescription.contains(" "+ENHANCE_TEXT[0]+". ")) {
						addString = addString+" "+ENHANCE_TEXT[0]+". ";
					}
				}else{
					c.rawDescription = c.rawDescription.replace(" "+ENHANCE_TEXT[0]+".", "");
					c.rawDescription = c.rawDescription.replace(" "+ENHANCE_TEXT[0]+" +"+(EnhanceCountField.enhanceCount.get(c)-1)+".", "");
					logger.info("replacing: "+" "+ENHANCE_TEXT[0]+".");
					addString = addString+" "+ENHANCE_TEXT[0]+" +"+EnhanceCountField.enhanceCount.get(c)+".";
				}
			}
		}else{
			c.rawDescription = c.rawDescription.replace(" "+ENHANCE_TEXT[0]+".", "");
			c.rawDescription = c.rawDescription.replace(" "+ENHANCE_TEXT[0]+" +"+EnhanceCountField.enhanceCount.get(c)+".", "");
			c.rawDescription = c.rawDescription.replace(addString, "");
		}
		
		if(!CardStasisStatus.isStatis.get(c)) {
			logger.info("deleting stasis text");
			c.rawDescription = c.rawDescription.replace(" "+STASIS_TEXT[0]+".", "");
		}else if(!c.rawDescription.contains(" "+STASIS_TEXT[0]+".")) {
			addString = addString+" "+STASIS_TEXT[0]+".";
		}
		c.rawDescription = c.rawDescription + addString;
		c.initializeDescription();
	}
}
