package com.ascript.KappaMod.ui;

import com.ascript.KappaMod.patches.KappaFields;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

public class FloodPanel extends AbstractPanel {
    private static final Color COLOR = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    private Hitbox hb;
    private int lastFlood;

    public FloodPanel() {
        super(Settings.WIDTH/2.0f, Settings.HEIGHT/2.0f, Settings.WIDTH/2.0f, -200.0F * Settings.yScale, 12.0F * Settings.scale, -12.0F * Settings.scale, TextureLoader.getTexture("KappaModResources/images/powers/bubble84.png"), true);
        hb = new Hitbox(84.0f * Settings.scale, 84.0f * Settings.scale);
        lastFlood = 0;
    }

    public void update() {
        show_y = 25.0f * getFlood() + 300.0f;
        if (!isHidden) {
            target_y = show_y;
            if (Math.abs(current_y - target_y) >= 0.5f) {
                doneAnimating = false;
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), "" + getFlood(), this.current_x, this.current_y, COLOR);
        hb.move(current_x, current_y);
        hb.render(sb);
        // TODO: Render tip
    }

    public static int getFlood() {
        int amt = 0;
        // TODO: Refactor flood off Powers
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(FloodPower.POWER_ID)) {
            amt = AbstractDungeon.player.getPower(FloodPower.POWER_ID).amount;
        }
        return amt;
    }
}
