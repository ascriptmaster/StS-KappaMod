package com.ascript.KappaMod.actions;

import basemod.BaseMod;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.bubbles.CardBubble;
import com.ascript.KappaMod.util.BubbleUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FloatAction extends AbstractGameAction {
    public static final Logger logger = LogManager.getLogger(FloatAction.class.getName());
    public FloatAction(int amt) {
        amount = amt;

        if (amt <= 0) {
            logger.error("FloatAction received an invalid amount: " + amt);
            isDone = true;
        }
    }

    @Override
    public void update() {
        logger.debug("FloatAction update(): amount=" + amount);
        AbstractPlayer player = AbstractDungeon.player;

        List<CardBubble> bubbles = BubbleUtils.bubblesWithCards(player);

        if (bubbles.size() > amount) {
            bubbles = bubbles.subList(0, amount);
        }
        if (!bubbles.isEmpty()) {
            for (CardBubble bubble : bubbles) {
                bubble.pop();
            }

            BubbleUtils.getBubbles(player).removeAll(bubbles);
            BubbleUtils.increaseBubbleSlots(player, bubbles.size());
        }

        if (amount > bubbles.size()) {
            addToTop(new BottomCardToHandAction(amount - bubbles.size()));
        }

        isDone = true;
    }
}
