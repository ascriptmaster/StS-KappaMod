package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.Iterator;

public class CleansingGeyserAction extends AbstractGameAction {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ExhaustAction");
    private static final String[] TEXT = uiStrings.TEXT;

    private AbstractPlayer player;
    private AbstractCard card;

    public CleansingGeyserAction(AbstractPlayer owner, AbstractMonster target, AbstractCard card) {
        this.source = this.player = owner;
        this.target = target;
        this.card = card;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    public void attack(int amt) {
        for (int i = 0; i < amt; i++) {
            addToTop(new DamageAction(target, new DamageInfo(source, card.damage, card.damageTypeForTurn), KappaAttackEffect.SPLASH));
        }
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (player.discardPile.isEmpty()) {
                this.isDone = true;
            } else if (player.discardPile.size() == 1) {
                if (player.discardPile.getBottomCard().costForTurn == -1) {
                    attack(EnergyPanel.getCurrentEnergy());
                } else {
                    attack(player.discardPile.getBottomCard().costForTurn);
                }

                player.discardPile.moveToExhaustPile(player.discardPile.getBottomCard());
                this.tickDuration();
            } else {
                AbstractDungeon.gridSelectScreen.open(player.discardPile, 1, TEXT[0], false, false, false, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for(AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    if (c.costForTurn == -1) {
                        attack(EnergyPanel.getCurrentEnergy());
                    } else if (c.costForTurn > 0) {
                        attack(c.costForTurn);
                    }
                    player.discardPile.moveToExhaustPile(c);
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }
}
