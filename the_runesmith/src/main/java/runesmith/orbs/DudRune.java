package runesmith.orbs;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

public class DudRune extends RuneOrb {

	public DudRune() {
		super( "Dud",
				false,
				0);
	}

	@Override
	public AbstractOrb makeCopy() { return new DudRune(); }

}
