package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.DrownPower;
import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KappaPororoca extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(KappaPororoca.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Attack.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int MAGIC = 6;
    private static final int UPGRADE_PLUS_MAGIC = 2;
    private static final int DROWN = 2;
    private static final int UPGRADE_PLUS_DROWN = 2;

    public KappaPororoca() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        baseDrownNumber = drownNumber = DROWN;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new SubmergePower(m, p, magicNumber), magicNumber));
        addToBot(new ApplyPowerAction(m, p, new DrownPower(m, p, drownNumber), drownNumber));
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_MAGIC);
            updateDrownNumber(UPGRADE_PLUS_DROWN);
            initializeDescription();
        }
    }
}
