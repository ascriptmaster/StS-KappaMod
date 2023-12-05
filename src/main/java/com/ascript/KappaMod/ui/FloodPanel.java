package com.ascript.KappaMod.ui;

import com.ascript.KappaMod.KappaMod;
import com.ascript.KappaMod.patches.PlayerFields;
import com.ascript.KappaMod.powers.OverflowPower;
import com.ascript.KappaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public static final int MAX_FLOOD = 5;
    public static final int MIN_FLOOD = 0;
    public static int flood;
    private static int direction;

    public FloodPanel() {
        super(Settings.WIDTH/2.0f - 42.0f * Settings.scale, Settings.HEIGHT/2.0f, Settings.WIDTH/2.0f - 42.0f * Settings.scale, -200.0F * Settings.yScale, 12.0F * Settings.scale, -12.0F * Settings.scale, TextureLoader.getTexture("KappaModResources/images/powers/bubble84.png"), true);
        hb = new Hitbox(84.0f * Settings.scale, 84.0f * Settings.scale);
        flood = 0;
        direction = 1;
    }

    public static void prep() {
        flood = 0;
        direction = 1;
        if (AbstractDungeon.player != null) {
            PlayerFields.flood.get(AbstractDungeon.player).update();
        }
    }

    public static void ripple(int amt) {
        flood += amt * direction;
        if (flood >= MAX_FLOOD) {
            flood = MAX_FLOOD;
            direction = -1;
        } else if (flood <= MIN_FLOOD) {
            flood = MIN_FLOOD;
            direction = 1;
        }
        if (AbstractDungeon.player != null) {
            PlayerFields.flood.get(AbstractDungeon.player).update();
        }
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
        BitmapFont font = direction > 0 ? FontHelper.energyNumFontBlue : FontHelper.energyNumFontRed;
        FontHelper.renderFontCentered(sb, font, "" + getFlood(), current_x + offset, current_y + offset, COLOR);
        hb.move(current_x + offset, current_y + offset);
        hb.render(sb);
        // TODO: Render tip
        if (hb.hovered && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp) {
            TipHelper.renderGenericTip(current_x + 70.0f * Settings.scale, current_y, getTitle(), getText());
        }
    }

    private String getTitle() {
        return TEXT[0] + EX[(direction + 1) / 2] + TEXT[1];
    }

    private String getText() {
        int offset = (direction+1) / 2;
        return TEXT[2] + EX[offset] + TEXT[3] + EX[2+offset] + TEXT[4] + EX[1-offset] + TEXT[5] + ((direction + 1) / 2 * MAX_FLOOD) + TEXT[6];
    }

    public static int getFlood() {
        if (AbstractDungeon.player == null || AbstractDungeon.overlayMenu == null) return 0;
        return flood;
    }

    public static boolean surging() {
        if (AbstractDungeon.player == null || AbstractDungeon.overlayMenu == null) return true;
        return direction > 0 || AbstractDungeon.player.hasPower(OverflowPower.POWER_ID);
    }

    public static boolean receding() {
        if (AbstractDungeon.player == null || AbstractDungeon.overlayMenu == null) return false;
        return direction < 0 || AbstractDungeon.player.hasPower(OverflowPower.POWER_ID);
    }
}
