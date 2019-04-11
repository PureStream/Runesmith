package runesmith.helpers;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import runesmith.cards.Runesmith.AbstractRunicCard;

public class PotencyVariable extends DynamicVariable {
    @Override
    public String key() {
        return "RS:Pot";
        // What you put in your localization file between ! to show your value. Eg, !myKey!.
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof AbstractRunicCard) {
            AbstractRunicCard arc = (AbstractRunicCard) card;
            return arc.isPotencyModified;
        } else {
            return false;
        }

        // Set to true if the value is modified from the base value.
    }

    public void setIsModified(AbstractCard card, boolean v) {
        // Do something such that isModified will return the value v.
        // This method is only necessary if you want smith upgrade previews to function correctly.
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof AbstractRunicCard) {
            AbstractRunicCard arc = (AbstractRunicCard) card;
            return arc.potency;
        } else {
            return 0;
        }
        // What the dynamic variable will be set to on your card. Usually uses some kind of int you store on your card.
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof AbstractRunicCard) {
            AbstractRunicCard arc = (AbstractRunicCard) card;
            return arc.basePotency;
        } else {
            return 0;
        }
        // Should generally just be the above.
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof AbstractRunicCard) {
            AbstractRunicCard arc = (AbstractRunicCard) card;
            return arc.potencyUpgraded;
        } else {
            return false;
        }
        // Set to true if this value is changed on upgrade
    }
}
