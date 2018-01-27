using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace KinectGestures
{
    public partial class MainWindow
    {
        public void checkCounter()
        {
            if (counter >= 150 && this.isRecognized != "submitted" && this.isRecognized != "submittedToFinal")
            {
                //reset counter
                //counter = 0;
                //post to web server
                this.isRecognized = "submitting";
                postToURL(this.rectLocation, this.rectWidth, this.rectHeight);
            }

            if (counter >= 300 && this.isRecognized == "submitted")
            {
                //reset counter
                counter = 0;
                //post to web server
                this.isRecognized = "submitting";
                submitToFinal();
            }
        }
    }
}
