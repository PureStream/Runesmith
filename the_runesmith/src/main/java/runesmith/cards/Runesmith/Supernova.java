package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.DudRune;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;
import runesmith.patches.PlayerRuneField;

import java.util.ArrayList;
import java.util.List;

public class Supernova extends CustomCard implements BreakCard{
    public static final String ID = "Runesmith:Supernova";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "runesmith/images/cards/Supernova.png"; //need some img
    private static final int COST = 1;
    private static final int UPG_COST = 0;

    public Supernova() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.SKILL,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.RARE,
                CardTarget.SELF
        );
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        PlayerRune playerRune = PlayerRuneField.playerRune.get(p);
//        ArrayList<RuneOrb> runes = playerRune.runes;
        if(!playerRune.hasRune())
            return;

        int count = 0;
        for (RuneOrb rune : playerRune.runes) {
            if (!(rune instanceof DudRune))
                count++;
            AbstractDungeon.actionManager.addToBottom(new BreakRuneAction(rune));
        }

        if (count == 0) return;

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, count));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(count));
    }

    public AbstractCard makeCopy() {
        return new Supernova();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(UPG_COST);
        }
    }

    @Override
    public int showBreakValueAt() {
        return 0;
    }

    @Override
    public boolean showAllBreakValues() {
        return true;
    }
}