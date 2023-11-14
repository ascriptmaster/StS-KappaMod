package com.ascript.KappaMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.Iterator;

public class BubbleFromDiscardAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("BubbleSelectionAction");
    public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;

    public BubbleFromDiscardAction(int amt) {
        p = AbstractDungeon.player;
        setValues((AbstractCreature)null, p, amt);
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (duration == startDuration) {
                if (p.discardPile.isEmpty()) {
                    isDone = true;
                    return;
                }

                if (p.discardPile.size() <= amount) {
                    for (AbstractCard c : p.discardPile.group) {
                        p.discardPile.removeCard(c);
                        addToTop(new BubbleAction(c));
                    }
                    isDone = true;
                    return;
                }

                if (p.discardPile.group.size() > amount) {
                    AbstractDungeon.gridSelectScreen.open(p.discardPile, amount, TEXT[0], false, false, false, false);
                    tickDuration();
                    return;
                }
            }

            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {

                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    p.discardPile.removeCard(c);
                    addToTop(new BubbleAction(c));
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                p.hand.refreshHandLayout();
            }

            tickDuration();
        }
    }
}
