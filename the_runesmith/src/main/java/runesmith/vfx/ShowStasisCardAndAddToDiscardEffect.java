/*    */ package runesmith.vfx;
/*    */ 
/*    */ import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*    */ import com.badlogic.gdx.math.MathUtils;
/*    */ import com.megacrit.cardcrawl.cards.AbstractCard;
/*    */ import com.megacrit.cardcrawl.core.Settings;
/*    */ import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
/*    */ import com.megacrit.cardcrawl.vfx.combat.CardPoofEffect;
/*    */ 
/*    */ public class ShowStasisCardAndAddToDiscardEffect extends AbstractGameEffect{
/*    */   private static final float EFFECT_DUR = 0.5F;
/*    */   private AbstractCard card;
/* 16 */   private static final float PADDING = 30.0F * Settings.scale;
/*    */   
/*    */   public ShowStasisCardAndAddToDiscardEffect(AbstractCard srcCard, float x, float y) {
/* 19 */     this.card = srcCard.makeStatEquivalentCopy();
/* 20 */     this.duration = EFFECT_DUR;
/* 21 */     this.card.target_x = x;
/* 22 */     this.card.target_y = y;
/* 23 */     AbstractDungeon.effectsQueue.add(new CardPoofEffect(this.card.target_x, this.card.target_y));
/* 24 */     this.card.drawScale = 0.75F;
/* 25 */     this.card.targetDrawScale = 0.75F;
/* 26 */     com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("CARD_OBTAIN");
/* 27 */     AbstractDungeon.player.discardPile.addToTop(srcCard);
/*    */   }
/*    */   
/*    */   public ShowStasisCardAndAddToDiscardEffect(AbstractCard card) {
/* 31 */     this.card = card;
/* 32 */     this.duration = EFFECT_DUR;
/* 33 */     identifySpawnLocation(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F);
/* 34 */     AbstractDungeon.effectsQueue.add(new CardPoofEffect(card.target_x, card.target_y));
/* 35 */     card.drawScale = 0.01F;
/* 36 */     card.targetDrawScale = 1.0F;
/* 37 */     AbstractDungeon.player.discardPile.addToTop(card);
/*    */   }
/*    */   
/*    */   private void identifySpawnLocation(float x, float y) {
/* 41 */     int effectCount = 0;
/* 42 */     for (com.megacrit.cardcrawl.vfx.AbstractGameEffect e : AbstractDungeon.effectList) {
/* 43 */       if ((e instanceof ShowStasisCardAndAddToDiscardEffect)) {
/* 44 */         effectCount++;
/*    */       }
/*    */     }
/*    */     
/* 48 */     this.card.target_y = (Settings.HEIGHT * 0.5F);
/*    */     
/* 50 */     switch (effectCount) {
/*    */     case 0: 
/* 52 */       this.card.target_x = (Settings.WIDTH * 0.5F);
/* 53 */       break;
/*    */     case 1: 
/* 55 */       this.card.target_x = (Settings.WIDTH * 0.5F - PADDING - AbstractCard.IMG_WIDTH);
/* 56 */       break;
/*    */     case 2: 
/* 58 */       this.card.target_x = (Settings.WIDTH * 0.5F + PADDING + AbstractCard.IMG_WIDTH);
/* 59 */       break;
/*    */     case 3: 
/* 61 */       this.card.target_x = (Settings.WIDTH * 0.5F - (PADDING + AbstractCard.IMG_WIDTH) * 2.0F);
/* 62 */       break;
/*    */     case 4: 
/* 64 */       this.card.target_x = (Settings.WIDTH * 0.5F + (PADDING + AbstractCard.IMG_WIDTH) * 2.0F);
/* 65 */       break;
/*    */     default: 
/* 67 */       this.card.target_x = MathUtils.random(Settings.WIDTH * 0.1F, Settings.WIDTH * 0.9F);
/* 68 */       this.card.target_y = MathUtils.random(Settings.HEIGHT * 0.2F, Settings.HEIGHT * 0.8F);
/*    */     }
/*    */     
/*    */   }
/*    */   
/*    */   public void update()
/*    */   {
/* 75 */     this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
/* 76 */     this.card.update();
/*    */     
/* 78 */     if (this.duration < 0.0F) {
/* 79 */       this.isDone = true;
/* 80 */       this.card.shrink();
/* 81 */       AbstractDungeon.getCurrRoom().souls.discard(this.card, true);
/*    */     }
/*    */   }
/*    */   
/*    */   public void render(SpriteBatch sb)
/*    */   {
/* 87 */     if (!this.isDone) {
/* 88 */       this.card.render(sb);
/*    */     }
/*    */   }
/*    */   
/*    */   public void dispose() {}
/*    */ }


/* Location:              C:\Users\Nook\Documents\StS_Modding\lib\desktop-1.0.jar!\com\megacrit\cardcrawl\vfx\cardManip\ShowStasisCardAndAddToDiscardEffect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */