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
	public static final Logger logger = LogManager.getLogger(StasisExhaustPrevention.class.getName());
	
	@SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
	public static class preventExhaust {
		public static void Postfix(CardGroup self, AbstractCard c) {
			if(CardStasisStatus.isStatis.get(c)) {
				logger.info("Attempting exhaust prevention");
				AbstractDungeon.effectList.add(new CardDarkFlashVfx(c, RunesmithMod.BEIGE));
				CardStasisStatus.isStatis.set(c, false);
				AbstractDungeon.player.exhaustPile.removeTopCard();
				AbstractDungeon.player.discardPile.addToTop(c);
				return;
			}
		}
	}
}
