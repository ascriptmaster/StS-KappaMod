package com.ascript.KappaMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.util.Iterator;

public class BubbleFromHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BubbleFromHandAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    public static int numDiscarded;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public BubbleFromHandAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom) {
        this.p = (AbstractPlayer)target;
        this.isRandom = isRandom;
        this.setValues(target, source, amount);
        this.actionType = ActionType.DISCARD;
        this.duration = DURATION;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                isDone = true;
                return;
            }

            int handSize = p.hand.size();
            if (handSize <= amount) {
                this.amount = handSize;

                for(int i = 0; i < handSize; ++i) {
                    AbstractCard c = p.hand.getTopCard();
                    p.hand.removeCard(c);
                    addToTop(new BubbleAction(c));
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            if (!this.isRandom) {
                if (this.amount < 0) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    this.tickDuration();
                    return;
                }

                numDiscarded = this.amount;
                if (this.p.hand.size() > this.amount) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false);
                }

                AbstractDungeon.player.hand.applyPowers();
                this.tickDuration();
                return;
            }

            for(int i = 0; i < this.amount; ++i) {
                AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                this.p.hand.removeCard(c);
                addToTop(new BubbleAction(c));
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                p.hand.removeCard(c);
                addToTop(new BubbleAction(c));
            }

            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        this.tickDuration();
    }
}
