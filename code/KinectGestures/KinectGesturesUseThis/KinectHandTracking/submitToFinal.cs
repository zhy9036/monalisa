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
        public void submitToFinal()
        {

            /* string URL = "http://shapetest.loganstrong.com/Updater.php";
             using (WebClient wc = new WebClient()) {
                 wc.Headers[HttpRequestHeader.ContentType] = "application/xml";
                 string htmlResult = wc.UploadString(URL, "");
             }
             this.isRecognized = "submittedToFinal";*/

            var httpWebRequest = (HttpWebRequest)WebRequest.Create("http://andrewlewis.pythonanywhere.com/currentWall/");
            httpWebRequest.ContentType = "application/json";
            httpWebRequest.Method = "POST";

            using (var streamWriter = new StreamWriter(httpWebRequest.GetRequestStream()))
            {
                string json = new JavaScriptSerializer().Serialize(new
                {
                    currentWall = 3
                });

                streamWriter.Write(json);
            }

            var httpResponse = (HttpWebResponse)httpWebRequest.GetResponse();
            using (var streamReader = new StreamReader(httpResponse.GetResponseStream()))
            {
                var result = streamReader.ReadToEnd();
            }
        }
    }
}
