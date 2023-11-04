package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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

    @Override
    public void atEndOfRound() {
        if (amount > 1) {
            addToBot(new ReducePowerAction(owner, source, this, 1));
        } else {
            addToBot(new RemoveSpecificPowerAction(owner, source, this));
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.85f;
        } else {
            return damage;
        }
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 1.25f;
        } else {
            return damage;
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + 15 + DESCRIPTIONS[1] + 25 + DESCRIPTIONS[2] + amount + DESCRIPTIONS[amount == 1 ? 3 : 4];
    }
}
