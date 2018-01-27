using Microsoft.Kinect;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.Drawing;
using System.Net;
using System.Web.Script.Serialization;

namespace KinectGestures
{
    public partial class MainWindow
    {

        public void DrawRectangle(Canvas canvas, Point point, double width, double height, string recognition)
        {
            // These colors below indicate on screen which action the wall is taking
            SolidColorBrush brush = new SolidColorBrush();
            if (recognition == "true") //set to true when gesture is being recognized
            {
                brush.Color = Colors.Blue;
            }
            else if (recognition == "false") //set to false when we refresh gestures
            {
                brush.Color = Colors.Black;
            }
            else if (recognition == "submitting") //set to submitting when counter is > 30 and we havent already submitted
            {
                brush.Color = Colors.Orange;
            }
            else if (recognition == "submitted") //set to submitted when xml is posted to web page
            {
                brush.Color = Colors.Yellow;
            }
            else if (recognition == "submittedToFinal")
            {
                brush.Color = Colors.Green;
            }
            else
            {
                brush.Color = Colors.Black;
            }

            //normal thickness
            int strokeThickness = 10;

            //account for when width  and/or height are 0 (we wanted something to show up on-screen)
            if (width <= 0)
            {
                width = 1;
                strokeThickness = 50;
            }
            if (height <= 0)
            {
                height = 1;
                strokeThickness = 50;
            }

            //draw rectangle
            Rectangle newRect = new Rectangle
            {
                Width = width,
                Height = height,
                StrokeThickness = strokeThickness,
                Stroke = brush,
                Fill = new SolidColorBrush(Colors.Transparent)
            };

            Canvas.SetLeft(newRect, point.X - newRect.Width / 2);
            Canvas.SetTop(newRect, point.Y - newRect.Height / 2);

            canvas.Children.Add(newRect);

        }
    }
}
