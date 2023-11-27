package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.ascript.KappaMod.powers.FloodPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class KappaKapAction extends AbstractGameAction {
    private static final String id = KappaMod.makeID("BubbleSelectionAction");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(id);
    public static final String[] TEXT = uiStrings.TEXT;

    public KappaKapAction(int numCards) {
        amount = numCards;
        actionType = ActionType.CARD_MANIPULATION;
        duration = startDuration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.isDone = true;
            return;
        }

        CardGroup draw = AbstractDungeon.player.drawPile;
        if (duration == startDuration) {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                isDone = true;
                return;
            }

            CardGroup tmpGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            if (amount < 0 || amount > draw.size()) {
                for (AbstractCard c : draw.group) {
                    tmpGroup.addToBottom(c);
                }
            } else {
                for (int i = 0; i < amount; i++) {
                    tmpGroup.addToTop(draw.group.get(draw.size() - i - 1));
                }
            }

            AbstractDungeon.gridSelectScreen.open(tmpGroup, 1, TEXT[1] + TEXT[0], false, false, false, false);
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                draw.removeCard(c);
                addToTop(new BubbleAction(c));
            }
            isDone = true;
        }
        tickDuration();
    }
}
