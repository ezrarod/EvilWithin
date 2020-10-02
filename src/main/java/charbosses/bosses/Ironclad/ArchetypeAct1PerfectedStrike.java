package charbosses.bosses.Ironclad;

import charbosses.cards.curses.EnDoubt;
import charbosses.cards.red.*;
import charbosses.cards.status.EnWound;
import charbosses.relics.CBR_MercuryHourglass;
import charbosses.relics.CBR_NeowsBlessing;
import charbosses.relics.CBR_Vajra;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class ArchetypeAct1PerfectedStrike extends ArchetypeBaseIronclad {

    public ArchetypeAct1PerfectedStrike() {
        super("IC_STRIKE_ARCHETYPE", "Strike");
    }

    public void initialize() {
        //Tuning Recommendation per Act (CARDS): 1 Card Removal, 2 Upgrades, ~6-8 cards added to deck
        //Tuning Recommendation per Act (RELICS): 2 relics and an Event relic (simulate what the Event did)
        //Tuning Recommendation for Act 2-3: At least 1 Rare and 1 Boss Relic in addition to above 2
        //Make the total cards always divisible by 3 - Shuffle should not occur on a partial hand

        //STARTER DECK - 5 Strikes, 4 Defends, 1 Bash

        //1 Strike 1 Defend Removed

        //6 Cards Added, 3 Upgrades:
        //Metallicize+
        //Perfected Strike+
        //Twin Strike
        //Ghostly Armor+
        //Inflame
        //Wild Strike


        /////   RELICS   /////

        addRelic(new CBR_NeowsBlessing());

        addRelic(new CBR_Vajra());
        // addRelic(new CBR_WarPaint());   //Upgrade 2 Defends
        // addRelic(new CBR_Orichalcum());
        // addRelic(new CBR_Serpent());   //Money used to buy Orichalcum

        /////   CARDS   /////
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;//Turn 1
        addToDeck(new EnBash(), extraUpgrades);
        addToDeck(new EnStrikeRed(), false);
        addToDeck(new EnDoubt(), false);

        //Turn 2
        addToDeck(new EnMetallicize(), extraUpgrades); //Removed
        addToDeck(new EnWildStrike(), true);  //TODO - Don't add the Wound
        addToDeck(new EnStrikeRed(), false);
        //addToDeck(new EnDefendRed(), false);

        //Turn 3
        addToDeck(new EnPerfectedStrike(), true);  //6 strikes in deck
        addToDeck(new EnDefendRed(), true);
        addToDeck(new EnTwinStrike(), false);  //Not before Loop

        //Turn 4
        //TODO - True Grit to Exhaust the Wound from Wild Strike
        addToDeck(new EnGhostlyArmor(), true);
        addToDeck(new EnWound(), false);

        //Turn 5
        addToDeck(new EnInflame(), extraUpgrades); //Removed
        //TODO - Pummel  //Removed
        addToDeck(new EnStrikeRed(), false); //Not before Loop

        //INFINITE LOOP
        addToDeck(new EnWildStrike(), true);  //TODO - Don't add the Wound
        addToDeck(new EnGhostlyArmor(), true);
        addToDeck(new EnDoubt(), false);

        addToDeck(new EnTwinStrike(), false);
        //TODO - True Grit to Exhaust the Wound from Wild Strike
        addToDeck(new EnWound(), false);

        addToDeck(new EnBash(), extraUpgrades);  //Never uses during loop
        addToDeck(new EnStrikeRed(), false);
        addToDeck(new EnDefendRed(), true);  //Never uses during loop

        addToDeck(new EnPerfectedStrike(), true);
        addToDeck(new EnStrikeRed(), false);  //Never uses during loop
        addToDeck(new EnStrikeRed(), false);  //Never uses during loop


    }

    @Override
    public ArrayList<AbstractCard> getThisTurnCards() {
        ArrayList<AbstractCard> cardsList = new ArrayList<>();
        boolean extraUpgrades = AbstractDungeon.ascensionLevel >= 4;
        if (looped) {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnWildStrike(), true);  //TODO - Don't add the Wound
                    addToList(cardsList, new EnGhostlyArmor(), true);
                    addToList(cardsList, new EnDoubt(), false);
                    break;
                case 1:
                    addToList(cardsList, new EnTwinStrike(), false);
                    //TODO - True Grit to Exhaust the Wound from Wild Strike
                    addToList(cardsList, new EnWound(), false);
                    break;
                case 2:
                    addToList(cardsList, new EnBash(), extraUpgrades);  //Never uses during loop
                    addToList(cardsList, new EnStrikeRed(), false);
                    addToList(cardsList, new EnDefendRed(), true);  //Never uses during loop
                    break;
                case 3:
                    addToList(cardsList, new EnPerfectedStrike(), true);
                    addToList(cardsList, new EnStrikeRed(), false);  //Never uses during loop
                    addToList(cardsList, new EnStrikeRed(), false);  //Never uses during loop
                    break;
            }
        } else {
            switch (turn) {
                case 0:
                    addToList(cardsList, new EnBash(), extraUpgrades);
                    addToList(cardsList, new EnStrikeRed(), false);
                    addToList(cardsList, new EnDoubt(), false);
                    break;
                case 1:
                    addToList(cardsList, new EnMetallicize(), extraUpgrades); //Removed
                    addToList(cardsList, new EnWildStrike(), true);  //TODO - Don't add the Wound
                    addToList(cardsList, new EnStrikeRed(), false);
                    break;
                case 2:
                    addToList(cardsList, new EnPerfectedStrike(), true);  //6 strikes in deck
                    addToList(cardsList, new EnDefendRed(), true);
                    addToList(cardsList, new EnTwinStrike(), false);  //Not before Loop
                    break;
                case 3:
                    //TODO - True Grit to Exhaust the Wound from Wild Strike
                    addToList(cardsList, new EnGhostlyArmor(), true);
                    addToList(cardsList, new EnWound(), false);
                    break;
                case 4:
                    addToList(cardsList, new EnInflame(), extraUpgrades); //Removed
                    //TODO - Pummel  //Removed
                    addToList(cardsList, new EnStrikeRed(), false); //Not before Loop
                    break;
            }
        }
        turn++;
        if (turn > 3 && !looped) looped = true;
        return cardsList;
    }


    @Override
    public void initializeBonusRelic() {
        addRelic(new CBR_MercuryHourglass());
    }
}