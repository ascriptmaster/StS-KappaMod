package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.powers.SubmergePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PhotonTorpedoAction extends AbstractGameAction {
    private AbstractCard card;

    public PhotonTorpedoAction(AbstractCard card, int amount) {
        this.card = card;
        this.amount = amount;
    }

    @Override
    public void update() {
        if (amount <= 0 || AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            isDone = true;
            return;
        }

        AbstractMonster target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        card.calculateCardDamage(target);
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, new SubmergePower(target, AbstractDungeon.player, 1), 1, true));
        addToTop(new PhotonTorpedoAction(card, amount - 1));
        addToTop(new DamageAction(target, new DamageInfo(AbstractDungeon.player, card.damage, card.damageTypeForTurn), AttackEffect.FIRE));

        this.isDone = true;
    }
}
