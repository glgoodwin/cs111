// Animation Players which want the AnimationPlayer GUI component
// to update a frame number display should implement this interface.
// Then, to activate the service, the Animation Player must invoke the
// setFrameNumberDisplay(FrameNumberDisplay gui) method
// of the AnimationPlayer GUI component.
interface FrameNumberDisplay
{
    void setFrameNumberDisplay (int currentFrameNumber);
}
