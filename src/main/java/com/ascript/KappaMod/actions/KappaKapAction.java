package com.ascript.KappaMod.actions;

import com.ascript.KappaMod.powers.FloodPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class KappaKapAction extends AbstractGameAction {
    @Override
    public void update() {
        int amt = 0;
        if (AbstractDungeon.player.hasPower(FloodPower.POWER_ID)) {
            amt = AbstractDungeon.player.getPower(FloodPower.POWER_ID).amount;
        }
        if (amt > 0) {
            addToTop(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 3, DamageInfo.DamageType.THORNS), AttackEffect.POISON));
        } else {
            addToTop(new GainBlockAction(AbstractDungeon.player, 3));
        }
        isDone = true;
    }
}
