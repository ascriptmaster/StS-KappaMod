package com.ascript.KappaMod;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.ascript.KappaMod.cards.*;
import com.ascript.KappaMod.characters.TheKappa;
import com.ascript.KappaMod.relics.KappaKap;
import com.ascript.KappaMod.relics.WaterBoard;
import com.ascript.KappaMod.util.IDCheckDontTouchPls;
import com.ascript.KappaMod.util.TextureLoader;
import com.ascript.KappaMod.variables.DrownVariable;
import com.ascript.KappaMod.variables.SplashDamageVariable;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

@SpireInitializer
public class KappaMod implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        PostInitializeSubscriber {
    
    public static final Logger logger = LogManager.getLogger(KappaMod.class.getName());
    private static String modID;
    
    public static Properties theDefaultDefaultSettings = new Properties();
    public static final String ENABLE_PLACEHOLDER_SETTINGS = "enablePlaceholder";
    public static boolean enablePlaceholder = true;
    
    private static final String MODNAME = "The Kappa";
    private static final String AUTHOR = "Ascriptmaster";
    private static final String DESCRIPTION = "If I forgot to change this, please tell me.";
    
    public static final Color DEFAULT_GRAY = CardHelper.getColor(64, 70, 70);
    public static final Color KAPPA_AQUA = CardHelper.getColor(0, 200, 200);

    public static final Color PLACEHOLDER_POTION_LIQUID = CardHelper.getColor(209, 53, 18);
    public static final Color PLACEHOLDER_POTION_HYBRID = CardHelper.getColor(255, 230, 230);
    public static final Color PLACEHOLDER_POTION_SPOTS = CardHelper.getColor(100, 25, 10);
    
    private static final String ATTACK_DEFAULT_GRAY = "KappaModResources/images/512/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY = "KappaModResources/images/512/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY = "KappaModResources/images/512/bg_power_default_gray.png";

    private static final String ATTACK_KAPPA_AQUA = "KappaModResources/images/512/bg_attack_kappa_aqua.png";
    private static final String SKILL_KAPPA_AQUA = "KappaModResources/images/512/bg_skill_kappa_aqua.png";
    private static final String POWER_KAPPA_AQUA = "KappaModResources/images/512/bg_power_kappa_aqua.png";

    private static final String ENERGY_ORB_DEFAULT_GRAY = "KappaModResources/images/512/card_default_gray_orb.png";
    private static final String CARD_ENERGY_ORB = "KappaModResources/images/512/card_small_orb.png";

    private static final String ENERGY_ORB_KAPPA_AQUA = "KappaModResources/images/512/card_kappa_aqua_orb.png";
    private static final String CARD_ENERGY_ORB_KAPPA = "KappaModResources/images/512/card_kappa_aqua_small_orb.png";

    private static final String ATTACK_DEFAULT_GRAY_PORTRAIT = "KappaModResources/images/1024/bg_attack_default_gray.png";
    private static final String SKILL_DEFAULT_GRAY_PORTRAIT = "KappaModResources/images/1024/bg_skill_default_gray.png";
    private static final String POWER_DEFAULT_GRAY_PORTRAIT = "KappaModResources/images/1024/bg_power_default_gray.png";
    private static final String ENERGY_ORB_DEFAULT_GRAY_PORTRAIT = "KappaModResources/images/1024/card_default_gray_orb.png";

    private static final String ATTACK_KAPPA_AQUA_PORTRAIT = "KappaModResources/images/1024/bg_attack_kappa_aqua.png";
    private static final String SKILL_KAPPA_AQUA_PORTRAIT = "KappaModResources/images/1024/bg_skill_kappa_aqua.png";
    private static final String POWER_KAPPA_AQUA_PORTRAIT = "KappaModResources/images/1024/bg_power_kappa_aqua.png";
    private static final String ENERGY_ORB_KAPPA_AQUA_PORTRAIT = "KappaModResources/images/1024/card_kappa_aqua_orb.png";

    private static final String THE_DEFAULT_BUTTON = "KappaModResources/images/charSelect/DefaultCharacterButton.png";
    private static final String THE_DEFAULT_PORTRAIT = "KappaModResources/images/charSelect/DefaultCharacterPortraitBG.png";
    public static final String THE_DEFAULT_SHOULDER_1 = "KappaModResources/images/char/defaultCharacter/shoulder.png";
    public static final String THE_DEFAULT_SHOULDER_2 = "KappaModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String THE_DEFAULT_CORPSE = "KappaModResources/images/char/defaultCharacter/corpse.png";

    private static final String KAPPA_BUTTON = "KappaModResources/images/charSelect/KappaButton.png";
    private static final String KAPPA_PORTRAIT = "KappaModResources/images/charSelect/TempNitoriBG.jpg";
    public static final String KAPPA_SHOULDER_1 = "KappaModResources/images/char/defaultCharacter/shoulder.png";
    public static final String KAPPA_SHOULDER_2 = "KappaModResources/images/char/defaultCharacter/shoulder2.png";
    public static final String KAPPA_CORPSE = "KappaModResources/images/char/mechanist/corpse.png";
    
    public static final String BADGE_IMAGE = "KappaModResources/images/Badge.png";
    
    public static final String THE_DEFAULT_SKELETON_ATLAS = "KappaModResources/images/char/defaultCharacter/skeleton.atlas";
    public static final String THE_DEFAULT_SKELETON_JSON = "KappaModResources/images/char/defaultCharacter/skeleton.json";
    
    public static String makeCardPath(String resourcePath) {
        return getModID() + "Resources/images/cards/" + resourcePath;
    }
    
    public static String makeRelicPath(String resourcePath) {
        return getModID() + "Resources/images/relics/" + resourcePath;
    }
    
    public static String makeRelicOutlinePath(String resourcePath) {
        return getModID() + "Resources/images/relics/outline/" + resourcePath;
    }
    
    public static String makeOrbPath(String resourcePath) {
        return getModID() + "Resources/orbs/" + resourcePath;
    }
    
    public static String makePowerPath(String resourcePath) {
        return getModID() + "Resources/images/powers/" + resourcePath;
    }
    
    public static String makeEventPath(String resourcePath) {
        return getModID() + "Resources/images/events/" + resourcePath;
    }
    
    public KappaMod() {
        logger.info("Subscribe to BaseMod hooks");
        
        BaseMod.subscribe(this);
        
        
        setModID("KappaMod");
        
        
        logger.info("Done subscribing");
        
        logger.info("Creating the color " + TheKappa.Enums.COLOR_AQUA.toString());

        BaseMod.addColor(TheKappa.Enums.COLOR_AQUA, KAPPA_AQUA, KAPPA_AQUA, KAPPA_AQUA,
                KAPPA_AQUA, KAPPA_AQUA, KAPPA_AQUA, KAPPA_AQUA,
                ATTACK_KAPPA_AQUA, SKILL_KAPPA_AQUA, POWER_KAPPA_AQUA, ENERGY_ORB_KAPPA_AQUA,
                ATTACK_KAPPA_AQUA_PORTRAIT, SKILL_KAPPA_AQUA_PORTRAIT, POWER_KAPPA_AQUA_PORTRAIT,
                ENERGY_ORB_KAPPA_AQUA_PORTRAIT, CARD_ENERGY_ORB_KAPPA);
        
        logger.info("Done creating the color");
        
        
        logger.info("Adding mod settings");
        
        
        theDefaultDefaultSettings.setProperty(ENABLE_PLACEHOLDER_SETTINGS, "FALSE");
        try {
            SpireConfig config = new SpireConfig("KappaMod", "KappaModConfig", theDefaultDefaultSettings);
            
            config.load();
            enablePlaceholder = config.getBool(ENABLE_PLACEHOLDER_SETTINGS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Done adding mod settings");
    }
    
    public static void setModID(String ID) {
        Gson coolG = new Gson();
        
        InputStream in = KappaMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }
    
    public static String getModID() {
        return modID;
    }
    
    private static void pathCheck() {
        Gson coolG = new Gson();
        
        InputStream in = KappaMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        String packageName = KappaMod.class.getPackage().getName();
        int packageIndex = packageName.lastIndexOf('.');
        if (packageIndex >= 0) {
            packageName = packageName.substring(packageIndex+1);
        }
        FileHandle resourcePathExists = Gdx.files.internal(getModID() + "Resources");
        if (!modID.equals(EXCEPTION_STRINGS.DEVID)) {
            if (!packageName.equals(getModID())) {
                throw new RuntimeException(EXCEPTION_STRINGS.PACKAGE_EXCEPTION + getModID());
            }
            if (!resourcePathExists.exists()) {
                throw new RuntimeException(EXCEPTION_STRINGS.RESOURCE_FOLDER_EXCEPTION + getModID() + "Resources");
            }
        }
    }
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Default Mod. Hi. =========================");
        KappaMod defaultmod = new KappaMod();
        logger.info("========================= /Default Mod Initialized. Hello World./ =========================");
    }
    
    @Override
    public void receiveEditCharacters() {
        logger.info("Beginning to edit characters. " + "Add " + TheKappa.Enums.KAPPA.toString());

        BaseMod.addCharacter(new TheKappa("the Mechanist", TheKappa.Enums.KAPPA),
                KAPPA_BUTTON, KAPPA_PORTRAIT, TheKappa.Enums.KAPPA);
        
        receiveEditPotions();
        logger.info("Added " + TheKappa.Enums.KAPPA.toString());
    }
    
    @Override
    public void receivePostInitialize() {
        logger.info("Loading badge image and mod options");

        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);

        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton enableNormalsButton = new ModLabeledToggleButton("This is the text which goes next to the checkbox.",
                350.0f, 700.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                enablePlaceholder,
                settingsPanel,
                (label) -> {
                },
                (button) -> {
                    
                    enablePlaceholder = button.enabled;
                    try {
                        
                        SpireConfig config = new SpireConfig("defaultMod", "theDefaultConfig", theDefaultDefaultSettings);
                        config.setBool(ENABLE_PLACEHOLDER_SETTINGS, enablePlaceholder);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
        
        settingsPanel.addUIElement(enableNormalsButton);
        
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        
        //BaseMod.addEvent(IdentityCrisisEvent.ID, IdentityCrisisEvent.class, TheCity.ID);

        
        logger.info("Done loading badge Image and mod options");
    }
    
    public void receiveEditPotions() {
        logger.info("Beginning to edit potions");

        logger.info("Done editing potions");
    }
    
    @Override
    public void receiveEditRelics() {
        logger.info("Adding relics");

        BaseMod.addRelicToCustomPool(new KappaKap(), TheKappa.Enums.COLOR_AQUA);
        BaseMod.addRelicToCustomPool(new WaterBoard(), TheKappa.Enums.COLOR_AQUA);

        UnlockTracker.markRelicAsSeen(WaterBoard.ID);

        logger.info("Done adding relics!");
    }
    
    @Override
    public void receiveEditCards() {
        logger.info("Adding variables");
        
        pathCheck();
        
        logger.info("Add variables");
        
        BaseMod.addDynamicVariable(new SplashDamageVariable());
        BaseMod.addDynamicVariable(new DrownVariable());

        logger.info("Adding and unlocking cards");

        // Add the cards
        // Don't delete these default cards yet. You need 1 of each type and rarity (technically) for your game not to crash
        // when generating card rewards/shop screen items.

        // This method automatically adds any cards so you don't have to manually load them 1 by 1
        // For more specific info, including how to exclude cards from being added:
        // https://github.com/daviscook477/BaseMod/wiki/AutoAdd

        // The ID for this function isn't actually your modid as used for prefixes/by the getModID() method.
        // It's the mod id you give MTS in ModTheSpire.json - by default your artifact ID in your pom.xml

        //TODO: Rename the "DefaultMod" with the modid in your ModTheSpire.json file
        //TODO: The artifact mentioned in ModTheSpire.json is the artifactId in pom.xml you should've edited earlier
        Loader.getClassPool().importPackage("com.ascript.KappaMod");
        new AutoAdd("KappaMod") // ${project.artifactId}
                .packageFilter(KappaBasicStrike.class) // filters to any class in the same package as AbstractDefaultCard, nested packages included
                .setDefaultSeen(true)
                .cards();

        // .setDefaultSeen(true) unlocks the cards
        // This is so that they are all "seen" in the library,
        // for people who like to look at the card list before playing your mod
        
        logger.info("Done adding cards!");
    }
    
    @Override
    public void receiveEditStrings() {
        logger.info("You seeing this?");
        logger.info("Beginning to edit strings for mod with ID: " + getModID());
        
        BaseMod.loadCustomStringsFile(CardStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Card-Strings.json");
        
        BaseMod.loadCustomStringsFile(PowerStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Power-Strings.json");
        
        BaseMod.loadCustomStringsFile(RelicStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Relic-Strings.json");
        
        BaseMod.loadCustomStringsFile(EventStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Event-Strings.json");
        
        BaseMod.loadCustomStringsFile(PotionStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Potion-Strings.json");
        
        BaseMod.loadCustomStringsFile(CharacterStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Character-Strings.json");
        
        BaseMod.loadCustomStringsFile(OrbStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-Orb-Strings.json");

        BaseMod.loadCustomStringsFile(UIStrings.class,
                getModID() + "Resources/localization/eng/KappaMod-UI-Strings.json");
        
        logger.info("Done editing strings");
    }
    
    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/KappaMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
        
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    
    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
