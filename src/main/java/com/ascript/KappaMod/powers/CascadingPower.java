package com.ascript.KappaMod.powers;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.enums.KappaTags;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class CascadingPower extends AbstractPower {
    public static final String POWER_ID = KappaMod.makeID(CascadingPower.class.getSimpleName());
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("placeholder_power84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("placeholder_power32.png"));

    public CascadingPower(final AbstractCreature owner, final int amt) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        amount = amt;

        type = PowerType.BUFF;

        loadRegion("like_water");
        // this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        // this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.tags.contains(KappaTags.RIPPLE)) {
            addToBot(new ApplyPowerAction(owner, owner, new StrengthPower(owner, amount), amount, true));
            addToBot(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, amount), amount, true));
            addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, amount), amount, true));
            addToBot(new ApplyPowerAction(owner, owner, new LoseDexterityPower(owner, amount), amount, true));
        }
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
