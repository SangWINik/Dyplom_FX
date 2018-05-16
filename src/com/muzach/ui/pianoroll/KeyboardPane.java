package com.muzach.ui.pianoroll;

import javafx.beans.NamedArg;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class KeyboardPane extends Pane {
    
    private int octaveCount;
    private int laneHeight;
    
    public KeyboardPane(@NamedArg(value = "octaveCount", defaultValue = "4") int octaveCount, @NamedArg(value = "laneHeight", defaultValue = "4") int laneHeight) {
        this.octaveCount = octaveCount;
        this.laneHeight = laneHeight;
        this.setPrefWidth(60);
        this.draw();
    }
    
    private void draw(){
        for (int i = 0; i < octaveCount; i++) {
            Color blackLaneFill = new Color(0, 0, 0, 1);
            Color whiteLaneFill = new Color(1, 1, 1, 1);
            Rectangle b = new Rectangle(0, i*12*laneHeight + 0*laneHeight, getPrefWidth(), laneHeight);
            b.setStroke(Color.BLACK);
            b.setStrokeType(StrokeType.INSIDE);
            b.setFill(whiteLaneFill);
            Rectangle as = new Rectangle(0, i*12*laneHeight + 1*laneHeight, getPrefWidth(), laneHeight);
            as.setFill(blackLaneFill);
            Rectangle a = new Rectangle(0, i*12*laneHeight + 2*laneHeight, getPrefWidth(), laneHeight);
            a.setStroke(Color.BLACK);
            a.setStrokeType(StrokeType.INSIDE);
            a.setFill(whiteLaneFill);
            Rectangle gs = new Rectangle(0, i*12*laneHeight + 3*laneHeight, getPrefWidth(), laneHeight);
            gs.setFill(blackLaneFill);
            Rectangle g = new Rectangle(0, i*12*laneHeight + 4*laneHeight, getPrefWidth(), laneHeight);
            g.setStroke(Color.BLACK);
            g.setStrokeType(StrokeType.INSIDE);
            g.setFill(whiteLaneFill);
            Rectangle fs = new Rectangle(0, i*12*laneHeight + 5*laneHeight, getPrefWidth(), laneHeight);
            fs.setFill(blackLaneFill);
            Rectangle f = new Rectangle(0, i*12*laneHeight + 6*laneHeight, getPrefWidth(), laneHeight);
            f.setStroke(Color.BLACK);
            f.setStrokeType(StrokeType.INSIDE);
            f.setFill(whiteLaneFill);
            Rectangle e = new Rectangle(0, i*12*laneHeight + 7*laneHeight, getPrefWidth(), laneHeight);
            e.setStroke(Color.BLACK);
            e.setStrokeType(StrokeType.INSIDE);
            e.setFill(whiteLaneFill);
            Rectangle ds = new Rectangle(0, i*12*laneHeight + 8*laneHeight, getPrefWidth(), laneHeight);
            ds.setFill(blackLaneFill);
            Rectangle d = new Rectangle(0, i*12*laneHeight + 9*laneHeight, getPrefWidth(), laneHeight);
            d.setStroke(Color.BLACK);
            d.setStrokeType(StrokeType.INSIDE);
            d.setFill(whiteLaneFill);
            Rectangle cs = new Rectangle(0, i*12*laneHeight + 10*laneHeight, getPrefWidth(), laneHeight);
            cs.setFill(blackLaneFill);
            Rectangle c = new Rectangle(0, i*12*laneHeight + 11*laneHeight, getPrefWidth(), laneHeight);
            c.setStroke(Color.BLACK);
            c.setStrokeType(StrokeType.INSIDE);
            c.setFill(whiteLaneFill);

            this.getChildren().addAll(c, cs, d, ds, e, f, fs, g, gs, a, as, b);
        }
    }
}
