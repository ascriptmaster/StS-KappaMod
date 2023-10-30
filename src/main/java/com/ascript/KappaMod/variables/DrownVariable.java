package com.ascript.KappaMod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.ascript.KappaMod.cards.AbstractKappaCard;

import static com.ascript.KappaMod.KappaMod.makeID;

public class DrownVariable extends DynamicVariable {
    
    @Override
    public String key() {
        return makeID("DrownVariable");
    }
    
    @Override
    public boolean isModified(AbstractCard card) {
        return ((AbstractKappaCard) card).isDrownNumberModified;
    }
    
    @Override
    public int value(AbstractCard card) {
        return ((AbstractKappaCard) card).drownNumber;
    }
    
    @Override
    public int baseValue(AbstractCard card) {
        return ((AbstractKappaCard) card).baseDrownNumber;
    }
    
    @Override
    public boolean upgraded(AbstractCard card) {
        return ((AbstractKappaCard) card).upgradedDrownNumber;
    }
}