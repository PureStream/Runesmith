package runesmith.character.player;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import runesmith.RunesmithMod;
import runesmith.cards.Runesmith.CraftFirestone;
import runesmith.cards.Runesmith.Defend_RS;
import runesmith.cards.Runesmith.Fortify;
import runesmith.cards.Runesmith.Strike_RS;
import runesmith.patches.AbstractCardEnum;
import runesmith.patches.PlayerClassEnum;
import runesmith.relics.BrokenRuby;
import runesmith.ui.EnergyOrbBeige;

import java.util.ArrayList;

public class RunesmithCharacter extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;

    private static final String THE_RUNESMITH_SHOULDER_2 = "runesmith/images/character/shoulder2.png"; // campfire pose
    private static final String THE_RUNESMITH_SHOULDER_1 = "runesmith/images/character/shoulder.png"; // another campfire pose
    private static final String THE_RUNESMITH_CORPSE = "runesmith/images/character/corpse.png"; // dead corpse
    private static final String THE_RUNESMITH_SKELETON_ATLAS = "runesmith/images/character/idle/skeleton.atlas"; // spine animation atlas
    private static final String THE_RUNESMITH_SKELETON_JSON = "runesmith/images/character/idle/skeleton.json"; // spine animation json
    private static final String THE_RUNESMITH_SPRITER = "runesmith/images/character/idle/animation.scml"; //Spriter File
    private static final String RUNESMITH_PORTRAIT = "runesmith/images/character/runesmithPortrait.png";

    private Texture BEIGE_ORB_FLASH_VFX = ImageMaster.loadImage("runesmith/images/ui/beige/energyBeigeVFX.png");
    private EnergyOrbInterface energyOrb = new EnergyOrbBeige();

    private static final EventStrings heartString = CardCrawlGame.languagePack.getEventString("Runesmith:SpireHeart");
    private static final CharacterStrings characterString = CardCrawlGame.languagePack.getCharacterString("The Runesmith");
    private static final String CHAR_NAME = characterString.NAMES[0];
    private static final String CHAR_FLAVOR_TEXT = characterString.TEXT[0];

    public RunesmithCharacter(String name) {
        super(name, PlayerClassEnum.RUNESMITH_CLASS, null, "runesmith/images/vfx.png", new SpriterAnimation(THE_RUNESMITH_SPRITER));

        initializeClass(null, THE_RUNESMITH_SHOULDER_2, // required call to load textures and setup energy/loadout
                THE_RUNESMITH_SHOULDER_1,
                THE_RUNESMITH_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 350.0F, new EnergyManager(ENERGY_PER_TURN));

//		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
//		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

//		loadAnimation(THE_RUNESMITH_SKELETON_ATLAS, THE_RUNESMITH_SKELETON_JSON, 1.0F);

//		AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
//		e.setTime(e.getEndTime() * MathUtils.random());
    }

    @Override
    public String getPortraitImageName() {
        return RUNESMITH_PORTRAIT;
    }

    public ArrayList<String> getStartingDeck() { // starting deck 'nuff said
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike_RS.ID);
        retVal.add(Strike_RS.ID);
        retVal.add(Strike_RS.ID);
        retVal.add(Strike_RS.ID);
        retVal.add(Defend_RS.ID);
        retVal.add(Defend_RS.ID);
        retVal.add(Defend_RS.ID);
        retVal.add(Defend_RS.ID);
        retVal.add(CraftFirestone.ID);
        retVal.add(Fortify.ID);
        //retVal.add("MyCard2");
        return retVal;
    }

    public ArrayList<String> getStartingRelics() { // starting relics - also simple
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BrokenRuby.ID);
        UnlockTracker.markRelicAsSeen(BrokenRuby.ID);
        return retVal;
    }

    private static final int STARTING_HP = 70;
    private static final int MAX_HP = 70;
    private static final int STARTING_GOLD = 99;
    private static final int HAND_SIZE = 5;
    private static final int ORB_SLOTS = 0;

    public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
        // TODO use Character.json instead

        return new CharSelectInfo(CHAR_NAME, CHAR_FLAVOR_TEXT,
                STARTING_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, HAND_SIZE,
                this, getStartingRelics(), getStartingDeck(), false);
    }


    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("BLUNT_HEAVY", MathUtils.random(-0.1f, 0.1f));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT, false);
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public CardColor getCardColor() {
        return AbstractCardEnum.RUNESMITH_BEIGE;
    }

    @Override
    public Color getCardRenderColor() {
        return RunesmithMod.BEIGE.cpy();
    }

    @Override
    public Color getCardTrailColor() {
        return RunesmithMod.BEIGE.cpy();
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "RUNESMITH_HAMMER";
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }

    @Override
    public String getLocalizedCharacterName() {
        return CHAR_NAME;
    }

    @Override
    public Color getSlashAttackColor() {
        return RunesmithMod.BEIGE.cpy();
    }

    @Override
    public AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_LIGHT};
    }

    @Override
    public String getSpireHeartText() {
        return heartString.DESCRIPTIONS[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Fortify();
    }

    @Override
    public String getTitle(PlayerClass arg0) {
        return this.getLocalizedCharacterName();
    }

    @Override
    public String getVampireText() {
        return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[1];
    }

    @Override
    public AbstractPlayer newInstance() {

        return new RunesmithCharacter(this.name);
    }

    public TextureAtlas.AtlasRegion getOrb() {
        return new TextureAtlas.AtlasRegion(ImageMaster.loadImage("runesmith/images/cardui/description_beige_orb.png"), 0, 0, 24, 24);
    }

    public void updateOrb(int energy) {
        this.energyOrb.updateOrb(energy);
    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        this.energyOrb.renderOrb(sb, enabled, current_x, current_y);
    }

    public Texture getEnergyImage() {
        return this.BEIGE_ORB_FLASH_VFX;
    }
}
