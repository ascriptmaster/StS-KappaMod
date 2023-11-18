package com.ascript.KappaMod.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import static com.ascript.KappaMod.KappaMod.makeID;

public class SplashDamageVariable extends DynamicVariable {
    
    @Override
    public String key() {
        return makeID("SPLASH_DAMAGE");
    }
    
    @Override
    public boolean isModified(AbstractCard card) {
        AbstractCard clone = card.makeStatEquivalentCopy();
        clone.applyPowers();
        return clone.isDamageModified;
    }
    
    @Override
    public int value(AbstractCard card) {
        AbstractCard clone = card.makeStatEquivalentCopy();
        clone.applyPowers();
        return clone.damage;
    }
    
    @Override
    public int baseValue(AbstractCard card) {
        return card.baseDamage;
    }
    
    @Override
    public boolean upgraded(AbstractCard card) {
        return card.upgradedDamage;
    }
}