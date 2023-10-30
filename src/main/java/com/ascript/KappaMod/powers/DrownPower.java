package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.enums.KappaAttackEffect;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class DrownPower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID("DrownPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("overheat_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("overheat_32.png"));

    private AbstractCreature source;

    public DrownPower(final AbstractCreature owner, final AbstractCreature source,  final int amt) {
        super();
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        amount = amt;
        priority = -1;

        type = PowerType.DEBUFF;
        isTurnBased = true;

        this.loadRegion("like_water");
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurnPreEndTurnCards(boolean isPlayer) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flashWithoutSound();
            if (owner.hasPower(SubmergePower.POWER_ID)) {
                addToBot(new LoseHPAction(owner, source, amount, KappaAttackEffect.SPLASH));
            } else if (amount <= 1) {
                addToBot(new RemoveSpecificPowerAction(owner, source, this));
            } else {
                addToBot(new ReducePowerAction(owner, source, this, amount/2));
            }
        }
    }

    @Override
    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }
}
