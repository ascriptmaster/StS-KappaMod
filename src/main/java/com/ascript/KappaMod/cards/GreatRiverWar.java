package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.DrownPower;
import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GreatRiverWar extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(GreatRiverWar.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("WarBeneathRiver.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int MAGIC = 3;
    private static final int UPGRADE_PLUS_MAGIC = 1;

    public GreatRiverWar() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new SubmergePower(m, p, magicNumber), magicNumber));
        if (p.hasPower(DrownPower.POWER_ID)) {
            int amt = p.getPower(DrownPower.POWER_ID).amount * (upgraded ? 2 : 1);
            addToBot(new ApplyPowerAction(m, p, new DrownPower(m, p, amt), amt));
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
