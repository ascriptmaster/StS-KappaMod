package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.ui.FloodPanel;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class RippleAction extends AbstractGameAction {
    public RippleAction(AbstractCreature player, int amt) {
        setValues(player, player, amt);
    }

    @Override
    public void update() {
        FloodPanel.ripple(amount);
        isDone = true;
    }
}
