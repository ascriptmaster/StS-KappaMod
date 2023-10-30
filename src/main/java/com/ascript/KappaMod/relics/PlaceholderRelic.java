package com.ascript.KappaMod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.util.TextureLoader;

import static com.ascript.KappaMod.KappaMod.makeRelicOutlinePath;
import static com.ascript.KappaMod.KappaMod.makeRelicPath;

public class PlaceholderRelic extends CustomRelic {
    
    public static final String ID = KappaMod.makeID("PlaceholderRelic");
    
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("placeholder_relic.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));
    
    public PlaceholderRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }
    
    @Override
    public void atBattleStartPreDraw() {
        flash();
    }
    
    @Override
    public void onEquip() {
        AbstractDungeon.player.energy.energyMaster += 1;
    }
    
    @Override
    public void onUnequip() {
        AbstractDungeon.player.energy.energyMaster -= 1;
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
}
