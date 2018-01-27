using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KinectGestures
{
    public partial class MainWindow
    {
        public void refreshGestures()
        {
            //used for color of rectangle
            this.isRecognized = "false";

            //right hand swipes
            gestures.isRightSwipeRight = false;
            gestures.isRightSwipeLeft = false;

            //right hand updown lasso
            gestures.isRightLassoUp = false;
            gestures.isRightLassoDown = false;

            //left hand swipes
            gestures.isLeftSwipeRight = false;
            gestures.isLeftSwipeLeft = false;
            //left hand swipes
            gestures.isLeftLassoUp = false;
            gestures.isLeftLassoDown = false;
        }
    }
}
