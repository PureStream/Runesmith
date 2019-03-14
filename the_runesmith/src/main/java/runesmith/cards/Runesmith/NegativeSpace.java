package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;

public class NegativeSpace extends CustomCard {
    public static final String ID = "Runesmith:NegativeSpace";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] IMG_PATH = {"images/cards/NegativeSpaceSkill.png", "images/cards/NegativeSpaceAttack.png"};
    private static final AbstractCard.CardType[] CARD_TYPE = {CardType.SKILL, CardType.ATTACK};
    private static final AbstractCard.CardTarget[] CARD_TARGET = {CardTarget.SELF, CardTarget.ENEMY};
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADE_PLUS_DMG = 2;
    private static final int BLOCK_AMT = 9;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final int SCALE_AMT = 3;

    public NegativeSpace() {
        super(
                ID,
                NAME,
                IMG_PATH[0],
                COST,
                DESCRIPTION,
                CARD_TYPE[0],
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CARD_TARGET[0]
        );
        this.baseDamage = ATTACK_DMG;
        this.baseBlock = BLOCK_AMT;
    }

    public void applyPowers() {
//        int tempBlock = this.baseBlock;
//        int defaultBlock = tempBlock;
//        defaultBlock -= runeCount*SCALE_AMT;
//        this.baseBlock = defaultBlock;
        int defaultBlock = BLOCK_AMT;
        if (upgraded)
            defaultBlock += UPGRADE_BLOCK_AMT;
        int runeCount = RuneOrb.getRuneCount();
        defaultBlock -= runeCount*SCALE_AMT;
        this.baseBlock = (defaultBlock >= 0) ? defaultBlock : 0;

        super.applyPowers();

        if (runeCount > 0)
            switchType(1);
        else
            switchType(0);
        this.loadCardImage(textureImg);

//        this.baseBlock = tempBlock;
    }

    private void switchType(int type) {
        if (!textureImg.equals(IMG_PATH[type])) {
            this.textureImg = IMG_PATH[type];
            loadCardImage(this.textureImg);
        }
        if (this.type != CARD_TYPE[type])
            this.type = CARD_TYPE[type];
        if (this.target != CARD_TARGET[type])
            this.target = CARD_TARGET[type];
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

        for (int i = 0; i < RuneOrb.getRuneCount(); i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true));
        }
    }

    public AbstractCard makeCopy() {
        return new NegativeSpace();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}
