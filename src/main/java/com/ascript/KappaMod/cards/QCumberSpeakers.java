package com.ascript.KappaMod.cards;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.characters.TheKappa;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class QCumberSpeakers extends AbstractDynamicCard {

    public static final String ID = KappaMod.makeID(QCumberSpeakers.class.getSimpleName());
    public static final String IMG = KappaMod.makeCardPath("Skill.png");

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheKappa.Enums.COLOR_AQUA;

    private static final int COST = 1;

    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 2;

    public QCumberSpeakers() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC;
        exhaust = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster mo) {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber, true));
            if (!m.hasPower(ArtifactPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber, true));
            }
            addToBot(new ApplyPowerAction(m, p, new WeakPower(m, 2, false), 2, true));
        }
    }
    
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }
}
