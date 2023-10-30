package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class FloodPower extends TwoAmountPower {
    public static final String POWER_ID = KappaMod.makeID("FloodPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("heat_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("heat_32.png"));

    private static final int MAX_AMT = 3;

    public FloodPower(final AbstractCreature owner, final int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amt;
        this.amount2 = 1;
        canGoNegative = true;
        canGoNegative2 = true;

        type = PowerType.BUFF;
        priority = -1;

        this.loadRegion("like_water");
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public static boolean surging(final AbstractCreature owner) {
        if (owner.hasPower(OverflowPower.POWER_ID)) return true;
        if (owner.hasPower(POWER_ID)) {
            return owner.getPower(POWER_ID).amount >= 0;
        }
        return true; // Flood is treated as 0 in this case.
    }

    public static boolean receding(final AbstractCreature owner) {
        if (owner.hasPower(OverflowPower.POWER_ID)) return true;
        if (owner.hasPower(POWER_ID)) {
            return owner.getPower(POWER_ID).amount < 0;
        }
        return false;
    }

    @Override
    public void stackPower(int stackAmount) {
        amount += stackAmount;
        if (amount >= MAX_AMT) {
            this.flashWithoutSound();
            amount = MAX_AMT;
            amount2 = -1;
        } else if (amount <= -MAX_AMT) {
            this.flashWithoutSound();
            amount = -MAX_AMT;
            amount2 = 1;
        }
    }

    @Override
    public void reducePower(int reduceAmount) {
        amount -= reduceAmount;
        if (amount >= MAX_AMT) {
            this.flashWithoutSound();
            amount = MAX_AMT;
            amount2 = -1;
        } else if (amount <= -MAX_AMT) {
            this.flashWithoutSound();
            amount = -MAX_AMT;
            amount2 = 1;
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + amount2*MAX_AMT + DESCRIPTIONS[2];
    }
}
