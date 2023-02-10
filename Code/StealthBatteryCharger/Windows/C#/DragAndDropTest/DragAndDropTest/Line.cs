using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace DragAndDropTest
{
    class Line : DrawingObjects 
    {
        Point mOne;
        Point mTwo;
        double mSize;

        public Line(Point one, Point two, double size, Color color) : base()
    {
            mOne = one;
            mTwo = two;
            mColor = color;
            mSize = size;
        }

        public Line(double x1, double y1, double x2, double y2, double size, Color color)
        {
            mOne = new Point(x1, y1, size, color);
            mTwo = new Point(x2, y2, size, color);
            mColor = color;
            mSize = size;
        }

        public override void Draw(Graphics graph, double xOffset, double yOffset)
        {
            if(Color.Black != mColor)
            {
                Brush theBush = new SolidBrush(mColor);
                Pen thePen = new Pen(mColor);
                thePen.Width = (float)mSize;

                if(mColor == Color.MistyRose)
                {
                    thePen = new Pen(Color.Black);
                    thePen.Width = (float)mSize;
                }

                if (mColor == Color.DarkBlue)
                {
                    thePen = new Pen(Color.White);
                    if (mOne.GetX() != mTwo.GetX())
                    {
                        graph.DrawLine(thePen,
                                        (float)(xOffset + mOne.GetX()),
                                        (float)(yOffset + mOne.GetY()+(mSize/2)),
                                        (float)(xOffset + mTwo.GetX()),
                                        (float)(yOffset + mTwo.GetY() + (mSize / 2)));
                        graph.DrawLine(thePen,
                                        (float)(xOffset + mOne.GetX()),
                                        (float)(yOffset + mOne.GetY() - (mSize / 2)),
                                        (float)(xOffset + mTwo.GetX()),
                                        (float)(yOffset + mTwo.GetY() - (mSize / 2)));
                    }
                    else
                    {
                        graph.DrawLine(thePen,
                                        (float)(xOffset + mOne.GetX() + (mSize / 2)),
                                        (float)(yOffset + mOne.GetY()),
                                        (float)(xOffset + mTwo.GetX() + (mSize / 2)),
                                        (float)(yOffset + mTwo.GetY()));
                        graph.DrawLine(thePen,
                                        (float)(xOffset + mOne.GetX() - (mSize / 2)),
                                        (float)(yOffset + mOne.GetY()),
                                        (float)(xOffset + mTwo.GetX() - (mSize / 2)),
                                        (float)(yOffset + mTwo.GetY()));
                    }
                }
                else
                {
                    graph.DrawLine( thePen, 
                                    (float)(xOffset + mOne.GetX()),
                                    (float)(yOffset + mOne.GetY()),
                                    (float)(xOffset + mTwo.GetX()),
                                    (float)(yOffset + mTwo.GetY()));
                }
            }
        }



    }
}
