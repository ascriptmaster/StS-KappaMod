package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class OozeFloodPower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID("OozeFloodPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("opticalcamo84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("opticalcamo32.png"));

    public OozeFloodPower(final AbstractCreature owner, final int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amt;

        type = PowerType.BUFF;

        this.loadRegion("like_water");
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (damageAmount > 0 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            flash();
            addToTop(new ApplyPowerAction(target, this.owner, new SubmergePower(target, this.owner, this.amount), this.amount, true));
            addToTop(new ApplyPowerAction(target, this.owner, new DrownPower(target, this.owner, this.amount), this.amount, true));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
