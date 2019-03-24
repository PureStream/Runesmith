package runesmith.actions.runes;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import runesmith.orbs.PlayerRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.PlayerRuneField;

public class TriggerEndOfTurnRunesAction extends AbstractGameAction {

    public TriggerEndOfTurnRunesAction(){

    }

    public void update(){
        PlayerRune playerRune = PlayerRuneField.playerRune.get(AbstractDungeon.player);
        if(!playerRune.runes.isEmpty()){
            playerRune.runes.forEach(AbstractOrb::onEndOfTurn);
        }
        this.isDone = true;
    }

}
