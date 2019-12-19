package runesmith.relics;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

//Thanks kio
public abstract class AbstractRunesmithRelic extends AbstractRelic {

    private String basePath;
    private boolean hasLarge;

    public AbstractRunesmithRelic(String setId, String basePath, RelicTier tier, LandingSound sfx, boolean hasLarge) {
        super(setId, "", tier, sfx);
        this.basePath = basePath;
        this.hasLarge = hasLarge;

        loadImages();
    }

    public AbstractRunesmithRelic(String setId, String basePath, RelicTier tier, LandingSound sfx){
        this(setId, basePath, tier, sfx, false);
    }

    public Texture getLargeRelicImage(){
        if (largeImg != null && largeImg.getTextureData() instanceof FileTextureData) {
            FileHandle file = ((FileTextureData) largeImg.getTextureData()).getFileHandle();
            return ImageMaster.loadImage(file.path());
        }
        return null;
    }

    private void loadImages(){
        img = ImageMaster.loadImage(basePath);
        outlineImg = ImageMaster.loadImage(basePath.replace(".png", "_o.png"));
        if(hasLarge) largeImg = ImageMaster.loadImage(basePath.replace(".png", "_l.png"));
    }

    public AbstractRelic makeCopy() {
        try {
            return this.getClass().newInstance();
        } catch (IllegalAccessException | InstantiationException var2) {
            throw new RuntimeException("BaseMod failed to auto-generate makeCopy for relic: " + this.relicId);
        }
    }
}
