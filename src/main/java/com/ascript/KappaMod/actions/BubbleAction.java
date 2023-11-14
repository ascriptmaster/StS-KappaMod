package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.bubbles.CardBubble;
import com.ascript.KappaMod.cards.interfaces.BubbleListeningCardInterface;
import com.ascript.KappaMod.powers.interfaces.BubbleListeningPowerInterface;
import com.ascript.KappaMod.util.BubbleUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;


public class BubbleAction extends AbstractGameAction {
    private AbstractCard card;

    public BubbleAction(AbstractCard c) {
        card = c;
    }

    @Override
    public void update() {
        CardBubble bubble = BubbleUtils.firstEmptyBubble(AbstractDungeon.player);
        if (bubble != null) {
            bubble.card = card;
            if (card instanceof BubbleListeningCardInterface) {
                ((BubbleListeningCardInterface) card).onBubble();
            }
            AbstractDungeon.player.powers.stream()
                    .filter(BubbleListeningPowerInterface.class::isInstance)
                    .map(BubbleListeningPowerInterface.class::cast)
                    .forEach(p -> p.onBubble(card));
        } else {
            addToTop(new BubbleAction(card));
            addToTop(new PopAction(AbstractDungeon.player));
        }
        isDone = true;
    }
}
