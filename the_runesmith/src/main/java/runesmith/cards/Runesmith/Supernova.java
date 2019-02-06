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
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.DudRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class Supernova extends CustomCard {
    public static final String ID = "Runesmith:Supernova";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Supernova.png"; //need some img
    private static final int COST = 1;
    private static final int UPG_COST = 0;
    private static final int SKILL_AMT = 1;

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
        if (p.orbs.size() == 0) return;

        int count = 0;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof RuneOrb) {
                RuneOrb r = (RuneOrb) o;
                if (!(r instanceof DudRune))
                    count++;
                AbstractDungeon.actionManager.addToBottom(
                        new BreakRuneAction(r)
                );
            }
        }
        if (count == 0) return;

        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, SKILL_AMT * count));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(SKILL_AMT * count));
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
}