package org.clock;
import javafx.scene.paint.Color;

public class ArcPieceBuilder {

    ArcPiece arcPiece = new ArcPiece();

    public static ArcPieceBuilder create() {
        return new ArcPieceBuilder();
    }

    public ArcPieceBuilder x(final double x) {
        arcPiece.x = x;
        return this;
    }

    public ArcPieceBuilder y(final double y) {
        arcPiece.y = y;
        return this;
    }

    public ArcPieceBuilder w(final double w) {
        arcPiece.w = w;
        return this;
    }

    public ArcPieceBuilder h(final double h) {
        arcPiece.h = h;
        return this;
    }

    public ArcPieceBuilder startAngle(final double startAngle) {
        arcPiece.startAngle = startAngle;
        return this;
    }

    public ArcPieceBuilder arcExtent(final double arcExtent) {
        arcPiece.arcExtent = arcExtent;
        return this;
    }

    public ArcPieceBuilder strokeWidth(final double width) {
        arcPiece.strokeWidth = width;
        return this;
    }

    public ArcPieceBuilder strokeColor(final Color c) {
        arcPiece.strokeColor = c;
        return this;
    }

    public ArcPieceBuilder clockwise() {
        arcPiece.clockwise = true;
        return this;
    }

    public ArcPieceBuilder counterClockwise() {
        arcPiece.clockwise = false;
        return this;
    }

    public ArcPieceBuilder displayTimePerFrameMillis(final long millis) {
        arcPiece.displayTimePerFrameMillis = millis;
        return this;
    }

    public ArcPieceBuilder pixelsToMove(final double numPixels) {
        arcPiece.pixelsToMove = numPixels;
        return this;
    }

    public ArcPiece build() {
        return arcPiece;
    }
}
