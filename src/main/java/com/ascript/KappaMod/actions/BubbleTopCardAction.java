package com.ascript.KappaMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BubbleTopCardAction extends AbstractGameAction {
    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player.drawPile.isEmpty()) {
            if (!player.discardPile.isEmpty()) {
                addToTop(new BubbleTopCardAction());
                addToTop(new EmptyDeckShuffleAction());
            }
        } else {
            AbstractCard card = player.drawPile.getTopCard();
            player.drawPile.removeTopCard();
            AbstractDungeon.getCurrRoom().souls.remove(card);
            addToTop(new BubbleAction(card));
            addToTop(new WaitAction(Settings.FAST_MODE ? Settings.ACTION_DUR_FASTER : Settings.ACTION_DUR_FAST));
        }

        isDone = true;
    }
}
