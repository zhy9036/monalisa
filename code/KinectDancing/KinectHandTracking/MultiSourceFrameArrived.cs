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
        void Reader_MultiSourceFrameArrived(object sender, MultiSourceFrameArrivedEventArgs e)
        {
            var reference = e.FrameReference.AcquireFrame();
            int counter = 0;

            // Color
            using (var frame = reference.ColorFrameReference.AcquireFrame())
            {
                if (frame != null)
                {
                    camera.Source = frame.ToBitmap();
                }
            }

            // Body
            using (var frame = reference.BodyFrameReference.AcquireFrame())
            {
                //check if we got connection to the connect
                if (frame != null)
                {
                    //setup
                    canvas.Children.Clear();
                    Bodies = new Body[frame.BodyFrameSource.BodyCount];
                    frame.GetAndRefreshBodyData(Bodies);
                    counter++;

                    //iterate through the bodies and get their hands
                    foreach (var body in Bodies)
                    {
                        //checks if there are any bodies needing to be tracked
                        if (body != null)
                        {
                            if (body.IsTracked)
                            {
                                // Find the joints being used
                                Joint handRight = body.Joints[JointType.HandRight];
                                Joint thumbRight = body.Joints[JointType.ThumbRight];
                                Joint elbowRight = body.Joints[JointType.ElbowRight];

                                Joint handLeft = body.Joints[JointType.HandLeft];
                                Joint thumbLeft = body.Joints[JointType.ThumbLeft];
                                Joint elbowLeft = body.Joints[JointType.ElbowLeft];

                                Joint head = body.Joints[JointType.Head];

                                Joint shoulderLeft = body.Joints[JointType.ShoulderLeft];
                                Joint shoulderRight = body.Joints[JointType.ShoulderRight];

                                Joint hipLeft = body.Joints[JointType.HipLeft];
                                Joint hipRight = body.Joints[JointType.HipRight];
                                Joint bodyMiddle = body.Joints[JointType.SpineMid];

                                Joint kneeLeft = body.Joints[JointType.KneeLeft];
                                Joint kneeRight = body.Joints[JointType.KneeRight];

                                // Draw hands and thumbs
                                //canvas.DrawHand(handRight, Sensor.CoordinateMapper);
                                //canvas.DrawHand(handLeft, Sensor.CoordinateMapper);
                                //canvas.DrawThumb(thumbRight, Sensor.CoordinateMapper);
                                //canvas.DrawThumb(thumbLeft, Sensor.CoordinateMapper);

                                //draws entire skeleton
                                canvas.DrawSkeleton(body, Sensor.CoordinateMapper);

                                // get true values for right hand x and right hand y (rhY)
                                double rhx = canvas.ActualWidth * handRight.Position.X + canvas.ActualWidth / 2;
                                double rhy = canvas.ActualHeight * -handRight.Position.Y + canvas.ActualHeight / 2;
                                rightHandLocation.X = rhx;
                                rightHandLocation.Y = rhy;
                                //Console.WriteLine("width: " + canvas.ActualWidth); 

                                //get the right hand state

                                //pose 1: arms crossed around of chest
                                //arms crossed like thug fires off
                                //arms on top of each other also fires off
                                //

                                if (handRight.Position.X < handLeft.Position.X &&
                                    handRight.Position.X < head.Position.X &&
                                    handLeft.Position.X > head.Position.X &&
                                    handRight.Position.Y > elbowRight.Position.Y &&
                                    handLeft.Position.Y > elbowLeft.Position.Y &&
                                    Math.Abs(handRight.Position.Y - elbowRight.Position.Y) > .05 &&
                                    Math.Abs(handLeft.Position.Y - elbowLeft.Position.Y) > .05 &&
                                    handRight.Position.Y < head.Position.Y &&
                                    handLeft.Position.Y < head.Position.Y &&
                                    handRight.Position.Y > bodyMiddle.Position.Y &&
                                    handLeft.Position.Y > bodyMiddle.Position.Y)
                                {
                                    if (predetermined_state != 1)
                                    {
                                        predetermined_state = 1;
                                        submitToFinal(predetermined_state);
                                    }

                                }

                                //pose 2: right hand over head & left arm is down by the left leg
                                //energy channeling fires off (saluting will fire off as well)
                                //

                                if (handRight.Position.Y > head.Position.Y &&
                                    handRight.Position.X > shoulderLeft.Position.X &&
                                    handRight.Position.X < shoulderRight.Position.X &&
                                    handLeft.Position.Y < hipLeft.Position.Y)


                                {
                                    if (predetermined_state != 2)
                                    {
                                        predetermined_state = 2;
                                        submitToFinal(predetermined_state);
                                    }

                                }

                                Console.WriteLine("hand right difference: " + Math.Abs(handRight.Position.Y - hipRight.Position.Y));
                                Console.WriteLine("hand left difference: " + Math.Abs(handLeft.Position.Y - hipLeft.Position.Y));
                                //pose 3: hands on hips              

                                if (handRight.Position.X > handLeft.Position.X &&
                                    handRight.Position.X > hipRight.Position.X &&
                                    handLeft.Position.X < hipLeft.Position.X &&
                                    Math.Abs(handRight.Position.X - hipRight.Position.X) < .16 &&
                                    Math.Abs(handLeft.Position.X - hipLeft.Position.X) < .16 &&                                
                                    handRight.Position.Y > hipRight.Position.Y &&
                                    handLeft.Position.Y > hipLeft.Position.Y &&
                                     Math.Abs(handRight.Position.Y - hipRight.Position.Y) > .06 &&
                                    Math.Abs(handLeft.Position.Y - hipLeft.Position.Y) > .06 &&
                                    Math.Abs(handRight.Position.Y - hipRight.Position.Y) < .2 &&
                                    Math.Abs(handLeft.Position.Y - hipLeft.Position.Y) < .2 &&
                                    handLeft.Position.Y < bodyMiddle.Position.Y)
                                {
                                    if (predetermined_state != 3)
                                    {
                                        predetermined_state = 3;
                                        submitToFinal(predetermined_state);
                                    }
                                }

                                //pose 4:  both hands above the head

                                if (handRight.Position.X < head.Position.X &&
                                    handLeft.Position.X > head.Position.X &&
                                    handRight.Position.Y > head.Position.Y &&
                                    handLeft.Position.Y > head.Position.Y &&
                                    handRight.Position.Y > elbowRight.Position.Y &&
                                    handLeft.Position.Y > elbowLeft.Position.Y)
                                {
                                    if (predetermined_state != 4)
                                    {
                                        predetermined_state = 4;
                                        submitToFinal(predetermined_state);
                                    }
                                }

                                //pose 5:  hands on knees (the conditions aren't smoothed out yet)

                                if (handRight.Position.X < shoulderRight.Position.X &&
                                    handLeft.Position.X > head.Position.X &&
                                    handRight.Position.Y > head.Position.Y &&
                                    handLeft.Position.Y > head.Position.Y &&
                                    handRight.Position.Y > elbowRight.Position.Y &&
                                    handLeft.Position.Y > elbowLeft.Position.Y)
                                {
                                    if (predetermined_state != 4)
                                    {
                                        predetermined_state = 5;
                                        submitToFinal(predetermined_state);
                                    }
                                }

                                //pose 6:  dabbing

                                if (handRight.Position.X < head.Position.X &&
                                    handLeft.Position.X > head.Position.X &&
                                    handRight.Position.Y > head.Position.Y &&
                                    handLeft.Position.Y > head.Position.Y &&
                                    handRight.Position.Y > elbowRight.Position.Y &&
                                    handLeft.Position.Y > elbowLeft.Position.Y)
                                {
                                    if (predetermined_state != 4)
                                    {
                                        predetermined_state = 6;
                                        submitToFinal(predetermined_state);
                                    }
                                }


                                // kinect captures approximately 30 frames-per-second
                                // therefore we wait approximately 5 seconds before posting
                                // we also do not post unless the state of the wall has been changed since the previous submit
                                /*if(predetermined_state != 0)
                                {
                                    checkCounter();
                                    predetermined_state = 0;
                                }*/

                            }
                        }
                    }
                }
            }
        }
    }
}
