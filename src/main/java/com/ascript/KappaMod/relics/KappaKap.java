package com.ascript.KappaMod.relics;

import basemod.abstracts.CustomRelic;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.actions.KappaKapAction;
import com.ascript.KappaMod.actions.RippleAction;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static com.ascript.KappaMod.KappaMod.makeRelicOutlinePath;
import static com.ascript.KappaMod.KappaMod.makeRelicPath;

public class KappaKap extends CustomRelic {

    public static final String ID = KappaMod.makeID(KappaKap.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("KappaKap.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("KappaKapOutline.png"));

    private static final int AMT = 4;

    public KappaKap() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        addToBot(new KappaKapAction(AMT));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMT + DESCRIPTIONS[1];
    }
}
