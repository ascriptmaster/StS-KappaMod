package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.EmpoweringBubblesPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FluvialForm extends AbstractKappaCard {

    public static final String ID = KappaMod.makeID(FluvialForm.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("EmpoweringBubbles.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 3;

    private static final int MAGIC = 2;

    public FluvialForm() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EmpoweringBubblesPower(p, p, magicNumber), magicNumber));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            initializeDescription();
        }
    }
}
