package com.ascript.KappaMod.bubbles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.vfx.BobEffect;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBubble {
    public String name;
    public String description;
    public String ID;
    protected List<PowerTip> tips = new ArrayList<>();
    public int evokeAmount = 0;
    public int passiveAmount = 0;
    protected int baseEvokeAmount = 0;
    protected int basePassiveAmount = 0;
    public float cX = 0.0F;
    public float cY = 0.0F;
    public float tX;
    public float tY;
    protected Color c;
    protected Color shineColor;
    protected static final int W = 96;
    public Hitbox hb;
    protected Texture img;
    protected BobEffect bobEffect;
    //protected static final float NUM_X_OFFSET;
    //protected static final float NUM_Y_OFFSET;
    protected float angle;
    protected float scale;
    protected float fontScale;
    protected boolean showEvokeValue;
    protected static final float CHANNEL_IN_TIME = 0.5F;
    protected float channelAnimTimer;

    public AbstractBubble() {
        this.c = Settings.CREAM_COLOR.cpy();
        this.shineColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
        this.img = null;
        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
        this.fontScale = 0.7F;
        this.showEvokeValue = false;
        this.channelAnimTimer = 0.5F;
    }

    public void update() {
        this.hb.update();
        if (this.hb.hovered) {
            TipHelper.renderGenericTip(this.tX + 96.0F * Settings.scale, this.tY + 64.0F * Settings.scale, this.name, this.description);
        }

        this.fontScale = MathHelper.scaleLerpSnap(this.fontScale, 0.7F);
    }

    public void updateAnimation() {
        this.bobEffect.update();
        this.cX = MathHelper.orbLerpSnap(this.cX, AbstractDungeon.player.animX + this.tX);
        this.cY = MathHelper.orbLerpSnap(this.cY, AbstractDungeon.player.animY + this.tY);
        if (this.channelAnimTimer != 0.0F) {
            this.channelAnimTimer -= Gdx.graphics.getDeltaTime();
            if (this.channelAnimTimer < 0.0F) {
                this.channelAnimTimer = 0.0F;
            }
        }

        this.c.a = Interpolation.pow2In.apply(1.0F, 0.01F, this.channelAnimTimer / 0.5F);
        this.scale = Interpolation.swingIn.apply(Settings.scale, 0.01F, this.channelAnimTimer / 0.5F);
    }

    public void setSlot(int slotNum, int maxOrbs) {
        float dist = 250.0F * Settings.scale + (float)maxOrbs * 10.0F * Settings.scale;
        float angle = 100.0F + (float)maxOrbs * 12.0F;
        float offsetAngle = angle / 2.0F;
        angle *= (float)slotNum / ((float)maxOrbs - 1.0F);
        angle += 90.0F - offsetAngle;
        this.tX = dist * MathUtils.cosDeg(angle) + AbstractDungeon.player.drawX;
        this.tY = dist * MathUtils.sinDeg(angle) + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        if (maxOrbs == 1) {
            this.tX = AbstractDungeon.player.drawX;
            this.tY = 250.0F * Settings.scale + AbstractDungeon.player.drawY + AbstractDungeon.player.hb_h / 2.0F;
        }

        this.hb.move(this.tX, this.tY);
    }

    public abstract void render(SpriteBatch sb);

    // Return true if the pop is successful. Checked in PopAction
    public abstract boolean pop();
}
