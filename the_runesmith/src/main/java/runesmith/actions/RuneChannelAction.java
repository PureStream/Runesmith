package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class RuneChannelAction extends AbstractGameAction{

	private AbstractOrb orbType;
	private boolean autoEvoke = false;
	
	public RuneChannelAction(AbstractOrb newOrbType) {
		this(newOrbType, true);
	}
	
	public RuneChannelAction(AbstractOrb newOrbType, boolean autoEvoke) {
		this.duration = Settings.ACTION_DUR_FAST;
	    this.orbType = newOrbType;
	    this.autoEvoke = autoEvoke;
	}

	@Override
	public void update() {
		if (AbstractDungeon.player.maxOrbs == 10) {
		      if (!AbstractDungeon.player.hasEmptyOrb()) {
		        AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, "You cannot fit any more #rreplicas for now.", true));

		        this.isDone = true;
		        return;
		      }
		 } else {
		      AbstractDungeon.player.increaseMaxOrbSlots(1, false);
		      CardCrawlGame.sound.playA("GUARDIAN_ROLL_UP", 1.0F);
		    }
		    if (this.autoEvoke) {
		      AbstractDungeon.player.channelOrb(this.orbType);
		    } else {
		      for (AbstractOrb o : AbstractDungeon.player.orbs) {
		        if ((o instanceof EmptyOrbSlot))
		        {
		          AbstractDungeon.player.channelOrb(this.orbType);
		          break;
		        }
		      }
	    }
	    tickDuration();
	    this.isDone = true;
	    return;
	}
}
