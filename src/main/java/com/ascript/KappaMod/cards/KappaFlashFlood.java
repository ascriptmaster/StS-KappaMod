package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.powers.DrownPower;
import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class KappaFlashFlood extends AbstractKappaCard {

    public static final String ID = KappaMod.makeID(KappaFlashFlood.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 2;

    private static final int MAGIC = 4;
    private static final int UPGRADE_PLUS_MAGIC = 3;

    public KappaFlashFlood() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(m, p, new DrownPower(m, p, magicNumber), magicNumber, true));
            addToBot(new ApplyPowerAction(m, p, new SubmergePower(m, p, 2), 2, true));
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
