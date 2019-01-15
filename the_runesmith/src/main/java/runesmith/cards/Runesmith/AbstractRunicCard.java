package runesmith.cards.Runesmith;

import basemod.abstracts.CustomCard;

public abstract class AbstractRunicCard extends CustomCard {
	public AbstractRunicCard(String id, String name, String img, int cost, String rawDescription, CardType type, CardColor color,
            CardRarity rarity, CardTarget target) {
		super(id, name, img, cost, rawDescription, type,
				color, rarity, target);
	}
	
	public int potency;
	public int basePotency;
	public boolean potencyUpgraded;
	public boolean isPotencyModified;
	
	public void upgradePotency(int amount) {
		this.basePotency += amount; 
		this.potency = this.basePotency;
		if(this.potency > this.basePotency || amount>0) isPotencyModified = true;
	}
}
