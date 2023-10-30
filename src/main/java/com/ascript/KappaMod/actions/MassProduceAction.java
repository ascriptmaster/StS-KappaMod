package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.cards.MassProduction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MassProduceAction extends AbstractGameAction {
    private List<AbstractCard> cannotDuplicate;
    private static String[] TEXT = CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT;

    public MassProduceAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = ActionType.DRAW;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.duration == Settings.ACTION_DUR_FAST) {
            cannotDuplicate = p.hand.group.stream().filter(c -> !canCopy(c)).collect(Collectors.toList());
            if (cannotDuplicate.size() == p.hand.group.size()) {
                isDone = true;
                return;
            }
            if (p.hand.group.size() - cannotDuplicate.size() == 1) {
                Optional<AbstractCard> c = p.hand.group.stream().filter(this::canCopy).findAny();
                if (c.isPresent()) {
                    cloneCard(c.get());
                }
                isDone = true;
                return;
            }
            p.hand.group.removeAll(cannotDuplicate);

            if (p.hand.group.size() > 1) {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false, false, false, false);
                tickDuration();
                return;
            } else if (p.hand.group.size() == 1) {
                // This probably shouldn't happen due to earlier if statements, but it's in DualWieldAction so just in case.
                cloneCard(p.hand.getTopCard());
                returnCards();
                isDone = true;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            AbstractCard card = AbstractDungeon.handCardSelectScreen.selectedCards.getTopCard();
            cloneCard(card);
            AbstractDungeon.handCardSelectScreen.selectedCards.moveToHand(card);
            AbstractDungeon.handCardSelectScreen.selectedCards.clear();
            returnCards();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            isDone = true;
        }
    }

    private boolean canCopy(AbstractCard card) {
        if (card.cardID.equals(MassProduction.ID)) return false;
        return card.type.equals(AbstractCard.CardType.SKILL) || card.type.equals(AbstractCard.CardType.POWER);
    }

    private void returnCards() {
        AbstractDungeon.player.hand.group.addAll(cannotDuplicate);
        AbstractDungeon.player.hand.refreshHandLayout();
    }

    private void cloneCard(AbstractCard card) {
        for(int i = 0; i < this.amount; ++i) {
            this.addToTop(new MakeTempCardInHandAction(card.makeStatEquivalentCopy()));
        }
    }
}
