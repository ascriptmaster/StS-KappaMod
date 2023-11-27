package com.ascript.KappaMod.core;

import com.ascript.KappaMod.powers.OverflowPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class FloodManager {
    private static final int MAX = 3;
    public int flood;
    private int direction;

    public void prep() {
        flood = 0;
        direction = 1;
    }

    public boolean surging() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(OverflowPower.POWER_ID))
            return true;
        return direction > 0;
    }

    public boolean receding() {
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(OverflowPower.POWER_ID))
            return true;
        return direction < 0;
    }

    public void ripple(int amt) {
        flood += amt * direction;
        if (flood >= MAX) {
            flood = MAX;
            direction = -1;
        } else if (flood <= 0) {
            flood = 0;
            direction = 1;
        }
    }
}
