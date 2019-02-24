package runesmith.patches;

import basemod.ReflectionHacks;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.cards.Runesmith.AbstractRunicCard;

import java.util.ArrayList;
import java.util.Arrays;


public class EnhancedCardValueModified {
    public static final Logger logger = LogManager.getLogger(EnhancedCardValueModified.class.getName());

//    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "applyPowers")
//    public static class applyPowers {
//        public static void Postfix(AbstractCard self) {
////        	logger.info("patching enhance");
//            if (EnhanceCountField.enhanceCount.get(self) != 0) {
//
//                int tmp = self.damage;
////	            self.damage = (int) Math.floor((self.baseDamage * (Math.pow(1.5,EnhanceCountField.enhanceCount.get(self)))));
//                self.damage = self.damage + MathUtils.floor(self.damage * (0.5F * EnhanceCountField.enhanceCount.get(self)));
//                if (self.damage != tmp) {
//                    self.isDamageModified = true;
//                }
//
//            }
//        }
//    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "applyPowers")
    public static class applyPowers {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard self, @ByRef float[] tmp) {
            if (EnhanceCountField.enhanceCount.get(self) != 0) {

//                int tmp = self.damage;
//	            self.damage = (int) Math.floor((self.baseDamage * (Math.pow(1.5,EnhanceCountField.enhanceCount.get(self)))));
                tmp[0] = tmp[0] + tmp[0] * (0.5F * EnhanceCountField.enhanceCount.get(self));
                if (self.baseDamage != (int)tmp[0]) {
                    self.isDamageModified = true;
                }

            }
        }

        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException{
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageFinalGive");
                ArrayList<Matcher> preMatchers = new ArrayList<>();
//                preMatchers.add(new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers"));
//                preMatchers.add(new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers"));
//                preMatchers.add(new Matcher.FieldAccessMatcher(AbstractPlayer.class, "powers"));

                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
//                int[] line = LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
//                line[0]-=2;
                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }


    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "applyPowers")
    public static class applyPowersMulti {
        @SpireInsertPatch(locator = MultiLocator.class, localvars = {"tmp"})
        public static void InsertMulti(AbstractCard self, float[] tmp) {
            if (EnhanceCountField.enhanceCount.get(self) != 0) {

                for(int i = 0; i < tmp.length; i++) {
                    tmp[i] = tmp[i] + tmp[i] * (0.5F * EnhanceCountField.enhanceCount.get(self));
                    if (self.baseDamage != (int)tmp[i]) {
                        self.isDamageModified = true;
                    }
                }

            }
        }

        private static class MultiLocator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException{
                ArrayList<Matcher> preMatchers = new ArrayList<>();
                preMatchers.add(new Matcher.MethodCallMatcher(MathUtils.class, "floor"));

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "multiDamage");
                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "applyPowersToBlock")
    public static class applyPowersToBlock {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard self, @ByRef float[] tmp) {
            if (EnhanceCountField.enhanceCount.get(self) != 0) {
                tmp[0] = tmp[0] + tmp[0] * (0.5F * EnhanceCountField.enhanceCount.get(self));
                if (self.baseBlock != (int)tmp[0]) {
                    self.isBlockModified = true;
                }
            }
        }

        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException{
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageFinalGive");
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, preMatchers, finalMatcher)[1]};
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "calculateCardDamage")
    public static class calculateDamageResetEnhance {
        public static void Postfix(AbstractCard self, AbstractMonster mo) {
            if (EnhanceCountField.enhanceReset.get(self)) {
                EnhanceCountField.enhanceCount.set(self, 0);
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "calculateCardDamage")
    public static class calculateCardDamage {
        @SpireInsertPatch(locator = Locator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard self, AbstractMonster mo, @ByRef float[] tmp) {
            if (EnhanceCountField.enhanceCount.get(self) != 0) {

                tmp[0] = tmp[0] + tmp[0] * (0.5F * EnhanceCountField.enhanceCount.get(self));

            }
        }

        private static class Locator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException{
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractPower.class, "atDamageFinalGive");
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "powers");

                return new int[]{LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher)[0]-1};
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "calculateCardDamage")
    public static class calculateCardDamageMulti {

        @SpireInsertPatch(locator = MultiLocator.class, localvars = {"tmp"})
        public static void Insert(AbstractCard self, AbstractMonster mo, float[] tmp) {
            if (EnhanceCountField.enhanceCount.get(self) != 0) {
                for(int i = 0; i < tmp.length; i++)
                    tmp[i] = tmp[i] + tmp[i] * (0.5F * EnhanceCountField.enhanceCount.get(self));

            }
        }

        private static class MultiLocator extends SpireInsertLocator{
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException{
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "powers");

                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, preMatchers, finalMatcher)[2]-1};
            }
        }
    }

    @SpirePatch(cls = "com.megacrit.cardcrawl.cards.AbstractCard", method = "resetAttributes")
    public static class resetAttributes {
        public static SpireReturn Prefix(AbstractCard self) {
            // Check required for Compendium
            if (AbstractDungeon.player != null) {
                self.block = self.baseBlock;
                self.isBlockModified = false;
                self.damage = self.baseDamage;
                self.isDamageModified = false;
                if (self instanceof AbstractRunicCard) {
                    ((AbstractRunicCard) self).potency = ((AbstractRunicCard) self).basePotency;
                    ((AbstractRunicCard) self).isPotencyModified = false;
                }
                self.magicNumber = self.baseMagicNumber;
                self.isMagicNumberModified = false;
                self.damageTypeForTurn = (DamageInfo.DamageType) ReflectionHacks.getPrivate(self, AbstractCard.class, "damageType");
                EnhanceCountField.enhanceReset.set(self, false);
                EnhanceCountField.lastEnhance.set(self, 0);


//                logger.info("reset attributes");

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
}
