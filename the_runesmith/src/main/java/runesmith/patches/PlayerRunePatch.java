package runesmith.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import runesmith.actions.runes.TriggerEndOfTurnRunesAction;
import runesmith.cards.Runesmith.BreakCard;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;

import java.util.ArrayList;

public class PlayerRunePatch {

    @SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class combatUpdate{

        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            for(RuneOrb rune : playerRune.runes){
                rune.update();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "update")
    public static class update{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            for(RuneOrb rune : playerRune.runes){
                rune.updateAnimation();
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "updateInput")
    public static class updateInput{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self){
            if(self.hoveredCard instanceof BreakCard){
                PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
                if(((BreakCard) self.hoveredCard).showAllBreakValues()){
                    for(RuneOrb rune : playerRune.runes){
                        rune.showEvokeValue();
                    }
                }else{
                    int loc = ((BreakCard) self.hoveredCard).showBreakValueAt();
                    if(loc >= 0 && playerRune.runes.size() > 0)
                        playerRune.runes.get(loc).showEvokeValue();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "showEvokeValue");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "manuallySelectCard")
    public static class manuallySelectCard{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self, AbstractCard card){
            if(self.hoveredCard instanceof BreakCard){
                PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
                if(((BreakCard) self.hoveredCard).showAllBreakValues()){
                    for(RuneOrb rune : playerRune.runes){
                        rune.showEvokeValue();
                    }
                }else{
                    int loc = ((BreakCard) self.hoveredCard).showBreakValueAt();
                    if(loc >= 0 && playerRune.runes.size() > 0)
                        playerRune.runes.get(loc).showEvokeValue();
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "showEvokeValue");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "releaseCard")
    public static class releaseCard{
        public static void Prefix(AbstractPlayer self){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            for(RuneOrb rune : playerRune.runes){
                rune.hideEvokeValues();
            }
        }
    }

//    @SpirePatch(clz = CardGroup.class, method = "refreshHandLayout")
//    public static class refreshHandLayout{
//        @SpireInsertPatch(locator = Locator.class)
//        public static void Insert(CardGroup __instance){
//
//            AbstractPlayer p = AbstractDungeon.player;
//            if(p != null) {
//                PlayerRune playerRune = PlayerRuneField.playerRune.get(p);
//                for (RuneOrb rune : playerRune.runes) {
//                    rune.hideEvokeValues();
//                }
//            }
//        }
//
//        private static class Locator extends SpireInsertLocator {
//            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//                ArrayList<Matcher> preMatchers = new ArrayList<>();
//
//                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");
//
//                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
//            }
//        }
//    }

    @SpirePatch(clz = AbstractPlayer.class, method = "preBattlePrep")
    public static class preBattlePrep{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self){
            PlayerRuneField.playerRune.get(self).preBattlePrep();
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "render")
    public static class render{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self, SpriteBatch sb){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            for(RuneOrb rune : playerRune.runes){
                rune.render(sb);
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = GameActionManager.class, method = "callEndOfTurnActions")
    public static class callEndOfTurnActions{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(GameActionManager __instance){
            AbstractDungeon.actionManager.addToBottom(new TriggerEndOfTurnRunesAction());
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "actionManager");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }


    @SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfTurnOrbs")
    public static class applyStartOfTurnOrbs{
        public static void Postfix(AbstractPlayer self){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            playerRune.applyStartOfTurnRunes();
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "increaseMaxOrbSlots")
    public static class increaseMaxOrbSlots{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self, int amount, boolean playSfx){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            for(int i = 0; i < playerRune.runes.size() ; i++){
                playerRune.runes.get(i).setSlot(i, playerRune.runes.size());
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();
                preMatchers.add(new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs"));

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }

    @SpirePatch(clz = AbstractPlayer.class, method = "decreaseMaxOrbSlots")
    public static class decreaseMaxOrbSlots{
        @SpireInsertPatch(locator = Locator.class)
        public static void Insert(AbstractPlayer self, int amount){
            PlayerRune playerRune = PlayerRuneField.playerRune.get(self);
            for(int i = 0; i < playerRune.runes.size() ; i++){
                playerRune.runes.get(i).setSlot(i, playerRune.runes.size());
            }
        }

        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                ArrayList<Matcher> preMatchers = new ArrayList<>();

                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractPlayer.class, "orbs");

                return LineFinder.findInOrder(ctMethodToPatch, preMatchers, finalMatcher);
            }
        }
    }
}
