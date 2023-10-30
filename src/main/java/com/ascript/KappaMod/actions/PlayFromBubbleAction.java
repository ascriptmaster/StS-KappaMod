package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.bubbles.CardBubble;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayFromBubbleAction extends AbstractGameAction {
    private AbstractPlayer player;
    private CardBubble bubble;

    public PlayFromBubbleAction(AbstractPlayer p, CardBubble b) {
        player = p;
        bubble = b;
        duration = startDuration = Settings.ACTION_DUR_FAST;
        actionType = ActionType.WAIT;
    }

    @Override
    public void update() {
        if (duration == startDuration) {
            target = AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng);

            AbstractCard card = bubble.card;
            bubble.card = null;
            AbstractDungeon.getCurrRoom().souls.remove(card);
            card.current_x = bubble.cX;
            card.current_y = bubble.cY;
            card.target_x = (float) Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = (float) Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            addToTop(new NewQueueCardAction(card, target, false, true));
            addToTop(new UnlimboAction(card));
            addToTop(new WaitAction(Settings.FAST_MODE ? Settings.ACTION_DUR_FASTER : Settings.ACTION_DUR_MED));

            isDone = true;
        }
    }
}
