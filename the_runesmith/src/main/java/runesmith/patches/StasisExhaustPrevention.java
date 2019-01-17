package runesmith.patches;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.cardManip.CardDarkFlashVfx;
import com.megacrit.cardcrawl.vfx.cardManip.ExhaustCardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import runesmith.RunesmithMod;
import runesmith.helpers.AdditionalCardDescriptions;

public class StasisExhaustPrevention {
	public static final Logger logger = LogManager.getLogger(StasisExhaustPrevention.class.getName());
	
//	@SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
//	public static class preventExhaust {
//		public static void Postfix(CardGroup self, AbstractCard c) {
//			if(CardStasisStatus.isStatis.get(c)) {
//				logger.info("Attempting exhaust prevention");
//				AbstractDungeon.effectList.add(new CardDarkFlashVfx(c, RunesmithMod.BEIGE));
//				CardStasisStatus.isStatis.set(c, false);
//				AbstractDungeon.player.exhaustPile.removeTopCard();
//				AbstractDungeon.player.discardPile.addToTop(c);
//				AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(c));
//				return;
//			}
//		}
//	}
	
	@SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
	public static class preventExhaust {
		public static void Replace(CardGroup self, AbstractCard c) {
			if(CardStasisStatus.isStatis.get(c)) {
				logger.info("Attempting exhaust prevention");
				CardStasisStatus.isStatis.set(c, false);
				//AbstractDungeon.player.discardPile.addToTop(c);
				
				if (AbstractDungeon.player.hoveredCard == c) {
					AbstractDungeon.player.releaseCard();
				}
				AbstractDungeon.actionManager.removeFromQueue(c);
				c.unhover();
				c.untip();
				c.stopGlowing();
				self.group.remove(c);
				//update card description
				AdditionalCardDescriptions.modifyDescription(c);
				AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(c));
				return;
			}
			for (AbstractRelic r : AbstractDungeon.player.relics) {
				r.onExhaust(c);
			}
			for (AbstractPower p : AbstractDungeon.player.powers) {
				p.onExhaust(c);
			}
			c.triggerOnExhaust();
			 
			if (AbstractDungeon.player.hoveredCard == c) {
				AbstractDungeon.player.releaseCard();
			}
			AbstractDungeon.actionManager.removeFromQueue(c);
			c.unhover();
			c.untip();
			c.stopGlowing();
			self.group.remove(c);
			 
			AbstractDungeon.effectList.add(new ExhaustCardEffect(c));
			AbstractDungeon.player.exhaustPile.addToTop(c);
			AbstractDungeon.player.onCardDrawOrDiscard();
		}
	}	
}
