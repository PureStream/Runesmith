package runesmith;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomRelic;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.audio.Sfx;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import elementalist_mod.ElementalistMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import runesmith.cards.Runesmith.*;
import runesmith.character.player.RunesmithCharacter;
import runesmith.helpers.PotencyVariable;
import runesmith.orbs.MedicinaeRune;
import runesmith.orbs.RuneOrb;
import runesmith.patches.CardStasisStatus;
import runesmith.patches.ElementsGainedCountField;
import runesmith.patches.EnhanceCountField;
import runesmith.patches.PlayerClassEnum;
import runesmith.powers.PermafrostPower;
import runesmith.relics.*;
import runesmith.ui.ElementsCounter;
import runesmith.utils.KeywordWithProper;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static runesmith.patches.AbstractCardEnum.RUNESMITH_BEIGE;
import static runesmith.patches.CardTagEnum.*;


@SpireInitializer
public class RunesmithMod implements PostExhaustSubscriber,
        PostBattleSubscriber,
        PostDungeonInitializeSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber,
        EditRelicsSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        OnCardUseSubscriber,
        EditKeywordsSubscriber,
        OnPowersModifiedSubscriber,
        OnPlayerLoseBlockSubscriber,
        PostDrawSubscriber,
        PreMonsterTurnSubscriber,
        OnStartBattleSubscriber{

    public static final Logger logger = LogManager.getLogger(RunesmithMod.class.getName());

    private static final String RUNESMITH_BUTTON = "images/character/runesmithButton.png";
    private static final String RUNESMITH_PORTRAIT = "images/character/runesmithPortrait.png";
    public static Color BEIGE = new Color(175f / 255f, 145f / 255f, 100f / 255f, 1f);

    private static final String CARD_STRING = "localization/%s/RuneSMod_Cards.json";
    private static final String CHARACTER_STRING = "localization/%s/RuneSMod_Character.json";
    private static final String RELIC_STRING = "localization/%s/RuneSMod_Relics.json";
    private static final String POWER_STRING = "localization/%s/RuneSMod_Powers.json";
    private static final String ORB_STRING = "localization/%s/RuneSMod_Orbs.json";
    private static final String POTION_STRING = "localization/%s/RuneSMod_Potions.json";
    private static final String UI_STRING = "localization/%s/RuneSMod_UI.json";
    private static final String TUTORIAL_STRING = "localization/%s/RuneSMod_Tutorials.json";
    private static final String KEYWORD_STRING = "localization/%s/RuneSMod_Keywords.json";
    private static final String EVENT_STRING = "localization/%s/RuneSMod_Events.json";

    private List<AbstractCard> cardsToAdd = new ArrayList<>();
    private List<CustomRelic> relicsToAddOnlyThisClass = new ArrayList<>();
    private List<CustomRelic> relicsToAddAllClass = new ArrayList<>();

    private static boolean renderElementsCounter = false;
    private static ElementsCounter elementsCounter;
    private static boolean elementalistEnabled = false;
    private static Texture ELEMENTS_GREEN_MASK = ImageMaster.loadImage("images/ui/elements/GMask.png");

    private static CardGroup hammerCards;
    private static CardGroup chiselCards;
    private static CardGroup craftCards;

    public static final int DEFAULT_MAX_ELEMENTS = 10;

    public RunesmithMod() {
        BaseMod.subscribe(this);
        logger.info("creating the color : RUNESMITH_BEIGE");
        BaseMod.addColor(
                RUNESMITH_BEIGE,
                BEIGE.cpy(),
                "images/cardui/512/bg_attack_beige.png", //attackBg
                "images/cardui/512/bg_skill_beige.png", //skillBg
                "images/cardui/512/bg_power_beige.png", //powerBg
                "images/cardui/512/card_beige_orb.png", //energyOrb
                "images/cardui/1024/bg_attack_beige.png", //attackBgPortrait
                "images/cardui/1024/bg_skill_beige.png", //skillBgPortrait
                "images/cardui/1024/bg_power_beige.png", //powerBgPortrait
                "images/cardui/1024/card_beige_orb.png", //energyOrbPortrait
                "images/cardui/description_beige_orb.png"); //cardEnergyOrb
    }

    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= THE RUNESMITH INIT =========================");

        new RunesmithMod();

        logger.info("======================================================================");
    }

    @Override
    public void receiveEditCharacters() {
        logger.info("begin editing characters");
        BaseMod.addCharacter(new RunesmithCharacter(CardCrawlGame.playerName),
                RUNESMITH_BUTTON,
                RUNESMITH_PORTRAIT,
                PlayerClassEnum.RUNESMITH_CLASS);
        logger.info("done editing characters");
    }

    private void loadAudio() {
        @SuppressWarnings (value="unchecked")
        HashMap<String, Sfx> map = (HashMap<String, Sfx>) ReflectionHacks.getPrivate(CardCrawlGame.sound, SoundMaster.class, "map");
        map.put("RUNESMITH_HAMMER", new Sfx("audio/HammerDoubleHit.ogg", false));
    }

    @Override
    public void receiveEditStrings() {
        logger.info("start editing strings");

        logger.info("Insert ENG strings");
        loadStrings("eng");

        String language = Settings.language.toString();
        if (!language.equals("ENG"))
            try {
                logger.info("Insert " + language + " strings");
                loadStrings(language.toLowerCase());
            } catch (GdxRuntimeException e) {
                logger.info(language + " json files not found.");
            }

        logger.info("done editing strings");
    }

    private static void loadStrings(String lang) {
        BaseMod.loadCustomStringsFile(RelicStrings.class, String.format(RELIC_STRING, lang));
        BaseMod.loadCustomStringsFile(CardStrings.class, String.format(CARD_STRING, lang));
        BaseMod.loadCustomStringsFile(CharacterStrings.class, String.format(CHARACTER_STRING, lang));
        BaseMod.loadCustomStringsFile(PowerStrings.class, String.format(POWER_STRING, lang));
        BaseMod.loadCustomStringsFile(OrbStrings.class, String.format(ORB_STRING, lang));
        BaseMod.loadCustomStringsFile(PotionStrings.class, String.format(POTION_STRING, lang));
        BaseMod.loadCustomStringsFile(UIStrings.class, String.format(UI_STRING, lang));
        BaseMod.loadCustomStringsFile(TutorialStrings.class, String.format(TUTORIAL_STRING, lang));
        BaseMod.loadCustomStringsFile(EventStrings.class, String.format(EVENT_STRING, lang));
    }

    @Override
    public void receiveEditKeywords() {
        logger.info("Setting up custom keywords");

        logger.info("Insert ENG keywords.");
        loadKeywords("eng");

        String language = Settings.language.name();
        if (!language.equals("ENG"))
            try {
                logger.info("Insert " + language + " keywords.");
                loadKeywords(language.toLowerCase());
            } catch (GdxRuntimeException e) {
                logger.info(language + " keywords not found.");
            }

        logger.info("Keywords setting finished.");
    }

    private static void loadKeywords(String lang) {
        Gson gson = new Gson();
        Keywords keywords = gson.fromJson(loadJson(String.format(KEYWORD_STRING, lang)), Keywords.class);
        Arrays.stream(keywords.keywords).forEach(key -> {
            logger.info("Loading keyword : " + key.PROPER_NAME);
            BaseMod.addKeyword("runesmith", key.PROPER_NAME, key.NAMES, key.DESCRIPTION);
        });
    }

    private static String loadJson(String jsonPath) {
        return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    class Keywords {
        KeywordWithProper[] keywords;
    }

    @Override
    public boolean receivePreMonsterTurn(AbstractMonster abstractMonster) {
        ElementsGainedCountField.elementsCount.set(AbstractDungeon.player, 0);
        return true;
    }

    public static void renderElementsCounter(SpriteBatch sb, float current_x){
//        AbstractPlayer p = AbstractDungeon.player;
        if (elementalistEnabled){
            if (getRenderElementalistOrbs()) {
                elementsCounter.setYOffset(95.0F * Settings.scale);
            } else{
                elementsCounter.setYOffset(0.0F);
            }
        }
        elementsCounter.update();
        elementsCounter.render(sb, current_x);
    }

    private static boolean getRenderElementalistOrbs(){
        return ElementalistMod.elementalEnergyIsEnabled();
    }

    public static boolean getElementsRender(){
        if (CardCrawlGame.dungeon != null && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            if (renderElementsCounter) {
                return true;
            } else if (ElementsCounter.getIgnis() > 0 || ElementsCounter.getTerra() > 0 || ElementsCounter.getAqua() > 0) {
                renderElementsCounter = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public void receivePostDraw(AbstractCard arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void receivePowersModified() {
        // TODO Auto-generated method stub

    }

    @Override
    public void receiveCardUsed(AbstractCard c) {
        if (CardStasisStatus.isStasis.get(c) || EnhanceCountField.enhanceCount.get(c) > 0) {
            if (CardStasisStatus.isStasis.get(c) && !c.exhaust)
                CardStasisStatus.isStasis.set(c, false);
            else
                if (EnhanceCountField.enhanceCount.get(c) > 0)
                    EnhanceCountField.enhanceReset.set(c, true);

//			AdditionalCardDescriptions.modifyDescription(c);
            c.initializeDescription();
        }
//		logger.info("reset enhancement");
    }

    @Override
    public void receiveEditCards() {
        logger.info("begin editting cards");
        BaseMod.addDynamicVariable(new PotencyVariable());

        loadCardsToAdd();

        logger.info("add cards for the Runesmith");

        cardsToAdd.forEach(BaseMod::addCard);

        logger.info("done editing cards");
    }

    private void loadCardsToAdd() {
        cardsToAdd.clear();
        cardsToAdd.add(new Strike_RS());
        cardsToAdd.add(new Defend_RS());
        cardsToAdd.add(new CraftFirestone());
        cardsToAdd.add(new Fortify());
        cardsToAdd.add(new ChiselStab());
        cardsToAdd.add(new EarthShield());
        cardsToAdd.add(new RuneHurl());
        cardsToAdd.add(new HeatedChisel());
        cardsToAdd.add(new Terraform());
        cardsToAdd.add(new CraftProtectio());
        cardsToAdd.add(new CraftIncendium());
        cardsToAdd.add(new Prism());
        cardsToAdd.add(new EnergizedChisel());
        cardsToAdd.add(new MakeshiftArmor());
        cardsToAdd.add(new HammerThrow());
        cardsToAdd.add(new ShiftingStrike());
        cardsToAdd.add(new Grindstone());
        cardsToAdd.add(new Repurpose());
        cardsToAdd.add(new HammerTime());
        cardsToAdd.add(new Firestorm());
        cardsToAdd.add(new StaticCage());
        cardsToAdd.add(new Duplicate());
        cardsToAdd.add(new CraftIndustria());
        cardsToAdd.add(new LithiumIon());
        cardsToAdd.add(new RunicBlueprint());
        cardsToAdd.add(new CraftMagma());
        cardsToAdd.add(new ArcReactor());
        cardsToAdd.add(new UnlimitedPower());
        cardsToAdd.add(new CraftMedicinae());
        cardsToAdd.add(new OneForEveryone());
        cardsToAdd.add(new ElementalConversion());
        cardsToAdd.add(new ConvertIgnis());
        cardsToAdd.add(new ConvertTerra());
        cardsToAdd.add(new ConvertAqua());
        cardsToAdd.add(new Rearm());
        cardsToAdd.add(new FieryHammer());
        cardsToAdd.add(new Empowerment());
        cardsToAdd.add(new Runesonance());
        cardsToAdd.add(new DuctTape());
        cardsToAdd.add(new CraftReservo());
        cardsToAdd.add(new CraftSpiculum());
        cardsToAdd.add(new CraftPotentia());
        cardsToAdd.add(new ChaoticBlast());
        cardsToAdd.add(new MetallurgicalResearch());
        cardsToAdd.add(new GrandSlam());
        cardsToAdd.add(new WhiteBalance());
        cardsToAdd.add(new LastStand());
        cardsToAdd.add(new ConstructBifrost());
        cardsToAdd.add(new GoWithTheFlow());
        cardsToAdd.add(new PerfectChisel());
        cardsToAdd.add(new DoubleUp());
        cardsToAdd.add(new Refinement());
        cardsToAdd.add(new Permafrost());
        cardsToAdd.add(new CreatorForm());
        cardsToAdd.add(new Reinforce());
        cardsToAdd.add(new HeatExchange());
        cardsToAdd.add(new Supernova());
        cardsToAdd.add(new GenerateForcefield());
        cardsToAdd.add(new Preparation());
        cardsToAdd.add(new Malloc());
        cardsToAdd.add(new FrozenChisel());
        cardsToAdd.add(new NanitesCloud());
        cardsToAdd.add(new Overpower());
        cardsToAdd.add(new Firewall());
        cardsToAdd.add(new HammerAndChisel());
        cardsToAdd.add(new Discharge());
        cardsToAdd.add(new Attraction());
        cardsToAdd.add(new PoweredAnvil());
        cardsToAdd.add(new ReplicatingBarrier());
        cardsToAdd.add(new Electrocute());
        cardsToAdd.add(new FissionHammer());
        cardsToAdd.add(new Accelerate());
        cardsToAdd.add(new Augmentation());
        cardsToAdd.add(new ChargedHammer());
        cardsToAdd.add(new UnstableHammer());
//        cardsToAdd.add(new Devolution());
        cardsToAdd.add(new NegativeSpace());
        cardsToAdd.add(new LightningRod());
        cardsToAdd.add(new CraftObretio());

    }

    @Override
    public void receiveEditRelics() {
        logger.info("Begin editing relics.");

        loadRelicsToAdd();

        relicsToAddOnlyThisClass.forEach(r -> BaseMod.addRelicToCustomPool(r, RUNESMITH_BEIGE));
        relicsToAddAllClass.forEach(r -> BaseMod.addRelic(r, RelicType.SHARED));
    }

    private void loadRelicsToAdd() {
        relicsToAddOnlyThisClass.clear();
        relicsToAddAllClass.clear();

        relicsToAddOnlyThisClass.add(new BrokenRuby());
        relicsToAddOnlyThisClass.add(new MiniCore());
        relicsToAddOnlyThisClass.add(new Locket());
        relicsToAddOnlyThisClass.add(new EmergencyProvisions());
        relicsToAddOnlyThisClass.add(new CoreCrystal());
        relicsToAddOnlyThisClass.add(new PocketReactor());

        relicsToAddAllClass.add(new Nanobots());
        relicsToAddAllClass.add(new AutoHammer());
        relicsToAddAllClass.add(new UraniumAnvil());
    }

    @Override
    public void receivePostInitialize() {
        this.loadAudio();

        elementsCounter = new ElementsCounter(ELEMENTS_GREEN_MASK);

        initializeElementalist();

        if(hammerCards == null){
            hammerCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            chiselCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            craftCards = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            CardLibrary.getAllCards().forEach(card -> {
                if (card.hasTag(RS_HAMMER))
                    hammerCards.addToBottom(card);
                if (card.hasTag(RS_CHISEL))
                    chiselCards.addToBottom(card);
                if(card.hasTag(RS_CRAFT))
                    craftCards.addToBottom(card);
            });
        }
    }

    public static CardGroup getHammerCards(){
        return hammerCards;
    }

    public static CardGroup getChiselCards(){
        return chiselCards;
    }

    public static CardGroup getCraftCards(){
        return craftCards;
    }

    private void initializeElementalist() {
        try {
            ElementalistMod.logger.info("Runesmith | Elementalist found");
            elementalistEnabled = true;
        } catch (NoClassDefFoundError e){
            logger.info("Runesmith | Elementalist not found");
        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        // TODO Auto-generated method stub

    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        AbstractPlayer p = AbstractDungeon.player;
        renderElementsCounter = p instanceof RunesmithCharacter;
        if (p.hasRelic(CoreCrystal.ID))
            ElementsCounter.setMaxElements(CoreCrystal.MAX_ELEMENTS);
        else
            ElementsCounter.setMaxElements(DEFAULT_MAX_ELEMENTS);

        //Reset Elements gained count.
        ElementsGainedCountField.elementsCount.set(p, 0);
        ElementsCounter.resetElements();
    }

    @Override
    public void receivePostBattle(AbstractRoom arg0) {
        AbstractPlayer p = AbstractDungeon.player;
        RuneOrb.getAllRunes(p, new MedicinaeRune(0))
                .forEach(r -> p.heal(r.getPotential()/2));

        renderElementsCounter = false;
    }

    @Override
    public void receivePostExhaust(AbstractCard arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public int receiveOnPlayerLoseBlock(int arg0) {
        AbstractPlayer p = AbstractDungeon.player;
        logger.info("current block is: " + p.currentBlock);
        int blockLoss = arg0;
        if (p.hasPower(PermafrostPower.POWER_ID)) {
            int lostByPermafrost = (int) Math.round(p.currentBlock / 2.0);
            blockLoss = Math.min(blockLoss, lostByPermafrost);
            if (blockLoss == lostByPermafrost)
                p.getPower(PermafrostPower.POWER_ID).flash();
        }
        return blockLoss;
    }

}
