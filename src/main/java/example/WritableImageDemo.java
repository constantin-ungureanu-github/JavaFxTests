package example;

import java.nio.IntBuffer;
import java.util.Arrays;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class WritableImageDemo extends Application {
    private Image src;
    private WritableImage dest;
    private int kernelSize = 1;
    private int width;
    private int height;

    private RadioButton blurButton;
    private RadioButton blur2Button;
    private RadioButton mosaicButton;

    @Override
    public void start(final Stage stage) {
        final AnchorPane root = new AnchorPane();

        initImage(root);

        final Scene scene = new Scene(root);

        stage.setTitle("WritableImage Demo");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void initImage(final AnchorPane root) {
        src = new Image("panda.bmp");
        final ImageView srcView = new ImageView(src);
        root.getChildren().add(srcView);
        AnchorPane.setTopAnchor(srcView, 0.0);
        AnchorPane.setLeftAnchor(srcView, 0.0);

        width = (int) src.getWidth();
        height = (int) src.getHeight();
        root.setPrefSize(width * 2.0, height + 50);

        dest = new WritableImage(width, height);
        final ImageView destView = new ImageView(dest);
        destView.setTranslateX(width);
        root.getChildren().add(destView);
        AnchorPane.setTopAnchor(destView, 0.0);
        AnchorPane.setRightAnchor(destView, (double) width);

        final Slider slider = new Slider(0, 10, kernelSize);
        slider.setPrefSize(width, 50);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickUnit(1.0);
        slider.setMinorTickCount(0);

        slider.valueProperty().addListener((InvalidationListener) o -> {
            final DoubleProperty value = (DoubleProperty) o;
            final int intValue = (int) value.get();
            if (intValue != kernelSize) {
                kernelSize = intValue;
                if (blurButton.isSelected()) {
                    blur();
                } else if (blur2Button.isSelected()) {
                    blur2();
                } else {
                    mosaic();
                }
            }
        });

        root.getChildren().add(slider);
        AnchorPane.setBottomAnchor(slider, 0.0);
        AnchorPane.setRightAnchor(slider, 10.0);

        final HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefWidth(width);
        hbox.setPrefHeight(50);
        root.getChildren().add(hbox);
        AnchorPane.setBottomAnchor(hbox, 0.0);
        AnchorPane.setLeftAnchor(hbox, 10.0);

        final ToggleGroup group = new ToggleGroup();
        blurButton = new RadioButton("Blur");
        blurButton.setToggleGroup(group);
        blurButton.setSelected(true);
        hbox.getChildren().add(blurButton);
        blur2Button = new RadioButton("Blur2");
        blur2Button.setToggleGroup(group);
        hbox.getChildren().add(blur2Button);
        mosaicButton = new RadioButton("Mosaic");
        mosaicButton.setToggleGroup(group);
        hbox.getChildren().add(mosaicButton);

        blur();
    }

    private void blur() {
        final PixelReader reader = src.getPixelReader();
        final PixelWriter writer = dest.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                double alpha = 0;
                int count = 0;
                for (int i = -kernelSize; i <= kernelSize; i++) {
                    for (int j = -kernelSize; j <= kernelSize; j++) {
                        if (((x + i) < 0) || ((x + i) >= width) || ((y + j) < 0) || ((y + j) >= height)) {
                            continue;
                        }
                        final Color color = reader.getColor(x + i, y + j);
                        red += color.getRed();
                        green += color.getGreen();
                        blue += color.getBlue();
                        alpha += color.getOpacity();
                        count++;
                    }
                }
                final Color blurColor = Color.color(red / count, green / count, blue / count, alpha / count);
                writer.setColor(x, y, blurColor);
            }
        }
    }

    private void blur2() {
        final PixelReader reader = src.getPixelReader();
        final PixelWriter writer = dest.getPixelWriter();
        final WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int centerX = x - kernelSize;
                int centerY = y - kernelSize;
                int kernelWidth = (kernelSize * 2) + 1;
                int kernelHeight = (kernelSize * 2) + 1;

                if (centerX < 0) {
                    centerX = 0;
                    kernelWidth = x + kernelSize;
                } else if ((x + kernelSize) >= width) {
                    kernelWidth = width - centerX;
                }

                if (centerY < 0) {
                    centerY = 0;
                    kernelHeight = y + kernelSize;
                } else if ((y + kernelSize) >= height) {
                    kernelHeight = height - centerY;
                }

                final int[] buffer = new int[kernelWidth * kernelHeight];
                reader.getPixels(centerX, centerY, kernelWidth, kernelHeight, format, buffer, 0, kernelWidth);

                int alpha = 0;
                int red = 0;
                int green = 0;
                int blue = 0;

                for (final int color : buffer) {
                    alpha += (color >>> 24);
                    red += ((color >>> 16) & 0xFF);
                    green += ((color >>> 8) & 0xFF);
                    blue += (color & 0xFF);
                }
                alpha = alpha / kernelWidth / kernelHeight;
                red = red / kernelWidth / kernelHeight;
                green = green / kernelWidth / kernelHeight;
                blue = blue / kernelWidth / kernelHeight;

                final int blurColor = (alpha << 24) + (red << 16) + (green << 8) + blue;
                writer.setArgb(x, y, blurColor);
            }
        }
    }

    private void mosaic() {
        final PixelReader reader = src.getPixelReader();
        final PixelWriter writer = dest.getPixelWriter();
        final WritablePixelFormat<IntBuffer> format = WritablePixelFormat.getIntArgbInstance();

        for (int x = kernelSize; x < (width - (kernelSize * 2)); x += (kernelSize * 2) + 1) {
            for (int y = kernelSize; y < (height - (kernelSize * 2)); y += (kernelSize * 2) + 1) {
                final int kernelWidth = (kernelSize * 2) + 1;
                final int kernelHeight = (kernelSize * 2) + 1;

                final int[] buffer = new int[kernelWidth * kernelHeight];
                reader.getPixels(x, y, kernelWidth, kernelHeight, format, buffer, 0, kernelWidth);

                int alpha = 0;
                int red = 0;
                int green = 0;
                int blue = 0;

                for (final int color : buffer) {
                    alpha += (color >>> 24);
                    red += ((color >>> 16) & 0xFF);
                    green += ((color >>> 8) & 0xFF);
                    blue += (color & 0xFF);
                }

                alpha = alpha / kernelWidth / kernelHeight;
                red = red / kernelWidth / kernelHeight;
                green = green / kernelWidth / kernelHeight;
                blue = blue / kernelWidth / kernelHeight;

                final int blurColor = (alpha << 24) + (red << 16) + (green << 8) + blue;
                Arrays.fill(buffer, blurColor);
                writer.setPixels(x, y, kernelWidth, kernelHeight, format, buffer, 0, kernelWidth);
            }
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }
}
