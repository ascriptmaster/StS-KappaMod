package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.util.BubbleUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.function.Supplier;

public class ActionPerBubbledCardAction extends AbstractGameAction {
    private Supplier<AbstractGameAction> actionSupplier;

    public ActionPerBubbledCardAction(Supplier<AbstractGameAction> actSup) {
        actionSupplier = actSup;
    }
    @Override
    public void update() {
        int amount = BubbleUtils.bubblesWithCards(AbstractDungeon.player).size();
        for (int i = 0; i < amount; i++) {
            addToTop(actionSupplier.get());
        }
        isDone = true;
    }
}
