package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DrawReductionNextTurnPower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID("DrawReductionNextTurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DrawReductionNextTurnPower(final AbstractCreature owner, final int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        amount = amt;
        type = PowerType.DEBUFF;
        loadRegion("lessdraw");

        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void onInitialApplication() {
        AbstractDungeon.player.gameHandSize -= amount;
    }

    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        AbstractDungeon.player.gameHandSize -= stackAmount;
    }

    public void onRemove() {
        AbstractDungeon.player.gameHandSize += amount;
    }

    public void updateDescription() {
        if (amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }
}
