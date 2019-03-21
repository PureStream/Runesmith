//package runesmith.cards.deprecated;
//
//import basemod.abstracts.CustomCard;
//import com.megacrit.cardcrawl.actions.AbstractGameAction;
//import com.megacrit.cardcrawl.actions.common.DamageAction;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.cards.DamageInfo;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.localization.CardStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import runesmith.orbs.RuneOrb;
//import runesmith.patches.AbstractCardEnum;
//
//import java.util.stream.IntStream;
//
//public class RunicBullets extends CustomCard {
//    public static final String ID = "Runesmith:RunicBullets";
//    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
//    public static final String NAME = cardStrings.NAME;
//    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
//    public static final String IMG_PATH = "images/cards/RunicBullets.png"; //need some img
//    private static final int COST = 1;
//    private static final int ATTACK_DMG = 5;
//    private static final int UPGRADE_PLUS_DMG = 2;
//
//    public RunicBullets() {
//        super(
//                ID,
//                NAME,
//                IMG_PATH,
//                COST,
//                DESCRIPTION,
//                CardType.ATTACK,
//                AbstractCardEnum.RUNESMITH_BEIGE,
//                CardRarity.COMMON,
//                CardTarget.ENEMY
//        );
//        this.baseDamage = ATTACK_DMG;
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        int bound = RuneOrb.getRuneCount();
//        IntStream
//                .range(0, bound)
//                .forEach(i -> AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT, true)));
//    }
//
//    public AbstractCard makeCopy() {
//        return new RunicBullets();
//    }
//
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            upgradeDamage(UPGRADE_PLUS_DMG);
//        }
//    }
//}
