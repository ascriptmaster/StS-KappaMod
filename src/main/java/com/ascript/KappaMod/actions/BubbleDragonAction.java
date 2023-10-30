package com.ascript.KappaMod.actions;

import basemod.BaseMod;
import com.ascript.KappaMod.bubbles.CardBubble;
import com.ascript.KappaMod.util.BubbleUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.List;

public class BubbleDragonAction extends AbstractGameAction {
    private AbstractPlayer player;

    public BubbleDragonAction(AbstractPlayer p) {
        player = p;
    }

    @Override
    public void update() {
        List<CardBubble> bubbles = BubbleUtils.bubblesWithCards(player);

        if (!bubbles.isEmpty()) {
            int cardsFetched = 0;
            for (CardBubble bubble : bubbles) {
                if (player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                    player.createHandIsFullDialog();
                    break;
                }
                bubble.card.setCostForTurn(-9);
                bubble.pop();
                cardsFetched++;
            }

            BubbleUtils.getBubbles(player).removeAll(bubbles.subList(0, cardsFetched));
            BubbleUtils.increaseBubbleSlots(player, cardsFetched);
        }

        isDone = true;
    }
}
