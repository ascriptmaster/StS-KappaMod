package com.ascript.KappaMod.actions;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class OozeFloodingAction extends AbstractGameAction {
    private boolean freeToPlayOnce = false;
    private AbstractPlayer p;
    private int energyOnUse;

    public OozeFloodingAction(AbstractCreature target, AbstractPlayer player, int extraTimes, boolean freeToPlayOnce, int energyOnUse) {
        setValues(target, player, extraTimes);
        this.p = player;
        this.freeToPlayOnce = freeToPlayOnce;
        duration = startDuration = Settings.ACTION_DUR_XFAST;
        actionType = ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic(ChemicalX.ID)) {
            effect += 2;
            this.p.getRelic(ChemicalX.ID).flash();
        }

        if (amount > 0) {
            effect += amount;
        }

        if (effect > 0) {
            addToTop(new ApplyPowerAction(target, p, new SubmergePower(target, p, effect), effect));
            addToTop(new ApplyPowerAction(target, p, new WeakPower(target, effect, false), effect));

            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
