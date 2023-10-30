package com.ascript.KappaMod.variables;

import basemod.BaseMod;
import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static com.ascript.KappaMod.KappaMod.makeID;

public class GoheiVariable extends DynamicVariable {
    
    @Override
    public String key() {
        return makeID("GoheiDamage");
    }
    
    @Override
    public boolean isModified(AbstractCard card) {
        return card.isDamageModified;
    }
    
    @Override
    public int value(AbstractCard card) {
        BaseMod.logger.info("Testing GoheiDamage: " + card.damage);
        return card.damage / 2;
    }
    
    @Override
    public int baseValue(AbstractCard card) {
        return card.baseDamage / 2;
    }
    
    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgradedDamage;
    }
}