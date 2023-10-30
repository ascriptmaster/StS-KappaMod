package com.ascript.KappaMod.actions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BottomCardToHandAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(BottomCardToHandAction.class.getName());
    private boolean shuffleCheck;

    public BottomCardToHandAction(int amt) {
        amount = amt;
        actionType = ActionType.DRAW;
        duration = startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FASTER;
        shuffleCheck = false;
    }

    @Override
    public void update() {
        if (amount <= 0) {
            isDone = true;
            return;
        }

        // Wait for SoulGroup to become inactive.
        if (SoulGroup.isActive()) {
            return;
        }

        AbstractPlayer player = AbstractDungeon.player;
        int deckSize = player.drawPile.size();
        int discardSize = player.discardPile.size();
        if (deckSize == 0 && discardSize == 0) {
            isDone = true;
            return;
        }
        if (player.hand.size() >= BaseMod.MAX_HAND_SIZE) {
            player.createHandIsFullDialog();
            isDone = true;
            return;
        }

        if (amount + player.hand.size() > 10) {
            player.createHandIsFullDialog();
            amount = 10 - player.hand.size();
        }
        if (!shuffleCheck) {
            if (amount > deckSize) {
                addToTop(new BottomCardToHandAction(amount - deckSize));
                addToTop(new EmptyDeckShuffleAction());
                if (deckSize > 0) {
                    addToTop(new BottomCardToHandAction(deckSize));
                }

                isDone = true;
                return;
            }
            shuffleCheck = true;
        }

        duration -= Gdx.graphics.getDeltaTime();
        if (amount > 0 && duration < 0.0F) {
            duration = startDuration;
            amount--;

            if (player.drawPile.isEmpty()) {
                logger.warn("Player attempted to draw from an empty drawpile mid-DrawAction?MASTER DECK: " + player.masterDeck.getCardNames());
                isDone = true;
                return;
            }
            AbstractCard botCard = player.drawPile.getBottomCard();
            player.drawPile.removeCard(botCard);
            player.hand.addToTop(botCard);
            player.hand.refreshHandLayout();
            player.hand.applyPowers();

            if (amount == 0) {
                isDone = true;
                return;
            }
        }
    }
}
