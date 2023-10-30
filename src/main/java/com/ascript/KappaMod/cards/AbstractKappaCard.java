package com.ascript.KappaMod.cards;

import basemod.abstracts.CustomCard;

public abstract class AbstractKappaCard extends CustomCard {
    
    public int drownNumber;
    public int baseDrownNumber;
    public boolean upgradedDrownNumber;
    public boolean isDrownNumberModified;
    
    public AbstractKappaCard(final String id,
                             final String name,
                             final String img,
                             final int cost,
                             final String rawDescription,
                             final CardType type,
                             final CardColor color,
                             final CardRarity rarity,
                             final CardTarget target) {
        
        super(id, name, img, cost, rawDescription, type, color, rarity, target);
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDrownNumberModified = false;
    }
    
    public void displayUpgrades() {
        super.displayUpgrades();
        if (upgradedDrownNumber) {
            drownNumber = baseDrownNumber;
            isDrownNumberModified = true;
        }
    }
    
    public void updateDrownNumber(int amount) {
        baseDrownNumber += amount;
        drownNumber = baseDrownNumber;
        upgradedDrownNumber = true;
    }
}