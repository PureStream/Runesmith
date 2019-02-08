package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import runesmith.actions.BreakRuneAction;
import runesmith.orbs.DudRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class GenerateForcefield extends CustomCard {
    public static final String ID = "Runesmith:GenerateForcefield";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String DESCRIPTION_UPG = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/GenerateForcefield.png"; //need some img
    private static final int COST = -1;

    public GenerateForcefield() {
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
        this.exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int energy = EnergyPanel.totalCount;
        if (upgraded) energy++;
        if (p.hasRelic("Chemical X"))
            energy += 2;
        if (!this.freeToPlayOnce)
            p.energy.use(EnergyPanel.totalCount);

        if (p.orbs.size() == 0 || energy == 0) return;

        int count = 0;
        for (AbstractOrb o : p.orbs) {
            if (o instanceof RuneOrb && !(o instanceof DudRune)) {
                RuneOrb r = (RuneOrb) o;
                count++;
                AbstractDungeon.actionManager.addToBottom(
                        new BreakRuneAction(r)
                );
                if (count == energy) break;
            }
        }
        if (count == 0) return;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new BufferPower(p, count), count));
    }

    public AbstractCard makeCopy() {
        return new GenerateForcefield();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = DESCRIPTION_UPG;
            initializeDescription();
        }
    }
}
