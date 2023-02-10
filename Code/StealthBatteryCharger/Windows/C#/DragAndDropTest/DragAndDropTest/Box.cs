using System;
using System.Collections.Generic;
using System.Text;
using System.Windows.Forms;
using System.Drawing;

namespace DragAndDropTest
{
    class Box : DrawingObjects 
    {
        Line mOne;
        Line mTwo;
        Line mThree;
        Line mFour;

        public Box(double x1, double y1, double x2, double y2, double size, Color color) : base()
        {
            mOne   = new Line(x1, y1, x2, y1, size, color);
            mTwo   = new Line(x2, y1, x2, y2, size, color);
            mThree = new Line(x2, y2, x1, y2, size, color);
            mFour  = new Line(x1, y1, x1, y2, size, color);
        }

        public override void SetColor(Color color)
        {
            mOne.SetColor(color);
            mTwo.SetColor(color);
            mThree.SetColor(color);
            mFour.SetColor(color);
        }

        public override void Draw(Graphics graph, double xOffset, double yOffset)
        {
            mOne.Draw(graph, xOffset, yOffset);
            mTwo.Draw(graph, xOffset, yOffset);
            mThree.Draw(graph, xOffset, yOffset);
            mFour.Draw(graph, xOffset, yOffset);
        }


    }
}
