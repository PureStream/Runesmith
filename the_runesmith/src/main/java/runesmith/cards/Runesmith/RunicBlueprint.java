package runesmith.cards.Runesmith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.Madness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.actions.ApplyElementsPowerAction;
import runesmith.patches.AbstractCardEnum;
import static runesmith.patches.CardTagEnum.CRAFT;

public class RunicBlueprint extends CustomCard {

	public static final String ID = "Runesmith:RunicBlueprint";
	public static final String IMG_PATH = "images/cards/RunicBlueprint.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	
	private static final int COST = 1;
	private static final int UPG_COST = 0;
	private static final int AQUA_AMT = 2;
	
	public RunicBlueprint() {
		super(
			ID,
			NAME,
			IMG_PATH,
			COST,
			DESCRIPTION,
			AbstractCard.CardType.SKILL,
			AbstractCardEnum.RUNESMITH_BEIGE,
			AbstractCard.CardRarity.UNCOMMON,
			AbstractCard.CardTarget.SELF
		);
		this.exhaust = true;
		
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		//AbstractCard c = AbstractDungeon.returnTrulyRandomCardInCombat(AbstractCard.CardType.).makeCopy();
		
		ArrayList<String> tmp = new ArrayList<>();
        @SuppressWarnings("rawtypes")
		Iterator var3 = CardLibrary.cards.entrySet().iterator();
        while(var3.hasNext()) {
            @SuppressWarnings({ "unchecked", "rawtypes" })
			Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
            if (c.getValue().hasTag(CRAFT)) {
                tmp.add(c.getKey());
            }
        }
        AbstractCard cZero;
        if (tmp.size() > 0){
        	cZero = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
        } else {
            cZero = new Madness();
        }
        cZero.setCostForTurn(0);
        if(cZero instanceof AbstractRunicCard)
			((AbstractRunicCard)cZero).freeElementOnce = true;
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cZero));
        AbstractDungeon.actionManager.addToBottom(
				new ApplyElementsPowerAction(p,p,0,0,AQUA_AMT));
//        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new AquaPower(p, AQUA_AMT), AQUA_AMT));
		
	}
	
	public AbstractCard makeCopy() {
		return new RunicBlueprint();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  upgradeBaseCost(UPG_COST);
		}
	}
	
}
