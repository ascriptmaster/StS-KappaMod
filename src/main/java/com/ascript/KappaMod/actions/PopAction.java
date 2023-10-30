package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.bubbles.AbstractBubble;
import com.ascript.KappaMod.bubbles.CardBubble;
import com.ascript.KappaMod.util.BubbleUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.List;

public class PopAction extends AbstractGameAction {
    AbstractPlayer player;

    public PopAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {
        List<AbstractBubble> bubbles = BubbleUtils.getBubbles(player);
        if (bubbles.size() > 0) {
            if (bubbles.get(0).pop()) {
                bubbles.remove(0);
                BubbleUtils.increaseBubbleSlots(player, 1);
            }
        }
        isDone = true;
    }
}
