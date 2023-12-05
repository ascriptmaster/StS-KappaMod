package com.ascript.KappaMod.powers;

import basemod.helpers.CardModifierManager;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.cardmods.EmpoweringBubblesModifier;
import com.ascript.KappaMod.powers.interfaces.BubbleListeningPowerInterface;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static com.ascript.KappaMod.KappaMod.makePowerPath;

public class EmpoweringBubblesPower extends AbstractPower implements BubbleListeningPowerInterface {
    public static final String POWER_ID = KappaMod.makeID("EmpoweringBubblesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath("overheat_84.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath("overheat_32.png"));

    private AbstractCreature source;

    public EmpoweringBubblesPower(final AbstractCreature owner, final AbstractCreature source, final int amt) {
        super();
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        amount = amt;

        type = PowerType.BUFF;

        this.loadRegion("like_water");
//        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
//        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onBubble(AbstractCard c) {
        flashWithoutSound();
        CardModifierManager.addModifier(c, new EmpoweringBubblesModifier(amount));
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
