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
        public void postToURL(Point point, double width, double height)
        {
            debug("Calling Submit function");
            //adjust points on canvas to be on the scale of 
            // 0 < x < 24.5 and 0 < y < 48.5
            double x_val = (24.5 / canvas.ActualWidth) * point.X; // 0.01276 * point.X
            double y_val = (48.5 / canvas.ActualHeight) * point.Y; // 0.04491 * point.Y

            // translate width and height to percentages
            width = (width / canvas.ActualWidth) * 100;
            height = (height / canvas.ActualHeight) * 100;

            // extra check to make sure width and height are not 0 
            // (cannot be 0 from Hamid's specifications)
            if (width <= 0)
            {
                width = 0.1;
            }
            if (height <= 0)
            {
                height = 0.1;
            }

            // round all numbers to two decimal places
            x_val = Math.Round(x_val, 2);
            y_val = Math.Round(y_val, 2);
            width = Math.Round(width, 2);
            height = Math.Round(height, 2);

            // used for coloring the box on-screen
            var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://andrewlewis.pythonanywhere.com/currentWallCoord/");
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    x = Convert.ToInt32(x_val),
                    y = Convert.ToInt32(y_val),
                    w = Convert.ToInt32(width),
                    h = Convert.ToInt32(height)
                });

                streamWriter.Write(json);
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }

            this.isRecognized = "submitted";
        }
    }
}
