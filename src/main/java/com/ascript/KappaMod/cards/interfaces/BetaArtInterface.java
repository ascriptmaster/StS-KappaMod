package com.ascript.KappaMod.cards.interfaces;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.ascript.KappaMod.KappaMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;

public interface BetaArtInterface {
    static void loadBetaCardImage(CustomCard card, String img) {
        Texture cardTexture;
        if (CustomCard.imgMap.containsKey(img)) {
            cardTexture = CustomCard.imgMap.get(img);
        } else {
            cardTexture = ImageMaster.loadImage(img);
            CustomCard.imgMap.put(img, cardTexture);
        }

        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(card, CustomCard.class, "jokePortrait", cardImg);
    }
}
