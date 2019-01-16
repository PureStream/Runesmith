package runesmith.patches;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.cards.DamageInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.ReflectionHacks;
import basemod.interfaces.OnCardUseSubscriber;
import runesmith.cards.Runesmith.AbstractRunicCard;


public class EnhancedCardValueModified {
	public static final Logger logger = LogManager.getLogger(EnhancedCardValueModified.class.getName());
			
	@SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method="applyPowers")
    public static class applyPowers {
        public static void Postfix(AbstractCard self)
        {
//        	logger.info("patching enhance");
        	if(EnhanceCountField.enhanceCount.get(self)!=0) {

	            int tmp = self.damage;
	            
	            self.damage = self.damage + MathUtils.floor((float) (self.damage * (Math.pow(0.5F,EnhanceCountField.enhanceCount.get(self)))));
	            if (self.damage != tmp) {
	                self.isDamageModified = true;
	            }
	            
	            if(self instanceof AbstractRunicCard) {
	            	int tmp2 = ((AbstractRunicCard) self).potency;
	            	((AbstractRunicCard) self).potency = tmp2 + MathUtils.floor((float) (tmp2 * (Math.pow(0.5F,EnhanceCountField.enhanceCount.get(self)))));
	            	if(tmp2 != ((AbstractRunicCard) self).potency) {
	            		((AbstractRunicCard) self).isPotencyModified = true;
	            	}
	            }
        	}
        }
    }
	
//	@SpirePatch(clz = "AbstractCard.class", method = "enhance"){
//		public static class enhance{
//			public static void
//		}
//	}

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method="applyPowersToBlock")
    public static class applyPowersToBlock {
        public static void Postfix(AbstractCard self)
        {
        	if(EnhanceCountField.enhanceCount.get(self)!=0) {
        	
	            int tmp = self.block;
	            self.block = self.block+MathUtils.floor((float) (self.block * (Math.pow(0.5F,EnhanceCountField.enhanceCount.get(self)))));
	            if (self.block != tmp) {
	                self.isBlockModified = true;
	            }
        	}
        }
    }
    
    

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method="calculateCardDamage")
    public static class calculateCardDamage {
        public static void Postfix(AbstractCard self, AbstractMonster mo)
        {
        	if(EnhanceCountField.enhanceCount.get(self)==0) {
	        	int tmp = self.damage;
	        	self.damage = self.damage+MathUtils.floor((float) (self.damage * (Math.pow(0.5F,EnhanceCountField.enhanceCount.get(self)))));
	
	            if ((boolean)ReflectionHacks.getPrivate(self, AbstractCard.class, "isMultiDamage")) {
	                for (int i = 0; i < self.multiDamage.length; i++) {
	                    self.multiDamage[i] = self.multiDamage[i]+MathUtils.floor((float) (self.multiDamage[i] * (Math.pow(0.5F,EnhanceCountField.enhanceCount.get(self)))));
	                }
	            }
	
	            if (self.damage != tmp) {
	                self.isDamageModified = true;
	            }
        	}
        }
    }
    
//    Reset enhance counter on card usage
//    @SpirePatch(
//    		clz=AbstractPlayer.class,
//    		method="useCard",
//    		paramtypez={
//    			AbstractCard.class,
//    			AbstractMonster.class,
//    			int.class
//    		}
//    	)
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.characters.AbstractPlayer", method="useCard")
    public static class enhanceRmv{
    	public static void PostFix(AbstractPlayer self, AbstractCard card, AbstractCreature target, int energyOnUse) {
    		//Reset Enhance counter on card use
    		EnhanceCountField.enhanceCount.set(card,0);
    	}
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method="resetAttributes")
	public static class resetAttributes {
    	public static SpireReturn Prefix(AbstractCard self) {
    	// Check required for Compendium
			if (AbstractDungeon.player != null) {

				
				self.block = self.baseBlock;
				self.isBlockModified = false;
				self.damage = self.baseDamage;
				self.isDamageModified = false;
				self.magicNumber = self.baseMagicNumber;
				self.isMagicNumberModified = false;
				self.damageTypeForTurn = (DamageInfo.DamageType)ReflectionHacks.getPrivate(self, AbstractCard.class, "damageType");
				if(self instanceof AbstractRunicCard) {
					((AbstractRunicCard) self).potency = ((AbstractRunicCard) self).basePotency;
					((AbstractRunicCard) self).isPotencyModified = false;
				}
				
//	            if(self instanceof AbstractRunicCard) {
//	            	int tmp2 = ((AbstractRunicCard) self).potency;
//	            	((AbstractRunicCard) self).potency = tmp2 + MathUtils.floor(tmp2 * (0.5F * EnhanceCountField.enhanceCount.get(self)));
//	            	if(tmp2 != ((AbstractRunicCard) self).potency) {
//	            		((AbstractRunicCard) self).isPotencyModified = true;
//	            	}
//	            }
			}
			
			return SpireReturn.Continue();
		}
    }

//	@Override
//	public void receiveCardUsed(AbstractCard card) {
//		EnhanceCountField.enhanceCount.set(card,0);
//	}
    
    
}