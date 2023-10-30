package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class SubmergePower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID("SubmergePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("overheat_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("overheat_32.png"));

    private static final int DMG_START = 2;

    private AbstractCreature source;

    public SubmergePower(final AbstractCreature owner, final AbstractCreature source, final int amt) {
        super();
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        amount = amt;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.loadRegion("like_water");
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public int getDrownIncrease() {
        if (owner != null && owner.hasPower(DrownPower.POWER_ID)) {
            int drownAmt = owner.getPower(DrownPower.POWER_ID).amount;
            return Math.max(drownAmt/2, 1);
        }
        return DMG_START;
    }

    @Override
    public void atEndOfRound() {
        if (amount > 1) {
            int drownInc = getDrownIncrease();
            addToBot(new ApplyPowerAction(owner, source, new DrownPower(owner, source, drownInc), drownInc));
            addToBot(new ReducePowerAction(owner, source, this, 1));
        } else {
            addToBot(new RemoveSpecificPowerAction(owner, source, this));
        }
    }

    @Override
    public void updateDescription() {
        int drownInc = getDrownIncrease();
        this.description = DESCRIPTIONS[0] + amount;
        if (amount == 1) {
            this.description += DESCRIPTIONS[1] + DESCRIPTIONS[3] + drownInc + DESCRIPTIONS[4];
        } else {
            this.description += DESCRIPTIONS[2] + DESCRIPTIONS[3] + drownInc + DESCRIPTIONS[4];
        }
    }
}
