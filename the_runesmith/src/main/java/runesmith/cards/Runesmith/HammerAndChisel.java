package runesmith.cards.Runesmith;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import runesmith.patches.AbstractCardEnum;
import static runesmith.patches.CardTagEnum.HAMMER;
import static runesmith.patches.CardTagEnum.CHISEL;

public class HammerAndChisel extends CustomCard {

	public static final String ID = "Runesmith:HammerAndChisel";
	public static final String IMG_PATH = "images/cards/HammerAndChisel.png";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;	
	
	private static final int COST = 2;
	
	public HammerAndChisel() {
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
	}
	
	public void use(AbstractPlayer p, AbstractMonster m) {
		ArrayList<String> hammer = new ArrayList<>();
		ArrayList<String> chisel = new ArrayList<>();
        @SuppressWarnings("rawtypes")
		Iterator var3 = CardLibrary.cards.entrySet().iterator();
        while(var3.hasNext()) {
            @SuppressWarnings({ "unchecked", "rawtypes" })
			Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
            if (c.getValue().hasTag(HAMMER)) {
                hammer.add(c.getKey());
            }
            if (c.getValue().hasTag(CHISEL)) {
                chisel.add(c.getKey());
            }
        }
        AbstractCard c1;
        AbstractCard c2;
        c1 = CardLibrary.cards.get(hammer.get(AbstractDungeon.cardRng.random(0, hammer.size() - 1)));
        c2 = CardLibrary.cards.get(chisel.get(AbstractDungeon.cardRng.random(0, chisel.size() - 1)));
        
        if(this.upgraded) {
        	c1.upgrade();
        	c2.upgrade();
        }
        c1.modifyCostForCombat(-1);
        c2.modifyCostForCombat(-1);
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c1));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c2));	
	}
	
	public AbstractCard makeCopy() {
		return new HammerAndChisel();
	}
	
	public void upgrade() {
		if (!this.upgraded) {
		  upgradeName();
		  this.rawDescription = UPGRADE_DESCRIPTION;
		  initializeDescription();
		}
	}
	
}
