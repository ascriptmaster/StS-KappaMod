package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.KappaMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.function.Function;

public class BubbleFromHandAction extends AbstractGameAction {
    private static final String id = KappaMod.makeID("BubbleSelectionAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(id);
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean anyNumber;
    private Function<AbstractCard, AbstractGameAction> followUpAction;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public BubbleFromHandAction(AbstractPlayer player, int amount, boolean random, boolean anyNum) {
        this(player, amount, random, null);
        anyNumber = anyNum;
    }

    public BubbleFromHandAction(AbstractPlayer player, int amount, boolean random, Function<AbstractCard, AbstractGameAction> followUp) {
        p = player;
        isRandom = random;
        followUpAction = followUp;
        setValues(player, player, amount);
        actionType = ActionType.DISCARD;
        duration = DURATION;
    }

    public void update() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                isDone = true;
                return;
            }

            int handSize = p.hand.size();
            if (handSize <= amount && !anyNumber) {
                this.amount = handSize;

                for(int i = 0; i < handSize; ++i) {
                    AbstractCard c = p.hand.getTopCard();
                    processCard(c);
                }

                AbstractDungeon.player.hand.applyPowers();
                isDone = true;
                return;
            }

            if (!this.isRandom) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], amount < 0 ? 99 : amount, amount < 0 || anyNumber, amount < 0 || anyNumber);
                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
            } else {
                for (int i = 0; i < this.amount; ++i) {
                    AbstractCard c = this.p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                    processCard(c);
                }
                isDone = true;
            }
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractDungeon.handCardSelectScreen.selectedCards.group.stream().forEach(this::processCard);
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }

    private void processCard(AbstractCard c) {
        p.hand.removeCard(c);
        if (followUpAction != null) {
            addToTop(followUpAction.apply(c));
        }
        addToTop(new BubbleAction(c));
    }
}
