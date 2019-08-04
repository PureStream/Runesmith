//package runesmith.cards.deprecated;
//
//import basemod.abstracts.CustomCard;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.core.CardCrawlGame;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.localization.CardStrings;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//import com.megacrit.cardcrawl.orbs.AbstractOrb;
//import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
//import runesmith.actions.cards.ShatteruneAction;
//import runesmith.orbs.RuneOrb;
//import runesmith.patches.AbstractCardEnum;
//
//public class Shatterune extends CustomCard {
//    public static final String ID = "Runesmith:Shatterune";
//    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
//    public static final String NAME = cardStrings.NAME;
//    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
//    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
//    public static final String IMG_PATH = "runesmith/images/cards/Shatterune.png";
//    private static final int COST = -1;
//
//    public Shatterune() {
//        super(
//                ID,
//                NAME,
//                IMG_PATH,
//                COST,
//                DESCRIPTION,
//                CardType.SKILL,
//                AbstractCardEnum.RUNESMITH_BEIGE,
//                CardRarity.UNCOMMON,
//                CardTarget.NONE
//        );
//        this.showEvokeValue = true;
//    }
//
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        if (this.energyOnUse < EnergyPanel.totalCount) {
//            this.energyOnUse = EnergyPanel.totalCount;
//        }
//
//        if (p.orbs.size() == 0) return;
//
//        RuneOrb r = null;
//        for (AbstractOrb o : p.orbs) {
//            if (o instanceof RuneOrb) {
//                r = (RuneOrb) o;
//                break;
//            }
//        }
//        if (r == null) return;
//
//        AbstractDungeon.actionManager.addToBottom(
//                new ShatteruneAction(p, this.energyOnUse, this.upgraded, this.freeToPlayOnce, r)
//        );
//
//    }
//
//    public AbstractCard makeCopy() {
//        return new Shatterune();
//    }
//
//    public void upgrade() {
//        if (!this.upgraded) {
//            upgradeName();
//            this.rawDescription = UPGRADE_DESCRIPTION;
//            initializeDescription();
//        }
//    }
//}