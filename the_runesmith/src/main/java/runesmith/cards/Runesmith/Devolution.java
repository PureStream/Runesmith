package runesmith.cards.Runesmith;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.SetDontTriggerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.actions.DowngradeEntireHandAction;

public class Devolution extends CustomCard {
    public static final String ID = "Runesmith:Devolution";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "images/cards/Devolution.png";
    private static final int COST = -2;

    public Devolution() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.CURSE,
                CardColor.CURSE,
                CardRarity.CURSE,
                CardTarget.NONE
        );
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((!this.dontTriggerOnUseCard) && (p.hasRelic("Blue Candle")))
            useBlueCandle(p);
        else
            AbstractDungeon.actionManager.addToBottom(new DowngradeEntireHandAction(p));
    }

    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new SetDontTriggerAction(this, false));
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        this.dontTriggerOnUseCard = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, true));
    }

    public AbstractCard makeCopy() {
        return new Devolution();
    }

    public void upgrade() { }

}
