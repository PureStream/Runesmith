package runesmith.character.player;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import runesmith.RunesmithMod;
import runesmith.cards.Runesmith.Fortify;
import runesmith.patches.AbstractCardEnum;
import runesmith.patches.PlayerClassEnum;

public class RunesmithCharacter extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;
	
	public static final String THE_RUNESMITH_SHOULDER_2 = "images/character/shoulder2.png"; // campfire pose
    public static final String THE_RUNESMITH_SHOULDER_1 = "images/character/shoulder.png"; // another campfire pose
    public static final String THE_RUNESMITH_CORPSE = "images/character/corpse.png"; // dead corpse
    public static final String THE_RUNESMITH_SKELETON_ATLAS = "images/character/idle/skeleton.atlas"; // spine animation atlas
    public static final String THE_RUNESMITH_SKELETON_JSON = "images/character/idle/skeleton.json"; // spine animation json
    public static final String THE_RUNESMITH_SPRITER = "images/character/idle/animation.scml"; //Spriter File
	
	public RunesmithCharacter (String name) {
		super(name, PlayerClassEnum.RUNESMITH_CLASS, null, "images/vfx.png",new SpriterAnimation(THE_RUNESMITH_SPRITER));
		
		initializeClass(null, THE_RUNESMITH_SHOULDER_2, // required call to load textures and setup energy/loadout
				THE_RUNESMITH_SHOULDER_1,
				THE_RUNESMITH_CORPSE, 
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
		
//		this.dialogX = (this.drawX + 0.0F * Settings.scale); // set location for text bubbles
//		this.dialogY = (this.drawY + 220.0F * Settings.scale); // you can just copy these values

//		loadAnimation(THE_RUNESMITH_SKELETON_ATLAS, THE_RUNESMITH_SKELETON_JSON, 1.0F);
		
//		AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
//		e.setTime(e.getEndTime() * MathUtils.random());
	}
	
	public ArrayList<String> getStartingDeck() { // starting deck 'nuff said
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Runesmith:Strike_RS");
		retVal.add("Runesmith:Strike_RS");
		retVal.add("Runesmith:Strike_RS");
		retVal.add("Runesmith:Strike_RS");
		retVal.add("Runesmith:Defend_RS");
		retVal.add("Runesmith:Defend_RS");
		retVal.add("Runesmith:Defend_RS");
		retVal.add("Runesmith:Defend_RS");
		retVal.add("Runesmith:CraftFirestone");
		retVal.add("Runesmith:Fortify");
		//retVal.add("MyCard2");
		return retVal;
	}
	
	public ArrayList<String> getStartingRelics() { // starting relics - also simple
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Runesmith:BrokenRuby");
		UnlockTracker.markRelicAsSeen("Runesmith:BrokenRuby");
		return retVal;
	}
	
    public static final int STARTING_HP = 70;
    public static final int MAX_HP = 70;
    public static final int STARTING_GOLD = 99;
    public static final int HAND_SIZE = 5;
    public static final int ORB_SLOTS = 0;
    
	public CharSelectInfo getLoadout() { // the rest of the character loadout so includes your character select screen info plus hp and starting gold
		return new CharSelectInfo("The Runesmith", "A smith awoken from a deep cryosleep. NL Uses forgotten technology to craft runes.",
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
		return RunesmithMod.BEIGE;
	}

	@Override
	public Color getCardTrailColor() {
		return RunesmithMod.BEIGE;
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
		return "The Runesmith";
	}

	@Override
	public Color getSlashAttackColor() {
		return RunesmithMod.BEIGE;
	}

	@Override
	public AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.BLUNT_HEAVY , 
				AbstractGameAction.AttackEffect.FIRE, 
				AbstractGameAction.AttackEffect.BLUNT_LIGHT, 
				AbstractGameAction.AttackEffect.BLUNT_HEAVY  , 
				AbstractGameAction.AttackEffect.FIRE, 
				AbstractGameAction.AttackEffect.BLUNT_LIGHT};	   
	}

	@Override
	public String getSpireHeartText() {
		return "You charge up your hammer...";
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
		return com.megacrit.cardcrawl.events.city.Vampires.DESCRIPTIONS[0];
	}

	@Override
	public AbstractPlayer newInstance() {

		return new RunesmithCharacter(this.name);
	}
	
	 public TextureAtlas.AtlasRegion getOrb()
    {
        return new TextureAtlas.AtlasRegion(ImageMaster.loadImage("images/cardui/description_beige_orb.png"), 0, 0, 24, 24);
    }
}
