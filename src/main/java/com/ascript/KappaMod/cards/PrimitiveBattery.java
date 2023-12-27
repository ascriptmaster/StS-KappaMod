package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.EmpoweringBubblesPower;
import com.ascript.KappaMod.powers.EnergyPower;
import com.ascript.KappaMod.powers.PrimitiveBatteryPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PrimitiveBattery extends AbstractKappaCard {

    public static final String ID = KappaMod.makeID(PrimitiveBattery.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("EmpoweringBubbles.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int MAGIC = 1;

    public PrimitiveBattery() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EnergyPower(p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(p, p, new PrimitiveBatteryPower(p)));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(MAGIC);
            initializeDescription();
        }
    }

    @Override
    public float getTitleFontSize()
    {
        return 20;
    }
}
