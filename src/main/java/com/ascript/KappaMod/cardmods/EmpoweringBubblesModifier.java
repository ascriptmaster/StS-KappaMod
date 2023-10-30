package com.ascript.KappaMod.cardmods;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class EmpoweringBubblesModifier extends AbstractCardModifier {
    private int amount;

    public EmpoweringBubblesModifier(final int amt) {
        amount = amt;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        return damage + amount;
    }

    @Override
    public float modifyBlock(float block, AbstractCard card) {
        return block + amount;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new EmpoweringBubblesModifier(amount);
    }
}
