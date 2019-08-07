package runesmith.effects;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.combat.BlockedNumberEffect;

public class VitaeBlockedNumberEffect extends BlockedNumberEffect {
    private Color originalColor;

    public VitaeBlockedNumberEffect(float x, float y, String msg) {
        super(x, y, msg);
        this.color = Settings.GOLD_COLOR.cpy();
        this.originalColor = this.color.cpy();
    }

    public void update() {
        super.update();
        this.color = this.originalColor.cpy();
    }
}
