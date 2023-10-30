package com.ascript.KappaMod.cards;

import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.ascript.KappaMod.KappaMod;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.CardStrings;

public abstract class AbstractBetaArtCard extends CustomCard {
    private static final String _ID = KappaMod.makeID(AbstractBetaArtCard.class.getSimpleName());
    private static final CardStrings _cardStrings = CardCrawlGame.languagePack.getCardStrings(_ID);

    public AbstractBetaArtCard(final String id,
                               final String name,
                               final String img,
                               final String betaImg,
                               final int cost,
                               final String rawDescription,
                               final CardType type,
                               final CardColor color,
                               final CardRarity rarity,
                               final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        loadBetaCardImage(betaImg);
    }

    public void loadBetaCardImage(String img) {
        Texture cardTexture;
        if (imgMap.containsKey(img)) {
            cardTexture = imgMap.get(img);
        } else {
            cardTexture = ImageMaster.loadImage(img);
            imgMap.put(img, cardTexture);
        }

        cardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int tw = cardTexture.getWidth();
        int th = cardTexture.getHeight();
        TextureAtlas.AtlasRegion cardImg = new TextureAtlas.AtlasRegion(cardTexture, 0, 0, tw, th);
        ReflectionHacks.setPrivateInherited(this, CustomCard.class, "jokePortrait", cardImg);
    }
}
