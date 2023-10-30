package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.powers.FloodPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class RippleAction extends AbstractGameAction {
    public RippleAction(AbstractCreature player, int amt) {
        setValues(player, player, amt);
    }

    @Override
    public void update() {
        int direction = 1;
        if (target.hasPower(FloodPower.POWER_ID)) {
            direction = ((FloodPower) target.getPower(FloodPower.POWER_ID)).amount2;
        }
        addToTop(new ApplyPowerAction(target, target, new FloodPower(target, direction*amount), direction*amount));
        isDone = true;
    }
}
