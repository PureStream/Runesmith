package runesmith;

import static runesmith.patches.AbstractCardEnum.RUNESMITH_BEIGE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.Keyword;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.EditCharactersSubscriber;
import runesmith.cards.Runesmith.*;
import runesmith.character.player.RunesmithCharacter;
import runesmith.helpers.AdditionalCardDescriptions;
import runesmith.helpers.PotencyVariable;
import runesmith.patches.EnhanceCountField;
import runesmith.patches.PlayerClassEnum;
import runesmith.relics.BrokenRuby;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.PostInitializeSubscriber;


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
	PostDrawSubscriber {
	
	public static final Logger logger = LogManager.getLogger(RunesmithMod.class.getName());
	
	private static final String RUNESMITH_BUTTON = "images/character/runesmithButton.png";
	private static final String RUNESMITH_PORTRAIT = "images/character/runesmithPortrait.png";
	public static Color BEIGE = new Color(245f/255f, 245f/255f, 220f/255f, 1f);
	
	private static final String CARD_STRING = "localization/RuneSMod_Cards.json";
	private static final String RELIC_STRING = "localization/RuneSMod_Relics.json";
	private static final String POWER_STRING = "localization/RuneSMod_Powers.json";
	private static final String POTION_STRING = "localization/RuneSMod_Potions.json";
	private static final String UI_STRING = "localization/RuneSMod_UI.json";
	private static final String KEYWORD_STRING = "localization/RuneSMod_Keywords.json";
	
	private List<AbstractCard> cardsToAdd = new ArrayList<>();
	private List<CustomRelic> relicsToAdd = new ArrayList<>();

	public RunesmithMod() {
		BaseMod.subscribe(this);
		logger.info("creating the color : RUNESMITH_BEIGE");
		BaseMod.addColor(
				RUNESMITH_BEIGE,
				BEIGE,
				"images/cardui/512/bg_attack_beige.png", //attackBg
				"images/cardui/512/bg_skill_beige.png", //skillBg
				"images/cardui/512/bg_power_beige.png", //powerBg
				"images/cardui/512/card_beige_orb.png", //energyOrb
				"images/cardui/1024/bg_attack_beige.png", //attackBgPortrait
				"images/cardui/1024/bg_skill_beige.png", //skillBgPortrait
				"images/cardui/1024/bg_power_beige.png", //powerBgPortrait
				"images/cardui/1024/card_beige_orb.png", //energyOrbPortrait
				"images/cardui/description_beige_orb.png"  ); //cardEnergyOrb
	}
		
	public static void initialize() {
		RunesmithMod mod = new RunesmithMod();
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
	
	@Override
	public void receiveEditStrings() {
	    logger.info("start editing strings");
	
		String relicStrings, cardStrings, powerStrings, potionStrings, relic, card, power, potion, ui, uiStrings;
		
		logger.info("lang == eng");
		card = CARD_STRING;
		relic = RELIC_STRING;
		power = POWER_STRING;
		potion = POTION_STRING;
		ui = UI_STRING;
		
		/*if (Settings.language == Settings.GameLanguage.ZHS) {
		  logger.info("lang == zhs");
		  card = CARD_STRING_ZH;
		  relic = RELIC_STRING_ZH;
		  power = POWER_STRING_ZH;
		  potion = POTION_STRING_ZH;
		} else if (Settings.language == Settings.GameLanguage.JPN) {
		  logger.info("lang == jpn");
		  card = CARD_STRING_JP;
		  relic = RELIC_STRING_JP;
		  power = POWER_STRING_JP;
		  potion = POTION_STRING_JP;
		} else if (Settings.language == Settings.GameLanguage.ZHT) {
		  logger.info("lang == zht");
		  card = CARD_STRING_ZHT;
		  relic = RELIC_STRING_ZHT;
		  power = POWER_STRING_ZHT;
		  potion = POTION_STRING_ZHT;
		} else if (Settings.language == Settings.GameLanguage.KOR) {
		  logger.info("lang == kor");
		  card = CARD_STRING_KR;
		  relic = RELIC_STRING_KR;
		  power = POWER_STRING_KR;
		  potion = POTION_STRING_KR;
		} else if(Settings.language == Settings.GameLanguage.FRA) {
		  logger.info("lang == fra");
		  card = CARD_STRING_FR;
		  relic = RELIC_STRING_FR;
		  power = POWER_STRING_FR;
		  potion = POTION_STRING_FR;
		} else {
		  logger.info("lang == eng");
		  card = CARD_STRING;
		  relic = RELIC_STRING;
		  power = POWER_STRING;
		  potion = POTION_STRING;
		}*/
		
		relicStrings = Gdx.files.internal(relic).readString(
		    String.valueOf(StandardCharsets.UTF_8)
		);
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		
		cardStrings = Gdx.files.internal(card).readString(
		    String.valueOf(StandardCharsets.UTF_8)
		);
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		
		powerStrings = Gdx.files.internal(power).readString(
		    String.valueOf(StandardCharsets.UTF_8)
		);
		BaseMod.loadCustomStrings(PowerStrings.class, powerStrings);
		
		potionStrings = Gdx.files.internal(potion).readString(
		    String.valueOf(StandardCharsets.UTF_8)
		);
		
		uiStrings = Gdx.files.internal(ui).readString(
			String.valueOf(StandardCharsets.UTF_8)
		);
		BaseMod.loadCustomStrings(UIStrings.class, uiStrings);
		
		String orbStrings = Gdx.files.internal("localization/RuneSMod_Orbs.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(OrbStrings.class, orbStrings);
//		BaseMod.loadCustomStrings(PotionStrings.class, potionStrings);
		
		logger.info("done editing strings");
	}

	@Override
	public void receiveEditKeywords() {
		logger.info("Setting up custom keywords");

	    String keywordsPath;
	    /*switch (Settings.language) {
	      case ZHT:
	        keywordsPath = KEYWORD_STRING_ZHT;
	        break;
	      case ZHS:
	        keywordsPath = KEYWORD_STRING_ZHS;
	        break;
	      case KOR:
	        keywordsPath = KEYWORD_STRING_KR;
	        break;
	      case JPN:
	        keywordsPath = KEYWORD_STRING_JP;
	        break;
	      case FRA:
	      keywordsPath = KEYWORD_STRING_FR;
	      break;
	      default:
	        keywordsPath = KEYWORD_STRING;
	        break;
	    }*/
	    keywordsPath = KEYWORD_STRING;

	    Gson gson = new Gson();
	    Keywords keywords;
	    keywords = gson.fromJson(loadJson(keywordsPath), Keywords.class);
	    for (Keyword key : keywords.keywords) {
	      logger.info("Loading keyword : " + key.NAMES[0]);
	      BaseMod.addKeyword(key.NAMES, key.DESCRIPTION);
	    }
	    logger.info("Keywords setting finished.");
	}
	
	private static String loadJson(String jsonPath) {
		return Gdx.files.internal(jsonPath).readString(String.valueOf(StandardCharsets.UTF_8));
	}
	
	class Keywords {
	    Keyword[] keywords;
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
		EnhanceCountField.enhanceReset.set(c,true);
		AdditionalCardDescriptions.modifyDescription(c);
//		logger.info("reset enhancement");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");
		BaseMod.addDynamicVariable(new PotencyVariable());
		
		loadCardsToAdd();
		
	    logger.info("add cards for the Runesmith");
	   
	    for (AbstractCard card : cardsToAdd) {
	    	BaseMod.addCard(card);
	    }
		
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
		cardsToAdd.add(new ElementalShield());
		cardsToAdd.add(new UnstableHammer());
		cardsToAdd.add(new HammerSlam());
		cardsToAdd.add(new EnchantedChisel());
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
		cardsToAdd.add(new Shatterune());
		cardsToAdd.add(new HammerPolish());
		cardsToAdd.add(new RunicBullets());
		cardsToAdd.add(new RunicBlueprint());
		cardsToAdd.add(new CraftMagma());
		cardsToAdd.add(new Demonforge());
		cardsToAdd.add(new UnlimitedPower());
		cardsToAdd.add(new CraftMedicinae());
		cardsToAdd.add(new OneForEveryone());
		cardsToAdd.add(new ElementalConversion());
		cardsToAdd.add(new ConvertIgnis());
		cardsToAdd.add(new ConvertTerra());
		cardsToAdd.add(new ConvertAqua());
		cardsToAdd.add(new Rearm());
		cardsToAdd.add(new FieryHammer());
	}

	@Override
	public void receiveEditRelics() {
		logger.info("Begin editing relics.");
		
		loadRelicsToAdd();
		
		for (CustomRelic relic : relicsToAdd) {
			BaseMod.addRelicToCustomPool(relic, RUNESMITH_BEIGE);
		}
	}
	
	private void loadRelicsToAdd() {
		relicsToAdd.clear();
//		relicsToAdd.add(new Blueberries());
		relicsToAdd.add(new BrokenRuby());
	}

	@Override
	public void receivePostInitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostDungeonInitialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receivePostExhaust(AbstractCard arg0) {
		// TODO Auto-generated method stub
		
	}
}
