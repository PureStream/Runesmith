package runesmith.ui;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;

public class EnergyOrbBeige extends CustomEnergyOrb {
    Texture ENERGY_BEIGE_LAYER1 = ImageMaster.loadImage("images/ui/beige/1.png");
    Texture ENERGY_BEIGE_LAYER2 = ImageMaster.loadImage("images/ui/beige/2.png");
    Texture ENERGY_BEIGE_LAYER3 = ImageMaster.loadImage("images/ui/beige/3.png");
    Texture ENERGY_BEIGE_LAYER4 = ImageMaster.loadImage("images/ui/beige/4.png");
    Texture ENERGY_BEIGE_LAYER5 = ImageMaster.loadImage("images/ui/beige/5.png");
    Texture ENERGY_BEIGE_LAYER6 = ImageMaster.loadImage("images/ui/beige/border.png");
    Texture ENERGY_BEIGE_LAYER1D = ImageMaster.loadImage("images/ui/beige/1d.png");
    Texture ENERGY_BEIGE_LAYER2D = ImageMaster.loadImage("images/ui/beige/2d.png");
    Texture ENERGY_BEIGE_LAYER3D = ImageMaster.loadImage("images/ui/beige/3d.png");
    Texture ENERGY_BEIGE_LAYER4D = ImageMaster.loadImage("images/ui/beige/4d.png");
    Texture ENERGY_BEIGE_LAYER5D = ImageMaster.loadImage("images/ui/beige/5d.png");
    Texture ENERGY_BEIGE_MASK = ImageMaster.loadImage("images/ui/beige/mask.png");
    public static float fontScale = 1.0F;
    private static final float ORB_IMG_SCALE;
    private float angle4 = 0.0F;
    private float angle3 = 0.0F;
    private float angle2 = 0.0F;
    private float angle1 = 0.0F;
    private FrameBuffer fbo;
//    private SpriteBatch sbTmp;

    public EnergyOrbBeige() {
        super((String[]) null, (String) null, (float[]) null);
        this.fbo = new FrameBuffer(Format.RGBA8888, Settings.WIDTH, Settings.HEIGHT, false, false);
        //        this.sbTmp = new SpriteBatch();
    }

    public void updateOrb(int orbCount) {
        if (orbCount == 0) {
            this.angle1 += Gdx.graphics.getDeltaTime() * -8.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 5.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -4.0F;
            this.angle4 += Gdx.graphics.getDeltaTime() * 8.0F;
        } else {
            this.angle1 += Gdx.graphics.getDeltaTime() * -40.0F;
            this.angle2 += Gdx.graphics.getDeltaTime() * 20.0F;
            this.angle3 += Gdx.graphics.getDeltaTime() * -16.0F;
            this.angle4 += Gdx.graphics.getDeltaTime() * 40.0F;
        }

    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {

        if (enabled) {
            sb.setColor(Color.WHITE);
            sb.end();

            this.fbo.begin();
            {
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true, true, true, true);
                sb.begin();
                {
                    sb.setColor(Color.WHITE);
                    sb.setBlendFunction(-1, -1);//disable spritebatch blending override
                    Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    sb.draw(this.ENERGY_BEIGE_LAYER1, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
                    sb.draw(this.ENERGY_BEIGE_LAYER2, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 256, 256, false, false);
                    sb.draw(this.ENERGY_BEIGE_LAYER3, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 256, 256, false, false);
                    sb.setBlendFunction(770, 1);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.5F));
                    sb.draw(this.ENERGY_BEIGE_LAYER4, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 256, 256, false, false);
                    sb.setBlendFunction(0, 770);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                    sb.draw(this.ENERGY_BEIGE_MASK, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                    sb.setBlendFunction(770, 771);
                }
                sb.end();
            }
            this.fbo.end();

            sb.begin();
            sb.draw(this.fbo.getColorBufferTexture(), 0.0F, (float) (-Settings.HEIGHT) + 2.0F * current_y);
            sb.draw(this.ENERGY_BEIGE_LAYER5, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
            sb.draw(this.ENERGY_BEIGE_LAYER6, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        } else {
            sb.setColor(Color.WHITE);
            sb.end();

            this.fbo.begin();
            {
                Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true, true, true, true);
                sb.begin();
                {
                    sb.setColor(Color.WHITE);
                    sb.setBlendFunction(-1, -1);//disable spritebatch blending overrride
                    Gdx.gl.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
                    sb.draw(this.ENERGY_BEIGE_LAYER1D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle1, 0, 0, 256, 256, false, false);
                    sb.draw(this.ENERGY_BEIGE_LAYER2D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle2, 0, 0, 256, 256, false, false);
                    sb.draw(this.ENERGY_BEIGE_LAYER3D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle3, 0, 0, 256, 256, false, false);
                    sb.draw(this.ENERGY_BEIGE_LAYER4D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, this.angle4, 0, 0, 256, 256, false, false);
                    sb.setBlendFunction(0, 770);
                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
                    sb.draw(this.ENERGY_BEIGE_MASK, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
                    sb.setBlendFunction(770, 771);
                }
                sb.end();
            }
            this.fbo.end();

            sb.begin();
            sb.draw((Texture) this.fbo.getColorBufferTexture(), 0.0F, (float) (-Settings.HEIGHT) + 2.0F * current_y);
            sb.draw(this.ENERGY_BEIGE_LAYER5D, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
            sb.draw(this.ENERGY_BEIGE_LAYER6, current_x - 128.0F, current_y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0.0F, 0, 0, 256, 256, false, false);
        }

    }

    static {
        ORB_IMG_SCALE = 1.15F * Settings.scale;
    }
}
