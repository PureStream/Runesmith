package runesmith.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import runesmith.orbs.PlayerRune;

@SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
public class PlayerRuneField {
    public static SpireField<PlayerRune> playerRune = new SpireField<>(PlayerRune::new);
}
