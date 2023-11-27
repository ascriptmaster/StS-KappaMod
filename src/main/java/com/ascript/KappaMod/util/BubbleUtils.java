package com.ascript.KappaMod.util;

import com.ascript.KappaMod.bubbles.AbstractBubble;
import com.ascript.KappaMod.bubbles.CardBubble;
import com.ascript.KappaMod.patches.PlayerFields;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import java.util.List;
import java.util.stream.Collectors;

public class BubbleUtils {
    public static List<AbstractBubble> getBubbles(AbstractPlayer p) {
        return PlayerFields.bubbles.get(p);
    }

    public static List<CardBubble> bubblesWithCards(AbstractPlayer p) {
        return getBubbles(p).stream()
                .filter(CardBubble.class::isInstance)
                .map(CardBubble.class::cast)
                .filter(b -> b.card != null)
                .collect(Collectors.toList());
    }

    public static CardBubble firstEmptyBubble(AbstractPlayer p) {
        return getBubbles(p).stream()
                .filter(CardBubble.class::isInstance)
                .map(CardBubble.class::cast)
                .filter(b -> b.card == null)
                .findFirst()
                .orElse(null);
    }

    public static void resetBubbles(AbstractPlayer p) {
        PlayerFields.bubbles.get(p).clear();
        increaseBubbleSlots(p, PlayerFields.masterMaxBubbles.get(p));
    }

    public static void increaseBubbleSlots(AbstractPlayer p, int amt) {
        List<AbstractBubble> bubbles = getBubbles(p);

        for (int i=0; i<amt; i++) {
            bubbles.add(new CardBubble());
        }

        int i=0;
        for (AbstractBubble b : bubbles) {
            b.setSlot(i++, bubbles.size());
        }
    }
}
