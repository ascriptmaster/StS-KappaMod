package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class OverflowPower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID("OverflowPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("heat_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("heat_32.png"));

    private static final int MAX_AMT = 5;

    public OverflowPower(final AbstractCreature owner, final int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amt;

        type = PowerType.BUFF;
        isTurnBased = true;

        //this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        //this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        loadRegion("like_water");

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        if (amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(owner, owner, this, 1));
        }
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[3];
        } else {
            description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2] + DESCRIPTIONS[3];
        }
    }
}
