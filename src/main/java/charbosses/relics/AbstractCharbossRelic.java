package charbosses.relics;

import charbosses.bosses.AbstractBossDeckArchetype;
import charbosses.cards.AbstractBossCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import charbosses.bosses.AbstractCharBoss;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.util.ArrayList;

public abstract class AbstractCharbossRelic extends AbstractRelic {

	public AbstractCharBoss owner;
	private AbstractRelic baseRelic;

	public AbstractCharbossRelic(String setId, String imgName, RelicTier tier, LandingSound sfx) {
		super(setId, imgName, tier, sfx);
        isSeen = true;
        UnlockTracker.markRelicAsSeen(this.relicId);
	}


    public AbstractCharbossRelic(String setId, RelicTier tier, LandingSound sfx, Texture texture) {
        super(setId, "", tier, sfx);
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        this.setTexture(texture);

        isSeen = true;
        UnlockTracker.markRelicAsSeen(this.relicId);
    }

    public void setTexture(Texture t) {
        this.img = t;
        this.largeImg = t;
        this.outlineImg = t;
    }

	public AbstractCharbossRelic(AbstractRelic baseRelic) {
		super(baseRelic.relicId, baseRelic.imgUrl, baseRelic.tier, LandingSound.CLINK);
		this.baseRelic = baseRelic;
        isSeen = true;
        UnlockTracker.markRelicAsSeen(this.relicId);
	}
	public AbstractCharbossRelic(AbstractRelic baseRelic, RelicTier tier) {
		super(baseRelic.relicId, baseRelic.imgUrl, tier, LandingSound.CLINK);
		this.baseRelic = baseRelic;
        isSeen = true;
        UnlockTracker.markRelicAsSeen(this.relicId);
	}

	private static float START_X;
	private static float START_Y;
	private static float PAD_X;


    public void reorganizeObtain(final AbstractCharBoss p, final int slot, final boolean callOnEquip, final int relicAmount) {
    	this.owner = p;
        this.isDone = true;
        this.isObtained = true;
        p.relics.add(this);
        this.currentX = START_X + slot * PAD_X;
        this.currentY = START_Y;
        this.targetX = this.currentX;
        this.targetY = this.currentY;
        this.hb.move(this.currentX, this.currentY);
        if (callOnEquip) {
            this.onEquip();
            this.relicTip();
        }
    }

    public void instantObtain(final AbstractCharBoss p, final int slot, final boolean callOnEquip) {
    	this.owner = p;
        if (this.relicId.equals("Circlet") && p != null && p.hasRelic("Circlet")) {
            final AbstractRelic relic;
            final AbstractRelic circ = relic = p.getRelic("Circlet");
            ++relic.counter;
            circ.flash();
            this.isDone = true;
            this.isObtained = true;
            this.discarded = true;
        }
        else {
            this.isDone = true;
            this.isObtained = true;
            if (slot >= p.relics.size()) {
                p.relics.add(this);
            }
            else {
                p.relics.set(slot, this);
            }
            this.currentX = START_X + slot * PAD_X;
            this.currentY = START_Y;
            this.targetX = this.currentX;
            this.targetY = this.currentY;
            this.hb.move(this.currentX, this.currentY);
            if (callOnEquip) {
                this.onEquip();
                this.relicTip();
            }
            this.getUpdatedDescription();
            //AbstractDungeon.topPanel.adjustRelicHbs();
        }
    }

    public void instantObtain(AbstractCharBoss boss) {
    	this.owner = boss;
    	this.instantObtain();
    }
    public void instantObtain() {
    	if (this.owner == null) {
            AbstractBossDeckArchetype.logger.info("OWNER IS NULL!");
    		return;
    	}
        if (this.relicId == "Circlet" && this.owner.hasRelic("Circlet")) {
            final AbstractRelic relic;
            final AbstractRelic circ = relic = this.owner.getRelic("Circlet");
            ++relic.counter;
            circ.flash();
        }
        else {
            this.playLandingSFX();
            this.isDone = true;
            this.isObtained = true;
            this.currentX = Settings.WIDTH - START_X - this.owner.relics.size() * PAD_X;
            this.currentY = START_Y;
            this.targetX = this.currentX;
            this.targetY = this.currentY;
            this.flash();
            this.owner.relics.add(this);
            this.hb.move(this.currentX, this.currentY);
            this.onEquip();
            this.relicTip();
        }
        //AbstractDungeon.topPanel.adjustRelicHbs();
    }

    public void obtain() {
        if (this.relicId == "Circlet" && this.owner.hasRelic("Circlet")) {
            final AbstractRelic relic;
            final AbstractRelic circ = relic = this.owner.getRelic("Circlet");
            ++relic.counter;
            circ.flash();
            this.hb.hovered = false;
        }
        else {
            this.hb.hovered = false;
            final int row = this.owner.relics.size();
            this.targetX = Settings.WIDTH - START_X - row * AbstractRelic.PAD_X;
            this.targetY = START_Y;
            this.owner.relics.add(this);
            this.relicTip();
        }
    }

    public void obtain(AbstractCharBoss boss) {
    	this.owner = boss;
    	this.obtain();
    }
    public int getColumn() {
        return this.owner.relics.indexOf(this);
    }

    public void update() {
        //this.updateFlash();
        if (!this.isDone) {
            super.update();
        }
        else
        {
        	if (this.flashTimer != 0.0f) {
                this.flashTimer -= Gdx.graphics.getDeltaTime();
                if (this.flashTimer < 0.0f) {
                    if (this.pulse) {
                        this.flashTimer = 1.0f;
                    }
                    else {
                        this.flashTimer = 0.0f;
                    }
                }
            }
            //if (this.owner != null && this.owner.relics.indexOf(this) / 25 == AbstractRelic.relicPage) {
                this.hb.update();
            /*}
            else {
                this.hb.hovered = false;
            }*/

                //note to self: delete these comments later

            /*if (this.hb.hovered && AbstractDungeon.topPanel.potionUi.isHidden) {
                this.scale = Settings.scale * 1.25f;
                CardCrawlGame.cursor.changeType(GameCursor.CursorType.INSPECT);
            }
            else {
                this.scale = MathHelper.scaleLerpSnap(this.scale, Settings.scale);
            }
            this.updateRelicPopupClick();*/
            updateBosscharRelicPopupClick();
        }
    }


    private void updateBosscharRelicPopupClick() {
        if (this.hb.hovered && InputHelper.justClickedLeft) {
            this.hb.clickStarted = true;
        }
        if (this.hb.clicked || (this.hb.hovered && CInputActionSet.select.isJustPressed())) {
            CardCrawlGame.relicPopup.open(this);
            CInputActionSet.select.unpress();
            this.hb.clicked = false;
            this.hb.clickStarted = false;
        }
    }

    public void playLandingSFX() {
    	if (this.baseRelic == null) {
    		super.playLandingSFX();
    	} else {
    		this.baseRelic.playLandingSFX();
    	}
    }

    public boolean canSpawn() {
        return false;
    }

    public void modifyCardsOnCollect(ArrayList<AbstractBossCard> groupToModify){

    }
    
    static {
        START_X = 364.0f * Settings.scale;
        START_Y = Settings.HEIGHT - 174.0f * Settings.scale;
        PAD_X = 72.0f * Settings.scale;
    }
}
