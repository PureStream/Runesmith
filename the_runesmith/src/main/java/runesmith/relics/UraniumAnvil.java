package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import runesmith.actions.DowngradeRandomCardInDeckAction;

public class UraniumAnvil extends CustomRelic {

    public static final String ID = "Runesmith:UraniumAnvil";
    private static final String IMG = "images/relics/UraniumAnvil.png"; //<--------- Need some img
    private static final String IMG_O = "images/relics/UraniumAnvil_o.png";
    private static final int DOWN_AMT = 1;

    public UraniumAnvil() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_O), RelicTier.BOSS, LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        if (com.megacrit.cardcrawl.dungeons.AbstractDungeon.player != null) {
            return setDescription(com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.chosenClass);
        }
        return setDescription(null);
    }

    private String setDescription(AbstractPlayer.PlayerClass c) {
        return this.DESCRIPTIONS[1] + this.DESCRIPTIONS[0];
    }

    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = setDescription(c);
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();
    }

    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(
                new DowngradeRandomCardInDeckAction(AbstractDungeon.player, DOWN_AMT));
    }

    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }

    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }

    public AbstractRelic makeCopy() {
        return new UraniumAnvil();
    }

}
