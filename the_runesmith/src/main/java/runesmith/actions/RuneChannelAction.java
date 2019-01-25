package runesmith.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

import runesmith.orbs.RuneOrb;

public class RuneChannelAction extends AbstractGameAction{

	private AbstractOrb orbType;
	private boolean autoEvoke = false;
	private int MAX_ORBS = 7;
	
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
		AbstractPlayer p = AbstractDungeon.player;
		if (p.maxOrbs == MAX_ORBS) {
			if (!p.hasEmptyOrb()) {
				AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, "I'm out of #rrunes space.", true));
				this.isDone = true;
		        return;
			}
		} else {
		 	p.increaseMaxOrbSlots(1, false);
		 	CardCrawlGame.sound.playA("GUARDIAN_ROLL_UP", 1.0F);
		}
		if (this.autoEvoke) {
			if(p.hasPower("Runesmith:DuplicatePower")) {channelDuplicate(this.orbType, this.autoEvoke);}
    		p.channelOrb(this.orbType);
    		if(this.orbType instanceof RuneOrb) {
    			((RuneOrb) this.orbType).onCraft();	
    		}
	    } else {
	    	for (AbstractOrb o : p.orbs) {
	    		if ((o instanceof EmptyOrbSlot)){
	    			if(p.hasPower("Runesmith:DuplicatePower")) {channelDuplicate(this.orbType, this.autoEvoke);}
	    			p.channelOrb(this.orbType);
	    			if(this.orbType instanceof RuneOrb) {
	    				((RuneOrb) this.orbType).onCraft();	
	    			}
	    			break;
	    		}
	    	}		
	    }
	    tickDuration();
	    this.isDone = true;
	}
	
	private void channelDuplicate(AbstractOrb newOrbType, boolean autoEvoke) {
		AbstractPlayer p = AbstractDungeon.player;
		if(!(p.maxOrbs == MAX_ORBS - 1 && !p.hasEmptyOrb())) {
			AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, "Runesmith:DuplicatePower", 1));
			p.increaseMaxOrbSlots(1, false);
			p.channelOrb(this.orbType.makeCopy());
			if(this.orbType instanceof RuneOrb) {
				((RuneOrb) this.orbType).onCraft();	
			}
		}else{
			AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, "I'm out of #rrunes space.", true));
			this.isDone = true;
		}
}
}
