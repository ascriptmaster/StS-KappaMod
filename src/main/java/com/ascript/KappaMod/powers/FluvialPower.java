package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FluvialPower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID(FluvialPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FluvialPower(final AbstractCreature owner, final int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amt;

        loadRegion("like_water");
    }

    @Override
    public void atStartOfTurn() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            return;
        }
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(m, AbstractDungeon.player, new DrownPower(m, AbstractDungeon.player, amount), amount));
        }
    }
}
