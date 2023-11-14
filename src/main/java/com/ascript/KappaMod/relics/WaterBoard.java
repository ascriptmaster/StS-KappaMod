package com.ascript.KappaMod.relics;

import basemod.abstracts.CustomRelic;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.powers.DrownPower;
import com.ascript.KappaMod.powers.SubmergePower;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static com.ascript.KappaMod.KappaMod.makeRelicOutlinePath;
import static com.ascript.KappaMod.KappaMod.makeRelicPath;

public class WaterBoard extends CustomRelic {
    public static final String ID = KappaMod.makeID(WaterBoard.class.getSimpleName());

    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("WaterBoard.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("WaterBoard_Outline.png"));

    private static final int AMT = 4;

    public WaterBoard() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.FLAT);
    }

    @Override
    public void atBattleStart() {
        flash();
        AbstractPlayer p = AbstractDungeon.player;
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            addToBot(new RelicAboveCreatureAction(m, this));
            addToBot(new ApplyPowerAction(m, p, new DrownPower(m, p, AMT), AMT, true));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + AMT + DESCRIPTIONS[1];
    }
}
