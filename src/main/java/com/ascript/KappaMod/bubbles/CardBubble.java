package com.ascript.KappaMod.bubbles;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;

public class CardBubble extends AbstractBubble {
    public AbstractCard card;

    public void updateAnimation() {
        super.updateAnimation();
        this.angle += Gdx.graphics.getDeltaTime() * 10.0F;

        if (card != null) {
            card.current_x = this.cX;
            card.current_y = this.cY;
            card.setAngle(0, true);
        }
    }

    @Override
    public void update() {
        hb.update();
        if (card != null) {
            card.drawScale = hb.hovered ? 0.6f : 0.3f;
            card.hb.hovered = hb.hovered;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(this.c);
        sb.draw(ImageMaster.ORB_SLOT_2, this.cX - 48.0F - this.bobEffect.y / 8.0F, this.cY - 48.0F + this.bobEffect.y / 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0, 0, 96, 96, false, false);
        sb.draw(ImageMaster.ORB_SLOT_1, this.cX - 48.0F + this.bobEffect.y / 8.0F, this.cY - 48.0F - this.bobEffect.y / 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, this.angle, 0, 0, 96, 96, false, false);
        this.hb.render(sb);
        if (card != null) {
            card.render(sb);
            if (hb.hovered) {
                TipHelper.renderTipForCard(card, sb, card.keywords);
                if (card.cardsToPreview != null) {
                    card.renderCardPreview(sb);
                }
            }
        }
    }

    @Override
    public boolean pop() {
        // Alerts PopAction, FloatAction etc. to not shift bubbles around.
        if (card == null) return false;

        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.size() < BaseMod.MAX_HAND_SIZE) {
            p.hand.addToHand(card);

            card.lighten(false);
            card.unhover();
            card.applyPowers();

            p.hand.refreshHandLayout();
        } else {
            p.createHandIsFullDialog();
            p.discardPile.addToTop(card);
        }

        card = null;
        return true;
    }
}
