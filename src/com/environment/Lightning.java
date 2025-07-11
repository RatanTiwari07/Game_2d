package com.environment;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.image.BufferedImage;

import com.main.GamePanel;

public class Lightning {

    GamePanel gp;
    BufferedImage darknessFilter ;

    public final int day = 1;
    public final int dusk = 2;
    public final int night = 3;
    public final int dawn  = 4;
    public int currentState = day ;
    public int stateCounter = 0;

    float currComposite = 0f;

    public Lightning (GamePanel gp) {

        this.gp = gp;
        getLightSource();
    }

    public void getLightSource () {

        darknessFilter = new BufferedImage(gp.screenWidth, gp.screenHeight , BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D)darknessFilter.getGraphics();

        if (gp.player.currentLight == null) {

            g2.setColor(new Color (0,0,0,0.98f));

        } else {

            Color color [] = new Color [12];
            float fraction [] = new float [12];

            int centerX = gp.player.screenX + (gp.tileSize/2);
            int centerY = gp.player.screenY + (gp.tileSize/2);

            color[0] = new Color(0,0,0,0.20f);
            color[1] = new Color(0,0,0,0.42f);
            color[2] = new Color(0,0,0,0.52f);
            color[3] = new Color(0,0,0,0.61f);
            color[4] = new Color(0,0,0,0.69f);
            color[5] = new Color(0,0,0,0.76f);
            color[6] = new Color(0,0,0,0.82f);
            color[7] = new Color(0,0,0,0.87f);
            color[8] = new Color(0,0,0,0.91f);
            color[9] = new Color(0,0,0,0.94f);
            color[10] = new Color(0,0,0,0.96f);
            color[11] = new Color(0,0,0,0.98f);

            fraction[0] = 0f;
            fraction[1] = 0.4f;
            fraction[2] = 0.5f;
            fraction[3] = 0.6f;
            fraction[4] = 0.65f;
            fraction[5] = 0.7f;
            fraction[6] = 0.75f;
            fraction[7] = 0.80f;
            fraction[8] = 0.85f;
            fraction[9] = 0.90f;
            fraction[10] = 0.95f;
            fraction[11] = 1f;

            RadialGradientPaint rgp = new RadialGradientPaint (centerX, centerY, gp.player.currentLight.lightRadius, fraction, color);

            g2.setPaint(rgp);

        }

        g2.fillRect(0, 0, gp.screenWidth,gp.screenHeight);

        g2.dispose();

    }

    public void update () {

        if (gp.player.lightUpdated) {
            getLightSource();
            gp.player.lightUpdated = false;
        }

        if (currentState == day) {

            stateCounter ++;

            if (stateCounter == 1200) {
                stateCounter = 0;
                currentState = dusk;
            }

        } else if (currentState == dusk) {

            currComposite += 0.001f;

            if (currComposite > 0.98f) {
                currentState = night;
                currComposite = 0.98f;
            }

        } else if (currentState == night) {

            stateCounter ++;

            if (stateCounter == 1200) {
                stateCounter = 0;
                currentState = dawn;
            }

        } else if (currentState == dawn) {

            currComposite -= 0.001f;

            if (currComposite < 0f) {
                currentState = day;
                currComposite = 0f;
            }
        }

    }

    public void draw (Graphics2D g2) {

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, currComposite));
        g2.drawImage(darknessFilter , 0, 0, null);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
