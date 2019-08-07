package runesmith.orbs;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import java.util.ArrayList;

public class PlayerRune {
    public ArrayList<RuneOrb> runes = new ArrayList<>();
    private int maxRunes = 7;

    public PlayerRune(){
    }

    public void render(SpriteBatch sb){
        AbstractPlayer p = AbstractDungeon.player;
        if (p != null &&((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !p.isDead)) {
            if (!this.runes.isEmpty()) {
                for(RuneOrb rune: runes){
                    rune.render(sb);
                }
            }
        }
    }

    public void preBattlePrep(){
        runes.clear();
    }

    public void craftRune(RuneOrb rune){
        if(this.runes.size() < this.maxRunes){
            this.runes.add(rune);
            rune.onCraft();
            rune.playChannelSFX();
            for(int i = 0; i < this.runes.size() ; i++){
                this.runes.get(i).setSlot(i, this.runes.size());
            }
        }
    }

    public void breakRune(){
        if(this.runes.size() > 0){
            this.runes.get(0).onBreak();
            this.runes.remove(0);

            for(int i = 0; i < this.runes.size(); i++){
                this.runes.get(i).setSlot(i, this.runes.size());
            }
        }
    }

    public void breakRune(RuneOrb r){
        if(this.runes.size() > 0){
            int index = this.runes.indexOf(r);
            if(index >= 0) {
                this.runes.get(index).onBreak();
                this.runes.remove(index);

                for (int i = 0; i < this.runes.size(); i++) {
                    this.runes.get(i).setSlot(i, this.runes.size());
                }
            }
        }
    }

    public boolean hasRune(){
        return !this.runes.isEmpty();
    }

    public int runeCount(){
        return this.runes.size();
    }

    public void breakWithoutLosingRune(){
        if(this.runes.size()>0){
            if (this.runes.get(0).useMultiBreak) {
                this.runes.get(0).onMultiBreak();
            }else{
                this.runes.get(0).onBreak();
            }
        }
    }

    public void removeRune(){
        if(this.runes.size()>0){
            this.runes.get(0).onRemove();
            this.runes.remove(0);

            for(int i = 0; i < this.runes.size(); i++){
                this.runes.get(i).setSlot(i, this.runes.size());
            }
        }
    }

    public void removeRune(RuneOrb r){
        if(this.runes.size() > 0){
            int index = this.runes.indexOf(r);
            if(index >= 0) {
                this.runes.get(0).onRemove();
                this.runes.remove(0);

                for (int i = 0; i < this.runes.size(); i++) {
                    this.runes.get(i).setSlot(i, this.runes.size());
                }
            }
        }
    }

    public boolean hasRuneSpace(){
        return runes.size() < maxRunes;
    }

    public void applyStartOfTurnRunes(){
        if(!this.runes.isEmpty()){
            for(RuneOrb rune : this.runes){
                rune.onStartOfTurn();
            }
        }
    }

    public int getMaxRunes(){
        return maxRunes;
    }

    public int getNonDudPosition(){
        for(int i = 0; i < this.runeCount() ; i++){
            if(!(this.runes.get(i) instanceof DudRune)){
                return i;
            }
        }
        return -1;
    }
}
