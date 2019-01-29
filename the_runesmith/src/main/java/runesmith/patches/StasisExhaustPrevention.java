package runesmith.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.helpers.AdditionalCardDescriptions;
import runesmith.vfx.ShowStasisCardAndAddToDiscardEffect;

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
	public static class preventExhaust{
		public static SpireReturn Prefix(CardGroup self, AbstractCard c){
			if(CardStasisStatus.isStasis.get(c)) {
				//logger.info("Attempting exhaust prevention");
				CardStasisStatus.isStasis.set(c, false);
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
				c.initializeDescription();
				AbstractDungeon.effectList.add(new ShowStasisCardAndAddToDiscardEffect(c));
				return SpireReturn.Return(null);
			}
			return SpireReturn.Continue();
		}

	}

	/*
	@SpirePatch(clz = CardGroup.class, method = "moveToExhaustPile")
	public static class preventExhaust {
		public static void Replace(CardGroup self, AbstractCard c) {
			if(CardStasisStatus.isStasis.get(c)) {
				//logger.info("Attempting exhaust prevention");
				CardStasisStatus.isStasis.set(c, false);				
				if (AbstractDungeon.player.hoveredCard == c) {
					AbstractDungeon.player.releaseCard();
				}
				AbstractDungeon.actionManager.removeFromQueue(c);
				c.unhover();
				c.untip();
				c.stopGlowing();
				self.group.remove(c);
				//update card description
				c.initializeDescription();
//				AdditionalCardDescriptions.modifyDescription(c);
//				AbstractDungeon.actionManager.addToBottom(new VFXAction(new CardDarkFlashVfx(c), 0.1F));
//				AbstractDungeon.player.discardPile.addToTop(c);
				AbstractDungeon.effectList.add(new ShowStasisCardAndAddToDiscardEffect(c));
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
	}	*/
}
