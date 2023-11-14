package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.cards.WaterCarpet;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class WaterCarpetAction extends AbstractGameAction {
    AbstractCard card;
    public WaterCarpetAction(AbstractPlayer p, WaterCarpet source, AbstractCard c) {
        setValues(p, p, c.costForTurn);
        card = source;
        duration = Settings.ACTION_DUR_XFAST;
    }

    @Override
    public void update() {
        if (amount == -1) {
            amount = EnergyPanel.getCurrentEnergy();
        }

        if (amount > 0) {
            addToTop(new GainBlockAction(target, card.block * amount));
        }

        isDone = true;
        return;
    }
}
