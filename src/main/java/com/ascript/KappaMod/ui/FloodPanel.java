package com.ascript.KappaMod.ui;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.powers.FloodPower;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.AbstractPanel;

public class FloodPanel extends AbstractPanel {
    private static final String id = KappaMod.makeID("FloodPanel");
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(id);
    public static final String[] TEXT = uiStrings.TEXT;
    public static final String[] EX = uiStrings.EXTRA_TEXT;
    private static final Color COLOR = new Color(1.0F, 1.0F, 0.86F, 1.0F);
    private Hitbox hb;

    public FloodPanel() {
        super(Settings.WIDTH/2.0f - 42.0f * Settings.scale, Settings.HEIGHT/2.0f, Settings.WIDTH/2.0f - 42.0f * Settings.scale, -200.0F * Settings.yScale, 12.0F * Settings.scale, -12.0F * Settings.scale, TextureLoader.getTexture("KappaModResources/images/powers/bubble84.png"), true);
        hb = new Hitbox(84.0f * Settings.scale, 84.0f * Settings.scale);
    }

    public void update() {
        show_y = 25.0f * getFlood() + 300.0f;
        if (!isHidden) {
            target_y = show_y;
            if (Math.abs(current_y - target_y) >= 0.5f) {
                doneAnimating = false;
            }
        }

        hb.update();
        if (hb.hovered && !AbstractDungeon.isScreenUp) {
            AbstractDungeon.overlayMenu.hoveredTip = true;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        super.render(sb);
        float offset = 42.0f * Settings.scale;
        FontHelper.renderFontCentered(sb, AbstractDungeon.player.getEnergyNumFont(), "" + getFlood(), current_x + offset, current_y + offset, COLOR);
        hb.move(current_x + offset, current_y + offset);
        hb.render(sb);
        // TODO: Render tip
        if (hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(current_x + 70.0f * Settings.scale, current_y, getTitle(), getText());
        }
    }

    private String getTitle() {
        return TEXT[0] + EX[FloodPower.surging(AbstractDungeon.player) ? 0 : 1] + TEXT[1];
    }

    private String getText() {
        int offset = FloodPower.surging(AbstractDungeon.player) ? 0 : 1;
        return TEXT[2] + EX[offset] + TEXT[3] + EX[2+offset] + TEXT[4];
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
