package runesmith.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class Blueberries extends CustomRelic {
    public static final String ID = "Blueberries";
    //private static final int HP_PER_CARD = 1;

    public Blueberries() {
        super(ID, "", // <------------------------------------ Some img needed
                RelicTier.UNCOMMON, LandingSound.MAGICAL); // this relic is uncommon and sounds magic when you click it
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void onEquip() {
		/*int count = 0;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c.isEthereal) { // when equipped (picked up) this relic counts how many ethereal cards are in the player's deck
				count++;
			}
		}
		AbstractDungeon.player.increaseMaxHp(count * HP_PER_CARD, true);*/
        AbstractDungeon.player.increaseMaxHp(8, true);
    }

    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new Blueberries();
    }

}
