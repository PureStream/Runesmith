package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import runesmith.actions.BreakRuneAction;
import runesmith.actions.RuneChannelAction;
import runesmith.orbs.DudRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.AbstractCardEnum;
import runesmith.powers.AquaPower;
import runesmith.powers.IgnisPower;
import runesmith.powers.PotentialPower;
import runesmith.powers.TerraPower;

import java.util.ArrayList;
import java.util.List;

import static runesmith.patches.CardTagEnum.HAMMER;

public class ChargedHammer extends CustomCard {
    public static final String ID = "Runesmith:ChargedHammer";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String IMG_PATH = "images/cards/ChargedHammer.png"; //<-------------- need some img
    private static final int COST = 2;
    private static final int ATTACK_DMG = 16;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int ELEMENT_SPENT = 3;
    private static final int ELEMENT_SPENT_UPG = -1;

    public ChargedHammer() {
        super(
                ID,
                NAME,
                IMG_PATH,
                COST,
                DESCRIPTION,
                CardType.ATTACK,
                AbstractCardEnum.RUNESMITH_BEIGE,
                CardRarity.UNCOMMON,
                CardTarget.ENEMY
        );
        this.baseDamage = ATTACK_DMG;
        this.tags.add(HAMMER);
        this.baseMagicNumber = this.magicNumber = ELEMENT_SPENT;
    }

    public AbstractCard makeCopy() {
        return new ChargedHammer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(ELEMENT_SPENT_UPG);
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(
                        m,
                        new DamageInfo(p, this.damage, this.damageTypeForTurn),
                        AbstractGameAction.AttackEffect.BLUNT_HEAVY
                )
        );

        if (p.orbs.size() != 0) {
            RuneOrb r = (RuneOrb) p.orbs.stream()
                    .filter(o -> o instanceof DudRune)
                    .findFirst().orElse(null);
            if (r != null) {
                int ignis = 0, terra = 0, aqua = 0;
                String ignisID = IgnisPower.POWER_ID, terraID = TerraPower.POWER_ID, aquaID = AquaPower.POWER_ID;
                if (p.hasPower(ignisID))
                    ignis = p.getPower(ignisID).amount;
                if (p.hasPower(terraID))
                    terra = p.getPower(terraID).amount;
                if (p.hasPower(aquaID))
                    aqua = p.getPower(aquaID).amount;
                if (ignis + terra + aqua >= this.magicNumber) {
                    for (int i=0; i<this.magicNumber; i++) {
                        List<String> elementsList = new ArrayList<>();
                        if (ignis > 0)
                            elementsList.add(ignisID);
                        if (terra > 0)
                            elementsList.add(terraID);
                        if (aqua > 0)
                            elementsList.add(aquaID);
                        String rngElementID = elementsList.get(AbstractDungeon.cardRandomRng.random(elementsList.size()-1));
                        if (rngElementID.equals(ignisID)) {
                            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, ignisID, 1));
                            ignis--;
                        }
                        else if (rngElementID.equals(terraID)) {
                            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, terraID, 1));
                            terra--;
                        }
                        else {
                            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, aquaID, 1));
                            aqua--;
                        }
                    }
                    float speedTime = 0.1F;
                    if (Settings.FAST_MODE) {
                        speedTime = 0.0F;
                    }
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(p.drawX, p.drawY), speedTime));
                    AbstractDungeon.actionManager.addToBottom(new SFXAction("ORB_LIGHTNING_EVOKE"));
                    int playerPot = 0;
                    if (p.hasPower(PotentialPower.POWER_ID))
                        playerPot = p.getPower(PotentialPower.POWER_ID).amount;
                    AbstractDungeon.actionManager.addToBottom(new BreakRuneAction(r));
                    AbstractDungeon.actionManager.addToBottom(new RuneChannelAction(RuneOrb.getRandomRune(true, playerPot, true)));
                }
            }
        }



    }

}