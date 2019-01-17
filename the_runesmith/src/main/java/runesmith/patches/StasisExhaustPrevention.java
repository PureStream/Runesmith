package runesmith.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.CardDarkFlashVfx;

import runesmith.RunesmithMod;

public class StasisExhaustPrevention {
	public static final Logger logger = LogManager.getLogger(EnhancedCardValueModified.class.getName());
	
	@SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
	public static class preventExhaust {
		public static void Prefix(CardGroup self, AbstractCard c) {
			if(CardStasisStatus.isStatis.get(c)) {
				AbstractDungeon.effectList.add(new CardDarkFlashVfx(c, RunesmithMod.BEIGE));
				CardStasisStatus.isStatis.set(c, false);
				return;
			}
		}
	}
}
