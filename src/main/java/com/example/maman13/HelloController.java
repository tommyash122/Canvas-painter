package com.example.maman13;

import javafx.fxml.FXML;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

//this class will control over the graphic windows that we will draw on
public class HelloController {

    private static final int PANES_BORDER = 0;
    private double x, y;

    public enum shapes {
        LINE,
        CIRCLE,
        RECTANGLE
    }

    private shapes whichShape;
    private Color myColor;
    private boolean colorPicked = false;


    @FXML
    private Pane myPane;

    @FXML
    private ToggleButton fill;

    @FXML
    private ToggleButton NoFill;

    @FXML
    private ColorPicker whichColor;

    @FXML
    void clearWindow() {
        //clears the windows
        myPane.getChildren().clear();
    }

    @FXML
    void undoLastMove() {
        //removes the last shape that we draw on our pane
        if (!myPane.getChildren().isEmpty())
            myPane.getChildren().remove(myPane.getChildren().size() - 1);
    }


    @FXML
    void whichColorPicked() {
        //set the color chosen and indicates that there was a color pick
        setMyColor(whichColor.getValue());
        this.colorPicked = true;
    }

    @FXML
    void circleBtnPressed() {
        //set the shape to circle
        setWhichShape(shapes.CIRCLE);
    }

    @FXML
    void lineBtnPressed() {
        //set the shape to line
        setWhichShape(shapes.LINE);
    }

    @FXML
    void rectBtnPressed() {
        //set the shape to rectangle
        setWhichShape(shapes.RECTANGLE);
    }

    private void drawLine(double startX, double startY, double endX, double endY) {
        //draws the line
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        myPane.getChildren().add(line);
    }

    private void drawCircle(double startX, double startY, double endX, double endY) {
        //draws the circle
        double centCircleX = Math.max(startX, endX) - (Math.max(startX, endX) - Math.min(startX, endX)) / 2;
        double centCircleY = Math.max(startY, endY) - (Math.max(startY, endY) - Math.min(startY, endY)) / 2;

        Circle circle = new Circle();
        circle.setCenterX(centCircleX);
        circle.setCenterY(centCircleY);
        circle.setRadius((distTwoPoints(startX, endX, startY, endY) / 2));

        //we need also to choose to fill/not to fill
        if (!fill.isSelected() && !NoFill.isSelected())
            JOptionPane.showMessageDialog(null,
                    "Please choose if you want your "
                            + getWhichShape() +
                            " to be filled with color or not...");

            //chose to fill without pick what color
        else if (fill.isSelected() && !colorPicked)
            JOptionPane.showMessageDialog(null,
                    "Please select a color for your "
                            + getWhichShape() +
                            "...");

            //if the circle will pass the boarders with its radios
        else if (circle.getCenterY() - circle.getRadius() < PANES_BORDER)
            JOptionPane.showMessageDialog(null,
                    "This " + getWhichShape() +
                            " will pass the limits of our drawing area...");
        else {
            if (NoFill.isSelected()) {
                circle.setFill(Color.TRANSPARENT);
                circle.setStroke(Color.BLACK);
            } else
                circle.setFill(getMyColor());


            myPane.getChildren().add(circle);
        }
    }

    private double distTwoPoints(double x1, double x2, double y1, double y2) {
        //calculate the distance of the two points for the circle measures
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void drawRectangle(double startX, double startY, double endX, double endY) {
        //draw the rectangle
        double rectWidth = Math.abs(endX - startX);
        double rectHeight = Math.abs(endY - startY);

        Rectangle rect = new Rectangle();
        rect.setX(Math.min(startX, endX));
        rect.setY(Math.min(startY, endY));
        rect.setWidth(rectWidth);
        rect.setHeight(rectHeight);

        //we need also to choose to fill/not to fill
        if (!fill.isSelected() && !NoFill.isSelected())
            JOptionPane.showMessageDialog(null,
                    "Please choose if you want your "
                            + getWhichShape() +
                            " to be filled with color or not...");

            //chose to fill without pick what color
        else if (fill.isSelected() && !colorPicked)
            JOptionPane.showMessageDialog(null,
                    "Please select a color for your "
                            + getWhichShape()
                            + "...");

        else {
            if (NoFill.isSelected()) {
                rect.setFill(Color.TRANSPARENT);
                rect.setStroke(Color.BLACK);
            } else
                rect.setFill(getMyColor());

            myPane.getChildren().add(rect);
        }
    }

    @FXML
    void endDraw(MouseEvent event) {

        //if we try to draw outside our panes limits
        if (event.getY() < PANES_BORDER)
            JOptionPane.showMessageDialog(null,
                    "Please don't draw outside the borders...");

        else {
            //release the mouse and draw the shape
            if (getWhichShape() == shapes.LINE)
                drawLine(x, y, event.getX(), event.getY());

            else if (getWhichShape() == shapes.CIRCLE)
                drawCircle(x, y, event.getX(), event.getY());

            else if (getWhichShape() == shapes.RECTANGLE)
                drawRectangle(x, y, event.getX(), event.getY());

            else
                JOptionPane.showMessageDialog(null,
                        "Please pick a shape...");
        }
    }

    @FXML
    void startDraw(MouseEvent event) {
        //the starting point of the shape
        x = event.getX();
        y = event.getY();
    }

    private void setWhichShape(shapes whichShape) {
        this.whichShape = whichShape;
    }

    private shapes getWhichShape() {
        return this.whichShape;
    }

    private void setMyColor(Color color) {
        this.myColor = color;
    }

    private Color getMyColor() {
        return this.myColor;
    }

}
