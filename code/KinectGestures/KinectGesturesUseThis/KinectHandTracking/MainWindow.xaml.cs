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
    /// </summary>
    /// 

    public class gesture
    {
        //right hand swipes
        public bool isRightSwipeRight = false;
        public bool isRightSwipeLeft = false;
        //right hand updown lasso
        public bool isRightLassoUp = false;
        public bool isRightLassoDown = false;

        //left hand swipes
        public bool isLeftSwipeRight = false;
        public bool isLeftSwipeLeft = false;
        //left hand swipes
        public bool isLeftLassoUp = false;
        public bool isLeftLassoDown = false;

        public gesture() { }
    }

    public partial class MainWindow : Window
    {
        //gesture variables
        //bool isGesture = false;

        // default width and height of rectangle hard-coded to 400..
        // these variables are used as globals that our drawing and posting functions use
        double rectWidth = 400;
        double rectHeight = 400;
        Point rectLocation;
        string isRecognized = "false";

        //used as the counter for posting xml after 5 seconds of user-inactivity
        int counter = 0;

        gesture gestures = new gesture();

        // testing
        bool openingWWall = false;
        bool secCheck = false;
        double i_left;
        double i_right;
        double handRightY1, handLeftY1, handRightX;
        double yi, xi;

        Point rightHandLocation = new Point();

        // flip to get debug messages in console
        bool DebugFlag = false;

        //
        int coolDown = 0;

        //handRight.Position only works in comparisons with other joint locations,
        // you cannot use this to compare to the rectLocation

        #region Members

        KinectSensor Sensor;
        MultiSourceFrameReader Reader;
        IList<Body> Bodies;

        #endregion

        #region Constructor

        public MainWindow()
        {
            InitializeComponent();

        }

        #endregion

        #region Event handlers



        //sets up sensor
        private void Window_Loaded(object sender, RoutedEventArgs e)
        {
            submitToFinal();
            Sensor = KinectSensor.GetDefault();
            rectLocation = new Point(canvas.ActualWidth / 2, canvas.ActualHeight / 2);

            //System.Drawing.SolidBrush myBrush = new System.Drawing.SolidBrush(System.Drawing.Color.Red);
            //System.Drawing.Graphics formGraphics = this.CreateGraphics();
            //formGraphics.FillRectangle(myBrush, new Rectangle(0, 0, 200, 300));
            //myBrush.Dispose();
            //formGraphics.Dispose();

            if (Sensor != null)
            {
                Sensor.Open();

                Reader = Sensor.OpenMultiSourceFrameReader(FrameSourceTypes.Color | FrameSourceTypes.Depth | FrameSourceTypes.Infrared | FrameSourceTypes.Body);
                Reader.MultiSourceFrameArrived += Reader_MultiSourceFrameArrived;
            }
        }

        //terminates data streams
        private void Window_Closed(object sender, EventArgs e)
        {
            if (Reader != null)
            {
                Reader.Dispose();
            }

            if (Sensor != null)
            {
                Sensor.Close();
            }
        }

        public void debug(String str)
        {
            if (this.DebugFlag == true)
            {
                Console.WriteLine(str);
            }
        }

            #endregion
    }
    
}
