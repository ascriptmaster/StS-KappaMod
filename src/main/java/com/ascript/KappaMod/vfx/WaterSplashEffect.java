package com.ascript.KappaMod.vfx;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;

public class WaterSplashEffect extends FlameParticleEffect {
    private static Color BASE_COLOR = new Color(0.1f, 0.6f, 1.0f, 0.0f);

    public WaterSplashEffect(float x, float y) {
        super(x, y);
        this.color.set(BASE_COLOR);
    }
}
