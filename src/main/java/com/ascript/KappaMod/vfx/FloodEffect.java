package com.ascript.KappaMod.vfx;

import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;

public class FloodEffect {
    private static final Texture texture = TextureLoader.getTexture("KappaModResources/images/vfx/flood.png");
    private BobEffect bobEffect;
    private float cY;
    private float tY;

    public FloodEffect() {
        bobEffect = new BobEffect(5.0f * Settings.scale, 2.0f);
        cY = tY = -100.0f;
    }

    public void update() {
        int amt = 0;
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hasPower(FloodPower.POWER_ID)) {
            amt = p.getPower(FloodPower.POWER_ID).amount;
        }
        tY = 25.0f * amt - 100.0f;
    }

    public void updateAnimation() {
        cY = MathHelper.popLerpSnap(cY, tY);
        bobEffect.update();
    }

    public void render(SpriteBatch sb) {
        sb.setColor(Settings.CREAM_COLOR.cpy());
        sb.draw(texture, 0, bobEffect.y + cY, Settings.WIDTH, texture.getHeight());
    }
}
